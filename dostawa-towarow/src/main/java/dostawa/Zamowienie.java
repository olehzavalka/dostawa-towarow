package dostawa;

public class Zamowienie {
    private PunktDostawy punktDostawy;
    private int ilosc;

    public Zamowienie(PunktDostawy punktDostawy, int ilosc) {
        this.punktDostawy = punktDostawy;
        this.ilosc = ilosc;
    }

    public PunktDostawy getPunktDostawy() {
        return punktDostawy;
    }

    public int getIlosc() {
        return ilosc;
    }

    // Przechowanie informacji o pojezdzie dla zamowienia w tej klasie
}