package presentation.controller;

import controller.backtest.UniverseController;
import controller.backtest.UniverseControllerImpl;
import controller.stock.StockController;
import controller.stock.StockControllerImpl;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javafx.util.Duration;
import presentation.ui.SectionInfoPane;
import presentation.ui.SelfSelectStockPane;
import presentation.ui.StockStrategyPane;
import presentation.util.alert.WarningLabelTimeLine;
import util.exception.StockNotFoundException;
import vo.backtest.UniverseVO;
import vo.stock.StockAttributesVO;
import vo.stock.StockItemVO;

import java.util.*;

/**
 * Created by wyj on 2017/4/16.
 */
public class SectionInfoController {

    @FXML private TableView stockList;
    @FXML private TableColumn codeCol;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn industryCol;
    @FXML private TableColumn btnCol;

    @FXML private TextField inputField;
    @FXML private Label warningExistLabel;

    @FXML private Label sectionNameLabel;

    @FXML private Button backBtn;

    @FXML private FlowPane industryFlowPane;

    @FXML private Label stockNum;

    private Pane mainPane;
    private String poolName;

    @FXML private ListView listView;

    @FXML private Pane controllerBtnPane;

    @FXML private Button recallBtn;

    private UniverseController universeController;
    private StockController stockController;

    private Map btnMap;
    private Map operatedBtnMap;

    private ObservableList btnIndustryList;
    private Set<String> stockCodeSet;
    private Set<String> operateAddCodeSet;
    private Set<String> operateDelCodeSet;

    public void launch(Pane mianPane, String poolName) {
        this.mainPane = mianPane;
        this.poolName = poolName;

        controllerBtnPane.setVisible(false);

        warningExistLabel.setVisible(false);

        sectionNameLabel.setText(poolName);

        btnIndustryList = industryFlowPane.getChildren();

        universeController = new UniverseControllerImpl();
        stockController = new StockControllerImpl();

        stockCodeSet = new HashSet<>();
        operateAddCodeSet = new HashSet<>();
        operateDelCodeSet = new HashSet<>();

        btnMap = new HashMap();
        operatedBtnMap = new HashMap();

        initRecallEvent();

        initStockSet();

        initMap();
        initBtnStyle();
        initTable();
        initStockSearch();
    }

    /**
     * 初始化按钮map
     */
    private void initMap() {
        for (int i = 0; i<btnIndustryList.size(); i++) {
            Button button = (Button) btnIndustryList.get(i);
            btnMap.put(button, false);
            operatedBtnMap.put(button, false);
        }
    }

    /**
     * 初始化股票集合
     */
    private void initStockSet() {
        ArrayList<UniverseVO> sectionlist = (ArrayList) universeController.getAllUniverses();

        for (int i = 0; i<sectionlist.size(); i++) {
            if (sectionlist.get(i).name.equals(poolName)) {
                ArrayList<String> temp = (ArrayList<String>) sectionlist.get(i).codes;
                for (int j = 0; j<temp.size(); j++) {
                    stockCodeSet.add(temp.get(j));
                }
            }
        }
    }


    /**
     * 初始化股票列表
     */
    private void initTable() {
        stockNum.setText(String.valueOf(stockCodeSet.size()));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        industryCol.setCellValueFactory(new PropertyValueFactory<>("section"));
        btnCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new SectionStockListButtonCell();
            }
        });
        stockList.setItems(getTableItemList());
    }

    private ObservableList getTableItemList() {

        ArrayList<StockAttributesVO> stockAttributesVOArrayList = new ArrayList<>();
        for (String code : stockCodeSet) {
            try {
                stockAttributesVOArrayList.add(universeController.getStockAttributes(code));
            } catch (StockNotFoundException e) {
                e.printStackTrace();
            }
        }

        ObservableList<StockAttributesVO> list = FXCollections.observableArrayList(stockAttributesVOArrayList);
        return list;
    }


    @FXML
    private void backEvent() {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new SelfSelectStockPane(mainPane));
    }

    /**
     * 初始化按钮样式
     */
    private void initBtnStyle() {
        for (int i = 0; i<btnIndustryList.size(); i++) {
            Button button = (Button) btnIndustryList.get(i);

            FlowPane.setMargin(button, new Insets(5, 10, 5, 10));
            initMouseClickEvent(button);

            boolean judge = judgeIndustryAllIn(button.getText());
            if (!judge) {
                button.getStyleClass().add("industry-btn");
                btnMap.put(button, false);
            } else {
                button.getStyleClass().add("industry-selected-btn");
                btnMap.put(button, true);
            }
        }
    }

    /**
     * 判断行业股票是否全部被添加
     * @param industry
     * @return
     */
    private Boolean judgeIndustryAllIn(String industry) {

        ArrayList<String> industryCodeList = (ArrayList<String>) stockController.getStockCodesBySection(industry);
        boolean industryAllIn = true;
        for (int i = 0; i<industryCodeList.size(); i++) {
            boolean have = false;
            if (stockCodeSet.contains(industryCodeList.get(i))) {
                have = have || true;
            } else {
                have = have || false;
            }
            industryAllIn = industryAllIn && have;
        }

        return industryAllIn;
    }


    /**
     * 初始化按钮事件
     * @param button
     */
    private void initMouseClickEvent(Button button) {
        button.setOnMouseClicked(event -> {
            controllerBtnPane.setVisible(true);
            ObservableList observableList = button.getStyleClass();

            if (btnMap.get(button).equals(false)) {
                observableList.remove(observableList.size() - 1);
                button.getStyleClass().add("new-chosen-btn");
                btnMap.put(button, true);
                operatedBtnMap.put(button, true);
                initListAddCode(button);
            } else {
                observableList.remove(observableList.size() - 1);
                button.getStyleClass().add("new-not-chosen-btn");
                btnMap.put(button, false);
                operatedBtnMap.put(button, true);
                initListDelCode(button);
            }

            stockCodeSet.addAll(operateAddCodeSet);
            stockCodeSet.removeAll(operateDelCodeSet);
            initTable();
            initOperateStockInList();
            stockList.refresh();
        });
    }



    private void initOperateStockInList() {
        codeCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new StockCodeTableCell();
            }
        });
        nameCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new StockNameTableCell();
            }
        });
        industryCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new StockSectionTableCell();
            }
        });
    }

    private class StockCodeTableCell extends TableCell<StockAttributesVO, String> {
        public StockCodeTableCell() {}
        @Override
        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                int index = getIndex();
                StockAttributesVO stockAttributesVO = (StockAttributesVO) stockList.getItems().get(index);
                String code = stockAttributesVO.code;
                if (operateAddCodeSet.contains(code)) {
                    setStyle("-fx-text-fill: red;");
                } else if (operateDelCodeSet.contains(code)) {
                    setStyle("-fx-text-fill: #00bd00");
                } else {
                    setStyle("-fx-text-fill: #37474F");
                }
                setGraphic(null);
                setText(code);
            }
        }
    }

    private class StockNameTableCell extends TableCell<StockAttributesVO, String> {
        public StockNameTableCell() {}
        @Override
        protected void updateItem(String s, boolean empty) {
            super.updateItem(s, empty);
            if (!empty) {
                int index = getIndex();
                StockAttributesVO stockAttributesVO = (StockAttributesVO) stockList.getItems().get(index);
                String code = stockAttributesVO.code;
                String name = stockAttributesVO.name;
                if (operateAddCodeSet.contains(code)) {
                    setStyle("-fx-text-fill: red;");
                } else if (operateDelCodeSet.contains(code)) {
                    setStyle("-fx-text-fill: #00bd00");
                } else {
                    setStyle("-fx-text-fill: #37474F");
                }
                setGraphic(null);
                setText(name);
            }
        }
    }

    private class StockSectionTableCell extends TableCell<StockAttributesVO, String> {
        public StockSectionTableCell() {}
        @Override
        protected void updateItem(String s, boolean empty) {
            super.updateItem(s, empty);
            if (!empty) {
                int index = getIndex();
                StockAttributesVO stockAttributesVO = (StockAttributesVO) stockList.getItems().get(index);
                String section = stockAttributesVO.section;
                String code = stockAttributesVO.code;
                if (operateAddCodeSet.contains(code)) {
                    setStyle("-fx-text-fill: red;");
                } else if (operateDelCodeSet.contains(code)) {
                    setStyle("-fx-text-fill: #00bd00");
                } else {
                    setStyle("-fx-text-fill: #37474F");
                }
                setGraphic(null);
                setText(section);
            }
        }
    }



    /**
     * 新增的股票集合
     * @param button
     */
    private void initListAddCode(Button button) {
        ArrayList<String> list = (ArrayList<String>) stockController.getStockCodesBySection(button.getText());
        operateAddCodeSet.addAll(list);
        deleteAddSameCode();
    }

    /**
     * 删除的股票集合
     * @param button
     */
    private void initListDelCode(Button button) {
        ArrayList<String> list = (ArrayList<String>) stockController.getStockCodesBySection(button.getText());
        operateDelCodeSet.addAll(list);
        deleteDelSameCode();
    }

    /**
     * 删除operateDelCodeSet中重复元素
     */
    private void deleteAddSameCode() {
        for (String code : operateAddCodeSet) {
            if (operateDelCodeSet.contains(code)) {
                operateDelCodeSet.remove(code);
            }
        }
    }
    /**
     * 删除operateAddCodeSet中重复元素
     */
    private void deleteDelSameCode() {
        for (String code : operateDelCodeSet) {
            if (operateAddCodeSet.contains(code)) {
                operateAddCodeSet.remove(code);
            }
        }
    }


    /**
     * 初始化个股搜索添加
     */
    private void initStockSearch() {

        inputField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String query = inputField.getText();

                ObservableList<String> list = null;
                try {
                    list = FXCollections.observableArrayList(stockController.findSuggestions(query));
                } catch (StockNotFoundException e) {
                    listView.setVisible(false);
                }

                listView.setItems(list);
                initSuggestView(list);
                listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            String name = listView.getSelectionModel().getSelectedItem().toString();
                            listView.setVisible(false);
                            inputField.setText(name);
                        } catch (Exception e) {
                            //此处无需做任何处理
                        }
                    }
                });
                listView.setVisible((list != null) && !query.equals(""));
                listView.setOpacity(1);
            }
        });
    }
    private void initSuggestView(ObservableList list) {
        listView.setLayoutX(37);
        listView.setLayoutY(100);
        if (list != null) {
            if (list.size() > 6) {
                listView.setPrefHeight(200);
            } else {
                listView.setPrefHeight(list.size() * 30);
            }
        }
        listView.setPrefWidth(300);
        listView.setVisible(false);
    }

    @FXML
    private void addSingleStockEvent() {
        StockAttributesVO stockAttributesVO = null;
        try {
            stockAttributesVO = universeController.getStockAttributes(inputField.getText());

            String codeMark = stockAttributesVO.code;
            if (!stockCodeSet.contains(codeMark)) {
                controllerBtnPane.setVisible(true);
                operateAddCodeSet.add(codeMark);
                stockCodeSet.add(codeMark);
                deleteAddSameCode();
                initTable();
                initOperateStockInList();
                stockList.refresh();
            } else {
                WarningLabelTimeLine.timeLineWarning(warningExistLabel);
            }
        } catch (StockNotFoundException e) {
            WarningLabelTimeLine.timeLineWarningContent(warningExistLabel, "股票不存在");
        }

    }

    /**
     * 确认修改事件
     */
    @FXML
    private void confirmChangeStockList() {
        for (int i = 0; i<btnIndustryList.size(); i++) {
            Button button = (Button) btnIndustryList.get(i);
            if (operatedBtnMap.get(button).equals(true)) {
                if (btnMap.get(button).equals(true)) {
                    ArrayList<String> codeList = (ArrayList) stockController.getStockCodesBySection(button.getText());
                    for (int j = 0; j<codeList.size(); j++) {
                        stockCodeSet.add(codeList.get(j));
                    }
                } else {
                    ArrayList<String> codeList = (ArrayList) stockController.getStockCodesBySection(button.getText());
                    for (int j = 0; j<codeList.size(); j++) {
                        if (stockCodeSet.contains(codeList.get(j))) {
                            stockCodeSet.remove(codeList.get(j));
                        }
                    }
                }
            }
        }

        stockCodeSet.addAll(operateAddCodeSet);
        stockCodeSet.removeAll(operateDelCodeSet);

        operateAddCodeSet.clear();
        operateDelCodeSet.clear();

        universeController.updateUniverse(poolName, setToList());
        ObservableList paneList = mainPane.getChildren();
        mainPane.getChildren().remove(paneList.size() - 1);
        mainPane.getChildren().add(new SectionInfoPane(mainPane, poolName));
    }

    /**
     * 取消修改
     */
    @FXML
    private void cancelChangeStock() {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new SectionInfoPane(mainPane, poolName));
    }

    /**
     * set转list
     * @return
     */
    private ArrayList<String> setToList() {
        ArrayList<String> codeList = new ArrayList<>();
        codeList.addAll(stockCodeSet);
        return codeList;
    }

    /**
     * 回测
     */
    private void initRecallEvent() {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new StockStrategyPane(mainPane, poolName));
        recallBtn.setOnMouseEntered(event -> {
            Timeline timeline = new Timeline();
            timeline.setAutoReverse(true);
            KeyValue kv1 = new KeyValue(recallBtn.prefWidthProperty(), 320);
            KeyFrame kf1 = new KeyFrame(Duration.millis(300), kv1);
            KeyValue kv2 = new KeyValue(recallBtn.prefHeightProperty(), 80);
            KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);
            KeyValue kv3 = new KeyValue(recallBtn.layoutXProperty(), 965);
            KeyFrame kf3 = new KeyFrame(Duration.millis(300), kv3);
            KeyValue kv4 = new KeyValue(recallBtn.layoutYProperty(), 740);
            KeyFrame kf4 = new KeyFrame(Duration.millis(300), kv4);
            timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
            timeline.play();
        });
        recallBtn.setOnMouseExited(event -> {
            Timeline timeline = new Timeline();
            timeline.setAutoReverse(true);
            KeyValue kv1 = new KeyValue(recallBtn.prefWidthProperty(), 300);
            KeyFrame kf1 = new KeyFrame(Duration.millis(300), kv1);
            KeyValue kv2 = new KeyValue(recallBtn.prefHeightProperty(), 70);
            KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);
            KeyValue kv3 = new KeyValue(recallBtn.layoutXProperty(), 975);
            KeyFrame kf3 = new KeyFrame(Duration.millis(300), kv3);
            KeyValue kv4 = new KeyValue(recallBtn.layoutYProperty(), 745);
            KeyFrame kf4 = new KeyFrame(Duration.millis(300), kv4);
            timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
            timeline.play();
        });
        recallBtn.setOnAction(event -> {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(new StockStrategyPane(mainPane, poolName));
        });

    }

    /**
     * 股票列表按钮
     */
    private class SectionStockListButtonCell extends TableCell<StockAttributesVO, Boolean> {

        final private Button button = new Button();

        public SectionStockListButtonCell() {
            Image image = new Image("/img/del-btn.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            button.setGraphic(imageView);
            button.getStyleClass().add("del-btn");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controllerBtnPane.setVisible(true);
                    int index = getIndex();
                    StockAttributesVO stockAttributesVO = (StockAttributesVO) stockList.getItems().get(index);
                    stockList.getItems().remove(index);
                    operateDelCodeSet.add(stockAttributesVO.code);
                    deleteDelSameCode();
                    initOperateStockInList();
                    stockList.refresh();
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                this.setGraphic(null);
                this.setText(null);
            } else {
                this.setText(null);
                this.setGraphic(button);
            }
        }
    }
}
