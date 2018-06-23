/**
 * Created by wyj on 2017/6/3.
 * description:
 */
import { routerRedux } from 'dva/router';
import * as strategyService from '../services/strategyService';

export default {
  namespace: 'strategyResearch',
  state: {
    algoId: '',
    standardData: [],
    myData: [],
    measureHistoryDate: [],
    strategyList: [],
    strategyContent: [],
    backTestHistory: [],
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/strategy') {
          dispatch({ type: 'fetchStrategyById', payload: query });
        } else if (pathname === '/mystrategy') {
          dispatch({ type: 'fetchStrategyByUsername', payload: query });
        } else if (pathname === '/strategyhistory') {
          dispatch({ type: 'fetchBackTestHistory', payload: query });
          dispatch({ type: 'fetchHistoryAlgoId', payload: query });
        }
      });
    },
  },

  effects: {
    * createStrategy({ payload: name }, { call, put, select }) {
      const username = yield select(state => state.users);
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(strategyService.createStrategy, name, username, token);
        yield put({
          type: 'saveNewStrategy',
        });
      }
    },
    * fetchStrategyByUsername({ payload }, { call, put, select }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield put({
          type: 'users/initUserInfo',
        });
        const username = sessionStorage.getItem('username');
        const list = yield call(strategyService.finStrategyByUsername, username, token);
        for (let i = 0; i < list.data.length; i += 1) {
          list.data[i].history =
            yield call(strategyService.fetchLatestHistory, list.data[i].algoId, token);
          const num = yield call(strategyService.fetchHistoryNum, list.data[i].algoId, token);
          list.data[i].hisNum = num.data;
        }
        yield put({
          type: 'saveStrategyListByUsername',
          payload: {
            strategyList: list.data,
          },
        });
      } else {
        sessionStorage.setItem('beforeUrl', '/mystrategy');
        yield put(routerRedux.push('/login'));
      }
    },
    * fetchStrategyById({ payload: { algoId: id } }, { call, put, select }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        const info = yield call(strategyService.fetchStrategyById, id, token);
        yield put({
          type: 'saveStrategyContent',
          payload: {
            strategyContent: info.data,
            algoId: id,
          },
        });
      }
    },
    * removeStrategy({ payload: id }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(strategyService.removeStrategy, id, token);
        yield put({
          type: 'saveRemoveStrategy',
        });
      }
    },
    * updateStrategy({ payload: strategy }, { call, put, select }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(strategyService.updateStrategy, strategy, token);
        yield put({
          type: 'saveUpdateStrategy',
        });
      }
    },
    * fetchBackTestHistory({ payload: id }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        const data = yield call(strategyService.fetchBackTestHistory, id.algoId, token);
        yield put({
          type: 'saveBackTestHistory',
          payload: {
            backTestHistory: data,
          },
        });
      }
    },
    * fetchHistoryAlgoId({ payload: id }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield put({
          type: 'saveHistoryAlgoId',
          payload: {
            algoId: id,
          },
        });
      }
    },
  },

  reducers: {
    saveMeasureHistory(state, { payload: { measureHistoryDate } }) {
      return { ...state, measureHistoryDate };
    },
    saveNewStrategy(state) {
      return { ...state };
    },
    saveStrategyListByUsername(state, { payload: { strategyList } }) {
      return { ...state, strategyList };
    },
    saveStrategyContent(state, { payload: { strategyContent, algoId } }) {
      return { ...state, strategyContent, algoId };
    },
    saveRemoveStrategy(state) {
      return { ...state };
    },
    saveUpdateStrategy(state) {
      return { ...state };
    },
    saveBackTestHistory(state, { payload: { backTestHistory } }) {
      return { ...state, backTestHistory };
    },
    saveHistoryAlgoId(state, { payload: { algoId } }) {
      return { ...state, algoId };
    },
  },
};
