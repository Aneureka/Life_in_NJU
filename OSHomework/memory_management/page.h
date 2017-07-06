//
// Created by Hiki on 2017/5/23.
//

#ifndef MEMORY_MANAGEMENT_PA_GE_H
#define MEMORY_MANAGEMENT_PA_GE_H

#include "type.h"
#include "bottom.h"

#define PAGE_SIZE (1024 * 4)
#define VM_SIZE (1024 * 1024 * (512 + 128))
#define MAX_V_PAGE_NUM (1024 * 1024 * (512 + 128) / (1024 * 4))
#define MAX_P_PAGE_NUM (1024 * 1024 * (512 + 128) / (1024 * 4))
#define MAX_PM_PAGE_NUM (1024 * 1024 * (128) / (1024 * 4))

typedef unsigned int v_page_index;
typedef unsigned int p_page_index;
typedef unsigned int page_num;


// 初始化
void init_pages();

// 根据请求的地址空间得到请求的页数
page_num get_page_num(m_size_t size);

// 分配虚拟页号
int allocate_pages(v_page_index *vpi, page_num num);

// 释放虚拟页号
void free_pages(v_page_index vpi, page_num num);

// 根据虚拟地址及请求的地址空间得到页数
page_num get_page_num(m_size_t size);

// 根据虚拟地址得到相应的虚拟页号
v_page_index get_vpi_by_va(v_address address);

// 根据虚拟地址找到相应的物理地址：通过页映射关系
p_address get_p_address(v_address address);

// 在页式存储的前提下读取数据
void read_in_paging(data_unit *data, v_address address);

// 在页式存储的前提下写入数据
void write_in_paging(data_unit data, v_address address);

#endif //MEMORY_MANAGEMENT_PA_GE_H
