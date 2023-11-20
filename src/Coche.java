
/**
 * Clase que representa un coche que puede entrar y salir de un parking.
 * Extiende la clase Thread para poder ejecutarse como un hilo.
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
     * Método synchronized que simula la entrada de un coche al parking.
     *
     * @throws NoPlazasDisponiblesException Excepción lanzada si no hay plazas disponibles.
     */
    public synchronized void entradaParking() throws NoPlazasDisponiblesException {
        System.out.println(ANSI_AMARILLO + "El coche " +  numCoche + " se dispone a entrar al parking..." + ANSI_RESET);
        //Si comprueba y no hay plaza lanzará NoPLazasDisponiblesException
        if (parking.comprobarPlazas() == null){
            throw new NoPlazasDisponiblesException(ANSI_ROJO + "El coche " + numCoche + " ha intentado entrar. Aún no hay plazas libres." + ANSI_RESET);
        }
        else{
            //Meto la plaza disponible en una variable llamada plazaDisponible
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
     */
    public synchronized void salidaParking(){
        //Lo selecciono así porque no sé si cambiar la palza desde aquí antes de borrarla, también cambia la plaza que quiero en Parking.
        System.out.println( ANSI_AMARILLO + "El coche " + numCoche + " va a salir de la plaza " +  plazaCoche.getNumPlaza() + ANSI_RESET);

        parking.getPlazas().get((plazaCoche.getNumPlaza() -1)).setOcupada(false);
        this.setPlazaCoche(new Plaza(-1));
        System.out.println("   El coche " + numCoche + " salió del Parking correctamente. \n");

    }

    public void run(){
        System.out.println(ANSI_AZUL + "Coche " + numCoche + " inicializado." + ANSI_RESET);
        //Quiero que esperen a que se hayan inicializado todos antes de empezar el bucle.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while(true){
            //Si no ha entrado aún no tendrá un tiempo de espera.
            if(!haEntrado){
                try {
                    entradaParking();
                } catch (NoPlazasDisponiblesException e) {
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
                        System.out.println(e.getMessage());
                    }
                }
                //Una vez esperado, y si está dentro del parking:
                else if (plazaCoche.getNumPlaza() >0 && plazaCoche.getNumPlaza()<=5){
                    salidaParking();
                }
                else{
                    System.out.println("Error de plaza asociada al coche");
                }
            }
        }
    }

    public int numeroRandom(){
        //minimos segundos serán 5, máximo 10.
        return  ( (int) (Math.random() *5) +5)+1;
    }
    
}
