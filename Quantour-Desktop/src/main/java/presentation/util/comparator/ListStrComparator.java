package presentation.util.comparator;

import java.util.Comparator;

/**
 * Created by Hitiger on 2017/3/24.
 * Description :
 */
public class ListStrComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int len1 = o1.length();
        int len2 = o2.length();
        double inc1 = Double.parseDouble(o1.substring(0, len1 - 1));
        double inc2 = Double.parseDouble(o2.substring(0, len2 - 1));
        if(inc1 < inc2) return 1;
        if(inc1> inc2)return -1;
        return 0;
    }

}
