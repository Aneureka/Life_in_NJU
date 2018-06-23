/**
 * Created by wyj on 2017/5/17.
 * description:
 */
import request from '../utils/request';

export function fetchListData() {
  const promiseData = request('/api/stock/pankou/SZ000001');
  // console.log(promiseData);
  const buyList = [];
  const sellList = [];
  const p = Promise.resolve(promiseData);
  return p.then((v) => {
    const allData = v.data;
    buyList.push({
      key: '1',
      type: '买1',
      price: allData.bp1,
      amount: allData.bc1,
    }, {
      key: '2',
      type: '买2',
      price: allData.bp2,
      amount: allData.bc2,
    }, {
      key: '3',
      type: '买3',
      price: allData.bp3,
      amount: allData.bc3,
    }, {
      key: '4',
      type: '买4',
      price: allData.bp4,
      amount: allData.bc4,
    }, {
      key: '5',
      type: '买5',
      price: allData.bp5,
      amount: allData.bc5,
    });

    sellList.push({
      key: '1',
      type: '卖1',
      price: allData.sp1,
      amount: allData.sc1,
    }, {
      key: '2',
      type: '卖2',
      price: allData.sp2,
      amount: allData.sc2,
    }, {
      key: '3',
      type: '卖3',
      price: allData.sp3,
      amount: allData.sc3,
    }, {
      key: '4',
      type: '卖4',
      price: allData.sp4,
      amount: allData.sc4,
    }, {
      key: '5',
      type: '卖5',
      price: allData.sp5,
      amount: allData.sc5,
    });
    return { buyData: buyList, sellData: sellList, diff: allData.diff, ratio: allData.ratio };
  });
}
