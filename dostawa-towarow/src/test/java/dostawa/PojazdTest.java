package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PojazdTest {

    @Test
    public void testTworzeniePojazdu() {
        Pozycja pozycja = new Pozycja(4, 5);
        Pojazd pojazd = new Pojazd(7, 9, pozycja);

        assertEquals(7, pojazd.getId());
        assertEquals(9, pojazd.getLadownoscMax());
        assertEquals(0, pojazd.getAktualnaIloscTowaru());
        assertEquals(pozycja, pojazd.getPozycja());
    }

    @Test
    public void testCzyMoznaZaladowac() {
        Pozycja pozycja = new Pozycja(4, 5);
        Pojazd pojazd = new Pojazd(4, 17, pozycja);

        assertTrue(pojazd.czyMoznaZaladowac(5));
        assertTrue(pojazd.czyMoznaZaladowac(17));
        assertFalse(pojazd.czyMoznaZaladowac(18));
        assertFalse(pojazd.czyMoznaZaladowac(34));
    }
}