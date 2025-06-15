package dostawa;

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

        System.out.print("Podaj liczbe epok symulacji: ");
        int liczbaEpok = scanner.nextInt();

        Symulacja symulacja = new Symulacja(szerokosc, dlugosc, liczbaMagazynow, liczbaPunktowDostawy,
                liczbaMalych, liczbaSrednich, liczbaDuzych
        );

        symulacja.wyswietlMape();
        System.out.println(symulacja);
        System.out.println();

        // Uruchamianie symulacji
        symulacja.uruchomSymulacje(liczbaEpok);
    }
}
