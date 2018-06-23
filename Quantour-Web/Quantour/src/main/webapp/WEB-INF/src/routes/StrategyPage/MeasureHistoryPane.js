/**
 * Created by wyj on 2017/6/7.
 * description:
 */
import React from 'react';
import { Layout, Row, Col, Radio } from 'antd';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import Navigation from '../../components/Navigation/Navigation';
import BottomBar from '../../components/BottomBar/BottomBar';
import MeasureHistoryTable from '../../components/MeasureHistoryTable/MeasureHistoryTable';
import styles from './MeasureHistoryPane.less';

const Content = Layout.Content;

function MeasureHistoryPane({ location, backTestHistory, algoId: id, dispatch }) {
  function handlePageChange(e) {
    if (e.target.value === 'result') {
      dispatch(routerRedux.push({
        pathname: '/strategydetail',
        query: {
          algoId: id.algoId,
        },
      }));
    } else if (e.target.value === 'edit') {
      dispatch(routerRedux.push({
        pathname: '/strategy',
        query: {
          algoId: id.algoId,
        },
      }));
    } else if (e.target.value === 'history') {
      dispatch(routerRedux.push({
        pathname: '/strategyhistory',
        query: {
          algoId: id.algoId,
        },
      }));
    }
  }
  return (
    <Layout>
      <Navigation location={location} />
      <Content>
        <div>
          <Row>
            <div className={styles.stage_change_btn}>
              <div className={styles.stage_btn_pos}>
                <Radio.Group value="history" onChange={handlePageChange.bind(this)}>
                  <Radio.Button value="edit">编辑策略</Radio.Button>
                  <Radio.Button value="result">回测结果</Radio.Button>
                  <Radio.Button value="history">回测历史</Radio.Button>
                </Radio.Group>
              </div>
            </div>
            <Col span={16} offset={4}>
              <div className={styles.measure_history}>
                <MeasureHistoryTable backTestHistory={backTestHistory.data} />
              </div>
            </Col>
          </Row>
        </div>
      </Content>
      <BottomBar />
    </Layout>
  );
}

function mapStateToProps(state) {
  const { backTestHistory, algoId } = state.strategyResearch;
  return {
    backTestHistory,
    algoId,
  };
}

export default connect(mapStateToProps)(MeasureHistoryPane);
