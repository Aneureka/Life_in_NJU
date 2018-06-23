import React from 'react';
import { connect } from 'dva';
import { Layout } from 'antd';
import Navigation from '../components/Navigation/Navigation';
import BottomBar from '../components/BottomBar/BottomBar';
import IndexContent from '../components/IndexContent/IndexContent';

function IndexPage({ location, dispatch }) {
  // // 获得大盘数据
  // dispatch({
  //   type: 'stockList/fetchExponent',
  // });
  // // 获取股票列表数据
  // dispatch({
  //   type: 'stockList/fetch',
  // });
  // // 获取股票列表分时图
  // dispatch({
  //   type: 'candleChart/fetchTableTimeList',
  //   payload: { board: 'a', page: 1, sort: 'changePercent,desc' },
  // });
  return (
    <Layout>
      <Navigation location={location} />
      <IndexContent />
      <BottomBar />
    </Layout>
  );
}


export default connect()(IndexPage);
