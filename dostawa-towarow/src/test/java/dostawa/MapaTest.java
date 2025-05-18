package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MapaTest {

    @Test
    public void testTworzenieMapy() {
        Mapa mapa = new Mapa(30, 50, new ArrayList<>(), new ArrayList<>());

        assertEquals(30, mapa.getSzerokosc());
        assertEquals(50, mapa.getDlugosc());
        assertNotNull(mapa.getMagazyny());
        assertNotNull(mapa.getPunktyDostawy());
    }
}
