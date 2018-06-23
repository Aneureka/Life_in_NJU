package presentation.util;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Hitiger on 2017/3/17.
 * Description :
 */
public class DisableColumnChangeListener implements ListChangeListener {

    private boolean suspended;
    private TableView tableView;
    private TableColumn[] columns;

    public DisableColumnChangeListener(TableView tableView, TableColumn[] columns){
        this.tableView = tableView;
        this.columns = columns;
    }

    @Override
    public void onChanged(Change c) {
        c.next();
        if (c.wasReplaced() && !suspended) {
            this.suspended = true;
            tableView.getColumns().setAll(columns);
            this.suspended = false;
        }
    }
}
