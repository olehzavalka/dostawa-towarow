package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ZamowienieTest {

    @Test
    public void testTworzenieZamowienia() {
        PunktDostawy punkt = new PunktDostawy(2, new Pozycja(1, 2), 15);
        Zamowienie zamowienie = new Zamowienie(punkt, 5);

        assertEquals(punkt, zamowienie.getPunktDostawy());
        assertEquals(5, zamowienie.getIlosc());
    }

    @Test
    public void testRozneZamowienia() {
        PunktDostawy p1 = new PunktDostawy(3, new Pozycja(4, 7), 30);
        PunktDostawy p2 = new PunktDostawy(5, new Pozycja(5, 3), 40);
        Zamowienie z1 = new Zamowienie(p1, 12);
        Zamowienie z2 = new Zamowienie(p2, 8);

        assertNotEquals(z1.getPunktDostawy(), z2.getPunktDostawy());
        assertNotEquals(z1.getIlosc(), z2.getIlosc());
    }
}
