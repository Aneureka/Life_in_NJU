/**
 * Created by Hitigerzzz on 2017/6/3.
 */
import React from 'react';
import SumBar from './SumBar';

const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/chart/candlestick');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/component/grid');
require('echarts/lib/component/dataZoom');
require('echarts/lib/component/legend');
require('echarts/lib/chart/bar');

class Summary extends React.Component {
  componentDidMount() {
    this.initIncomeChart();
  }
  componentDidUpdate() {
    this.initIncomeChart();
  }
  getOption(profit) {
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
            if (params[i].seriesIndex === 0 || params[i].seriesIndex === 1) {
              res += `<p><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color: ${params[i].color}"></span>${params[i].seriesName}:${(params[i].data * 100).toFixed(3)}%</p>`;
            } else if (params[i].seriesIndex === 2) {
              res += `<p><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color: ${params[i].color}"></span>${params[i].seriesName}:¥${(params[i].data * 1).toFixed(2)}</p>`;
            } else {
              res += `<p><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color: ${params[i].color}"></span>${params[i].seriesName}:${params[i].data}</p>`;
            }
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
          xAxisIndex: [0, 1, 2],
        },
        {
          type: 'slider',
          start: 0,
          end: 100,
          y: '90%',
          xAxisIndex: [0, 1, 2],
        },
      ],
      legend: {
        data: ['基准累计收益', '我的累计收益'],
      },
      grid: [
        {
          top: '5%',
          left: '1%',
          right: '3%',
          height: '40%',
        },
        {
          left: '1%',
          right: '3%',
          top: '50%',
          height: '15%',
        },
        {
          left: '1%',
          right: '3%',
          top: '70%',
          height: '15%',
        }],
      xAxis: [
        {
          type: 'category',
          scale: true,
          data: this.props.date,
          boundaryGap: false,
          axisLine: {
            onZero: false,
            lineStyle: {
              color: '#dfdfdf',
            },
          },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: true },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
          axisPointer: {
            z: 100,
          },
        },
        {
          type: 'category',
          data: this.props.date,
          gridIndex: 1,
          scale: true,
          boundaryGap: false,
          axisLine: {
            onZero: false,
            lineStyle: {
              color: '#dfdfdf',
            },
          },
          axisTick: { show: false },
          splitLine: { show: false },
          axisLabel: { show: false },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
        },
        {
          type: 'category',
          data: this.props.date,
          gridIndex: 2,
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
        {
          name: '每日盈亏',
          nameLocation: 'middle',
          nameTextStyle: {
            color: '#5e5e5e',
          },
          nameRotate: -90,
          position: 'right',
          type: 'value',
          scale: true,
          gridIndex: 1,
          splitNumber: 2,
          axisLabel: {
            inside: true,
            textStyle: {
              color: '#5e5e5e',
            },
            margin: 0,
          },
          axisLine: {
            lineStyle: {
              color: '#dfdfdf',
            },
          },
          z: 1000,
          axisTick: { show: false },
          splitLine: { show: false },
        },
        {
          name: '成交记录',
          nameLocation: 'middle',
          nameTextStyle: {
            color: '#5e5e5e',
          },
          nameRotate: -90,
          position: 'right',
          type: 'value',
          scale: true,
          gridIndex: 2,
          splitNumber: 2,
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
          },
          z: 1000,
          axisTick: { show: false },
          splitLine: { show: false },
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
        },
        {
          name: '每日盈亏',
          type: 'bar',
          xAxisIndex: 1,
          yAxisIndex: 1,
          data: this.props.profit,
          itemStyle: {
            normal: {
              color(params) {
                if (profit.length > 0) {
                  return profit[params.dataIndex] > 0 ? '#f79992' : '#76d0a3';
                }
              },
            },
          },
        },
        {
          name: '买入',
          type: 'bar',
          xAxisIndex: 2,
          yAxisIndex: 2,
          data: this.props.buy,
          itemStyle: {
            normal: {
              color: '#7ec2f3',
            },
          },
        },
        {
          name: '卖出',
          type: 'bar',
          xAxisIndex: 2,
          yAxisIndex: 2,
          data: this.props.sell,
          itemStyle: {
            normal: {
              color: '#3db8c1',
            },
          },
        }],
    };
    return option;
  }
  initIncomeChart() {
    // 每日盈亏
    const profit = this.props.profit;
    const myChart = echarts.init(this.ref);
    myChart.setOption(this.getOption(profit));
    // 图表宽度随浏览器宽度改变
    window.addEventListener('resize', () => {
      myChart.resize();
    });
  }
  render() {
    return (
      <div>
        <SumBar sumData={this.props.sumData} />
        <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '600px' }} />
      </div>
    );
  }
}

export default Summary;
