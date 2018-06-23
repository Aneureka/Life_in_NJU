package presentation.util.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * Created by Hitiger on 2016/11/25.
 * Description : 确认提示框
 */
public class ConfirmAlert extends Alert{

    public ConfirmAlert(String contentText,String title){
        this(AlertType.CONFIRMATION,contentText,ButtonType.OK,ButtonType.CANCEL);
        this.setTitle(title);
        this.setHeaderText("");
        this.initStyle(StageStyle.UTILITY);
    }

    public ConfirmAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }
}
