package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PunktDostawyTest {

    @Test
    public void testTworzeniePunktuDostawy() {
        Pozycja pozycja = new Pozycja(3, 15);
        PunktDostawy punkt = new PunktDostawy(14, pozycja, 30);

        assertEquals(14, punkt.getId());
        assertEquals(pozycja, punkt.getPozycja());
        assertEquals(30, punkt.getPojemnoscMax());
        assertEquals(0, punkt.getAktualnaIloscTowaru());
    }

    @Test
    public void testCzyPusty() {
        PunktDostawy punkt = new PunktDostawy(2, new Pozycja(0, 0), 20);
        assertTrue(punkt.czyPusty());
    }

    @Test
    public void testCzyMoznaZaladowac() {
        PunktDostawy punkt = new PunktDostawy(5, new Pozycja(3, 4), 4);

        assertTrue(punkt.czyMoznaZaladowac(2));
        assertTrue(punkt.czyMoznaZaladowac(4));
        assertFalse(punkt.czyMoznaZaladowac(5));
        assertFalse(punkt.czyMoznaZaladowac(17));
    }
}