package budget;

public class Main {
    public static void main(String[] args) {
        try {
            FirstTask.mainMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }

    }
}
