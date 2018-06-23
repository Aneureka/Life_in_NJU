package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.controller.ComStockController;
import presentation.util.MyWindow;

import java.io.IOException;

/**
 * Created by Hitiger on 2017/3/8.
 * Description :
 */
public class ComStockScene extends Scene{
    public ComStockScene(Parent root, Stage primaryStage) {
        super(root);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/common.fxml"));

        try {
            this.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //配置控制器
        ComStockController comStockController = fxmlLoader.getController();
        comStockController.launch(primaryStage);

        //实现窗口可拖动
        MyWindow.enableWindowDrag(this.getRoot(), primaryStage);
    }
}
