/**
 * Created by wyj on 2017/5/24.
 * description:
 */
import React from 'react';
import { Layout, Row, Col } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
// import BottomBar from '../../components/BottomBar/BottomBar';
import IndividualPageContent from '../../components/IndividualPage/IndividualPageContent';
import styles from './IndividualPage.less';


function IndividualPage() {
  return (
    <Layout>
      <Navigation location={location} />
      <Layout>
        <Row className={styles.section_pos}>
          <Col span={14} offset={5}>
            <IndividualPageContent />
          </Col>
        </Row>
      </Layout>
      {/* <BottomBar />*/}
    </Layout>
  );
}

export default IndividualPage;
