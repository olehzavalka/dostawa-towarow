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
        // TODO: Zaimplementowac znajdowanie najlepszego pojazd do przewiezienia danej ilosci do wskazanego punktu
        return null;
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