package dostawa;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

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

        System.out.print("Podaj opoznienie między epokami (ms): ");
        int opoznienie = scanner.nextInt();


        java.util.List<Magazyn> magazyny = new java.util.ArrayList<>();
        java.util.List<PunktDostawy> punktyDostawy = new java.util.ArrayList<>();
        java.util.List<Pojazd> pojazdy = new java.util.ArrayList<>();

        // Stworzenie mapy i rozmieszczenie obiektow
        Mapa mapa = new Mapa(szerokosc, dlugosc, magazyny, punktyDostawy);
        mapa.rozmiescObiekty(liczbaMagazynow, liczbaPunktowDostawy, liczbaMalych, liczbaSrednich, liczbaDuzych, pojazdy);

        // Utworzenie symulacji
        Symulacja symulacja = new Symulacja(mapa, pojazdy);

        // Uruchomienie interfejsu graficznego
        uruchomInterfejsGraficzny(symulacja, liczbaEpok, opoznienie);
    }

    private static void uruchomInterfejsGraficzny(Symulacja symulacja, int liczbaEpok, int opoznienie) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Symulacja dostaw");
            PanelSymulacji panel = new PanelSymulacji(symulacja);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            final int[] epoka = {0};
            Timer timer = new Timer(opoznienie, e -> {

                if (epoka[0] >= liczbaEpok) {
                    ((Timer) e.getSource()).stop();
                    // Wygeneruj wykresy po zakończeniu
                    Wykresy.generujWszystkieWykresy();
                    JOptionPane.showMessageDialog(frame, "Symulacja zakończona po "
                            + liczbaEpok + " epokach.\nWykresy zostały wygenerowane.");

                } else {
                    symulacja.wykonajEpoke();
                    symulacja.zapiszStatystykiDoCSV(epoka[0] + 1); // zapisz dane do CSV
                    epoka[0]++;
                    panel.repaint();
                }
            });
            timer.start();
        });
    }
}
