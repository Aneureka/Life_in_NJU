package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.StockStrategyController;

import java.io.IOException;

/**
 * Created by wyj on 2017/3/27.
 */
public class StockStrategyPane extends Pane {

    public StockStrategyPane(Pane mainPane, String poolName) {
        loadFxml(mainPane, poolName);
    }

    private void loadFxml(Pane mainPane, String poolName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/stockStrategy.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StockStrategyController stockStrategyController = fxmlLoader.getController();
        stockStrategyController.launch(mainPane, poolName);
    }
}
