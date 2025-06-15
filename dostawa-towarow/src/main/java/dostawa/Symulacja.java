package dostawa;

import java.util.*;

public class Symulacja {
    private Mapa mapa;
    private List<Pojazd> pojazdy;
    private List<Zdarzenie> zdarzenia;

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

            // Szukanie trasy do magazynu
            List<Pozycja> zakazane = mapa.getZakazanePola();
            zakazane.remove(pojazd.getPozycja());
            List<Pozycja> trasaDoMagazynu = mapa.znajdzNajkrotszaTrase(pojazd.getPozycja(), poleObokMagazynu, zakazane);

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

            // Usuwanie przypisanego zamowienia z listy wolnych
            iter.remove();
            return true;
        }
        return false;
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


    // Ladowanie pojazdu w magazynie
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
                System.out.println("Pojazd ID " + pojazd.getId() +
                        " dojechal obok punktu dostawy ID " + pojazd.getPunktDocelowy().getId());
            }
        }
    }



    public void zapiszStatystyki() {
        // TODO: Zaimplementowac zapisywanie danych statystycznych do pliku csv
    }

    public void generujZdarzeniaLosowe() {
        // TODO: Zaimplementowac powstawanie losowych zdarzen drogowych
    }

    // Metoda do uruchomienia symulacji
    public void uruchomSymulacje() {
    }
}
