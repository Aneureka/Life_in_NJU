package leftovers.service;

import leftovers.model.Algorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Hiki on 2017/6/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AlgorithmServiceTest {

    @Autowired
    AlgorithmService tested;

    String code = "from rqalpha.api import *\n" +
            "\n" +
            "\n" +
            "# 在这个方法中编写任何的初始化逻辑。context对象将会在你的算法策略的任何方法之间做传递。\n" +
            "def init(context):\n" +
            "    logger.info(\"init\")\n" +
            "    context.s1 = \"000001.XSHE\"\n" +
            "    update_universe(context.s1)\n" +
            "    # 是否已发送了order\n" +
            "    context.fired = False\n" +
            "\n" +
            "\n" +
            "def before_trading(context):\n" +
            "    pass\n" +
            "\n" +
            "\n" +
            "# 你选择的证券的数据更新将会触发此段逻辑，例如日或分钟历史数据切片或者是实时数据切片更新\n" +
            "def handle_bar(context, bar_dict):\n" +
            "    # 开始编写你的主要的算法逻辑\n" +
            "\n" +
            "    # bar_dict[order_book_id] 可以拿到某个证券的bar信息\n" +
            "    # context.portfolio 可以拿到现在的投资组合状态信息\n" +
            "\n" +
            "    # 使用order_shares(id_or_ins, amount)方法进行落单\n" +
            "\n" +
            "    # TODO: 开始编写你的算法吧！\n" +
            "    if not context.fired:\n" +
            "        # order_percent并且传入1代表买入该股票并且使其占有投资组合的100%\n" +
            "        order_percent(context.s1, 1)\n" +
            "        context.fired = True\n";

    String algoId = "fejgir6515gwf1w5fdwadw";
    String algoName = "dwadwa";
    String username = "aneureka";
    String time = LocalDateTime.now().toString();
    String beginDate = "2016-06-01";
    String endDate = "2016-12-01";
    double stockStartCash = 100000;
    String benchmark = "000300.XSHG";

    Algorithm algorithm = new Algorithm(algoId, algoName, username, time, code, beginDate, endDate, stockStartCash, benchmark);

    @Test
    public void removeAlgorithm() throws Exception {

    }

    @Test
    public void updateAlgorithm() throws Exception {
    }

    @Test
    public void createAlgorithm() throws Exception {
        tested.createAlgorithm(algorithm);
    }

    @Test
    public void findAlgorithmById() throws Exception {
    }

    @Test
    public void findAlgorithmsByUsername() throws Exception {
    }

}