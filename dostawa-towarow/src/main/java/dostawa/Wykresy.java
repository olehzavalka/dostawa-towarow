package dostawa;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import java.io.*;
import java.awt.*;

public class Wykresy {

    // Ustawienia wygladu wykresu
    private static void ustawWygladWykresu(JFreeChart wykres) {
        wykres.setBackgroundPaint(Color.WHITE);
        wykres.getPlot().setBackgroundPaint(Color.WHITE);

        org.jfree.chart.plot.XYPlot plot = (org.jfree.chart.plot.XYPlot) wykres.getPlot();
        org.jfree.chart.renderer.xy.XYLineAndShapeRenderer renderer = (org.jfree.chart.renderer.xy.XYLineAndShapeRenderer) plot.getRenderer();

        for (int i = 0; i < plot.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new BasicStroke(2.5f));
        }
        renderer.setDefaultShapesVisible(false);

        // Linie siatki
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Linia pozioma dla zera
        plot.setRangeZeroBaselineVisible(true);
        plot.setRangeZeroBaselinePaint(Color.BLACK);
        plot.setRangeZeroBaselineStroke(new BasicStroke(3.0f));
    }


    // WYKRES 1: saldo trzech typów pojazdów w kolejnych epokach (krokach pojazdow) ---
    public static void wykresSaldoPojazdow() {
        XYSeries malyPojazd = new XYSeries("Saldo małych pojazdów");
        XYSeries sredniPojazd = new XYSeries("Saldo średnich pojazdów");
        XYSeries duzyPojazd = new XYSeries("Saldo dużych pojazdów");

        wczytajDaneSalda("pojazdy_Maly.csv", malyPojazd);
        wczytajDaneSalda("pojazdy_Sredni.csv", sredniPojazd);
        wczytajDaneSalda("pojazdy_Duzy.csv", duzyPojazd);

        XYSeriesCollection dane = new XYSeriesCollection();
        dane.addSeries(malyPojazd);
        dane.addSeries(sredniPojazd);
        dane.addSeries(duzyPojazd);

        JFreeChart wykres = ChartFactory.createXYLineChart(
                "Saldo pojazdów w zależności od typu w kolejnych epokach",
                "Epoka",
                "Saldo (PLN)",
                dane,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ustawWygladWykresu(wykres);

        try {
            ChartUtils.saveChartAsPNG(new File("wykres_pojazdy_saldo.png"), wykres, 900, 600);
            System.out.println("Wykres 1: wygenerowano wykres_pojazdy_saldo.png");
        } catch (IOException e) {}
    }

    // WYKRES 2: koszty, zarobki, saldo punktow dostawy
    public static void wykresFinansePunktowDostawy() {
        XYSeries koszty = new XYSeries("Koszty punktów");
        XYSeries zarobki = new XYSeries("Zarobki punktów");
        XYSeries saldo = new XYSeries("Saldo punktów");

        wczytajDaneFinanse("punkty_dostawy.csv", koszty, zarobki, saldo);

        XYSeriesCollection dane = new XYSeriesCollection();
        dane.addSeries(koszty);
        dane.addSeries(zarobki);
        dane.addSeries(saldo);

        JFreeChart wykres = ChartFactory.createXYLineChart(
                "Finanse punktów dostawy w kolejnych epokach",
                "Epoka",
                "Wartość (PLN)",
                dane,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ustawWygladWykresu(wykres);

        try {
            ChartUtils.saveChartAsPNG(new File("wykres_punkty_dostawy_finanse.png"), wykres, 900, 600);
            System.out.println("Wykres 2: wygenerowano wykres_punkty_dostawy_finanse.png");
        } catch (IOException e) {}
    }

    // WYKRES 3: koszty, zarobki, saldo magazynow
    public static void wykresFinanseMagazynow() {
        XYSeries koszty = new XYSeries("Koszty magazynów");
        XYSeries zarobki = new XYSeries("Zarobki magazynów");
        XYSeries saldo = new XYSeries("Saldo magazynów");

        wczytajDaneFinanse("magazyny.csv", koszty, zarobki, saldo);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(koszty);
        dataset.addSeries(zarobki);
        dataset.addSeries(saldo);

        JFreeChart wykres = ChartFactory.createXYLineChart(
                "Finanse magazynów w kolejnych epokach",
                "Epoka",
                "Wartość (PLN)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ustawWygladWykresu(wykres);

        try {
            ChartUtils.saveChartAsPNG(new File("wykres_magazyny_finanse.png"), wykres, 900, 600);
            System.out.println("Wykres 3: wygenerowano wykres_magazyny_finanse.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Wczytywanie danych
    private static void wczytajDaneSalda(String plik, XYSeries seria) {
        try (BufferedReader br = new BufferedReader(new FileReader(plik))) {
            String line;
            boolean pierwszy = true;

            while ((line = br.readLine()) != null) {
                if (pierwszy) { pierwszy = false; continue; }
                String[] dane = line.split(";");
                int epoka = Integer.parseInt(dane[0]);
                double saldo = Double.parseDouble(dane[3].replace(",", "."));
                seria.add(epoka, saldo);
            }

        } catch (Exception e) {}
    }

    private static void wczytajDaneFinanse(String plik, XYSeries koszty, XYSeries zarobki, XYSeries saldo) {
        try (BufferedReader br = new BufferedReader(new FileReader(plik))) {
            String line;
            boolean pierwszy = true;

            while ((line = br.readLine()) != null) {
                if (pierwszy) { pierwszy = false; continue; }
                String[] dane = line.split(";");
                int epoka = Integer.parseInt(dane[0]);
                double koszt = Double.parseDouble(dane[1].replace(",", "."));
                double zarobek = Double.parseDouble(dane[2].replace(",", "."));
                double saldp = Double.parseDouble(dane[3].replace(",", "."));
                koszty.add(epoka, koszt);
                zarobki.add(epoka, zarobek);
                saldo.add(epoka, saldp);
            }

        } catch (Exception e) {}
    }


    // Metoda do generowania wszystkich wykresow
    public static void generujWszystkieWykresy() {
        wykresSaldoPojazdow();
        wykresFinansePunktowDostawy();
        wykresFinanseMagazynow();
        System.out.println("Wszystkie wykresy zostaly wygenerowane.");
    }
}
