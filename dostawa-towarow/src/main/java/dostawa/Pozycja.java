package dostawa;

import java.util.Objects;

public class Pozycja {
    private int x;
    private int y;

    public Pozycja(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pozycja pozycja)) return false;
        return x == pozycja.x && y == pozycja.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
