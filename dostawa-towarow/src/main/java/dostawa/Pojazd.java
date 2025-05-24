package dostawa;

public class Pojazd {
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

    // Metody sprawdzajace

    public boolean czyMoznaZaladowac(int ilosc) {
        return (aktualnaIloscTowaru + ilosc) <= ladownoscMax;
    }
}