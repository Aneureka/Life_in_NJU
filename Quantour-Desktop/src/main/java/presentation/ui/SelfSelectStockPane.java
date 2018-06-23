package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.SelfSelectStockController;

import java.io.IOException;

/**
 * Created by wyj on 2017/4/13.
 */
public class SelfSelectStockPane extends Pane {

    public SelfSelectStockPane(Pane mainPane) {
        loadFxml(mainPane);
    }

    private void loadFxml(Pane mainPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/selfSelectStock.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SelfSelectStockController selfSelectStockController = fxmlLoader.getController();
        selfSelectStockController.launch(mainPane);
    }
}
