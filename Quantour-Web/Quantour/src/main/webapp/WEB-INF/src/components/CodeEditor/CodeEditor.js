/**
 * Created by wyj on 2017/6/2.
 * description:
 */
import React from 'react';
import AceEditor from 'react-ace';
import moment from 'moment';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import { Row, Col, Button, DatePicker, InputNumber, Select, Tabs, Input, Radio } from 'antd';

import 'brace/mode/python';
import 'brace/snippets/python';
import 'brace/ext/language_tools';
import 'brace/theme/eclipse';
import ace from 'brace';

import IncomeChart from './IncomeChart';
import styles from './CodeEditor.less';
import { INIT_CODE, STRATEGY_API } from '../../components/constants';
import * as TimeFormatter from '../../utils/timeformatter';

const RangePicker = DatePicker.RangePicker;
const Option = Select.Option;
const TabPane = Tabs.TabPane;
const langTools = ace.acequire('ace/ext/language_tools');

// 自定义自动补全
const customCompleter = {
  getCompletions(editor, session, pos, prefix, callback) {
    callback(null, STRATEGY_API);
  },
};
langTools.addCompleter(customCompleter);

class CodeEditor extends React.Component {
  state = {
    code: INIT_CODE,
    editCodeDisplay: 'block',
    measureHistoryDisplay: 'none',
    value: 'edit',
    startDate: '',
    endDate: '',
    stockStartCash: 0,
    benchmark: '',
    loading: false,
    compile: false,
  };

  componentWillReceiveProps(nextProps) {
    if (nextProps.strategyContent !== undefined) {
      this.setState({
        code: nextProps.strategyContent.code,
        startDate: nextProps.strategyContent.beginDate,
        endDate: nextProps.strategyContent.endDate,
        stockStartCash: nextProps.strategyContent.stockStartCash,
        benchmark: nextProps.strategyContent.benchmark,
        loading: false,
        compile: false,
      });
    }
  }
  getStartegy = () => {
    const now = TimeFormatter.timeToDateTimeSecond(Date.now());
    const strategy = {
      code: this.state.code,
      algoId: this.props.strategyContent.algoId,
      algoName: this.props.strategyContent.algoName,
      username: this.props.strategyContent.username,
      time: now,
      beginDate: this.state.startDate,
      endDate: this.state.endDate,
      stockStartCash: this.state.stockStartCash,
      benchmark: this.state.benchmark,
    };
    return strategy;
  };
  updateCode = (newCode) => {
    this.setState({
      code: newCode,
    });
  };
  saveEditedStrategy = () => {
    const strategy = this.getStartegy();
    this.props.dispatch({
      type: 'strategyResearch/updateStrategy',
      payload: strategy,
    });
  };

  handlePaneChange = (e) => {
    if (e.target.value === 'edit') {
      this.setState({
        editCodeDisplay: 'block',
        measureHistoryDisplay: 'none',
        value: 'edit',
      });
    } else if (e.target.value === 'history') {
      this.setState({
        editCodeDisplay: 'none',
        measureHistoryDisplay: 'block',
        value: 'history',
      });
    }
  };
  updateDate = (date, dateString) => {
    this.setState({
      startDate: dateString[0],
      endDate: dateString[1],
    });
  };
  updateStartCash = (value) => {
    this.setState({
      stockStartCash: value,
    });
  };
  updateBenchmark = (e) => {
    this.setState({
      benchmark: e.target.value,
    });
  };
  doBackTest = (id) => {
    this.setState({
      loading: true,
    });
    const data = this.getStartegy();
    this.props.dispatch({
      type: 'strategyResult/fetchResult',
      payload: { algoId: id, strategy: data },
    });
  };
  compileBackTest = (id) => {
    this.setState({
      compile: true,
    });
    const data = this.getStartegy();
    this.props.dispatch({
      type: 'strategyResult/fetchCompileResult',
      payload: { algoId: id, strategy: data },
    });
  };
  handlePageChange = (e) => {
    const id = this.props.algoId;
    if (e.target.value === 'result') {
      if (this.props.runAlgoId === '' || id !== this.props.runAlgoId) {
        // 还没回测过
        this.doBackTest(id);
      } else {
        this.props.dispatch(routerRedux.push({
          pathname: '/strategydetail',
          query: {
            algoId: id,
          },
        }));
      }
    } else if (e.target.value === 'edit') {
      this.props.dispatch(routerRedux.push({
        pathname: '/strategy',
        query: {
          algoId: id,
        },
      }));
    } else if (e.target.value === 'history') {
      this.props.dispatch(routerRedux.push({
        pathname: '/strategyhistory',
        query: {
          algoId: id,
        },
      }));
    }
  };
  render() {
    let haveChartData = (this.props.sumData.length !== 0);
    if (this.props.algoId !== this.props.runAlgoId) haveChartData = false;
    return (
      <div style={{ width: '99.99%' }}>
        <div className={styles.stage_change_btn}>
          <div className={styles.stage_btn_pos}>
            <Radio.Group value="edit" onChange={this.handlePageChange.bind(this)}>
              <Radio.Button value="edit">编辑策略</Radio.Button>
              <Radio.Button value="result">回测结果</Radio.Button>
              <Radio.Button value="history">回测历史</Radio.Button>
            </Radio.Group>
          </div>
        </div>
        <div style={{ display: this.state.editCodeDisplay }}>
          <Row>
            <Col span={12}>
              <div className={styles.code_btngroup}>
                <Button
                  className={styles.code_btngroup_btn}
                  onClick={this.saveEditedStrategy.bind(this)}
                >
                  保存
                </Button>
                <Button
                  className={styles.code_btngroup_btn}
                  type="primary"
                  onClick={() => this.compileBackTest(this.props.strategyContent.algoId)}
                  loading={this.state.compile}
                >编译</Button>
              </div>
              <div>
                <AceEditor
                  mode="python"
                  theme="eclipse"
                  name="pythonEditor"
                  width="100%"
                  height="760px"
                  value={this.state.code}
                  onChange={this.updateCode}
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
              </div>
            </Col>
            <Col span={12}>
              <div>
                <div>
                  <div className={styles.chart_selectorgroup}>
                    <RangePicker
                      value={[moment(this.state.startDate),
                        moment(this.state.endDate)]}
                      onChange={this.updateDate}
                      allowClear={false}
                    />
                    <InputNumber
                      onChange={this.updateStartCash}
                      className={styles.btn_gap}
                      value={this.state.stockStartCash}
                      formatter={value => `￥ ${value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}`}
                      parser={value => value.replace(/￥\s?|(,*)/g, '')}
                    />
                    <span className={styles.standrad_section}>
                      基准合约:
                      <Input
                        value={this.state.benchmark}
                        className={styles.standard_promise}
                        onChange={this.updateBenchmark}
                      />
                    </span>
                    <Button
                      className={styles.btn_gap}
                      type="primary"
                      onClick={() => this.doBackTest(this.props.strategyContent.algoId)}
                      loading={this.state.loading}
                    >
                      运行回测
                    </Button>
                  </div>
                  <div className={styles.chart_section}>
                    <div className={styles.result_info}>
                      <table className={styles.sum_table}>
                        <tbody>
                          <tr>
                            <th>回测收益</th>
                            <th>回测年化收益</th>
                            <th>基准收益</th>
                            <th>基准年化收益</th>
                            <th>Sharpe</th>
                            <th>最大回撤</th>
                          </tr>
                          <tr>
                            <td>{haveChartData ? this.props.sumData.sProfit : '--'}</td>
                            <td>{haveChartData ? this.props.sumData.sYearProfit : '--'}</td>
                            <td>{haveChartData ? this.props.sumData.bProfit : '--'}</td>
                            <td>{haveChartData ? this.props.sumData.bYearProfit : '--'}</td>
                            <td>{haveChartData ? this.props.sumData.sharpe : '--'}</td>
                            <td>{haveChartData ? this.props.sumData.back : '--'}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div className={styles.incomeChart_style}>
                      { haveChartData ?
                        <IncomeChart
                          standardData={this.props.standardData}
                          myData={this.props.myData}
                          date={this.props.date}
                        /> : '请编译你的策略'}
                    </div>
                  </div>
                </div>
              </div>
            </Col>
          </Row>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { algoId, measureHistoryDate, strategyContent } = state.strategyResearch;
  const { runAlgoId, sumData, standardData, myData, date } = state.strategyResult;
  return {
    algoId,
    runAlgoId,
    sumData,
    standardData,
    myData,
    date,
    measureHistoryDate,
    strategyContent,
  };
}

export default connect(mapStateToProps)(CodeEditor);
