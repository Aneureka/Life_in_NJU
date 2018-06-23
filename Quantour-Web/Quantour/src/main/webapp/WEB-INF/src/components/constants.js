/**
 * Created by wyj on 2017/6/7.
 * description:
 */
export const INIT_CODE = '# 可以自己import我们平台支持的第三方python模块，比如pandas、numpy等。\n\n'
  + '# 在这个方法中编写任何的初始化逻辑。context对象将会在你的算法策略的任何方法之间做传递。\n'
  + 'def init(context):\n'
  + '    # 在context中保存全局变量\n'
  + '    context.s1 = "000001.XSHE"\n'
  + '    # 实时打印日志\n'
  + '    logger.info("RunInfo: {}".format(context.run_info))\n\n'
  + '# before_trading此函数会在每天策略交易开始前被调用，当天只会被调用一次\n'
  + 'def before_trading(context):\n'
  + '    pass\n\n'
  + '# 你选择的证券的数据更新将会触发此段逻辑，例如日或分钟历史数据切片或者是实时数据切片更新\n'
  + 'def handle_bar(context, bar_dict):\n'
  + '    # 开始编写你的主要的算法逻辑\n\n'
  + '    # bar_dict[order_book_id] 可以拿到某个证券的bar信息\n'
  + '    # context.portfolio 可以拿到现在的投资组合信息\n\n'
  + '    # 使用order_shares(id_or_ins, amount)方法进行落单\n\n'
  + '# TODO: 开始编写你的算法吧！\n'
  + '    order_shares(context.s1, 1000)\n\n'
  + '# after_trading函数会在每天交易结束后被调用，当天只会被调用一次\n'
  + 'def after_trading(context):\n'
  + '    pass'
;

export const NETWORK_ERROR = '网络连接异常，请检查您的网路';

export const REQUEST_ERROR = '您的请求过于频繁，请稍后再试';

export const STRATEGY_API = [
  {
    name: 'init',
    caption: 'init(context)',
    value: 'init',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'handle_bar',
    caption: 'handle_bar(context, bar_dict)',
    value: 'handle_bar',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'before_trading',
    caption: 'before_trading(context)',
    value: 'before_trading',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'after_trading',
    caption: 'after_trading(context)',
    value: 'after_trading',
    meta: 'api',
    score: 1000,
  },
  {
    name: '',
    caption: '',
    value: '',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'order_shares',
    caption: 'order_shares(id_or_ins, amount, style)',
    value: 'order_shares',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'order_lots',
    caption: 'order_lots(id_or_ins, amount, style)',
    value: 'order_lots',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'order_value',
    caption: 'order_value(id_or_ins, cash_amount, style)',
    value: 'order_value',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'order_percent',
    caption: 'order_percent(id_or_ins, percent, style)',
    value: 'order_percent',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'order_target_value',
    caption: 'order_target_value(id_or_ins, cash_amount, style)',
    value: 'order_target_value',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'order_target_percent',
    caption: 'order_target_percent(id_or_ins, percent, style)',
    value: 'order_target_percent',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'get_shares',
    caption: 'get_shares(id_or_symbols, count=1, fields=None)',
    value: 'get_shares',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'get_turnover_rate',
    caption: 'get_turnover_rate(id_or_symbols, count=1, fields=None)',
    value: 'get_turnover_rate',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'industry',
    caption: 'industry(code)',
    value: 'industry',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'sector',
    caption: 'sector(code)',
    value: 'sector',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'get_trading_dates',
    caption: 'get_trading_dates(start_date, end_date)',
    value: 'get_trading_dates',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'get_previous_trading_date',
    caption: 'get_previous_trading_date(date)',
    value: 'get_previous_trading_date',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'get_next_trading_date',
    caption: 'get_next_trading_date(date)',
    value: 'get_next_trading_date',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'get_price_change_rate',
    caption: 'get_price_change_rate(id_or_symbols, count=1)',
    value: 'get_price_change_rate',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'is_suspended',
    caption: 'is_suspended(order_book_id, count=1)',
    value: 'is_suspended',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'is_st_stock',
    caption: 'is_st_stock(order_book_id, count=1)',
    value: 'is_st_stock',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'update_universe',
    caption: 'update_universe(order_book_id)',
    value: 'update_universe',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'subscribe',
    caption: 'subscribe(order_book_id)',
    value: 'subscribe',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'unsubscribe',
    caption: 'unsubscribe(order_book_id)',
    value: 'unsubscribe',
    meta: 'api',
    score: 1000,
  },
  {
    name: 'now',
    caption: 'now',
    value: 'now',
    meta: 'api(context)',
    score: 1000,
  },
  {
    name: 'portfolio',
    caption: 'portfolio',
    value: 'portfolio',
    meta: 'api(context)',
    score: 1000,
  },
  {
    name: 'stock_account',
    caption: 'stock_account',
    value: 'stock_account',
    meta: 'api(context)',
    score: 1000,
  },
  {
    name: 'run_info',
    caption: 'run_info',
    value: 'run_info',
    meta: 'api(context)',
    score: 1000,
  },
  {
    name: 'universe',
    caption: 'universe',
    value: 'universe',
    meta: 'api(context)',
    score: 1000,
  },
  {
    name: 'run_daily',
    caption: 'run_daily',
    value: 'run_daily',
    meta: 'api(scheduler)',
    score: 1000,
  },
  {
    name: 'run_weekly',
    caption: 'run_weekly',
    value: 'run_weekly',
    meta: 'api(scheduler)',
    score: 1000,
  },
  {
    name: 'run_monthly',
    caption: 'run_monthly',
    value: 'run_monthly',
    meta: 'api(scheduler)',
    score: 1000,
  },
  {
    name: 'time_rule',
    caption: 'time_rule',
    value: 'time_rule',
    meta: 'api(scheduler)',
    score: 1000,
  },
];
