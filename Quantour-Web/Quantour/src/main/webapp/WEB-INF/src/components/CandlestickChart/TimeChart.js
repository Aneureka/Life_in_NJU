/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import React from 'react';
import { connect } from 'dva';
import { AutoComplete, Input, Tag, Icon } from 'antd';

const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/chart/bar');
require('echarts/lib/component/grid');

const Option = AutoComplete.Option;

class TimeChart extends React.Component {
  state = {
    tags: [],
    inputValue: '',
  };
  componentDidMount() {
    this.myChart = echarts.init(this.ref); // 初始化echarts
    // 图表宽度随浏览器宽度改变
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    // 每1min动态获取实时数据
    this.intervalID = setInterval(this.getDynamicData.bind(this), 60000);
  }
  componentDidUpdate() {
    this.myChart.setOption(this.getOption(), true);
  }
  componentWillUnmount() {
    clearInterval(this.intervalID);
  }
  getDynamicData() {
    const code = this.props.code;
    this.props.dispatch({
      type: 'candleChart/fetchTime',
      payload: { code },
    });
  }
  // 一个基本的echarts图表配置函数
  getOption() {
    const option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
        },
        backgroundColor: 'rgba(249,249,250,1)',
        borderColor: '#3db8c1',
        borderRadius: 4,
        borderWidth: 1,
        padding: 5,    // [5, 10, 15, 20]
        textStyle: {
          color: '#5e5e5e',
        },
        formatter(params) {
          let res = `<div><p>${params[0].name}</p></div>`;
          for (let i = 0; i < params.length; i += 1) {
            res += `<p><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color: ${params[i].color}"></span>${params[i].seriesName}:${params[i].data}</p>`;
          }
          return res;
        },
      },
      axisPointer: {
        link: { xAxisIndex: 'all' },
        label: {
          backgroundColor: '#777',
        },
      },
      grid: [
        {
          top: '5%',
          left: '10%',
          right: '8%',
          height: '70%',
          width: '80%',
        },
        {
          left: '10%',
          right: '8%',
          top: '80%',
          height: '16%',
          width: '80%',
        },
      ],
      xAxis: [
        {
          type: 'category',
          data: this.props.timeData,
          boundaryGap: false,
          axisLine: { onZero: false },
          splitLine: { show: false },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
          axisPointer: {
            z: 100,
          },
        },
        {
          type: 'category',
          data: this.props.timeData,
          gridIndex: 1,
          // data: this.props.candle_data.category,
          scale: true,
          boundaryGap: false,
          axisLine: { onZero: false },
          axisTick: { show: false },
          splitLine: { show: false },
          axisLabel: { show: false },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
        },
      ],
      yAxis: [
        {
          type: 'value',
          scale: true,
          boundaryGap: ['10%', '10%'],
          splitLine: {
            show: false,
          },
          axisLabel: {
            formatter: this.props.isCompare === 1 ? '{value} %' : '{value}',
          },
        },
        {
          type: 'value',
          scale: true,
          gridIndex: 1,
          splitNumber: 2,
          axisLabel: { show: false },
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: { show: false },
        }],
      series: this.props.series,
    };
    return option;
  }
  updateSeries = (tags) => {
    const baseCode = this.props.code;
    const codes = []; // 股票代码
    const names = []; // 股票名称
    codes.push(baseCode); // 被对比的股票代码
    names.push(this.props.name); // 被对比的股票名称
    for (let i = 0; i < tags.length; i += 1) {
      let tagVal = [];
      tagVal = tags[i].split('!');
      names.push(tagVal[0]);
      codes.push(tagVal[1]);
    }
    if (tags.length === 0) {
      // 没有其他股票了
      this.props.dispatch({
        type: 'candleChart/fetchTime',
        payload: { code: baseCode },
      });
      // 每1min动态获取实时数据
      this.intervalID = setInterval(this.getDynamicData.bind(this), 60000);
    } else {
      // 还有其他股票
      this.props.dispatch({
        type: 'candleChart/fetchCompareData',
        payload: { codes, names },
      });
      clearInterval(this.intervalID);
    }
  };

  // 股票对比组件
  handleClose = (removedTag) => {
    const tags = this.state.tags.filter(tag => tag !== removedTag);
    this.setState({ tags });
    // 更新数据线
    this.updateSeries(tags);
  };
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
    const state = this.state;
    const inputValue = value;
    let tags = state.tags;
    if (inputValue && tags.indexOf(inputValue) === -1) {
      if (inputValue.split('!')[1] !== this.props.code) {
        tags = [...tags, inputValue];
      }
    }
    this.setState({
      tags,
      inputValue: '',
    });
    this.updateSeries(tags);
  };
  render() {
    const { tags } = this.state;
    const children = this.props.searchResult.map((source) => {
      const result = `${source.name}(${source.id})`;
      return <Option key={`${source.name}!${source.id}`}>{result}</Option>;
    });
    return (
      <div>
        <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '400px' }}>s</div>
        <div style={{ marginLeft: '5px', marginBottom: '20px' }}>
          <span>股票对比：</span>
          {tags.map((tag) => {
            let tagVal = [];
            tagVal = tag.split('!');
            const showVal = `${tagVal[0]}(${tagVal[1]})`;
            return (
              <Tag
                key={tagVal[1]}
                closable
                afterClose={() => this.handleClose(tag)}
              >
                {showVal}
              </Tag>);
          })}
          {
            <AutoComplete
              onSelect={this.handleInputConfirm.bind(this)}
              style={{ width: 200 }}
              dataSource={children}
              onSearch={this.handleSearch.bind(this)}
              allowClear
            >
              <Input
                prefix={<Icon type="search" />}
                onKeyPress={this.handleKeyPress}
              />
            </AutoComplete>
          }
        </div>
      </div>
    );
  }
}

export default connect()(TimeChart);

