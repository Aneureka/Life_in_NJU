/**
 * Created by Hitigerzzz on 2017/5/17.
 */
export function timeToDate(time) {
  const date = new Date(time);
  const Y = `${date.getFullYear()}-`;
  const M = `${date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1}-`;
  const D = `${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()}`;
  return Y + M + D;
}

export function timeToTime(time) {
  const date = new Date(time);
  const h = `${date.getHours()}`;
  const m = `${date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes()}`;
  // const s = date.getSeconds();
  return [h, m].join(':');
}

export function timeToDateTime(time) {
  const date = new Date(time);
  const Y = `${date.getFullYear()}-`;
  const M = `${date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1}-`;
  const D = `${date.getDate()} `;
  const h = `${date.getHours()}`;
  const m = `${date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes()}`;
  // const s = date.getSeconds();
  return Y + M + D + [h, m].join(':');
}

export function timeToDateTimeSecond(time) {
  const date = new Date(time);
  const Y = `${date.getFullYear()}-`;
  const M = `${date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1}-`;
  const D = `${date.getDate()} `;
  const h = `${date.getHours() < 10 ? `0${date.getHours()}` : date.getHours()}`;
  const m = `${date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes()}`;
  const s = `${date.getSeconds() < 10 ? `0${date.getSeconds()}` : date.getSeconds()}`;
  return Y + M + D + [h, m, s].join(':');
}

export function DateToYYYYMMDD(time) {
  const date = new Date(time);
  const Y = `${date.getFullYear()}`;
  const M = `${date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1}`;
  const D = `${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()}`;
  return Y + M + D;
}

export function DatesToYYYYMMDDWithBar(dates) {
  const data = [];
  const length = dates.length;
  for (let i = 0; i < length; i += 1) {
    data.push(timeToDate(dates[i]));
  }
  return data;
}
