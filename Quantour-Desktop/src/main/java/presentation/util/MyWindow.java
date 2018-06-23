package presentation.util;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Hitiger on 2017/3/8.
 * Description : 实现窗口可拖动
 */
public class MyWindow {

    private static double xOffset = 0;
    private static double yOffset = 0;
    /**
     * 实现窗口可拖动
     * @param root
     * @param primaryStage
     */
    public static void enableWindowDrag(Parent root, Stage primaryStage) {
        root.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            event.consume();
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }
}
