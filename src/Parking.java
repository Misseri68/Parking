import java.util.ArrayList;
import java.util.List;

public class Parking {

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


    public void inicializarCoches(){
        for (int i = 0; i < numCoches; i++) {
            coches.add(new Coche((i+1), this));
        }
    }

    public void inicializarPlazas(){
        for (int i = 0; i < numPlazas; i++) {
            plazas.add(new Plaza((i+1),false));
        }
    }

    //Comprobará si hay plazas disponibles. Devolverá la plaza en caso de que haya, y si no devolverá null
    //TODO: no sé muy bien si debería pasarle como argumento una lista de las plazas o no.
    public Plaza comprobarPlazas(List<Plaza> plazas){
        for (Plaza plaza:plazas) {
            if (!plaza.isOcupada()){
                return plaza;
            }
        }
        return null;
    }

    //Buscará la plaza pasada por argumento en la lista de plazas y cambiará su estado a "ocupada". Devolverá verdadero si se ha ocupado y falso si no se ha hecho bien.
    //TODO: cuidado con que la plaza pasada tenga la misma referencia del objeto, y que no sólo coincidan sus atributos.
    public boolean ocuparPlaza(Plaza plaza){
        for (Plaza p : plazas) {
            if
        }
        return false;
    }

}
