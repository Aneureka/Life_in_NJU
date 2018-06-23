import React from 'react';
import { Router } from 'dva/router';

const cached = {};
function registerModel(app, model) {
  if (!cached[model.namespace]) {
    app.model(model);
    cached[model.namespace] = 1;
  }
}

function RouterConfig({ history, app }) {
  const routes = [
    {
      path: '/',
      name: 'IndexPage',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/stockList'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/IndexPage'));
        }, 'IndexPage');
      },
    },
    {
      path: '/login',
      name: 'Login',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/LoginPage/LoginPage'));
        }, 'Login');
      },
    },
    {
      path: '/stockmarket',
      name: 'StockMarket',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          registerModel(app, require('./models/stockList'));
          registerModel(app, require('./models/newsList'));
          cb(null, require('./routes/StockMarket/StockMarket'));
        }, 'StockMarket');
      },
    },
    {
      path: '/strategy',
      name: 'StrategyPage',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/strategyResult'));
          registerModel(app, require('./models/strategyResearch'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/StrategyPage/StrategyPage'));
        }, 'StrategyPage');
      },
    },
    {
      path: '/strategyhistory',
      name: 'StrategyHistory',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/strategyResearch'));
          registerModel(app, require('./models/strategyResult'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/StrategyPage/MeasureHistoryPane'));
        }, 'StrategyHistory');
      },
    },
    {
      path: '/mystrategy',
      name: 'MyStrategy',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/strategyResult'));
          registerModel(app, require('./models/candleChart'));
          registerModel(app, require('./models/strategyResearch'));
          cb(null, require('./routes/StrategyPage/MyStrategy'));
        }, 'MyStrategy');
      },
    },
    {
      path: '/strategydetail',
      name: 'StrategyDetailPage',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          registerModel(app, require('./models/strategyResearch'));
          registerModel(app, require('./models/strategyResult'));
          cb(null, require('./routes/StrategyDetail/StrategyDetail'));
        }, 'StrategyDetailPage');
      },
    },
    {
      path: '/stockinfo',
      name: 'StockInfo',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/tapeList'));
          registerModel(app, require('./models/stockBasic'));
          registerModel(app, require('./models/candleChart'));
          registerModel(app, require('./models/forecast'));
          registerModel(app, require('./models/myStockTable'));
          cb(null, require('./routes/StockInfo/StockInfo'));
        }, 'StockInfo');
      },
    },
    {
      path: '/individualpage',
      name: 'IndividualPage',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/IndividualPage/IndividualPage'));
        }, 'IndividualPage');
      },
    },
    {
      path: '/newpost',
      name: 'NewPost',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          registerModel(app, require('./models/article'));
          cb(null, require('./routes/Community/Community'));
        }, 'NewPost');
      },
    },
    {
      path: '/register',
      name: 'Register',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/RegisterPage/RegisterPage'));
        }, 'RegisterPage');
      },
    },
    {
      path: '/forget',
      name: 'Forget',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/ForgetPage/ForgetPage'));
        }, 'ForgetPage');
      },
    },
    {
      path: '/community',
      name: 'Community',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/article'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/Community/CommunityPage'));
        }, 'CommunityPage');
      },
    },
    {
      path: '/post',
      name: 'Post',
      getComponent(nextState, cb) {
        require.ensure([], (require) => {
          registerModel(app, require('./models/users'));
          registerModel(app, require('./models/article'));
          registerModel(app, require('./models/candleChart'));
          cb(null, require('./routes/Community/PostPage'));
        }, 'PostPage');
      },
    },
  ];

  return <Router history={history} routes={routes} />;
}

export default RouterConfig;
