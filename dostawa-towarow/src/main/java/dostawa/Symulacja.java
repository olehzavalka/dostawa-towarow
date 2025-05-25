package dostawa;

import java.util.ArrayList;
import java.util.List;

public class Symulacja {
    private Mapa mapa;
    private List<Pojazd> pojazdy;
    private List<Zdarzenie> zdarzenia;

    public Symulacja(Mapa mapa, List<Pojazd> pojazdy) {
        this.mapa = mapa;
        this.pojazdy = pojazdy;
        this.zdarzenia = new ArrayList<>();
    }

    public Symulacja(int szerokosc, int dlugosc, int liczbaMagazynow, int liczbaPunktowDostawy) {
        mapa = new Mapa(szerokosc, dlugosc, new ArrayList<>(), new ArrayList<>());
    }

    public void wyswietlMape() {
        mapa.wyswietlMape();
    }

    @Override
    public String toString() {
        return mapa.toString();
    }

    // Gettery

    public Mapa getMapa() {
        return mapa;
    }

    public List<Pojazd> getPojazdy() {
        return pojazdy;
    }

    public List<Zdarzenie> getZdarzenia() {
        return zdarzenia;
    }

    // Metoda do uruchomienia symulacji
    public void uruchomSymulacje() {
    }
}
