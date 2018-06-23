/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import React from 'react';
import { Tabs, Row, Col } from 'antd';
import { connect } from 'dva';

import TimeChart from './TimeChart';
import CandlestickChart from './CandlestickChart';
import TapeList from '../../components/TapeList/TapeList';
import styles from './KChartTabPane.less';

const TabPane = Tabs.TabPane;

function KChartTabPane({ code, name, timeData, series, isCompare,
                         candle_list: candleList, ma_list: maList, change_list: changeList,
                         candleW_list: candleWList, maW_list: maWList, changeW_list: changeWList,
                         candleM_list: candleMList, maM_list: maMList, changeM_list: changeMList,
                         searchResult,
                       }) {
  const tabBarStyle = {
    margin: 0,
  };
  return (
    <div>
      <Tabs type="card" tabBarStyle={tabBarStyle}>
        <TabPane tab="分时" key="time">
          <Row>
            <Col span={18}>
              <TimeChart
                code={code} name={name} timeData={timeData} series={series}
                searchResult={searchResult} isCompare={isCompare}
              />
            </Col>
            <Col span={5} className={styles.tape_list}>
              <TapeList />
            </Col>
          </Row>
        </TabPane>
        <TabPane tab="日K" key="1day"><CandlestickChart candle_list={candleList} ma_list={maList} change_list={changeList} /></TabPane>
        <TabPane tab="周K" key="1week"><CandlestickChart candle_list={candleWList} ma_list={maWList} change_list={changeWList} /></TabPane>
        <TabPane tab="月K" key="1month"><CandlestickChart candle_list={candleMList} ma_list={maMList} change_list={changeMList} /></TabPane>
      </Tabs>
    </div>
  );
}

function mapStateToProps(state) {
  const { code, timeData, series, isCompare,
    candle_list, ma_list, candleW_list, maW_list, candleM_list,
    maM_list, change_list, changeW_list, changeM_list, searchResult } = state.candleChart;
  const { basicData } = state.stockBasic;
  return {
    code,
    name: basicData.name,
    timeData,
    series,
    isCompare,
    candle_list,
    ma_list,
    candleW_list,
    maW_list,
    candleM_list,
    maM_list,
    change_list,
    changeW_list,
    changeM_list,
    searchResult,
  };
}
export default connect(mapStateToProps)(KChartTabPane);
