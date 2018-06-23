package presentation.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import vo.stock.StockItemVO;

import java.util.List;

/**
 * Created by Hitiger on 2017/4/19.
 * Description :
 */
public class MakeBarChart {

    // 成交量最低值
    private int minVolume;
    // 成交量最高值
    private int maxVolume;

    public void showVolumeChart(BarChart<String, Number> volumeBarChart, CategoryAxis volumeXAxis, NumberAxis volumeYAxis, List<StockItemVO> stockItemList) {
        XYChart.Series series = new XYChart.Series();
        int size = stockItemList.size();
        for (int i = 0; i < size; i++) {
            final StockItemVO stockItemVO = stockItemList.get(i);
            XYChart.Data<String, Number> data = new XYChart.Data<>(stockItemVO.date.toString(), (int)(stockItemVO.volume/1000));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldNode, Node newNode) {
                    if (newNode != null) {
                        if (stockItemVO.open < stockItemVO.close) {
                            newNode.setStyle("-fx-bar-fill: #e74c3c;");
                        } else {
                            newNode.setStyle("-fx-bar-fill: #2ecc71;");
                        }
                    }
                }
            });
            series.getData().add(data);
        }

        if (volumeBarChart.getData() != null) {
            volumeBarChart.getData().clear();

        }

        volumeYAxis.setAutoRanging(false);
        initMinAndMaxVolumeValue(stockItemList);
        volumeYAxis.setLowerBound(minVolume - 100);
        volumeYAxis.setUpperBound(maxVolume + 100);
        volumeYAxis.setTickUnit((maxVolume - minVolume) / 10);
        volumeXAxis.setAnimated(false);

        volumeBarChart.getData().add(series);
        volumeBarChart.setBarGap(0.0);
    }

    private void initMinAndMaxVolumeValue(List<StockItemVO> stockItemList) {
        minVolume = (int)(stockItemList.get(0).volume/1000);
        maxVolume = minVolume;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            int temp = (int)(stockItemList.get(i).volume/1000);
            if (temp < minVolume) {
                minVolume = temp;
            }
            if (temp > maxVolume) {
                maxVolume = temp;
            }
        }
    }
}
