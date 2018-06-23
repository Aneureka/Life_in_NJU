package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import presentation.ui.ComStockScene;

/**
 * Created by Hitiger on 2017/3/8.
 * Description : 程序入口
 */
public class Quantour extends Application{

    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new ComStockScene(new Group(), primaryStage));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
