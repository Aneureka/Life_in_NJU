/**
 * Created by wyj on 2017/6/14.
 * description:
 */
import request from '../utils/request';

export function createArticle(article, token) {
  return request('/api/user/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify(article),
  });
}

export function findArticleById(id) {
  return request(`/api/findArticleById?aid=${id}`);
}

export function findArticleAll() {
  return request('/api/findAll');
}
