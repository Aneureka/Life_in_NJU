package presentation.util.tablecell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import vo.market.DefaultStockVO;

/**
 * Created by wyj on 2017/3/29.
 */
public class StockListChangeCell extends TableCell<DefaultStockVO, String> {

    private TableView tableView;

    public StockListChangeCell(final TableView tableView) {
        this.tableView = tableView;
    }

    @Override
    protected void updateItem(String t, boolean empty) {
        super.updateItem(t, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {

            DefaultStockVO defaultStockVO = (DefaultStockVO) tableView.getItems().get(getTableRow().getIndex());
            if (defaultStockVO.increase < 0) {
                setText(t);
                setStyle("-fx-text-fill:#00c200;");
            } else {
                setText("+" + t);
                setStyle("-fx-text-fill:#ff0000;");
            }
        }

    }
}
