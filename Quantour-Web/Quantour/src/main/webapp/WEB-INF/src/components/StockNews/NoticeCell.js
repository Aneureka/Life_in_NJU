/**
 * Created by wyj on 2017/5/25.
 * description:
 */
import React from 'react';
import styles from './NewsCell.less';

function NoticeCell({ data }) {
  return (
    <div className={styles.news_container}>
      <div className={styles.news_title}>{data.text}<a href={data.link}>网页链接...</a></div>
      <div className={styles.news_time}>{data.time}</div>
    </div>
  );
}

export default NoticeCell;
