import org.junit.rules.ErrorCollector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

//Cambiar la plaza y que siga funcionando bien

    @org.junit.Test
    public void comprobarPlazas(){
        int numCoches = 10;
        int numPlazas = 5;

        Parking parking = new Parking(numCoches, numPlazas);
        parking.inicializarPlazas();


        assertEquals( 1, parking.comprobarPlazas().getNumPlaza());

        parking.getPlazas().get(0).setOcupada(true);

        assertEquals(2,parking.comprobarPlazas().getNumPlaza() );
    }

    }
