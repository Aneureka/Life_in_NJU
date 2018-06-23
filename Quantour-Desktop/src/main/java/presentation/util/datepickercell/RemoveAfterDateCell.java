package presentation.util.datepickercell;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

/**
 * Created by Hitiger on 2017/3/15.
 * Description :
 */
public class RemoveAfterDateCell implements Callback<DatePicker, DateCell> {
    private LocalDate localDate;

    public RemoveAfterDateCell(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public DateCell call(final DatePicker param) {
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isAfter(
                        localDate)
                        ) {
                    setDisable(true);
                    setStyle("-fx-background-color: #e8eef2;-fx-opacity: 0.5");
                }
            }
        };
    }
}
