package presentation.controller;

import controller.market.MarketControllerImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import presentation.util.DisableColumnChangeListener;
import presentation.util.comparator.ListChangeComparator;
import presentation.util.comparator.ListStrComparator;
import presentation.util.datepickercell.RemoveWeekendDateCell;
import presentation.util.tablecell.StockListChangeCell;
import util.tool.NumberFormatter;
import vo.market.DefaultStockVO;
import vo.market.MarketVO;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by wyj on 2017/3/8.
 */
public class StockMarketController {

    @FXML private DatePicker datePicker;
    @FXML private Button checkBtn;

    @FXML private Label dateLabel;
    @FXML private Label amountLabel;
    @FXML private Label upStopLabel;
    @FXML private Label downStopLabel;
    @FXML private Label upLabel;
    @FXML private Label downLabel;
    @FXML private Label moreLabel;
    @FXML private Label lessLabel;

    @FXML private Pane infoPane;

    @FXML private BarChart<String, Number> updownChart;
    @FXML private CategoryAxis updownXAxis;
    @FXML private NumberAxis updownYAxis;

    @FXML private TableView dayStockList;
    @FXML private TableColumn stockCodeCol;
    @FXML private TableColumn stockNameCol;
    @FXML private TableColumn stockPriceCol;
    @FXML private TableColumn stockperCol;
    @FXML private TableColumn stockupCol;
    @FXML private TableColumn stockVolumeCol;
    @FXML private TableColumn high;
    @FXML private TableColumn low;
    @FXML private TableColumn market;

    private MarketControllerImpl marketController;

    public void launch(Pane mainPane) {

        marketController = new MarketControllerImpl();
        initPane();
    }

    private void initPane() {
        initDatePicker();
        initMarketInfo(datePicker.getValue());
    }

    private void initDatePicker() {
        datePicker.setDayCellFactory(new RemoveWeekendDateCell());
        LocalDate now = LocalDate.now();
        LocalDate adjust = null;
        if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            adjust = now.minusDays(1);
        } else if (now.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            adjust = now.minusDays(2);
        } else {
            adjust = now;
        }
        datePicker.setValue(adjust);
    }


    @FXML
    private void checkMarketInfo() {
        LocalDate localDate = datePicker.getValue();
        initMarketInfo(localDate);

    }

    private void initMarketInfo(LocalDate date) {
        try {
            long startTime = System.currentTimeMillis();
            MarketVO marketVO = marketController.getMarketInfo(date);
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);


            dateLabel.setText(date.toString());
            amountLabel.setText(NumberFormatter.formatToBaseWan(marketVO.dayVolume));
            upStopLabel.setText(String.valueOf(marketVO.SLSNum) + "支");
            downStopLabel.setText(String.valueOf(marketVO.DLSNum) + "支");
            upLabel.setText(String.valueOf(marketVO.SFSNum) + "支");
            downLabel.setText(String.valueOf(marketVO.DFSNum) + "支");
            moreLabel.setText(String.valueOf(marketVO.OCHFNum) + "支");
            lessLabel.setText(String.valueOf(marketVO.OCLNFNum) + "支");

            initChart(marketVO.increaseDistribution);
            initList(FXCollections.observableArrayList(marketVO.stocks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initChart(int[] array) {
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < array.length; i++) {
            final int k =i;
            XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(i), array[i]);
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        if (k < array.length/2) {
                            newValue.setStyle("-fx-bar-fill: #2ecc71;");
                        } else {
                            newValue.setStyle("-fx-bar-fill: #e74c3c;");
                        }
                    }
                }
            });
            series.getData().add(data);
        }

        if (updownChart.getData() != null) {
            updownChart.getData().clear();
        }

        updownXAxis.setTickLabelsVisible(false);
        updownChart.setLegendVisible(false);
        updownChart.setBarGap(0.0);
        updownChart.setCategoryGap(25);
        updownChart.getData().addAll(series);
    }

    private void initList(ObservableList<DefaultStockVO> list) {
        stockCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        stockNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPriceCol.setCellValueFactory(new PropertyValueFactory<>("close"));
        stockperCol.setCellValueFactory(new PropertyValueFactory<>("increase"));
        stockupCol.setCellValueFactory(new PropertyValueFactory<>("change"));
        stockVolumeCol.setCellValueFactory(new PropertyValueFactory<>("volume"));
        high.setCellValueFactory(new PropertyValueFactory<>("high"));
        low.setCellValueFactory(new PropertyValueFactory<>("low"));
        market.setCellValueFactory(new PropertyValueFactory<>("market"));


        stockperCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                StockListChangeCell cell = new StockListChangeCell(dayStockList);
                return cell;
            }
        });
        stockupCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                StockListChangeCell stockListChangeCell = new StockListChangeCell(dayStockList);
                return stockListChangeCell;
            }
        });
        // 设置列表根据 String 排序,并让列表默认按涨幅降序排列
        stockperCol.setComparator(new ListStrComparator());
        stockperCol.setSortType(TableColumn.SortType.ASCENDING);
        stockupCol.setComparator(new ListChangeComparator());
        stockVolumeCol.setComparator(new ListStrComparator());

        dayStockList.setItems(list);

//        dayStockList.setFixedCellSize(60);
//        dayStockList.prefHeightProperty().bind(dayStockList.fixedCellSizeProperty().multiply(Bindings.size(dayStockList.getItems()).add(1.1)));
//        dayStockList.minHeightProperty().bind(dayStockList.prefHeightProperty());
//        dayStockList.maxHeightProperty().bind(dayStockList.prefHeightProperty());

        final TableColumn[] columns = {stockCodeCol, stockNameCol , stockPriceCol, stockperCol, stockupCol, stockVolumeCol, high, low, market};
        dayStockList.getColumns().addListener(new DisableColumnChangeListener(dayStockList, columns));

        // 让列表默认按涨幅降序排列
        dayStockList.getSortOrder().add(stockperCol);
    }
}
