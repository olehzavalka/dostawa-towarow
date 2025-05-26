package dostawa;

public class PojazdMaly extends Pojazd {
    private static final int MAKSYMALNA_LADOWNOSC = 10;
    private static final int ZUZYCIE_PALIWA = 1; // Możesz potem wykorzystać

    public PojazdMaly(int id, Pozycja pozycja) {
        super(id, MAKSYMALNA_LADOWNOSC, pozycja);
    }

    @Override
    public String getTyp() {
        return "Maly";
    }

    public int getZuzyciePaliwa() {
        return ZUZYCIE_PALIWA;
    }
}