/**
 * Created by wyj on 2017/5/25.
 * description:
 */
import React from 'react';
import styles from './NewsCell.less';

function NewsCell({ data }) {
  return (
    <div className={styles.news_container}>
      <div className={styles.news_title}><a href={data.link}>{data.title}</a></div>
      <div className={styles.news_time}>{data.time}</div>
      <p>{data.text}</p>
    </div>
  );
}

export default NewsCell;
