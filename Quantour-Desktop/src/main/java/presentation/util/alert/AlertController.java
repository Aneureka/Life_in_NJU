package presentation.util.alert;

import javafx.scene.control.ButtonType;

/**
 * Created by Hitiger on 2017/3/16.
 * Description :
 */
public class AlertController {

    /**
     * 确认退出系统提示框
     * @return
     */
    public static Boolean showConfirmExitAlert(){
        ConfirmAlert confirmAlert = new ConfirmAlert("您确定要退出系统吗？","确认退出");
        confirmAlert.showAndWait();
        final ButtonType rtn = confirmAlert.getResult();
        if (rtn == ButtonType.OK) {
            return true;
        }
        return false;
    }

    /**
     * 查询结果为空提示框
     * @param contentText 提示内容
     * @param title       提示标题
     */
    public static void showNullWrongAlert(String contentText,String title){
        NullWrongAlert nullWrongAlert = new NullWrongAlert(contentText,title);
        nullWrongAlert.showAndWait();
    }
}
