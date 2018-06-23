/**
 * Created by wyj on 2017/6/10.
 * description:
 */
import React from 'react';
import { Row, Col, Layout, BackTop } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
// import BottomBar from '../../components/BottomBar/BottomBar';
import CommunityPane from '../../components/Community/CommunityPane';

const Content = Layout.Content;

function CommunityPage({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <Row>
          <Col span={20} offset={2}>
            <CommunityPane />
          </Col>
        </Row>
      </Content>
      <BackTop />
    </Layout>
  );
}

export default CommunityPage;
