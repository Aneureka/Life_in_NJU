/**
 * Created by wyj on 2017/5/17.
 * description:
 */
import React from 'react';
// 导入echarts
const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/component/title'); // 标题插件
require('echarts/lib/component/tooltip');// 提示框

class TableExponentChart extends React.Component {
  componentDidMount() {
    this.mychart = echarts.init(this.ref);
    this.mychart.setOption(this.getOption());
    window.onresize = this.mychart.resize;
  }

  componentDidUpdate() {
    this.mychart.setOption(this.getOption());
  }

  componentWillUnmount() {
    clearInterval(this.intervalID);
  }

  getOption() {
    const option = {
      title: {
        text: '',
      },
      tooltip: {
        trigger: 'none',
        axisPointer: {
          type: 'cross',
          // animation: false,
        },
      },
      grid: {
        top: '0%',
        bottom: '0%',
        left: '0%',
        right: '0%',
        height: '100%',
        width: '100%',
      },
      xAxis: {
        show: false,
        type: 'category',
        data: this.props.data.timeData,
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
      yAxis: {
        show: false,
        type: 'value',
        scale: true,
        boundaryGap: ['10%', '10%'],
        splitLine: {
          show: false,
        },
      },
      series: [{
        name: this.props.code,
        type: 'line',
        smooth: true,
        data: this.props.data.presentData,
        itemStyle: {
          normal: {
            color: '#00a2ae',
          },
        },
        lineStyle: {
          normal: {
            color: '#00a2ae',
            width: 0.5,
          },
        },
        areaStyle: {
          normal: {
            color: '#00a2ae',
            opacity: 0.4,
          },
        },
      }],
    };

    return option;
  }


  render() {
    return (
      <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '50px' }} />
    );
  }
}

export default TableExponentChart;
