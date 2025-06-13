package dostawa;

import java.util.Random;

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

    // Metoda do zlozenia zamowienia przez punkt dostawy do magazynu
    public Zamowienie zlozZamowienie() {
        Random random = new Random();
        int maxMozliwaIlosc = pojemnoscMax - aktualnaIloscTowaru;

        if (maxMozliwaIlosc < 1) {
            // Nie mozna zamowic nawet 1 jednostki towaru, nie generujemy zamowienia
            return null;
        }

        // Losuj ilosc z przedzialu [1, min(25, maxMozliwaIlosc)]
        int gornaGranica = Math.min(25, maxMozliwaIlosc);
        int ilosc = random.nextInt(gornaGranica) + 1; // [1, gornaGranica]

        return new Zamowienie(this, ilosc);
    }

    public void dodajTowar(int ilosc) {
        this.aktualnaIloscTowaru += ilosc;
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