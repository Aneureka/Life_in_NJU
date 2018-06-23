package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.StockCompareGraphController;

import java.io.IOException;

/**
 * Created by Hitiger on 2017/3/8.
 * Description :
 */
public class StockCompareGraphPane extends Pane{

    public StockCompareGraphPane(Pane mainPane) {
        loadFxml(mainPane);
    }

    private void loadFxml(Pane mainPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/stockCompareGraph.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StockCompareGraphController stockCompareGraphController = fxmlLoader.getController();
        stockCompareGraphController.launch(mainPane);
    }
}
