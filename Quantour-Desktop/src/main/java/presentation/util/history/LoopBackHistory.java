package presentation.util.history;
import presentation.util.history.History;

import java.util.ArrayList;

/**
 * Created by Hitiger on 2017/4/13.
 * Description :
 */
public class LoopBackHistory {

    private static ArrayList<History> list = new ArrayList<>();

    public static ArrayList<History> getHistoryList() {
        return list;
    }

    public static void addHistory(History history) {
        list.add(history);
    }
}
