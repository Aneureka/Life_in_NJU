/**
 * Created by wyj on 2017/5/31.
 * description:
 */
import React from 'react';
import { Table, Button, message } from 'antd';
import { connect } from 'dva';
import { Link } from 'dva/router';
import TableExponentChart from '../../components/MarketTable/TableExponentChart';

class SelfStockTable extends React.Component {
  state = {
    selfStockList: [],
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.selfStockList !== undefined) {
      this.setState({
        selfStockList: nextProps.selfStockList,
      });
    }
  }
  columns = [
    {
      title: '代码',
      dataIndex: 'code',
      key: 'code',
      render: text => <Link to={`/stockinfo?code=${text}`}>{text}</Link>,
    },
    {
      title: '名称',
      dataIndex: 'name',
      key: 'name',
      render: (text, record) => {
        return <Link to={`/stockinfo?code=${record.code}`}>{text}</Link>;
      },
    },
    {
      title: '现价',
      dataIndex: 'close',
      key: 'close',
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
    },
    {
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
    },
    {
      title: '涨跌',
      dataIndex: 'change',
      key: 'change',
      sorter: (a, b) => a.change - b.change,
      render: (text, record) => {
        if (record.change > 0) {
          return <span style={{ color: 'red' }}>{text}</span>;
        } else if (record.change < 0) {
          return <span style={{ color: 'green' }}>{text}</span>;
        } else {
          return <span>--</span>;
        }
      },
    },
    // {
    //   title: '分时图',
    //   dataIndex: 'time_chart',
    //   width: 150,
    //   render: () => (
    //     <TableExponentChart />
    //   ),
    // },
    {
      title: '操作',
      key: 'operation',
      render: (text, record) => (
        <span>
          <Button
            type="danger"
            shape="circle"
            icon="delete"
            onClick={() => this.removeSelfStock(record.code)}
          />
        </span>
      ),
    },
  ];
  removeSelfStock = (code) => {
    this.props.dispatch({
      type: 'users/deleteSelfStock',
      payload: code,
    });
    setTimeout(this.updateProps, 10);
  };
  updateProps = () => {
    this.props.dispatch({
      type: 'users/fetchSelfStock',
    });
  };
  render() {
    return (
      <div>
        <Table
          columns={this.columns}
          rowKey={record => record.code}
          dataSource={this.state.selfStockList}
          size="middle"
        />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { selfStockList } = state.users;
  return {
    selfStockList,
  };
}

export default connect(mapStateToProps)(SelfStockTable);
