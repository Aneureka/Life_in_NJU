/**
 * Created by Hitigerzzz on 2017/5/16.
 */
import * as stockinfoService from '../services/stockinfoService';


export default {
  namespace: 'stockBasic',
  state: {
    code: '',
    basicData: [],
    companyInfo: [],
    newsList: [],
    notices: [],
    newsTotal: null,
    newsCurrent: null,
    noticesTotal: null,
    noticesCurrent: null,
    remote_auth_s3: '',
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/stockinfo') {
          dispatch({ type: 'fetch', payload: query });
          dispatch({ type: 'fetchNews', payload: query });
          dispatch({ type: 'fetchNotices', payload: query });
          dispatch({ type: 'fetchComment', payload: query });
        }
      });
    },
  },

  effects: {
    *fetch({ payload: { code , count=10, page=1 } }, { call, put }) {  // eslint-disable-line
      const { data } = yield call(stockinfoService.getStockBasic, code);
      const companyInfo = yield call(stockinfoService.getCompanyInfo, code); // 公司信息
      yield put({
        type: 'save',
        payload: {
          code,
          data,
          companyInfo,
        },
      });
    },
    *fetchNews({ payload: { code , count=10, page=1 } }, { call, put }) {  // eslint-disable-line
      const newsData = yield call(stockinfoService.getStockNews, { code, count, page }); // 个股新闻
      yield put({
        type: 'saveNews',
        payload: {
          newsList: newsData.newsList,
          newsTotal: newsData.total,
          newsCurrent: page,
        },
      });
    },
    *fetchNotices({ payload: { code , count=10, page=1 } }, { call, put }) {  // eslint-disable-line
      const noticesData =
        yield call(stockinfoService.getStockNotice, { code, count, page }); // 个股公告
      yield put({
        type: 'saveNotices',
        payload: {
          notices: noticesData.notices,
          noticesTotal: noticesData.total,
          noticesCurrent: page,
        },
      });
    },
    *fetchComment({ payload }, { call, put }) {  // eslint-disable-line
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      if (token && token.length > 0) {
        const CommentAuth =
          yield call(stockinfoService.getCommentAuth, token);
        yield put({
          type: 'saveCommentAuth',
          payload: {
            remote_auth_s3: CommentAuth.data.remote_auth_s3,
          },
        });
      }
    },
  },

  reducers: {
    save(state,
      { payload:
           { code, data: basicData, companyInfo } }) {
      return { ...state, code, basicData, companyInfo };
    },
    saveNews(state, { payload: { newsList, newsTotal, newsCurrent } }) {
      return { ...state, newsList, newsTotal, newsCurrent };
    },
    saveNotices(state, { payload: { notices, noticesTotal, noticesCurrent } }) {
      return { ...state, notices, noticesTotal, noticesCurrent };
    },
    saveCommentAuth(state, { payload: { remote_auth_s3 } }) {
      return { ...state, remote_auth_s3 };
    },
  },

};
