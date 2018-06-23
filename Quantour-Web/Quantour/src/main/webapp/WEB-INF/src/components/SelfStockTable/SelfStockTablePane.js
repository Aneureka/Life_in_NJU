/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import { Row, Col } from 'antd';
import SelfStockTable from './SelfStockTable';
import styles from './SelfStockTablePane.less';

class SelfStockTablePane extends React.Component {
  render() {
    return (
      <div className={styles.pane_back}>
        <SelfStockTable />
      </div>
    );
  }
}

export default SelfStockTablePane;
