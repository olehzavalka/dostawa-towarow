package dostawa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowarTest {

    @Test
    public void testTworzenieTowaru() {
        Towar towar = new Towar(5.5, 10.0);
        assertEquals(5.5, towar.getCenaHurtowa());
        assertEquals(10.0, towar.getCenaDetaliczna());
    }

    @Test
    public void testRozneCeny() {
        Towar t1 = new Towar(4.0, 8.0);
        Towar t2 = new Towar(7.0, 12.0);
        assertNotEquals(t1.getCenaHurtowa(), t2.getCenaHurtowa());
        assertNotEquals(t1.getCenaDetaliczna(), t2.getCenaDetaliczna());
    }
}
