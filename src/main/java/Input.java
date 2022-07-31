import java.time.LocalDate;
import java.util.Scanner;

public class Input {


    static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    static public String getString() {

        String line = scanner.nextLine();

        return line;

    }

    static public Integer getNumber() {

        int num = -1;
        if (scanner.hasNextInt()) {
            num = scanner.nextInt();

        } else {
            System.out.println("please enter a number");
        }
        scanner.nextLine();
        return num;
    }

    static public LocalDate getDate() {

        String newDate = scanner.nextLine();
        try {

            LocalDate newDate2 = LocalDate.parse(newDate);
            return newDate2;

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("incorrect format");
        }
        return null;
    }
}
