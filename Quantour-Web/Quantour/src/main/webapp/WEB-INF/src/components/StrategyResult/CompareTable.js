/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { Table } from 'antd';

function CompareTable({ data }) {
  const columns = [
    {
      title: '日期',
      dataIndex: 'month',
      key: 'month',
      width: 100,
    },
    {
      title: '1个月',
      children: [
        {
          title: '策略',
          dataIndex: 'strategy1',
          key: 'strategy1',
        // sorter: (a, b) => a.age - b.age,
        },
        {
          title: '基准',
          dataIndex: 'base1',
          key: 'base1',
          // sorter: (a, b) => a.age - b.age,
        }],
    },
    {
      title: '3个月',
      children: [
        {
          title: '策略',
          dataIndex: 'strategy3',
          key: 'strategy3',
          // sorter: (a, b) => a.age - b.age,
        },
        {
          title: '基准',
          dataIndex: 'base3',
          key: 'base3',
          // sorter: (a, b) => a.age - b.age,
        }],
    },
    {
      title: '6个月',
      children: [
        {
          title: '策略',
          dataIndex: 'strategy6',
          key: 'strategy6',
          // sorter: (a, b) => a.age - b.age,
        },
        {
          title: '基准',
          dataIndex: 'base6',
          key: 'base6',
          // sorter: (a, b) => a.age - b.age,
        }],
    },
    {
      title: '12个月',
      children: [
        {
          title: '策略',
          dataIndex: 'strategy12',
          key: 'strategy12',
          // sorter: (a, b) => a.age - b.age,
        },
        {
          title: '基准',
          dataIndex: 'base12',
          key: 'base12',
          // sorter: (a, b) => a.age - b.age,
        }],
    },
  ];
  const needPage = data.length > 10;
  return (
    <Table
      columns={columns}
      dataSource={data}
      rowKey={record => record.key}
      bordered
      // scroll={{ y: 240 }}
      pagination={needPage}
    />
  );
}

export default CompareTable;
