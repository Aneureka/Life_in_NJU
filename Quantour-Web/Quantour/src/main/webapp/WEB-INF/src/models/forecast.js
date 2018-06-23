/**
 * Created by Hitigerzzz on 2017/6/4.
 */
import * as ForecastService from '../services/stockinfoService';
import * as timeformatter from '../utils/timeformatter';

export default {
  namespace: 'forecast',
  state: {
    // 股票预测
    retCode: -1,
    markPosition: '',
    expectedPrice: [],
    actualPrice: [],
    // 新闻指数
    timeData: [],
    relatedScores: [],
    sentimentScores: [],
    // 平均新闻指数
    sumScores: 0,
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockinfo') {
          dispatch({ type: 'fetchPrice', payload: query });
          dispatch({ type: 'fetchNewsScore', payload: query });
        }
      });
    },
  },

  effects: {
    * fetchPrice({ payload: { code } }, { call, put }) {
      yield put({
        type: 'saveRetCode',
        payload: {
          retCode: -1,
        },
      });
      const data = yield call(ForecastService.fetchForecastData, { code, num: 90 });
      const retCode = data.data.retCode;
      const expectedPrice = [];
      const actualPrice = [];
      let markPosition = '';
      if (retCode === 1) {
        const length = data.data.dates.length;
        for (let i = 0; i < length; i += 1) {
          const date = new Date(data.data.dates[i]);
          expectedPrice.push({
            name: date.toString(),
            value: [
              [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('-'),
              data.data.predicted_prices[i].toFixed(2),
            ],
          });
          if (i < length - 1) {
            actualPrice.push({
              name: date.toString(),
              value: [
                [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('-'),
                data.data.prices[i],
              ],
            });
          }
          // 设置 forecast 提示线的位置
          if (i === length - 2) {
            markPosition = [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('-');
          }
        }
        yield put({
          type: 'save',
          payload: {
            retCode,
            expectedPrice,
            actualPrice,
            markPosition,
          },
        });
      } else {
        yield put({
          type: 'saveRetCode',
          payload: {
            retCode,
          },
        });
      }
    },
    * fetchNewsScore({ payload: { code } }, { call, put }) {
      const now = new Date();
      const endDate = timeformatter.DateToYYYYMMDD(now);
      // 减去2天
      now.setDate(now.getDate() - 2);
      const beginDate = timeformatter.DateToYYYYMMDD(now);
      const data = yield call(ForecastService.fetchNewsScore, { code, beginDate, endDate });
      const sumData =
        yield call(ForecastService.fetchNewsSentimentScore, { code, beginDate, endDate });
      const sumScores = sumData.data.sentimentScore.toFixed(2);
      const dataList = data.data.data;
      const timeData = [];
      const relatedScores = [];
      const sentimentScores = [];
      for (let i = 0; i < dataList.length; i += 1) {
        const date = new Date(dataList[i].newsPublishTime);
        timeData.push(timeformatter.timeToDateTime(date));
        relatedScores.push(dataList[i].relatedScore.toFixed(2));
        // relatedScores.push({
        //   name: date.toString(),
        //   value: [
        //     timeformatter.timeToDateTime(date),
        //     dataList[i].relatedScore.toFixed(2),
        //   ],
        // });
        sentimentScores.push(dataList[i].sentimentScore.toFixed(2));
        // sentimentScores.push({
        //   name: date.toString(),
        //   value: [
        //     timeformatter.timeToDateTime(date),
        //     dataList[i].sentimentScore.toFixed(2),
        //   ],
        // });
      }
      yield put({
        type: 'saveNews',
        payload: {
          timeData,
          relatedScores,
          sentimentScores,
          sumScores,
        },
      });
    },
  },

  reducers: {
    save(state, { payload: { retCode, expectedPrice, actualPrice, markPosition } }) {
      return { ...state, retCode, expectedPrice, actualPrice, markPosition };
    },
    saveNews(state, { payload: { timeData, relatedScores, sentimentScores, sumScores } }) {
      return { ...state, timeData, relatedScores, sentimentScores, sumScores };
    },
    saveRetCode(state, { payload: { retCode } }) {
      return { ...state, retCode };
    },
  },
};
