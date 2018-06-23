/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { Table } from 'antd';

function RadioTable({ data }) {
  const columns = [{
    title: '日期',
    dataIndex: 'month',
    key: 'month',
    // width: 90,
  }, {
    title: '1个月',
    dataIndex: 'one',
    key: 'one',
    // width: 60,
  }, {
    title: '3个月',
    dataIndex: 'three',
    key: 'three',
  }, {
    title: '6个月',
    dataIndex: 'six',
    key: 'six',
  }, {
    title: '12个月',
    dataIndex: 'twelve',
    key: 'twelve',
  }];
  const needPage = data.length > 10;
  return (
    <Table
      columns={columns}
      dataSource={data}
      rowKey={record => record.key}
      pagination={needPage}
      // scroll={{ x: false, y: 240 }}
      // size="small"
    />
  );
}

export default RadioTable;
