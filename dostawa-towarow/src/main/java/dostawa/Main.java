package dostawa;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj szerokosc mapy: ");
        int szerokosc = scanner.nextInt();
        System.out.print("Podaj dlugosc mapy: ");
        int dlugosc = scanner.nextInt();

        System.out.print("Podaj liczbe magazynow: ");
        int liczbaMagazynow = scanner.nextInt();

        System.out.print("Podaj liczbe punktow dostawy: ");
        int liczbaPunktowDostawy = scanner.nextInt();

        System.out.print("Podaj liczbe malych pojazdow: ");
        int liczbaMalych = scanner.nextInt();

        System.out.print("Podaj liczbe srednich pojazdow: ");
        int liczbaSrednich = scanner.nextInt();

        System.out.print("Podaj liczbe duzych pojazdow: ");
        int liczbaDuzych = scanner.nextInt();

        Symulacja symulacja = new Symulacja(szerokosc, dlugosc, liczbaMagazynow, liczbaPunktowDostawy, liczbaMalych, liczbaSrednich, liczbaDuzych);

        symulacja.wyswietlMape();
        System.out.println(symulacja);
        System.out.println("\n");

        // Wyswietlanie informacji o punktach dostawy
        // symulacja.getMapa().wyswietlPunktyDostawy();

        List<Zamowienie> zamowienia = symulacja.generujZamowienia();
        for (Zamowienie zam : zamowienia) {
            System.out.println("Punkt dostawy ID " + zam.getPunktDostawy().getId() +
                    " (" + zam.getPunktDostawy().getPozycja().getX() + ", " + zam.getPunktDostawy().getPozycja().getY() + ")" +
                    " zamawia " + zam.getIlosc() + " jednostek towaru");
        }

        for (Zamowienie zam : zamowienia) {
            Magazyn magazyn = symulacja.znajdzNajblizszyMagazyn(zam.getPunktDostawy().getPozycja(), zam.getIlosc());
            if (magazyn != null) {
                Pojazd pojazd = magazyn.znajdzNajlepszyPojazd(symulacja.getPojazdy(), zam.getIlosc(), zam.getPunktDostawy().getPozycja());
                if (pojazd != null) {
                    Pozycja pozycjaPojazduPrzed = pojazd.getPozycja();
                    magazyn.ladujPojazdIMarsz(pojazd, magazyn, zam);
                    System.out.println("Pojazd ID " + pojazd.getId() +
                            " (" + pozycjaPojazduPrzed.getX() + ", " + pozycjaPojazduPrzed.getY() + ")" +
                            " dostal zlecenie od magazynu ID " + magazyn.getId() +
                            " (" + magazyn.getPozycja().getX() + ", " + magazyn.getPozycja().getY() + ")" +
                            " i dostarczyl " + zam.getIlosc() + " jednostek towaru do punktu " +
                            zam.getPunktDostawy().getId());
                } else {
                    System.out.println("Brak wolnego pojazdu dla zamowienia do punktu " + zam.getPunktDostawy().getId());
                }
            } else {
                System.out.println("Nie znaleziono magazynu dla zamowienia do punktu " + zam.getPunktDostawy().getId());
            }
        }
    }
}