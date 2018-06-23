/**
 * Created by Hitigerzzz on 2017/5/16.
 */
import React from 'react';
import { connect } from 'dva';
import { Tabs } from 'antd';
import News from './News';
import Notice from './Notice';
import DisqusThread from './DisqusThread.js';

const TabPane = Tabs.TabPane;
function StockNews({ code, newsList, notices,
                     newsTotal, newsCurrent, noticesTotal, noticesCurrent, remote_auth_s3: auth }) {
  return (
    <div>
      <Tabs>
        <TabPane tab="新闻" key="1"><News code={code} newsList={newsList} total={newsTotal} current={newsCurrent} /></TabPane>
        <TabPane tab="公告" key="2"><Notice code={code} notices={notices} total={noticesTotal} current={noticesCurrent} /></TabPane>
        <TabPane tab="讨论" key="3">
          <DisqusThread
            id="e94d73ff-fd92-467d-b643-c86889f4b8be"
            title="How to integrate Disqus into ReactJS App"
            path="/stockinfo"
            remote_auth_s3={auth}
          />
        </TabPane>
      </Tabs>
    </div>
  );
}

function mapStateToProps(state) {
  const { code, newsList, notices,
    newsTotal, newsCurrent, noticesTotal, noticesCurrent, remote_auth_s3 } =
    state.stockBasic;
  return { code,
    newsList,
    notices,
    newsTotal,
    newsCurrent,
    noticesTotal,
    noticesCurrent,
    remote_auth_s3 };
}

export default connect(mapStateToProps)(StockNews);
