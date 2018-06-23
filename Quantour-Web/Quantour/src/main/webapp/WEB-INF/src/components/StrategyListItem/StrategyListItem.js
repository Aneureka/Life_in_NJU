/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import { Row, Col, Button, Icon, Popconfirm, message } from 'antd';
import { connect } from 'dva';
import { Link } from 'dva/router';
import styles from './StrategyListItem.less';

class StrategyListItem extends React.Component {
  removeStrategy = (id) => {
    this.props.dispatch({
      type: 'strategyResearch/removeStrategy',
      payload: id,
    });
    setTimeout(this.updateProps, 500);
    // message.success('删除成功');
  };
  updateProps = () => {
    this.props.dispatch({
      type: 'strategyResearch/fetchStrategyByUsername',
    });
  };
  render() {
    const myStrategy = this.props.myStrategy;
    const history = myStrategy.history.data;
    return (
      <div className={styles.list_item}>
        <Row>
          <Col span={12} offset={3}>
            <Link to={`/strategy?algoId=${myStrategy.algoId}`} className={styles.strategy_name}>{myStrategy.algoName}</Link>
            <div className={styles.item_result_info}>
              <ul>
                <li>
                  <div>
                    <div>回测收益</div>
                    <div>{history ? history.totalReturns : '--'}</div>
                  </div>
                </li>
                <li>
                  <div>
                    <div>回测年化收益</div>
                    <div>{history ? history.annualizedReturns : '--'}</div>
                  </div>
                </li>
                <li>
                  <div>
                    <div>基准收益</div>
                    <div>{history ? history.benchmarkTotalReturns : '--'}</div>
                  </div>
                </li>
                <li>
                  <div>
                    <div>Alpha</div>
                    <div>{history ? history.alpha : '--'}</div>
                  </div>
                </li>
                <li>
                  <div>
                    <div>Sharpe</div>
                    <div>{history ? history.sharpe : '--'}</div>
                  </div>
                </li>
                <li>
                  <div>
                    <div>最大回撤</div>
                    <div>{history ? history.maxDrawdown : '--'}</div>
                  </div>
                </li>
              </ul>
            </div>
          </Col>
          <Col span={4}>
            <div className={styles.time_info}>
              <div>修改时间：{myStrategy.time}</div>
              <div>回测历史：{myStrategy.hisNum}</div>
            </div>
          </Col>
          <Col span={2}>
            <div className={styles.operate_btn_group}>
              <Popconfirm title="确认删除？" onConfirm={() => this.removeStrategy(myStrategy.algoId)}>
                <Button
                  className={styles.delete_btn}
                >
                  <span><Icon type="delete" /></span>
                </Button>
              </Popconfirm>
            </div>
          </Col>
        </Row>
      </div>
    );
  }
}

export default connect()(StrategyListItem);

