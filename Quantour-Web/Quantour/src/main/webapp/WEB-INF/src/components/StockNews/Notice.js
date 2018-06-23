/**
 * Created by Hitigerzzz on 2017/5/16.
 */
import React from 'react';
import { connect } from 'dva';
import { Timeline, Pagination } from 'antd';
import NoticeCell from './NoticeCell';
import styles from './News.less';

function Notice({ dispatch, code: codeID, notices, total, current }) {
  function pageChangeHandler(page) {
    const currentPage = page;
    dispatch({
      type: 'stockBasic/fetchNotices',
      payload: { code: codeID, page: currentPage },
    });
  }
  if (notices.length === 0) {
    return <div className={styles.no_notices}>暂无相关公告</div>;
  } else {
    return (
      <div className={styles.timeline}>
        <Timeline>
          {
            notices.map((v) => {
              return (
                <Timeline.Item key={v.id}>
                  <NoticeCell data={v} />
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
}


export default connect()(Notice);
