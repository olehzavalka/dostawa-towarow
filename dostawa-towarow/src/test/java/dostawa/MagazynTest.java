package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagazynTest {

    @Test
    public void testTworzenieMagazynow() {
        Pozycja pozycja1 = new Pozycja(14, 4);
        Magazyn magazyn1 = new Magazyn(1, pozycja1);
        assertEquals(1, magazyn1.getId());
        assertEquals(pozycja1, magazyn1.getPozycja());

        Pozycja pozycja2 = new Pozycja(10, 2);
        Magazyn magazyn2 = new Magazyn(2, pozycja2);
        assertEquals(2, magazyn2.getId());
        assertEquals(pozycja2, magazyn2.getPozycja());

        Pozycja pozycja3 = new Pozycja(347, 399);
        Magazyn magazyn3 = new Magazyn(145, pozycja3);
        assertEquals(145, magazyn3.getId());
        assertEquals(pozycja3, magazyn3.getPozycja());
    }
}