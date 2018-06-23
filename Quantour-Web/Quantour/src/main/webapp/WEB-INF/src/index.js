import dva from 'dva';
import { browserHistory } from 'dva/router';
// import { message } from 'antd';
import createLoading from 'dva-loading';
import 'antd/dist/antd.less';
import './index.css';
// import { NETWORK_ERROR, REQUEST_ERROR } from './components/constants';

const ERROR_MSG_DURATION = 3; // 3 秒

// 1. Initialize
const app = dva({
  history: browserHistory,
  onError(e) {
    // message.error(e.message, ERROR_MSG_DURATION);
    // message.error(NETWORK_ERROR, ERROR_MSG_DURATION);
    // message.error(REQUEST_ERROR, ERROR_MSG_DURATION);
  },
});

// 2. Plugins
app.use(createLoading({
  effects: true,
}));

// 3. Model
// app.model(require('./models/example'));

// 4. Router
app.router(require('./router'));

// 5. Start
app.start('#root');
