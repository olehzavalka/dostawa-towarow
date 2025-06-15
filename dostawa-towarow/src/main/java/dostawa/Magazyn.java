package dostawa;

import java.util.List;

public class Magazyn implements Finanse {
    private int id;
    private Pozycja pozycja;

    // Finanse
    private double sumaKosztow = 0.0;
    private double sumaZarobkow = 0.0;
    private double saldo = 0.0;

    public Magazyn(int id, Pozycja pozycja) {
        this.id = id;
        this.pozycja = pozycja;
    }


//    public Pojazd znajdzNajlepszyPojazd(List<Pojazd> pojazdy, int iloscTowaru, Pozycja punktDostawy) {
//        Pojazd najlepszy = null;
//        int minZuzycie = Integer.MAX_VALUE;
//        for (Pojazd p : pojazdy) {
//            if (p.czyMoznaZaladowac(iloscTowaru)) {
//                int zuzyciePaliwa = p.getZuzyciePaliwa(); // Wywołuje się właściwa wersja metody!
//                if (zuzyciePaliwa < minZuzycie) {
//                    minZuzycie = zuzyciePaliwa;
//                    najlepszy = p;
//                }
//            }
//        }
//        return najlepszy;
//    }

    public void zaladujPojazd(Pojazd pojazd, int ilosc) {
        pojazd.setAktualnaIloscTowaru(ilosc);
    }

    // Metody finansowe
    public void dodajKoszt(double koszt) {
        sumaKosztow += koszt;
        saldo -= koszt;
    }

    public void dodajZarobek(double zarobek) {
        sumaZarobkow += zarobek;
        saldo += zarobek;
    }

    // Gettery

    public int getId() {
        return id;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public double getSumaZarobkow() {
        return sumaZarobkow;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getSumaKosztow() {
        return sumaKosztow;
    }
}