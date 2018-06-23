/**
 * Created by wyj on 2017/5/16.
 * description:
 */
import React from 'react';
import { Link } from 'dva/router';
import styles from './ExponentChart.less';
// 导入echarts
const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/chart/line'); // 图表类型
require('echarts/lib/component/title'); // 标题插件
require('echarts/lib/component/tooltip');// 提示框

class ExponentChart extends React.Component {
  componentDidMount() {
    this.initChart();
  }

  componentDidUpdate() {
    this.initChart();
  }
  // 一个基本的echarts图表配置函数
  getOption() {
    const option = {
      tooltip: {
        trigger: 'axis',
        backgroundColor: 'rgba(249,249,250,1)',
        borderColor: '#3db8c1',
        borderRadius: 4,
        borderWidth: 1,
        padding: 5,    // [5, 10, 15, 20]
        textStyle: {
          color: '#5e5e5e',
        },
      },
      grid: {
        top: '5%',
        bottom: '12%',
        left: '14%',
        right: '10%',
        // height: '100%',
        // width: '100%',
      },
      xAxis: {
        type: 'category',
        splitLine: {
          show: false,
        },
        min: 'dataMin',
        max: 'dataMax',
        axisLine: {
          show: false,
          onZero: false,
        },
        axisTick: {
          show: false,
        },
        axisLabel: {
          interval: 240,
        },
        data: this.props.data.category,
      },
      yAxis: {
        type: 'value',
        boundaryGap: ['10%', '10%'],
        scale: true,
        splitLine: {
          show: false,
        },
        axisLine: {
          show: false,
          onZero: false,
        },
        axisTick: {
          show: false,
        },
        interval: 500,
      },
      series: [{
        name: '价格',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: this.props.data.value,
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
        areaStyle: {
          normal: {
            color: '#E3FDFD',
          },
        },
      }],
    };
    return option;
  }
  initChart() {
    const myChart = echarts.init(this.ref); // 初始化echarts
    myChart.setOption(this.getOption());
    // 图表宽度随浏览器宽度改变
    window.addEventListener('resize', () => {
      myChart.resize();
    });
  }

  render() {
    const isUp = this.props.basicData.change > 0 ? styles.stock_change : styles.stock_change_down;
    return (
      <div>
        <div>
          <Link to={`/stockinfo?code=${this.props.basicData.symbol}`} className={styles.stock_name}>{this.props.title}</Link>
          <span className={`${styles.stock_present} ${isUp}`}>{this.props.basicData.current}</span>
          <span className={isUp}>{this.props.basicData.percentage}%</span>
          <span className={isUp}>{this.props.basicData.change}</span>
        </div>
        <div ref={(c) => { this.ref = c; }} className={styles.exponent_chart}>s</div>
      </div>
    );
  }
}


export default ExponentChart;
