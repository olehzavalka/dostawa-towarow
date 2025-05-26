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

    public void wyswietlMape() {
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

        // Wyswietlanie mapy
        System.out.println("Mapa:");
        for (int y = 0; y < dlugosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                System.out.print(mapa[y][x] + " ");
            }
            System.out.println();
        }
    }


    public void rozmiescObiekty(int liczbaMagazynow, int liczbaPunktowDostawy) {
        if (liczbaMagazynow + liczbaPunktowDostawy > szerokosc * dlugosc) {
            System.out.println("Blad: Zbyt duzo magazynow i punktow dostawy w stosunku do liczby miejsc na planszy!");
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

            // Losowe wybieranie pojemnosci dla punktu dostawy z zakresu od 10 do 100
            int pojemnosc = random.nextInt(91) + 10;
            punktyDostawy.add(new PunktDostawy(i, pozycja, pojemnosc));
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


    @Override
    public String toString() {
        return "Mapa o wymiarach: " + szerokosc + " x " + dlugosc +
                "\nLiczba magazynow: " + magazyny.size() +
                "\nLiczba punktow dostawy: " + punktyDostawy.size();
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
