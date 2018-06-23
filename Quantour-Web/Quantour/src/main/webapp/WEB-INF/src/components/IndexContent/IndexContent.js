/**
 * Created by wyj on 2017/5/10.
 */
import React from 'react';
import { Layout, Row, Col, Button } from 'antd';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import logo from '../../assets/QAQquant.png';
import stock from '../../assets/stock.png';
import strategy from '../../assets/strategy.png';
import social from '../../assets/social.png';

import styles from './IndexContent.less';

const Content = Layout.Content;

function IndexContent({ dispatch }) {
  function handleStock() {
    dispatch(routerRedux.push({
      pathname: '/stockmarket',
    }));
  }
  function handleStrategy() {
    dispatch(routerRedux.push({
      pathname: '/mystrategy',
    }));
  }
  function handleSocial() {
    dispatch(routerRedux.push({
      pathname: '/community',
    }));
  }
  return (
    <Content className={styles.content}>
      <div className={styles.banner}>
        <Row>
          <Col span={18} offset={3}>
            <img className={styles.logo_img} role="presentation" src={logo} />
            <div className={styles.name_section}>
              {/* <h1>QAQquant</h1>*/}
              <h3>最懂你的股票分析网站</h3>
            </div>
          </Col>
        </Row>
      </div>
      <Row className={styles.intro_stock}>
        <Col span={5} offset={6}>
          <img className={styles.left} role="presentation" src={stock} />
        </Col>
        <Col span={11} offset={2}>
          <div className={styles.right}>
            <div className={styles.title}>股票市场</div>
            <div className={styles.underline} />
            <div className={styles.description}>获取海量股票数据，更有精准的价格预测、舆情分析</div>
            <div className={styles.detail}>
              <p>我们提供高质量的股票历史以及实时数据</p>
              <p>股票排行助你了解市场行情</p>
              <p>舆情分析，基于机器学习的价格预测，帮你做出最优选择</p>
            </div>
            <Button type="primary" className={styles.operation} onClick={handleStock}>查看股票市场</Button>
          </div>
        </Col>
      </Row>
      <Row className={styles.intro_strategy}>
        <Col span={5} offset={6}>
          <div className={styles.left}>
            <div className={styles.title}>策略研究</div>
            <div className={styles.underline} />
            <div className={styles.description}>高效、精准的回测引擎，快速验证策略，支持日级回测</div>
            <div className={styles.detail}>
              <p>免费提供在线 python 编辑器制定策略</p>
              <p>回测结果可视化，策略优劣一目了然</p>
            </div>
            <Button type="primary" className={styles.operation} onClick={handleStrategy}>开始研究</Button>
          </div>
        </Col>
        <Col span={11} offset={2}>
          <img className={styles.right} role="presentation" src={strategy} />
        </Col>
      </Row>
      <Row className={styles.intro_stock}>
        <Col span={5} offset={6}>
          <img className={styles.left} role="presentation" src={social} />
        </Col>
        <Col span={11} offset={2}>
          <div className={styles.right}>
            <div className={styles.title}>社区交流</div>
            <div className={styles.underline} />
            <div className={styles.description}>社区讨论，交流促使共同成长</div>
            <div className={styles.detail}>
              <p>支持对股票的评论交流</p>
              <p>为量化爱好者提供线上交流社区，便于用户交流量化策略、学习量化知识，一起成长</p>
            </div>
            <Button type="primary" className={styles.operation} onClick={handleSocial}>进入社区</Button>
          </div>
        </Col>
      </Row>
    </Content>
  );
}

export default connect()(IndexContent);
