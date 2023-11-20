
/**
 * Clase que representa un coche que puede entrar y salir de un parking.
 * Extiende la clase Thread para poder ejecutarse como un hilo.
 * Contiene los métodos de entrada y salida de los coches del parking.
 *
 * @author Ariadna López Abalo
 */

public class Coche extends Thread{
    private int numCoche;
    private Plaza plazaCoche;
    private Parking parking;
    private boolean haEntrado = false;

    public static final String ANSI_RESET= "\u001B[0m";
    public static final String ANSI_VERDE = "\u001B[32m";
    public static final String ANSI_AMARILLO = "\u001B[33m";

    public static final String ANSI_ROJO = "\u001B[31m";

    public static final String ANSI_AZUL = "\u001B[34m";


    /**
     * Constructor de la clase Coche.
     *
     * @param numCoche Número identificador del coche.
     * @param parking  Instancia de la clase Parking al que pertenece el coche.
     */

    public Coche (int numCoche, Parking parking){
        super();
        this.numCoche = numCoche;
        //Una plaza con valor -1 significa que al coche todavía no se le ha asignado una plaza del Parking.
        this.plazaCoche = new Plaza(-1);
        this.parking = parking;
    }
    /**
     * Establece la plaza asociada al coche.
     *
     * @param plazaCoche Plaza que se asignará al coche.
     */
    public void setPlazaCoche(Plaza plazaCoche) {
        this.plazaCoche = plazaCoche;
    }

    /**
     * Realiza la entrada al parking, asignando al coche una plaza devuelta por el método comprobarPlazas() de Parking.
     *      -Si no hay plazas disponibles devolverá Null y lanzará la excepción personalizada.
     *      -Si hay plazas disponibles:
     *          Guarda la Plaza devuelta (la primera plaza que encuentre) y cambiará su estado a ocupada.
     *          Asignará esa Plaza a la plaza asociada al coche
     *          Cambiará el estado del coche permanentemente a "haEntrado", que se usará en run().
     * Imprimirá el numero del coche y el de la plaza con colores ANSI.
     * Utiliza synchronized para que no haya choques entre los usos de los hilos y lo que comparten.
     *
     * @throws NoPlazasDisponiblesException Si no hay plazas libres en el parking.
     */
    public synchronized void entradaParking() throws NoPlazasDisponiblesException {
        System.out.println(ANSI_AMARILLO + "El coche " +  numCoche + " se dispone a entrar al parking..." + ANSI_RESET);
        //Si comprueba y no hay plaza lanzará NoPLazasDisponiblesException
        if (parking.comprobarPlazas() == null){
            throw new NoPlazasDisponiblesException(ANSI_ROJO + "El coche " + numCoche + " ha intentado entrar. Aún no hay plazas libres." + ANSI_RESET);
        }
        else{
            //Meto la plaza disponible en la var. llamada plazaDisponible
            Plaza plazaDisponible = parking.comprobarPlazas();
            //TODO: asegurarse de que apuntan a la misma referencia y no a una copia.
            //cambio el estado de esa plaza a ocupada (true).
            plazaDisponible.setOcupada(true);
            //Asigno esa Plaza (ahora ocupada) a el coche, hasta que salga.
            this.setPlazaCoche(plazaDisponible);
            this.plazaCoche = plazaDisponible;
            this.haEntrado = true;
            System.out.println(ANSI_VERDE + "El coche " + numCoche + " ha entrado en la plaza " + plazaCoche.getNumPlaza() + " correctamente. "+ ANSI_RESET + "\n");
        }
    }

    /**
     * Método synchronized que simula la salida de un coche del parking.
     * Imprime que el coche sale de la plaza con su numero de coche y el numero de la plaza
     * Establece el estado de la plaza asociada al coche como falso (la libera)
     * Establece la plaza asociada al coche como una nueva Plaza inicializada con un "-1", que indica que no está aparcado.
     */
    public synchronized void salidaParking(){
        System.out.println( ANSI_VERDE + "El coche " + numCoche + " sale de la plaza " +  plazaCoche.getNumPlaza() + ANSI_RESET);
        //TODO Lo selecciono así porque no sé si cambiar la palza desde plazaAsignada (atributo de esta clase) antes de borrarla también cambia la plaza que quiero en la clase Parking.
        parking.getPlazas().get((plazaCoche.getNumPlaza() -1)).setOcupada(false);
        this.setPlazaCoche(new Plaza(-1));

    }

    /**
     * El método run() representa la ejecución del hilo Coche. Inicializa el coche y espera a que todos los coches se hayan inicializado antes de comenzar el bucle principal.
     * En el bucle infinito, el coche realiza las siguientes acciones:
     * - Si el coche aún no ha entrado al parking, no tiene un tiempo de espera previo y ejecuta el método entradaParking(),
     *      hasta haber entrado por primera vez cambiando su booleano, y tendrá tiempo de espera a partir de entonces.
     * - Si el coche ya ha entrado (bool haEntrado true), espera un tiempo aleatorio y realiza una de las siguientes acciones:
     *   - Si el coche está fuera del parking (num de la plaza asociada al coche == -1), intenta entrar.
     *   - Si el coche está dentro del parking, sale del parking.
     * Si se produce una interrupción durante la espera, se lanza una RuntimeException.
     * Si se detecta una excepción al intentar entrar al parking, se imprime un mensaje de error.
     * Si hay un error con la plaza asociada al coche, se imprime un mensaje de error.
     */
    public void run(){
        System.out.println(ANSI_AZUL + "Coche " + numCoche + " inicializado." + ANSI_RESET);
        //Quiero que esperen a que se hayan inicializado todos antes de empezar el bucle.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while(true){
            //Si no ha entrado aún no tendrá un tiempo de espera.
            if(!haEntrado){
                try {
                    entradaParking();
                } catch (NoPlazasDisponiblesException e) {
                    //En caso de que no haya plazas disponibles.
                    System.out.println(e.getMessage());
                }
                try {
                    Thread.sleep((numeroRandom())*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //Si ya ha entrado:
            else {
                //Esperará antes de intentar hacer cualquier operación.
                try {
                    Thread.sleep((numeroRandom())*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Hilo interrumpido durante el tiempo de espera", e);
                }
                //Una vez esperado un tiempo aleatorio, y si está fuera del parking (si el numero de la plaza es -1, es porque está fuera del parking):
                if (plazaCoche.getNumPlaza() == -1){
                    try {
                        entradaParking();
                    } catch (NoPlazasDisponiblesException e) {
                        //En caso de que no haya plazas disponibles.
                        System.out.println(e.getMessage());
                    }
                }
                //Una vez esperado, y si está dentro del parking:
                else if (plazaCoche.getNumPlaza() >0 && plazaCoche.getNumPlaza()<= parking.getPlazas().size()){
                    salidaParking();
                }
                else{
                    //No debería llegar a este punto.
                    System.out.println("Error de plaza asociada al coche");
                }
            }
        }
    }

    /**
     * Genera un número aleatorio que se usará como los segundos de espera. El tiempo de espera mínimo es de 5 segundos,
     * y el tiempo de espera máximo es de 10 segundos.
     *
     * @return Un entero que representa los segundos de espera aleatorios.
     */

    public int numeroRandom(){
        return  ( (int) (Math.random() *5) +5)+1;
    }
    
}
