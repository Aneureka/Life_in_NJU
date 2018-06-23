/**
 * Created by Hitigerzzz on 2017/5/30.
 */
export function parsePromiseData(list) {
  const p = Promise.resolve(list);
  return p.then((v) => {
    return v.data;
  });
}

/**
 * 获得文本中的超连接
 * @param str
 * @returns {*}
 */
export function getStrLink(str) {
  const result = str.match(/href="[^\s]*(?=")/ig);
  for (let i = 0; i < result.length; i += 1) {
    result[i] = result[i].replace('href="', '');
    return result[i];
  }
  return str;
}

/**
 * 删除文本中的超连接
 * @param {string}
 * @returns {string}
 */
export function delStrLink(str) {
  const htmlreg = /<a[^>]+>(.*?)<\/a>/gi;
  const result = str.replace(htmlreg, '');
  return result;
}
