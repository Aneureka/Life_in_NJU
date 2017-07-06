//
// Created by Hiki on 2017/5/23.
//

#include "page.h"
#include <stdio.h>

struct vpage {

    // 是否被占用 空闲=0 占用=1
    int occupied;

    // 对应的页框
    p_page_index ppi;

} v_pages[VM_SIZE / PAGE_SIZE];

struct ppage {

    // 占用状态，空闲为0，占用时间越长并且没有进行读写操作，status越大
    int status;

} p_pages[VM_SIZE / PAGE_SIZE];


/*
 * ================= 辅助方法 =================
 */
void allocate_p_pages(v_page_index vpi, page_num num);
void free_p_pages(v_page_index vpi, page_num num);
int is_proper_vp_index(v_page_index vpi, page_num num);
v_page_index get_vpi_by_va(v_address address);
p_page_index get_ppi_by_pa(p_address address);
unsigned int get_in_page_offset(v_address address);
int is_in_memory(p_page_index ppi);
int is_memory_full();
v_page_index get_vpi_by_ppi(p_page_index ppi);
p_page_index find_first_p_page();
p_page_index find_empty_mem_p_page();
p_page_index find_empty_disk_p_page();
void page_schedule(p_page_index ppi);
void disk_swap_memory(p_page_index ppi_in_mem, p_page_index ppi_in_disk);
void disk_to_memory(p_page_index ppi_in_disk);
void update_mem_status();
// 初始化
void init_pages(){

    // 初始化虚拟内存
    for (v_page_index i = 0; i < MAX_V_PAGE_NUM; ++i) {
        v_pages[i].occupied = 0;
        v_pages[i].ppi = -1;
    }

    // 初始化物理内存
    for (p_page_index i = 0; i < MAX_P_PAGE_NUM; ++i) {
        p_pages[i].status = 0;
    }

}

// 分配虚拟页号
int allocate_pages(v_page_index *vpi, page_num num) {
    // 找到一个合适的起始页号，使得空间可以得到分配，同时这些虚页的占用状态设为1
    for (v_page_index i = 0; i < MAX_V_PAGE_NUM; i++) {
        // 若遇到第一个空闲页号，判断是否满足要求，若满足要求，直接返回，若不满足，则找到下一个为1的位置
        if (v_pages[i].occupied == 0) {
            if (is_proper_vp_index(i, num)){
                *vpi = i;
                // 分配物理空间，同时occupied设为1
                allocate_p_pages(*vpi, num);
                return 0;
            }
            else {
                while (v_pages[i].occupied != 1){
                    i++;
                }
            }
        }
    }

    // 如果始终没找到，返回-1，分配失败
    return -1;

}

// 释放虚拟页号
void free_pages(v_page_index vpi, page_num num){
    // 先释放物理空间
    free_p_pages(vpi, num);
    // 释放虚拟空间
    for (v_page_index i = vpi; i < vpi + num; ++i) {
        v_pages[i].occupied = 0;
    }
}

    // 根据请求的地址空间得到请求的页数
    page_num get_page_num(m_size_t size){
    // printf("size: %d", size);
    // printf("page num : %d\n", size / PAGE_SIZE + (size % PAGE_SIZE ? 1 : 0));
    return size / PAGE_SIZE + (size % PAGE_SIZE ? 1 : 0);
}

// 根据虚拟地址找到相应的物理地址：通过页映射关系
p_address get_p_address(v_address address){
    // 获取虚拟页号，并转化为页框号
    p_page_index ppi = v_pages[get_vpi_by_va(address)].ppi;
    // 获取页内偏移
    unsigned int offset = get_in_page_offset(address);

    return ppi * PAGE_SIZE + offset;

}


// 在页式存储的前提下读取数据
void read_in_paging(data_unit *data, v_address address){

    // 将虚拟地址映射到物理地址
    p_address pa = get_p_address(address);
    // 得到其ppi
    p_page_index ppi = get_ppi_by_pa(pa);

    // 若该地址处于内存中，直接读出来
    if (is_in_memory(ppi)) {
        *data = mem_read(pa);
        // status++
        update_mem_status();
        p_pages[ppi].status--;
        return;
    }

    // 若不在内存中，进行页面调度
    page_schedule(ppi);

    // 从内存中获取数据，注意此时address对应的物理地址已经发生改变
    mem_read(get_p_address(address));

}


// 在页式存储的前提下写入数据
void write_in_paging(data_unit data, v_address address){

    // 将虚拟地址映射到物理地址
    p_address pa = get_p_address(address);
    // 得到其ppi
    p_page_index ppi = get_ppi_by_pa(pa);

    // 若该地址处于内存中，直接写入数据
    if (is_in_memory(ppi)) {
        mem_write(data, pa);
        // status++
        update_mem_status();
        p_pages[ppi].status--;
        return;
    }

    // 若不在内存中，进行页面调度
    page_schedule(ppi);

    // 从内存中写入数据，注意此时address对应的物理地址已经发生改变
    mem_write(data, get_p_address(address));

}




/*
 * ================= 辅助方法 =================
 */

// 建立虚页与页框的映射关系，即真正分配物理存储空间，若内存不足，则额外的分配到外存中
void allocate_p_pages(v_page_index vpi, page_num num){

    p_page_index ppi = 0;

    // 遍历物理内存，找到空闲的就映射到虚拟页号，如果被占用就继续loop
    for (v_page_index i = vpi; i < vpi + num; ++i) {
        while(p_pages[ppi].status != 0){
            ppi++;
        }
        // TODO
        p_pages[ppi].status = 1;
        v_pages[i].ppi = ppi;
        v_pages[i].occupied = 1;
        ppi++;

    }

//    // 如果分配到内存中，返回1，否则返回0
//    return is_in_memory(ppi);

}

// 解除虚页与页框的映射关系，即真正释放物理存储空间
void free_p_pages(v_page_index vpi, page_num num){
    // 需要将虚页对应的页框号的status改成0，同时虚页号对应的ppi设为0
    for (v_page_index i = vpi; i < vpi + num; i++) {
        p_pages[v_pages[i].ppi].status = 0;
        v_pages[i].ppi = 0;
    }
}


// 判断以这个vpi开始的虚页空间是否满足要求，即是否能够提供足够大小的空间
int is_proper_vp_index(v_page_index vpi, page_num num){

    // 如果超出范围，返回0
    if (vpi + num > MAX_V_PAGE_NUM){
        // printf("out of vm!");
        return 0;
    }

    // 遍历，观察是否status都为0;
    for (v_page_index i = vpi; i < vpi + num; ++i) {
        if (v_pages[i].occupied == 1)
            return 0;
    }

    return 1;

}

// 根据虚拟地址得到相应的虚拟页号
v_page_index get_vpi_by_va(v_address address){
    return address / PAGE_SIZE;
}

// 根据物理地址得到相应的物理页号
p_page_index get_ppi_by_pa(p_address address){
    return address / PAGE_SIZE;
}

// 得到页内偏移量
unsigned int get_in_page_offset(v_address address){
    return address % PAGE_SIZE;
}

// 判断实际页号是否在内存中
int is_in_memory(p_page_index ppi){
    return ppi < MAX_PM_PAGE_NUM ? 1 : 0;
}

// 判断内存中是否占满
int is_memory_full(){
    for (p_page_index i = 0; i < MAX_PM_PAGE_NUM; ++i) {
        if (p_pages[i].status == 0)
            return 0;
    }
    return 1;
}

// 根据页框号找到相应的虚页号
v_page_index get_vpi_by_ppi(p_page_index ppi){
    for (v_page_index i = 0; i < MAX_V_PAGE_NUM; ++i) {
        if (v_pages[i].ppi == ppi)
            return i;
    }
    return -1;
}

// 找出最先用到的页框号，即它是最有可能被换掉的
p_page_index find_first_p_page(){
    p_page_index found = 0;

    for (p_page_index i = 0; i < MAX_PM_PAGE_NUM; ++i) {
        if (p_pages[i].status > p_pages[found].status)
            found = i;
    }

    return found;

}

// 找出第一个空闲的内存页框号
p_page_index find_empty_mem_p_page(){

    for (p_page_index i = 0; i < MAX_PM_PAGE_NUM; ++i) {
        if (p_pages[i].status == 0)
            return i;
    }

    return -1;

}


// 找出第一个空闲的外存页框号
p_page_index find_empty_disk_p_page(){

    for (p_page_index i = MAX_PM_PAGE_NUM; i < MAX_P_PAGE_NUM; ++i) {
        if (p_pages[i].status == 0)
            return i;
    }

    return -1;

}


// 页面调度
void page_schedule(p_page_index ppi){

    // 如果内存足够，只需将disk中的相应的一个页框加载到内存中
    if(!is_memory_full()){
        // printf("memory not full\n");
        disk_to_memory(ppi);
    } else {
        // printf("memory full\n");
        disk_swap_memory(find_first_p_page(), ppi);
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
    v_pages[get_vpi_by_ppi(ppi_in_disk)].ppi = ppi_in_mem;
    v_pages[get_vpi_by_ppi(ppi_in_mem)].ppi = dppi;

}

// 将disk中的一个页加载到内存中
void disk_to_memory(p_page_index ppi_in_disk){

    // 找出合适的内存块
    p_page_index mppi = find_empty_mem_p_page();
    // printf("proper mem: %d\n", mppi);

    // 将disk中的内容加载到内存中
    disk_load(mppi * PAGE_SIZE, (ppi_in_disk - MAX_PM_PAGE_NUM) *  PAGE_SIZE, PAGE_SIZE);

    // 相应vpi对应的ppi更换
    v_pages[get_vpi_by_ppi(ppi_in_disk)].ppi = mppi;

}


void update_mem_status(){
    for (p_page_index i = 0; i < MAX_PM_PAGE_NUM; ++i) {
        if (p_pages[i].status != 0)
            p_pages[i].status++;
    }
}