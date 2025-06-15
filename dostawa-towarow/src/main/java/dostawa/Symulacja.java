package dostawa;

import java.util.*;

public class Symulacja {
    private Mapa mapa;
    private List<Pojazd> pojazdy;
    private List<Zdarzenie> zdarzenia;

    private static final double CENA_PALIWA = 2.0;
    private static final double CENA_KROKU_POJAZDU = 1.0;
    private static final double CENA_KROKU_POJAZDU_DLA_MAGAZYNU = 1.2;
    private static final double CENA_KROKU_POJAZDU_DLA_PUNKTU = 1.4;

    private static final Towar towar = new Towar(6.0, 25.0);


    public Symulacja(Mapa mapa, List<Pojazd> pojazdy) {
        this.mapa = mapa;
        this.pojazdy = pojazdy;
        this.zdarzenia = new ArrayList<>();
    }

    public Symulacja(int szerokosc, int dlugosc, int liczbaMagazynow, int liczbaPunktowDostawy,
                     int liczbaMalych, int liczbaSrednich, int liczbaDuzych) {
        this.pojazdy = new ArrayList<>();
        this.mapa = new Mapa(szerokosc, dlugosc, new ArrayList<>(), new ArrayList<>());
        // Rozmieść wszystkie obiekty na mapie
        mapa.rozmiescObiekty(liczbaMagazynow, liczbaPunktowDostawy, liczbaMalych, liczbaSrednich, liczbaDuzych, pojazdy);
        this.zdarzenia = new ArrayList<>();
    }

    public void wyswietlMape() {
        mapa.wyswietlMape(pojazdy);
    }

    @Override
    public String toString() {
        return mapa.toString(pojazdy);
    }

    // Gettery

    public Mapa getMapa() {
        return mapa;
    }

    public List<Pojazd> getPojazdy() {
        return pojazdy;
    }

    public List<Zdarzenie> getZdarzenia() {
        return zdarzenia;
    }

    public List<Zamowienie> generujZamowienia() {
        List<Zamowienie> zamowienia = new ArrayList<>();
        for (PunktDostawy punkt : mapa.getPunktyDostawy()) {
            if (punkt.getCzyMaAktywneZamowienie()) {
                continue; // ten punkt czeka na dostawe – nie generuje nowego zamowienia
            }

            // Generuje kolejne zamowienie
            // tylko jesli punkt nie jest pelny i nie ma aktywnego zamowienia
            Zamowienie zam = punkt.zlozZamowienie();
            if (zam != null) {
                zamowienia.add(zam);
                punkt.setCzyMaAktywneZamowienie(true); // zaznaczenie, ze punkt oczekuje na realizacje zamowienia
            }
        }
        return zamowienia;
    }


    public Magazyn znajdzNajblizszyMagazyn(Pozycja punktDostawy, int iloscTowaru) {
        Magazyn najblizszy = null;
        int minDystans = Integer.MAX_VALUE;

        for (Magazyn magazyn : mapa.getMagazyny()) {
            int odlegloscPozioma = Math.abs(magazyn.getPozycja().getX() - punktDostawy.getX());
            int odlegloscPionowa = Math.abs(magazyn.getPozycja().getY() - punktDostawy.getY());

            int dystans = odlegloscPozioma + odlegloscPionowa;

            if (dystans < minDystans) {
                minDystans = dystans;
                najblizszy = magazyn;
            }
        }

        return najblizszy;
    }


    // Przechowywanie zamowien bedacych w realizacji
    private List<Zamowienie> zamowieniaWRealizacji = new ArrayList<>();


    // Tworzenie id zamowien na podstawie informacji o punkcie dostawy i ilosci towaru
    private Set<String> przygotujNoweIdZamowien(List<Zamowienie> zamowienia) {
        Set<String> idZamowien = new HashSet<>();

        for (Zamowienie z : zamowienia) {
            idZamowien.add(z.getPunktDostawy().getId() + "_" + z.getIlosc());
        }

        return idZamowien;
    }



    private void wypiszZamowienia(List<Zamowienie> zamowienia, Set<String> noweIdZamowien) {

        // Zamowienia w realizacji z poprzednich epok
        for (Zamowienie zam : zamowieniaWRealizacji) {
            String idZamowienia = zam.getPunktDostawy().getId() + "_" + zam.getIlosc();
            if (!noweIdZamowien.contains(idZamowienia)) {
                System.out.println("Punkt dostawy ID " + zam.getPunktDostawy().getId() +
                        " (" + zam.getPunktDostawy().getPozycja().getX() + ", " +
                        zam.getPunktDostawy().getPozycja().getY() + ") zamawia " +
                        zam.getIlosc() + " jednostek towaru (w realizacji)");
            }
        }

        // Nowe zamowienia
        for (Zamowienie zam : zamowienia) {
            System.out.println("Punkt dostawy ID " + zam.getPunktDostawy().getId() +
                    " (" + zam.getPunktDostawy().getPozycja().getX() + ", " +
                    zam.getPunktDostawy().getPozycja().getY() + ") zamawia " +
                    zam.getIlosc() + " jednostek towaru (nowe zamowienie)");
        }
    }


    // Aktualizacja listy zamowien w realizacji
    private void zaktualizujListeZamowienWRealizacji(List<Zamowienie> zamowienia) {
        for (Zamowienie zam : zamowienia) {
            boolean jestWRealizacji = false;

            for (Zamowienie z : zamowieniaWRealizacji) {
                if (z.getPunktDostawy().getId() == zam.getPunktDostawy().getId()
                        && z.getIlosc() == zam.getIlosc()) {
                    jestWRealizacji = true;
                    break;
                }
            }

            if (!jestWRealizacji) {
                zamowieniaWRealizacji.add(zam);
            }
        }
    }


    // Wyszukiwanie pojazdow nie bedacych w trakcie realizacji zamowienia
    private List<Pojazd> wyszukajWolnePojazdy() {
        List<Pojazd> wolnePojazdy = new ArrayList<>();

        for (Pojazd pojazd : pojazdy) {
            if (pojazd.getStanPojazdu() == Pojazd.STAN_WOLNY) {
                wolnePojazdy.add(pojazd);
            }
        }

        return wolnePojazdy;
    }


    private boolean czyJestPojazdRealizujacyZamowienie(Zamowienie zam) {
        for (Pojazd p : pojazdy) {
            if (p.getStanPojazdu() != Pojazd.STAN_WOLNY &&
                    p.getPunktDocelowy() != null &&
                    p.getPunktDocelowy().getId() == zam.getPunktDostawy().getId() &&
                    p.getIloscDoDostarczenia() == zam.getIlosc()) {
                return true;
            }
        }
        return false;
    }


    private List<Zamowienie> wyszukajWolneZamowienia() {
        List<Zamowienie> wolneZamowienia = new ArrayList<>();
        for (Zamowienie zam : zamowieniaWRealizacji) {
            if (!czyJestPojazdRealizujacyZamowienie(zam)) {
                wolneZamowienia.add(zam);
            }
        }
        return wolneZamowienia;
    }


    // Przypisanie wolnych zamowien do pojazdu
    private boolean przypiszZamowienieDoPojazdu(Pojazd pojazd, List<Zamowienie> wolneZamowienia) {

        Iterator<Zamowienie> iter = wolneZamowienia.iterator();
        while (iter.hasNext()) {
            Zamowienie zam = iter.next();

            // Sprawdzenie, czy pojazd ma wystarczająca ladownosc
            if (!pojazd.czyMoznaZaladowac(zam.getIlosc())) {
                continue;
            }

            // Szukanie najblizszego magazynu do punktu dostawy
            Magazyn magazyn = znajdzNajblizszyMagazyn(zam.getPunktDostawy().getPozycja(), zam.getIlosc());

            // Sprawdzenie dostępnosci wolnego pola obok magazynu
            Pozycja poleObokMagazynu = mapa.znajdzWolnePoleObok(magazyn.getPozycja(), pojazd.getPozycja(), pojazdy);

            // Wyznaczenie trasy pojazdu do magazynu
            List<Pozycja> zakazane = mapa.getZakazanePola();
            zakazane.remove(pojazd.getPozycja());
            List<Pozycja> trasaDoMagazynu = mapa.znajdzNajkrotszaTrase(pojazd.getPozycja(), poleObokMagazynu, zakazane);
            int liczbaKrokowDoMagazynu = trasaDoMagazynu.size();

            // Wyznaczenie pola obok punktu dostawy
            Pozycja poleObokPunktu = mapa.znajdzWolnePoleObok(zam.getPunktDostawy().getPozycja(), poleObokMagazynu, pojazdy);

            // Wyznaczenie trasy z magazynu do punktu dostawy
            List<Pozycja> zakazane2 = mapa.getZakazanePola();
            zakazane2.remove(poleObokMagazynu);
            List<Pozycja> trasaMagazynDoPunktu = mapa.znajdzNajkrotszaTrase(poleObokMagazynu, poleObokPunktu, zakazane2);
            int liczbaKrokowMagazynDoPunktu = trasaMagazynDoPunktu.size();

            // Suma krokow pojazdu
            int sumaKrokowPojazdu = liczbaKrokowDoMagazynu + liczbaKrokowMagazynDoPunktu;



            // Finanse

            double kosztMagazynu = (liczbaKrokowDoMagazynu * (CENA_KROKU_POJAZDU_DLA_MAGAZYNU * CENA_PALIWA)) * pojazd.getZuzyciePaliwa();
            magazyn.dodajKoszt(kosztMagazynu);
            double zarobekMagazynu = zam.getIlosc() * towar.getCenaHurtowa();
            magazyn.dodajZarobek(zarobekMagazynu);

            // Koszt dla punktu dostawy (według wzoru – koszt za towar + droga z magazynu do punktu dostawy)
            double kosztDlaPunktu = zam.getIlosc() * towar.getCenaHurtowa() +
                    (liczbaKrokowMagazynDoPunktu * (CENA_KROKU_POJAZDU_DLA_PUNKTU * CENA_PALIWA)) * pojazd.getZuzyciePaliwa();
            zam.getPunktDostawy().dodajKoszt(kosztDlaPunktu);

            // Koszt pojazdu (paliwo na całej trasie: do magazynu + z magazynu do punktu)
            double kosztDlaPojazdu = sumaKrokowPojazdu * CENA_KROKU_POJAZDU * CENA_PALIWA * pojazd.getZuzyciePaliwa();
            pojazd.dodajKoszt(kosztDlaPojazdu);
            // Zarobek pojazd
            double zarobekPojazdu = obliczZarobekPojazdu(pojazd, liczbaKrokowDoMagazynu, liczbaKrokowMagazynDoPunktu);
            pojazd.dodajZarobek(zarobekPojazdu);

            // Aktualizacja stanu pojazdu i przypisanie do niego zamowienia
            pojazd.setStanPojazdu(Pojazd.STAN_DOJEZDZA_DO_MAGAZYNU);
            pojazd.setMagazynDocelowy(magazyn);
            pojazd.setPunktDocelowy(zam.getPunktDostawy());
            pojazd.setIloscDoDostarczenia(zam.getIlosc());
            pojazd.setTrasaDoCelu(trasaDoMagazynu);

            System.out.println("Pojazd ID " + pojazd.getId() +
                    " (" + pojazd.getPozycja().getX() + ", " + pojazd.getPozycja().getY() + ")" +
                    " dostal zlecenie od magazynu ID " + magazyn.getId() +
                    " (" + magazyn.getPozycja().getX() + ", " + magazyn.getPozycja().getY() + ")" +
                    " i ma dostarczyc " + zam.getIlosc() + " jednostek towaru do punktu " +
                    zam.getPunktDostawy().getId() +
                    " (" + zam.getPunktDostawy().getPozycja().getX() + ", " +
                    zam.getPunktDostawy().getPozycja().getY() + ")");


            // Wyswietlanie informacji o finansach
            System.out.println("- Finanse: Magazyn ID " + magazyn.getId() + " zarabia " + zarobekMagazynu +
                    ", punkt dostawy ID " + zam.getPunktDostawy().getId() + " placi " + kosztDlaPunktu +
                    ", dla pojazdu ID " + pojazd.getId() + " koszt paliwa wynosi " + kosztDlaPojazdu +
                    " (kroki do magazynu: " + liczbaKrokowDoMagazynu + ", kroki z magazynu do punktu ID: "
                    + liczbaKrokowMagazynDoPunktu + ")");

            // Usuwanie przypisanego zamowienia z listy wolnych
            iter.remove();
            return true;
        }
        return false;
    }


    // Obliczanie zysku dla pojazdu
    private double obliczZarobekPojazdu(Pojazd pojazd, int krokiDoMagazynu, int krokiDoPunktu) {
        double zarobek = ((krokiDoMagazynu * CENA_KROKU_POJAZDU_DLA_MAGAZYNU * CENA_PALIWA)
                + (krokiDoPunktu * CENA_KROKU_POJAZDU_DLA_PUNKTU * CENA_PALIWA)) * pojazd.getZuzyciePaliwa();
        return zarobek;
    }


    private Map<String, Double[]> podsumujFinansePojazdow() {
        Map<String, Double[]> map = new HashMap<>();
        for (Pojazd pojazd : pojazdy) {
            String typ = pojazd.getTyp();
            Double[] dane = map.getOrDefault(typ, new Double[]{0.0, 0.0, 0.0});
            dane[0] += pojazd.getSumaKosztow();
            dane[1] += pojazd.getSumaZarobkow();
            dane[2] += pojazd.getSaldo();
            map.put(typ, dane);
        }
        return map;
    }



    // Proba przypisania zamowienia wszystkim wolnym pojazdom, korzystajac z metody przypisujacej
    // zamowienie do pojedynczego pojazdu
    private void przypiszZamowieniaPojazdom() {
        List<Zamowienie> wolneZamowienia = wyszukajWolneZamowienia();
        List<Pojazd> wolnePojazdy = wyszukajWolnePojazdy();

        for (Pojazd pojazd : wolnePojazdy) {
            // Przypisywanie zamowien dla wolnych pojazdow
            przypiszZamowienieDoPojazdu(pojazd, wolneZamowienia);
        }
    }



    // Ruch pojazdu do magazynu
    private void obsluzDojazdDoMagazynu(Pojazd pojazd) {
        pojazd.przesunNaKolejnyKrokTrasy();
        if (pojazd.getTrasaDoCelu() == null || pojazd.getTrasaDoCelu().isEmpty()) {
            if (Pozycja.czySasiednie(pojazd.getPozycja(), pojazd.getMagazynDocelowy().getPozycja())) {
                pojazd.setStanPojazdu(Pojazd.STAN_LADOWANIE);
                System.out.println("Pojazd ID " + pojazd.getId() +
                        " dojechal obok magazynu ID " + pojazd.getMagazynDocelowy().getId());
            }
        }
    }


    // Ladowanie pojazdu obok magazynu
    private void obsluzLadowanie(Pojazd pojazd) {
        pojazd.getMagazynDocelowy().zaladujPojazd(pojazd, pojazd.getIloscDoDostarczenia());
        Pozycja poleObokPunktu = mapa.znajdzWolnePoleObok(pojazd.getPunktDocelowy().getPozycja(), pojazd.getPozycja(), pojazdy);

        if (poleObokPunktu == null) {
            System.out.println("Brak wolnego miejsca przy punkcie dostawy dla pojazdu " + pojazd.getId());
            return;
        }

        List<Pozycja> zakazane = mapa.getZakazanePola();
        zakazane.remove(pojazd.getPozycja());
        List<Pozycja> trasaDoPunktu = mapa.znajdzNajkrotszaTrase(pojazd.getPozycja(), poleObokPunktu, zakazane);
        pojazd.setTrasaDoCelu(trasaDoPunktu);
        pojazd.setStanPojazdu(Pojazd.STAN_DOJEZDZA_DO_PUNKTU);

        System.out.println("Pojazd ID " + pojazd.getId() +
                " zaladowany i jedzie obok punktu dostawy ID " + pojazd.getPunktDocelowy().getId());
    }


    // Ruch pojazdu do punktu dostawy
    private void obsluzDojazdDoPunktu(Pojazd pojazd) {

        if (pojazd.getTrasaDoCelu() != null && !pojazd.getTrasaDoCelu().isEmpty()) {
            pojazd.setPozycja(pojazd.getTrasaDoCelu().remove(0));
        }

        if (pojazd.getTrasaDoCelu() == null || pojazd.getTrasaDoCelu().isEmpty()) {
            if (Pozycja.czySasiednie(pojazd.getPozycja(), pojazd.getPunktDocelowy().getPozycja())) {
                pojazd.setStanPojazdu(Pojazd.STAN_ROZLADUNEK);
                System.out.println("Pojazd ID " + pojazd.getId() + " dojechal obok punktu dostawy ID " + pojazd.getPunktDocelowy().getId());
            }
        }
    }


    // Rozladunek pojazdu obok punktu dostawy
    private void obsluzRozladunek(Pojazd pojazd) {

        pojazd.getPunktDocelowy().rozladujPojazd(pojazd);
        System.out.println("Pojazd ID " + pojazd.getId() +
                " dostarczyl " + pojazd.getIloscDoDostarczenia() +
                " jednostek towaru do punktu " +
                pojazd.getPunktDocelowy().getId() +
                " (" + pojazd.getPunktDocelowy().getPozycja().getX() + ", " +
                pojazd.getPunktDocelowy().getPozycja().getY() + ")");

        Iterator<Zamowienie> iterator = zamowieniaWRealizacji.iterator();
        while (iterator.hasNext()) {
            Zamowienie zam = iterator.next();

            if (zam.getPunktDostawy().getId() == pojazd.getPunktDocelowy().getId()
                    && zam.getIlosc() == pojazd.getIloscDoDostarczenia()) {
                iterator.remove();
                break;
            }
        }

        pojazd.setStanPojazdu(Pojazd.STAN_WOLNY);
        pojazd.setMagazynDocelowy(null);
        pojazd.setPunktDocelowy(null);
        pojazd.setIloscDoDostarczenia(0);
        pojazd.setTrasaDoCelu(null);
    }


    private void ruchPojazdow() {
        for (Pojazd pojazd : pojazdy) {
            int stan = pojazd.getStanPojazdu();
            if (stan == Pojazd.STAN_DOJEZDZA_DO_MAGAZYNU) {
                obsluzDojazdDoMagazynu(pojazd);
            } else if (stan == Pojazd.STAN_LADOWANIE) {
                obsluzLadowanie(pojazd);
            } else if (stan == Pojazd.STAN_DOJEZDZA_DO_PUNKTU) {
                obsluzDojazdDoPunktu(pojazd);
            } else if (stan == Pojazd.STAN_ROZLADUNEK) {
                obsluzRozladunek(pojazd);
            }
        }
    }


    private void sprzedazDetalicznaWPunktachDostawy() {
        for (PunktDostawy punkt : mapa.getPunktyDostawy()) {
            punkt.sprzedajLosowoTowar(towar);
        }
    }


    private StatystykiCSV statystykiCSV = new StatystykiCSV();

    // Metoda do uruchomienia symulacji
    public void uruchomSymulacje(int liczbaEpok) {
        for (int epoka = 1; epoka <= liczbaEpok; epoka++) {
            System.out.println("******* Epoka " + epoka + " *******");

            List<Zamowienie> zamowienia = generujZamowienia();

            Set<String> noweIdZamowien = przygotujNoweIdZamowien(zamowienia);
            wypiszZamowienia(zamowienia, noweIdZamowien);

            zaktualizujListeZamowienWRealizacji(zamowienia);

            przypiszZamowieniaPojazdom();

            ruchPojazdow();

            sprzedazDetalicznaWPunktachDostawy();

            wyswietlMape();
            statystykiCSV.zapisz(epoka, mapa.getMagazyny(), mapa.getPunktyDostawy(), pojazdy);
            System.out.println();
        }
        Wykresy.generujWszystkieWykresy();
    }
}
