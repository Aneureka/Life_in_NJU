/**
 * Created by wyj on 2017/5/17.
 * description:
 */
import React from 'react';
import { Table } from 'antd';
import { connect } from 'dva';
import styles from './TapeList.less';

function TapeList({ dataBuyList, dataSellList, diff, ratio }) {
  const column = [
    {
      title: '类型',
      dataIndex: 'type',
      key: 'type',
    }, {
      title: '价格',
      dataIndex: 'price',
      key: 'price',
    }, {
      title: '数量',
      dataIndex: 'amount',
      key: 'amount',
    },
  ];

  return (
    <div>
      <Table
        className={styles.table}
        columns={column}
        rowKey={record => record.id}
        dataSource={dataSellList}
        pagination={false}
        showHeader={false}
        size="small"
      />
      <Table
        className={styles.table}
        columns={column}
        rowKey={record => record.id}
        dataSource={dataBuyList}
        pagination={false}
        showHeader={false}
        size="small"
      />
      <div className={styles.tape_footer}>
        <span>委比{ratio}%</span>
        <span>&nbsp;&nbsp;&nbsp;&nbsp;委差{diff}</span>
      </div>
    </div>
  );
}

function mapStateToProps(state) {
  const data = state.tapelist;
  return data;
}

export default connect(mapStateToProps)(TapeList);
