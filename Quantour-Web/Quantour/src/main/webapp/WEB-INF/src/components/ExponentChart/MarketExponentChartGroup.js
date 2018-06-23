/**
 * Created by wyj on 2017/5/16.
 * description:
 */
import React from 'react';
import { connect } from 'dva';
import { Row, Col } from 'antd';
import ExponentChart from '../../components/ExponentChart/ExponentChart';
import styles from './MarketExponentChartGroup.less';

function MarketExponentChartGroup({ exponentSHData,
                                    basicSHData,
                                    exponentSZData,
                                    basicSZData,
                                    exponentCYData,
                                    basicCYData }) {
  return (
    <div>
      <Row gutter={16}>
        <Col span={8}>
          <div className={styles.container}>
            <ExponentChart title="上证指数" data={exponentSHData} basicData={basicSHData} />
          </div>
        </Col>
        <Col span={8}>
          <div className={styles.container}>
            <ExponentChart title="深证指数" data={exponentSZData} basicData={basicSZData} />
          </div>
        </Col>
        <Col span={8}>
          <div className={styles.container}>
            <ExponentChart title="创业板指" data={exponentCYData} basicData={basicCYData} />
          </div>
        </Col>
      </Row>
    </div>
  );
}

function mapStateToProps(state) {
  const
    { exponentSHData,
      basicSHData,
      exponentSZData,
      basicSZData,
      exponentCYData,
      basicCYData } = state.stockList;
  return { exponentSHData,
    basicSHData,
    exponentSZData,
    basicSZData,
    exponentCYData,
    basicCYData };
}

export default connect(mapStateToProps)(MarketExponentChartGroup);
