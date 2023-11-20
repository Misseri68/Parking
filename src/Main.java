import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int numCoches = 10;
        int numPlazas = 5;

        Parking parking = new Parking(numCoches, numPlazas);
        parking.inicializarPlazas();
        parking.inicializarCoches();
    }
}