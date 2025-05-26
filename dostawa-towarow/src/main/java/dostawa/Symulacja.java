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

    public Symulacja(int szerokosc, int dlugosc, int liczbaMagazynow, int liczbaPunktowDostawy,
                     int liczbaMalych, int liczbaSrednich, int liczbaDuzych) {
        this.pojazdy = new ArrayList<>();
        this.mapa = new Mapa(szerokosc, dlugosc, new ArrayList<>(), new ArrayList<>());
        // Rozmiesc wszystkie obiekty na mapie
        mapa.rozmiescObiekty(liczbaMagazynow, liczbaPunktowDostawy, liczbaMalych, liczbaSrednich, liczbaDuzych, pojazdy);
        this.zdarzenia = new ArrayList<>();
    }

    public void wyswietlMape() {
        mapa.wyswietlMape(pojazdy);
    }

    @Override
    public String toString() {
        return mapa.toString(pojazdy);
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
