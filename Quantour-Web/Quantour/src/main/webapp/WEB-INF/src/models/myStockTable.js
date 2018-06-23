/**
 * Created by Hitigerzzz on 2017/5/16.
 */
import * as stockinfoService from '../services/stockinfoService';

export default {
  namespace: 'myStockTable',
  state: {
    recentStockList: [],
    longStockList: [],
    hotStockList: [],
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockinfo') {
          dispatch({ type: 'fetch', payload: query });
          dispatch({ type: 'fetchRecent', payload: query });
        }
      });
    },
  },

  effects: {
    *fetch({ payload }, { call, put }) {  // eslint-disable-line
      const { longStockData } = yield call(stockinfoService.getLongHuStock);
      const { hotStockData } = yield call(stockinfoService.getHotRankStock);
      yield put({
        type: 'saveStockList',
        payload: {
          longStockData,
          hotStockData,
        },
      });
    },
    * fetchRecent({ payload: { code } }, { call, put }) {
      function getRecentStock() {
        const data = localStorage.getItem('recentStock');
        const recent = JSON.parse(data);
        const stockList = recent.recentStock;
        return stockList;
      }
      function setRecentStock(list) {
        const data = {
          recentStock: list,
        };
        const str = JSON.stringify(data);
        localStorage.setItem('recentStock', str);
      }
      const localStorage = window.localStorage;
      if (localStorage.recentStock) {
        const stockList = getRecentStock();
        const index = stockList.indexOf(code);
        if (index === -1) {
          if (code && code.length === 8) {
            stockList.push(code);
          }
        } else {
          stockList.splice(index, 1);
          stockList.push(code);
        }
        setRecentStock(stockList);
      } else if (code && code.length === 8) {
        const recent = [];
        recent.push(code);
        setRecentStock(recent);
      }
      const stockList = getRecentStock();
      const recentStockList = [];
      for (let i = stockList.length - 1; i > -1; i -= 1) {
        const { data } = yield call(stockinfoService.getStockBasic, stockList[i]);
        yield recentStockList.push({
          key: i,
          code: data.symbol,
          name: data.name,
          close: data.current,
          pch: data.percentage,
        });
      }
      yield put({
        type: 'saveRecentList',
        payload: {
          recentStockList,
        },
      });
    },
  },
  reducers: {
    saveStockList(state, { payload: {
      selfStockData: selfStockList,
      longStockData: longStockList,
      hotStockData: hotStockList } }) {
      return { ...state, selfStockList, longStockList, hotStockList };
    },
    saveRecentList(state, { payload: { recentStockList } }) {
      return { ...state, recentStockList };
    },
  },
};
