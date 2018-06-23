/**
 * Created by Hitigerzzz on 2017/5/10.
 */
import React from 'react';
import { connect } from 'dva';
import { Table, Button, message } from 'antd';
import { Link } from 'dva/router';
import TableExponentChart from './TableExponentChart';

class StockTable extends React.Component {
  state = {
    selfStockList: [],
    selfStockCodeList: [],
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.selfStockList !== undefined) {
      this.setState({
        selfStockList: nextProps.selfStockList,
        selfStockCodeList: nextProps.selfStockCodeList,
      });
    }
  }
  index = -1;
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
      dataIndex: 'curPrice',
      key: 'curPrice',
      sorter: (a, b) => a.curPrice - b.curPrice,
      render: (text, record) => {
        if (record.changePercent > 0) {
          return <span style={{ color: 'red' }}>{text}</span>;
        } else if (record.changePercent < 0) {
          return <span style={{ color: 'green' }}>{text}</span>;
        } else {
          return <span>{text}</span>;
        }
      },
    },
    {
      title: '涨跌幅(%)',
      dataIndex: 'changePercent',
      key: 'changePercent',
      sorter: (a, b) => a.changePercent - b.changePercent,
      render: (text, record) => {
        if (record.changePercent > 0) {
          return <span style={{ color: 'red' }}>+{text}%</span>;
        } else if (record.changePercent < 0) {
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
        if (record.changePercent > 0) {
          return <span style={{ color: 'red' }}>+{text}</span>;
        } else if (record.changePercent < 0) {
          return <span style={{ color: 'green' }}>{text}</span>;
        } else {
          return <span>--</span>;
        }
      },
    },
    {
      title: '换手',
      dataIndex: 'turnRate',
      key: 'turnRate',
      sorter: (a, b) => a.turnRate - b.turnRate,
    },
    {
      title: '量比',
      dataIndex: 'volRatio',
      key: 'volRatio',
      sorter: (a, b) => a.volRatio - b.volRatio,
    },
    {
      title: '振幅(%)',
      dataIndex: 'swing',
      key: 'swing',
      sorter: (a, b) => a.swing - b.swing,
    },
    {
      title: '成交额',
      dataIndex: 'amount',
      key: 'amount',
      sorter: (a, b) => a.amount - b.amount,
    },
    {
      title: '流通市值',
      dataIndex: 'floatMarketCap',
      key: 'floatMarketCap',
      sorter: (a, b) => a.floatMarketCap - b.floatMarketCap,
    },
    {
      title: '市盈率',
      dataIndex: 'peTTM',
      key: 'peTTM',
      sorter: (a, b) => a.peTTM - b.peTTM,
    },
    {
      title: '分时图',
      dataIndex: 'time_chart',
      key: 'time_chart',
      width: 150,
      render: (text, record) => {
        this.index += 1;
        if (this.index === 10) this.index = 0;
        if (this.index >= this.props.timeList.length) return;
        return (<TableExponentChart
          code={record.code} data={this.props.timeList[this.index]}
        />);
      },
    },
    {
      title: '加自选',
      key: 'operation',
      render: (text, record) => {
        return (
          <span>
            <Button
              type={this.state.selfStockCodeList.indexOf(record.code) !== -1 ? 'danger' : 'primary'}
              onClick={this.state.selfStockCodeList.indexOf(record.code) !== -1 ? () =>
                this.removeSelfStock(record.code) : () => this.addSelfStock(record.code)}
              shape="circle"
              icon={this.state.selfStockCodeList.indexOf(record.code) !== -1 ? 'delete' : 'plus'}
            />
          </span>
        );
      },
    },
  ];

  addSelfStock = (baseCode) => {
    this.props.dispatch({
      type: 'users/addSelfStock',
      payload: {
        code: baseCode,
        pathname: '/stockmarket',
      },
    });
    this.setState({
      selfStockCodeList: [...this.state.selfStockCodeList, baseCode],
    });
  };
  removeSelfStock = (baseCode) => {
    this.props.dispatch({
      type: 'users/deleteSelfStock',
      payload: {
        code: baseCode,
        pathname: '/stockmarket',
      },
    });
    const newList = this.state.selfStockCodeList;
    newList.splice(newList.indexOf(baseCode), 1);
    this.setState({
      selfStockCodeList: newList,
    });
  };
  tableChangeHandler = (pagination, filters, sorter) => {
    this.index = -1;
    const current = pagination.current;
    if (!this.isOwnEmpty(sorter)) {
      const sortKey = sorter.columnKey;
      const order = sorter.order === 'ascend' ? 'asc' : 'desc';
      // this.props.dispatch({
      //   type: 'stockList/fetch',
      //   payload: {
      //     board: this.board,
      //     page: current,
      //     sort: `${sortKey}`,
      //     order: `${order}`,
      //   },
      // });
      this.props.dispatch({
        type: 'candleChart/fetchTableTimeList',
        payload: {
          board: this.props.board,
          page: current,
          sort: `${sortKey},${order}`,
          // sort: `${sortKey}`,
          // order: `${order}`,
        },
      });
    } else {
      this.props.dispatch({
        type: 'candleChart/fetchTableTimeList',
        payload: {
          board: this.props.board,
          page: current,
        },
      });
    }
  };
  isOwnEmpty = (obj) => {
    for (const name in obj) {
      if (Object.prototype.hasOwnProperty.call(obj, name)) {
        return false;
      }
    }
    return true;
  };
  render() {
    this.index = -1;
    return (
      <div>
        <Table
          size="small"
          columns={this.columns}
          dataSource={this.props.dataSource}
          rowKey={record => record.code}
          loading={this.props.loading}
          onChange={this.tableChangeHandler}
        />
      </div>
    );
  }
}

export default connect()(StockTable);
