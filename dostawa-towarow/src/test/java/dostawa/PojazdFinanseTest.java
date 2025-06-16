package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PojazdFinanseTest {

    @Test
    public void testDodajKosztIZarobekPojazd() {
        Pojazd pojazd = new PojazdMaly(10, new Pozycja(0, 0));
        pojazd.dodajKoszt(40.0);
        pojazd.dodajZarobek(150.0);

        assertEquals(40.0, pojazd.getSumaKosztow());
        assertEquals(150.0, pojazd.getSumaZarobkow());
        assertEquals(110.0, pojazd.getSaldo());
    }
}
