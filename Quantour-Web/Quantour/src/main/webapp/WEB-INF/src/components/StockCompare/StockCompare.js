/**
 * Created by wyj on 2017/5/28.
 * description:
 */
import React from 'react';
import { AutoComplete, Input, Tag } from 'antd';

class StockCompare extends React.Component {
  state = {
    tags: [],
    inputValue: '',
    dataSource: [],
  };

  handleClose = (removedTag) => {
    const tags = this.state.tags.filter(tag => tag !== removedTag);
    this.setState({ tags });
  };
  handleKeyPress = (e) => {
    this.setState({
      inputValue: e.target.value,
    });
  };
  handleSearch = (value) => {
    this.setState({
      dataSource: !value ? [] : [
        value,
        value + value,
        value + value + value,
      ],
    });
  };
  handleInputConfirm = (value) => {
    const state = this.state;
    const inputValue = value;
    let tags = state.tags;
    if (inputValue && tags.indexOf(inputValue) === -1) {
      tags = [...tags, inputValue];
    }
    this.setState({
      tags,
      inputValue: '',
    });
  };
  render() {
    const { tags, dataSource } = this.state;
    return (
      <div>
        {tags.map((tag) => {
          return <Tag key={tag} closable afterClose={() => this.handleClose(tag)}>{tag}</Tag>;
        })}
        {
          <AutoComplete
            onSelect={this.handleInputConfirm}
            style={{ width: 300 }}
            dataSource={dataSource}
            onSearch={this.handleSearch}
            allowClear
          >
            <Input
              onKeyPress={this.handleKeyPress}
            />
          </AutoComplete>
        }
      </div>
    );
  }
}

export default StockCompare;
