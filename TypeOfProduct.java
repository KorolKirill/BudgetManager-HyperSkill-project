package budget;

import java.util.ArrayList;
import java.util.Comparator;

public class TypeOfProduct {
    String name;
    ArrayList<String> list = new ArrayList<>();
    private Comparator productsPriceComporator = new productsPriceComporator();

    public TypeOfProduct(String name) {
        this.name = name;
    }
    public boolean isEmpty() {
        return list.isEmpty();
    }
    public void add(String product) {
        list.add(product);
    }

    void sortList() {
        list.sort(productsPriceComporator);
    }

    public void showListOfPurchases () {
        System.out.println(name+":");
        if (isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        }
        else {
            double total = 0;
            for (String purchase : list) {
                double price = Double.parseDouble(purchase.substring(purchase.lastIndexOf("$")+1));
                String nameOfProduct = purchase.substring(0,purchase.lastIndexOf("$"));
                System.out.printf("%s$%.2f\n",nameOfProduct,price);
                total += price*100;
            }
            System.out.println("Total sum: $" + total/100);
            System.out.println();
        }
    }
    public double showListForTotal() {
        double total = 0;
        for (String purchase : list) {
            double price = Double.parseDouble(purchase.substring(purchase.lastIndexOf("$")+1));
            String nameOfProduct = purchase.substring(0,purchase.lastIndexOf("$"));
            System.out.printf("%s$%.2f\n",nameOfProduct,price);
            total += price*100;
        }
        return total/100;
    }

    double countTotalOfCurrentProduct() {
        double total = 0;
        for (String purchase : list) {
            double price = Double.parseDouble(purchase.substring(purchase.lastIndexOf("$")+1));
            total += price*100;
        }
        return total/100;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }
}
