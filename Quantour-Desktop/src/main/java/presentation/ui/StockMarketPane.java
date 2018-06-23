package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.StockMarketController;

import java.io.IOException;

/**
 * Created by wyj on 2017/3/8.
 */
public class StockMarketPane extends Pane {

    public StockMarketPane(Pane mainPane) {
        loadFxml(mainPane);
    }

    private void loadFxml(Pane mainPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/stockMarket.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StockMarketController stockMarketController = fxmlLoader.getController();
        stockMarketController.launch(mainPane);
    }
}
