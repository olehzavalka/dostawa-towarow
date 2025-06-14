package dostawa;

public class PojazdDuzy extends Pojazd {
    private static final int MAKSYMALNA_LADOWNOSC = 25;
    private static final int ZUZYCIE_PALIWA = 4;

    public PojazdDuzy(int id, Pozycja pozycja) {
        super(id, MAKSYMALNA_LADOWNOSC, pozycja, "Duzy", 'd');
    }

    public int getZuzyciePaliwa() {
        return ZUZYCIE_PALIWA;
    }
}