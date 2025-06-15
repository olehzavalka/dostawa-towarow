package dostawa;

import java.util.*;

public class Mapa {
    private int szerokosc;
    private int dlugosc;
    private List<Magazyn> magazyny;
    private List<PunktDostawy> punktyDostawy;

    public Mapa(int szerokosc, int dlugosc, List<Magazyn> magazyny, List<PunktDostawy> punktyDostawy) {
        this.szerokosc = szerokosc;
        this.dlugosc = dlugosc;
        this.magazyny = magazyny;
        this.punktyDostawy = punktyDostawy;
    }


    public void wyswietlMape(List<Pojazd> pojazdy) {
        char[][] mapa = new char[dlugosc][szerokosc];

        // Wypelnienie mapy "."
        for (int y = 0; y < dlugosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                mapa[y][x] = '.';
            }
        }

        // Ustawienie 'M' dla magazynow
        for (Magazyn m : magazyny) {
            int x = m.getPozycja().getX();
            int y = m.getPozycja().getY();
            mapa[y][x] = 'M';
        }

        // Ustawienie 'P' dla punktow dostawy
        for (PunktDostawy p : punktyDostawy) {
            int x = p.getPozycja().getX();
            int y = p.getPozycja().getY();
            mapa[y][x] = 'P';
        }

        // Ustawienie symboly 'm' - pojazd maly, 's' - pojazd sredni, 'd' - pojazd duzy
        for (Pojazd pojazd : pojazdy) {
            int x = pojazd.getPozycja().getX();
            int y = pojazd.getPozycja().getY();
            mapa[y][x] = pojazd.getSymbol();
        }

        // Wyswietlanie mapy
        // Dodanie numeracji kolumn na gorze
        System.out.print("\n    ");
        for (int x = 0; x < szerokosc; x++) {
            System.out.printf("%2d ", x);
        }
        System.out.println();

        // Dodanie numeracji wierszy z boku
        for (int y = 0; y < dlugosc; y++) {
            System.out.printf("%2d |", y);
            for (int x = 0; x < szerokosc; x++) {
                System.out.print(" " + mapa[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("Legenda:" +
                "\nM - magazyn, P - punkt dostawy, " +
                "\nm - maly pojazd, s - sredni pojazd, d - duzy pojazd\n");
    }


    private Pozycja losujPoprawnaPozycje(int szerokosc, int dlugosc, Set<Pozycja> zajetePozycje) {
        Random random = new Random();
        Pozycja kandydat;
        boolean poprawna;

        do {
            int x = random.nextInt(szerokosc);
            int y = random.nextInt(dlugosc);
            kandydat = new Pozycja(x, y);
            poprawna = true;
            // Sprawdzanie czy kandydat nie sasiaduje dookola z zadna juz zajeta pozycja
            for (Pozycja poz : zajetePozycje) {
                int dx = Math.abs(kandydat.getX() - poz.getX());
                int dy = Math.abs(kandydat.getY() - poz.getY());
                if (dx <= 1 && dy <= 1) {
                    poprawna = false;
                    break;
                }
            }
        } while (!poprawna);

        return kandydat;
    }



    public void rozmiescObiekty(int liczbaMagazynow, int liczbaPunktowDostawy,
                                int liczbaMalych, int liczbaSrednich, int liczbaDuzych,
                                List<Pojazd> pojazdy) {
        int liczbaObiektow = liczbaMagazynow + liczbaPunktowDostawy + liczbaMalych + liczbaSrednich + liczbaDuzych;
        if (liczbaObiektow > szerokosc * dlugosc) {
            System.out.println("Blad: Zbyt duzo obiektow na mapie!");
            return;
        }

        Random random = new Random();
        Set<Pozycja> zajetePozycje = new HashSet<>();

        // Rozmieszczanie magazynow
        for (int i = 1; i <= liczbaMagazynow; i++) {
            Pozycja pozycja = losujPoprawnaPozycje(szerokosc, dlugosc, zajetePozycje);
            zajetePozycje.add(pozycja);
            magazyny.add(new Magazyn(i, pozycja));
        }

        // Rozmieszczanie punktow dostawy
        for (int i = 1; i <= liczbaPunktowDostawy; i++) {
            Pozycja pozycja = losujPoprawnaPozycje(szerokosc, dlugosc, zajetePozycje);
            zajetePozycje.add(pozycja);
            int pojemnosc = random.nextInt(91) + 10;
            punktyDostawy.add(new PunktDostawy(i, pozycja, pojemnosc));
        }

        int idPojazdu = 1;

        // Rozmieszczanie malych pojazdow
        for (int i = 1; i <= liczbaMalych; i++) {
            Pozycja pozycja;
            do {
                int x = random.nextInt(szerokosc);
                int y = random.nextInt(dlugosc);
                pozycja = new Pozycja(x, y);
            } while (zajetePozycje.contains(pozycja));
            zajetePozycje.add(pozycja);
            pojazdy.add(new PojazdMaly(idPojazdu++, pozycja));
        }

        // Rozmieszczanie srednich pojazdow
        for (int i = 1; i <= liczbaSrednich; i++) {
            Pozycja pozycja;
            do {
                int x = random.nextInt(szerokosc);
                int y = random.nextInt(dlugosc);
                pozycja = new Pozycja(x, y);
            } while (zajetePozycje.contains(pozycja));
            zajetePozycje.add(pozycja);
            pojazdy.add(new PojazdSredni(idPojazdu++, pozycja));
        }

        // Rozmieszczanie duzych pojazdow
        for (int i = 1; i <= liczbaDuzych; i++) {
            Pozycja pozycja;
            do {
                int x = random.nextInt(szerokosc);
                int y = random.nextInt(dlugosc);
                pozycja = new Pozycja(x, y);
            } while (zajetePozycje.contains(pozycja));
            zajetePozycje.add(pozycja);
            pojazdy.add(new PojazdDuzy(idPojazdu++, pozycja));
        }
    }

    public void wyswietlPunktyDostawy() {
        System.out.println("\nWszystkie punkty dostawy:");
        for (PunktDostawy punkt : punktyDostawy) {
            System.out.println("ID: " + punkt.getId() +
                    "; Pozycja: (kolumna: " + punkt.getPozycja().getX() +
                    "; wiersz: " + punkt.getPozycja().getY() + ")" +
                    "; Pojemnosc maksymalna: " + punkt.getPojemnoscMax());
        }
    }


    public String toString(List<Pojazd> pojazdy) {
        return "Mapa o wymiarach: " + szerokosc + " x " + dlugosc +
                "\nLiczba magazynow: " + magazyny.size() +
                "\nLiczba punktow dostawy: " + punktyDostawy.size() +
                "\nLiczba wszystkich pojazdow: " + pojazdy.size();
    }




    public List<Pozycja> znajdzNajkrotszaTrase(Pozycja start, Pozycja cel, List<Pozycja> zakazane) {
        Queue<List<Pozycja>> kolejka = new LinkedList<>();
        Set<Pozycja> odwiedzone = new HashSet<>();
        List<Pozycja> sciezkaStartowa = new ArrayList<>();
        sciezkaStartowa.add(start);
        kolejka.add(sciezkaStartowa);
        odwiedzone.add(start);

        int[][] kierunki = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!kolejka.isEmpty()) {
            List<Pozycja> sciezkaDoAnalizy = kolejka.poll();
            Pozycja ostatnia = sciezkaDoAnalizy.get(sciezkaDoAnalizy.size()-1);
            if (ostatnia.equals(cel)) {
                return sciezkaDoAnalizy.subList(1, sciezkaDoAnalizy.size());
            }
            for (int[] k : kierunki) {
                int nowyX = ostatnia.getX() + k[0];
                int nowyY = ostatnia.getY() + k[1];
                Pozycja nowa = new Pozycja(nowyX, nowyY);
                if (nowyX >= 0 && nowyX < szerokosc && nowyY >= 0 && nowyY < dlugosc
                        && !odwiedzone.contains(nowa)
                        && (zakazane == null || !zakazane.contains(nowa))) {
                    List<Pozycja> nowaSciezka = new ArrayList<>(sciezkaDoAnalizy);
                    nowaSciezka.add(nowa);
                    kolejka.add(nowaSciezka);
                    odwiedzone.add(nowa);
                }
            }
        }
        return new ArrayList<>(); // Brak trasy
    }


    // Szukanie pola obok magazynu/punktu dostawy w celu umozliwienia rozladunku
    public Pozycja znajdzWolnePoleObok(Pozycja cel, Pozycja start, List<Pojazd> pojazdy) {
        int[][] kierunki = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} }; // prawo, lewo, dol, gora
        Pozycja najlepszePole = null;
        int najlepszyDystans = Integer.MAX_VALUE;

        for (int[] k : kierunki) {
            int nowyX = cel.getX() + k[0];
            int nowyY = cel.getY() + k[1];
            if (nowyX >= 0 && nowyX < szerokosc && nowyY >= 0 && nowyY < dlugosc) {
                Pozycja kandydat = new Pozycja(nowyX, nowyY);
                boolean zajete = false;
                for (Magazyn m : magazyny) {
                    if (m.getPozycja().equals(kandydat)) {
                        zajete = true;
                        break;
                    }
                }
                if (zajete) continue;
                for (PunktDostawy pd : punktyDostawy) {
                    if (pd.getPozycja().equals(kandydat)) {
                        zajete = true;
                        break;
                    }
                }
                if (zajete) continue;
                int dystans = Math.abs(kandydat.getX() - start.getX()) + Math.abs(kandydat.getY() - start.getY());
                if (dystans < najlepszyDystans) {
                    najlepszyDystans = dystans;
                    najlepszePole = kandydat;
                }
            }
        }
        return najlepszePole;
    }

    // Rezerwacja pozycji zajetych przez magazyny i punkty dostawy
    public List<Pozycja> getZakazanePola() {
        List<Pozycja> zakazane = new ArrayList<>();
        for (Magazyn m : magazyny) {
            zakazane.add(m.getPozycja());
        }
        for (PunktDostawy pd : punktyDostawy) {
            zakazane.add(pd.getPozycja());
        }
        return zakazane;
    }


    // Gettery

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public List<Magazyn> getMagazyny() {
        return magazyny;
    }

    public List<PunktDostawy> getPunktyDostawy() {
        return punktyDostawy;
    }
}
