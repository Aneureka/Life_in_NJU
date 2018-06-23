/**
 * Created by Hitigerzzz on 2017/6/5.
 */
import React from 'react';
import { connect } from 'dva';

const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/component/grid');
require('echarts/lib/component/legend');

class NewsSentiment extends React.Component {

  componentDidMount() {
    this.myChart = echarts.init(this.ref); // 初始化echarts
    // 图表宽度随浏览器宽度改变
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    this.myChart.setOption(this.getOption());
  }
  componentDidUpdate() {
    this.myChart.setOption(this.getOption());
  }

  getOption() {
    const option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          // animation: false,
        },
        backgroundColor: 'rgba(249,249,250,1)',
        borderColor: '#3db8c1',
        borderRadius: 4,
        borderWidth: 1,
        padding: 5,    // [5, 10, 15, 20]
        textStyle: {
          color: '#5e5e5e',
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
          top: '15%',
          left: '10%',
          right: '10%',
        },
      ],
      legend: {
        data: ['相关度', '情感分数'],
        top: '5%',
      },
      xAxis: [
        {
          type: 'category',
          data: this.props.timeData,
          scale: true,
          boundaryGap: ['10%', '10%'],
          axisLine: { onZero: false },
          splitLine: { show: false },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
          axisPointer: {
            type: 'shadow',
          },
        },
      ],
      yAxis: [
        {
          type: 'value',
          name: '相关度',
          scale: true,
          boundaryGap: ['10%', '10%'],
          splitLine: {
            show: false,
          },
        },
        {
          type: 'value',
          name: '情感分数',
          scale: true,
          boundaryGap: ['10%', '10%'],
          splitLine: {
            show: false,
          },
          // axisLabel: {
          //   formatter: '{value} °C',
          // },
        }],
      series: [
        {
          name: '相关度',
          type: 'bar',
          // showSymbol: false,
          // hoverAnimation: true,
          // symbol: 'none',
          // smooth: true,
          data: this.props.relatedScores,
          itemStyle: {
            normal: {
              color: '#7ec2f3',
            },
          },
        },
        {
          name: '情感分数',
          type: 'line',
          // symbol: 'none',
          yAxisIndex: 1,
          // smooth: true,
          data: this.props.sentimentScores,
          itemStyle: {
            normal: {
              color: '#FCE38A',
            },
          },
          lineStyle: {
            normal: {
              color: '#FCE38A',
              width: 1,
            },
          },
        }],
    };
    return option;
  }

  render() {
    return (
      <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '250px' }}>s</div>
    );
  }
}

function mapStateToProps(state) {
  const { timeData, relatedScores, sentimentScores } = state.forecast;
  return { timeData, relatedScores, sentimentScores };
}
export default connect(mapStateToProps)(NewsSentiment);
