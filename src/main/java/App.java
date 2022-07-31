

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    /**
     * num of maxim friends possible
     */
    // TODO El límite de 10 amigos es un poco antipático, aparte de pequeño
    private final int MAX_FRIENDS = 10;
    /**
     * array where all friends are added.
     */
    // TODO Fíjate que aquí as puesto 10 a pelo, en vez de MAX_FRIENDS
    private final Friend[] friends = new Friend[10];

    /*
    TODO Una mejora importante que tenemos que hacer es eliminar la limitación de tamaño
        de la matriz, usando mejor una List<Friend>, que puede crecer indefinidamente
        Al hacerlo, toda esta clase tendría que refactorizarse (por cierto, ya usas List<Friend>
        más abajo, en getListFriend(), por si quieres echarle un ojo)
     */

    // TODO Javadoc desactualizado por culpa de incDays
    /**
     * Adds new friend to array Friend[]
     *
     * @param name friends name
     * @throws Exception a
     */
    public void addFriend(String name, int incDays) throws Exception {

        if (this.friendAlreadyExists(name) >= 0) {

            throw new Exception("friends name already exists");
        }

        for (int i = 0; i < MAX_FRIENDS; i++) {

            if (friends[i] == null) {

                friends[i] = new Friend(name, incDays);

                return;
            }
        }

        /*
        TODO Cambiar Exception por una excepción personalizada (AppException, por ejemplo),
            para que el control de errores sea un poco más fino.
        *  */

        throw new Exception("Not room for more friends :/");

    }

    private void friendLoader(String name, LocalDate nextDate, int incDays) throws Exception {

        if (this.friendAlreadyExists(name) >= 0) {

            throw new Exception("friends name already exists");
        }

        for (int i = 0; i < MAX_FRIENDS; i++) {

            if (friends[i] == null) {

                friends[i] = new Friend(name, nextDate, incDays);

                return;
            }
        }


        throw new Exception("Not room for more friends :/");

    }

    /**
     * Remove friend from array Friend[]
     *
     * @param name friends name
     */
    public void removeFriend(String name) {

        int i = this.friendAlreadyExists(name);

        if (i >= 0) {

            friends[i] = null;

        }
    }

    //  TODO Este es un Javadoc bien hecho
    /**
     * checks if friends name is in use already
     *
     * @param name friends name
     * @return no. of entry of the name or -1 if name doesn't exist in the array.
     */
    public int friendAlreadyExists(String name) {

        for (int i = 0; i < MAX_FRIENDS; i++) {

            if (friends[i] == null) {

                continue;
            }

            if (name.equals(friends[i].getName())) {

                return i;
            }
        }

        return -1;
    }

    /**
     * @return all not null entries from the array Friend[]
     */


    public List<Friend> getFriendList() {

        List<Friend> list = new ArrayList<>();

        for (Friend elem : friends) {

            if (elem != null) {

                list.add(elem);

            }
        }

        return list;
    }

    /*
    TODO Echo en falta funciones para devolver la lista ordenada por nombre o fecha,
        o filtrada por fechas pasadas, futuras o presentes. No son muy difíciles de
        hacer, ya existen métodos sort() y filter() para ello.
     */


    /**
     * @param name friends name
     * @return one specific friend from the array
     */
    public Friend getFriend(String name) throws Exception {

        int i = this.friendAlreadyExists(name);

        if (i >= 0) {

            return friends[i];

        } else {

            throw new Exception("this friend doesn't exist");
        }

    }

    /**
     * @return Array of friends converted into a String of data.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Friend elem : friends) {
            if (elem != null) {
                sb.append(elem.toString()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }


    public void loadData() throws Exception {

        for (int i = 0; i < MAX_FRIENDS; i++) {

            friends[i] = null;
        }

        /*T
        TODO El nombre del fichero friendList.txt no debería estar puesto a pelo aquí,
            como mínimo tendría que ser una constante al inicio de la clase o mejor incluso
            ser un atributo de una clase independiente que gestione estas dos funciones
            (un atributo que le pasarías en el constructor)
        */

        // TODO Esto se puede abreviar como BufferedReader br = new BufferedReader( new FileReader("friendList. txt"));
        File file = new File("friendList.txt");

        FileReader fr = new FileReader(file);

        BufferedReader br = new BufferedReader(fr);

        // TODO Esta declaración podría estar dentro del while, dado que no se usa después
        String line;

        while ((line = br.readLine()) != null) {

            String[] fields = line.split(",");
            this.friendLoader(fields[0], LocalDate.parse(fields[1]), Integer.parseInt(fields[2]));
        }
        br.close();
    }

    // TODO La misma cuestión de tener el nombre del fichero hardwired.
    public void saveData() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("friendList.txt"));
        writer.write(this.toString());
        writer.close();

    }

    /*
    TODO Esta función hace la actualización automática de las nextDate, existe en Main
        pero no se usa en el bucle principal (así que es como si estuviera en desuso)
    */
    public void updateFriends() {

        for (Friend elem : friends) {

            if (elem != null && elem.needUpdate()) {
                elem.setNextDate();
            }
        }
    }

    // TODO Si le cambias el nombre a setCustomNextDate(), aquí tendrás un error
    public void editNextDateManual(String name, LocalDate nextDate) throws Exception {

        Friend elem = getFriend(name);

        elem.setCustomNextDate(nextDate);

    }
}
