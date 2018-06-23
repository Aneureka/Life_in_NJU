package controller.backtest;

import bl.backtest.BackTest;
import blservice.backtestblservice.BackTestBLService;
import util.enums.Benchmark;
import util.enums.RateType;
import util.exception.StockNotFoundException;
import vo.backtest.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by 97257 on 2017/4/2.
 */
public class BackTestControllerImpl implements BackTestController{

    BackTestBLService backTestBLService;

    public BackTestControllerImpl() {
        try {
            this.backTestBLService = new BackTest();
        } catch (StockNotFoundException e) {
            System.out.println("BacktestBL获取不到");
        }
    }


    @Override
    public void runMomentum(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames, double capitalBase, int formRate, int refreshRate) throws StockNotFoundException {
        backTestBLService.runMomentum(beginTime, endTime, benchmark, universeNames, capitalBase, formRate, refreshRate);
    }

    @Override
    public void runMeanRevision(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames, double capitalBase, int pma, int refreshRate) throws StockNotFoundException {
        backTestBLService.runMeanRevision(beginTime, endTime, benchmark, universeNames, capitalBase, pma, refreshRate);
    }

    @Override
    public BacktestResultOverviewVO getBacktestResultOverview() {
        return backTestBLService.getBacktestResultOverview();
    }

    @Override
    public List<ChangingRateVO> getChangingWP(RateType rateType, int rate) {
        return backTestBLService.getChangingWP(rateType, rate);
    }

    @Override
    public ReturnDistributionVO getReturns() {
        return backTestBLService.getReturns();
    }


    @Override
    public List<PositionRecordVO> getPositionRecord() {
        return backTestBLService.getPositionRecord();
    }
}
