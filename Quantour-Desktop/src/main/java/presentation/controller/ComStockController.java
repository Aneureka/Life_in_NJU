package presentation.controller;

import controller.stock.StockController;
import controller.stock.StockControllerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.ui.*;
import presentation.util.LeftBarEffect;
import presentation.util.alert.AlertController;
import util.exception.StockNotFoundException;
import util.exception.TimeException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Hitiger on 2017/3/8.
 * Description :
 */
public class ComStockController {

    @FXML private Pane mainPane;
    @FXML private ImageView rightArrow;

    @FXML private Button stockInfoBtn;
    @FXML private Button careBtn;
    @FXML private Button compareBtn;
    @FXML private Button marketBtn;
    @FXML private Button logoutBtn;
    @FXML private Button strategyBtn;

    @FXML private ImageView stockInfoImg;
    @FXML private ImageView careImg;
    @FXML private ImageView compareImg;
    @FXML private ImageView marketImg;
    @FXML private ImageView strategyImg;

    @FXML private TextField stockNameOrCode;

    private Button currentBtn;

    private Stage stage;

    private LeftBarEffect leftBarEffect;
    private ArrayList<Button> leftBarArr;
    private StockController stockController;
    private ListView listView;

    public void launch(Stage primaryStage){
        this.stage = primaryStage;

        leftBarArr = new ArrayList<>(Arrays.asList(stockInfoBtn, careBtn, compareBtn, marketBtn, strategyBtn, logoutBtn));
        leftBarEffect = new LeftBarEffect();

        rightArrow.setLayoutY(300);
        currentBtn = stockInfoBtn;
        leftBarEffect.ButtonActionEffect(currentBtn, leftBarArr);
        mouseClickEvent();
        initSuggestView();
        showStockInfo();
        initService();
        initKeySearch();
        initListener(mainPane);
    }

    private void initListener(Pane newPane) {
       newPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listView.setVisible(false);
            }
        });
    }


    private void initService() {
        stockController = new StockControllerImpl();
    }


    private void initKeySearch() {

        stockNameOrCode.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String query = stockNameOrCode.getText();

                ObservableList<String> list = null;
                try {
                    list = FXCollections.observableArrayList(stockController.findSuggestions(query));
                    if (list.size() < 7) {
                        listView.setPrefHeight(list.size() * 28 + 2);
                    } else {
                        listView.setPrefHeight(200);
                    }
                } catch (StockNotFoundException e) {
                    listView.setVisible(false);
                }

                listView.setItems(list);
                listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            String name = listView.getSelectionModel().getSelectedItem().toString();
                            changePane(new StockInfoPane(mainPane, name));
                            listView.setVisible(false);
                            stockNameOrCode.setText(name);
                            headLabelMove(300, stockInfoBtn);
                        } catch (TimeException e) {
                            AlertController.showNullWrongAlert("未找到该股票", "查找失败");
                        } catch (StockNotFoundException e) {
                            AlertController.showNullWrongAlert("未找到该股票", "查找失败");
                        }
                    }
                });

                listView.setVisible((list != null) && !query.equals(""));
                listView.setOpacity(0.7);
            }
        });
    }
    /**
     * 更换Pane
     */
    private void changePane(Pane newPane){
        mainPane.getChildren().clear();
        listView.setVisible(false);
        mainPane.getChildren().addAll(newPane, listView);
        initListener(newPane);
    }

    private void initSuggestView(){
        listView = new ListView();
        listView.setLayoutX(35);
        listView.setLayoutY(0);
        listView.setPrefHeight(200);
        listView.setPrefWidth(265);
        listView.setVisible(false);
    }

    @FXML
    private void showStockInfo() {
        changePane(new StockListPane(mainPane));
        headLabelMove(300, stockInfoBtn);
    }


    @FXML
    private void showMyCare() {
        changePane(new SelfSelectStockPane(mainPane));
        headLabelMove(355, careBtn);
    }

    @FXML
    private void showStockCompare() {
        changePane(new StockCompareGraphPane(mainPane));
        headLabelMove(410, compareBtn);
    }

    @FXML
    private void showStockMarket() {
        changePane(new StockMarketPane(mainPane));
        headLabelMove(465, marketBtn);
    }

    @FXML
    private void showStockStrategy() {
        changePane(new StockStrategyPane(mainPane, ""));
        headLabelMove(520, strategyBtn);
    }

    private void headLabelMove(int y, Button stockBtn) {
        rightArrow.setLayoutY(y);
        currentBtn = stockBtn;
        mouseClickEvent();
        mouseAction(currentBtn);
    }

    @FXML
    private void findStock() {
        String stockName = stockNameOrCode.getText();
        if (stockName.equals("")) return;
        try {
            headLabelMove(300, stockInfoBtn);
            changePane(new StockInfoPane(mainPane,stockName));
        } catch (TimeException e) {
            AlertController.showNullWrongAlert("未找到该股票", "查找失败");
        } catch (StockNotFoundException e) {
            AlertController.showNullWrongAlert("未找到该股票", "查找失败");
        }
    }
    @FXML
    private void closeWindow(){
        stage.close();
    }

    @FXML
    private void minWindow(){
        stage.setIconified(true);
    }

    private void mouseAction(Button button) {
        leftBarEffect.ButtonActionEffect(button, leftBarArr);
    }

    private void mouseClickEvent() {
        Image listPic = new Image("/img/listIcon.png");
        Image carePic = new Image("/img/careIcon.png");
        Image comparePic = new Image("/img/compareIcon.png");
        Image marketPic = new Image("/img/marketIcon.png");
        Image strategyPic = new Image("/img/common/strategy.png");
        if (currentBtn == stockInfoBtn) {
            stockInfoImg.setImage(new Image("/img/listIcon_hover.png"));
            careImg.setImage(carePic);
            compareImg.setImage(comparePic);
            marketImg.setImage(marketPic);
            strategyImg.setImage(strategyPic);
        } else if (currentBtn == careBtn) {
            careImg.setImage(new Image("/img/careIcon_hover.png"));
            stockInfoImg.setImage(listPic);
            compareImg.setImage(comparePic);
            marketImg.setImage(marketPic);
            strategyImg.setImage(strategyPic);
        } else if (currentBtn == compareBtn) {
            compareImg.setImage(new Image("/img/compareIcon_hover.png"));
            stockInfoImg.setImage(listPic);
            careImg.setImage(carePic);
            marketImg.setImage(marketPic);
            strategyImg.setImage(strategyPic);
        } else if (currentBtn == marketBtn) {
            marketImg.setImage(new Image("/img/marketIcon_hover.png"));
            stockInfoImg.setImage(listPic);
            careImg.setImage(carePic);
            compareImg.setImage(comparePic);
            strategyImg.setImage(strategyPic);
        } else if (currentBtn == strategyBtn) {
            strategyImg.setImage(new Image("/img/common/strategy_hover.png"));
            marketImg.setImage(marketPic);
            careImg.setImage(carePic);
            compareImg.setImage(comparePic);
            stockInfoImg.setImage(listPic);
        }

    }

    private void mouseOnEvent(Button button) {
        leftBarEffect.ButtonOnEffect(button, leftBarArr, currentBtn);
    }

    private void mouseOutEvent(Button button) {
        leftBarEffect.ButtonOutEffect(button, currentBtn);
    }

    @FXML
    private void stockInfoMouseOn() {
        mouseOnEvent(stockInfoBtn);
    }
    @FXML
    private void  stockInfoMouseOut() {
        mouseOutEvent(stockInfoBtn);
    }
    @FXML
    private void careMouseOn() {
        mouseOnEvent(careBtn);
    }
    @FXML
    private void careMouseOut() {
        mouseOutEvent(careBtn);
    }
    @FXML
    private void compareMouseOn() {
        mouseOnEvent(compareBtn);
    }
    @FXML
    private void compareMouseOut() {
        mouseOutEvent(compareBtn);
    }
    @FXML
    private void marketMouseOn() {
        mouseOnEvent(marketBtn);
    }
    @FXML
    private void marketMouseOut() {
        mouseOutEvent(marketBtn);
    }
    @FXML
    private void strategyMouseOn() {
        mouseOnEvent(strategyBtn);
    }
    @FXML
    private void strategyMouseOut() {
        mouseOutEvent(strategyBtn);
    }
    @FXML
    private void logoutMouseOn() {
        mouseOnEvent(logoutBtn);
    }
    @FXML
    private void logoutMouseOut() {
        mouseOutEvent(logoutBtn);
    }
}
