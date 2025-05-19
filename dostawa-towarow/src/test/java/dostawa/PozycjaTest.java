package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PozycjaTest {

    @Test
    public void testTworzeniePozycji() {
        Pozycja pozycja = new Pozycja(14, 2);
        assertEquals(14, pozycja.getX());
        assertEquals(2, pozycja.getY());
    }
}