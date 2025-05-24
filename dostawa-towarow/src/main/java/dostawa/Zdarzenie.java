package dostawa;

public class Zdarzenie {
    private final String rodzajZdarzenia;

    public Zdarzenie(String rodzajZdarzenia) {
        this.rodzajZdarzenia = rodzajZdarzenia;
    }

    // Gettery

    public String getRodzajZdarzenia() {
        return rodzajZdarzenia;
    }
}