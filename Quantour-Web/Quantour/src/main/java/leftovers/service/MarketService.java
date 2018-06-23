package leftovers.service;

import leftovers.datahelper.showapigetter.ShowapiJsonProducer;
import leftovers.datahelper.xueqiuspider.XueqiuJsonProducer;
import leftovers.model.Board;
import leftovers.model.StockCurInfo;
import leftovers.repository.BoardRepository;
import leftovers.repository.MarketRepository;
import leftovers.repository.StockCurInfoRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by Hiki on 2017/5/17.
 */

@Service
public class MarketService {

    @Autowired
    private XueqiuJsonProducer xueqiuJsonProducer;

    @Autowired
    private ShowapiJsonProducer showapiJsonProducer;

    @Autowired
    private StockCurInfoRepository stockCurInfoRepository;

    @Autowired
    private BoardRepository boardRepository;

    public String getHotRankInfo(int size) {
        return xueqiuJsonProducer.getHotRankInfoJson(size);
    }

    public String getLongHuBangInfo(String date) {
        return xueqiuJsonProducer.getLongHuBangInfoJson(date);
    }

    public String getRankInfo(int size, String order, String exchange, String stockType, String orderBy) {
        boolean esc = order.equals("esc") ? true : false;
        return xueqiuJsonProducer.getRankInfoJson(size, esc, exchange, stockType, orderBy);
    }

    public String getNewsInfo(int page) {
        try {
            return showapiJsonProducer.getMarketNewsInfoJson(page);
        } catch (IOException e) {
            return new JSONObject()
                    .put("showapi_res_code", -1)
                    .toString();
        }
    }

    public String getStockListByBoard(Map<String, String[]> params) throws IOException, URISyntaxException {
        return new MarketRepository().getStockListByBoard(params);
    }

    public List<StockCurInfo> getStockList(String board, Sort sort) {
        return stockCurInfoRepository.findByCodeIn(boardRepository.findCodeByBoard(board), sort);
    }

    public Page<StockCurInfo> getStockListByPageable(String board, Pageable pageable) {
        return stockCurInfoRepository.findByCodeIn(boardRepository.findCodeByBoard(board), pageable);
    }
}
