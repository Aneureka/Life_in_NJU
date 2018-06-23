package presentation.controller;

import controller.backtest.UniverseController;
import controller.backtest.UniverseControllerImpl;
import controller.stock.StockController;
import controller.stock.StockControllerImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import presentation.ui.SectionInfoPane;
import presentation.ui.SelfSectionPane;
import presentation.ui.SelfSelectStockPane;
import presentation.util.alert.WarningLabelTimeLine;
import vo.backtest.UniverseVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyj on 2017/4/13.
 */
public class SelfSelectStockController {

    @FXML private Button addStockPoolBtn;

    @FXML private Pane confirmAddStockPoolPane;
    @FXML private TextField stockPoolNameInput;
    @FXML private Button confirmBtn;
    @FXML private Button addAreaBtn;

    @FXML private Pane coverPane;

    @FXML private Pane addAreaPane;
    @FXML private Button cancelBtn;
    @FXML private Button confirmAddBtn;

    @FXML private Label warningLabel;

    @FXML private VBox vboxLayout;

    @FXML private FlowPane industryFlowPane;

    private Pane mainPane;

    private UniverseController universeController;
    private StockController stockController;

    private ObservableList btnIndustryList;

    private Map btnStateMap = new HashMap();

    private Boolean btnChosen = true;
    private Boolean btnNotChosen = false;

    public void launch(Pane mainPane) {
        this.mainPane = mainPane;

        btnIndustryList = industryFlowPane.getChildren();

        universeController = new UniverseControllerImpl();
        stockController = new StockControllerImpl();

        initPoolList();
        initStyle();
        initStateMap();

        coverPane.setVisible(false);
        addAreaPane.setVisible(false);
        warningLabel.setVisible(false);
        confirmAddStockPoolPane.setVisible(false);
    }

    private void initStateMap() {
        for (int i = 0; i<btnIndustryList.size(); i++) {
            Button button = (Button) btnIndustryList.get(i);
            btnStateMap.put(button, btnNotChosen);
        }
    }

    private void initStyle() {
        for (int i = 0; i<btnIndustryList.size(); i++) {
            Button button = (Button) btnIndustryList.get(i);
            button.getStyleClass().add("industry-btn");
            FlowPane.setMargin(button, new Insets(5, 10, 5, 10));
            initMouseClickEvent(button);
        }
    }

    private void initMouseClickEvent(Button button) {
        button.setOnMouseClicked(event -> {
            ObservableList observableList = button.getStyleClass();
            if (btnStateMap.get(button).equals(btnNotChosen)) {
                observableList.remove(observableList.size() - 1);
                button.getStyleClass().add("industry-selected-btn");
                btnStateMap.put(button, btnChosen);
            } else {
                btnStateMap.put(button, btnNotChosen);
                observableList.remove(observableList.size() - 1);
                button.getStyleClass().add("industry-btn");
            }
        });
    }



    @FXML
    private void directAddNewPool() {

        String poolName = stockPoolNameInput.getText();

        if (!poolName.equals("")) {
            ArrayList<UniverseVO> sectionList = (ArrayList<UniverseVO>) universeController.getAllUniverses();
            boolean have = false;
            for (int i = 0; i<sectionList.size(); i++) {
                if (sectionList.get(i).name.equals(poolName)) {
                    have = true;
                    break;
                }
            }
            if (have) {
                WarningLabelTimeLine.timeLineWarning(warningLabel);
            } else {
                try {
                    universeController.createNewUniverse(poolName, new ArrayList<>());
                } catch (DuplicateName duplicateName) {
                    duplicateName.printStackTrace();
                }
                coverPane.setVisible(false);
                addAreaPane.setVisible(false);
                confirmAddStockPoolPane.setVisible(false);
                ArrayList<UniverseVO> list = (ArrayList) (universeController.getAllUniverses());
                for (int i = 0; i<list.size(); i++) {
                    if (list.get(i).name.equals(poolName)) {
                        mainPane.getChildren().add(1, new SectionInfoPane(mainPane, poolName));
                    }
                }
                initPoolList();
            }
        } else {
            WarningLabelTimeLine.timeLineWarningContent(warningLabel, "股票池名称不能为空");
        }

    }

    private void addNewHBox(String poolName) {
        HBox hBox = new HBox();
        hBox.getChildren().add(new SelfSectionPane(mainPane, poolName));
        vboxLayout.getChildren().add(hBox);
        setHBoxStyle(hBox);
    }

    private void initPoolList() {
        ArrayList<UniverseVO> list = (ArrayList) (universeController.getAllUniverses());

        vboxLayout.getChildren().clear();

        HBox hBox = new HBox();
        vboxLayout.getChildren().add(hBox);
        setHBoxStyle(hBox);

        for (int i = 0; i<list.size(); i++) {
            ObservableList child = vboxLayout.getChildren();
            ObservableList poolList = ((HBox)(child.get(child.size() - 1))).getChildren();
            if (poolList.size() == 4) {
                addNewHBox(list.get(i).name);
            } else {
                HBox lasthbox = (HBox) child.get(child.size() - 1);
                lasthbox.getChildren().add(new SelfSectionPane(mainPane, list.get(i).name));
            }
        }

    }

    private void setHBoxStyle(HBox hBox) {
        hBox.setPadding(new Insets(50, 70, 20, 95));
        hBox.setSpacing(50);
    }



    @FXML
    private void addStockPoolEvent() {
        coverPane.setVisible(true);
        addAreaPane.setVisible(false);
        confirmAddStockPoolPane.setVisible(true);
    }

    @FXML
    private void addAreaStockEvent() {
        String poolName = stockPoolNameInput.getText();

        if (!poolName.equals("")) {
            ArrayList<UniverseVO> sectionList = (ArrayList<UniverseVO>) universeController.getAllUniverses();
            boolean have = false;
            for (int i = 0; i<sectionList.size(); i++) {
                if (sectionList.get(i).name.equals(poolName)) {
                    have = true;
                    break;
                }
            }
            if (have) {
                WarningLabelTimeLine.timeLineWarning(warningLabel);
            } else {
                coverPane.setVisible(true);
                addAreaPane.setVisible(true);
                confirmAddStockPoolPane.setVisible(false);
            }
        } else {
            WarningLabelTimeLine.timeLineWarningContent(warningLabel, "股票池名称不能为空");
        }
    }
    @FXML
    private void confirmAddAreaEvent() {
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i<btnIndustryList.size(); i++) {
            Button button = (Button) btnIndustryList.get(i);
            Boolean isBtnChosen = (Boolean) btnStateMap.get(button);
            if (isBtnChosen) {
                ArrayList<String> codeList = (ArrayList<String>) stockController.getStockCodesBySection(button.getText());
                for (int j = 0; j<codeList.size(); j++) {
                    list.add(codeList.get(j));
                }
            }
        }
        try {
            universeController.createNewUniverse(stockPoolNameInput.getText(), list);
        } catch (DuplicateName duplicateName) {
            duplicateName.printStackTrace();
        }
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new SelfSelectStockPane(mainPane));
        mainPane.getChildren().add(new SectionInfoPane(mainPane, stockPoolNameInput.getText()));
    }

    @FXML
    private void closeAddNewPool() {
        coverPane.setVisible(false);
        addAreaPane.setVisible(false);
        confirmAddStockPoolPane.setVisible(false);
    }

    @FXML
    private void closeAddArea() {
        coverPane.setVisible(false);
        addAreaPane.setVisible(false);
        confirmAddStockPoolPane.setVisible(false);
    }
}
