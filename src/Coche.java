public class Coche extends Thread{
    private int numCoche;
    private Plaza plaza;
    private Parking parking;
    private boolean haEntrado = false;

    //Una plaza con valor -1 significa que al coche todavía no se le ha asignado una plaza del Parking.
    public Coche (int numCoche, Parking parking){
        super();
        this.numCoche = numCoche;
        this.plaza = new Plaza(-1);
        this.parking = parking;
    }

    public synchronized void entradaParking() throws NoPlazasDisponiblesException {
        //Si comprueba y no hay plaza lanzará NoPLazasDisponiblesException
        if (parking.comprobarPlazas(parking.getPlazas())== null){
            throw new NoPlazasDisponiblesException("El coche " + numCoche + " ha intentado entrar. Aún no hay plazas libres.");
        }
        else{
            //Meto la plaza disponible en una variable llamada plazaDisponible
            Plaza plazaDisponible = parking.comprobarPlazas(parking.getPlazas());
            //TODO: asegurarse de que apuntan a la misma referencia y no a una copia.
            //cambio el estado de esa plaza a ocupada (true).
            plazaDisponible.setOcupada(true);
            //Asigno esa Plaza (ahora ocupada) a el coche, hasta que salga.
            this.plaza = plazaDisponible;
        }
    }

    public synchronized void salidaParking(){

    }

    public void run(){
        while(true){
            //Si no ha entrado aún no tendrá un tiempo de espera.
            if(!haEntrado){
                try {
                    entradaParking();
                } catch (NoPlazasDisponiblesException e) {}
            }
            //Si ya ha entrado:
            else {
                //Esperará antes de intentar hacer cualquier operación.
                try {
                    Thread.sleep(numeroRandom()*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Hilo interrumpido durante el tiempo de espera", e);
                }
                //Una vez esperado un tiempo aleatorio, y si está fuera del parking:
                if (plaza.getNumPlaza() == -1){
                    try {
                        entradaParking();
                    } catch (NoPlazasDisponiblesException e) {}
                }
                //Una vez esperado, y si está dentro del parking:
                else if (plaza.getNumPlaza() >0 && plaza.getNumPlaza()<=5){
                    salidaParking();
                }
                else{
                    System.out.println("Error de plaza asociada al coche");
                }
            }
        }
    }

    public int numeroRandom(){
        return (int) ( Math.random() *5)+1;
    }
    
}
