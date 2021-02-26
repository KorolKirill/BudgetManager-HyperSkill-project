package budget;

import java.util.Comparator;

public class productsPriceComporator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        String s1 = (String) o1;
        String s2 = (String) o2;

        if (getPrice(s1) > getPrice(s2))
            return -1;
        else
            return 1;
    }
    private double getPrice(String s) {
        return Double.parseDouble(s.substring(s.lastIndexOf("$")+1));
    }
}
