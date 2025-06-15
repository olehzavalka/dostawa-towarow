package dostawa;

import java.util.*;

public abstract class Pojazd {
    private int id;
    private int ladownoscMax;
    private int aktualnaIloscTowaru;
    private Pozycja pozycja;

    public Pojazd(int id, int ladownoscMax, Pozycja pozycja) {
        this.id = id;
        this.ladownoscMax = ladownoscMax;
        this.pozycja = pozycja;
        this.aktualnaIloscTowaru = 0;
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

    // Metoda sprawdzajaca

    public boolean czyMoznaZaladowac(int ilosc) {
        return (aktualnaIloscTowaru + ilosc) <= ladownoscMax;
    }

    public abstract String getTyp();

    public abstract char getSymbol();

    public abstract int getZuzyciePaliwa();
}