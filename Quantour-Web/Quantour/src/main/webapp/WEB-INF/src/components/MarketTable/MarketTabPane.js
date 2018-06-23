/**
  * Created by Hitigerzzz on 2017/5/10.
  * Description:
  */
import React from 'react';
import { Tabs } from 'antd';
import { connect } from 'dva';
import StockTable from './StockTable';
import styles from './MarketTable.less';

const TabPane = Tabs.TabPane;

function MarketTabPane({ dispatch, data, loading, timeList, selfStockList, selfStockCodeList }) {
  const tabBarStyle = {
    margin: 0,
  };
  function tabChangeHandler(key) {
    dispatch({
      type: 'candleChart/fetchTableTimeList',
      payload: {
        board: key,
        page: 1,
        sort: 'changePercent,desc',
      },
    });
  }
  return (
    <div className={styles.back}>
      <Tabs tabBarStyle={tabBarStyle} onChange={tabChangeHandler}>
        <TabPane tab="全部股票" key="a"><StockTable dataSource={data.aList} timeList={timeList} selfStockList={selfStockList} selfStockCodeList={selfStockCodeList} loading={loading} board="a" /></TabPane>
        <TabPane tab="上证A股" key="sha"><StockTable dataSource={data.shaList} timeList={timeList} selfStockList={selfStockList} selfStockCodeList={selfStockCodeList} loading={loading} board="sha" /></TabPane>
        <TabPane tab="深证A股" key="sza"><StockTable dataSource={data.szaList} timeList={timeList} selfStockList={selfStockList} selfStockCodeList={selfStockCodeList} loading={loading} board="sza" /></TabPane>
        <TabPane tab="中小板" key="zxb"><StockTable dataSource={data.zxbList} timeList={timeList} selfStockList={selfStockList} selfStockCodeList={selfStockCodeList} loading={loading} board="zxb" /></TabPane>
        <TabPane tab="创业板" key="cyb"><StockTable dataSource={data.cybList} timeList={timeList} selfStockList={selfStockList} selfStockCodeList={selfStockCodeList} loading={loading} board="cyb" /></TabPane>
      </Tabs>
    </div>
  );
}

function mapStateToProps(state) {
  const data = state.stockList;
  const { timeList } = state.candleChart;
  const { selfStockList, selfStockCodeList } = state.users;
  return {
    loading: state.loading.effects['stockList/fetch'],
    data,
    timeList,
    selfStockList,
    selfStockCodeList,
  };
}

export default connect(mapStateToProps)(MarketTabPane);

