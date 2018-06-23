/**
 * Created by Hitigerzzz on 2017/5/23.
 */
import React from 'react';
import { Row, Col, Layout } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
import Login from '../../components/Login/Login';
import styles from './LoginPage.less';
import logo from '../../assets/QAQquant.png';

const Content = Layout.Content;

function LoginPage({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <Row>
          <Col span={6} offset={9}>
            <div className={styles.login}>
              <div className={styles.header}>
                <img className={styles.logo_img} role="presentation" src={logo} />
                <div className={styles.hello}>Hello, quantours :)</div>
              </div>
              <Login />
            </div>
          </Col>
        </Row>
      </Content>
    </Layout>
  );
}


export default LoginPage;
