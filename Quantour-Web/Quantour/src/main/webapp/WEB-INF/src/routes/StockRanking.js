/**
 * Created by Hitigerzzz on 2017/5/10.
 */
import React from 'react';
import { connect } from 'dva';
import MarketWave from '../components/MarketWave/MarketWave';

class StockRanking extends React.Component {
  render() {
    return (
      <MarketWave />
    );
  }
}

export default connect()(StockRanking);
