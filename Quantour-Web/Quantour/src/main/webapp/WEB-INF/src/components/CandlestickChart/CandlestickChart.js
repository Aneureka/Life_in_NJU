/**
 * Created by wyj on 2017/5/11.
 */
import React from 'react';

const echarts = require('echarts/lib/echarts');
require('echarts/lib/chart/candlestick');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/title');
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/chart/bar');
require('echarts/lib/component/grid');
require('echarts/lib/component/dataZoom');
require('echarts/lib/component/markPoint');
require('echarts/lib/component/markLine');
require('echarts/lib/component/markArea');
require('echarts/lib/component/legend');

class CandlestickChart extends React.Component {
  componentDidMount() {
    this.initCandlestick();
  }

  componentDidUpdate() {
    this.initCandlestick();
  }
  getOption(changeList) {
    const length = changeList.length > 0 ? changeList.length : 1;
    const startPoint = length > 100 ? 100 - ((100 / length) * 100) : 0;
    const option = {
      title: {
        text: '',
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
        },
        backgroundColor: 'rgba(249,249,250,0.8)',
        borderColor: '#00a2ae',
        borderRadius: 4,
        borderWidth: 1,
        padding: 5,    // [5, 10, 15, 20]
        textStyle: {
          color: '#5e5e5e',
        },
        position(pos, params, el, elRect, size) {
          const obj = { top: 10 };
          obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
          return obj;
        },
        extraCssText: 'width: 170px',
      },
      axisPointer: {
        link: { xAxisIndex: 'all' },
        label: {
          backgroundColor: '#777',
        },
      },
      legend: {
        data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
        top: '1%',
      },
      grid: [
        {
          left: '5%',
          right: '5%',
          top: '10%',
          height: '55%',
        },
        {
          left: '5%',
          right: '5%',
          top: '70%',
          height: '16%',
        },
      ],
      xAxis: [
        {
          type: 'category',
          data: this.props.candle_list.category,
          scale: true,
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
          gridIndex: 1,
          data: this.props.candle_list.category,
          scale: true,
          axisLine: { onZero: false },
          axisTick: { show: false },
          splitLine: { show: false },
          axisLabel: { show: false },
          splitNumber: 20,
          min: 'dataMin',
          max: 'dataMax',
          // axisPointer: {
          //   label: {
          //     formatter(params) {
          //       const seriesValue = (params.seriesData[0] || {}).value;
          //       return params.value
          //         + (seriesValue != null
          //             ? `\n${echarts.format.addCommas(seriesValue)}`
          //             : ''
          //         );
          //     },
          //   },
          // },
        },
      ],
      yAxis: [
        {
          scale: true,
          splitArea: {
            show: true,
          },
        },
        {
          scale: true,
          gridIndex: 1,
          splitNumber: 2,
          axisLabel: { show: false },
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: { show: false },
        },
      ],
      dataZoom: [
        {
          type: 'inside',
          xAxisIndex: [0, 1],
          start: startPoint,
          end: 100,
        },
        {
          show: true,
          xAxisIndex: [0, 1],
          type: 'slider',
          y: '90%',
          start: startPoint,
          end: 100,
        },
      ],
      series: [
        {
          name: '日K',
          type: 'candlestick',
          data: this.props.candle_list.value,
          // markPoint: {
          //   label: {
          //     normal: {
          //       formatter(param) {
          //         return param != null ? Math.round(param.value) : '';
          //       },
          //     },
          //   },
          //   data: [
          //     {
          //       name: 'highest value',
          //       type: 'max',
          //       valueDim: 'highest',
          //     },
          //     {
          //       name: 'lowest value',
          //       type: 'min',
          //       valueDim: 'lowest',
          //     },
          //     {
          //       name: 'average value on close',
          //       type: 'average',
          //       valueDim: 'close',
          //     },
          //   ],
          //   tooltip: {
          //     formatter(param) {
          //       return `${param.name}<br>${param.data.coord || ''}`;
          //     },
          //   },
          // },
          // markLine: {
          //   symbol: ['none', 'none'],
          //   data: [
          //     [
          //       {
          //         name: 'from lowest to highest',
          //         type: 'min',
          //         valueDim: 'lowest',
          //         symbol: 'circle',
          //         symbolSize: 10,
          //         label: {
          //           normal: { show: false },
          //           emphasis: { show: false },
          //         },
          //       },
          //       {
          //         type: 'max',
          //         valueDim: 'highest',
          //         symbol: 'circle',
          //         symbolSize: 10,
          //         label: {
          //           normal: { show: false },
          //           emphasis: { show: false },
          //         },
          //       },
          //     ],
          //     {
          //       name: 'min line on close',
          //       type: 'min',
          //       valueDim: 'close',
          //     },
          //     {
          //       name: 'max line on close',
          //       type: 'max',
          //       valueDim: 'close',
          //     },
          //   ],
          // },
          itemStyle: {
            normal: {
              color: '#f04b5b',
              color0: '#2bbe65',
              borderColor: '#f04b5b',
              borderColor0: '#2bbe65',
            },
          },
        },
        {
          name: 'MA5',
          type: 'line',
          data: this.props.ma_list.maf,
          smooth: true,
          lineStyle: {
            normal: { opacity: 0.5 },
          },
        },
        {
          name: 'MA10',
          type: 'line',
          data: this.props.ma_list.mat,
          smooth: true,
          lineStyle: {
            normal: { opacity: 0.5 },
          },
        },
        {
          name: 'MA20',
          type: 'line',
          data: this.props.ma_list.matt,
          smooth: true,
          lineStyle: {
            normal: { opacity: 0.5 },
          },
        },
        {
          name: 'MA30',
          type: 'line',
          data: this.props.ma_list.mattt,
          smooth: true,
          lineStyle: {
            normal: { opacity: 0.5 },
          },
        },
        {
          name: 'Volume',
          type: 'bar',
          xAxisIndex: 1,
          yAxisIndex: 1,
          data: this.props.candle_list.volume,
          itemStyle: {
            normal: {
              color(params) {
                if (changeList.length > 0) {
                  return changeList[params.dataIndex] > 0 ? '#f04b5b' : '#2bbe65';
                }
              },
            },
          },
          tooltip: {
            extraCssText: 'display: none',
          },
        },
      ],
    };
    return option;
  }

  initCandlestick() {
    const changeList = this.props.change_list;
    const myChart = echarts.init(this.ref);
    myChart.setOption(this.getOption(changeList));
    // 图表宽度随浏览器宽度改变
    window.addEventListener('resize', () => {
      myChart.resize();
    });
  }

  render() {
    return (
      <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '448px' }} />
    );
  }
}

export default CandlestickChart;
