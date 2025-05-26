package dostawa;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SymulacjaTest {

    @Test
    public void testTworzenieSymulacji() {
        Mapa mapa = new Mapa(20, 15, new ArrayList<>(), new ArrayList<>());
        Pojazd pojazd = new PojazdMaly(1, new Pozycja(7, 5));
        List<Pojazd> pojazdy = new ArrayList<>();
        pojazdy.add(pojazd);

        Symulacja symulacja = new Symulacja(mapa, pojazdy);

        assertEquals(mapa, symulacja.getMapa());
        assertEquals(pojazdy, symulacja.getPojazdy());
        assertNotNull(symulacja.getZdarzenia());
        assertTrue(symulacja.getZdarzenia().isEmpty());
    }
}