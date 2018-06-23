/**
  * Created by wyj on 2017/5/15.
  * Description:
  */
import * as stockMarketService from '../services/stockMarketService';

export default {
  namespace: 'newsList',
  state: {
    newsList: [],
    allNum: 0,
    current: 1,
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockmarket') {
          dispatch({ type: 'fetch', payload: query });
        }
      });
    },
  },

  effects: {
    * fetch({ payload: { page = 1 } }, { call, put }) {
      const { newsList, allNum } = yield call(stockMarketService.getMarketNews, page);
      yield put({
        type: 'save',
        payload: {
          newsList,
          allNum,
          current: page,
        },
      });
    },
  },

  reducers: {
    save(state, { payload: { newsList, allNum, current } }) {
      return { ...state, newsList, allNum, current };
    },
  },
};
