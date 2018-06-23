/**
 * Created by wyj on 2017/6/9.
 * description:
 */
import React from 'react';
import { Row, Col, Layout } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
import Register from '../../components/Register/Register';
import styles from './RegisterPage.less';
import logo from '../../assets/QAQquant.png';

const Content = Layout.Content;

function RegisterPage({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <Row>
          <Col span={6} offset={9}>
            <div className={styles.register}>
              <div className={styles.header}>
                <img className={styles.logo_img} role="presentation" src={logo} />
                <div className={styles.hello}>Hello, quantours :)</div>
              </div>
              <Register />
            </div>
          </Col>
        </Row>
      </Content>
    </Layout>
  );
}


export default RegisterPage;
