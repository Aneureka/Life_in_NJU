/**
 * Created by Hitigerzzz on 2017/6/6.
 */
import React from 'react';
import styles from './SumBar.less';

function SumBar({ sumData }) {
  return (
    <table className={styles.sum_table}>
      <tbody>
        <tr>
          <th>回测收益</th>
          <th>回测年化收益</th>
          <th>基准收益</th>
          <th>基准年化收益</th>
          <th>Alpha</th>
          <th>Beta</th>
          <th>Sharpe</th>
          <th>Sortino</th>
          <th>Information Ratio</th>
          <th>Volatility</th>
          <th>最大回撤
            {/* <Tooltip title="2016-08-15 至 2016-09-26">*/}
            {/* <Icon type="question-circle-o" />*/}
            {/* </Tooltip>*/}
          </th>
          <th>Tracking Error</th>
          <th>Downside Risk</th>
        </tr>
        <tr>
          <td>{sumData.sProfit}</td>
          <td>{sumData.sYearProfit}</td>
          <td>{sumData.bProfit}</td>
          <td>{sumData.bYearProfit}</td>
          <td>{sumData.alpha}</td>
          <td>{sumData.beta}</td>
          <td>{sumData.sharpe}</td>
          <td>{sumData.sortino}</td>
          <td>{sumData.informationRadio}</td>
          <td>{sumData.volatility}</td>
          <td>{sumData.back}</td>
          <td>{sumData.trackingError}</td>
          <td>{sumData.downsideRisk}</td>
        </tr>
      </tbody>
    </table>
  );
}

export default SumBar;
