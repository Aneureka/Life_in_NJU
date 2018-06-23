/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import React from 'react';
import { connect } from 'dva';
import { Button, message } from 'antd';
import styles from './StockBasic.less';


class StockBasic extends React.Component {
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
  handleAddSelf = () => {
    const baseCode = this.props.basicData.symbol;
    this.props.dispatch({
      type: 'users/addBasicSelfStock',
      payload: {
        code: baseCode,
        // pathname: `/stockinfo?code=${baseCode}`,
      },
    });
    this.setState({
      selfStockCodeList: [...this.state.selfStockCodeList, this.props.basicData.symbol],
    });
    this.props.dispatch({
      type: 'users/fetchSelfStock',
    });
    this.props.dispatch({
      type: 'users/fetchSelfStockCode',
    });
  };
  handleDelSelf = () => {
    this.props.dispatch({
      type: 'users/deleteBasicSelfStock',
      payload: this.props.basicData.symbol,
    });
    const newList = this.state.selfStockCodeList;
    newList.splice(newList.indexOf(this.props.basicData.symbol), 1);
    this.setState({
      selfStockCodeList: newList,
    });
    this.props.dispatch({
      type: 'users/fetchSelfStock',
    });
    this.props.dispatch({
      type: 'users/fetchSelfStockCode',
    });
  };
  handleBiggerE = (num) => {
    return num > 100000000 ? `${(num / 100000000).toFixed(2)}亿` : num;
  };

  render() {
    const basicData = this.props.basicData;
    const isUp = basicData.change > 0 ? styles.stock_change : styles.stock_change_down;
    const volume = Number(basicData.volume);
    const volumeStr = volume > 10000 ? `${(volume / 10000).toFixed(2)}万股` : `${volume}股`;
    const amount = Number(basicData.amount);
    const amountStr = this.handleBiggerE(amount);
    const marketCapital = Number(basicData.marketCapital);
    const marketCapitalStr = this.handleBiggerE(marketCapital);
    const floatShares = Number(basicData.float_shares);
    const floatSharesStr = this.handleBiggerE(floatShares);
    const totalShares = Number(basicData.totalShares);
    const totalSharesStr = this.handleBiggerE(totalShares);
    return (
      <div className={styles.container}>
        <div>
          <span className={styles.stock_name}>{basicData.name}</span>
          <span className={styles.stock_code}>{basicData.symbol}</span>
          <Button
            type={this.state.selfStockCodeList.indexOf(basicData.symbol) !== -1 ? 'danger' : 'primary'}
            icon={this.state.selfStockCodeList.indexOf(basicData.symbol) !== -1 ? 'delete' : 'plus'}
            className={styles.stock_btn}
            onClick={this.state.selfStockCodeList.indexOf(basicData.symbol) !== -1 ? () =>
              this.handleDelSelf() : () => this.handleAddSelf()}
          >
            {this.state.selfStockCodeList.indexOf(basicData.symbol) !== -1 ? '移除自选' : '加入自选'}
          </Button>
        </div>
        <div>
          <span className={`${styles.stock_present} ${isUp}`}>{basicData.current}</span>
          <span className={isUp}>
            {/* <Icon type="arrow-up" />*/}
            <span>{basicData.change} </span>
            <span>{basicData.percentage}%</span>
          </span>
          <span className={styles.stock_time}>{basicData.time}</span>
        </div>
        <table className={styles.data_table}>
          <tbody>
            <tr>
              <td>今开</td>
              <td>最高</td>
              <td>52周最高</td>
              <td>成交量</td>
              <td>涨停价</td>
              <td>总市值</td>
              <td>振幅</td>
              <td>每股收益</td>
              <td>每股净资产</td>
              <td>每股股息</td>
            </tr>
            <tr>
              <th>{basicData.open}</th>
              <th>{basicData.high}</th>
              <th>{basicData.high52week}</th>
              <th>{volumeStr}</th>
              <th>{basicData.rise_stop}</th>
              <th>{marketCapitalStr}</th>
              <th>{basicData.amplitude}</th>
              <th>{basicData.eps}</th>
              <th>{basicData.net_assets}</th>
              <th>{basicData.dividend}</th>
            </tr>
            <tr>
              <td>昨收</td>
              <td>最低</td>
              <td>52周最低</td>
              <td>成交额</td>
              <td>跌停价</td>
              <td>总股本</td>
              <td>流通股本</td>
              <td>市盈率(静)/(动)</td>
              <td>市净率(动)</td>
              <td>市销率(动)</td>
            </tr>
            <tr>
              <th>{basicData.last_close}</th>
              <th>{basicData.low}</th>
              <th>{basicData.low52week}</th>
              <th>{amountStr}</th>
              <th>{basicData.fall_stop}</th>
              <th>{totalSharesStr}</th>
              <th>{floatSharesStr}</th>
              <th>{basicData.pe_lyr}/{basicData.pe_ttm}</th>
              <th>{basicData.pb}</th>
              <th>{basicData.psr}</th>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { basicData } = state.stockBasic;
  const { selfStockList, selfStockCodeList } = state.users;
  return {
    basicData,
    selfStockList,
    selfStockCodeList,
  };
}
export default connect(mapStateToProps)(StockBasic);
