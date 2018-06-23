package leftovers.service;

import leftovers.enums.DefaultAlgorithmInfo;
import leftovers.model.Algorithm;
import leftovers.model.SimpleAlgorithm;
import leftovers.repository.AlgorithmRepository;
import leftovers.repository.BacktestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Hiki on 2017/6/9.
 */

@Service
public class AlgorithmService {

    @Autowired
    AlgorithmRepository algorithmRepository;

    @Autowired
    BacktestHistoryRepository backtestHistoryRepository;


    /**
     * 策略算法增删改查
     */

    public long removeAlgorithm(String algoId){
        backtestHistoryRepository.deleteByAlgoId(algoId);
        return algorithmRepository.deleteByAlgoId(algoId);
    }

    public long updateAlgorithm(Algorithm algorithm){
        if (algorithmRepository.findOne(algorithm.getAlgoId()) == null)
            return 0;
        algorithmRepository.saveAndFlush(algorithm);
        return 1;
    }

    public long createAlgorithm(Algorithm algorithm){
        if (algorithmRepository.findOne(algorithm.getAlgoId()) != null)
            return 0;
        algorithmRepository.saveAndFlush(algorithm);
        return 1;
    }

    public Algorithm findAlgorithmById(String algoId){
        return algorithmRepository.findOne(algoId);
    }

    public List<Algorithm> findAlgorithmsByUsername(String username){
        List<Algorithm> algorithms = algorithmRepository.findByUsername(username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        algorithms.sort((o1, o2) -> LocalDateTime.parse(o2.getTime(), formatter).compareTo(LocalDateTime.parse(o1.getTime(), formatter)));
        return algorithms;
    }

    public long createDefaultAlgorithm(SimpleAlgorithm simpleAlgorithm){
        Algorithm algorithm = new Algorithm(simpleAlgorithm.getAlgoId(), simpleAlgorithm.getAlgoName(), simpleAlgorithm.getUsername(), simpleAlgorithm.getTime(), DefaultAlgorithmInfo.CODE, DefaultAlgorithmInfo.BEGIN_DATE, DefaultAlgorithmInfo.END_DATE, DefaultAlgorithmInfo.STOCK_START_CASH, DefaultAlgorithmInfo.BENCHMARK);
        if (algorithmRepository.findOne(algorithm.getAlgoId()) != null)
            return 0;
        algorithmRepository.save(algorithm);
        return 1;
    }


    private String buildResultMessage(int retCode){
        return new org.json.JSONObject().put("retCode", retCode).toString();
    }
}
