/**
 * Created by wyj on 2017/5/25.
 * description:
 */
import React from 'react';
import styles from './MarketNewsCell.less';

function MarketNewsCell({ data }) {
  return (
    <div className={styles.news_container}>
      <div className={styles.news_title}><a href={data.link}>{data.title}</a></div>
      <div className={styles.news_time}>{data.pubDate}</div>
      <p className={styles.desc}>{data.desc}</p>
      <p className={styles.source}><span>{data.source}</span><span>{data.channelName}</span></p>
    </div>
  );
}

export default MarketNewsCell;
