/**
 * Created by Hitigerzzz on 2017/6/4.
 */
import React from 'react';
import { connect } from 'dva';
import { Tooltip, Icon, Row, Col, Spin } from 'antd';
import styles from './Forecast.less';
import ClosePrice from './ClosePrice';
import NewsSentiment from './NewsSentiment';
import NewsGauge from './NewsGauge';

function Forecast({ retCode, expectedPrice, actualPrice, markPosition }) {
  return (
    <div className={styles.container}>
      <Row>
        <div>
          <div className={styles.title_block} />
          <div className={styles.title}>未来1个交易日收盘价预测</div>
          <Tooltip title="获取十余年的个股信息作为数据集，进行基于BP神经网络的模型训练，最终预测下一个价格。">
            <Icon type="question-circle-o" />
          </Tooltip>
        </div>
        { retCode === 1 ? <ClosePrice
          expectedPrice={expectedPrice} actualPrice={actualPrice} markPosition={markPosition}
        /> : (retCode === -1 ? <div className={styles.tooltip}><Spin className={styles.spin} size="large" /><div style={{ color: '#3db8c1' }}>正在帮您计算，请稍等...</div></div> :
        <div className={styles.not_enough}><Icon type="frown-o" /> 抱歉，该股票数据量不足，不适合预测</div>)}
        <div>
          <div className={styles.header}>
            <div className={styles.title_block} />
            <div className={styles.title}>新闻情感指数分析</div>
            <Tooltip title="相关度>0.2319表示关联显著">
              <Icon type="question-circle-o" />
            </Tooltip>
          </div>
        </div>
      </Row>
      <Row>
        <Col span={16}>
          <NewsSentiment />
        </Col>
        <Col span={8}>
          <NewsGauge />
        </Col>
      </Row>
    </div>
  );
}

function mapStateToProps(state) {
  const { retCode, expectedPrice, actualPrice, markPosition } = state.forecast;
  return { retCode, expectedPrice, actualPrice, markPosition };
}

export default connect(mapStateToProps)(Forecast);
