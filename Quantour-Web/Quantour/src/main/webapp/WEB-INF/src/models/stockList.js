/**
 * Created by wyj on 2017/5/16.
 * description:
 */
import * as stockListService from '../services/stockMarketService';
import * as parseUtil from '../utils/parsedata';
import * as stockinfoService from '../services/stockinfoService';

export default {
  namespace: 'stockList',
  state: {
    exponentSHData: [],
    basicSHData: [],
    exponentSZData: [],
    basicSZData: [],
    exponentCYData: [],
    basicCYData: [],
    aList: [],
    shaList: [],
    szaList: [],
    zxbList: [],
    cybList: [],
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockmarket') {
          dispatch({ type: 'fetchExponent', payload: query });
          // dispatch({ type: 'candleChart/fetchTableTimeList', payload: query });
          dispatch({ type: 'fetch', payload: query });
        }
      });
    },
  },

  effects: {
    * fetch({ payload: { page = 1, sort = 'percent', order = 'desc' } }, { call, put, select }) {
      const stockList = yield select(state => state.stockList);
      let dataA = stockList.aList;
      let dataSHA = stockList.shaList;
      let dataSZA = stockList.szaList;
      let dataZXB = stockList.zxbList;
      let dataCYB = stockList.cybList;
      if (dataA.length !== 0 && dataSHA.length !== 0 && dataSZA.length !== 0 &&
        dataZXB.length !== 0 && dataCYB.length !== 0) {
        return;
      } else {
        dataA = (yield call(stockListService.fetchListData, 'a')).data;
        dataSHA = (yield call(stockListService.fetchListData, 'sha')).data;
        dataSZA = (yield call(stockListService.fetchListData, 'sza')).data;
        dataZXB = (yield call(stockListService.fetchListData, 'zxb')).data;
        dataCYB = (yield call(stockListService.fetchListData, 'cyb')).data;
        // dataA = (yield call(stockListService.newFetchListData, 'a', page, sort, order)).data;
        // dataSHA = (yield call(stockListService.newFetchListData, 'sha', page, sort, order)).data;
        // dataSZA = (yield call(stockListService.newFetchListData, 'sza', page, sort, order)).data;
        // dataZXB = (yield call(stockListService.newFetchListData, 'zxb', page, sort, order)).data;
        // dataCYB = (yield call(stockListService.newFetchListData, 'cyb', page, sort, order)).data;
      }
      yield put({
        type: 'save',
        payload: {
          aList: dataA,
          shaList: dataSHA,
          szaList: dataSZA,
          zxbList: dataZXB,
          cybList: dataCYB,
        },
      });
    },
    * fetchExponent({ payload }, { call, put, select }) {
      const stockList = yield select(state => state.stockList);
      if (stockList.exponentSHData.length !== 0) return;
      const dataSH = yield call(stockinfoService.fetchExponentData, 'SH000001');
      const dataSZ = yield call(stockinfoService.fetchExponentData, 'SZ399001');
      const dataCY = yield call(stockinfoService.fetchExponentData, 'SZ399006');
      const basicSHData = yield call(parseUtil.parsePromiseData, dataSH.basicData);
      const basicSZData = yield call(parseUtil.parsePromiseData, dataSZ.basicData);
      const basicCYData = yield call(parseUtil.parsePromiseData, dataCY.basicData);
      yield put({
        type: 'saveExponent',
        payload: {
          exponentSHData: dataSH.exponentData,
          exponentSZData: dataSZ.exponentData,
          exponentCYData: dataCY.exponentData,
          basicSHData,
          basicSZData,
          basicCYData,
        },
      });
    },
  },
  reducers: {
    save(state,
      { payload: {
      aList,
      shaList,
      szaList,
      zxbList,
      cybList,
      dataList: timeList,
    } }) {
      return { ...state,
        aList,
        shaList,
        szaList,
        zxbList,
        cybList,
        timeList,
      };
    },
    saveExponent(state, { payload: {
      exponentSHData,
      exponentSZData,
      exponentCYData,
      basicSHData,
      basicSZData,
      basicCYData } }) {
      return { ...state,
        exponentSHData,
        exponentSZData,
        exponentCYData,
        basicSHData,
        basicSZData,
        basicCYData };
    },
  },
};
