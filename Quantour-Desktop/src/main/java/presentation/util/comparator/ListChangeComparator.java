package presentation.util.comparator;

import java.util.Comparator;

/**
 * Created by Hitiger on 2017/4/15.
 * Description :
 */
public class ListChangeComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        double inc1 = Double.parseDouble(o1);
        double inc2 = Double.parseDouble(o2);
        if(inc1 < inc2) return 1;
        if(inc1> inc2)return -1;
        return 0;
    }

}
