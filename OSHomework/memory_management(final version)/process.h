//
// Created by Hiki on 2017/5/23.
//

#ifndef MEMORY_MANAGEMENT_PROCESS_H
#define MEMORY_MANAGEMENT_PROCESS_H

#include "page.h"

/**
 * 在内存块
 *
 */




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
