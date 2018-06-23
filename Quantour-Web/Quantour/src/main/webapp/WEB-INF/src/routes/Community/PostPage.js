/**
 * Created by wyj on 2017/6/12.
 * description:
 */
import React from 'react';
import { Row, Col, Layout, Button } from 'antd';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import Navigation from '../../components/Navigation/Navigation';
import styles from './PostPage.less';

const Content = Layout.Content;

class PostPage extends React.Component {
  state = {
    article: [],
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.article !== undefined) {
      this.setState({
        article: nextProps.article.data,
      });
    }
  }
  backToList = (e) => {
    this.props.dispatch(routerRedux.push('/community'));
  };
  render() {
    return (
      <Layout>
        <Navigation location={this.props.location} />
        <Content>
          <Row>
            <Col span={16} offset={4}>
              <div className={styles.article_section}>
                <div className={styles.article_info}>
                  <h3>{this.state.article.title}</h3>
                  <span className={styles.author}>{this.state.article.username}</span>
                  <span className={styles.date}>{this.state.article.time}</span>
                </div>
                <Button type="primary" onClick={this.backToList} className={styles.return_btn}>返回列表</Button>
                {/* <div>{this.test(this.state.article.content)}</div>*/}
                {/* <div />*/}
                <ReactQuill
                  className={styles.contentSection}
                  theme="snow"
                  readOnly
                  value={this.state.article.content}
                />
              </div>
            </Col>
          </Row>
        </Content>
      </Layout>
    );
  }
}

function mapStateToProps(state) {
  const { article } = state.article;
  return { article };
}

export default connect(mapStateToProps)(PostPage);
