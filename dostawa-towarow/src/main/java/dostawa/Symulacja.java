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
        // Rozmieść wszystkie obiekty na mapie
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

    public List<Zamowienie> generujZamowienia() {
        List<Zamowienie> zamowienia = new ArrayList<>();
        for (PunktDostawy punkt : mapa.getPunktyDostawy()) {
            Zamowienie zam = punkt.zlozZamowienie();
            if (zam != null) {
                zamowienia.add(zam);
            }
        }
        return zamowienia;
    }

    public Magazyn znajdzNajblizszyMagazyn(Pozycja punktDostawy, int iloscTowaru) {
        Magazyn najblizszy = null;
        int minDystans = Integer.MAX_VALUE;

        for (Magazyn magazyn : mapa.getMagazyny()) {
            int odlegloscPozioma = Math.abs(magazyn.getPozycja().getX() - punktDostawy.getX());
            int odlegloscPionowa = Math.abs(magazyn.getPozycja().getY() - punktDostawy.getY());

            int dystans = odlegloscPozioma + odlegloscPionowa;
            if (dystans < minDystans) {
                minDystans = dystans;
                najblizszy = magazyn;
            }
        }
        return najblizszy;
    }

    public void zapiszStatystyki() {
        // TODO: Zaimplementowac zapisywanie danych statystycznych do pliku csv
    }

    public void generujZdarzeniaLosowe() {
        // TODO: Zaimplementowac powstawanie losowych zdarzen drogowych
    }

    // Metoda do uruchomienia symulacji
    public void uruchomSymulacje() {
    }
}
