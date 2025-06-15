package dostawa;

import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class StatystykiCSV {
    private final String PLIK_MAGAZYNY = "magazyny.csv";
    private final String PLIK_PUNKTY = "punkty_dostawy.csv";
    private Set<String> utworzonePlikiTypow = new HashSet<>();
    private boolean plikiUtworzone = false;


    public void utworzPliki() {
        if (plikiUtworzone)
            return;

        try {
            PrintWriter magazyn = new PrintWriter(PLIK_MAGAZYNY);
            magazyn.println("Epoka;Koszty;Zarobki;Saldo");
            magazyn.close();

            PrintWriter pkt = new PrintWriter(PLIK_PUNKTY);
            pkt.println("Epoka;Koszty;Zarobki;Saldo");
            pkt.close();

            plikiUtworzone = true;

        } catch (IOException e) {
            System.out.println("Błąd tworzenia plików sumarycznych: " + e.getMessage());
        }
    }

    // Tworzenie pliku dla danego typu pojazdu
    private void utworzPlikTypuPojazdu(String typ) {
        String nazwaPliku = "pojazdy_" + typ + ".csv";

        if (utworzonePlikiTypow.contains(nazwaPliku))
            return;

        try {
            PrintWriter out = new PrintWriter(nazwaPliku);
            out.println("Epoka;Koszty;Zarobki;Saldo");
            out.close();
            utworzonePlikiTypow.add(nazwaPliku);

        } catch (IOException e) {
            System.out.println("Blad tworzenia pliku dla pojazdu typu " + typ + ": " + e.getMessage());
        }
    }


    private String dwaMiejscaPoPrzecinku(double liczba) {
        return String.format("%.2f", liczba);
    }


    public void zapisz(int epoka, List<Magazyn> magazyny, List<PunktDostawy> punkty, List<Pojazd> pojazdy) {
        utworzPliki();

        // Sumowanie kosztow magazynow
        double sumaKosztowMag = 0, sumaZarobkowMag = 0, sumaSaldaMag = 0;

        for (Magazyn magazyn : magazyny) {
            sumaKosztowMag += magazyn.getSumaKosztow();
            sumaZarobkowMag += magazyn.getSumaZarobkow();
            sumaSaldaMag += magazyn.getSaldo();
        }

        try (PrintWriter mag = new PrintWriter(new FileWriter(PLIK_MAGAZYNY, true))) {
            mag.println(epoka + ";" + dwaMiejscaPoPrzecinku(sumaKosztowMag) + ";" +
                    dwaMiejscaPoPrzecinku(sumaZarobkowMag) + ";" + dwaMiejscaPoPrzecinku(sumaSaldaMag));

        } catch (IOException e) {
            System.out.println("Blad zapisu magazynow: " + e.getMessage());
        }



        // Sumowanie kosztow dla punktow dostawy
        double sumaKosztowPkt = 0, sumaZarobkowPkt = 0, sumaSaldaPkt = 0;

        for (PunktDostawy punktDostawy : punkty) {
            sumaKosztowPkt += punktDostawy.getSumaKosztow();
            sumaZarobkowPkt += punktDostawy.getSumaZarobkow();
            sumaSaldaPkt += punktDostawy.getSaldo();
        }

        try (PrintWriter pkt = new PrintWriter(new FileWriter(PLIK_PUNKTY, true))) {
            pkt.println(epoka + ";" + dwaMiejscaPoPrzecinku(sumaKosztowPkt) + ";" +
                    dwaMiejscaPoPrzecinku(sumaZarobkowPkt) + ";" + dwaMiejscaPoPrzecinku(sumaSaldaPkt));

        } catch (IOException e) {
            System.out.println("Blad zapisu punktow: " + e.getMessage());
        }

        // Sumowanie kosztow pojazdow po typie
        Map<String, double[]> sumaTypPojazdu = new HashMap<>();
        for (Pojazd pojazd : pojazdy) {
            String typ = pojazd.getTyp();
            double[] suma = sumaTypPojazdu.getOrDefault(typ, new double[3]);

            suma[0] += pojazd.getSumaKosztow();
            suma[1] += pojazd.getSumaZarobkow();
            suma[2] += pojazd.getSaldo();

            sumaTypPojazdu.put(typ, suma);
        }

        // Tworznie pliku dla kazdego typu pojazdu
        for (String typPojazdu : sumaTypPojazdu.keySet()) {
            utworzPlikTypuPojazdu(typPojazdu);
            String nazwaPliku = "pojazdy_" + typPojazdu + ".csv";
            double[] suma = sumaTypPojazdu.get(typPojazdu);

            try (PrintWriter pojazd = new PrintWriter(new FileWriter(nazwaPliku, true))) {
                pojazd.println(epoka + ";" + dwaMiejscaPoPrzecinku(suma[0]) + ";"
                        + dwaMiejscaPoPrzecinku(suma[1]) + ";" + dwaMiejscaPoPrzecinku(suma[2]));

            } catch (IOException e) {
                System.out.println("Blad zapisu pojazdow typu " + typPojazdu + ": " + e.getMessage());
            }
        }
    }
}
