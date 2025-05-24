package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowarTest {

    @Test
    public void testTworzenieTowaru() {
        Towar towar = new Towar("odziez");
        assertEquals("odziez", towar.getNazwa());
    }

    @Test
    public void testRozneNazwy() {
        Towar towar1 = new Towar("elektronika");
        Towar towar2 = new Towar("meble");
        assertNotEquals(towar1.getNazwa(), towar2.getNazwa());
    }
}