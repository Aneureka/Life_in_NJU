/**
 * Created by wyj on 2017/5/29.
 * description:
 */
import React from 'react';
import { connect } from 'dva';
import styles from './CompanyInfo.less';

class CompanyInfo extends React.Component {
  state = {
    introLong: '',
    scopeLong: '',
    isLongIntro: '',
    isLongScope: '',
    showMoreIntro: '',
    showMoreScope: '',
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.companyInfo !== undefined) {
      this.setState({
        introLong: nextProps.companyInfo.compintro.length > 100,
        scopeLong: nextProps.companyInfo.bizscope.length > 100,
        isLongIntro: nextProps.companyInfo.compintro.length > 100,
        isLongScope: nextProps.companyInfo.bizscope.length > 100,
        showMoreIntro: nextProps.companyInfo.compintro.length > 100,
        showMoreScope: nextProps.companyInfo.bizscope.length > 100,
      });
    }
  }
  showMoreIntro = () => {
    this.setState({
      isLongIntro: false,
      showMoreIntro: false,
    });
  };
  hideMoreIntro = () => {
    this.setState({
      isLongIntro: true,
      showMoreIntro: true,
    });
  };
  showMoreScope = () => {
    this.setState({
      isLongScope: false,
      showMoreScope: false,
    });
  };
  hideMoreScope = () => {
    this.setState({
      isLongScope: true,
      showMoreScope: true,
    });
  };
  render() {
    const companyInfo = this.props.companyInfo;
    const url = `http://${companyInfo.compurl}`;
    return (
      <table className={styles.com_table}>
        <tbody>
          <tr>
            <th>公司名称：</th>
            <td>{companyInfo.compname}</td>
          </tr>
          <tr>
            <th>英文名：</th>
            <td>{companyInfo.engname}</td>
          </tr>
          <tr>
            <th>成立日期：</th>
            <td>{companyInfo.founddate}</td>
          </tr>
          <tr>
            <th>注册资本：</th>
            <td>{companyInfo.regcapital}（万元）</td>
          </tr>
          <tr>
            <th>董事长：</th>
            <td>{companyInfo.chairman}</td>
          </tr>
          <tr>
            <th>总经理：</th>
            <td>{companyInfo.manager}</td>
          </tr>
          <tr>
            <th>法人代表：</th>
            <td>{companyInfo.legrep}</td>
          </tr>
          <tr>
            <th>办公地址：</th>
            <td>{companyInfo.officeaddr}</td>
          </tr>
          <tr>
            <th>公司网址：</th>
            <td><a href={url}>{companyInfo.compurl}</a></td>
          </tr>
          <tr>
            <th>所属板块：</th>
            <td>{companyInfo.keyname}</td>
          </tr>
          <tr>
            <th>所属行业：</th>
            <td>{companyInfo.level2name}</td>
          </tr>
          <tr>
            <th>公司简介：</th>
            <td>{this.state.isLongIntro ? `${companyInfo.compintro.slice(0, 99)}...`
            : companyInfo.compintro}
              <a
                onClick={this.state.showMoreIntro ?
                  () => this.showMoreIntro() : () => this.hideMoreIntro()}
                style={{ visibility: this.state.introLong ? 'visible' : 'hidden' }}
              >
                {this.state.showMoreIntro ? '更多' : '收起'}
              </a>
            </td>
          </tr>
          <tr>
            <th>经营范围：</th>
            <td>{this.state.isLongScope ? `${companyInfo.bizscope.slice(0, 99)}...`
              : companyInfo.bizscope}
              <a
                onClick={this.state.showMoreScope ?
                  () => this.showMoreScope() : () => this.hideMoreScope()}
                style={{ visibility: this.state.scopeLong ? 'visible' : 'hidden' }}
              >
                {this.state.showMoreScope ? '更多' : '收起'}
              </a>
            </td>
          </tr>
        </tbody>
      </table>
    );
  }
}

function mapStateToProps(state) {
  const { companyInfo } = state.stockBasic;
  return {
    companyInfo,
  };
}

export default connect(mapStateToProps)(CompanyInfo);
