package dostawa;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PanelSymulacji extends JPanel {
    private final Symulacja symulacja;

    private final int MAKSYMALNA_SZEROKOSC = 900;
    private final int MAKSYMALNA_WYSOKOSC = 700;
    private int rozmiarKomorki;

    // Obrazki dla elementow symulacji
    private BufferedImage obrazMagazyn;
    private BufferedImage obrazPunktDostawy;
    private BufferedImage obrazPojazdMaly;
    private BufferedImage obrazPojazdSredni;
    private BufferedImage obrazPojazdDuzy;

    public PanelSymulacji(Symulacja symulacja) {
        this.symulacja = symulacja;
        int szerokosc = symulacja.getMapa().getSzerokosc();
        int wysokosc = symulacja.getMapa().getDlugosc();

        // Obliczanie rozmiaru komorki tak, aby mapa miescila sie w panelu
        int komorkaSzerokosc = MAKSYMALNA_SZEROKOSC / szerokosc;
        int komorkaWysokosc = MAKSYMALNA_WYSOKOSC / wysokosc;
        rozmiarKomorki = Math.max(15, Math.min(komorkaSzerokosc, komorkaWysokosc));

        int szerokoscPanelu = szerokosc * rozmiarKomorki;
        int wysokoscPanelu = wysokosc * rozmiarKomorki;
        setPreferredSize(new Dimension(szerokoscPanelu, wysokoscPanelu));
        zaladujObrazki();
    }

    private void zaladujObrazki() {
        try {
            obrazMagazyn = ImageIO.read(getClass().getResource("/warehouse.png"));
            obrazPunktDostawy = ImageIO.read(getClass().getResource("/deliveryPoint.png"));
            obrazPojazdMaly = ImageIO.read(getClass().getResource("/smallCar.png"));
            obrazPojazdSredni = ImageIO.read(getClass().getResource("/mediumCar.png"));
            obrazPojazdDuzy = ImageIO.read(getClass().getResource("/largeCar.png"));
        } catch (IOException | IllegalArgumentException ex) {}
    }

    @Override
    protected void paintComponent(Graphics grafika) {
        super.paintComponent(grafika);
        int szerokosc = symulacja.getMapa().getSzerokosc();
        int wysokosc = symulacja.getMapa().getDlugosc();


        // Rysowanie siatki
        grafika.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= szerokosc; i++)
            grafika.drawLine(i * rozmiarKomorki, 0, i * rozmiarKomorki, wysokosc * rozmiarKomorki);
        for (int j = 0; j <= wysokosc; j++)
            grafika.drawLine(0, j * rozmiarKomorki, szerokosc * rozmiarKomorki, j * rozmiarKomorki);

        // Rysowanie magazynow
        for (Magazyn m : symulacja.getMapa().getMagazyny()) {
            Pozycja pos = m.getPozycja();
            if (obrazMagazyn != null)
                grafika.drawImage(obrazMagazyn, pos.getX() * rozmiarKomorki, pos.getY() * rozmiarKomorki, rozmiarKomorki, rozmiarKomorki, this);
        }

        // Rysowanie punktow dostawy
        for (PunktDostawy punktDostawy : symulacja.getMapa().getPunktyDostawy()) {
            Pozycja pozycja = punktDostawy.getPozycja();
            if (obrazPunktDostawy != null)
                grafika.drawImage(obrazPunktDostawy, pozycja.getX() * rozmiarKomorki, pozycja.getY() * rozmiarKomorki, rozmiarKomorki, rozmiarKomorki, this);
        }

        // Rysowanie pojazdow
        for (Pojazd pojazd : symulacja.getPojazdy()) {
            Pozycja pozycja = pojazd.getPozycja();
            BufferedImage obrazek = obrazPojazdu(pojazd);
            if (obrazek != null)
                grafika.drawImage(obrazek, pozycja.getX() * rozmiarKomorki, pozycja.getY() * rozmiarKomorki, rozmiarKomorki, rozmiarKomorki, this);
            else {
                switch (pojazd.getSymbol()) {
                    case 'm': grafika.setColor(Color.GREEN); break;
                    case 's': grafika.setColor(Color.ORANGE); break;
                    case 'd': grafika.setColor(Color.BLACK); break;
                    default:  grafika.setColor(Color.GRAY);
                }
                grafika.fillOval(pozycja.getX() * rozmiarKomorki + rozmiarKomorki / 4, pozycja.getY() * rozmiarKomorki + rozmiarKomorki / 4,
                        rozmiarKomorki / 2, rozmiarKomorki / 2);
            }
        }
    }

    private BufferedImage obrazPojazdu(Pojazd pojazd) {
        switch (pojazd.getSymbol()) {
            case 'm': return obrazPojazdMaly;
            case 's': return obrazPojazdSredni;
            case 'd': return obrazPojazdDuzy;
            default:  return null;
        }
    }
}
