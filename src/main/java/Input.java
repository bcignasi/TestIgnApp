import java.time.LocalDate;
import java.util.Scanner;

// TODO Aquí se echan en falta los javadocs
public class Input {


    static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    /*
    TODO Te sale un warning porque podrías poner perfectamente return scanner.nextLine()
        Como te comenté, de cara a la depuración las variables ayudan, pero si ya está el
        código estabilizado se puede hacer el cambio y queda un código mas compacto y eficiente
     */
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
            // TODO El mismo warning de variable redundante
            LocalDate newDate2 = LocalDate.parse(newDate);
            return newDate2;

        } catch (Exception e) {
            /*
            TODO El aviso de Throwable te lo explican aquí:
             https://stackoverflow.com/questions/61927493/throwable-argument-ex-to-system-out-println-call
             */
            System.out.println(e);
            System.out.println("incorrect format");
        }
        return null;
    }
}
