package presentation.util;

import javafx.scene.control.DatePicker;
import presentation.util.datepickercell.RemoveAfterDateCell;
import presentation.util.datepickercell.RemoveBeforeDateCell;

/**
 * Created by Hitiger on 2017/4/16.
 * Description :
 */
public class MyDatePicker {

    public static void removeDateCell(DatePicker startDatePicker, DatePicker endDatePicker) {
        endDatePicker.setOnAction(e -> {
            startDatePicker.setDayCellFactory(new RemoveAfterDateCell(endDatePicker.getValue()));
        });
        startDatePicker.setOnAction(e -> {
            endDatePicker.setDayCellFactory(new RemoveBeforeDateCell(startDatePicker.getValue()));
        });

    }
}
