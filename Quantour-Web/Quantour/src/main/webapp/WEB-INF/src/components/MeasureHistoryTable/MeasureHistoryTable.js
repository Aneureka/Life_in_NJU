/**
 * Created by wyj on 2017/6/5.
 * description:
 */
import React from 'react';
import { connect } from 'dva';
import { Table, Button, Modal } from 'antd';
import AceEditor from 'react-ace';

import 'brace/mode/python';
import 'brace/snippets/python';
import 'brace/ext/language_tools';
import 'brace/theme/eclipse';
import ace from 'brace';

class MeasureHistoryTable extends React.Component {
  state = {
    modalShow: false,
    code: '',
  };
  onModalShow = (code) => {
    this.setState({
      modalShow: true,
      code,
    });
  };
  handleOk = (e) => {
    this.setState({
      modalShow: false,
      code: '',
    });
  };
  handleCancel = (e) => {
    this.setState({
      modalShow: false,
      code: '',
    });
  };
  columns = [{
    title: '回测时间',
    dataIndex: 'time',
    key: 'time',
  }, {
    title: '开始日期',
    dataIndex: 'beginDate',
    key: 'beginDate',
  }, {
    title: '结束日期',
    dataIndex: 'endDate',
    key: 'endDate',
  }, {
    title: '起始资金',
    dataIndex: 'stockStartCash',
    key: 'stockStartCash',
  }, {
    title: '收益',
    dataIndex: 'totalReturns',
    key: 'totalReturns',
  }, {
    title: 'Alpha',
    dataIndex: 'alpha',
    key: 'alpha',
  }, {
    title: 'Sharpe',
    dataIndex: 'sharpe',
    key: 'sharpe',
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    render: text => (
      <span>{text ? '回测已完成' : '回测失败'}</span>
    ),
  }, {
    title: '代码',
    key: 'code',
    render: (text, record) => (
      <div>
        <Button onClick={() => this.onModalShow(record.code)}>查看</Button>
      </div>
    ),
  }];
  render() {
    return (
      <div>
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
            value={this.state.code}
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
        <Table
          columns={this.columns}
          rowKey={record => record.id}
          dataSource={this.props.backTestHistory}
        />
      </div>
    );
  }
}

export default connect()(MeasureHistoryTable);
