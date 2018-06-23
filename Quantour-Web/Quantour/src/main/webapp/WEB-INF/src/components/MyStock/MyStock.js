/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import React from 'react';
import { Tabs } from 'antd';
import { connect } from 'dva';
import MyStockTable from './MyStockTable';
import styles from './MyStock.less';

const TabPane = Tabs.TabPane;

class MyStock extends React.Component {
  // state = {
  //   selfStockList: [],
  //   // recentStockList: [],
  // };
  // componentWillReceiveProps(nextProps) {
  //   if (nextProps.selfStockList !== undefined) {
  //     this.setState({
  //       selfStockList: nextProps.selfStockList,
  //       // recentStockList: nextProps.recentStockList,
  //     });
  //   }
  // }
  tabBarStyle = {
    margin: 0,
  };
  render() {
    return (
      <div>
        <Tabs className={styles.stock_table} tabBarStyle={this.tabBarStyle}>
          <TabPane tab="最近访问" key="最近访问"><MyStockTable list={this.props.recentStockList} /></TabPane>
          <TabPane tab="自选股" key="自选股"><MyStockTable list={this.props.selfStockList} /></TabPane>
        </Tabs>
        <Tabs className={styles.stock_table} tabBarStyle={this.tabBarStyle}>
          <TabPane tab="热门股票" key="热门股票"><MyStockTable list={this.props.hotStockList} /></TabPane>
          <TabPane tab="龙虎榜" key="龙虎榜"><MyStockTable list={this.props.longStockList} /></TabPane>
        </Tabs>
      </div>
    );
  }
}
function mapStateToProps(state) {
  const { recentStockList, longStockList, hotStockList } = state.myStockTable;
  const { selfStockList } = state.users;
  return {
    recentStockList, selfStockList, longStockList, hotStockList,
  };
}
export default connect(mapStateToProps)(MyStock);
