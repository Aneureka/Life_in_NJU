/**
 * Created by Hitigerzzz on 2017/6/7.
 */
import React from 'react';

const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/component/grid');
require('echarts/lib/component/markLine');
require('echarts/lib/component/legend');


class ClosePrice extends React.Component {

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
      legend: {
        data: ['预测收盘价', '实际收盘价'],
        top: '5%',
      },
      grid: [
        {
          top: '15%',
          left: '5%',
          right: '3%',
        },
      ],
      xAxis: [
        {
          type: 'time',
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
      ],
      yAxis: [
        {
          type: 'value',
          scale: true,
          boundaryGap: ['20%', '20%'],
          splitLine: {
            show: false,
          },
          // min: 'dataMin',
          max: 'dataMax',
        }],
      series: [
        {
          name: '预测收盘价',
          type: 'line',
          symbol: 'none',
          // smooth: true,
          data: this.props.expectedPrice,
          itemStyle: {
            normal: {
              color: '#3db8c1',
            },
          },
          lineStyle: {
            normal: {
              color: '#3db8c1',
              width: 1,
            },
          },
          markLine: {
            lineStyle: {
              normal: {
                color: '#FCE38A',
                type: 'dashed',
              },
            },
            data: [
              { xAxis: this.props.markPosition },
            ],
            label: {
              normal: {
                formatter() {
                  const LineName = 'Forecast';
                  return LineName;
                },
              },
            },
          },
        },
        {
          name: '实际收盘价',
          type: 'line',
          symbol: 'none',
          // smooth: true,
          data: this.props.actualPrice,
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
        // {
        //   name: '误差高价',
        //   type: 'line',
        //   symbol: 'none',
        //   smooth: true,
        //   data: this.props.deviationH,
        //   itemStyle: {
        //     normal: {
        //       color: '#FCE38A',
        //     },
        //   },
        //   lineStyle: {
        //     normal: {
        //       color: '#FCE38A',
        //       width: 2,
        //     },
        //   },
        //   areaStyle: {
        //     normal: {
        //       color: '#FCE38A',
        //       opacity: 0.3,
        //     },
        //   },
        // },
        // {
        //   name: '误差低价',
        //   type: 'line',
        //   symbol: 'none',
        //   smooth: true,
        //   data: this.props.deviationL,
        //   itemStyle: {
        //     normal: {
        //       color: '#FCE38A',
        //     },
        //   },
        //   lineStyle: {
        //     normal: {
        //       color: '#FCE38A',
        //       width: 2,
        //     },
        //   },
        //   areaStyle: {
        //     normal: {
        //       color: '#ffffff',
        //       opacity: 1,
        //     },
        //   },
        // }
      ],
    };
    return option;
  }

  render() {
    return (
      <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '250px' }}>s</div>
    );
  }
}


export default ClosePrice;
