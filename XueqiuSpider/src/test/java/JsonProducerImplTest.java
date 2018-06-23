import constant.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Hiki on 2017/5/14.
 */
class JsonProducerImplTest {

    JsonProducerImpl tested;

    String code;

    @BeforeEach
    void setUp() {
        tested = new JsonProducerImpl();
        code = "SZ000001";
    }

    @Test
    void getStockQuoteJson() {
        String json = tested.getStockQuoteJson(code);
        System.out.println(json);
    }

    @Test
    void getStockKInfoJson() {
        LocalDateTime begin = LocalDateTime.parse("2005-01-01T01:00:00.010");
        LocalDateTime end = LocalDateTime.parse("2009-01-01T01:00:00.010");
        String json = tested.getStockKInfoJson(code, Period.DAY, PriceType.BEFORE, begin.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(json);
    }

    @Test
    void getStockMinInfoJson() {
        String json = tested.getStockMinInfoJson(code);
        System.out.println(json);
    }

    @Test
    void getStockPankouInfoJson() {
        String json = tested.getStockPankouInfoJson(code);
        System.out.println(json);
    }

    @Test
    void getStockIndustryInfoJson() {
        String json = tested.getStockIndustryInfoJson(code, 10);
        System.out.println(json);
    }

    @Test
    void getStockCompBasicInfoJson() {
        String json =  tested.getStockCompBasicInfoJson(code);
        System.out.println(json);
    }

    @Test
    void getStockNewsInfoJson(){
        String json = tested.getStockNewsInfoJson(code, 10);
        System.out.println(json);
    }

    @Test
    void getStockAnnouncement(){
        String json = tested.getStockAnnouncementInfoJson(code, 10);
        System.out.println(json);
    }


    @Test
    void getHotRankInfoJson() {
        String json = tested.getHotRankInfoJson(10);
        System.out.println(json);
    }

    @Test
    void getLongHuBangInfoJson() {
        String json = tested.getLongHuBangInfoJson("20160505");
        System.out.println(json);
    }

    @Test
    void getRankInfoJson() {
        String json = tested.getRankInfoJson(10, false, Exchange.CHINA, StockType.SECOND_BOARD, OrderFactor.AMOUNT);
        System.out.println(json);
    }

    @Test
    void getNewsInfoJson() {
        String json = tested.getNewsInfoJson();
        System.out.println(json);
    }

}