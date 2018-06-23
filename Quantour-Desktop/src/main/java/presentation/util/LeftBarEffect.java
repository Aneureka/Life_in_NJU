package presentation.util;

import javafx.scene.control.Button;

import java.util.ArrayList;

/**
 * Created by wyj on 2017/3/9.
 */
public class LeftBarEffect {

    public void ButtonActionEffect(Button button, ArrayList<Button> list) {
        for (int i = 0; i<list.size(); i++) {
            if (button != list.get(i)) {
                list.get(i).setStyle("-fx-opacity: 0.45");
                list.get(i).getStyleClass().remove("left-btn-actived");
            } else {
                button.setStyle("-fx-opacity: 1");
                button.getStyleClass().remove("left-btn-actived");
                button.getStyleClass().add("left-btn-actived");
            }
        }
    }

    public void ButtonOnEffect(Button button, ArrayList<Button> list, Button currentBtn) {
        button.setStyle("-fx-opacity: 1");
        for (int i = 0; i<list.size(); i++) {
            Button tempBtn = list.get(i);
            if (tempBtn != button) {
                if (tempBtn != currentBtn) {
                    tempBtn.setStyle("-fx-opacity: 0.45");
                } else {
                    tempBtn.setStyle("-fx-opacity: 1");
                }
            }
        }
    }

    public void ButtonOutEffect(Button button, Button currentBtn) {
        button.setStyle("-fx-opacity: 0.45");
        currentBtn.setStyle("-fx-opacity: 1");
    }
}
