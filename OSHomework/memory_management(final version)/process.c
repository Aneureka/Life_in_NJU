//
// Created by Hiki on 2017/5/23.
//

#include "process.h"

/*
 * ================= 辅助函数 =================
 */


p_address get_pa_of_proc_alloc(m_pid_t pid, int alloc_num); // 获得进程具体的申请记录相应的物理地址

void write_pa_size_to_proc(m_pid_t pid, int alloc_num, p_address p_a, m_size_t size); // 为进程写入新的申请记录

v_address read_va_from_proc(m_pid_t pid, int alloc_num); // 读出特定申请记录的申请地址

m_size_t read_size_from_proc(m_pid_t pid, int alloc_num); // 读出特定申请记录的申请空间大小

void init_processes(){

    for (m_pid_t i = 0; i < MAX_PROCESS_NUM; ++i) {
        for (int j = 0; j < MAX_ALLOC_TIME; ++j) {
            write_pa_size_to_proc(i, j, 0, 0);
        }
    }

}

int is_legal(v_address address, m_pid_t pid){

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {

        v_address va = read_va_from_proc(pid, i);
        m_size_t size = read_size_from_proc(pid, i);

        if (va <= address && address < va + size){
            return 1;
        }

    }

    return 0;
}

void add_new_alloc_mem(v_address address, m_size_t size, m_pid_t pid){

//    printf("pid: %d, address: %d, size: %d\n", pid, address, size);

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {
        if (read_size_from_proc(pid, i) == 0) {
            write_pa_size_to_proc(pid, i, address, size);
            break;
        }
    }

}

void remove_alloc_mem(v_address address, m_pid_t pid){

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {
        if (read_va_from_proc(pid, i) == address) {
            write_pa_size_to_proc(pid, i, 0, 0);
        }
    }

}

m_size_t get_size_by_address(v_address address, m_pid_t pid){

    for (int i = 0; i < MAX_ALLOC_TIME; ++i) {
        if (read_va_from_proc(pid, i) == address) {
            return read_size_from_proc(pid, i);
        }
    }

    return 0;

}

p_address get_pa_of_proc_alloc(m_pid_t pid, int alloc_num){
    return START_PROC_PA + (pid-1) * PROC_SIZE + alloc_num * ALLOC_SIZE;
}

void write_pa_size_to_proc(m_pid_t pid, int alloc_num, p_address p_a, m_size_t size){
    // 找到pid所在的p_address
    p_address pa = get_pa_of_proc_alloc(pid, alloc_num);
//    printf("pa of proc: %d", pa);
    // 将p_a写入
    write_int_to_mem(pa, p_a);
    // 将size写入
    write_int_to_mem(pa + INT_BYTE, size);
}

v_address read_va_from_proc(m_pid_t pid, int alloc_num){
    // 找到pid所在的p_address
    p_address pa = get_pa_of_proc_alloc(pid, alloc_num);
    // 读出pa
    return (v_address) read_int_from_mem(pa);
}

m_size_t read_size_from_proc(m_pid_t pid, int alloc_num){
    // 找到pid所在的p_address
    p_address pa = get_pa_of_proc_alloc(pid, alloc_num);
    // 读出pa
    return (m_size_t) read_int_from_mem(pa + INT_BYTE);
}

