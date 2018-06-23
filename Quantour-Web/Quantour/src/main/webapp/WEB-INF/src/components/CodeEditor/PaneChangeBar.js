/**
 * Created by wyj on 2017/6/7.
 * description:
 */
import React from 'react';
import { Radio } from 'antd';
import { routerRedux } from 'dva/router';
import { connect } from 'dva';
import styles from './CodeEditor.less';

class PaneChangeBar extends React.Component {
  state = {
    value: this.props.current_page,
  };
  handlePaneChange = (e) => {
    const algoId = this.props.algoId;
    if (e.target.value === 'edit') {
      this.setState({
        value: 'edit',
      });
      this.props.dispatch(routerRedux.push({
        pathname: '/strategy',
        query: {
          algoId,
        },
      }));
    } else if (e.target.value === 'history') {
      this.setState({
        value: 'history',
      });
      this.props.dispatch(routerRedux.push({
        pathname: '/strategyhistory',
        query: {
          algoId,
        },
      }));
    } else if (e.target.value === 'result') {
      this.setState({
        value: 'result',
      });
      this.props.dispatch(routerRedux.push({
        pathname: '/strategydetail',
        query: {
          algoId,
        },
      }));
    }
  };
  render() {
    const value = this.state.value;
    return (
      <div className={styles.stage_change_btn}>
        <div className={styles.stage_btn_pos}>
          <Radio.Group value={value} onChange={this.handlePaneChange.bind(this)}>
            <Radio.Button value="edit">编辑策略</Radio.Button>
            <Radio.Button value="result">回测结果</Radio.Button>
            <Radio.Button value="history">回测历史</Radio.Button>
          </Radio.Group>
        </div>
      </div>
    );
  }
}

export default connect()(PaneChangeBar);
