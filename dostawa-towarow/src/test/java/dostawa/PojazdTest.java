package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PojazdTest {

    @Test
    public void testTworzeniePojazduMaly() {
        Pozycja pozycja = new Pozycja(4, 5);
        Pojazd pojazd = new PojazdMaly(7, pozycja);

        assertEquals(7, pojazd.getId());
        assertEquals(10, pojazd.getLadownoscMax());
        assertEquals(0, pojazd.getAktualnaIloscTowaru());
        assertEquals(pozycja, pojazd.getPozycja());
        assertEquals("Maly", pojazd.getTyp());
    }

    @Test
    public void testTworzeniePojazduSredni() {
        Pozycja pozycja = new Pozycja(3, 2);
        Pojazd pojazd = new PojazdSredni(8, pozycja);

        assertEquals(8, pojazd.getId());
        assertEquals(15, pojazd.getLadownoscMax());
        assertEquals(0, pojazd.getAktualnaIloscTowaru());
        assertEquals(pozycja, pojazd.getPozycja());
        assertEquals("Sredni", pojazd.getTyp());
    }

    @Test
    public void testTworzeniePojazduDuzy() {
        Pozycja pozycja = new Pozycja(1, 9);
        Pojazd pojazd = new PojazdDuzy(9, pozycja);

        assertEquals(9, pojazd.getId());
        assertEquals(25, pojazd.getLadownoscMax());
        assertEquals(0, pojazd.getAktualnaIloscTowaru());
        assertEquals(pozycja, pojazd.getPozycja());
        assertEquals("Duzy", pojazd.getTyp());
    }

    @Test
    public void testCzyMoznaZaladowac() {
        Pozycja pozycja = new Pozycja(4, 5);
        Pojazd pojazd = new PojazdDuzy(4, pozycja);

        assertTrue(pojazd.czyMoznaZaladowac(5));
        assertTrue(pojazd.czyMoznaZaladowac(25));
        assertFalse(pojazd.czyMoznaZaladowac(26));
        assertFalse(pojazd.czyMoznaZaladowac(50));
    }
}
