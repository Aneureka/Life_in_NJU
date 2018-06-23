package presentation.util.history;

import vo.backtest.BacktestResultOverviewVO;
import vo.backtest.DailyCumReturnVO;
import vo.backtest.PositionRecordVO;
import vo.backtest.ReturnDistributionVO;

import java.util.ArrayList;

/**
 * Created by Hitiger on 2017/4/18.
 * Description : 回测结果
 */
public class ResultVo {
    private BacktestResultOverviewVO overviewVO;
    private DailyCumReturnVO dailyCumReturnVO;
    private ReturnDistributionVO returnDistributionVO;
    private ArrayList<PositionRecordVO> recordList;

    public ResultVo(BacktestResultOverviewVO overviewVO, DailyCumReturnVO dailyCumReturnVO,
                    ReturnDistributionVO returnDistributionVO, ArrayList<PositionRecordVO> recordList) {
        this.overviewVO = overviewVO;
        this.dailyCumReturnVO = dailyCumReturnVO;
        this.returnDistributionVO = returnDistributionVO;
        this.recordList = recordList;
    }

    public BacktestResultOverviewVO getOverviewVO() {
        return overviewVO;
    }

    public DailyCumReturnVO getDailyCumReturnVO() {
        return dailyCumReturnVO;
    }

    public ReturnDistributionVO getReturnDistributionVO() {
        return returnDistributionVO;
    }

    public ArrayList<PositionRecordVO> getRecordList() {
        return recordList;
    }
}
