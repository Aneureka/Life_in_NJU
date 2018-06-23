package leftovers.enums;

/**
 * Created by Hiki on 2017/6/11.
 */
public class DefaultAlgorithmInfo {

    public final static String BEGIN_DATE = "2016-01-04";

    public final static String END_DATE = "2016-10-04";

    public final static double STOCK_START_CASH = 100000;

    public final static String BENCHMARK = "000300.XSHG";

    public final static String CODE_PREFIX = "from rqalpha.api import *\n";

    public final static String CODE = "# 可以自己import我们平台支持的第三方python模块，比如pandas、numpy等。\n" +
            "\n" +
            "# 在这个方法中编写任何的初始化逻辑。context对象将会在你的算法策略的任何方法之间做传递。\n" +
            "def init(context):\n" +
            "    # 在context中保存全局变量\n" +
            "    context.s1 = \"000001.XSHE\"\n" +
            "    # 实时打印日志\n" +
            "    logger.info(\"RunInfo: {}\".format(context.run_info))\n" +
            "\n" +
            "# before_trading此函数会在每天策略交易开始前被调用，当天只会被调用一次\n" +
            "def before_trading(context):\n" +
            "    pass\n" +
            "\n" +
            "\n" +
            "# 你选择的证券的数据更新将会触发此段逻辑，例如日或分钟历史数据切片或者是实时数据切片更新\n" +
            "def handle_bar(context, bar_dict):\n" +
            "    # 开始编写你的主要的算法逻辑\n" +
            "\n" +
            "    # bar_dict[order_book_id] 可以拿到某个证券的bar信息\n" +
            "    # context.portfolio 可以拿到现在的投资组合信息\n" +
            "\n" +
            "    # 使用order_shares(id_or_ins, amount)方法进行落单\n" +
            "\n" +
            "    # TODO: 开始编写你的算法吧！\n" +
            "    order_shares(context.s1, 1000)\n" +
            "\n" +
            "# after_trading函数会在每天交易结束后被调用，当天只会被调用一次\n" +
            "def after_trading(context):\n" +
            "    pass";



}
