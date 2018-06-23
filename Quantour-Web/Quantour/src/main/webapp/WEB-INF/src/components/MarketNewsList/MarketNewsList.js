/**
 * Created by wyj on 2017/5/11.
 */
import React from 'react';
import { connect } from 'dva';
import { Timeline, Pagination } from 'antd';
import MarketNewsCell from './MarketNewsCell';
import styles from './MarketNewsList.less';

function MarketNewsList({ dispatch, newsList, allNum, current }) {
  function pageChangeHandler(page) {
    const currentPage = page;
    dispatch({
      type: 'newsList/fetch',
      payload: { page: currentPage },
    });
  }
  return (
    <div className={styles.timeline}>
      <Timeline>
        {
          newsList.map((v) => {
            return (
              <Timeline.Item key={v.id}>
                <MarketNewsCell data={v} />
              </Timeline.Item>
            );
          })
        }
      </Timeline>
      <Pagination
        className="ant-table-pagination"
        total={allNum}
        current={current}
        pageSize={20}
        onChange={pageChangeHandler}
      />
    </div>
  );
}

function mapStateToProps(state) {
  const { newsList, allNum, current } = state.newsList;
  return { newsList, allNum, current };
}

export default connect(mapStateToProps)(MarketNewsList);
