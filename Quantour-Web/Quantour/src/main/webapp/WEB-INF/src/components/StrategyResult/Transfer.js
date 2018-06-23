/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { Table } from 'antd';

function Transfer({ data, head }) {
  const expandedRowRender = (dataSource) => {
    const columns = [
      { title: '日期', dataIndex: 'date', key: 'date', width: 100 },
      { title: '合约代码', dataIndex: 'code', key: 'code', width: 200 },
      { title: '买/卖', dataIndex: 'buy', key: 'buy', width: 100 },
      { title: '成交量', dataIndex: 'volume', key: 'volume', width: 100 },
      { title: '成交价', dataIndex: 'price', key: 'price', width: 100 },
      { title: '费用', dataIndex: 'cost', key: 'cost', width: 100 },
    ];
    return (
      <Table
        columns={columns}
        dataSource={dataSource}
        pagination={false}
        showHeader={false}
      />
    );
  };

  const columns = [
    { title: '日期', dataIndex: 'date', key: 'date', width: 100 },
    { title: '合约代码', dataIndex: 'code', key: 'code', width: 200 },
    { title: '买/卖', dataIndex: 'buy', key: 'buy', width: 100 },
    { title: '成交量', dataIndex: 'volume', key: 'volume', width: 100 },
    { title: '成交价', dataIndex: 'price', key: 'price', width: 100 },
    { title: '费用', dataIndex: 'cost', key: 'cost', width: 100 },
  ];

  return (
    <Table
      columns={columns}
      expandedRowRender={(recode) => { return expandedRowRender(data[recode.key]); }}
      dataSource={head}
      pagination={false}
      scroll={{ y: 500 }}
    />
  );
}

export default Transfer;
