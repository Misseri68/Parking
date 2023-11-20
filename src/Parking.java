import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un parking que contiene coches y plazas de aparcamiento.
 * Los coches pueden intentar entrar en el parking si hay plazas disponibles.
 *
 * @author Ariadna López Abalo
 */
public class Parking {

    // ...


    int numCoches;
    int numPlazas;
    private List<Coche> coches = new ArrayList<>();
    private List<Plaza> plazas = new ArrayList<>();

    public Parking(int numCoches, int numPlazas) {
        this.numCoches = numCoches;
        this.numPlazas = numPlazas;
    }


    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }

    public List<Plaza> getPlazas() {
        return plazas;
    }

    public void setPlazas(List<Plaza> plazas) {
        this.plazas = plazas;
    }

    /**
     * Inicializa las plazas del parking.
     */

    public void inicializarPlazas(){
        for (int i = 0; i < numPlazas; i++) {
            plazas.add(new Plaza((i+1),false));
        }
    }

    /**
     * Inicializa los coches del parking y los inicia como hilos.
     */

    public void inicializarCoches(){
        for (int i = 0; i < numCoches; i++) {
            coches.add(new Coche((i+1), this));
            coches.get(i).start();

        }
    }


    /** Comprueba si hay plazas disponibles en el parking.
     * @return La plaza disponible, o null si no hay plazas libres.
     */


    //TODO: no sé muy bien si debería pasarle como argumento una lista de las plazas o no
    // (Puedo pasarlos por aquí con la lista local de la clase Parking, o la lista cogida de la clase Coche. Supongo que la mejor es la primera opción ya que
    // la lista pertenece a esta clase, no a Plaza (pero se puede hacer de ambas formas correctamente no?).
    public synchronized Plaza comprobarPlazas(){
        for (Plaza plaza:plazas) {
            if (!plaza.isOcupada()){
                return plaza;
            }
        }
        return null;
    }
}
