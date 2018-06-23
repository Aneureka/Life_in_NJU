package controller.backtest;

import bl.backtest.universe.Universe;
import blservice.backtestblservice.UniverseBLService;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import util.exception.StockNotFoundException;
import vo.backtest.UniverseVO;
import vo.stock.StockAttributesVO;

import java.util.List;

/**
 * Created by Hiki on 2017/4/16.
 */
public class UniverseControllerImpl implements UniverseController{

    UniverseBLService universeBLService;

    public UniverseControllerImpl() {
        this.universeBLService = new Universe();
    }

    @Override
    public void createNewUniverse(String universeName, List<String> universe) throws DuplicateName {
        universeBLService.createNewUniverse(universeName, universe);
    }

    @Override
    public void deleteUniverse(String universeName) {
        universeBLService.deleteUniverse(universeName);
    }

    @Override
    public List<UniverseVO> getAllUniverses() {
        return universeBLService.getAllUniverses();
    }

    @Override
    public void updateUniverse(String universeName, List<String> universe) {
        universeBLService.updateUniverse(universeName, universe);
    }

    @Override
    public StockAttributesVO getStockAttributes(String code) throws StockNotFoundException {
        return universeBLService.getStockAttributes(code);
    }

}
