/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import { Button, Modal } from 'antd';
import AceEditor from 'react-ace';
import { connect } from 'dva';

import 'brace/mode/python';
import 'brace/snippets/python';
import 'brace/ext/language_tools';
import 'brace/theme/eclipse';
import ace from 'brace';

import styles from './Config.less';

class Config extends React.Component {
  state = {
    modalShow: false,
  };
  onModalShow = (e) => {
    this.setState({
      modalShow: true,
    });
  };
  // download = (e) => {
  //   this.props.dispatch({
  //     type: 'strategyResult/downloadResult',
  //   });
  // };
  handleOk = (e) => {
    this.setState({
      modalShow: false,
    });
  };
  handleCancel = (e) => {
    this.setState({
      modalShow: false,
    });
  };
  render() {
    const data = this.props.data;
    return (
      <div className={styles.container}>
        {/* <div className={styles.config}>*/}
        {/* </div>*/}
        <div className={styles.status}>
          <span>设置：</span>
          <span>从 {data.beginDate} 到 {data.endDate}</span>
          <span>|</span>
          <span>初始资金：¥</span>
          <span>{data.stockStartCash}</span>
          <span>|</span>
          <span>交易频率：</span>
          <span>每天</span>
          {/* <span>状态：</span>*/}
          {/* <span>回测完成，</span>*/}
          {/* <span>用时：</span>*/}
          {/* <span>10秒</span>*/}
        </div>
        <div className={styles.operation}>
          <Button type="primary" onClick={this.onModalShow}>查看代码</Button>
          <Modal
            title="查看代码"
            visible={this.state.modalShow}
            width="800px"
            okText=""
            onOk={this.handleOk}
            onCancel={this.handleCancel}
          >
            <AceEditor
              mode="python"
              theme="eclipse"
              name="pythonEditor"
              width="100%"
              height="560px"
              value={data.code}
              fontsize={14}
              showPrintMargin
              showGutter
              highlightActiveLine
              enableLiveAutocompletion
              enableBasicAutocompletion
              enableSnippets
              showLineNumbers
              tabSize={2}
            />
          </Modal>
          {/* <Button type="primary"
          icon="download" onClick={this.download.bind(this)}>导出</Button>*/}
        </div>
      </div>
    );
  }
}

export default connect()(Config);
