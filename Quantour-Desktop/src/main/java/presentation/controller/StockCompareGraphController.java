package presentation.controller;

import controller.stock.StockControllerImpl;
import presentation.util.MyDatePicker;
import presentation.util.alert.WarningLabelTimeLine;
import presentation.util.datepickercell.RemoveAfterDateCell;
import presentation.util.datepickercell.RemoveBeforeDateCell;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import util.tool.NumberFormatter;
import vo.stock.StockItemVO;
import vo.stock.StockVO;

import java.util.List;

/**
 * Created by Hitiger on 2017/3/8.
 * Description :
 */
public class StockCompareGraphController {

    @FXML private TextField comparedField1;
    @FXML private TextField comparedField2;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button comparedBtn;

    @FXML private Pane generalPane;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label stockOneName;
    @FXML private Label stockOneCode;
    @FXML private Label stockOneHigh;
    @FXML private Label stockOneLow;
    @FXML private Label stockOneChaPercent;
    @FXML private Label stockOneChaAmount;
    @FXML private Label stockTwoName;
    @FXML private Label stockTwoCode;
    @FXML private Label stockTwoHigh;
    @FXML private Label stockTwoLow;
    @FXML private Label stockTwoChaPercent;
    @FXML private Label stockTwoChaAmount;
    @FXML private Label lrVariance1;
    @FXML private Label lrVariance2;

    @FXML private Pane scrollGraphPane;
    @FXML private LineChart<String, Double> dayCloseChart;
    @FXML private CategoryAxis xAxisClose;
    @FXML private NumberAxis yAxisClose;
    @FXML private LineChart<String, Double> dayLogReturnChart;
    @FXML private CategoryAxis xAxisReturn;
    @FXML private NumberAxis yAxisReturn;

    @FXML private Label exceptionLabel;

    private Pane mainPane;

    private StockControllerImpl stockController;

    public void launch(Pane mainPane) {
        this.mainPane = mainPane;

        stockController = new StockControllerImpl();

        initComponents();
    }

    private void initComponents() {
        String instead = "-";
        startDateLabel.setText("                -");
        endDateLabel.setText(instead);

        stockOneName.setText(instead);
        stockOneCode.setText(instead);
        stockOneHigh.setText(instead);
        stockOneLow.setText(instead);
        stockOneChaPercent.setText(instead);
        stockOneChaAmount.setText(instead);
        lrVariance1.setText(instead);

        stockTwoName.setText(instead);
        stockTwoCode.setText(instead);
        stockTwoHigh.setText(instead);
        stockTwoLow.setText(instead);
        stockTwoChaPercent.setText(instead);
        stockTwoChaAmount.setText(instead);
        lrVariance2.setText(instead);
        initDatePicker();
    }

    private void initDatePicker() {
        MyDatePicker.removeDateCell(startDatePicker, endDatePicker);
    }

    private void initGeneralInfo(StockVO stockOneVO, StockVO stockTwoVO) {
        //初始化时间
        lrVariance1.setText(NumberFormatter.formatToScientific(stockOneVO.logReturnVariance));
        lrVariance2.setText(NumberFormatter.formatToScientific(stockTwoVO.logReturnVariance));
        startDateLabel.setText(String.valueOf(startDatePicker.getValue()));
        endDateLabel.setText(String.valueOf(endDatePicker.getValue()));
        initLabel(stockOneVO.getStockItemList().get(0), stockOneName, stockOneCode, stockOneHigh, stockOneLow, stockOneChaPercent, stockOneChaAmount);
        initLabel(stockTwoVO.getStockItemList().get(0), stockTwoName, stockTwoCode, stockTwoHigh, stockTwoLow, stockTwoChaPercent, stockTwoChaAmount);
    }

    private void initLabel(StockItemVO stockItemVO, Label name, Label code, Label high, Label low, Label percent, Label change) {
        name.setText(stockItemVO.name);
        code.setText(stockItemVO.code);
        high.setText(NumberFormatter.format(stockItemVO.high));
        low.setText(NumberFormatter.format(stockItemVO.low));
        percent.setText(NumberFormatter.formatToPercent(stockItemVO.increase) + "%");
        change.setText(NumberFormatter.format(stockItemVO.change));
        if (stockItemVO.increase < 0) {
            percent.getStyleClass().add("stock-decrease");
            change.getStyleClass().add("stock-decrease");
        } else {
            percent.getStyleClass().add("stock-increase");
            change.getStyleClass().add("stock-increase");
        }
    }

    private void initChart(List<StockItemVO> stockOne, List<StockItemVO> stockTwo) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(stockOne.get(0).name);
        stockClose(series1, stockOne);
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(stockTwo.get(0).name);
        stockClose(series2, stockTwo);

        yAxisClose.setAutoRanging(false);
        double minCloseOne = getMinCloseValue(stockOne);
        double minCloseTwo = getMinCloseValue(stockTwo);
        double maxCloseOne = getMaxCloseValue(stockOne);
        double maxCloseTwo = getMaxCloseValue(stockTwo);
        double minCloseValue = (minCloseOne < minCloseTwo ? minCloseOne : minCloseTwo);
        double maxCloseValue = (maxCloseOne > maxCloseTwo ? maxCloseOne : maxCloseTwo);
        yAxisClose.setLowerBound(minCloseValue * 0.98);
        yAxisClose.setUpperBound(maxCloseValue * 1.02);
        yAxisClose.setTickUnit((maxCloseValue * 1.02 - minCloseValue * 0.98) / 10);
        xAxisClose.setAnimated(false);

        if (dayCloseChart.getData() != null) {
            dayCloseChart.getData().clear();
        }
        dayCloseChart.getData().addAll(series1, series2);



        XYChart.Series series3 = new XYChart.Series();
        series3.setName(stockOne.get(0).name);
        stockLogReturn(series3, stockOne);
        XYChart.Series series4 = new XYChart.Series();
        series4.setName(stockTwo.get(0).name);
        stockLogReturn(series4, stockTwo);

        yAxisReturn.setAutoRanging(false);
        double minReturnOne = getMinReturnValue(stockOne);
        double minReturnTwo = getMinReturnValue(stockTwo);
        double maxReturnOne = getMaxReturnValue(stockOne);
        double maxReturnTwo = getMaxReturnValue(stockTwo);
        double minReturnValue = (minReturnOne < minReturnTwo ? minReturnOne : minReturnTwo);
        double maxReturnValue = (maxReturnOne > maxReturnTwo ? maxReturnOne : maxReturnTwo);
        yAxisReturn.setLowerBound(minReturnValue * ((minReturnValue > 0) ? 0.8 : 1.2));
        yAxisReturn.setUpperBound(maxReturnValue * 1.2);
        yAxisReturn.setTickUnit((maxReturnValue * 1.2 - minReturnValue * 0.8) / 10);
        xAxisReturn.setAnimated(false);

        if (dayLogReturnChart.getData() != null) {
            dayLogReturnChart.getData().clear();
        }
        dayLogReturnChart.getData().addAll(series3, series4);

        boolean isAdjust = stockOne.size() > 60 || stockTwo.size() > 60;
        dayCloseChart.setCreateSymbols(!isAdjust);
        dayLogReturnChart.setCreateSymbols(!isAdjust);

    }

    private double getMinCloseValue(List<StockItemVO> stockItemList) {
        double min = stockItemList.get(0).close;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            double temp = stockItemList.get(i).close;
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    private double getMaxCloseValue(List<StockItemVO> stockItemList) {
        double max = stockItemList.get(0).close;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            double temp = stockItemList.get(i).close;
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    private double getMinReturnValue(List<StockItemVO> stockItemList) {
        double min = stockItemList.get(0).logReturn;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            double temp = stockItemList.get(i).logReturn;
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }

    private double getMaxReturnValue(List<StockItemVO> stockItemList) {
        double max = stockItemList.get(0).logReturn;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            double temp = stockItemList.get(i).logReturn;
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    private void stockClose(XYChart.Series series, List<StockItemVO> list) {
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(list.get(i).date.toString(), list.get(i).close));
        }
    }


    private void stockLogReturn(XYChart.Series series, List<StockItemVO> list) {
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(list.get(i).date.toString(), list.get(i).logReturn));
        }
    }

    @FXML
    private void showCompareGraph() {

        if (!judgeBlank()) {
            try {
                exceptionLabel.setVisible(false);
                StockVO stockOne = stockController.findStock(comparedField1.getText(), startDatePicker.getValue(), endDatePicker.getValue());
                StockVO stockTwo = stockController.findStock(comparedField2.getText(), startDatePicker.getValue(), endDatePicker.getValue());

                initGeneralInfo(stockOne, stockTwo);
                initChart(stockOne.stockItemList, stockTwo.stockItemList);
            } catch (StockNotFoundException e) {
                WarningLabelTimeLine.timeLineWarningContent(exceptionLabel, "找不到股票");
            } catch (TimeException e) {
                WarningLabelTimeLine.timeLineWarningContent(exceptionLabel, "日期错误");
            }
        } else {
            WarningLabelTimeLine.timeLineWarningContent(exceptionLabel, "内容不能为空");
        }
    }

    private boolean judgeBlank() {
        boolean stockone = comparedField1.getText().equals("");
        boolean stocktwo = comparedField2.getText().equals("");
        boolean dateone = startDatePicker.getValue() == null;
        boolean dateTwo = endDatePicker.getValue() == null;

        return stockone || stocktwo || dateone || dateTwo;
    }
}
