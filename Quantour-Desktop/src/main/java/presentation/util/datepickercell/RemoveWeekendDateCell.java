package presentation.util.datepickercell;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by Hitiger on 2017/4/8.
 * Description :
 */
public class RemoveWeekendDateCell implements Callback<DatePicker, DateCell> {
    @Override
    public DateCell call(DatePicker param) {
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                        item.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #e8eef2;-fx-opacity: 0.5");
                }
            }
        };
    }
}
