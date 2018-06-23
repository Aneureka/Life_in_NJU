/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import { connect } from 'dva';
import { Row, Col, Button, Layout, Modal, Input } from 'antd';
import Navigation from '../../components/Navigation/Navigation';
import BottomBar from '../../components/BottomBar/BottomBar';
import StrategyListItem from '../../components/StrategyListItem/StrategyListItem';
import styles from './MyStrategy.less';
import emptyPic from '../../assets/empty.png';

const Content = Layout.Content;

class MyStrategy extends React.Component {
  state = {
    visible: false,
    inputValue: '',
    strategyList: [],
    emptyPicShow: false,
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.strategyList !== undefined) {
      this.setState({
        strategyList: nextProps.strategyList,
      });
    }
  }
  showModal = () => {
    this.setState({
      visible: true,
    });
  };
  handleOk = () => {
    this.props.dispatch({
      type: 'strategyResearch/createStrategy',
      payload: this.state.inputValue,
    });
    this.setState({
      visible: false,
    });
    setTimeout(this.updateProps, 500);
  };
  updateProps = () => {
    this.props.dispatch({
      type: 'strategyResearch/fetchStrategyByUsername',
    });
  };
  handleCancel = () => {
    this.setState({
      visible: false,
    });
  };
  handleKeyPress = (e) => {
    this.setState({
      inputValue: e.target.value,
    });
  };
  render() {
    const { visible } = this.state;
    return (
      <Layout>
        <Navigation location={location} />
        <Content>
          <Row className={styles.body_pos}>
            <Col span={18} offset={3}>
              <div className={styles.strategy_list}>
                <div className={styles.btn_section}>
                  <Button type="primary" onClick={this.showModal} className={styles.new_strategy_btn}>新建策略</Button>
                  <Modal
                    visible={visible}
                    title="新建策略"
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                    footer={[<Button type="primary" onClick={this.handleOk}>保存</Button>]}
                  >
                    <p>策略名:</p>
                    <Input
                      placeholder="请输入策略名称"
                      onKeyUp={this.handleKeyPress}
                    />
                  </Modal>
                </div>
                <div>
                  <div className={styles.item_list}>
                    {this.state.strategyList.map((myStrategy) => {
                      return <StrategyListItem myStrategy={myStrategy} key={myStrategy.algoId} />;
                    })}
                  </div>
                  <div className={styles.empty_pic_pos}>
                    <img
                      className={styles.empth_pic}
                      style={{ display: this.state.strategyList.length === 0 ? 'block' : 'none' }}
                      role="presentation" src={emptyPic}
                    />
                    <div
                      style={{ display: this.state.strategyList.length === 0 ? 'block' : 'none' }}
                      className={styles.tip_word}
                    >
                      快快点击右上角按钮新建您的策略吧~
                    </div>
                  </div>
                </div>
              </div>
            </Col>
          </Row>
        </Content>
        <BottomBar />
      </Layout>
    );
  }
}

function mapStateToProps(state) {
  const { strategyList } = state.strategyResearch;
  return { strategyList };
}

export default connect(mapStateToProps)(MyStrategy);
