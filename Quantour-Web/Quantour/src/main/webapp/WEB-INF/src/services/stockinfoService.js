/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import { message } from 'antd';
import request from '../utils/request';
import * as TimeFormatter from '../utils/timeformatter';
import * as parseUtil from '../utils/parsedata';

let ipFlag = 0;
/**
 * 获得分时图数据
 * @param code
 * @returns {Promise.<TResult>}
 */
export function fetchTimeData({ code }) {
  const data = request(`/api/stock/chartmin/${code}`);
  const p = Promise.resolve(data);
  return p.then((v) => {
    const chartlist = v.data.chartlist;
    const timeData = [];
    const presentData = [];
    const maData = [];
    const volumeData = [];
    for (let i = 0, len = chartlist.length; i < len; i += 1) {
      const item = chartlist[i];
      timeData.push(TimeFormatter.timeToTime(item.time));
      presentData.push(item.current);
      maData.push(item.avg_price);
      volumeData.push(item.volume);
    }
    const series = getOneStockSeries(presentData, maData, volumeData);
    return { timeData, series };
  });
}


export function fetchCompareTimeData(code) {
  const data = request(`/api/stock/chartmin/${code}`);
  const p = Promise.resolve(data);
  return p.then((v) => {
    const chartlist = v.data.chartlist;
    const presentData = [];
    for (let i = 0, len = chartlist.length; i < len; i += 1) {
      const item = chartlist[i];
      presentData.push(item.current);
    }
    return presentData;
  });
}

export function handleStockSearch({ value }) {
  const data = request(`/api/search?input=${value}`);
  const p = Promise.resolve(data);
  const result = [];
  return p.then((v) => {
    if (!v.err) {
      const solved = v.data.response.docs;
      for (let i = 0; i < solved.length; i += 1) {
        result.push(solved[i]);
      }
    }
    return result;
  });
}

function getOneStockSeries(presentData, maData, volumeData) {
  // 数据线
  const series = [
    {
      name: '现价',
      type: 'line',
      symbol: 'none',
      smooth: true,
      data: presentData,
      itemStyle: {
        normal: {
          color: '#3db8c1',
        },
      },
      lineStyle: {
        normal: {
          color: '#3db8c1',
          width: 1,
        },
      },
      areaStyle: {
        normal: {
          color: '#E3FDFD',
        },
      },
    },
    {
      name: '均价',
      type: 'line',
      // showSymbol: false,
      // hoverAnimation: true,
      symbol: 'none',
      smooth: true,
      data: maData,
      itemStyle: {
        normal: {
          color: '#FCE38A',
        },
      },
      lineStyle: {
        normal: {
          color: '#FCE38A',
          width: 1,
        },
      },
    },
    {
      name: '成交量',
      type: 'bar',
      xAxisIndex: 1,
      yAxisIndex: 1,
      data: volumeData,
      itemStyle: {
        normal: {
          color: '#3db8c1',
        },
      },
    },
  ];
  return series;
}

export function setCompareStockSeries({ series, name: stockName, presentData }) {
  series.push(
    {
      name: stockName,
      type: 'line',
      symbol: 'none',
      smooth: true,
      data: getChange(presentData),
      lineStyle: {
        normal: series.length === 0 ? {
          width: 1,
          color: '#3db8c1',
        } : { width: 1 },
      },
      itemStyle: {
        normal: series.length === 0 ? {
          color: '#3db8c1',
        } : 'default',
      },
      areaStyle: null,
    },
  );

  function getChange(data) {
    const length = data.length;
    const change = [];
    if (length > 0) {
      const base = data[0];
      for (let i = 0; i < length; i += 1) {
        const temp = (data[i] - base) / base;
        change.push((temp * 100).toFixed(2));
      }
    }
    return change;
  }
}
/**
 * K 线图数据
 * @param code
 * @param period
 * @param type
 * @returns {Promise.<TResult>}
 */
export function fetchCandleData({ code, period, type }) {
  const data = request(`/api/stock/chartallk/${code}?period=${period}&type=${type}`);
  const p = Promise.resolve(data);
  return p.then((v) => {
    const chartlist = v.data.chartlist;
    const categoryData = [];
    const values = [];
    const volumes = [];
    const changes = [];
    const ma5 = [];
    const ma10 = [];
    const ma20 = [];
    const ma30 = [];
    for (let i = 0, len = chartlist.length; i < len; i += 1) {
      const item = chartlist[i];
      categoryData.push(TimeFormatter.timeToDate(item.time));
      const temp = [item.open, item.close, item.low, item.high];
      values.push(temp);
      volumes.push(item.volume);
      ma5.push(item.ma5);
      ma10.push(item.ma10);
      ma20.push(item.ma20);
      ma30.push(item.ma30);
      changes.push(item.chg);
    }
    const candleData = { category: categoryData, value: values, volume: volumes };
    const datalists = { maf: ma5, mat: ma10, matt: ma20, mattt: ma30 };
    return { candle_data: candleData, ma_data: datalists, change_data: changes };
  });
  // // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
}

export function getHotRankStock() {
  const hotStockList = [];
  const hotStock = request('/api/market/hot_rank?size=10');
  const p = Promise.resolve(hotStock);
  return p.then((v) => {
    const dataInfo = v.data.ranks;
    if (dataInfo) {
      for (let j = 0; j < dataInfo.length; j += 1) {
        let tcloseVal = '';
        let pchgVal = '';
        if (!dataInfo[j]) {
          tcloseVal = '-';
          pchgVal = '-';
        } else {
          tcloseVal = dataInfo[j].quote_current;
          pchgVal = dataInfo[j].quote_percentage;
        }
        hotStockList.push({
          key: j,
          code: dataInfo[j].code,
          name: dataInfo[j].name,
          close: tcloseVal,
          pch: pchgVal,
        });
      }
    }
    return { hotStockData: hotStockList };
  });
}

export function getLongHuStock() {
  const longStockList = [];
  const longStock = request(`/api/market/longhubang?date=${getDate()}`);
  const p = Promise.resolve(longStock);
  return p.then((v) => {
    const dataInfo = v.data.list;
    if (dataInfo) {
      for (let j = 0; j < dataInfo.length; j += 1) {
        let tcloseVal = '';
        let pchgVal = '';
        if (!dataInfo[j].tqQtSkdailyprice) {
          tcloseVal = '-';
          pchgVal = '-';
        } else {
          tcloseVal = dataInfo[j].tqQtSkdailyprice.tclose;
          tcloseVal = tcloseVal.toFixed(2);
          let temp = dataInfo[j].tqQtSkdailyprice.pchg;
          temp = temp.toFixed(2);
          pchgVal = temp;
        }
        longStockList.push({
          key: j,
          code: dataInfo[j].symbol,
          name: dataInfo[j].name,
          close: tcloseVal,
          pch: pchgVal,
        });
      }
    }
    return { longStockData: longStockList };
  });
}

function getDate() {
  const today = Date.now();
  return timetrans(today);
}

function timetrans(timestamp) {
  const datee = new Date(timestamp - 86400000);// 如果date为10位不需要乘1000
  const Y = `${datee.getFullYear()}`;
  const M = `${datee.getMonth() + 1 < 10 ? `0${datee.getMonth() + 1}` : datee.getMonth() + 1}`;
  const D = `${datee.getDate() < 10 ? `0${datee.getDate()}` : datee.getDate()}`;
  return Y + M + D;
}


export function getStockBasic(code) {
  return request(`/api/stock/quote/${code}`);
}
/**
 * 获得个股新闻
 */
export function getStockNews({ code, count, page }) {
  const data = request(`/api/stock/news/${code}?count=${count}&page=${page}`);
  const p = Promise.resolve(data);
  return p.then((v) => {
    const total = v.data.maxPage * count;
    const dataList = v.data.list;
    const newsList = [];
    const length = dataList.length;
    for (let i = 0; i < length; i += 1) {
      const item = dataList[i];
      newsList.push({
        id: item.id,
        time: TimeFormatter.timeToDateTime(item.created_at),
        title: item.title,
        text: parseUtil.delStrLink(item.text),
        link: parseUtil.getStrLink(item.text),
      });
    }
    return { newsList, total };
  });
}

/**
 * 获得个股公告
 */
export function getStockNotice({ code, count, page }) {
  const data = request(`/api/stock/announcement/${code}?count=${count}&page=${page}`);
  const p = Promise.resolve(data);
  return p.then((v) => {
    const total = v.data.maxPage * count;
    const dataList = v.data.list;
    const notices = [];
    const length = dataList.length;
    for (let i = 0; i < length; i += 1) {
      const item = dataList[i];
      notices.push({
        id: item.id,
        time: TimeFormatter.timeToDateTime(item.created_at),
        text: parseUtil.delStrLink(item.text),
        link: parseUtil.getStrLink(item.text),
      });
    }
    return { notices, total };
  });
}

/**
 * 获得公司资料
 * @param code
 * @returns {Promise.<TResult>}
 */
export function getCompanyInfo(code) {
  const data = request(`/api/stock/company/${code}`);
  const p = Promise.resolve(data);

  function getBoardName(list) {
    let boardName = '';
    for (let i = 0; i < list.length; i += 1) {
      const item = list[i];
      if (i === 0) {
        boardName += `${item.keyname}：${item.boardname}\n`;
      } else {
        boardName += `${item.keyname}：${item.boardname}`;
      }
    }
    return boardName;
  }
  return p.then((v) => {
    const info = v.data.tqCompInfo;
    const companyInfo = {
      compname: info.compname,
      engname: info.engname,
      founddate: info.founddate,
      regcapital: info.regcapital,
      chairman: info.chairman,
      manager: info.manager,
      legrep: info.legrep,
      officeaddr: info.officeaddr,
      compurl: info.compurl.split(',')[0],
      compintro: info.compintro,
      bizscope: info.bizscope,
      keyname: getBoardName(info.tqCompBoardmapList),
      level2name: info.tqCompIndustryList.length === 0 ? '-' : info.tqCompIndustryList[0].level2name,
    };
    return companyInfo;
  });
}

/**
 * 获得上证等指数
 * @param code
 * @returns {Promise.<TResult>}
 */
export function fetchExponentData(code) {
  const basicData = getStockBasic(code);
  const promiseData = request(`/api/stock/chartmin/${code}`);
  const p = Promise.resolve(promiseData);
  return p.then((v) => {
    if (v.err) {
      if (ipFlag === 0) {
        ipBanError();
      }
      const exponentData = { value: [], category: [] };
      return { exponentData, basicData };
    } else {
      ipFlag = 0;
      const exponentDataX = [];
      const exponentDataY = [];
      const dataList = v.data.chartlist;
      for (let i = 0; i < dataList.length; i += 1) {
        exponentDataX.push(TimeFormatter.timeToTime(dataList[i].time));
        exponentDataY.push(dataList[i].current);
      }
      const exponentData = { value: exponentDataY, category: exponentDataX };
      return { exponentData, basicData };
    }
  });
}

/**
 * 获得股票预测价格
 * @param code
 * @param num
 * @returns {Object}
 */
export function fetchForecastData({ code, num }) {
  // const prefix = code.match(/[A-Z]+/ig);
  // const suffix = code.match(/\d+/ig);
  // return request(`/api/stock/NP1Price/${suffix}.${prefix}?num=${num}`);
  return request(`/api/stock/NP1Price/${code}?num=${num}`);
}

/**
 * 新闻情感指数
 * @param code
 * @param beginDate
 * @param endDate
 * @returns {Object}
 */
export function fetchNewsScore({ code, beginDate, endDate }) {
// http://localhost:8080/api/stock/newsAndScore/SZ000001?beginDate=20170601&endDate=20170607
  return request(`/api/stock/newsAndScore/${code}?beginDate=${beginDate}&endDate=${endDate}`);
}
export function fetchNewsSentimentScore({ code, beginDate, endDate }) {
  return request(`/api/stock/newsSentimentScore/${code}?beginDate=${beginDate}&endDate=${endDate}`);
}
/**
 * 获取股票列表分时图
 * @param board
 * @param page
 * @param sort
 * @returns {Promise.<TResult>}
 */
export function fetchTableTimeList({ board, page, sort }) {
  const data = request(`/api/market/stock_list_page?board=${board}&page=${page}&sort=${sort}`);
  const pro = Promise.resolve(data);
  const list = [];
  return pro.then((v) => {
    const stockData = v.data.content;
    for (let i = 0; i < stockData.length; i += 1) {
      list.push(getTimeData(stockData[i].code));
    }
    return list;
  });
}
function getTimeData(code) {
  const dataSource = request(`/api/stock/chartmin/${code}`);
  const p = Promise.resolve(dataSource);
  return p.then((v) => {
    if (v.err) {
      if (ipFlag === 0) {
        ipBanError();
      }
      const dataList = { timeData: [], presentData: [] };
      return { data: dataList };
    } else {
      ipFlag = 0; // ip 报错置0；
      const chartlist = v.data.chartlist;
      const timeList = [];
      const presentList = [];
      for (let j = 0, len = chartlist.length; j < len; j += 1) {
        const item = chartlist[j];
        timeList.push(TimeFormatter.timeToTime(item.time));
        presentList.push(item.current);
      }
      const dataList = { timeData: timeList, presentData: presentList };
      return { data: dataList };
    }
  });
}

function ipBanError() {
  message.error('您的请求过于频繁，请稍后再试', 3);
  ipFlag += 1;
}

/**
 * 把用户登录需要的用户信息传给 disqus
 */
export function getCommentAuth(token) {
  return request('/api/comment/get_remote_auth_s3', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}
