//
// Created by Hiki on 2017/5/23.
//

#include "page.h"
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <math.h>


/**
 * 页信息要保存在内存中
 * 虚页信息放在内存中，占40*4个页（0-159）
 * 页框信息放在内存中，占40个页（160-199）
 * 进程信息放在内存中，占100个页（200-299）
 */



/*
 * ================= 辅助函数 =================
 */

/*
 * 直接关系到核心功能的函数
 */

void allocate_p_pages(v_page_index vpi, page_num num); // 建立虚页与页框的映射关系，即真正分配物理存储空间，若内存不足，则额外的分配到外存中
void free_p_pages(v_page_index vpi, page_num num); // 解除虚页与页框的映射关系，即真正释放物理存储空间
void page_schedule(p_page_index ppi); // 进行页面调度，在读写操作所在的地址处于disk的时候
void disk_swap_memory(p_page_index ppi_in_mem, p_page_index ppi_in_disk); // 将disk与memory的内容进行互换（但不一定是点对点互换）
void disk_to_memory(p_page_index ppi_in_disk, p_page_index ppi_in_mem); // 将disk中的内容迁移到memory


/*
 * find/get函数
 */
p_page_index find_first_empty_pm_page(); // 找到memory中第一个空闲页框，如果找不到，返回MAX
p_page_index find_proper_p_page(); // 找到合适的页框，以将磁盘的内容迁移到内存中（现在的实现是随机算法）
p_page_index find_empty_disk_p_page(); // 找到disk中的一个空闲页框
v_page_index get_vpi_by_va(v_address address); // 根据虚拟地址得到虚页号
p_page_index get_ppi_by_pa(p_address address); // 根据物理地址得到页框号
v_page_index get_vpi_by_ppi(p_page_index ppi); // 根据页框号得到虚页号
unsigned int get_in_page_offset(v_address address); // 得到地址的页内偏移量

/*
 * 判断函数
 */
int is_proper_vp_index(v_page_index vpi, page_num num); // 判断vpi开始的虚页空间是否满足要求，即是否能够提供足够大小的空间
int is_in_memory(p_page_index ppi); // 判断页框是否在memory中

/*
 * 操作页面信息的函数
 */
p_page_index read_ppi_from_v_page(v_page_index vpi); // 在页表中读取ppi
void write_ppi_to_v_page(v_page_index vpi, p_page_index ppi); // 在页表中写入ppi
int is_v_page_occupied(v_page_index vpi); // 判断该虚页是否已经被占用
int read_status_from_p_page(p_page_index ppi); // 读取该页框的状态
void write_status_to_p_page(p_page_index ppi, int status); // 写入该页框的状态
p_address get_pa_of_vpi(v_page_index vpi); // 获取虚页表项的物理地址
p_address get_pa_of_ppi(p_page_index ppi); // 获取页框表项的物理地址
void init_ppi_for_v_page(v_page_index vpi); // 初始化页表项
void init_status_for_p_page(p_page_index ppi); // 初始化特定页框的状态
int read_int_from_mem(p_address pa); // 在内存中读取int型数据
void write_int_to_mem(p_address pa, int num); // 在内存中写入int型数据

void init_pages(){

    // 初始化虚拟内存
    for (v_page_index i = 0; i < MAX_V_PAGE_NUM; ++i) {
        write_ppi_to_v_page(i, MAX_P_PAGE_NUM);
    }

    // 初始化物理内存
    for (p_page_index i = 0; i < MAX_P_PAGE_NUM; ++i) {
        write_status_to_p_page(i, EMPTY);
    }

}

int allocate_pages(v_page_index *vpi, page_num num) {
    // 找到一个合适的起始页号，使得空间可以得到分配，同时这些虚页的ppi设为MAX（初始化ppi）
    for (v_page_index i = START_PI; i < MAX_V_PAGE_NUM; i++) {
        // 若遇到第一个空闲页号，判断是否满足要求，若满足要求，直接返回，若不满足，则找到下一个为1的位置
        if (!is_v_page_occupied(i)) {
            if (is_proper_vp_index(i, num)){
                *vpi = i;
                // 分配物理空间
                allocate_p_pages(*vpi, num);
                return 0;
            }
            else {
                while (!is_v_page_occupied(i)){
                    i++;
                }
            }
        }
    }


    // 如果始终没找到，返回-1，分配失败
    return -1;

}

void free_pages(v_page_index vpi, page_num num){
    // 先释放物理空间
    free_p_pages(vpi, num);
    // 释放虚拟空间
    for (v_page_index i = vpi; i < vpi + num; ++i) {
        init_ppi_for_v_page(i);
    }
}

page_num get_page_num(m_size_t size){
    return size / PAGE_SIZE + (size % PAGE_SIZE ? 1 : 0);
}

p_address get_p_address(v_address address){
    // 获取虚拟页号，并转化为页框号
    p_page_index ppi = read_ppi_from_v_page(get_vpi_by_va(address));
    // 获取页内偏移
    unsigned int offset = get_in_page_offset(address);

    return ppi * PAGE_SIZE + offset;

}

void read_in_paging(data_unit *data, v_address address){

    // 将虚拟地址映射到物理地址
    p_address pa = get_p_address(address);
    // 得到其ppi
    p_page_index ppi = get_ppi_by_pa(pa);

    // 若该地址处于内存中，直接读出来
    if (is_in_memory(ppi)) {
        *data = mem_read(pa);
        return;
    }

    // 若不在内存中，进行页面调度
    page_schedule(ppi);

    // 从内存中获取数据，注意此时address对应的物理地址已经发生改变
    mem_read(get_p_address(address));

}

void write_in_paging(data_unit data, v_address address){

    // 将虚拟地址映射到物理地址
    p_address pa = get_p_address(address);
    // 得到其ppi
    p_page_index ppi = get_ppi_by_pa(pa);

    // 若该地址处于内存中，直接写入数据
    if (is_in_memory(ppi)) {
        mem_write(data, pa);
        return;
    }

    // 若不在内存中，进行页面调度
    page_schedule(ppi);

    // 从内存中写入数据，注意此时address对应的物理地址已经发生改变
    mem_write(data, get_p_address(address));

}


void allocate_p_pages(v_page_index vpi, page_num num){

    p_page_index ppi = 0;

    // 遍历物理内存，找到空闲的就映射到虚拟页号，如果被占用就继续loop
    for (v_page_index i = vpi; i < vpi + num; ++i) {
            while(read_status_from_p_page(ppi) != 0){
                ppi++;
        }
        write_status_to_p_page(ppi, 1);
        write_ppi_to_v_page(i, ppi);
        ppi++;

    }

}

void free_p_pages(v_page_index vpi, page_num num){

    // 需要初始化虚页对应的页框号的status，同时初始化虚页号对应的ppi
    for (v_page_index i = vpi; i < vpi + num; i++) {
        p_page_index ppi = read_ppi_from_v_page(i);
        init_status_for_p_page(ppi);
        init_ppi_for_v_page(vpi);

    }
}

int is_proper_vp_index(v_page_index vpi, page_num num){

    // 如果超出范围，返回0
    if (vpi + num > MAX_V_PAGE_NUM){
        printf("out of vm!");
        return 0;
    }

    // 遍历，观察是否都被占用了
    for (v_page_index i = vpi; i < vpi + num; ++i) {
        if (is_v_page_occupied(i))
            return 0;
    }

    return 1;

}

v_page_index get_vpi_by_va(v_address address){
    return address / PAGE_SIZE;
}

p_page_index get_ppi_by_pa(p_address address){
    return address / PAGE_SIZE;
}

unsigned int get_in_page_offset(v_address address){
    return address % PAGE_SIZE;
}

int is_in_memory(p_page_index ppi){
    return ppi < MAX_PM_PAGE_NUM ? 1 : 0;
}

p_page_index find_first_empty_pm_page(){
    for (p_page_index i = START_PI; i < MAX_PM_PAGE_NUM; ++i) {
        if (read_status_from_p_page(i) == 0)
            return i;
    }
    return MAX_PM_PAGE_NUM;
}

v_page_index get_vpi_by_ppi(p_page_index ppi){
    for (v_page_index i = 0; i < MAX_V_PAGE_NUM; ++i) {
        if (read_ppi_from_v_page(i) == ppi)
            return i;
    }
    return MAX_V_PAGE_NUM;
}

p_page_index find_proper_p_page(){

    // 设定种子
    srand((unsigned) time(0));

    // 生成随机的ppi
    p_page_index ppi = (p_page_index)((rand() % (MAX_PM_PAGE_NUM - START_PI)) + START_PI);

    return ppi;

}

p_page_index find_empty_disk_p_page(){

    for (p_page_index i = MAX_PM_PAGE_NUM; i < MAX_P_PAGE_NUM; ++i) {
        if (read_status_from_p_page(i) == 0)
            return i;
    }

    return MAX_P_PAGE_NUM;

}

void page_schedule(p_page_index ppi){

    // 如果内存足够，只需将disk中的相应的一个页框加载到内存中
    p_page_index em_ppi = find_first_empty_pm_page();

    if(em_ppi != MAX_PM_PAGE_NUM){
//         printf("memory not full\n");
        disk_to_memory(ppi, em_ppi);
    } else {
//         printf("memory full\n");
        disk_swap_memory(find_proper_p_page(), ppi);
    }
}

// 页框号互换，此时映射关系也要改变，
// 流程：将memory中的一个页内容加载到disk某个合适的位置（需要记录）中，再将disk中内容加载到内存，最后vpi对应的ppi也要更换
void disk_swap_memory(p_page_index ppi_in_mem, p_page_index ppi_in_disk){

    // 将memory中的内容加载到disk空闲的位置中
    p_page_index dppi = find_empty_disk_p_page();
    // printf("dppi: %d\n", dppi);
    // printf("mem_add: %d\n", ppi_in_mem * PAGE_SIZE);
    // printf("disk_add: %d\n", (dppi - MAX_PM_PAGE_NUM) *  PAGE_SIZE);

    disk_save(ppi_in_mem * PAGE_SIZE, (dppi - MAX_PM_PAGE_NUM) *  PAGE_SIZE, PAGE_SIZE);

    // 将disk中的内容加载到内存中
    disk_load(ppi_in_mem * PAGE_SIZE, (ppi_in_disk - MAX_PM_PAGE_NUM) *  PAGE_SIZE, PAGE_SIZE);

    // 相应vpi对应的ppi更换
    write_ppi_to_v_page(get_vpi_by_ppi(ppi_in_disk), ppi_in_mem);
    write_ppi_to_v_page(get_vpi_by_ppi(ppi_in_mem), dppi);

}

void disk_to_memory(p_page_index ppi_in_disk, p_page_index ppi_in_mem){

    // 将disk中的内容加载到内存中
    disk_load(ppi_in_mem * PAGE_SIZE, (ppi_in_disk - MAX_PM_PAGE_NUM) *  PAGE_SIZE, PAGE_SIZE);

    // 相应vpi对应的ppi更换
    write_ppi_to_v_page(get_vpi_by_ppi(ppi_in_disk), ppi_in_mem);

}


p_page_index read_ppi_from_v_page(v_page_index vpi){
    // 找到vpi所在的p_address
    p_address pa = get_pa_of_vpi(vpi);
    // 读取ppi
    return (p_page_index) read_int_from_mem(pa);
}

void write_ppi_to_v_page(v_page_index vpi, p_page_index ppi){
//    printf("vpi: %d\n", vpi);
    // 找到vpi所在的p_address
    p_address pa = get_pa_of_vpi(vpi);
    // 将ppi写入
    write_int_to_mem(pa, ppi);
}

int is_v_page_occupied(v_page_index vpi){
    // 如果不被占用，对应的值为MAX_P_PAGE_NUM
    return read_ppi_from_v_page(vpi) != MAX_P_PAGE_NUM ? 1 : 0;
}

int read_status_from_p_page(p_page_index ppi){
    // 找到ppi所在的p_address
    p_address pa = get_pa_of_ppi(ppi);
    // 读出status
    return (int) mem_read(pa);

}

void write_status_to_p_page(p_page_index ppi, int status){
    // 找到ppi所在的p_address
    p_address pa = get_pa_of_ppi(ppi);
    // 写入status
    mem_write((data_unit) status, pa);

}

void init_ppi_for_v_page(v_page_index vpi){
    write_ppi_to_v_page(vpi, MAX_P_PAGE_NUM);
}

void init_status_for_p_page(p_page_index ppi){
    write_status_to_p_page(ppi, 0);
}


p_address get_pa_of_vpi(v_page_index vpi){
    return START_VPI_PA + vpi * PPI_SIZE;
}


p_address get_pa_of_ppi(p_page_index ppi){
    return START_PPI_PA + ppi;
}


int read_int_from_mem(p_address pa){

    int result = 0;

    for (int i = 0; i < PPI_SIZE; ++i) {
        result += mem_read(pa + i) * pow(2, (3-i) * CHAR_SIZE);
    }

    return result;
}

void write_int_to_mem(p_address pa, int num){

    // 将int拆成4个部分
    for (int i = 0; i < INT_BYTE; ++i) {
        int temp = num % (int) pow(2, CHAR_SIZE);
        num = num / (int) pow(2, CHAR_SIZE);
        mem_write((data_unit)temp, pa+3-i);
    }

}


