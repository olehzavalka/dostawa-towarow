package dostawa;

public class Towar {
    private final double cenaHurtowa;
    private final double cenaDetaliczna;

    public Towar(double cenaHurtowa, double cenaDetaliczna) {
        this.cenaHurtowa = cenaHurtowa;
        this.cenaDetaliczna = cenaDetaliczna;
    }

    // Gettery
    public double getCenaHurtowa() {
        return cenaHurtowa;
    }

    public double getCenaDetaliczna() {
        return cenaDetaliczna;
    }
}