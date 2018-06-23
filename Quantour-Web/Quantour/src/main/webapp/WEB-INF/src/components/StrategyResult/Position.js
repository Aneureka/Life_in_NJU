/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { Table } from 'antd';

function Position({ data, head }) {
  const expandedRowRender = (dataSource) => {
    const childColumns = [
      { title: '日期', dataIndex: 'date', key: 'date', width: 100 },
      { title: '合约代码', dataIndex: 'code', key: 'code', width: 100 },
      { title: '收盘价/结算价', dataIndex: 'close', key: 'close', width: 100 },
      { title: '仓位', dataIndex: 'position', key: 'position', width: 100 },
      { title: '开仓均价', dataIndex: 'average', key: 'average', width: 100 },
      { title: '市值', dataIndex: 'value', key: 'value', width: 100 },
    ];
    return (
      <Table
        columns={childColumns}
        dataSource={dataSource}
        pagination={false}
        showHeader={false}
      />
    );
  };

  const columns = [
    { title: '日期', dataIndex: 'date', key: 'date', width: 100 },
    { title: '合约代码', dataIndex: 'code', key: 'code', width: 100 },
    { title: '收盘价/结算价', dataIndex: 'close', key: 'close', width: 100 },
    { title: '仓位', dataIndex: 'position', key: 'position', width: 100 },
    { title: '开仓均价', dataIndex: 'average', key: 'average', width: 100 },
    { title: '市值', dataIndex: 'value', key: 'value', width: 100 },
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

export default Position;
