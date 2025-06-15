package dostawa;

import java.util.Random;

public class PunktDostawy implements Finanse {
    private int id;
    private Pozycja pozycja;
    private int pojemnoscMax;
    private int aktualnaIloscTowaru;


    // Finanse
    private double sumaKosztow = 0.0;
    private double sumaZarobkow = 0.0;
    private double saldo = 0.0;


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


    private boolean czyMaAktywneZamowienie = false;


    public void rozladujPojazd(Pojazd pojazd) {
        this.dodajTowar(pojazd.getAktualnaIloscTowaru());
        this.setCzyMaAktywneZamowienie(false);
        pojazd.setAktualnaIloscTowaru(0);
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

    public void sprzedajTowary(Towar towar) {
        if (aktualnaIloscTowaru > 0) {
            double zarobek = aktualnaIloscTowaru * towar.getCenaDetaliczna();
            dodajZarobek(zarobek);
            this.aktualnaIloscTowaru = 0;
        }
    }


    // Sprzedaz towaru
    public void sprzedajLosowoTowar(Towar towar) {
        if (aktualnaIloscTowaru > 0) {
            Random random = new Random();
            int ileSprzedac = Math.min(aktualnaIloscTowaru, random.nextInt(5) + 1); // 1-5, nie więcej niż masz na stanie
            double zarobek = ileSprzedac * towar.getCenaDetaliczna();
            dodajZarobek(zarobek);
            aktualnaIloscTowaru -= ileSprzedac;
            System.out.println("Punkt dostawy ID " + id + " sprzedał " + ileSprzedac + " szt. towaru detalicznie (zarobek: " + zarobek + ")");
        }
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

    public boolean getCzyMaAktywneZamowienie() {
        return czyMaAktywneZamowienie;
    }

    // Gettery finansowe

    public double getSumaKosztow() {
        return sumaKosztow;
    }

    public double getSumaZarobkow() {
        return sumaZarobkow;
    }

    public double getSaldo() {
        return saldo;
    }


    // Settery

    public void setCzyMaAktywneZamowienie(boolean wartosc) {
        this.czyMaAktywneZamowienie = wartosc;
    }

    // Metody sprawdzajace

    public boolean czyPusty() {
        return aktualnaIloscTowaru == 0;
    }

    public boolean czyMoznaZaladowac(int ilosc) {
        return (aktualnaIloscTowaru + ilosc) <= pojemnoscMax;
    }
}