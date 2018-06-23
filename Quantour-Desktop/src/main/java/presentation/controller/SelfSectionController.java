package presentation.controller;

import controller.backtest.UniverseController;
import controller.backtest.UniverseControllerImpl;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import presentation.ui.SectionInfoPane;
import presentation.ui.SelfSelectStockPane;
import presentation.ui.StockStrategyPane;
import vo.backtest.UniverseVO;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by wyj on 2017/4/13.
 */
public class SelfSectionController {

    @FXML private Label stockPoolNameLabel;
    @FXML private Label stockNumLabel;
    @FXML private Button recallBtn;

    @FXML private Pane wholePane;

    @FXML private Button stockSectionDelBtn;

    private Pane mainPane;

    private UniverseController universeController;

    public void launch(Pane mainPane, String poolName) {
        this.mainPane = mainPane;

        universeController = new UniverseControllerImpl();

        initDelEvent(poolName);
        initLabel(poolName);
    }

    private void initLabel(String poolName) {
        stockPoolNameLabel.setText(poolName);
        ArrayList<UniverseVO> list = (ArrayList) universeController.getAllUniverses();
        for (int i = 0; i<list.size(); i++) {
            if (list.get(i).name.equals(poolName)) {
                stockNumLabel.setText(String.valueOf(list.get(i).codes.size()));
            }
        }
    }

    private void initDelEvent(String poolName) {
        wholePane.setOnMouseEntered(event -> {
            stockSectionDelBtn.setVisible(true);
        });

        wholePane.setOnMouseExited(event -> {
            stockSectionDelBtn.setVisible(false);
        });

        wholePane.setOnMouseClicked(event -> {
            mainPane.getChildren().add(new SectionInfoPane(mainPane, poolName));
        });

        recallBtn.setOnMouseEntered(event -> {
            Timeline timeline = new Timeline();
            timeline.setAutoReverse(false);
            KeyValue kv1 = new KeyValue(recallBtn.prefWidthProperty(), 180);
            KeyFrame kf1 = new KeyFrame(Duration.millis(300), kv1);
            KeyValue kv2 = new KeyValue(recallBtn.prefHeightProperty(), 50);
            KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);
            KeyValue kv3 = new KeyValue(recallBtn.layoutXProperty(), 35);
            KeyFrame kf3 = new KeyFrame(Duration.millis(300), kv3);
            KeyValue kv4 = new KeyValue(recallBtn.layoutYProperty(), 300);
            KeyFrame kf4 = new KeyFrame(Duration.millis(300), kv4);
            timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
            timeline.play();
        });
        recallBtn.setOnMouseExited(event -> {
            Timeline timeline = new Timeline();
            timeline.setAutoReverse(false);
            KeyValue kv1 = new KeyValue(recallBtn.prefWidthProperty(), 160);
            KeyFrame kf1 = new KeyFrame(Duration.millis(300), kv1);
            KeyValue kv2 = new KeyValue(recallBtn.prefHeightProperty(), 40);
            KeyFrame kf2 = new KeyFrame(Duration.millis(300), kv2);
            KeyValue kv3 = new KeyValue(recallBtn.layoutXProperty(), 45);
            KeyFrame kf3 = new KeyFrame(Duration.millis(300), kv3);
            KeyValue kv4 = new KeyValue(recallBtn.layoutYProperty(), 305);
            KeyFrame kf4 = new KeyFrame(Duration.millis(300), kv4);
            timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4);
            timeline.play();
        });

        recallBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPane.getChildren().clear();
                mainPane.getChildren().add(new StockStrategyPane(mainPane, poolName));
            }
        });

        stockSectionDelBtn.setOnAction(event -> {
            universeController.deleteUniverse(poolName);
            System.out.println(universeController.getAllUniverses().stream().map(e -> e.name).collect(Collectors.toList()).contains(poolName));

            mainPane.getChildren().clear();
            mainPane.getChildren().add(new SelfSelectStockPane(mainPane));

            System.out.println(poolName);
        });
    }
}
