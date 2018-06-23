/**
 * Created by wyj on 2017/5/17.
 * description:
 */
import * as tapeListService from '../services/tapeListService';

export default {
  namespace: 'tapelist',
  state: {
    dataBuyList: [],
    dataSellList: [],
    diff: 0,
    ratio: 0,
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockinfo') {
          dispatch({ type: 'fetch', payload: query });
        }
      });
    },
  },

  effects: {
    * fetch({ payload }, { call, put }) {
      const { buyData, sellData, diff, ratio } = yield call(tapeListService.fetchListData);
      yield put({
        type: 'save',
        payload: {
          buyData,
          sellData,
          diff,
          ratio,
        },
      });
    },
  },

  reducers: {
    save(state, { payload: { buyData: dataBuyList, sellData: dataSellList, diff, ratio } }) {
      return { ...state, dataBuyList, dataSellList, diff, ratio };
    },
  },
};
