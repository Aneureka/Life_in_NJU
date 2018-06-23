package bl.backtest.universe;

import blservice.backtestblservice.UniverseBLService;
import dao.backtest.UniverseDao;
import dao.stock.StockDao;
import dataservice.backtestdata.UniverseDataService;
import dataservice.stock.StockDataService;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import po.stock.StockAttributesPO;
import util.exception.StockNotFoundException;
import vo.backtest.UniverseVO;
import vo.stock.StockAttributesVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/4/16.
 */
public class Universe implements UniverseBLService{

    UniverseDataService universeDataService;

    public Universe() {
        this.universeDataService = new UniverseDao();
    }

    @Override
    public void createNewUniverse(String universeName, List<String> universe) throws DuplicateName {
        universeDataService.addNewUniverse(universeName, universe);
    }

    @Override
    public void deleteUniverse(String universeName) {
        universeDataService.delUniverse(universeName);
    }

    @Override
    public List<UniverseVO> getAllUniverses() {
        return universeDataService.findAllUniverses().stream()
                .map(universePO -> new UniverseVO(universePO.getName(), universePO.getCodes()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUniverse(String universeName, List<String> universe) {
        universeDataService.updateUniverse(universeName, universe);
    }

    @Override
    public StockAttributesVO getStockAttributes(String codeOrName) throws StockNotFoundException {
        StockAttributesPO stockAttributesPO = new StockDao().getStockAttributes(codeOrName);

        String code = stockAttributesPO.getCode();
        String name = stockAttributesPO.getName();
        String section = stockAttributesPO.getSection();

        return new StockAttributesVO(code, name, section);
    }

}
