package dostawa;

public class PojazdSredni extends Pojazd {
    private static final int MAKSYMALNA_LADOWNOSC = 15;
    private static final int ZUZYCIE_PALIWA = 2;

    public PojazdSredni(int id, Pozycja pozycja) {
        super(id, MAKSYMALNA_LADOWNOSC, pozycja);
    }

    @Override
    public String getTyp() {
        return "Sredni";
    }

    public int getZuzyciePaliwa() {
        return ZUZYCIE_PALIWA;
    }
}