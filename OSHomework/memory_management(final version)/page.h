//
// Created by Hiki on 2017/5/23.
//

#ifndef MEMORY_MANAGEMENT_PA_GE_H
#define MEMORY_MANAGEMENT_PA_GE_H

#include "type.h"
#include "bottom.h"
#include "prop.h"

typedef unsigned int v_page_index;
typedef unsigned int p_page_index;
typedef unsigned int page_num;

// 初始化页面
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

// 在物理地址中写入int型数据
int read_int_from_mem(p_address pa);

// 读取int数据
void write_int_to_mem(p_address pa, int num);

#endif //MEMORY_MANAGEMENT_PA_GE_H
