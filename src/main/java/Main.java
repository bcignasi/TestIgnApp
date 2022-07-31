import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {


    static LocalDate today = null;

    static App app;

    static {
        app = new App();
    }

    public static void printMenu() {

        String menu = """
                                
                                
                1.Show friend list
                2.Create friend
                3.Delete friend
                4.Show friend
                5.Edit friend
                0.Exit 
                                
                                
                """;


        System.out.println(menu);
    }


/*
        boolean mustRepeat = true;
        int option = 0;
        do {
            System.out.print("Elija una opción (0 para salir): ");
            if (scanner.hasNextInt()) {     // Comprueba si tenemos un int a la entrada
                option = scanner.nextInt(); // Caso que sí, lo leemos
                scanner.nextLine();         // Scanner debe leer el resto de la línea (el carácter \n) para descartarla
                mustRepeat = false;         // Ya podemos salir
            } else if (scanner.hasNextLine()) {     // Si no hay int, leemos la línea para descartarla
                scanner.nextLine();
                System.out.println("Error. Se espera un número");
            }
        } while (mustRepeat);

        return option;
*/

    public static void executeOption(int option) throws Exception {


        // TODO Quizás estaría bien comentar cada opción para no tener que ir a mirar al menú
        // TODO Otra opción sería encapsular el código de cada case en una función independiente
        switch (option) {
            case 1 -> {
                System.out.println();
                for (Friend elem : app.getFriendList()) {
                    System.out.println(elem.showData());
                }
                System.out.println();
            }
            case 2 -> {
                // TODO validate incDays en App o en Input
                System.out.println("enter name to add friend");
                String lovedFriend = Input.getString();
                System.out.println("enter days between appointments");
                int incDaysOfFriend = Input.getNumber();
                app.addFriend(lovedFriend, incDaysOfFriend);
                app.saveData();
            }
            case 3 -> {
                System.out.println("enter name to delete friend");
                String hatedFriend = Input.getString();
                app.removeFriend(hatedFriend);
                app.saveData();
            }
            case 4 -> {
                System.out.println("enter name of friend");
                String inquiredFriend = Input.getString();
                if (app.friendAlreadyExists(inquiredFriend) == -1) {

                    System.out.println("friend doesn't exist");
                    break;
                }
                Friend thatFriend = app.getFriend(inquiredFriend);
                System.out.println("\n" + thatFriend.showData() + "\n");
            }
            // TODO En este caso modificas cosas y no haces saveData()
            case 5 -> {
                System.out.println("enter name of friend");
                String selectedFriend = Input.getString();
                try {
                    Friend theFriend = app.getFriend(selectedFriend);
                    System.out.println("\n" + theFriend.toString() + "\n");
                    System.out.println(" 1 = edit name \n 2 = edit date");

                    int opt = Input.getNumber();

                    switch (opt) {
                        case 1 -> {
                            System.out.println("enter new name");
                            String newName = Input.getString();
                            if (app.friendAlreadyExists(newName) > -1) {

                                System.out.println("name already in use");
                                break;
                            }
                            theFriend.setName(newName);
                        }
                        case 2 -> {
                            System.out.println("enter new date, format yyyy-mm-dd");
                            LocalDate newDate2 = Input.getDate();
                            if (newDate2 != null) {
                                app.editNextDateManual(theFriend.getName(), newDate2);
                            }
                        }
                        default -> System.out.println("you don't wanna collaborate, okay");
                    }

                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        }
    }

    // TODO Supongo que esta función debería formar parte de las opciones del switch, ahora mismo no se usa
    private static void updateTime() {

        if (today == null || today.compareTo(LocalDate.now()) < 0) {
            today = LocalDate.now();
            app.updateFriends();
        }
    }


    public static void main(String[] args) throws Exception {


        /*app.addFriend("diego");
        app.addFriend("sediego");
        app.addFriend("sedieg");

        app.saveData();*/

        // TODO Lo del fichero
        File f = new File("friendList.txt");
        if (f.isFile()) {

            app.loadData();
        }
        app.saveData();

        while (true) {

            // updateTime();
            printMenu();

            int option = Input.getNumber();

            if (option == 0) {
                System.out.println("get fucked");
                break;
            }
            executeOption(option);
            /*
            TODO Este saveData() es redundante dentro del while porque cada opción que modifica los datos
                ejecuta otro saveData() allí. En cualquier, caso lo pondría fuera por asegurar al salir del programa
             */
            app.saveData();
        }


    }

}
