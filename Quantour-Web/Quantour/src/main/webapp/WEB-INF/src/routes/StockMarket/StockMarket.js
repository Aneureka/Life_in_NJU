/**
  * Created by Hitigerzzz on 2017/5/10.
  * Description:
  */
import React from 'react';
import { connect } from 'dva';
import { Row, Col, Layout, BackTop } from 'antd';
import styles from './StockMarket.less';
// import MarketWave from '../../components/MarketWave/MarketWave';
import MarKetExponentChartGroup from '../../components/ExponentChart/MarketExponentChartGroup';
import MarketTabPane from '../../components/MarketTable/MarketTabPane';
import Navigation from '../../components/Navigation/Navigation';
import BottomBar from '../../components/BottomBar/BottomBar';
import MarketNewsList from '../../components/MarketNewsList/MarketNewsList';

const Content = Layout.Content;

function StockMarket({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <div>
          {/*
             <div className={styles.Navigation} >
             <MarketWave />
             </div>
             */}
          <Row className={styles.chart_group}>
            <Col span={18} offset={3}>
              <MarKetExponentChartGroup />
            </Col>
          </Row>
          <Row className={styles.stock_list}>
            <Col span={18} offset={3}>
              <MarketTabPane />
            </Col>
          </Row>
          <Row>
            <Col span={18} offset={3} className={styles.news_container}>
              <div className={styles.market_news}>
                <MarketNewsList />
              </div>
            </Col>
          </Row>
        </div>
        <BackTop />
      </Content>
      <BottomBar />
    </Layout>
  );
}

export default connect()(StockMarket);
