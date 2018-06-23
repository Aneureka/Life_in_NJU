/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import AccountSetting from './AccountSetting';
import styles from './AccountSettingPane.less';

class AccountSettingPane extends React.Component {
  render() {
    return (
      <div className={styles.pane_back} style={{ display: this.props.display }}>
        <AccountSetting />
      </div>
    );
  }
}

export default AccountSettingPane;
