/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import { Tabs, Radio } from 'antd';
import Config from './Config';
import Summary from './Summary';
import Transfer from './Transfer';
import Position from './Position';
import AccountTable from './AccountTable';
import RadioTable from './RadioTable';
import CompareTable from './CompareTable';
import styles from './StrategyResult.less';

const TabPane = Tabs.TabPane;

function StrategyResult({ data, strategyContent, dispatch }) {
  const tabBarStyle = {
    margin: 0,
    backgroundColor: '#eee',
    textAlign: 'left',
  };
  function handlePageChange(e) {
    if (e.target.value === 'result') {
      dispatch(routerRedux.push({
        pathname: '/strategydetail',
        query: {
          algoId: data.runAlgoId,
        },
      }));
    } else if (e.target.value === 'edit') {
      dispatch(routerRedux.push({
        pathname: '/strategy',
        query: {
          algoId: data.runAlgoId,
        },
      }));
    } else if (e.target.value === 'history') {
      dispatch(routerRedux.push({
        pathname: '/strategyhistory',
        query: {
          algoId: data.runAlgoId,
        },
      }));
    }
  }
  return (
    <div className={styles.container}>
      <div className={styles.stage_change_btn}>
        <div className={styles.stage_btn_pos}>
          <Radio.Group value="result" onChange={(e) => { handlePageChange(e); }}>
            <Radio.Button value="edit">编辑策略</Radio.Button>
            <Radio.Button value="result">回测结果</Radio.Button>
            <Radio.Button value="history">回测历史</Radio.Button>
          </Radio.Group>
        </div>
      </div>
      <Config data={strategyContent} />
      <div className={styles.content}>
        <Tabs tabBarStyle={tabBarStyle} tabPosition={'left'}>
          <TabPane tab="策略概述" key="1">
            <Summary
              sumData={data.sumData}
              standardData={data.standardData}
              myData={data.myData}
              date={data.date}
              profit={data.profit}
              buy={data.buy}
              sell={data.sell}
            />
          </TabPane>
          <TabPane tab="调仓记录" key="2"><Transfer data={data.transfer} head={data.transferHead} /></TabPane>
          <TabPane tab="持仓记录" key="3"><Position data={data.position} head={data.positionHead} /></TabPane>
          <TabPane tab="账户信息" key="11"><AccountTable data={data.stockAccount} /></TabPane>
          <TabPane tab="收益率" key="4"><CompareTable data={data.profitRadio} /></TabPane>
          <TabPane tab="收益波动率" key="8"><CompareTable data={data.volatility} /></TabPane>
          <TabPane tab="阿尔法" key="5"><RadioTable data={data.alpha} /></TabPane>
          <TabPane tab="贝塔" key="6"><RadioTable data={data.beta} /></TabPane>
          <TabPane tab="夏普比率" key="7"><RadioTable data={data.sharpe} /></TabPane>
          <TabPane tab="信息比率" key="9"><RadioTable data={data.informationRadio} /></TabPane>
          <TabPane tab="最大回撤" key="10"><RadioTable data={data.back} /></TabPane>
        </Tabs>
      </div>
    </div>
  );
}

function mapStateToProps(state) {
  const data = state.strategyResult;
  const { strategyContent } = state.strategyResearch;
  return { data, strategyContent };
}

export default connect(mapStateToProps)(StrategyResult);
