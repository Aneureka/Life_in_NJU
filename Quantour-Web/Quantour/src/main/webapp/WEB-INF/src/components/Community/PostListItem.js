/**
 * Created by wyj on 2017/6/10.
 * description:
 */
import React from 'react';
import { Row, Col } from 'antd';
import { connect } from 'dva';
import { Link, routerRedux } from 'dva/router';
import styles from './PostListItem.less';

class PostListItem extends React.Component {
  removeHTMLTag = (value) => {
    let text = value;
    text = text.replace(/<\/?[^>]*>/g, '');
    text = text.replace(/[ | ]*\n/g, '\n');
    text = text.replace(/\n[\s| | ]*\r/g, '\n');
    return text;
  };
  render() {
    return (
      <div className={styles.post_item}>
        <Row>
          <Col span={18} offset={3}>
            <Link to={`/post?aid=${this.props.article.aid}`}>
              <span className={styles.post_title}>{this.props.article.title}</span>
            </Link>
            <p>{ (this.removeHTMLTag(this.props.article.content)).slice(0, 100) }</p>
            <div>
              <div className={styles.author}>
                <span>{this.props.article.username}</span>
                发表于
                <span>2017-05-23</span>
              </div>
              <div className={styles.other_info}>
                <span>浏览{this.props.article.readNum}</span>
              </div>
            </div>
          </Col>
        </Row>
      </div>
    );
  }
}

export default connect()(PostListItem);
