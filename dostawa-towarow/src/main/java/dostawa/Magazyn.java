package dostawa;

import java.util.List;

public class Magazyn {
    private int id;
    private Pozycja pozycja;

    public Magazyn(int id, Pozycja pozycja) {
        this.id = id;
        this.pozycja = pozycja;
    }

    // Do zrobienia
    public Pojazd znajdzNajlepszyPojazd(List<Pojazd> pojazdy, int iloscTowaru, Pozycja punktDostawy) {
        Pojazd najlepszy = null;
        int minZuzycie = Integer.MAX_VALUE;
        for (Pojazd p : pojazdy) {
            if (p.czyMoznaZaladowac(iloscTowaru)) {
                int zuzyciePaliwa = p.getZuzyciePaliwa(); // Wywołuje się właściwa wersja metody!
                if (zuzyciePaliwa < minZuzycie) {
                    minZuzycie = zuzyciePaliwa;
                    najlepszy = p;
                }
            }
        }
        return najlepszy;
    }

    public void ladujPojazdIMarsz(Pojazd pojazd, Magazyn magazyn, Zamowienie zamowienie) {
        // TODO: Zaimplementowac zaladowanie towarow do pojazdu
    }

    public int getId() {
        return id;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }
}