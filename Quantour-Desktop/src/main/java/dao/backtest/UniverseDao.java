package dao.backtest;

import datahelper.backtest.UniverseDataHelper;
import datahelper.backtest.UniverseDataHelperImpl;
import dataservice.backtestdata.UniverseDataService;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import po.backtest.UniversePO;

import java.util.List;

/**
 * Created by Hiki on 2017/4/16.
 */
public class UniverseDao implements UniverseDataService {

    private UniverseDataHelper universeDataHelper;


    public UniverseDao() {
        this.universeDataHelper = new UniverseDataHelperImpl();
    }

    @Override
    public void addNewUniverse(String universeName, List<String> universe) throws DuplicateName {
        universeDataHelper.addUniverse(universeName, universe);
    }

    @Override
    public void delUniverse(String universeName) {
        universeDataHelper.delUniverse(universeName);
    }

    @Override
    public List<UniversePO> findAllUniverses() {
        return universeDataHelper.findAllUniverses();
    }

    @Override
    public void updateUniverse(String universeName, List<String> universe) {
        universeDataHelper.updateUniverse(universeName, universe);
    }

}
