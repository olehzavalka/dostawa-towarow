package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZamowienieTest {

    @Test
    void testTworzenieZamowienia() {
        PunktDostawy punkt = new PunktDostawy(1, new Pozycja(2, 3), 100);
        Zamowienie zam = new Zamowienie(punkt, 15);

        assertEquals(punkt, zam.getPunktDostawy());
        assertEquals(15, zam.getIlosc());
    }
}