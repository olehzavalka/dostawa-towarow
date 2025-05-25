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
    }
}