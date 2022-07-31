import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Friend {
    /**
     * Friends name
     */
    private String name;
    /**
     * date friend was first added
     */
    /* TODO initDate no se usa. Puedes seguir los Usages para ver qué funciones la usan y
        qué funciones usan estas funciones, hasta que tengas el árbol completo. Entonces
        decides si es algo a medio hacer o algo que se puede descartar por completo.
        Si decides borrarlo, elimina primero la variable, y luego que el compilador te dé
        todos los errores para ir borrando sucesivamente
     */
    private LocalDate initDate;
    /**
     * Next notification date
     */
    private LocalDate nextDate;

    // TODO Documentar incDays, no uses estilo Javadoc con cosas private, porque no salen (en principio)
    private int incDays;

    // TODO Borrar el Javadoc y volverlo a crear (porque ahora no muestra los cambios por incDays)
    /**
     * @param name creates instance af Friend with introduced name
     */
    public Friend(String name, int incDays) {
        setName(name);
        setIncDays(incDays);
        this.nextDate = LocalDate.now().plusDays(incDays);
    }

    // TODO Javadoc del constructor
    public Friend(String name, LocalDate nextDate, int incDays) {
        setIncDays(incDays);
        setName(name);
        this.nextDate = nextDate;
    }

    /* public Friend(String name, LocalDate nextDate, int incDays){
        setName(name);
        this.nextDate = nextDate;
        this.incDays = incDays


    }*/

    public String getName() {
        return name;
    }

    /*
    TODO setName() debería validar y sanitizar la entrada:
        quitar los espacios y verificar que contiene algo
     */
    public void setName(String name) {
        this.name = name;
    }

    // TODO Eliminar getInitdate(), no se usa en ningún lado
    public LocalDate getInitDate() {
        return initDate;
    }

    public LocalDate getNextDate() {
        return this.nextDate;
    }

    /**
     * @return true if notification is needed.
     */
    // TODO Eliminar checkDate() por estar en desuso. Cambiar Javadoc
    public boolean checkDate() {

        return (remainingDays() == 0);

    }

    /* TODO: En el Javadoc, aparte de comentar el @return conviene poner
        pequeña descripción al principio, ya que esto es lo que saldrá
        primero en el listado de la documentación generada
     */
    /**
     * @return true if nextDate needs to be updated to next week.
     */
    public boolean needUpdate() {

        return (remainingDays() <= -1);
    }

    /*
    TODO Aquí hay un problema de nomenclatura, esta función debería llamarse
        setNextDateAuto() (por ejemplo), y la siguiente setNextDate() a secas
        La razón es que la que hace la verdadera función de setter es la segunda,
        esta primera es en realidad el mecanismo automático de actualización
     */
    /**
     * Increases nextDate by 6 days. Check happens on -1 days, so in order to make it a full week we
     * only add 6 days.
     */
    public void setNextDate() {

        while (this.nextDate.compareTo(LocalDate.now()) < 0) {
            /*
            TODO Aquí se ha quedado un 7 olvidado (al pasar a incDays). Esto sirve para
                recordar por qué no se usan números "sueltos" dentro del código, si hubieras
                puesto private final int INC_DAYS = 7; arriba, al borrar esta constante todas
                las referencias te habrían salido como errores y no se te hubiera pasado esta.
             */
            this.nextDate = this.nextDate.plusDays(7);
        }
    }

    // TODO Este es el verdadero setNextDate(), habría que intercambiar nombres con anterior
    public void setCustomNextDate(LocalDate customNextDate) throws Exception {

        if (customNextDate.isBefore(LocalDate.now())) {
            throw new Exception("this date is in the past already, move on");
        } else {
            this.nextDate = customNextDate;
        }
    }

    /**
     * Converts the attributes to String.
     */
    @Override
    public String toString() {

        String s = getName() + "," + getNextDate().toString() + "," + getIncDays();

        return s;
    }

    private int getIncDays() {

        return incDays;
    }

    private void setIncDays(int incDays) {

        if (incDays <= 0){

            System.out.println("son of a fucking bitch introduce correct number " +
                    "piece of absolute dogshit");
            return;
        }

        this.incDays = incDays;

    }

    /**
     * @return returns same as previous method + remaining days.
     */
    public String showData() {

        String friendColor;
        String daysRemaining;

        // Past dates are negatives.
        if (this.remainingDays() < 0) {

            friendColor = Colors.ANSI_RED;
            daysRemaining = " || you're " + -remainingDays() + " days late!!";

            // Future dates are positive
        } else if (remainingDays() > 0) {

            friendColor = Colors.ANSI_GREEN;
            daysRemaining = " || days remaining " + remainingDays();

            // Today is case 0
        } else {     // In case I ony want to use 3 colors.

            friendColor = Colors.ANSI_YELLOW;
            daysRemaining = " || disappointment is today!!";

        }

        String s = friendColor + " --> " + getName() + daysRemaining +
                " || " + getNextDate().getDayOfWeek() + " <-- " + System.lineSeparator() + Colors.ANSI_RESET;

        return s;
    }

    /*
    TODO Falta una función showDataDetail() o así para presentar toda la info del usuario
     */

    /**
     * @return number of days remaining until nextDate.
     */
    public long remainingDays() {

        return DAYS.between(LocalDate.now(), nextDate);

    }

    // TODO Eliminar todo este comentario con el main()
/*
    public static void main(String args[]) {

        LocalDate x = LocalDate.now();
        System.out.println(x);

        x = x.plusDays(7);
        System.out.println(x);

        Friend y = new Friend("Diego");

        System.out.println(y.initDate);

    }
*/

}
