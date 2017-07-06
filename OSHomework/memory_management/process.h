//
// Created by Hiki on 2017/5/23.
//

#ifndef MEMORY_MANAGEMENT_PROCESS_H
#define MEMORY_MANAGEMENT_PROCESS_H

#include "page.h"

#define MAX_PROCESS_NUM 1000
#define MAX_ALLOC_TIME 1000

typedef struct allocinfo {

    // 起始虚拟地址
    v_address address;

    // 申请到的虚拟空间
    m_size_t size;

} alloc_info;

struct process {

    alloc_info ai[MAX_ALLOC_TIME];

} procs[MAX_PROCESS_NUM];

// 初始化
void init_processes();

// 查询对某个地址的访问是否合法
int is_legal(v_address address, m_pid_t pid);

// 增加新的申请空间信息
void add_new_alloc_mem(v_address address, m_size_t size, m_pid_t pid);

// 去除申请空间信息
void remove_alloc_mem(v_address address, m_pid_t pid);

// 获取address相应的size
m_size_t get_size_by_address(v_address address, m_pid_t pid);

#endif //MEMORY_MANAGEMENT_PROCESS_H
