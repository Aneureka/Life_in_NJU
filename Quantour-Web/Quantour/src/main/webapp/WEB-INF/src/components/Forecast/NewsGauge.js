/**
 * Created by Hitigerzzz on 2017/6/10.
 */
/**
 * Created by Hitigerzzz on 2017/6/7.
 */
import React from 'react';
import { connect } from 'dva';

const echarts = require('echarts/lib/echarts'); // 必须
require('echarts/lib/component/tooltip');
require('echarts/lib/chart/gauge'); // 图表类型

class NewsGauge extends React.Component {

  componentDidMount() {
    this.myChart = echarts.init(this.ref); // 初始化echarts
    window.onresize = this.myChart.resize;
    this.myChart.setOption(this.getOption());
  }
  componentDidUpdate() {
    this.myChart.setOption(this.getOption());
  }

  getOption() {
    const option = {
      tooltip: {
        formatter: '{a} <br/>{b} : {c}',
      },
      series: [
        {
          name: '平均情感指数',
          type: 'gauge',
          min: -1,
          max: 1,
          detail: { formatter: '{value}' },
          data: [{ value: this.props.sumScores }],
          axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
              width: 3,
              shadowColor: '#fff', // 默认透明
              shadowBlur: 10,
            },
          },
          splitLine: {           // 分隔线
            length: 5,         // 属性length控制线长
          },
          pointer: {
            width: 2,
          },
        },
      ],
    };
    return option;
  }

  render() {
    return (
      <div>
        <div style={{ textAlign: 'center', color: '#5e5e5e', fontSize: '16px' }}>平均情感指数</div>
        <div ref={(c) => { this.ref = c; }} style={{ width: '100%', height: '250px', marginTop: '-20px' }}>s</div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { sumScores } = state.forecast;
  return { sumScores };
}
export default connect(mapStateToProps)(NewsGauge);
