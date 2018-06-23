/**
 * Created by wyj on 2017/6/9.
 * description:
 */
import React from 'react';
import { Input, Button, Row, Col } from 'antd';
import ReactQuill from 'react-quill';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import 'react-quill/dist/quill.snow.css';
import styles from './PostEditor.less';
import * as TimeFormatter from '../../utils/timeformatter';

class PostEditor extends React.Component {
  state = {
    title: '',
    content: '',
  };
  onTextChange = (value) => {
    this.setState({
      content: value,
    });
  };
  onTitleChange = (e) => {
    this.setState({
      title: e.target.value,
    });
  };
  handleBack = () => {
    this.props.dispatch(routerRedux.push({
      pathname: '/community',
    }));
  };
  handleCreatePost = () => {
    this.props.dispatch({
      type: 'article/createArticle',
      payload: {
        title: this.state.title,
        content: this.state.content,
        time: TimeFormatter.timeToDateTimeSecond(Date.now()),
      },
    });
  };
  modules = {
    toolbar: [
      [{ font: [] }, { header: [] }],
      [{ align: [] }, 'direction'],
      ['bold', 'italic', 'underline', 'strike'],
      [{ color: [] }, { background: [] }],
      [{ script: 'super' }, { script: 'sub' }],
      ['blockquote', 'code-block'],
      [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
      ['link'],
      ['clean'],
    ],
  };
  render() {
    const { content } = this.state;
    return (
      <Row>
        <Col span={18} offset={3}>
          <div>
            <Input placeholder="标题" onKeyUp={this.onTitleChange} />
          </div>
          <div className={styles.section_back}>
            <ReactQuill
              className={styles.editor_section}
              value={content}
              modules={this.modules}
              theme="snow"
              onChange={this.onTextChange}
            />
          </div>
          <div>
            <Button
              type="primary"
              className={styles.submit_btn}
              onClick={this.handleCreatePost}
            >
              提交
            </Button>
            <Button
              className={styles.submit_btn}
              onClick={this.handleBack}
            >
              取消
            </Button>
          </div>
        </Col>
      </Row>
    );
  }
}

export default connect()(PostEditor);
