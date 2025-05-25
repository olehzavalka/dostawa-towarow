package dostawa;

import java.util.List;

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

        // Wyswietlanie mapy
        for (int y = 0; y < dlugosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                System.out.print(mapa[y][x] + " ");
            }
            System.out.println();
        }
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
