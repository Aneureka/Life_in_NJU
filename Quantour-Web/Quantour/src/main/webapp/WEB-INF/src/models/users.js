/**
 * Created by wyj on 2017/6/1.
 * description:
 */
import { routerRedux } from 'dva/router';
import { message } from 'antd';
import * as userService from '../services/userService';
import * as stockinfoService from '../services/stockinfoService';

export default {
  namespace: 'users',
  state: {
    userInfo: [],
    selfStockList: [],
    selfStockCodeList: [],
    username: '',
    isTrue: 'none',
    status: 0,
  },

  subscriptions: {
    setup({ dispatch, history }) {  // eslint-disable-line
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockmarket' || pathname === '/individualpage' || pathname === '/stockinfo') {
          dispatch({ type: 'initUserInfo', payload: query });
          dispatch({ type: 'fetchSelfStock', payload: query });
          dispatch({ type: 'fetchSelfStockCode', payload: query });
        }
      });
    },
  },

  effects: {
    * initUserInfo({ payload }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        // 已登录，获得用户信息
        const data = yield call(userService.fetchUserInfo, token);
        yield put({
          type: 'saveUserInfo',
          payload: {
            userInfo: data.data,
            username: data.data.username,
          },
        });
      }
    },
    * create({ payload: user }, { call, put }) {
      const result = yield call(userService.create, user);
      yield put({
        type: 'createUser',
        payload: {
          status: result.data.status,
        },
      });
      if (result.data.status === 200) {
        yield put(routerRedux.push('/login'));
      }
    },
    * changePassword({ payload: user }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      const tokenUsername = sessionStorage.getItem('username');
      const newUser = {
        username: tokenUsername,
        password: user.password,
        newPassword: user.newPassword,
      };
      if (token && token.length > 0) {
        const result = yield call(userService.changePassword, newUser, token);
        if (result.err) {
          message.error('修改密码失败，请输入正确的原密码');
        } else {
          message.success('修改密码成功');
          sessionStorage.setItem('userToken', '');
          sessionStorage.setItem('username', '');
          yield put(routerRedux.push('/login'));
        }
      }
    },
    * forgetPassword({ payload: user }, { call, put }) {
      const result = yield call(userService.forgetPassword, user);
      console.log(result);
      if (result.err) {
        message.error('找回密码失败');
      } else {
        message.success('修改密码成功');
        sessionStorage.setItem('userToken', '');
        sessionStorage.setItem('username', '');
        yield put(routerRedux.push('/login'));
      }
    },
    * signin({ payload: user }, { call, put }) {
      const response = yield call(userService.signin, user);
      if (response.data.status === 0) {
        const sessionStorage = window.sessionStorage;
        sessionStorage.setItem('userToken', response.data.result);
        sessionStorage.setItem('username', user.username);
        yield put({
          type: 'login',
          payload: {
            username: user.username,
          },
        });
        yield put({
          type: 'checkUser',
          payload: {
            isTrue: 'none',
          },
        });
        yield put({
          type: 'initUserInfo',
        });
        // 判断是否需要跳转界面，默认回到首页
        const beforeUrl = sessionStorage.getItem('beforeUrl');
        if (beforeUrl && beforeUrl.length > 0) {
          yield put(routerRedux.push(beforeUrl));
        } else {
          yield put(routerRedux.push('/'));
        }
      } else {
        yield put({
          type: 'checkUser',
          payload: {
            isTrue: 'block',
          },
        });
      }
    },
    * signout({ payload }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      sessionStorage.setItem('userToken', '');
      sessionStorage.setItem('username', '');
      yield put({
        type: 'logout',
        payload: {
          username: '',
          userInfo: [],
          selfStockList: [],
          selfStockCodeList: [],
        },
      });
      sessionStorage.setItem('beforeUrl', '');
      yield put(routerRedux.push('/'));
    },
    * addSelfStock({ payload: { code, pathname = '/' } }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(userService.addSelfSelectsdStock, { code, token });
        yield put({
          type: 'addSelfStockItem',
        });
        // yield put({
        //   type: 'fetchSelfStock',
        // });
        message.success('添加成功');
      } else {
        sessionStorage.setItem('beforeUrl', pathname);
        yield put(routerRedux.push('/login'));
      }
    },
    * deleteSelfStock({ payload: code }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(userService.deleteSelfStock, { code, token });
        yield put({
          type: 'delSelfStockItem',
        });
        // yield put({
        //   type: 'fetchSelfStock',
        // });
        message.success('删除成功');
      } else {
        yield put(routerRedux.push('/login'));
      }
    },
    * fetchSelfStock({ payload }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        // 已登录，获得股票代码
        const codeData = yield call(userService.fetchSelfStockList, token);
        const codes = codeData.data;
        // 获取股票列表
        const selfStockList = [];
        for (let i = 0; i < codes.length; i += 1) {
          const { data } = yield call(stockinfoService.getStockBasic, codes[i].code);
          yield selfStockList.push({
            key: i,
            code: data.symbol,
            name: data.name,
            close: data.current,
            pch: data.percentage,
            change: data.change,
          });
        }
        yield put({
          type: 'saveStockList',
          payload: {
            selfStockList,
          },
        });
      }
    },
    * fetchSelfStockCode({ payload }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        const list = yield call(userService.fetchSelfStockCodeList, token);
        yield put({
          type: 'saveSelfStockCode',
          payload: {
            selfStockCodeList: list,
          },
        });
      }
    },
    * addBasicSelfStock({ payload: { code, pathname = '/' } }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(userService.addSelfSelectsdStock, { code, token });
        yield put({
          type: 'addSelfStockItem',
        });
        yield put({
          type: 'fetchSelfStock',
        });
        message.success('添加成功');
      } else {
        sessionStorage.setItem('beforeUrl', pathname);
        yield put(routerRedux.push('/login'));
      }
    },
    * deleteBasicSelfStock({ payload: code }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(userService.deleteSelfStock, { code, token });
        yield put({
          type: 'delSelfStockItem',
        });
        yield put({
          type: 'fetchSelfStock',
        });
        message.success('删除成功');
      } else {
        yield put(routerRedux.push('/login'));
      }
    },
  },

  reducers: {
    saveUserInfo(state, { payload: { username, userInfo } }) {
      return { ...state, username, userInfo };
    },
    createUser(state, { payload: { status } }) {
      return { ...state, status };
    },
    login(state, { payload: { username } }) {
      return { ...state, username };
    },
    logout(state, { payload: { username, userInfo, selfStockList, selfStockCodeList } }) {
      return { ...state, username, userInfo, selfStockList, selfStockCodeList };
    },
    addSelfStockItem(state) {
      return { ...state };
    },
    saveStockList(state, { payload: { selfStockList } }) {
      return { ...state, selfStockList };
    },
    delSelfStockItem(state) {
      return { ...state };
    },
    checkUser(state, { payload: { isTrue } }) {
      return { ...state, isTrue };
    },
    saveSelfStockCode(state, { payload: { selfStockCodeList } }) {
      return { ...state, selfStockCodeList };
    },
  },
};
