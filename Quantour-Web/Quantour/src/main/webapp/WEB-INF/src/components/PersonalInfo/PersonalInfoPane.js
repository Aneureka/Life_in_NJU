/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import styles from './PersonalInfoPane.less';

class PersonalInfoPane extends React.Component {
  render() {
    return (
      <div>
        <table className={styles.person_table}>
          <tbody>
            <tr>
              <th>用户名:</th>
              <td>{this.props.data.username}</td>
            </tr>
            <tr>
              <th>邮箱:</th>
              <td>{this.props.data.emailAddress}</td>
            </tr>
            <tr>
              <th>手机号:</th>
              <td>{this.props.data.phoneNumber}</td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

export default PersonalInfoPane;
