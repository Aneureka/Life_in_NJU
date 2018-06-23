/**
 * Created by wyj on 2017/6/10.
 * description:
 */
import React from 'react';
import { Row, Col, Button } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import PostListItem from './PostListItem';
import styles from './CommunityPane.less';

class CommunityPane extends React.Component {
  state = {
    articleList: [],
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.articleList !== undefined) {
      this.setState({
        articleList: nextProps.articleList.data,
      });
    }
  }
  createNewPost = () => {
    const sessionStorage = window.sessionStorage;
    const token = sessionStorage.getItem('userToken');
    if (token && token.length > 0) {
      this.props.dispatch(routerRedux.push({
        pathname: '/newpost',
      }));
    } else {
      this.props.dispatch(routerRedux.push({
        pathname: '/login',
      }));
    }
  };
  render() {
    return (
      <div>
        <Row>
          <Col className={styles.post_section} span={18} offset={3}>
            <Row>
              <Col span={18} offset={3}>
                <Button
                  type="primary"
                  onClick={this.createNewPost.bind(this)}
                  className={styles.new_post_btn}
                >
                  发表主题
                </Button>
              </Col>
            </Row>
            <div>
              {this.state.articleList.map((article) => {
                return <PostListItem key={article.aid} article={article} />;
              })}
            </div>
          </Col>
        </Row>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { articleList } = state.article;
  return { articleList };
}

export default connect(mapStateToProps)(CommunityPane);
