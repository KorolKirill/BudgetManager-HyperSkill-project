package budget;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FirstTask {
    private static final Scanner scanner = new Scanner(System.in);
    private static double balance;
    private static TypeOfProduct[] goods = {new TypeOfProduct("Food"),
    new TypeOfProduct("Clothes"),new TypeOfProduct("Entertainment"),
    new TypeOfProduct("Other")};

    private static void enterPurchase(TypeOfProduct typerOfPurchase) {
        System.out.println();
        System.out.println("Enter purchase name:");
        String purchaseName = scanner.nextLine();
        System.out.println("Enter its price:");
        double price = Double.parseDouble(scanner.nextLine());
        balance -= price;
        typerOfPurchase.add(purchaseName +" $"+price);
        System.out.println("Purchase was added!");
        System.out.println();
    }

    private static void addPurcase () {
        System.out.println(
                "Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");
        int switchCase = Integer.parseInt(scanner.nextLine());

        if (switchCase == 5) {
            return;
        }
        else if (switchCase>0 &&switchCase<5) {
            enterPurchase(goods[switchCase-1]);
            addPurcase();
        }
    }

    public static void mainMenu() throws Exception {
        boolean working= true;
        while (working) {
            System.out.println();
            System.out.println(
                                "Choose your action:\n" +
                                "1) Add income\n" +
                                "2) Add purchase\n" +
                                "3) Show list of purchases\n" +
                                "4) Balance\n" +
                                "5) Save\n" +
                                "6) Load\n" +
                                "7) Analyze (Sort)\n" +
                                "0) Exit"
            );
            int switchCase = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch (switchCase) {
                case 0:
                    System.out.println("Bye!");
                    working = false;
                    break;
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurcase();
                    break;
                case 3:
                    showPurchaseList();
                    break;
                case 4:
                  showBalance();
                  break;
                case 5:
                    saveDataInFile();
                    break;
                case 6:
                    loadDataFromFile();
                    break;
                case 7:
                    analyze();
                    break;
            }
        }
    }
    private static void analyze() throws Exception {
        System.out.println(
                        "How do you want to sort?\n" +
                        "1) Sort all purchases\n" +
                        "2) Sort by type\n" +
                        "3) Sort certain type\n" +
                        "4) Back"
        );
        int switchCase = Integer.parseInt(scanner.nextLine());
        if (switchCase == 1) {
            System.out.println();
            sortAllPurchases();
            analyze();
        }
        else if (switchCase == 2) {
            System.out.println();
            sortByType();
            analyze();
        }
        else if (switchCase == 3 ) {
            System.out.println();
            sortCertainType();
            analyze();
        }
    }
    private static void sortCertainType() {
        System.out.println(
                        "Choose the type of purchase\n" +
                        "1) Food\n" +
                        "2) Clothes\n" +
                        "3) Entertainment\n" +
                        "4) Other");
        int switchCase = Integer.parseInt(scanner.nextLine());
        System.out.println();
        if (switchCase>0 && switchCase<5) {
            goods[switchCase-1].sortList();
            goods[switchCase-1].showListOfPurchases();
        }


    }
    private static void sortByType () {
        TypeOfProduct[] goodsCopy = goods.clone();
        for (int i = 0; i <goodsCopy.length; i++ ) {
            for (int a = 0; a < (goodsCopy.length-1) ; a++) {
                if (goodsCopy[a].countTotalOfCurrentProduct() < goodsCopy[a+1].countTotalOfCurrentProduct()) {
                    TypeOfProduct temp = goodsCopy[a];
                    goodsCopy[a] = goodsCopy[a+1];
                    goodsCopy[a+1] = temp;
                }
            }
        }
        double total = 0;
        System.out.println("Types:");
        for (TypeOfProduct currentProduct : goodsCopy) {
            System.out.println(currentProduct.name+" - $"+currentProduct.countTotalOfCurrentProduct());
            total+=currentProduct.countTotalOfCurrentProduct();
        }
        System.out.println("Total sum: $"+total+"\n");
    }

    private static void sortAllPurchases() {
        ArrayList<String> allPurchases = new ArrayList<>();
        for (TypeOfProduct tp: goods) {
            for (String p : tp.list) {
                allPurchases.add(p);
            }
        }
        allPurchases.sort(new productsPriceComporator());
        double total = 0;
        for (String p :allPurchases ) {
            double price = Double.parseDouble(p.substring(p.lastIndexOf("$")+1));
            String nameOfProduct = p.substring(0,p.lastIndexOf("$"));
            System.out.printf("%s$%.2f\n",nameOfProduct,price);
            total += price;
        }
        if (allPurchases.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        }
        else {
        System.out.println("Total sum: $"+total +"\n");
        }
    }

    private static String filePath = "purchases.txt";
    private static void saveDataInFile() throws IOException {
        File purchasesData = new File(filePath);
        try (PrintWriter writer = new PrintWriter(purchasesData)) {
            writer.println(balance);
            for (TypeOfProduct product: goods) {
                writer.println(product.name);
                for (String purchase: product.list) {
                    writer.println(purchase);
                }
            }
            System.out.println("Purchases were saved!");
        }
        catch (FileNotFoundException e) {
            purchasesData.createNewFile();
            saveDataInFile();
        }
    }
    private static void loadDataFromFile (){
        File purchasesData = new File(filePath);
        try (Scanner fileScanner = new Scanner(purchasesData)){
            int currentIndex = -1;
            boolean skipOneLoop = false;
            balance = Double.parseDouble(fileScanner.nextLine());
            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();
                for (TypeOfProduct product : goods) {
                    if (currentLine.equals(product.name)) {
                        currentIndex++;
                        skipOneLoop = true;
                    }
                }
                if (skipOneLoop) {
                    skipOneLoop = false;
                    continue;
                }
                TypeOfProduct currentTp = goods[currentIndex];
                currentTp.add(currentLine);
            }
        }
        catch (FileNotFoundException e) {

        }
        finally {
            System.out.println("Purchases were loaded!");
        }
    }
    private static void showBalance () {
        System.out.println("Balance: $"+balance);
    }
    private static boolean isEmpty () {
        for (TypeOfProduct list: goods) {
            if (!list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static void showPurchaseList() throws Exception {
        if (isEmpty()) {
            System.out.println("The purchase list is empty");
        }
        else {
            System.out.println(
                    "Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            int switchCase = Integer.parseInt(scanner.nextLine());
            System.out.println();
            if (switchCase>0 && switchCase<5) {
                goods[switchCase-1].showListOfPurchases();
                showPurchaseList();
            }
            else if (switchCase==5) {
                double totalSum = 0;
                for (TypeOfProduct currentType: goods) {
                    totalSum += currentType.showListForTotal();
                }
                System.out.println("Total sum: $" + totalSum);
                System.out.println();
                showPurchaseList();
            }
            else if (switchCase==6) {
                return;
            }
            else {
                throw new Exception("Вы ввели не правильную команду.");
            }
        }
    }
    private static void addIncome() {
        System.out.println("Enter income:");
        balance+= Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!");
    }
}
