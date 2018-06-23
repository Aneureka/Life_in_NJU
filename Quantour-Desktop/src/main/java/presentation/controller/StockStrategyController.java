package presentation.controller;

import controller.backtest.BackTestController;
import controller.backtest.BackTestControllerImpl;
import controller.backtest.UniverseController;
import controller.backtest.UniverseControllerImpl;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import presentation.ui.SelfSelectStockPane;
import presentation.util.*;
import javafx.scene.control.TreeTableColumn;
import presentation.util.alert.WarningLabelTimeLine;
import presentation.util.datepickercell.RemoveAfterDateCell;
import presentation.util.datepickercell.RemoveBeforeDateCell;
import presentation.util.history.Condition;
import presentation.util.history.History;
import presentation.util.history.LoopBackHistory;
import presentation.util.history.ResultVo;
import util.enums.Benchmark;
import util.enums.RateType;
import util.tool.NumberFormatter;
import vo.backtest.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hitigerzzz on 2017/3/27.
 */
public class StockStrategyController {

    @FXML private Pane makePane;
    @FXML private Menu boardMenu;
    @FXML private Menu selfMenu;
    // 制定策略
    @FXML private Button momentumBtn;
    @FXML private Button averageBtn;
    @FXML private Label sliderStrategyLabel;// 滑块
    @FXML private TextField generateField;
    @FXML private ComboBox<Integer> generateBox;
    @FXML private TextField holdField;
    @FXML private TextField initialFundField;
    @FXML private DatePicker strategyStartDatePick;
    @FXML private DatePicker strategyEndDatePick;
    @FXML private ComboBox incomeStandardComb;
    @FXML private MenuButton stockPoolComb;

    // 加载动画
    @FXML private Pane coverPane;
    @FXML private Pane animationPane;
    @FXML private ProgressIndicator loadingIndicator;// 动画组件
    @FXML private Label loadingLabel;

    // 回测结果
    @FXML private Pane  graphPane;
    @FXML private Pane  resultPane;
    @FXML private Button returnLineBtn;
    @FXML private Button returnBarBtn;
    @FXML private Label rateReturnLabel;
    @FXML private Label standardRateReturnLabel;
    @FXML private Label alphaLabel;
    @FXML private Label betaLabel;
    @FXML private Label sharpLabel;
    @FXML private Label returnVolatilityLabel;
    @FXML private Label maxReturnLabel;
    @FXML private Label totalReturnLabel;
    @FXML private LineChart<String, Double> cumulativeChart;
    @FXML private BarChart relativeIndexBarChart;
    @FXML private CategoryAxis barChartXAxis;
    @FXML private Label standardLabel;
    @FXML private NumberAxis barChartYAxis;
    @FXML private PieChart returnCyclePicChart;
    @FXML private Label positiveCycleLabel;
    @FXML private Label negativeCycleLabel;
    @FXML private Label winRateLabel;
    @FXML private DatePicker showStartDatePicker;
    @FXML private DatePicker showEndDatePicker;

    @FXML private TreeTableView holdRecordTable;
    @FXML private TreeTableColumn<Log, String> dateColumn;
    @FXML private TreeTableColumn<Log, String> codeColumn;
    @FXML private TreeTableColumn<Log, String> amountColumn;
    @FXML private TreeTableColumn<Log, String> capitalColumn;
    @FXML private TreeTableColumn<Log, String> changeColumn;

    // 回测历史
    @FXML private Pane historyPane;
    @FXML private TableView historyTable;
    @FXML private TableColumn historyTimeCol;
    @FXML private TableColumn historyPoolCol;
    @FXML private TableColumn historyTypeCol;
    @FXML private TableColumn historySECol;
    @FXML private TableColumn historyAmountCol;
    @FXML private TableColumn historyReturnCol;
    @FXML private TableColumn historyResultCol;
    // 滑块
    @FXML private Label sliderGraphLabel;
    @FXML private Pane returnLinePane;
    @FXML private Pane returnBarPane;

    // 超额收益率和策略胜率与不同形成期/持有期的关系图
    @FXML private Pane winRatePane;
    @FXML private TableView winRateTable;
    @FXML private TableColumn relativeStrengthCycleCol;
    @FXML private TableColumn excessReturnCol;
    @FXML private TableColumn yearWinRateCol;
    @FXML private AreaChart excessReturnAreaChart;
    @FXML private AreaChart winRateAreaChart;
    @FXML private Label winRateStartDateLabel;
    @FXML private Label winRateEndDateLabel;
    @FXML private Label strategyLabel;
    @FXML private ComboBox basePeriodComb;
    @FXML private TextField dayField;
    @FXML private ComboBox<Integer> dayComb;
    @FXML private HBox momentumHBox;
    @FXML private HBox meanReversionHBox;

    @FXML private Label strategyTipLabel;
    @FXML private Label winRateTipLabel;

    @FXML private Label warningLabel;

    private Pane mainPane;
    private BackTestController backTestController;
    private UniverseController universeController;
    private boolean isMomentum = true; // 是否是动量策略,回测系统
    private boolean isWinMomentum = true; // 是否是动量策略,胜率分析
    private ArrayList<String> poolType = new ArrayList<>(); // 存储股票的股票池
    private String poolKind;
    private boolean isBoardPool = false;// 是否是板块股票
    private boolean isSelfPool = false;// 是否是自选股票
    private BacktestResultOverviewVO overviewVO; // 总体数据
    private DailyCumReturnVO dailyCumReturnVO; // 收益率曲线数据
    private ReturnDistributionVO returnDistributionVO; // 相对收益数据
    private ArrayList<PositionRecordVO> recordList;// 持仓记录
    private String poolName; // 自选股票池名称

    public void launch(Pane mainPane, String poolName) {
        this.mainPane = mainPane;
        this.poolName = poolName;
        initService();
        initComponents();
    }

    private void initService() {
        backTestController = new BackTestControllerImpl();
        universeController = new UniverseControllerImpl();
    }

    private void initComponents() {
        initMenuButton();
        initDatePicker();
        initStandardComb();
        showMomentum();
    }

    private void initMenuButton() {
        // 默认全部信息
        chooseAll();

        // 板块
        ObservableList<MenuItem> list = boardMenu.getItems();
        for (MenuItem item : list) {
            CheckMenuItem checkItem = (CheckMenuItem) item;
            checkItem.selectedProperty().addListener(
                    (ov, oldValue, newValue) -> {
                        String etc = isBoardPool ? "等" : "";
                        if (newValue) {
                            chooseBoard();
                            stockPoolComb.setText(checkItem.getText() + etc);
                            poolType.add(checkItem.getText());
                        } else {
                            poolType.remove(checkItem.getText());
                            if (poolType.isEmpty()) {
                                backAllStatus();
                            }
                            if (poolType.size() == 1) {
                                stockPoolComb.setText(poolType.get(0));
                            }
                        }
                    }
            );
        }
        // 自选股票池
        List<UniverseVO> selfList = universeController.getAllUniverses();
        for (UniverseVO universeVO : selfList) {
            CheckMenuItem checkItem = new CheckMenuItem(universeVO.name);
            selfMenu.getItems().add(checkItem);
            checkItem.selectedProperty().addListener(
                    (ov, oldValue, newValue) -> {
                        if (newValue) {
                            chooseSelf();
                            stockPoolComb.setText(selfMenu.getText());
                            poolType.add(checkItem.getText());
                            // 收益基准不可选
                            incomeStandardComb.setDisable(true);
                        } else {
                            poolType.remove(checkItem.getText());
                            if (poolType.isEmpty()) {
                                backAllStatus();
                            }
                        }
                    }
            );

            if (checkItem.getText().equals(poolName)) {
                chooseSelf();
                checkItem.setSelected(true);
                stockPoolComb.setText(selfMenu.getText());
            }
        }

        if (selfList.isEmpty()) {
            MenuItem addSelfItem = new MenuItem("新建股票池");
            addSelfItem.setOnAction(event -> {
                mainPane.getChildren().clear();
                mainPane.getChildren().add(new SelfSelectStockPane(mainPane));
            });
            selfMenu.getItems().add(addSelfItem);
        }

    }

    @FXML
    private void chooseAll() {
        backAllStatus();
        clearCheckItemChoose(boardMenu);
        clearCheckItemChoose(selfMenu);
    }
    private void backAllStatus() {
        poolType.clear();
        poolKind = "全部股票";
        poolType.add(poolKind);
        stockPoolComb.setText(poolKind);
        incomeStandardComb.setDisable(false);
        isBoardPool = false;
        isSelfPool = false;
    }
    private void clearCheckItemChoose(Menu menu) {
        ObservableList<MenuItem> list = menu.getItems();
        for (MenuItem item : list) {
            CheckMenuItem checkItem = (CheckMenuItem) item;
            checkItem.setSelected(false);
        }
    }

    private void chooseBoard() {
        poolKind = "板块股票";
        if (!isBoardPool) {
            poolType.clear();
        }
        isBoardPool = true;
        isSelfPool = false;
        clearCheckItemChoose(selfMenu);
    }

    private void chooseSelf() {
        poolKind = "自选股票";
        if (!isSelfPool) {
            poolType.clear();
        }
        isSelfPool = true;
        isBoardPool = false;
        clearCheckItemChoose(boardMenu);
    }

    private void initDatePicker() {
        MyDatePicker.removeDateCell(strategyStartDatePick, strategyEndDatePick);
        strategyStartDatePick.setValue(LocalDate.of(2006, 7, 7));
        strategyEndDatePick.setValue(LocalDate.of(2008, 7, 7));
    }

    private void initStandardComb() {
        incomeStandardComb.getItems().clear();
        incomeStandardComb.getItems().addAll(Benchmark.HS300.toString(),
                Benchmark.GEI.toString(), Benchmark.SSC.toString());
        incomeStandardComb.setValue(Benchmark.HS300.toString());
    }

    /**
     * 显示动量策略
     */
    @FXML
    private void showMomentum() {
        MySlider.moveSliderLabel(sliderStrategyLabel, 0);
        generateField.setVisible(true);
        generateBox.setVisible(false);
        momentumBtn.setStyle("-fx-opacity:1.0;");
        averageBtn.setStyle("-fx-opacity:0.7;");
        isMomentum = true;
    }

    /**
     * 显示均值回归
     */
    @FXML
    private void showAverage() {
        MySlider.moveSliderLabel(sliderStrategyLabel, 140);
        generateBox.setVisible(true);
        generateField.setVisible(false);
        averageBtn.setStyle("-fx-opacity:1.0;");
        momentumBtn.setStyle("-fx-opacity:0.7;");
        isMomentum = false;

        generateBox.getItems().clear();
        generateBox.getItems().addAll(5, 10, 20);
        generateBox.setValue(5);
    }

    /**
     * 显示回测结果
     */
    @FXML
    private void showResult() {
        LocalDate lastDate = LocalDate.parse("2014-04-29");
        LocalDate firstDate = LocalDate.parse("2005-02-01");

        if (!makeStragetyCheck()) {

            boolean afterLast = strategyStartDatePick.getValue().isAfter(lastDate);
            boolean beforeFirst = strategyEndDatePick.getValue().isBefore(firstDate);

            if (!afterLast && !beforeFirst) {
                strategyTipLabel.setVisible(false); // 关闭内容为空标签
                String type = isMomentum ? "动量策略" : "均值回归";
                String generateTime = generateField.getText(); // 动量策略形成期
                String holdTime = holdField.getText(); // 持有期
                double initialFund = Double.parseDouble(initialFundField.getText()); // 起始资金
                LocalDate startDate = strategyStartDatePick.getValue(); // 起始时间
                LocalDate endDate = strategyEndDatePick.getValue(); // 结束时间
                String standard = incomeStandardComb.getValue().toString(); // 收益基准
                // 显示动画
                runAnimation(true);
                // 股票池
                String[] pool = new String[poolType.size()];
                for (int i = 0; i < poolType.size(); i++) {
                    pool[i] = poolType.get(i);
                }


                Task<Integer> task = new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        if (isMomentum) {// 动量策略
                            backTestController.runMomentum(startDate, endDate, (Benchmark) Benchmark.getEnum(standard), pool,
                                    initialFund, Integer.valueOf(generateTime), Integer.valueOf(holdTime));
                            // 超额胜率在动量策略下进行
                            isWinMomentum = true;
                        } else {// 均值回归
                            backTestController.runMeanRevision(startDate, endDate, (Benchmark) Benchmark.getEnum(standard), pool,
                                    initialFund, generateBox.getValue(), Integer.valueOf(holdTime));
                            // 超额胜率在均值回归下进行
                            isWinMomentum = false;
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // 关闭动画
                                runAnimation(false);
                                // 初始化结果Vo
                                initResultVo();
                                // 显示结果
                                initResult();
                                // 初始化展示区间
                                initShowRange(startDate, endDate);
                                // 生成回测历史
                                int formday = isMomentum ? Integer.valueOf(generateTime) : generateBox.getValue();
                                Condition condition = new Condition(poolKind, pool, type, formday, Integer.valueOf(holdTime),
                                        initialFund, startDate, endDate, standard);
                                ResultVo resultVo = new ResultVo(overviewVO, dailyCumReturnVO, returnDistributionVO, recordList);
                                History history = new History(LocalDateTime.now(), overviewVO.totalReturn, condition, resultVo);
                                LoopBackHistory.addHistory(history);

                            }
                        });
                        return 1;
                    }
                };


                new Thread(task).start();
            } else {
                WarningLabelTimeLine.timeLineWarningContent(warningLabel, "无股票数据");
            }
        } else {
            WarningLabelTimeLine.timeLineWarningContent(strategyTipLabel, "内容不能为空");
        }
    }

    private void runAnimation(boolean isRun) {
        coverPane.setVisible(isRun);
        animationPane.setVisible(isRun);
        loadingIndicator.setProgress(-1.0f);
        loadingIndicator.setVisible(isRun);
        loadingLabel.setVisible(isRun);
    }

    private void initResultVo() {
        overviewVO = backTestController.getBacktestResultOverview();
        dailyCumReturnVO = overviewVO.dailyCumReturn;
        returnDistributionVO = backTestController.getReturns();
        recordList = (ArrayList)backTestController.getPositionRecord();
    }

    private void initResult() {
        // 初始化回测结果与胜率图面板
        initResultPane();
        // 初始化收益曲线数据标签
        initGeneralLabel(overviewVO);
        // 初始化收益折线图
        initLineChart(dailyCumReturnVO, strategyStartDatePick.getValue(), strategyEndDatePick.getValue());
        // 初始化持仓记录
        initHoldRecord(recordList);
        // 初始化胜率图组件
        initWinComponent();
        // 默认显示收益曲线图
        showReturnLineChart();
    }

    private void initShowRange(LocalDate startDate, LocalDate endDate) {
        // 展示区间
        showStartDatePicker.setValue(startDate);
        showStartDatePicker.setDayCellFactory(new RemoveBeforeDateCell(startDate));
        showEndDatePicker.setValue(endDate);
        showEndDatePicker.setDayCellFactory(new RemoveAfterDateCell(endDate));

        showStartDatePicker.setOnAction(event -> {
            showRangeResult(showStartDatePicker.getValue(), showEndDatePicker.getValue());
        });
        showEndDatePicker.setOnAction(event -> {
            showRangeResult(showStartDatePicker.getValue(), showEndDatePicker.getValue());
        });
    }

    private void showRangeResult(LocalDate start, LocalDate end) {
        if (end.isBefore(start) || end.isEqual(start)) return;
        // 初始化该时间段内收益折线图
        initLineChart(dailyCumReturnVO, start, end);
        // 初始化该时间段内饼图
        initPieChart(returnDistributionVO);
        // 初始化该时间段内相对指数收益分布图
        initBarChart(returnDistributionVO);

    }

    private void initResultPane() {
        animationPane.setVisible(false);
        resultPane.setPrefHeight(1400);
        resultPane.setVisible(true);
        winRatePane.setPrefHeight(850);
        winRatePane.setVisible(true);
    }

    /**
     * 显示收益曲线
     */
    @FXML
    private void showReturnLineChart() {
        graphPane.setVisible(true);
        resultPane.setPrefHeight(1400);
        sliderGraphLabel.setVisible(true);
        MySlider.moveSliderLabel(sliderGraphLabel, 40);
        returnLinePane.setVisible(true);
        returnBarPane.setVisible(false);
        historyPane.setVisible(false);
        returnLineBtn.setStyle("-fx-opacity: 1.0");
        returnBarBtn.setStyle("-fx-opacity: 0.7");
    }

    private void initGeneralLabel(BacktestResultOverviewVO vo) {
        rateReturnLabel.setText(NumberFormatter.formatToPercent(vo.strategyAnnualisedReturn) + "%");
        standardRateReturnLabel.setText(NumberFormatter.formatToPercent(vo.benchmarkAnnualisedReturn) + "%");
        alphaLabel.setText(NumberFormatter.formatToPercent(vo.alpha) + "%");
        betaLabel.setText(NumberFormatter.format(vo.beta));
        sharpLabel.setText(NumberFormatter.format(vo.sharpRatio));
        returnVolatilityLabel.setText(NumberFormatter.formatToPercent(vo.volatility) + "%");
        maxReturnLabel.setText(NumberFormatter.formatToPercent(vo.maxDrawdown) + "%");
        standardLabel.setText(incomeStandardComb.getValue().toString());
        totalReturnLabel.setText(NumberFormatter.formatToPercent(vo.totalReturn) + "%");
    }

    /**
     *
     * @param vo 存储的数据
     * @param start 要显示的开始时间
     * @param end 要显示的结束时间
     */
    private void initLineChart(DailyCumReturnVO vo, LocalDate start, LocalDate end) {
        XYChart.Series seriesBase = new XYChart.Series();
        seriesBase.setName(incomeStandardComb.getValue().toString());
        addSeriesData(seriesBase, vo.dates, vo.benchmarkDailyCumReturn, start, end);
        XYChart.Series seriesStrategy = new XYChart.Series();
        seriesStrategy.setName("策略收益率");
        addSeriesData(seriesStrategy, vo.dates, vo.strategyDailyCumReturn, start, end);

        if (cumulativeChart.getData() != null) {
            cumulativeChart.getData().clear();
        }
        cumulativeChart.getData().addAll(seriesBase, seriesStrategy);
        cumulativeChart.setLegendVisible(false);
        cumulativeChart.setCreateSymbols(false);
    }

    private void addSeriesData(XYChart.Series series, List<LocalDate> dates, List<Double> dailyCumReturn,
                               LocalDate start, LocalDate end) {
        int size = dates.size();
        for (int i = 0; i < size; i++) {
            LocalDate showDate = dates.get(i);
            if (showDate.isBefore(start) || showDate.isAfter(end)) continue;
            series.getData().add(new XYChart.Data<String, Double>(showDate.toString(),
                    Double.parseDouble(NumberFormatter.formatToPercent(dailyCumReturn.get(i)))));
        }
    }

    private void initHoldRecord(ArrayList<PositionRecordVO> list) {
        // 被隐藏起来的根节点
        final TreeItem<Log> root = new TreeItem<>(new Log(LocalDate.now().toString(),
                "", "", "", ""));
        root.setExpanded(true);
        list.stream().forEach((listItem) -> {// listItem 每一个交易日的详情

            // 获得单日持有股票
            ArrayList<StockHoldingVO> voList = (ArrayList) listItem.getStockHoldings();
            int size = voList.size();
            // 新建子节点
            TreeItem<Log> childRoot = new TreeItem<>(new Log(listItem.getDate().toString(), "共 " + size + " 只股票",
                    "", NumberFormatter.format(listItem.getCapitalAll()), NumberFormatter.format(listItem.getAllProfit())));
            // 为子节点添加现金行
            TreeItem<Log> capitalPresent = new TreeItem<>(new Log("", "现金",
                    "--", NumberFormatter.format(listItem.getCapitalPresent()), "--"));
            childRoot.getChildren().add(capitalPresent);
            // 为子节点添加持有股票行
            for (int i = 0; i < size; i++) {
                StockHoldingVO stock = voList.get(i);
                TreeItem<Log> item = new TreeItem<>(new Log("", stock.getCode() + " " + stock.getName(),
                       stock.getNum() + "", NumberFormatter.format(stock.getPosition()) + "", "--"));
                childRoot.getChildren().add(item);
            }

            root.getChildren().add(childRoot);
        });

        dateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Log, String> param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getDate()));
        codeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Log, String> param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getName()));
        amountColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Log, String> param) ->
                new ReadOnlyStringWrapper(String.valueOf(param.getValue().getValue().getNum())));
        capitalColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Log, String> param) ->
                new ReadOnlyStringWrapper(String.valueOf(param.getValue().getValue().getMoney())));
        changeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Log, String> param) ->
                new ReadOnlyStringWrapper(String.valueOf(param.getValue().getValue().getAllProfit())));

        holdRecordTable.setRoot(root);
        holdRecordTable.setShowRoot(false);
    }

    /**
     * 显示收益率分布图
     */
    @FXML
    private void showReturnBarChart() {
        graphPane.setVisible(true);
        resultPane.setPrefHeight(1400);
        sliderGraphLabel.setVisible(true);
        MySlider.moveSliderLabel(sliderGraphLabel, 180);
        returnBarPane.setVisible(true);
        returnLinePane.setVisible(false);
        historyPane.setVisible(false);
        returnBarBtn.setStyle("-fx-opacity: 1.0");
        returnLineBtn.setStyle("-fx-opacity: 0.7");

        // 初始化饼图
        initPieChart(returnDistributionVO);
        // 初始化相对指数收益分布图
        initBarChart(returnDistributionVO);
    }

    private void initPieChart(ReturnDistributionVO vo) {
        returnCyclePicChart.getData().clear();
        positiveCycleLabel.setText(String.valueOf(vo.positiveReturnNum)); // 总正收益周期数
        negativeCycleLabel.setText(String.valueOf(vo.negativeReturnNum)); // 总负收益周期数
        winRateLabel.setText(NumberFormatter.formatToPercent(vo.winRate) + "%"); // 赢率
        winRateLabel.setStyle("-fx-text-fill: " + (vo.winRate >= 0.5 ? "#e74c3c" : "#2ecc71") + ";");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("总正收益周期数", vo.positiveReturnNum),
                        new PieChart.Data("总负收益周期数", vo.negativeReturnNum));

        returnCyclePicChart.setData(pieChartData);
        returnCyclePicChart.setTitle("");
    }

    @FXML
    private void initBarChart(ReturnDistributionVO vo) {
        relativeIndexBarChart.getData().clear();
        XYChart.Series poSeries = new XYChart.Series(); // 正收益
        poSeries.setName("正收益次数");
        XYChart.Series negSeries = new XYChart.Series();// 负收益
        negSeries.setName("负收益次数");

        double[] cutPoints = vo.cutPoints;
        int[] positive = vo.positiveDistribution;
        int[] negative = vo.negativeDistribution;
        int length = cutPoints.length;
        for (int i = 0; i < length - 1; i++) {
            poSeries.getData().add(new XYChart.Data(NumberFormatter.formatToPercent(cutPoints[i+1]) + "%",
                    i < positive.length ? positive[i] : 0));
            negSeries.getData().add(new XYChart.Data(NumberFormatter.formatToPercent(cutPoints[i+1]) + "%",
                    i < negative.length ? negative[i] : 0));
        }
        barChartXAxis.setAnimated(false);
        relativeIndexBarChart.getData().addAll(poSeries, negSeries);
        relativeIndexBarChart.setBarGap(0.0);

//        Timeline tl = new Timeline();
//        tl.getKeyFrames().add(new KeyFrame(Duration.millis(500),
//                (ActionEvent actionEvent) -> {
//                    relativeIndexBarChart.getData().stream().forEach((series) -> {
//                        ((XYChart.Series)series).getData().stream().forEach((data) -> {
//                            ((XYChart.Data)data).setYValue(Math.random() * 10);
//                        });
//                    });
//                }
//        ));
//
//        tl.setCycleCount(Animation.INDEFINITE);
//        tl.setAutoReverse(true);
//        tl.play();
    }

    @FXML
    private void showHistory() {
        sliderGraphLabel.setVisible(false);
        graphPane.setVisible(false);
        historyPane.setVisible(true);
        resultPane.setPrefHeight(650);
        returnLineBtn.setStyle("-fx-opacity: 0.7");
        returnBarBtn.setStyle("-fx-opacity: 0.7");

        initHistoryTable();
    }

    private void initHistoryTable() {
        historyTimeCol.setCellValueFactory(new PropertyValueFactory<>("histime"));
        historyPoolCol.setCellValueFactory(new PropertyValueFactory<>("poolKind"));
        historyTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        historySECol.setCellValueFactory(new PropertyValueFactory<>("se"));
        historyAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        historyReturnCol.setCellValueFactory(new PropertyValueFactory<>("returnPrice"));

        historyResultCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                HistoryResCell historyResCell = new HistoryResCell(historyTable);
                return historyResCell;
            }
        });
        ArrayList<History> list = LoopBackHistory.getHistoryList();
        ObservableList<History> hisList = FXCollections.observableList(list);
        historyTable.setItems(hisList);
    }

    /**
     * 超额收益率和策略胜率与不同形成期/持有期的关系图
     * 开始回测
     */
    @FXML
    private void startWin() {
        if (!strategyWinRateCheck()) {
            // 显示动画
            runAnimation(true);
            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    ArrayList<ChangingRateVO> list = null;
                    if (isWinMomentum) {
                        String basePeriod = basePeriodComb.getValue().toString(); // 基准期类型
                        String day = dayField.getText(); // 天数
                        // 调用动量策略接口
                        list = (ArrayList<ChangingRateVO>) backTestController.getChangingWP(
                                basePeriod.equals("形成期") ? RateType.formRate : RateType.RefreshRate, Integer.valueOf(day));
                    } else {
                        String dayM = dayComb.getValue().toString(); // 均值天数 5， 10， 20
                        // 调用均值回归接口
                        list = (ArrayList<ChangingRateVO>) backTestController.getChangingWP(
                                RateType.formRate, Integer.valueOf(dayM));
                    }

                    final ArrayList<ChangingRateVO> finalList = list;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // 关闭动画
                            runAnimation(false);
                            // 初始化胜率表
                            initWinTable(finalList);
                            // 初始化超额收益图
                            initExcessReturnLineChart(finalList);
                            // 初始化策略胜率图
                            initWinRateLineChart(finalList);
                        }
                    });
                    return 1;
                }
            };
            new Thread(task).start();
            winRateTipLabel.setVisible(false);
        } else {
            winRateTipLabel.setVisible(true);
            winRateTipLabel.setText("内容不能为空");
        }
    }

    private void initWinComponent() {
        winRateStartDateLabel.setText(strategyStartDatePick.getValue().toString()); // 起始日期
        winRateEndDateLabel.setText(strategyEndDatePick.getValue().toString()); // 结束日期
        strategyLabel.setText(isMomentum ? "动量策略" : "均值回归");

        if(isMomentum) {
            // 动量策略基准期
            basePeriodComb.getItems().clear();
            basePeriodComb.getItems().addAll("形成期", "持有期");
            basePeriodComb.setValue("形成期");
            dayField.setText("10");// 假设为10天
            momentumHBox.setVisible(true);
            meanReversionHBox.setVisible(false);
        }else {
            // 均值回归基准期
            dayComb.getItems().clear();
            dayComb.getItems().addAll(5, 10, 20);
            dayComb.setValue(5);
            momentumHBox.setVisible(false);
            meanReversionHBox.setVisible(true);
        }
    }

    private void initWinTable(ArrayList<ChangingRateVO> list) {
        relativeStrengthCycleCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        excessReturnCol.setCellValueFactory(new PropertyValueFactory<>("returnStr"));
        yearWinRateCol.setCellValueFactory(new PropertyValueFactory<>("winPerString"));

        // 设置列表头不可被拖动
        final TableColumn[] columns = {relativeStrengthCycleCol, excessReturnCol, yearWinRateCol};
        winRateTable.getColumns().addListener(new DisableColumnChangeListener(winRateTable, columns));

        winRateTable.setItems(FXCollections.observableArrayList(list));
    }

    private void initExcessReturnLineChart(ArrayList<ChangingRateVO> list) {
        excessReturnAreaChart.getData().clear();
        XYChart.Series seriesExcess= new XYChart.Series();
        for (ChangingRateVO vo : list) {
            seriesExcess.getData().add((new XYChart.Data(String.valueOf(vo.getRate()), vo.getAbnormalReturn())));
        }
        excessReturnAreaChart.setLegendVisible(false);
        excessReturnAreaChart.getData().add(seriesExcess);
    }

    private void initWinRateLineChart(ArrayList<ChangingRateVO> list) {
        winRateAreaChart.getData().clear();
        XYChart.Series seriesWinRate = new XYChart.Series();
        for (ChangingRateVO vo : list) {
            seriesWinRate.getData().add((new XYChart.Data(String.valueOf(vo.getRate()), vo.getWiningPercentage() * 100)));
        }
        winRateAreaChart.setLegendVisible(false);
        winRateAreaChart.getData().add(seriesWinRate);
    }

    private boolean makeStragetyCheck() {
        boolean formDate = true;
        if (isMomentum) {
            formDate = generateField.getText().equals("");
        } else {
            formDate = generateBox.getValue().toString().equals("");
        }
        boolean holdTime = holdField.getText().equals("");
        boolean initMoney = initialFundField.getText().equals("");
        boolean backDateStart = strategyStartDatePick.getValue() == null;
        boolean backDateEnd = strategyEndDatePick.getValue() == null;
        boolean incomeStandard = incomeStandardComb.getValue() == null;

        return formDate || holdTime || initMoney || backDateStart || backDateEnd || incomeStandard;
    }

    private boolean strategyWinRateCheck() {
        boolean chooseCheck = true;
        if (isWinMomentum) {
            chooseCheck = basePeriodComb.getValue() == null & dayField.getText().equals("");
        } else {
            chooseCheck = dayComb.getValue() == null;
        }

        return chooseCheck;
    }

    /**
     * 持仓记录类
     */
    class Log {
        private String date; // 交易日期

        private String name; // 证券代码/名称/共1只证券

        private String num; // 持仓数量

        private String money; // 总头寸/单只股票头寸/现金

        private String allProfit; // 总盈亏

        public Log(String date, String name, String num, String money, String  allProfit) {
            this.date = date;
            this.name = name;
            this.num = num;
            this.money = money;
            this.allProfit = allProfit;
        }

        public String getDate() {
            return date;
        }

        public String getName() {
            return name;
        }

        public String getNum() {
            return num;
        }

        public String  getMoney() {
            return money;
        }

        public String getAllProfit() {
            return allProfit;
        }
    }

    class HistoryResCell extends TableCell<History, String> {

        private TableView tableView;

        public HistoryResCell(final TableView tableView) {
            this.tableView = tableView;
        }

        @Override
        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            Hyperlink link = null;
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                link = new Hyperlink("回测结果");
                link.setOnAction(event -> {
                    int selectedIndex = getTableRow().getIndex();
                    History history = (History) tableView.getItems().get(selectedIndex);
                    backCondition(history.getCondition());
                    backResultVo(history.getResultVo());
                    initResult();

                });
                setGraphic(link);
                setText(null);
            }
        }

        private void backCondition(Condition condition) {
            backMenuButton(condition.getPool());
            if (condition.getType().equals("动量策略")) {
                showMomentum();
                generateField.setText(String.valueOf(condition.getFormdays()));
            } else {
                showAverage();
                generateBox.setValue(condition.getFormdays());
            }
            holdField.setText(String.valueOf(condition.getHolddays()));
            initialFundField.setText(String.valueOf(condition.getAmount()));
            strategyStartDatePick.setDayCellFactory(null);
            strategyEndDatePick.setDayCellFactory(null);
            strategyStartDatePick.setValue(condition.getStart());
            strategyEndDatePick.setValue(condition.getEnd());
            incomeStandardComb.setValue(condition.getStandard());
            // 初始化展示区间
            initShowRange(condition.getStart(), condition.getEnd());
        }

        private void backMenuButton(String[] pool) {
            poolType.clear();
            for(String s : pool) {
                poolType.add(s);
            }
            String poolkind = pool[0];

            if (poolkind.equals("全部股票")) {
                stockPoolComb.setText(poolkind);
            } else if (poolkind.equals("主板") ||
                    poolkind.equals("创业板") || poolkind.equals("中小板")){
                // 板块
                stockPoolComb.setText(poolkind + (pool.length > 1 ? "等" : ""));
                ObservableList<MenuItem> list = boardMenu.getItems();
                initCheckItem(list);
            } else {
                // 自选股
                stockPoolComb.setText("自选股票池");
                ObservableList<MenuItem> list = selfMenu.getItems();
                initCheckItem(list);
            }
        }

        private void initCheckItem(ObservableList<MenuItem> list) {
            for (MenuItem item : list) {
                CheckMenuItem checkItem = (CheckMenuItem) item;
                if (poolType.contains(checkItem.getText())) {
                    checkItem.setSelected(true);
                } else {
                    checkItem.setSelected(false);
                }
            }
        }
        private void backResultVo(ResultVo resultVo) {
            overviewVO = resultVo.getOverviewVO();
            dailyCumReturnVO = resultVo.getDailyCumReturnVO();
            returnDistributionVO = resultVo.getReturnDistributionVO();
            recordList = resultVo.getRecordList();
        }
    }


}
