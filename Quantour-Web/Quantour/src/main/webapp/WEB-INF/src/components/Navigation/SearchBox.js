/**
 * Created by wyj on 2017/6/8.
 * description:
 */
import React from 'react';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import { AutoComplete, Input } from 'antd';
import styles from './SearchBox.less';

const Option = AutoComplete.Option;

class SearchBox extends React.Component {
  state = {
    inputValue: [],
  };

  // 股票搜索组件
  handleKeyPress = (e) => {
    this.setState({
      inputValue: e.target.value,
    });
  };
  handleSearch = (value) => {
    this.props.dispatch({
      type: 'candleChart/fetchCompareStock',
      payload: { value },
    });
  };
  handleInputConfirm = (value) => {
    let valArr = [];
    valArr = value.split('!');
    this.props.dispatch(routerRedux.push({
      pathname: '/stockinfo',
      query: { code: valArr[1] },
    }));
    this.setState({
      inputValue: '',
    });
  };

  render() {
    const children = this.props.searchResult.map((source) => {
      const result = `${source.name}(${source.id})`;
      return <Option key={`${source.name}!${source.id}`}>{result}</Option>;
    });
    return (
      <div>
        {
          <AutoComplete
            className={styles.search_box}
            onSelect={this.handleInputConfirm.bind(this)}
            style={{ width: '100%' }}
            dataSource={children}
            onSearch={this.handleSearch.bind(this)}
            placeholder="股票名称/代码"
            allowClear
          >
            <Input
              className={styles.search_box}
              onKeyPress={this.handleKeyPress}
            />
          </AutoComplete>
        }
      </div>
    );
  }
}

export default connect()(SearchBox);
