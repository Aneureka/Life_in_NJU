package leftovers.datahelper.xueqiuspider.helper;

import leftovers.datahelper.xueqiuspider.constant.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * Created by Hiki on 2017/6/4.
 */
public class UrlGeneratorTest {

    UrlGenerator tested;
    String code;

    @Before
    public void setUp() throws Exception {
        tested = new UrlGenerator();
        code = "SZ000001";
    }

    @Test
    void generateMainUrl() {
        String url = tested.generateMainUrl();
        System.out.println(url);
    }

    @Test
    void generateStockQuoteUrl() {
        String url = tested.generateStockQuoteUrl(code);
        System.out.println(url);
    }

    @Test
    void generateStockKInfoUrl() {
        LocalDateTime begin = LocalDateTime.parse("2005-01-01T01:00:00.010");
        LocalDateTime end = LocalDateTime.parse("2009-01-01T01:00:00.010");
//        System.out.println(begin.toEpochSecond(ZoneOffset.UTC));
//        System.out.println(begin.atZone(ZoneId.systemDefault()).toEpochSecond());

        String url = tested.generateStockKInfoUrl(code, Period.DAY, PriceType.BEFORE, begin.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(url);
    }

    @Test
    void generateStockMinInfoUrl() {
        String url = tested.generateStockMinInfoUrl(code);
        System.out.println(url);
    }

    @Test
    void generateStockPankouInfoUrl() {
        String url = tested.generateStockPankouInfoUrl(code);
        System.out.println(url);
    }

    @Test
    void generateStockIndustryInfoUrl() {
        String url = tested.generateStockIndustryInfoUrl(code, 10);
        System.out.println(url);
    }

    @Test
    void generateStockCompBasicInfoUrl() {
        String url = tested.generateStockCompBasicInfoUrl(code);
        System.out.println(url);

    }

    @Test
    void generateStockNewsInfoUrl(){
        String url = tested.generateStockNewsInfoUrl(code, 10, 1);
        System.out.println(url);
    }

    @Test
    void generateStockAnnouncementInfoUrl(){
        String url = tested.generateStockAnnouncementInfoUrl(code, 10, 1);
        System.out.println(url);
    }


    @Test
    void generateHotRankInfoUrl() {
        String url = tested.generateHotRankInfoUrl(10);
        System.out.println(url);
    }

    @Test
    void generateLongHuBangInfoUrl() {
        String url = tested.generateLongHuBangInfoUrl("20170514");
        System.out.println(url);
    }

    @Test
    void generateRankInfoUrl() {
        String url = tested.generateRankInfoUrl(10, true, Exchange.CHINA, StockType.SH_A, OrderFactor.VOLUMN);
        System.out.println(url);

    }

    @Test
    void generateNewsInfoUrl() {
        String url = tested.generateNewsInfoUrl();
        System.out.println(url);
    }


}