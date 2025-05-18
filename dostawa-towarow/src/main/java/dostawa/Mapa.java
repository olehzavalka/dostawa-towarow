package dostawa;

import java.util.List;

public class Mapa {
    private int szerokosc;
    private int dlugosc;
    private List<Magazyn> magazyny;
    private List<PunktDostawy> punktyDostawy;


    public Mapa(int szerokosc, int dlugosc, List<Magazyn> magazyny, List<PunktDostawy> punktyDostawy) {
        this.szerokosc = szerokosc;
        this.dlugosc = dlugosc;
        this.magazyny = magazyny;
        this.punktyDostawy = punktyDostawy;
    }

    // Gettery

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public List<Magazyn> getMagazyny() {
        return magazyny;
    }

    public List<PunktDostawy> getPunktyDostawy() {
        return punktyDostawy;
    }
}
