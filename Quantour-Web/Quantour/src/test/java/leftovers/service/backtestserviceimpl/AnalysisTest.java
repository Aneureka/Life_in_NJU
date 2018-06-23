package leftovers.service.backtestserviceimpl;

import com.alibaba.fastjson.JSON;
import leftovers.model.backtest.BenchmarkPortfolio;
import leftovers.model.backtest.Portfolio;
import leftovers.util.DataFrame;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevin on 2017/6/13.
 */
public class AnalysisTest {

    private Analysis tested;

    @Before
    public void setup(){
        List<Portfolio> portfolios = new ArrayList<>();
        List<BenchmarkPortfolio> benchmarkPortfolios = new ArrayList<>();

        LocalDate date = LocalDate.of(2016, 1, 1);
        for (int i = 0; i < 1000; i++) {
            portfolios.add(new Portfolio());
            portfolios.get(i).setDate(date.plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + "");
            portfolios.get(i).setTotal_value(100 + i);

            benchmarkPortfolios.add(new BenchmarkPortfolio());
            benchmarkPortfolios.get(i).setDate(date.plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + "");
            benchmarkPortfolios.get(i).setTotal_value(200 + i);
        }

        tested = new Analysis(portfolios, benchmarkPortfolios);
    }

    @Test
    public void getAlphas() throws Exception {
        DataFrame res = tested.getAlphas();
    }

    @Test
    public void getBetas() throws Exception {
        DataFrame res = tested.getBetas();
    }

    @Test
    public void getMaxDrawdowns() throws Exception {
        DataFrame res = tested.getMaxDrawdowns(Analysis.STRATEGY);
    }

    @Test
    public void getVolatilitys() throws Exception {
        DataFrame res = tested.getVolatilitys(Analysis.STRATEGY);
    }

    @Test
    public void getReturns() throws Exception {
        DataFrame res = tested.getReturns(Analysis.STRATEGY);
        System.out.println(res.toJSON());
    }

    @Test
    public void getSharpRatios() throws Exception {
        DataFrame res = tested.getSharpRatios(Analysis.STRATEGY);
        DataFrame strategyVolatilitys = tested.getVolatilitys(Analysis.STRATEGY);
        DataFrame benchmarkVolatilitys = tested.getVolatilitys(Analysis.BENCHMARK);

        org.json.JSONObject jsonObject = new JSONObject(){
            {
                put("strategyReturns", JSON.parse(strategyVolatilitys.toJSON()));
                put("benchmarkReturns", JSON.parse(benchmarkVolatilitys.toJSON()));
            }
        };

        JSONObject jsonObject1 = new JSONObject(){
            {
                put("sharp_ratios", JSON.parse(res.toJSON()));
                put("volatilitys", JSON.parse(jsonObject.toString()));
            }
        };

        System.out.println(jsonObject1.toString());
    }

}