/**
 * Created by wyj on 2017/5/31.
 * description:
 */
import React from 'react';
import { Layout } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
import BottomBar from '../../components/BottomBar/BottomBar';
import CodeEditor from '../../components/CodeEditor/CodeEditor';


function StrategyPage({ location }) {
  return (
    <Layout>
      <Navigation location={location} />
      <CodeEditor />
      <BottomBar />
    </Layout>
  );
}


export default StrategyPage;
