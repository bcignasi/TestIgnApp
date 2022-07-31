import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    App app;
    List<Friend> lista;
    String mockingList = """
                pepe,2022-08-07,7
                maria,2022-08-01,1
                ignasi,2022-07-30,4
                guille,2022-07-15,7
                juan,2022-07-31,7        
                """;

    String friendListTestFile = "friendList.txt.bak";
    String friendListFile = "friendList.txt";

    void setMockingFile() throws IOException {
        if (Files.exists(Path.of(friendListFile))) {
            Files.copy(Path.of(friendListFile), Path.of(friendListTestFile), StandardCopyOption.REPLACE_EXISTING);
        }
        BufferedWriter br = new BufferedWriter(new FileWriter(friendListFile));
        br.write(mockingList);
        br.close();
    }

    void unsetMockingFile() throws IOException {
        if (Files.exists(Path.of(friendListTestFile))) {
            Files.copy(Path.of(friendListTestFile), Path.of(friendListFile), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(Path.of(friendListTestFile));
        }
    }

    String readMockingFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(friendListFile));
        String friendListString = br.lines().collect(Collectors.joining());;
        br.close();
        return friendListString;
    }

    @BeforeEach
    void setUp() throws Exception {
        setMockingFile();
        app = new App();
        app.loadData();
        lista = app.getFriendList();
    }

    @AfterEach
    void tearDown() throws IOException {
        unsetMockingFile();
    }

    @Test
    void comprobar_se_ha_cargado_el_fichero_de_datos() {
        assertEquals(5, lista.size());
    }

    // TODO descomentar las líneas de getIncDays() tras pruebas
    @Test
    void comprobar_primero_y_ultimo() {
        Friend friend = lista.get(0);
        assertEquals("pepe", friend.getName());
        assertEquals("2022-08-07", friend.getNextDate().toString());
        //assertEquals(7, friend.getIncDays());
        friend = lista.get(lista.size()-1);
        assertEquals("juan", friend.getName());
        assertEquals("2022-07-31", friend.getNextDate().toString());
        //assertEquals(7, friend.getIncDays());
    }

    // TODO El método removeFriend() no da información de si el borrado funcionó o no
    @Test
    void comprobar_borrado_amigo_existente_e_inexistente() {
        app.removeFriend("ignasi");
        List<Friend> result = app.getFriendList();
        assertEquals(4, result.size());

        app.removeFriend("scarlett");
        result = app.getFriendList();
        assertEquals(4, result.size());
    }

    /*
    TODO Es incómodo que una función que sugiere devolver true/false
        devuelva en realidad un integer. Sería mejor que esta función
        devolviera true/false y existiera otra getFriedIndex() que
        devolviera el índice propiamente (y que serviría para construir esta)
      */

    @Test
    void comprobar_control_existencia_amigos() {
        int index = app.friendAlreadyExists("ignasi");
        assertTrue(index == 2);
        index = app.friendAlreadyExists("scarlett");
        assertTrue(index == -1);
    }

    // TODO Lo hago indirectamente, comprobando el toString() de List<Friend>
    @Test
    void comprobar_getList() {
        String expected = "[pepe,2022-08-07,7, maria,2022-08-01,1, ignasi,2022-07-30,4, guille,2022-07-15,7, juan,2022-07-31,7]";
        assertEquals(expected, app.getFriendList().toString());
    }

    @Test
    void comprobar_getFriend() throws Exception {
        Friend friend = app.getFriend("ignasi");
        assertEquals("ignasi,2022-07-30,4", friend.toString());
    }

    // TODO El toString de la clase es el contenido del fichero friendList.txt
    @Test
    void comprobar_ToString() {
        assertEquals(mockingList, app.toString().replace("\r", ""));
    }

    // TODO Desactivado porque, en realidad, esto queda testeado en el setup()
    /*
    @Test
    void loadData() {
    }
    */

    /*
    TODO saveData() se hace aparte, quizás sería mejor hacerlo en la propia addFriend()
        y de esta forma se eliminan todas las referencias en Main (más encapsulación)
     */
    @Test
    void comprobar_save_ingresando_y_borrando_friend() throws Exception {
        app.addFriend("scarlett", 1);
        app.editNextDateManual("scarlett", LocalDate.parse("2022-08-01"));
        app.saveData();

        String friendList = readMockingFile();
        assertTrue(friendList.contains("scarlett,2022-08-01,1"));

        app.removeFriend("scarlett");
        app.saveData();

        friendList = readMockingFile();
        assertFalse(friendList.contains("scarlett"));
    }

    /*
    Ejecuta la actualización y comprueba que todas las fechas son futuras
     */
    @Test
    void comprobar_que_updateFriends_convierte_todas_las_fechas_en_futuras() {
        LocalDate today = LocalDate.now();
        app.updateFriends();
        for(Friend friend : app.getFriendList()) {
            LocalDate ld = friend.getNextDate();
            assertFalse(ld.isBefore(today));
        }
    }

    // TODO Estos dos últimos tests no pueden funcionar por el problema con LocalDate.now(), que cambia a diario
/*
    @Test
    void comprobar_edicion_manual_nextDate() throws Exception {
        // Fecha futura (respecto a la actual)
        app.editNextDateManual("ignasi", LocalDate.parse("2022-08-15"));
        Friend friend = app.getFriend("ignasi");
        assertEquals("2022-08-15", friend.getNextDate().toString());

        // Fecha actual
        app.editNextDateManual("ignasi", LocalDate.parse("2022-07-30"));
        friend = app.getFriend("ignasi");
        assertEquals("2022-07-30", friend.getNextDate().toString());

        // Fecha pasada
        app.editNextDateManual("ignasi", LocalDate.parse("2022-07-20"));
        friend = app.getFriend("ignasi");
        assertEquals("2022-07-20", friend.getNextDate().toString());
    }
*/

/*
    @Test
    void comprobar_errores() {
        assertThrows(Exception.class,
                () -> app.editNextDateManual("ignasi", LocalDate.parse("2022-07-20")));
    }
*/
}
