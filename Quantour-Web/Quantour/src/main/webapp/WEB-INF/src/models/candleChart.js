/**
 * Created by Hitigerzzz on 2017/5/12.
 */
import * as stockinfoService from '../services/stockinfoService';
import * as parseUtil from '../utils/parsedata';

export default {
  namespace: 'candleChart',
  state: {
    code: '',
    // 股票数据线
    isCompare: 0, // 是否股票对比 0 不是
    series: [],
    timeData: [],
    candle_list: [],
    ma_list: [],
    candleW_list: [],
    maW_list: [],
    candleM_list: [],
    maM_list: [],
    change_list: [],
    changeW_list: [],
    changeM_list: [],
    timeList: [], // 股票列表分时图
    searchResult: [],
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockinfo') {
          dispatch({ type: 'fetchTime', payload: query });
          dispatch({ type: 'fetchDay', payload: query });
          dispatch({ type: 'fetchWeek', payload: query });
          dispatch({ type: 'fetchMonth', payload: query });
        }
        if (pathname === '/stockmarket') {
          dispatch({ type: 'fetchTableTimeList', payload: query });
        }
      });
    },
  },

  effects: {
    *fetchTime({ payload: { code }}, { call, put }) {  // eslint-disable-line
      const { timeData, series } =
        yield call(stockinfoService.fetchTimeData, { code });
      yield put({
        type: 'saveT',
        payload: {
          code,
          timeData,
          series,
          isCompare: 0,
        },
      });
    },
    *fetchDay({ payload: { code, period='1day', type='normal' } }, { call, put }) {  // eslint-disable-line
      const { candle_data, ma_data, change_data } = yield call(
        stockinfoService.fetchCandleData, { code, period, type });
      yield put({
        type: 'saveD',
        payload: {
          candle_data,
          ma_data,
          change_data,
        },
      });
    },
    *fetchWeek({ payload: { code, period='1week', type='normal' } }, { call, put }) {  // eslint-disable-line
      const { candle_data, ma_data, change_data } = yield call(
        stockinfoService.fetchCandleData, { code, period, type });
      yield put({
        type: 'saveW',
        payload: {
          candle_data,
          ma_data,
          change_data,
        },
      });
    },
    *fetchMonth({ payload: { code, period='1month', type='normal'} }, { call, put }) {  // eslint-disable-line
      const { candle_data, ma_data, change_data } = yield call(
        stockinfoService.fetchCandleData, { code, period, type });
      yield put({
        type: 'saveM',
        payload: {
          candle_data,
          ma_data,
          change_data,
        },
      });
    },
    * fetchTableTimeList({ payload: { board = 'a', page = 1, sort = 'changePercent,desc' } }, { call, put }) {
      const timeList = yield call(stockinfoService.fetchTableTimeList,
        { board, page, sort });
      const dataList = [];
      for (let i = 0; i < timeList.length; i += 1) {
        const item = yield call(parseUtil.parsePromiseData, timeList[i]);
        dataList.push(item);
      }
      yield put({
        type: 'saveTableTime',
        payload: {
          dataList,
        },
      });
    },
    * fetchCompareData({ payload: { codes, names } }, { call, put, select }) {
      const candleChart = yield select(state => state.candleChart);
      const oldSeries = candleChart.series;
      // 多个数据线
      const series = [];
      for (let i = 0; i < codes.length; i += 1) {
        const code = codes[i];
        const name = names[i];
        const presentData = yield call(stockinfoService.fetchCompareTimeData, code);
        yield call(stockinfoService.setCompareStockSeries, { series, name, presentData });
      }
      // 成交量
      series.push(oldSeries[oldSeries.length - 1]);
      yield put({
        type: 'saveCompare',
        payload: {
          series,
          isCompare: 1,
        },
      });
    },
    * fetchCompareStock({ payload: value }, { call, put }) {
      const data = yield call(stockinfoService.handleStockSearch, value);
      yield put({
        type: 'saveCompareStock',
        payload: {
          searchResult: data,
        },
      });
    },
  },

  reducers: {
    saveT(state, { payload: { code, timeData, series, isCompare } }) {
      return { ...state, code, timeData, series, isCompare };
    },
    saveD(state, { payload: { candle_data: candle_list, ma_data: ma_list,
      change_data: change_list } }) {
      return { ...state, candle_list, ma_list, change_list };
    },
    saveW(state, { payload: { candle_data: candleW_list, ma_data: maW_list,
      change_data: changeW_list } }) {
      return { ...state, candleW_list, maW_list, changeW_list };
    },
    saveM(state, { payload: { candle_data: candleM_list, ma_data: maM_list,
      change_data: changeM_list } }) {
      return { ...state, candleM_list, maM_list, changeM_list };
    },
    saveTableTime(state, { payload: { dataList: timeList } }) {
      return { ...state, timeList };
    },
    saveCompare(state, { payload: { series, isCompare } }) {
      return { ...state, series, isCompare };
    },
    saveCompareStock(state, { payload: { searchResult } }) {
      return { ...state, searchResult };
    },
  },

};
