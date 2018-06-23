/**
 * Created by wyj on 2017/6/10.
 * description:
 */
import React from 'react';
import { Row, Col, Layout } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
import Forget from '../../components/Forget/Forget';
import styles from './ForgetPage.less';
import logo from '../../assets/QAQquant.png';

const Content = Layout.Content;

function ForgetPage({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <Row>
          <Col span={6} offset={9}>
            <div className={styles.forget}>
              <div className={styles.header}>
                <img className={styles.logo_img} role="presentation" src={logo} />
                <div className={styles.hello}>Hello, quantours :)</div>
              </div>
              <Forget />
            </div>
          </Col>
        </Row>
      </Content>
    </Layout>
  );
}

export default ForgetPage;
