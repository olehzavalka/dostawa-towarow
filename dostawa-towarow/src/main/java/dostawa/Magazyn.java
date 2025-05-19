package dostawa;

public class Magazyn {
    private int id;
    private Pozycja pozycja;

    public Magazyn(int id, Pozycja pozycja) {
        this.id = id;
        this.pozycja = pozycja;
    }

    public int getId() {
        return id;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }
}