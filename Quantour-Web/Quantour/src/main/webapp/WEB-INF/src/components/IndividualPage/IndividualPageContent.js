/**
 * Created by wyj on 2017/5/24.
 * description:
 */
import React from 'react';
import { connect } from 'dva';
import { Layout, Icon, Row, Col, Menu, Tabs } from 'antd';
import SelfStockTablePane from '../../components/SelfStockTable/SelfStockTablePane';
import MyPostsPane from '../../components/MyPostsPane/MyPostsPane';
import PersonalInfoPane from '../../components/PersonalInfo/PersonalInfoPane';
import AccountSettingPane from '../../components/AccountSetting/AccountSettingPane';

const MenuItemGroup = Menu.ItemGroup;
const MenuItem = Menu.Item;
const TabPane = Tabs.TabPane;

class IndividualPageContent extends React.Component {
  // state = {
  //   selfStockTablePaneDisplay: 'block',
  //   myPostsPaneDisplay: 'none',
  //   personalInfoPaneDisplay: 'none',
  //   accountSettingPaneDisplay: 'none',
  // };

  // handleClick = (e) => {
  //   if (e.key === 'self_stock') {
  //     this.setState({
  //       selfStockTablePaneDisplay: 'block',
  //       myPostsPaneDisplay: 'none',
  //       personalInfoPaneDisplay: 'none',
  //       accountSettingPaneDisplay: 'none',
  //     });
  //   } else if (e.key === 'posts') {
  //     this.setState({
  //       selfStockTablePaneDisplay: 'none',
  //       myPostsPaneDisplay: 'block',
  //       personalInfoPaneDisplay: 'none',
  //       accountSettingPaneDisplay: 'none',
  //     });
  //   } else if (e.key === 'self_info') {
  //     this.setState({
  //       selfStockTablePaneDisplay: 'none',
  //       myPostsPaneDisplay: 'none',
  //       personalInfoPaneDisplay: 'block',
  //       accountSettingPaneDisplay: 'none',
  //     });
  //   } else if (e.key === 'account_setting') {
  //     this.setState({
  //       selfStockTablePaneDisplay: 'none',
  //       myPostsPaneDisplay: 'none',
  //       personalInfoPaneDisplay: 'none',
  //       accountSettingPaneDisplay: 'block',
  //     });
  //   }
  // };

  render() {
    return (
      <Layout>
        <div style={{ backgroundColor: '#ffffff', padding: '20px' }}>
          <PersonalInfoPane data={this.props.userInfo} />
          <Tabs>
            <TabPane tab="自选股" key="1"><SelfStockTablePane /></TabPane>
            {/* <TabPane tab="个人信息" key="2"></TabPane>*/}
            <TabPane tab="账号设置" key="3"><AccountSettingPane /></TabPane>
            {/* <TabPane tab="帖子" key="4"><MyPostsPane />*/}
            {/* </TabPane>*/}
          </Tabs>
        </div>
        {/* <Row>*/}
        {/* /!* <Col span={4}>*!/*/}
        {/* /!* <Menu*!/*/}
        {/* /!* mode={'inline'}*!/*/}
        {/* /!* onClick={this.handleClick}*!/*/}
        {/* /!* defaultSelectedKeys={['self_stock']}*!/*/}
        {/* /!* >*!/*/}
        {/* /!* <MenuItemGroup key="my_stock" title={<span>*/}
        {/* <Icon type="setting" /><span>我的股票</span></span>}>*!/*/}
        {/* /!* <MenuItem key="self_stock">自选股</MenuItem>*!/*/}
        {/* /!* </MenuItemGroup>*!/*/}
        {/* /!* <MenuItemGroup key*/}
        {/*= "community" title={<span><Icon type="setting" /><span>社区</span></span>}>*!/*/}
        {/* /!* <MenuItem key="posts">帖子</MenuItem>*!/*/}
        {/* /!* <MenuItem key="concern">关注</MenuItem>*!/*/}
        {/* /!* <MenuItem key="fans">粉丝</MenuItem>*!/*/}
        {/* /!* </MenuItemGroup>*!/*/}
        {/* /!* <MenuItemGroup key*/}
        {/*= "account" title={<span><Icon type="setting" /><span>账号设置</span></span>}>*!/*/}
        {/* /!* <MenuItem key="self_info">个人信息</MenuItem>*!/*/}
        {/* /!* <MenuItem key="account_setting">账号设置</MenuItem>*!/*/}
        {/* /!* </MenuItemGroup>*!/*/}
        {/* /!* </Menu>*!/*/}
        {/* /!* </Col>*!/*/}
        {/* <Col span={20} offset={4} style={{ backgroundColor: '#ffffff' }}>*/}
        {/* <div />*/}
        {/* </Col>*/}
        {/* </Row>*/}
      </Layout>
    );
  }
}

function mapStateToProps(state) {
  const { userInfo } = state.users;
  return { userInfo };
}
export default connect(mapStateToProps)(IndividualPageContent);
