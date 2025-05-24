package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ZdarzenieTest {

    @Test
    public void testTworzenieZdarzenia() {
        Zdarzenie zdarzenie = new Zdarzenie("awaria");
        assertEquals("awaria", zdarzenie.getRodzajZdarzenia());
    }

    @Test
    public void testRozneRodzajeZdarzen() {
        Zdarzenie zdarzenie1 = new Zdarzenie("awaria");
        Zdarzenie zdarzenie2 = new Zdarzenie("brak przejazdu");

        assertEquals("awaria", zdarzenie1.getRodzajZdarzenia());
        assertEquals("brak przejazdu", zdarzenie2.getRodzajZdarzenia());
        assertNotEquals(zdarzenie1.getRodzajZdarzenia(), zdarzenie2.getRodzajZdarzenia());
    }
}