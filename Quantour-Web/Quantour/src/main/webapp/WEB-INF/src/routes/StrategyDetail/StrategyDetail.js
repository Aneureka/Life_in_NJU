/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { Layout } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
import BottomBar from '../../components/BottomBar/BottomBar';
import StrategyResult from '../../components/StrategyResult/StrategyResult';

const Content = Layout.Content;

function StrategyDetail({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <StrategyResult />
      </Content>
      <BottomBar />
    </Layout>
  );
}

export default StrategyDetail;
