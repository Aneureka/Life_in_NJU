/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import { Tabs, Row, Col } from 'antd';
import styles from './MyPostPane.less';

const TabPane = Tabs.TabPane;

class MyPostsPane extends React.Component {
  render() {
    return (
      <div className={styles.pane_back} style={{ display: this.props.display }}>
        <Row>
          <Col span={22} offset={1}>
            <Tabs className={styles.tabs_pos} defaultActiveKey="1">
              <TabPane tab="发布的帖子" key="1">发布的帖子</TabPane>
              <TabPane tab="收藏的帖子" key="2">收藏的帖子</TabPane>
              <TabPane tab="回复的帖子" key="3">回复的帖子</TabPane>
            </Tabs>
          </Col>
        </Row>
      </div>
    );
  }
}

export default MyPostsPane;

