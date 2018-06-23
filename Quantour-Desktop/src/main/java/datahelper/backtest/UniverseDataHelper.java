package datahelper.backtest;

import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import po.backtest.UniversePO;

import java.util.List;

/**
 * Created by Hiki on 2017/4/16.
 */
public interface UniverseDataHelper {

    /**
     * 创建新的股票池
     * @param universeName
     * @param universe
     */
    public void addUniverse(String universeName, List<String> universe) throws DuplicateName;

    /**
     * 删除股票池
     * @param universeName
     */
    public void delUniverse(String universeName);

    /**
     * 查找所有自建的股票池
     * @return 所有自建的股票池
     */
    public List<UniversePO> findAllUniverses();

    /**
     * 更新股票池
     * @param universeName 股票池名称
     * @param universe 股票池包含的股票代码
     */
    public void updateUniverse(String universeName, List<String> universe);

}
