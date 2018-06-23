/**
 * Created by Hitigerzzz on 2017/6/13.
 */
import React from 'react';
import { Table } from 'antd';

function AccountTable({ data }) {
  const columns = [{
    title: '日期',
    dataIndex: 'date',
    key: 'date',
    // width: 90,
  }, {
    title: '可用资金',
    dataIndex: 'cash',
    key: 'cash',
    // width: 60,
  }, {
    title: '总权益',
    dataIndex: 'totalValue',
    key: 'totalValue',
  }, {
    title: '累计盈亏',
    dataIndex: 'profit',
    key: 'profit',
  }, {
    title: '市值',
    dataIndex: 'marketValue',
    key: 'marketValue',
  }, {
    title: '费用',
    dataIndex: 'cost',
    key: 'cost',
  }];
  const needPage = data.length > 10;
  return (
    <Table
      columns={columns}
      dataSource={data}
      rowKey={record => record.key}
      pagination={needPage}
    />
  );
}

export default AccountTable;
