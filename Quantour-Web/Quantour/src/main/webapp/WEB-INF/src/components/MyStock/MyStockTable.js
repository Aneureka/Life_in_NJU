/**
 * Created by Hitigerzzz on 2017/5/16.
 */
import React from 'react';
import { Link } from 'dva/router';
import { Table } from 'antd';

function MyStockTable({ list: dataSource }) {
  const columns = [{
    title: '名称',
    dataIndex: 'name',
    key: 'name',
    width: 90,
    render: (text, record) => {
      return <Link to={`/stockinfo?code=${record.code}`}>{text}</Link>;
    },
  }, {
    title: '现价',
    dataIndex: 'close',
    key: 'close',
    width: 60,
    sorter: (a, b) => a.close - b.close,
    render: (text, record) => {
      if (record.pch > 0) {
        return <span style={{ color: 'red' }}>{text}</span>;
      } else if (record.pch < 0) {
        return <span style={{ color: 'green' }}>{text}</span>;
      } else {
        return <span>{text}</span>;
      }
    },
  }, {
    title: '涨跌幅(%)',
    dataIndex: 'pch',
    key: 'pch',
    sorter: (a, b) => a.pch - b.pch,
    render: (text) => {
      if (text > 0) {
        return <span style={{ color: 'red' }}>{text}%</span>;
      } else if (text < 0) {
        return <span style={{ color: 'green' }}>{text}%</span>;
      } else {
        return <span>--</span>;
      }
    },
  }];
  return (
    <Table
      columns={columns}
      dataSource={dataSource}
      rowKey={record => record.id}
      pagination={false}
      scroll={{ x: false, y: 240 }}
      size="small"
    />
  );
}

export default MyStockTable;
