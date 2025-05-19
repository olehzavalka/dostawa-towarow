package dostawa;

public class PunktDostawy {
    private int id;
    private Pozycja pozycja;
    private int pojemnoscMax;
    private int aktualnaIloscTowaru;


    public PunktDostawy(int id, Pozycja pozycja, int pojemnoscMax) {
        this.id = id;
        this.pozycja = pozycja;
        this.pojemnoscMax = pojemnoscMax;
        this.aktualnaIloscTowaru = 0;
    }

    // Gettery

    public int getId() {
        return id;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public int getPojemnoscMax() {
        return pojemnoscMax;
    }

    public int getAktualnaIloscTowaru() {
        return aktualnaIloscTowaru;
    }

    // Metody sprawdzajace

    public boolean czyPusty() {
        return aktualnaIloscTowaru == 0;
    }

    public boolean czyMoznaZaladowac(int ilosc) {
        return (aktualnaIloscTowaru + ilosc) <= pojemnoscMax;
    }
}