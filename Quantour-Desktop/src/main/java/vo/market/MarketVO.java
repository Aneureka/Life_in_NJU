package vo.market;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Hiki on 2017/3/8.
 */
public class MarketVO {

    /**
     * 当日总交易量
     */
    public double dayVolume;


    /**
     * 当日涨停股票数
     */
    public long SLSNum;


    /**
     * 获得当日跌停股票数
     */
    public long DLSNum;


    /**
     * 当日涨幅超过5%的股票数
     */
    public long SFSNum;


    /**
     * 当日跌幅超过5%的股票数
     */
    public long DFSNum;

    /**
     * 开盘‐收盘大于5%*上一个交易日收盘价的股票个数
     */
    public long OCHFNum;


    /**
     * 开盘‐收盘小于-5%*上一个交易日收盘价的股票个数
     */
    public long OCLNFNum;

    /**
     * 市场中的股票涨幅分布
     */
    public int[] increaseDistribution;

    /**
     * 市场中的股票
     */
    public List<DefaultStockVO> stocks;

    public MarketVO(double dayVolume, long SLSNum, long DLSNum, long SFSNum, long DFSNum, long OCHFNum, long OCLNFNum, int[] increaseDistribution, List<DefaultStockVO> stocks) {
        this.dayVolume = dayVolume;
        this.SLSNum = SLSNum;
        this.DLSNum = DLSNum;
        this.SFSNum = SFSNum;
        this.DFSNum = DFSNum;
        this.OCHFNum = OCHFNum;
        this.OCLNFNum = OCLNFNum;
        this.increaseDistribution = increaseDistribution;
        this.stocks = stocks;
    }
    
}


