/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import { routerRedux } from 'dva/router';
import { notification } from 'antd';
import * as strategyService from '../services/strategyService';

export default {
  namespace: 'strategyResult',
  state: {
    runAlgoId: '',
    result: [],
    // 策略概述
    sumData: [],
    standardData: [],
    myData: [],
    date: [],
    profit: [],
    buy: [],
    sell: [],
    // 调仓记录
    transferHead: [],
    transfer: [],
    // 持仓记录
    positionHead: [],
    position: [],
    // 账户信息
    stockAccount: [],
    // 收益率
    profitRadio: [],
    // 阿尔法
    alpha: [],
    // 贝塔
    beta: [],
    // 夏普比率
    sharpe: [],
    // 收益波动率
    volatility: [],
    // 信息比率
    informationRadio: [],
    // 最大回测
    back: [],
    // 日志
    diary: [],
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/strategydetail') {
          dispatch({ type: 'fetchDetailAlgoId', payload: query });
          dispatch({ type: 'fetchResult', payload: query });
        }
      });
    },
  },

  effects: {
    * fetchDetailAlgoId({ payload: { algoId: id } }, { call, put }) {
      yield put({
        type: 'saveDetailAlgoId',
        payload: {
          runAlgoId: id,
        },
      });
    },
    // 运行回测
    * fetchResult({ payload: { algoId: id, strategy } }, { call, put, select }) {
      // 保存策略
      if (strategy) {
        const sessionStorage = window.sessionStorage;
        const token = sessionStorage.getItem('userToken');
        if (token && token.length > 0) {
          yield call(strategyService.updateStrategy, strategy, token);
        }
      } else {
        // 非编译非运行回测
        const strategyResult = yield select(state => state.strategyResult);
        if (strategyResult.standardData.length > 0) { // 已有数据
          return;
        }
      }
      // 运行策略
      const data = yield call(strategyService.fetchStrategyResult, id);
      // 更新策略参数
      yield put({
        type: 'strategyResearch/fetchStrategyById',
        payload: { algoId: id },
      });
      if (data === 1) {
        notification.error({
          message: '编译错误',
          description: '您的代码发生了错误，请纠正后再重新编译',
          duration: 7,
        });
        return;
      }
      if (strategy) {
        // 跳转界面
        yield put(routerRedux.push({
          pathname: '/strategydetail',
          query: {
            algoId: id,
          },
        }));
      }
      yield put({
        type: 'saveResult',
        payload: {
          result: data.result,
          standardData: data.standardData,
          myData: data.myData,
          date: data.date,
          profit: data.profit,
          buy: data.buys,
          sell: data.sells,
          sumData: data.sumData,
          positionHead: data.position.headData,
          position: data.position.data,
          transferHead: data.transfer.headData,
          transfer: data.transfer.data,
          stockAccount: data.stockAccount,
          alpha: data.alpha,
          beta: data.beta,
          sharpe: data.sharpe,
          informationRadio: data.informationRadio,
          back: data.back,
          profitRadio: data.profitRadio,
          volatility: data.volatility,
        },
      });
      // 保存运行的 id
      yield put({
        type: 'saveDetailAlgoId',
        payload: {
          runAlgoId: id,
        },
      });
    },
    // 编译
    * fetchCompileResult({ payload: { algoId: id, strategy } }, { call, put }) {
      // 保存策略
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        yield call(strategyService.updateStrategy, strategy, token);
      }
      // 运行策略
      const data = yield call(strategyService.fetchStrategyResult, id);
      // 更新策略参数
      yield put({
        type: 'strategyResearch/fetchStrategyById',
        payload: { algoId: strategy.algoId },
      });
      if (data === 1) {
        notification.error({
          message: '编译错误',
          description: '您的代码发生了错误，请纠正后再重新编译',
          duration: 7,
        });
        return;
      }
      // 保存运行策略结果
      yield put({
        type: 'saveResult',
        payload: {
          result: data.result,
          standardData: data.standardData,
          myData: data.myData,
          date: data.date,
          profit: data.profit,
          buy: data.buys,
          sell: data.sells,
          sumData: data.sumData,
          positionHead: data.position.headData,
          position: data.position.data,
          transferHead: data.transfer.headData,
          transfer: data.transfer.data,
          stockAccount: data.stockAccount,
          alpha: data.alpha,
          beta: data.beta,
          sharpe: data.sharpe,
          informationRadio: data.informationRadio,
          back: data.back,
          profitRadio: data.profitRadio,
          volatility: data.volatility,
        },
      });
      // 保存运行的 id
      yield put({
        type: 'saveDetailAlgoId',
        payload: {
          runAlgoId: id,
        },
      });
    },
    * downloadResult({ payload }, { call, put, select }) {
      // 获得所有数据
      const strategyResult = yield select(state => state.strategyResult);
      yield call(strategyService.downloadStrategy, strategyResult.result);
    },
  },

  reducers: {
    saveDetailAlgoId(state, { payload: { runAlgoId } }) {
      return { ...state, runAlgoId };
    },
    saveResult(state, { payload:
      { result, sumData,
        standardData, myData, date, profit, buy, sell,
        alpha,
        beta,
        sharpe,
        informationRadio,
        back,
        profitRadio,
        volatility,
        transferHead,
        transfer,
        positionHead,
        position,
        stockAccount,
      } }) {
      return { ...state,
        result,
        sumData,
        standardData,
        myData,
        date,
        profit,
        buy,
        sell,
        alpha,
        beta,
        sharpe,
        informationRadio,
        back,
        profitRadio,
        volatility,
        transferHead,
        transfer,
        positionHead,
        position,
        stockAccount,
      };
    },
  },
};
