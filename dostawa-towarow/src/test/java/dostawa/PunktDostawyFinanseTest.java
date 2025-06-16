package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PunktDostawyFinanseTest {

    @Test
    public void testDodajKosztIZarobek() {
        PunktDostawy punkt = new PunktDostawy(1, new Pozycja(1, 2), 20);
        punkt.dodajKoszt(10.0);
        punkt.dodajZarobek(25.0);

        assertEquals(10.0, punkt.getSumaKosztow());
        assertEquals(25.0, punkt.getSumaZarobkow());
        assertEquals(15.0, punkt.getSaldo());
    }

    @Test
    public void testDodajTowar() {
        PunktDostawy punkt = new PunktDostawy(2, new Pozycja(0, 0), 50);
        punkt.dodajTowar(10);
        assertEquals(10, punkt.getAktualnaIloscTowaru());
    }
}
