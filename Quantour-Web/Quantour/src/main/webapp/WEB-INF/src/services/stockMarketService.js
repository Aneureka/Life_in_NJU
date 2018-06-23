/**
 * Created by wyj on 2017/5/16.
 * description:
 */
import request from '../utils/request';

export function fetchListData(board) {
  const promiseData = request(`/api/market/stock_list?board=${board}`);
  const list = [];
  const timeData = [];
  const p = Promise.resolve(promiseData);
  return p.then((v) => {
    const stockData = v.data;
    for (let i = 0; i < stockData.length; i += 1) {
      list.push({
        code: stockData[i].code,
        name: stockData[i].name,
        curPrice: stockData[i].curPrice.toFixed(2),
        changePercent: (stockData[i].changePercent * 100).toFixed(2),
        change: stockData[i].change.toFixed(2),
        turnRate: (stockData[i].turnRate * 100).toFixed(2),
        volRatio: stockData[i].volRatio.toFixed(4),
        swing: (stockData[i].swing * 100).toFixed(2),
        amount: stockData[i].amount,
        floatMarketCap: stockData[i].floatMarketCap,
        peTTM: stockData[i].peTTM.toFixed(4),
      });
    }
    return { data: list, timeList: timeData };
  });
}

export function newFetchListData(board, page, sort, order) {
  const data = request(`/api/market/stocklist_page?board=${board}&page=${page}&order=${order}&orderby=${sort}`);
  // console.log(data);
  const list = [];
  const timeData = [];
  if (board === 'a') {
    const p = Promise.resolve(data);
    return p.then((v) => {
      const stockData = v.data.stocks;
      for (let i = 0; i < stockData.length; i += 1) {
        list.push({
          code: stockData[i].symbol,
          name: stockData[i].name,
          current: stockData[i].current,
          percent: stockData[i].percent,
          change: stockData[i].change,
          // 当日股价幅度
          high: stockData[i].high - stockData[i].low,
          // 52周股价幅度
          high52w: stockData[i].high52w - stockData[i].low52w,
          // 成交量
          volume: stockData[i].volume,
          amount: stockData[i].amount,
          marketcapital: stockData[i].marketcapital,
          peTTM: stockData[i].pettm,
        });
      }
      return { data: list, timeList: timeData };
    });
  } else {
    const p = Promise.resolve(data);
    return p.then((v) => {
      const stockData = v.data;
      for (let i = 0; i < stockData.length; i += 1) {
        list.push({
          code: stockData[i][0],
          name: stockData[i][1],
          current: stockData[i][2],
          percent: stockData[i][4],
          change: stockData[i][3],
          // 当日股价幅度
          high: stockData[i][7] - stockData[i][8],
          // 52周股价幅度
          high52w: stockData[i][13] - stockData[i][14],
          // 成交量
          volume: stockData[i][9],
          amount: stockData[i][10],
          marketcapital: stockData[i][11],
          peTTM: stockData[i][12],
        });
      }
      return { data: list, timeList: timeData };
    });
  }
}

export function getMarketNews(page) {
  const promise = request(`/api/market/news?page=${page}`);
  const p = Promise.resolve(promise);
  return p.then((v) => {
    const data = v.data;
    const pagebean = data.showapi_res_body.pagebean;
    const allNum = pagebean.allNum;
    const contentList = pagebean.contentlist;
    const length = contentList.length;
    const newsList = [];
    for (let i = 0; i < length; i += 1) {
      const item = contentList[i];
      newsList.push({
        id: `${item.id}${i}`,
        pubDate: item.pubDate,
        title: item.title,
        channelName: item.channelName,
        desc: item.desc,
        source: item.source,
        link: item.link,
      });
    }
    return { newsList, allNum };
  });
}
