package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.StockListController;

import java.io.IOException;

/**
 * Created by wyj on 2017/3/8.
 */
public class StockListPane extends Pane {

    public StockListPane(Pane mainPane) {
        loadFxml(mainPane);
    }

    private void loadFxml(Pane mainPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/stockList.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StockListController stockListController = fxmlLoader.getController();
        stockListController.launch(mainPane);
    }
}
