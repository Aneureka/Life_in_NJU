/**
 * Created by wyj on 2017/6/1.
 * description:
 */
import request from '../utils/request';

/**
 * 用户注册
 * @param user
 * @returns {Object}
 */
export function create(user) {
  return request('/api/user/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(user),
  });
}

export function changePassword(newUser, token) {
  return request('/api/user/changePassword', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify(newUser),
  });
}

export function forgetPassword(newUser) {
  return request('/api/user/forgetPassword', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      // Authorization: token,
    },
    body: JSON.stringify(newUser),
  });
}

/**
 * 用户登录
 * @param user
 * @returns {Object}
 */
export function signin(user) {
  return request('/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(user),
  });
}


export function addSelfSelectsdStock({ code, token }) {
  return request(`/api/user/watchlist/additem?code=${code}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

export function deleteSelfStock({ code, token }) {
  return request(`/api/user/watchlist/removeitem?code=${code}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

export function fetchSelfStockList(token) {
  return request('/api/user/watchlist/getlist', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}
export function fetchSelfStockCodeList(token) {
  const data = request('/api/user/watchlist/getlist', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
  const selfStockCodeList = [];
  const p = Promise.resolve(data);
  return p.then((v) => {
    const list = v.data;
    for (let i = 0; i < list.length; i += 1) {
      selfStockCodeList.push(list[i].code);
    }
    return selfStockCodeList;
  });
}

export function fetchUserInfo(token) {
  return request('/api/user/getUserInfo', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}
