/**
 * Created by Hitigerzzz on 2017/5/10.
 */
import React from 'react';
import { Menu, Icon } from 'antd';
import styles from './MarketWave.less';

class MarketWave extends React.Component {
  state = {
    current: 'mail',
    up: 430,
    down: 2473,
    up_stop: 30,
    down_stop: 31,
    profit: 1.03,
  };
  handleClick = (e) => {
    this.setState({
      current: e.key,
    });
  };
  render() {
    return (
      <Menu
        onClick={this.handleClick}
        selectedKeys={[this.state.current]}
        mode="vertical"
      >
        <Menu.Item key="mail" className={styles.ant_menu_item}>
          <Icon type="bar-chart" />涨跌分布
          <div>
            <span>上涨：</span>{this.state.up}<span>支 </span>
            <span>下跌：</span>{this.state.down}<span>支</span>
          </div>
        </Menu.Item>
        <Menu.Item key="app" className={styles.ant_menu_item}>
          <Icon type="swap-left" />涨跌停
          <div>
            <span>涨停：</span>{this.state.up_stop}<span>支 </span>
            <span>跌停：</span>{this.state.down_stop}<span>支</span>
          </div>
        </Menu.Item>
        <Menu.Item key="alipay" className={styles.ant_menu_item}>
          <Icon type="pay-circle-o" />昨日涨停今日收益
          <div>
            <span>今收益：</span>{this.state.profit}<span>%</span>
          </div>
        </Menu.Item>
      </Menu>
    );
  }
}

export default MarketWave;
