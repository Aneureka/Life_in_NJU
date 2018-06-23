/**
 * Created by wyj on 2017/6/11.
 * description:
 */

export function toHash(input) {
  const CHAR_TABLE = `ABCDEFGHIJKLMNOPQRSTUV${
    'WXYZqwertyuiopasdfghjklzxcvbnm1092837465'.split('')}`;
  let hash = 6483;
  let i = input.length - 1;
  if (typeof input === 'string') {
    for (; i > -1; i -= 1) {
      hash += (hash << 5) + input.charCodeAt(i);
    }
  } else {
    for (; i > -1; i -= 1) {
      hash += (hash << 5) + input[i];
    }
  }
  let value = hash & 0x7FFFFFFF;
  let retVal = '';
  do {
    retVal += CHAR_TABLE[value & 0x3F];
  } while (value >>= 1);

  retVal = retVal.split(',').join('');
  return retVal;
}
