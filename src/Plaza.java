/**
 * Representa una plaza de estacionamiento en un parking.
 *
 * @author Ariadna López Abalo
 */
public class Plaza {
    private boolean ocupada;
    private int numPlaza;

    /**
     * Constructor de la clase Plaza.
     *
     * @param numPlaza Número identificador de la plaza.
     * @param ocupada  Estado de ocupación de la plaza (true si está ocupada, false si está libre).
     */
    public Plaza(int numPlaza, boolean ocupada) {
        this.ocupada = ocupada;
        this.numPlaza = numPlaza;
    }

    /**
     * Constructor de la clase Plaza para plazas que se asocian a los coches para saber que no están dentro del Parking.
     *
     * @param numPlaza Número identificador de la plaza.
     */
    public Plaza (int numPlaza){
        this.numPlaza = -1;
    }

    /**
     * Comprueba si la plaza está ocupada.
     *
     * @return true si la plaza está ocupada, false si está libre.
     */
    public boolean isOcupada() {
        return ocupada;
    }

    /**
     * Establece el estado de ocupación de la plaza.
     *
     * @param ocupada true si se quiere marcar como ocupada, false si se quiere marcar como libre.
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Obtiene el número de la plaza.
     *
     * @return Número de la plaza.
     */
    public int getNumPlaza() {
        return numPlaza;
    }
}





