package presentation.controller;

import controller.backtest.UniverseController;
import controller.backtest.UniverseControllerImpl;
import controller.market.MarketController;
import controller.market.MarketControllerImpl;
import controller.stock.StockController;
import controller.stock.StockControllerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import presentation.ui.StockInfoPane;
import presentation.util.DisableColumnChangeListener;
import presentation.util.MakeBarChart;
import presentation.util.candlestick.CandleStickChart;
import presentation.util.comparator.ListChangeComparator;
import presentation.util.comparator.ListStrComparator;
import presentation.util.tablecell.StockListChangeCell;
import presentation.util.alert.AlertController;
import util.enums.Board;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import vo.market.DefaultStockVO;
import vo.stock.StockAttributesVO;
import vo.stock.StockVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wyj on 2017/3/8.
 */
public class StockListController {

    @FXML private TableView stockList;

    @FXML private TableColumn code;
    @FXML private TableColumn name;
    @FXML private TableColumn currentPrice;
    @FXML private TableColumn increaseper;
    @FXML private TableColumn increaseprice;
    @FXML private TableColumn volume;
    @FXML private TableColumn high;
    @FXML private TableColumn low;
    @FXML private TableColumn market;
    @FXML private Pane candlePane;
    @FXML private BarChart volumeBarChart;
    @FXML private Label volumeUnit;
    @FXML private Label sectionLabel;
    @FXML private Label plateLabel;
    @FXML private Label stockNameLabel;
    @FXML private Label stockCodeLabel;
    @FXML private Label sectionLongLabel;

    private MarketController marketController;
    private StockController stockController;
    private UniverseController universeController;
    private CandleStickChart chart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private ArrayList<Label> averageLabelList;
    private MakeBarChart makeBarChart;

    private Pane mainPane;

    public void launch(Pane mainPane) {
        this.mainPane = mainPane;
        universeController = new UniverseControllerImpl();
        initService();
        tableEvent();
        initTable();
        // 初始化 K 线图
        initLabelList();
        initGraph();
        makeBarChart = new MakeBarChart();
        addDataToGraph(0);
    }

    private void initLabelList() {
        averageLabelList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            averageLabelList.add(new Label());
        }
    }

    private void initService() {
        marketController = new MarketControllerImpl();
        stockController = new StockControllerImpl();
    }

    private void initTable() {
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentPrice.setCellValueFactory(new PropertyValueFactory<>("close"));
        volume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        high.setCellValueFactory(new PropertyValueFactory<>("high"));
        low.setCellValueFactory(new PropertyValueFactory<>("low"));
        market.setCellValueFactory(new PropertyValueFactory<>("market"));
        increaseper.setCellValueFactory(new PropertyValueFactory<DefaultStockVO, String>("increase"));
        increaseprice.setCellValueFactory(new PropertyValueFactory<DefaultStockVO, Double>("change"));
        increaseper.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                StockListChangeCell cell = new StockListChangeCell(stockList);
                return cell;
            }
        });
        increaseprice.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                StockListChangeCell stockListChangeCell = new StockListChangeCell(stockList);
                return stockListChangeCell;
            }
        });

        ObservableList<DefaultStockVO> list = getDataList();

        // 设置列表根据 String 排序,并让列表默认按涨幅降序排列
        increaseper.setComparator(new ListStrComparator());
        increaseper.setSortType(TableColumn.SortType.ASCENDING);
        increaseprice.setComparator(new ListChangeComparator());
        volume.setComparator(new ListStrComparator());

        stockList.setItems(list);


        // 设置列表头不可被拖动
        final TableColumn[] columns = {code, name , currentPrice, increaseper, increaseprice, volume, high, low, market};
        stockList.getColumns().addListener(new DisableColumnChangeListener(stockList, columns));

        // 让列表默认按涨幅降序排列
        stockList.getSortOrder().add(increaseper);

    }

    private ObservableList getDataList() {
        ObservableList<DefaultStockVO> list = null;
        try {
            list = FXCollections.observableArrayList(marketController.getAllStocks());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void initGraph() {
        chart = null;
        xAxis = new CategoryAxis();
        xAxis.setTickLabelsVisible(false);
//        xAxis.setLabel("日期");
        xAxis.setTickMarkVisible(false);
        yAxis = new NumberAxis();
//        yAxis.setTickLabelsVisible(false);
        yAxis.setMinorTickCount(1);
//        yAxis.setTickMarkVisible(false);
        chart = new CandleStickChart(xAxis, yAxis);

        chart.setLabelList(averageLabelList);

        // 设置 chart 大小
        chart.setPrefWidth(440);
        chart.setPrefHeight(300);
        chart.setLayoutX(0);
        chart.setLayoutY(0);
        chart.setAnimated(false);

        candlePane.getChildren().clear();
        candlePane.getChildren().add(chart);
    }

    private void tableEvent() {
        stockList.setRowFactory(tv -> {
            TableRow<DefaultStockVO> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event -> {
                if (!tableRow.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    int index = tableRow.getIndex();
                    DefaultStockVO defaultStockVO = (DefaultStockVO) stockList.getItems().get(index);
                    mainPane.getChildren().clear();
                    try {
                        mainPane.getChildren().add(new StockInfoPane(mainPane, defaultStockVO.code));
                    } catch (TimeException e) {
                        AlertController.showNullWrongAlert("未找到该股票", "查找失败");
                    } catch (StockNotFoundException e) {
                        AlertController.showNullWrongAlert("未找到该股票", "查找失败");

                    }
                } else if (!tableRow.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    int index = tableRow.getIndex();
                    addDataToGraph(index);
                }
            });

            return tableRow;
        });
    }

    private void addDataToGraph(int index) {
        DefaultStockVO defaultStockVO = (DefaultStockVO) stockList.getItems().get(index);
        try {
            StockAttributesVO stockAttributesVO = universeController.getStockAttributes(defaultStockVO.code);
            if (stockAttributesVO.section.length() > 7) {
                sectionLabel.setText(stockAttributesVO.section.substring(0, 6));
                sectionLongLabel.setText(stockAttributesVO.section.substring(6));
            } else {
                sectionLabel.setText(stockAttributesVO.section);
                sectionLongLabel.setText("");
            }
            plateLabel.setText(Board.getBoardName(Board.getBoardByStock(defaultStockVO.code)));
            StockVO stockVO = stockController.findStock(defaultStockVO.code);
            stockNameLabel.setText(defaultStockVO.name);
            stockCodeLabel.setText(defaultStockVO.code);
            initGraph();
            CandleStickChart.createContent(chart, yAxis, stockVO.getStockItemList(), true);
            makeBarChart.showVolumeChart(volumeBarChart, (CategoryAxis) volumeBarChart.getXAxis(),
                    (NumberAxis) volumeBarChart.getYAxis(), stockVO.getStockItemList());
            volumeBarChart.setVisible(true);
            volumeUnit.setVisible(true);
        } catch (TimeException e) {
            e.printStackTrace();
        } catch (StockNotFoundException e) {
            e.printStackTrace();
        }
    }
}
