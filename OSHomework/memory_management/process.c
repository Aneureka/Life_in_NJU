//
// Created by Hiki on 2017/5/23.
//

#include "process.h"
#include <stdio.h>


// 初始化进程
void init_processes(){


    for (int i = 0; i < MAX_PROCESS_NUM; ++i) {
        for (int j = 0; j < MAX_ALLOC_TIME; ++j) {
            procs[i].ai[j].address = 0;
            procs[i].ai[j].size = 0;
        }
    }

}

// 查询对某个地址的访问是否合法
int is_legal(v_address address, m_pid_t pid){

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {

        v_address va = procs[pid].ai[i].address;
        m_size_t size = procs[pid].ai[i].size;

        if (va <= address && address < va + size){
            return 1;
        }

    }

    return 0;
}

// 增加新的申请空间信息
// 如果有空闲的就填进去
void add_new_alloc_mem(v_address address, m_size_t size, m_pid_t pid){

//    printf("pid: %d, address: %d, size: %d\n", pid, address, size);

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {
        if (procs[pid].ai[i].address == 0 && procs[pid].ai[i].size == 0) {
            procs[pid].ai[i].address = address;
            procs[pid].ai[i].size = size;
            break;
        }
    }

}

void remove_alloc_mem(v_address address, m_pid_t pid){

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {
        if (procs[pid].ai[i].address == address) {
            procs[pid].ai[i].address = 0;
            procs[pid].ai[i].size = 0;
        }
    }

}

// 获取address相应的size
m_size_t get_size_by_address(v_address address, m_pid_t pid){

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {
        if (procs[pid].ai[i].address == address) {
            return procs[pid].ai[i].size;
        }
    }

    return 0;

}



