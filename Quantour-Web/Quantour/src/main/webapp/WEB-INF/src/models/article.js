/**
 * Created by wyj on 2017/6/15.
 * description:
 */
import { routerRedux } from 'dva/router';
import * as articleService from '../services/articleService';

export default {
  namespace: 'article',
  state: {
    article: [],
    articleList: [],
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname, query }) => {
        if (pathname === '/community') {
          dispatch({ type: 'fetchAllArticle', payload: query });
        } else if (pathname === '/post') {
          dispatch({ type: 'fetchArticleById', payload: query });
        }
      });
    },
  },

  effects: {
    * createArticle({ payload: article }, { call, put }) {
      const sessionStorage = window.sessionStorage;
      const token = sessionStorage.getItem('userToken');
      const username = sessionStorage.getItem('username');
      if (token && token.length > 0) {
        const newArticle = {
          title: article.title,
          content: article.content,
          time: article.time,
          username,
          readNum: 0,
        };
        yield call(articleService.createArticle, newArticle, token);
        yield put({
          type: 'saveNewArticle',
        });
        yield put(routerRedux.push('/community'));
      }
    },
    * fetchArticleById({ payload: id }, { call, put }) {
      const data = yield call(articleService.findArticleById, id.aid);
      yield put({
        type: 'saveArticleById',
        payload: {
          article: data,
        },
      });
    },
    * fetchAllArticle({ payload }, { call, put }) {
      const data = yield call(articleService.findArticleAll);
      yield put({
        type: 'saveAllArticle',
        payload: {
          articleList: data,
        },
      });
    },
  },

  reducers: {
    saveNewArticle(state) {
      return { ...state };
    },
    saveArticleById(state, { payload: { article } }) {
      return { ...state, article };
    },
    saveAllArticle(state, { payload: { articleList } }) {
      return { ...state, articleList };
    },
  },
};
