//
// Created by Hiki on 2017/5/22.
//

#include "call.h"
#include "process.h"
#include <stdio.h>

// 对已经使用的变量进行一些初始化工作
void init(){

//    printf("init process...");
    // 初始化进程
    init_processes();

//    printf("init pages...");
    // 初始化页
    init_pages();

}

int read(data_unit *data, v_address address, m_pid_t pid){
    // 若读取不合法，返回-1
    if (!is_legal(address, pid))
        return -1;

    read_in_paging(data, address);

    return 0;
}

int write(data_unit data, v_address address, m_pid_t pid){
    // 若写入不合法，返回-1
    if (!is_legal(address, pid))
        return -1;

    write_in_paging(data, address);

    return 0;
}

int allocate(v_address *address, m_size_t size, m_pid_t pid){
    // 根据所需申请的内存大小获得相应的页数
    page_num num = get_page_num(size);
    // 申请相应的地址空间（以页为单位）
    v_page_index vpi;
    int result = allocate_pages(&vpi, num);
    if (result == -1)
        return result;

    // 写入地址
    *address = vpi * PAGE_SIZE;
    // 为该进程添加新的内存申请信息
    add_new_alloc_mem(*address, size, pid);
    // 返回结果
    return result;

}

int free(v_address address, m_pid_t pid){
    // 若访问不合法，返回-1
    if (!is_legal(address, pid)){
        printf("illegal free");
        return -1;
    }
    // 获得address相应页号及页数
    v_page_index vpi = address / PAGE_SIZE;
    m_size_t size = get_size_by_address(address, pid);
    // 释放空间
//    printf("free pid:%d  address:%d\n", pid, address);
    free_pages(vpi, get_page_num(size));
    // 清除进程的相关空间申请信息
    remove_alloc_mem(address, pid);
    return 0;
}