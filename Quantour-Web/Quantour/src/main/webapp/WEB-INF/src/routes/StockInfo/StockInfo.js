/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import React from 'react';
import { Row, Col, Layout, BackTop } from 'antd';
import { connect } from 'dva';
import Navigation from '../../components/Navigation/Navigation';
import BottomBar from '../../components/BottomBar/BottomBar';
import MyStock from '../../components/MyStock/MyStock';
import KChartTabPane from '../../components/CandlestickChart/KChartTabPane';
import StockNews from '../../components/StockNews/StockNews';
import StockBasic from '../../components/StockBasic/StockBasic';
import CompanyInfo from '../../components/CompanyInfo/CompanyInfo';
import Forecast from '../../components/Forecast/Forecast';
import styles from './StockInfo.less';

const Content = Layout.Content;

function StockInfo({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <div className={styles.stock_info}>
          <Row>
            <Col span={18} offset={3}>
              <Row gutter={48}>
                <Col span={6} className={styles.stock_list}>
                  <MyStock />
                  <CompanyInfo />
                </Col>
                <Col span={18}>
                  <div className={styles.container}>
                    <Row>
                      <Col span={24} className={styles.stock_basic}>
                        <StockBasic />
                      </Col>
                    </Row>
                    <KChartTabPane />
                  </div>
                  <Forecast />
                  <div className={styles.stock_news}>
                    <StockNews />
                  </div>
                </Col>
              </Row>
            </Col>
          </Row>
        </div>
        <BackTop />
      </Content>
      <BottomBar />
    </Layout>
  );
}

StockInfo.propTypes = {
};

export default connect()(StockInfo);
