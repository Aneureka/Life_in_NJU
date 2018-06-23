/**
 * Created by wyj on 2017/6/3.
 * description:
 */
import { message } from 'antd';
import request from '../utils/request';
import * as TimeFormatter from '../utils/timeformatter';
import * as Encrypt from '../utils/encrypt';

/**
 * 新建策略
 * @param name
 * @param user
 * @param token
 * @returns {Object}
 */
export function createStrategy(name, user, token) {
  const time = Date.now();
  const algoId = name + time;
  const data = {
    algoId: Encrypt.toHash(algoId),
    algoName: name,
    username: user.username,
    time: TimeFormatter.timeToDateTimeSecond(time),
  };
  const received = request('/api/algorithm/createDefault', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify(data),
  });
  const p = Promise.resolve(received);
  return p.then((v) => {
    if (v.data === 1) {
      message.success('添加成功');
    } else {
      message.error('添加失败');
    }
  });
}

/**
 * 获得某个用户的所有策略
 * @param username
 * @param token
 * @returns {Object}
 */
export function finStrategyByUsername(username, token) {
  return request(`/api/algorithm/findAlgorithmByUsername?username=${username}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

/**
 * 获取某个策略的内容
 * @param id
 * @param token
 * @returns {Object}
 */
export function fetchStrategyById(id, token) {
  return request(`/api/algorithm/findAlgorithmById?algoId=${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

export function removeStrategy(id, token) {
  const received = request(`/api/algorithm/remove?algoId=${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

export function updateStrategy(strategy, token) {
  const received = request('/api/algorithm/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify(strategy),
  });
  const p = Promise.resolve(received);
  return p.then((v) => {
    if (v.data === 1) {
      message.success('保存成功');
    } else {
      message.error('保存失败');
    }
  });
}

export function fetchBackTestHistory(id, token) {
  const data = request(`/api/backtest/history?algoId=${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
  return data;
}

export function fetchLatestHistory(id, token) {
  return request(`/api/backtest/last_history?algoId=${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

export function fetchHistoryNum(id, token) {
  return request(`/api/backtest/hisNum?algoId=${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  });
}

export function fetchStrategyResult(id, token) {
  const data = request(`/api/backtest/fortest?algoId=${id}`);
  const promise = Promise.resolve(data);
  return promise.then((v) => {
    const result = v.data;
    if (result.retCode === 1) {
      const sumData = getSumData(result.summary); // 策略概况
      const standardData = result.summary.benchmark_daily_total_returns; // 基准收益
      const myData = result.summary.daily_total_returns; // 策略收益
      const date = TimeFormatter.DatesToYYYYMMDDWithBar(result.summary.date_times); // 收益日期
      const profit = result.summary.daily_pnls; // 每日盈亏
      const tradeDates = result.summary.trade_dates; // 买卖日期
      const buys = handleBuyAndSell(date, tradeDates, result.summary.buys); // 当日买入
      const sells = handleBuyAndSell(date, tradeDates, result.summary.sells);// 当日卖出
      const position = getStockPosition(result.stock_position); // 持仓记录
      const transfer = getTransferData(result.trade_details); // 调仓记录
      const stockAccount = getStockAccount(result.stock_account); // 账户信息
      // // 无比较的
      const alpha = getRadioData(result.alphas);
      const beta = getRadioData(result.betas);
      const sharpe = getRadioData(result.sharp_ratios);
      const informationRadio = getRadioData(result.information_ratios);
      const back = getRadioData(result.max_drawdowns);
      // // 有比较的
      const profitRadio =
        getCompareData(result.returns.benchmarkReturns, result.returns.strategyReturns);
      const volatility =
        getCompareData(result.volatilitys.benchmarkReturns, result.volatilitys.strategyReturns);
      return {
        result,
        sumData,
        standardData,
        myData,
        date,
        profit,
        buys,
        sells,
        position,
        transfer,
        stockAccount,
        alpha,
        beta,
        sharpe,
        informationRadio,
        back,
        profitRadio,
        volatility };
    } else {
      const err = 1;
      return err;
    }
  });
}

/**
 * 获得策略概述
 * @param summary
 * @returns
 */
function getSumData(summary) {
  const sum = {
    startDate: summary.start_date,
    endDate: summary.end_date,
    sProfit: `${(summary.total_returns * 100).toFixed(3)}%`,
    sYearProfit: `${(summary.annualized_returns * 100).toFixed(3)}%`,
    bProfit: `${(summary.benchmark_total_returns * 100).toFixed(3)}%`,
    bYearProfit: `${(summary.benchmark_annualized_returns * 100).toFixed(3)}%`,
    alpha: summary.alpha,
    beta: summary.beta,
    sharpe: summary.sharpe,
    sortino: summary.sortino,
    informationRadio: summary.information_ratio,
    volatility: summary.volatility,
    back: `${(summary.max_drawdown * 100).toFixed(3)}%`,
    trackingError: summary.tracking_error,
    downsideRisk: summary.downside_risk,
  };
  return sum;
}

/**
 * 获得持仓记录
 * @param data
 * @returns {{data: Array, headData: Array}}
 */
function getStockPosition(posiData) {
  const length = posiData.length;
  const data = [];
  const headData = [];
  for (let i = 0; i < length; i += 1) {
    headData.push({
      key: i,
      date: posiData[i].date,
      code: '',
      close: '',
      position: '',
      average: '',
      value: '',
    });

    const dataItem = [];
    const positions = posiData[i].daily_positions;
    const nums = positions.length;
    for (let j = 0; j < nums; j += 1) {
      const item = positions[j];
      dataItem.push({
        key: j,
        date: item.date_time,
        code: item.symbol,
        close: item.last_price,
        position: item.quantity,
        average: item.avg_price,
        value: item.market_value,
      });
    }
    data.push(dataItem);
  }
  return { data, headData };
}

/**
 * 获得交易详情
 * @returns {{data: Array, headData: Array}}
 */
export function getTransferData(tradeData) {
  const length = tradeData.length;
  const data = [];
  const headData = [];
  for (let i = 1; i < length; i += 1) {
    headData.push({
      key: i,
      date: tradeData[i].date,
      code: `(${tradeData[i].trade_num}交易)`,
      buy: '',
      volume: '',
      price: '',
      cost: '',
    });

    const dataItem = [];
    const records = tradeData[i].trade_records;
    const nums = records.length;
    for (let j = 0; j < nums; j += 1) {
      const item = records[j];
      const action = item.side === 'SELL' ? '卖出' : '买进';
      dataItem.push({
        key: j,
        date: item.trade_date_time,
        code: item.symbol,
        buy: action,
        volume: item.last_quantity,
        price: item.last_price,
        cost: item.transaction_cost,
      });
    }
    data.push(dataItem);
  }
  return { data, headData };
}

/**
 * 获得账户信息
 * @param accountData
 */
export function getStockAccount(accountData) {
  const data = [];
  const length = accountData.length;
  for (let i = 0; i < length; i += 1) {
    const item = accountData[i];
    data.push({
      date: item.date,
      cash: item.cash.toFixed(2),
      totalValue: item.total_value.toFixed(2),
      marketValue: item.market_value.toFixed(1),
      cost: item.transaction_cost.toFixed(2),
      profit: item.total_pnls.toFixed(2),
    });
  }
  return data;
}

/**
 * 处理买卖时间
 * @param dates
 * @param tradeDates
 * @param source
 * @returns {Array}
 */
function handleBuyAndSell(dates, tradeDates, source) {
  const length = dates.length;
  const tradeLength = tradeDates.length;
  const target = [];
  let sameNum = 0;
  for (let i = 0; i < length; i += 1) {
    if (sameNum < tradeLength) {
      if (dates[i] === tradeDates[sameNum]) {
        target.push(source[sameNum]);
        sameNum += 1;
      } else {
        target.push(0);
      }
    } else {
      target.push(0);
    }
  }
  return target;
}

/**
 * 获得没有比较收益策略的列表数据
 * @param data
 * @returns {*}
 */
function getRadioData(data) {
  const length = data.length;
  const result = [];
  for (let i = 0; i < length; i += 1) {
    const item = data[i];
    result.push({
      key: i,
      month: item.date,
      one: (item.one_month * 1).toFixed(4),
      three: (item.three_months * 1).toFixed(4),
      six: (item.six_months * 1).toFixed(4),
      twelve: (item.twelve_months * 1).toFixed(4),
    });
  }
  return result;
}

/**
 * @param data
 * @returns {Array}
 */
function getCompareData(baseData, straData) {
  const length = baseData.length;
  const result = [];
  for (let i = 0; i < length; i += 1) {
    const baseItem = baseData[i];
    const straItem = straData[i];
    result.push({
      key: i,
      month: baseItem.date,
      base1: (baseItem.one_month * 1).toFixed(4),
      strategy1: (straItem.one_month * 1).toFixed(4),
      base3: (baseItem.three_months * 1).toFixed(4),
      strategy3: (straItem.three_months * 1).toFixed(4),
      base6: (baseItem.six_months * 1).toFixed(4),
      strategy6: (straItem.six_months * 1).toFixed(4),
      base12: (baseItem.twelve_months * 1).toFixed(4),
      strategy12: (straItem.twelve_months * 1).toFixed(4),
    });
  }
  return result;
}

export function downloadStrategy(result) {
  request('/api/backtest/downloadResult', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(result),
  });
}
