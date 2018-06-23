/**
 * Created by wyj on 2017/5/10.
 */
import React from 'react';
import { Menu, Layout, Icon } from 'antd';
import { Link } from 'dva/router';
import { connect } from 'dva';
import styles from './Navigation.less';
import logo from '../../assets/QAQquant.png';
import SearchBox from './SearchBox';

const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
const Header = Layout.Header;

function Navigation({ location, dispatch, searchResult, username }) {
  function handleLogout() {
    dispatch({
      type: 'users/signout',
    });
  }
  const sessionStorage = window.sessionStorage;
  const token = sessionStorage.getItem('userToken');
  let showLogin;
  let showName;
  let name;
  if (token) {
    if (token.length > 0) {
      showLogin = styles.hide;
      showName = styles.show;
      name = sessionStorage.getItem('username');
    } else {
      showLogin = styles.show;
      showName = styles.hide;
      name = '';
    }
  } else {
    showLogin = styles.show;
    showName = styles.hide;
    name = '';
  }
  return (
    <Header className={styles.head}>
      <div className={styles.img_hold}>
        <Link to="#">
          <img className={styles.logo} role="presentation" src={logo} />
        </Link>
      </div>
      <div className={styles.search}>
        <SearchBox
          searchResult={searchResult}
        />
      </div>
      <Menu
        selectedKeys={[location.pathname]}
        mode="horizontal"
        theme="dark"
        className={styles.menu}
      >
        <SubMenu
          key="sub" title={<span><Icon type="user" /><span>{name}</span></span>}
          className={`${styles.submenu} ${showName}`}
        >
          <MenuItemGroup>
            <Menu.Item key="1"><Link to="/individualpage">个人中心</Link></Menu.Item>
            <Menu.Item key="2"><span onClick={handleLogout}>退出登录</span></Menu.Item>
          </MenuItemGroup>
        </SubMenu>
        <Menu.Item className={showLogin}>
          <Link to="/register">注册</Link>
        </Menu.Item>
        <Menu.Item className={showLogin}>
          <Link to="/login">登录</Link>
        </Menu.Item>
        <Menu.Item>
          <a href="https://www.ricequant.com/api/python/chn">帮助</a>
        </Menu.Item>
        <Menu.Item>
          <Link to="/community">社区</Link>
        </Menu.Item>
        <Menu.Item>
          <Link to="/mystrategy">策略研究</Link>
        </Menu.Item>
        <Menu.Item>
          <Link to="/stockmarket">股票市场</Link>
        </Menu.Item>
      </Menu>
    </Header>
  );
}

function mapStateToProps(state) {
  const { searchResult } = state.candleChart;
  const { username } = state.users;
  return { searchResult, username };
}

export default connect(mapStateToProps)(Navigation);
