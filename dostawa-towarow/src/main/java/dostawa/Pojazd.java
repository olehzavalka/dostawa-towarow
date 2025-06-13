package dostawa;

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

    // Settery

    public void setPozycja(Pozycja pozycja) {
        this.pozycja = pozycja;
    }

    public void setAktualnaIloscTowaru(int ilosc) {
        this.aktualnaIloscTowaru = ilosc;
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

    // Metoda sprawdzajaca

    public boolean czyMoznaZaladowac(int ilosc) {
        return (aktualnaIloscTowaru + ilosc) <= ladownoscMax;
    }

    public abstract String getTyp();

    public abstract char getSymbol();

    public abstract int getZuzyciePaliwa();
}