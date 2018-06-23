package leftovers.datahelper.xueqiuspider;

import leftovers.datahelper.xueqiuspider.constant.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * Created by Hiki on 2017/6/4.
 */
public class XueqiuJsonProducerImplTest {

    XueqiuJsonProducerImpl tested;

    String code;

    @Before
    public void setUp() throws Exception {
        tested = new XueqiuJsonProducerImpl();
        code = "SZ000001";
    }


    @Test
    public void getStockQuoteJson() {
        String json = tested.getStockQuoteJson(code);
        System.out.println(json);
    }

    @Test
    public void getStockKInfoJson() {
        LocalDateTime begin = LocalDateTime.parse("2005-01-01T01:00:00.010");
        LocalDateTime end = LocalDateTime.parse("2009-01-01T01:00:00.010");
        String json = tested.getStockKInfoJson(code, Period.DAY.toString(), PriceType.BEFORE.toString(), begin.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(json);
    }

    @Test
    public void getStockMinInfoJson() {
        String json = tested.getStockMinInfoJson(code);
        System.out.println(json);
    }

    @Test
    public void getStockPankouInfoJson() {
        String json = tested.getStockPankouInfoJson(code);
        System.out.println(json);
    }

    @Test
    public void getStockIndustryInfoJson() {
        String json = tested.getStockIndustryInfoJson(code, 10);
        System.out.println(json);
    }

    @Test
    public void getStockCompBasicInfoJson() {
        String json =  tested.getStockCompBasicInfoJson(code);
        System.out.println(json);
    }

    @Test
    public void getStockNewsInfoJson(){
        String json = tested.getStockNewsInfoJson(code, 10, 1);
        System.out.println(json);
    }

    @Test
    public  void getStockAnnouncement(){
        String json = tested.getStockAnnouncementInfoJson(code, 10, 1);
        System.out.println(json);
    }


    @Test
    public void getHotRankInfoJson() {
        String json = tested.getHotRankInfoJson(10);
        System.out.println(json);
    }

    @Test
    public void getLongHuBangInfoJson() {
        String json = tested.getLongHuBangInfoJson("20160505");
        System.out.println(json);
    }

    @Test
    public void getRankInfoJson() {
        String json = tested.getRankInfoJson(10, false, Exchange.CHINA.toString(), StockType.SECOND_BOARD.toString(), OrderFactor.AMOUNT.toString());
        System.out.println(json);
    }

    @Test
    public void getNewsInfoJson() {
        String json = tested.getNewsInfoJson();
        System.out.println(json);
    }


}