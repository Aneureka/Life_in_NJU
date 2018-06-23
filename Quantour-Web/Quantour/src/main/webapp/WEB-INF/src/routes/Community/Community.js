/**
 * Created by wyj on 2017/6/9.
 * description:
 */
import React from 'react';
import { Row, Col, BackTop } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
// import BottomBar from '../../components/BottomBar/BottomBar';
import PostEditor from '../../components/Community/PostEditor';
import styles from './Community.less';

function Community({ location }) {
  return (
    <div>
      <Navigation location={location} />
      <Row>
        <Col className={styles.editer_section} span={16}>
          <PostEditor />
        </Col>
      </Row>
      <BackTop />
      {/* <BottomBar />*/}
    </div>
  );
}

export default Community;
