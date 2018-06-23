package presentation.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import presentation.controller.SectionInfoController;

import java.io.IOException;

/**
 * Created by wyj on 2017/4/16.
 */
public class SectionInfoPane extends Pane {

    public SectionInfoPane(Pane mainPane, String poolName) {
        loadFxml(mainPane, poolName);
    }

    private void loadFxml(Pane mainPane, String poolName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/sectionInfo.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SectionInfoController sectionInfoController = fxmlLoader.getController();
        sectionInfoController.launch(mainPane, poolName);
    }
}
