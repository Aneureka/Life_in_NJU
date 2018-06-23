package blservice.backtestblservice;

import com.sun.org.apache.bcel.internal.generic.LSTORE;
import util.enums.Benchmark;
import util.enums.RateType;
import util.exception.StockNotFoundException;
import vo.backtest.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by 97257 on 2017/4/2.
 */
public interface BackTestBLService {

    // 提供回测公共接口，给BacktestController调用

    /**
     * 动量策略回测的接口
     * @param beginTime 回测开始时间
     * @param endTime 回测结束时间
     * @param benchmark 基准类型
     * @param universeNames 股票池名称
     * @param capitalBase 起始资金
     * @param formRate 形成期
     * @param refreshRate 持有期
     */
    public void runMomentum(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames, double capitalBase, int formRate, int refreshRate) throws StockNotFoundException;


    /**
     * 均值回归策略回测的接口
     * @param beginTime 回测开始时间
     * @param endTime 回测结束时间
     * @param benchmark 基准类型
     * @param universeNames 股票池名称
     * @param capitalBase 起始资金
     * @param pma 日均线类型（其实也就是形成期）
     * @param refreshRate 持有期
     */
    public void runMeanRevision(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames, double capitalBase, int pma, int refreshRate) throws StockNotFoundException;


    /**
     * 获取回测总体结果
     * @return
     */
    public BacktestResultOverviewVO getBacktestResultOverview();


    /**
     * 对于不同的形成期/持有期（均值回归只有形成期pma），超额收益率与年胜率与另一个变量的关系
     * @param rateType
     * @param rate
     */
    public List<ChangingRateVO> getChangingWP(RateType rateType, int rate);


    /**
     * 获得每个周期的收益率
     * @return
     */
    public ReturnDistributionVO getReturns();


    /**
     * 获得持仓记录
     * @return
     */
    public List<PositionRecordVO> getPositionRecord();








}
