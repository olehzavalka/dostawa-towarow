package dostawa;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        System.out.println("\nMapa:");
        for (int y = 0; y < dlugosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                System.out.print(mapa[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("Legenda:" +
                "\nM - magazyn, P - punkt dostawy, " +
                "\nm - maly pojazd, s - sredni pojazd, d - duzy pojazd\n");
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
            Pozycja pozycja;
            do {
                int x = random.nextInt(szerokosc);
                int y = random.nextInt(dlugosc);
                pozycja = new Pozycja(x, y);
            } while (zajetePozycje.contains(pozycja));
            zajetePozycje.add(pozycja);
            magazyny.add(new Magazyn(i, pozycja));
        }

        // Rozmieszczanie punktow dostawy
        for (int i = 1; i <= liczbaPunktowDostawy; i++) {
            Pozycja pozycja;
            do {
                int x = random.nextInt(szerokosc);
                int y = random.nextInt(dlugosc);
                pozycja = new Pozycja(x, y);
            } while (zajetePozycje.contains(pozycja));
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


    // Do zrobienia
    public void znajdzNajkrotszaTrase(Pozycja start, Pozycja cel) {
        // TODO: Zaimplementuj BFS/Dijkstra, zwróć listę pozycji na trasie
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
