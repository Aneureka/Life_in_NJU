package presentation.util.history;

import util.tool.NumberFormatter;
import vo.backtest.BacktestResultOverviewVO;
import vo.backtest.DailyCumReturnVO;
import vo.backtest.PositionRecordVO;
import vo.backtest.ReturnDistributionVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Hitiger on 2017/4/18.
 * Description : 用于记录回测历史
 */
public class History {
    private LocalDateTime histime;
    private double returnPrice;
    private Condition condition;
    private ResultVo resultVo;

    public History(LocalDateTime histime, double returnPrice, Condition condition, ResultVo resultVo) {
        this.histime = histime;
        this.returnPrice = returnPrice;
        this.condition = condition;
        this.resultVo = resultVo;
    }

    public LocalDateTime getHistime() {
        return histime;
    }

    public String getPoolKind() {
        return condition.getPoolKind();
    }
    public String getType() {
        return condition.getType();
    }

    public String getSe() {
        return (condition.getStart().toString() + " 至 " + condition.getEnd().toString());
    }

    public double getAmount() {
        return condition.getAmount();
    }

    public String getReturnPrice() {
        return NumberFormatter.formatToPercent(returnPrice) + "%";
    }

    public Condition getCondition() {
        return condition;
    }

    public ResultVo getResultVo() {
        return resultVo;
    }

}
