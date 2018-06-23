package presentation.controller;

import controller.stock.StockController;
import controller.stock.StockControllerImpl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.util.MakeBarChart;
import presentation.util.datepickercell.RemoveAfterDateCell;
import presentation.util.datepickercell.RemoveBeforeDateCell;
import presentation.util.alert.AlertController;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import presentation.util.MySlider;
import presentation.util.candlestick.CandleStickChart;
import util.tool.NumberFormatter;
import vo.stock.StockItemVO;
import vo.stock.StockVO;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wyj on 2017/3/8.
 * Description:
 */
public class StockInfoController {

    @FXML
    private Pane candlestickPane;
    @FXML
    private Label stockName;
    @FXML
    private Label stockCode;
    @FXML
    private Label rmbIcon;
    @FXML
    private Label nowLabel;
    @FXML
    private ImageView increaseIcon;
    @FXML
    private Label changeLabel;
    @FXML
    private Label increaseLabel;
    @FXML
    private Label highLabel;
    @FXML
    private Label lowLabel;
    @FXML
    private Label openLabel;
    @FXML
    private Label closeLabel;
    @FXML
    private Label volumLabel;
    @FXML
    private Label adjcloseLabel;

    @FXML private Label ma5label;
    @FXML private Label ma10label;
    @FXML private Label ma30label;
    @FXML private Label ma60label;
    @FXML private Label ma120label;
    @FXML private Label ma240label;

    @FXML private Label volumeUnit;
    @FXML
    private Button dayKBtn;
    @FXML
    private Button weekKBtn;
    @FXML
    private Button monthKBtn;


    @FXML
    private Label sliderPicLabel;// 滑块
    @FXML
    private ProgressIndicator loadingIndicator;// 动画

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private BarChart<String, Number> volumeBarChart;
    @FXML
    private CategoryAxis volumeXAxis;
    @FXML
    private NumberAxis volumeYAxis;


    // K线图 DAY, OPEN, CLOSE, HIGH, LOW, AVERAGE,涨跌额，涨跌幅，成交量
    private StockController stockController;
    // 股票代码
    private String code;

    private CandleStickChart chart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private StockVO stockVO;
    private boolean isDay = false;
    private boolean isWeek = false;
    private boolean isMonth = false;
    private ArrayList<Label> averageLabelList;
    private MakeBarChart makeBarChart;

    public void launch(Pane mainPane, String code) throws TimeException, StockNotFoundException {
        this.code = code;
        averageLabelList = new ArrayList<>(Arrays.asList(ma5label, ma10label, ma30label, ma60label, ma120label, ma240label));
        initService();
        initDatePicker();
        initGraph();
        initData();
        makeBarChart = new MakeBarChart(); // 用于加载柱状图
    }

    private void initData() throws TimeException, StockNotFoundException {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            isDay = true;
            stockVO = stockController.findStock(code);
            StockItemVO startItemVO = stockVO.getStockItemList().get(0);
            StockItemVO endItemVO = stockVO.getStockItemList().get(stockVO.getStockItemList().size() - 1);
            initCandleStickGraph(stockVO);
            startDatePicker.setValue(startItemVO.date);
            endDatePicker.setValue(endItemVO.date);
        } else {
            stockVO = stockController.findStock(code, startDatePicker.getValue(), endDatePicker.getValue());
        }
        StockItemVO stockItemVO = stockVO.getStockItemList().get(stockVO.getStockItemList().size() - 1);
        // 初始化简略信息
        initGeneralLabel(stockItemVO);
    }

    private void initService() {
        stockController = new StockControllerImpl();
    }

    private void showDayk() {
        if (judgeTime()) {
            try {
                stockVO = stockController.findStock(code, startDatePicker.getValue(), endDatePicker.getValue());
//                StockItemVO stockItemVO = stockVO.getStockItemList().get(stockVO.getStockItemList().size() - 1);
                // 初始化简略信息
                // initGeneralLabel(stockItemVO);
            } catch (StockNotFoundException e) {
                AlertController.showNullWrongAlert(e.getMessage(), "查找失败");
            } catch (TimeException e) {
                AlertController.showNullWrongAlert(e.getMessage(), "查找失败");
            }

        }
        initCandleStickGraph(stockVO);
    }

    private void showWeekK() {
        if (judgeTime()) {
            try {
                stockVO = stockController.findWeekStock(code, startDatePicker.getValue(), endDatePicker.getValue());
            } catch (StockNotFoundException e) {
                AlertController.showNullWrongAlert(e.getMessage(), "查找失败");
            } catch (TimeException e) {
                AlertController.showNullWrongAlert(e.getMessage(), "查找失败");
            }
        }
        initCandleStickGraph(stockVO);
    }

    private void showMonthK() {
        if (judgeTime()) {
            try {
                stockVO = stockController.findMonthStock(code, startDatePicker.getValue(), endDatePicker.getValue());
            } catch (StockNotFoundException e) {
                AlertController.showNullWrongAlert(e.getMessage(), "查找失败");
            } catch (TimeException e) {
                AlertController.showNullWrongAlert(e.getMessage(), "查找失败");
            }
        }
        initCandleStickGraph(stockVO);
    }

    private void initCandleStickGraph(StockVO stockVO) {
        // 初始化 K 线图
        initGraph();
        // 为 K 线图添加数据
        CandleStickChart.createContent(chart, yAxis, stockVO.getStockItemList(), isDay);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // 显示成交量统计图
                makeBarChart.showVolumeChart(volumeBarChart, volumeXAxis, volumeYAxis, stockVO.getStockItemList());
                candlestickPane.getChildren().add(chart);
            }
        });
    }

    private void initGraph() {
        chart = null;
        xAxis = new CategoryAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        yAxis = new NumberAxis();
        chart = new CandleStickChart(xAxis, yAxis);

        chart.setLabelList(averageLabelList);

        // 设置 chart 大小
        chart.setPrefWidth(1000);
        chart.setPrefHeight(450);
        chart.setLayoutX(30);
        chart.setLayoutY(25);
        chart.setAnimated(false);
    }

    private void initGeneralLabel(StockItemVO stockItemVO) {
        boolean isIncrease = (stockItemVO.increase > 0);
        String increaseStyle = isIncrease ? "stock-increase" : "stock-decrease";
        stockName.setText(stockItemVO.name);
        stockCode.setText(stockItemVO.code);
        nowLabel.setText(NumberFormatter.format(stockItemVO.close));
        nowLabel.getStyleClass().setAll(increaseStyle);
        rmbIcon.getStyleClass().setAll(increaseStyle);
        increaseIcon.setImage(new Image(isIncrease ? "/img/up.png" : "/img/down.png"));
        changeLabel.setText((isIncrease ? "+" : "") + NumberFormatter.format(stockItemVO.change));
        changeLabel.getStyleClass().setAll("change-label", increaseStyle);
        increaseLabel.setText((isIncrease ? "+" : "") + NumberFormatter.formatToPercent(stockItemVO.increase) + "%");
        increaseLabel.getStyleClass().setAll("change-label", increaseStyle);

        highLabel.setText(NumberFormatter.format(stockItemVO.high));
        lowLabel.setText(NumberFormatter.format(stockItemVO.low));
        openLabel.setText(NumberFormatter.format(stockItemVO.open));
        closeLabel.setText(NumberFormatter.format(stockItemVO.close));
        volumLabel.setText(NumberFormatter.formatToBaseWan(stockItemVO.volume));
        adjcloseLabel.setText(NumberFormatter.format(stockItemVO.adjClose));
    }

    private void initDatePicker() {
        endDatePicker.setOnAction(e -> {
            startDatePicker.setDayCellFactory(new RemoveAfterDateCell(endDatePicker.getValue()));
            if (startDatePicker.getValue() != null) chooseType();
        });
        startDatePicker.setOnAction(e -> {
            endDatePicker.setDayCellFactory(new RemoveBeforeDateCell(startDatePicker.getValue()));
            if (endDatePicker.getValue() != null) chooseType();
        });
    }

    private void chooseType() {
        if (isMonth) {
            showMonthGraph();
        } else if (isWeek) {
            showWeekGraph();
        } else {
            showDayGraph();
        }
    }

    @FXML
    private void showDayGraph() {
        isDay = true;
        isWeek = false;
        isMonth = false;
        runAnimation(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                showDayk();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        runAnimation(false); // stop displaying the loading indicator
                        MySlider.moveSliderLabel(sliderPicLabel, 0);
                    }
                });
                return 1;
            }
        };
        new Thread(task).start();
    }

    @FXML
    private void showWeekGraph() {
        isDay = false;
        isWeek = true;
        isMonth = false;
        runAnimation(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                showWeekK();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        runAnimation(false); // stop displaying the loading indicator
                        MySlider.moveSliderLabel(sliderPicLabel, 120);
                    }
                });
                return 1;
            }
        };
        new Thread(task).start();
    }

    @FXML
    private void showMonthGraph() {
        isDay = false;
        isWeek = false;
        isMonth = true;
        runAnimation(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                showMonthK();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        runAnimation(false); // stop displaying the loading indicator
                        MySlider.moveSliderLabel(sliderPicLabel, 240);
                    }
                });
                return 1;
            }
        };
        new Thread(task).start();
    }

    private void runAnimation(boolean isRun) {
        volumeUnit.setVisible(!isRun);
        chart.setVisible(!isRun);
        volumeBarChart.setVisible(!isRun);
        loadingIndicator.setVisible(isRun);
        loadingIndicator.setProgress(-1.0f);
    }

    private boolean judgeTime() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null &&
                startDatePicker.getValue().isBefore(endDatePicker.getValue())) {
            return true;
        }
        return false;
    }
}
