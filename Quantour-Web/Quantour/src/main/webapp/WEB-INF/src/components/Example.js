/**
 * Created by Hitigerzzz on 2017/5/11.
 */
import React from 'react';
import { connect } from 'dva';

function Example() {
  return (
    <div>
      Example
    </div>
  );
}

function mapStateToProps(state) {
  return (state);
}

export default connect(mapStateToProps)(Example);
