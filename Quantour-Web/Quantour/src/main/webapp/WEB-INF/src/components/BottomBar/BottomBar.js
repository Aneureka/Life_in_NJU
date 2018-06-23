/**
 * Created by wyj on 2017/5/10.
 */
import React from 'react';
import { Icon } from 'antd';
import { Link } from 'dva/router';
import styles from './BottomBar.less';

function BottomBar() {
  return (
    <div className={styles.element}>
      <a className={styles.link} href="https://github.com/Leftovers4">关于我们<Icon type="github" /></a>
      <p>Copyright &copy; 2017 Leftovers</p>
    </div>
  );
}

export default BottomBar;
