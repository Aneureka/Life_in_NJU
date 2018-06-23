package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.SelfSectionController;

import java.io.IOException;

/**
 * Created by wyj on 2017/4/13.
 */
public class SelfSectionPane extends Pane {

    public SelfSectionPane(Pane mainPane, String poolName) {
        loadFxml(mainPane, poolName);
    }

    private void loadFxml(Pane mainPane, String poolName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/selfSection.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SelfSectionController selfSectionController = fxmlLoader.getController();
        selfSectionController.launch(mainPane, poolName);
    }
}
