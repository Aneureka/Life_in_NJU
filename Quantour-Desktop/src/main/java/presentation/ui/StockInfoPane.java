package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.StockInfoController;
import util.exception.StockNotFoundException;
import util.exception.TimeException;

import java.io.IOException;

/**
 * Created by wyj on 2017/3/8.
 * Description:
 */
public class StockInfoPane extends Pane {

    public StockInfoPane(Pane mainPane, String code) throws TimeException, StockNotFoundException {
        loadFxml(mainPane, code);
    }

    private void loadFxml(Pane mainPane, String code) throws TimeException, StockNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/stockInfo.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StockInfoController stockInfoController = fxmlLoader.getController();
        stockInfoController.launch(mainPane, code);
    }

}
