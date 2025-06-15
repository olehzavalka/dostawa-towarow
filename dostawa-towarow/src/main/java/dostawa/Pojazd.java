package dostawa;

import java.util.*;

public abstract class Pojazd implements Finanse {
    private int id;
    private int ladownoscMax;
    private int aktualnaIloscTowaru;
    private Pozycja pozycja;
    private final String typ;
    private final char symbol;

    // Finanse
    private double sumaKosztow = 0.0;
    private double sumaZarobkow = 0.0;
    private double saldo = 0.0;

    public Pojazd(int id, int ladownoscMax, Pozycja pozycja, String typ, char symbol) {
        this.id = id;
        this.ladownoscMax = ladownoscMax;
        this.pozycja = pozycja;
        this.aktualnaIloscTowaru = 0;
        this.typ = typ;
        this.symbol = symbol;
    }

    public static final int STAN_WOLNY = 0;
    public static final int STAN_DOJEZDZA_DO_MAGAZYNU = 1;
    public static final int STAN_LADOWANIE = 2;
    public static final int STAN_DOJEZDZA_DO_PUNKTU = 3;
    public static final int STAN_ROZLADUNEK = 4;

    private int stanPojazdu = STAN_WOLNY;
    private Magazyn magazynDocelowy;
    private PunktDostawy punktDocelowy;
    private int iloscDoDostarczenia;
    private List<Pozycja> trasaDoCelu;

    public void przesunNaKolejnyKrokTrasy() {
        if (trasaDoCelu != null && !trasaDoCelu.isEmpty()) {
            setPozycja(trasaDoCelu.remove(0));
        }
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

    // Settery

    public void setPozycja(Pozycja pozycja) {
        this.pozycja = pozycja;
    }

    public void setAktualnaIloscTowaru(int ilosc) {
        this.aktualnaIloscTowaru = ilosc;
    }

    public void setStanPojazdu(int stan) {
        this.stanPojazdu = stan;
    }

    public void setMagazynDocelowy(Magazyn magazynDocelowy) {
        this.magazynDocelowy = magazynDocelowy;
    }

    public void setPunktDocelowy(PunktDostawy punktDocelowy) {
        this.punktDocelowy = punktDocelowy;
    }

    public void setIloscDoDostarczenia(int ilosc) {
        this.iloscDoDostarczenia = ilosc;
    }

    public void setTrasaDoCelu(List<Pozycja> trasa) {
        this.trasaDoCelu = trasa;
    }

    // Gettery

    public int getId() {
        return id;
    }

    public int getLadownoscMax() {
        return ladownoscMax;
    }

    public int getAktualnaIloscTowaru() {
        return aktualnaIloscTowaru;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public int getStanPojazdu() {
        return stanPojazdu;
    }

    public Magazyn getMagazynDocelowy() {
        return magazynDocelowy;
    }

    public PunktDostawy getPunktDocelowy() {
        return punktDocelowy;
    }

    public int getIloscDoDostarczenia() {
        return iloscDoDostarczenia;
    }

    public List<Pozycja> getTrasaDoCelu() {
        return trasaDoCelu;
    }

    public double getSumaKosztow() {
        return sumaKosztow;
    }

    public double getSumaZarobkow() {
        return sumaZarobkow;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTyp() {
        return typ;
    }

    public char getSymbol() {
        return symbol;
    }

    // Metoda sprawdzajaca

    public boolean czyMoznaZaladowac(int ilosc) {
        return (aktualnaIloscTowaru + ilosc) <= ladownoscMax;
    }

    public abstract int getZuzyciePaliwa();
}