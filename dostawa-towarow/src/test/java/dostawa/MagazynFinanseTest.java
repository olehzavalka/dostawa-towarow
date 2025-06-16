package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagazynFinanseTest {

    @Test
    public void testDodajKosztIZarobek() {
        Magazyn magazyn = new Magazyn(1, new Pozycja(2, 3));
        magazyn.dodajKoszt(20.0);
        magazyn.dodajZarobek(100.0);

        assertEquals(20.0, magazyn.getSumaKosztow());
        assertEquals(100.0, magazyn.getSumaZarobkow());
        assertEquals(80.0, magazyn.getSaldo());
    }
}
