package controller.backtest;

import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import util.exception.StockNotFoundException;
import vo.backtest.UniverseVO;
import vo.stock.StockAttributesVO;

import java.util.List;

/**
 * Created by Hiki on 2017/4/16.
 */
public interface UniverseController {

    /**
     * 创建新的股票池
     * @param universeName
     * @param universe
     */
    public void createNewUniverse(String universeName, List<String> universe) throws DuplicateName;


    /**
     * 删除股票池
     * @param universeName
     */
    public void deleteUniverse(String universeName);


    /**
     * 获取所有自建的股票池
     * @return 所有自建的股票池
     */
    public List<UniverseVO> getAllUniverses();


    /**
     * 更新股票池
     * @param universeName 股票池名称
     * @param universe 股票池包含的股票代码
     */
    public void updateUniverse(String universeName, List<String> universe);


    /**
     * 获取股票的属性
     * @param code 股票代码
     * @return 股票的属性
     */
    public StockAttributesVO getStockAttributes(String code) throws StockNotFoundException;

}
