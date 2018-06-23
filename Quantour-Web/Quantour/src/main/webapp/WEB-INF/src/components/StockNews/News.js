/**
 * Created by Hitigerzzz on 2017/5/16.
 */
import React from 'react';
import { connect } from 'dva';
import { Timeline, Pagination } from 'antd';
import NewsCell from './NewsCell';
import styles from './News.less';

function News({ dispatch, code: codeID, newsList, total, current }) {
  function pageChangeHandler(page) {
    const currentPage = page;
    dispatch({
      type: 'stockBasic/fetchNews',
      payload: { code: codeID, page: currentPage },
    });
  }
  return (
    <div className={styles.timeline}>
      <Timeline>
        {
          newsList.map((v) => {
            return (
              <Timeline.Item key={v.id}>
                <NewsCell data={v} />
              </Timeline.Item>
            );
          })
        }
      </Timeline>
      <Pagination
        className="ant-table-pagination"
        total={total}
        current={current}
        pageSize={10}
        onChange={pageChangeHandler}
      />
    </div>
  );
}

export default connect()(News);
