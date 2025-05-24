package dostawa;

public class Towar {
    private final String typ;

    public Towar(String nazwa) {
        this.typ = nazwa;
    }

    public String getNazwa() {
        return typ;
    }
}