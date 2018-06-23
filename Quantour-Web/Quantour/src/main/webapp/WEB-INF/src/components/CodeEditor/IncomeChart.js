/**
 * Created by wyj on 2017/6/3.
 * description:
 */
import React from 'react';
import { connect } from 'dva';

const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/chart/candlestick');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/component/grid');
require('echarts/lib/component/dataZoom');
require('echarts/lib/component/legend');

class IncomeChart extends React.Component {
  componentDidMount() {
    this.initIncomeChart();
  }
  componentDidUpdate() {
    this.initIncomeChart();
  }
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
            res += `<p><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color: ${params[i].color}"></span>${params[i].seriesName}:${(params[i].data * 100).toFixed(3)}%</p>`;
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
      dataZoom: [
        {
          type: 'inside',
          start: 0,
          end: 100,
        },
        {
          type: 'slider',
          start: 0,
          end: 100,
          y: '88%',
        },
      ],
      legend: {
        data: ['基准累计收益', '我的累计收益'],
      },
      grid: [
        {
          top: '12%',
          left: '5%',
          right: '4%',
          bottom: '25%',
        }],
      xAxis: [
        {
          type: 'category',
          data: this.props.date,
          scale: true,
          boundaryGap: false,
          axisLine: {
            onZero: false,
            lineStyle: {
              color: '#dfdfdf',
            },
          },
          axisLabel: {
            textStyle: {
              color: '#5e5e5e',
            },
          },
          splitLine: { show: false },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
        },
      ],
      yAxis: [
        {
          name: '累计收益',
          nameLocation: 'middle',
          nameTextStyle: {
            color: '#5e5e5e',
          },
          nameRotate: -90,
          position: 'right',
          type: 'value',
          scale: true,
          boundaryGap: ['20%', '20%'],
          splitLine: {
            show: true,
          },
          axisLine: {
            lineStyle: {
              color: '#dfdfdf',
            },
          },
          axisLabel: {
            inside: true,
            textStyle: {
              color: '#5e5e5e',
            },
            margin: 0,
            formatter(value, index) {
              const newValue = (value * 100).toFixed(0);
              return `${newValue}%`;
            },
          },
          axisTick: {
            show: false,
          },
          min: 'dataMin',
          max: 'dataMax',
        },
      ],
      series: [
        {
          name: '基准累计收益',
          type: 'line',
          symbol: 'none',
          smooth: true,
          data: this.props.standardData,
          itemStyle: {
            normal: {
              color: '#2178b1',
            },
          },
          lineStyle: {
            normal: {
              color: '#2178b1',
              width: 1,
            },
          },
          // areaStyle: {
          //   normal: {
          //     color: '#E3FDFD',
          //   },
          // },
        },
        {
          name: '我的累计收益',
          type: 'line',
          symbol: 'none',
          smooth: true,
          data: this.props.myData,
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
  initIncomeChart() {
    const myChart = echarts.init(this.ref);
    myChart.setOption(this.getOption());
    window.onresize = myChart.resize;
  }
  render() {
    return (
      <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '400px' }} />
    );
  }
}

export default connect()(IncomeChart);
