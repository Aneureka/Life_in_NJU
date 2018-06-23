package presentation.util.datepickercell;


import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

/**
 * Created by Hitiger on 2017/3/15.
 * Description :
 */
public class RemoveBeforeDateCell implements Callback<DatePicker, DateCell> {

    private LocalDate localDate;

    public RemoveBeforeDateCell(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public DateCell call(final DatePicker param) {
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(
                        localDate)
                        ) {
                    setDisable(true);
                    setStyle("-fx-background-color: #e8eef2;-fx-opacity: 0.5");
                }
            }
        };
    }
}
