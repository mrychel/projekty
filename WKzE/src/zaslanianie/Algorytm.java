/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zaslanianie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import wirtualnakamera.Krawedz2D;
import wirtualnakamera.Sciana;
import wirtualnakamera.Widok;
import zaslanianie.ListaWielokatow.Wielokat;
import zaslanianie.TablicaKrawedziAktywnych.AktywnaKrawedz;

/**
 *
 * @author rafal
 */
public class Algorytm {

    ArrayList<Krawedz2D> krawedzie;
    ArrayList<Sciana> sciany;
    int wysokosc, szerokosc;
    Color kolorTla;
    public TablicaKrawedzi tablicaKrawedzi;
    public TablicaKrawedziAktywnych tablicaKrawedziAktywnych;
    ListaWielokatow listaWielokatow;
    public double d;
    Widok widok;

    public Algorytm(ArrayList<Krawedz2D> krawedzie, ArrayList<Sciana> sciany, int wysokosc, int szerokosc, Color kolorTla, double d, Widok widok) {
        this.krawedzie = krawedzie;
        this.sciany = sciany;
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        this.kolorTla = kolorTla;
        this.tablicaKrawedzi = new TablicaKrawedzi(krawedzie, wysokosc);
        this.tablicaKrawedziAktywnych = new TablicaKrawedziAktywnych(this.tablicaKrawedzi, wysokosc);
        this.listaWielokatow = new ListaWielokatow(sciany);
        this.d = d;
        this.widok = widok;

    }

    public ArrayList<Segment> przygotujListeSegmentow(int y, List<AktywnaKrawedz> aktywneKrawedzie) {
        ArrayList<Segment> listaSegmentow = new ArrayList<Segment>();
        float xLewe = 0;
        float xPrawe;

        if (aktywneKrawedzie.isEmpty()) {
            xPrawe = szerokosc - 1;
        } else {
            xPrawe = aktywneKrawedzie.get(0).x_przeciecia;
        }


        listaSegmentow.add(new Segment(y, xLewe, xPrawe, new ArrayList<Wielokat>()));


        for (Wielokat w : listaWielokatow.lista) {
            w.we_wy = false;
        }

        
        
   
        for (AktywnaKrawedz ak : aktywneKrawedzie) {
            xLewe = ak.x_przeciecia;
            if (aktywneKrawedzie.indexOf(ak) == aktywneKrawedzie.size() - 1) {
                xPrawe = szerokosc - 1;
            } else {
                xPrawe = aktywneKrawedzie.get(aktywneKrawedzie.indexOf(ak) + 1).x_przeciecia;
            }

            listaWielokatow.lista.get(ak.nr_wielokata1).we_wy = !listaWielokatow.lista.get(ak.nr_wielokata1).we_wy;


            ArrayList<ListaWielokatow.Wielokat> listaToogleTrue = new ArrayList<Wielokat>();
            for (Wielokat w : listaWielokatow.lista) {
                if (w.we_wy) {
                    listaToogleTrue.add(w);
                }
            }


            Segment seg = new Segment(y, xLewe, xPrawe, listaToogleTrue);

            listaSegmentow.add(seg);
        }


        return listaSegmentow;
    }

    public BuforEkranu eliminuj() {
        BuforEkranu buforEkranu = new BuforEkranu(szerokosc, wysokosc, kolorTla);

        for (int y = 0; y < wysokosc; y++) {
            List<AktywnaKrawedz> aktywneKrawedzie = tablicaKrawedziAktywnych.NastepneAktywneKrawedzie(y);
            ArrayList<Segment> listaSegmentow = przygotujListeSegmentow(y, aktywneKrawedzie);

            for (Segment seg : listaSegmentow) {
                buforEkranu.pomaluj(y, Math.round(seg.xLewy), Math.round(seg.xPrawy), seg.kolor);
//                System.out.println(seg.toString());
            }
//            System.out.println("\n");

        }


        return buforEkranu;
    }

    public Color kolorWidocznejSciany(double x, int y, ArrayList<ListaWielokatow.Wielokat> wielokaty) {
        if (wielokaty.isEmpty()) {
            return kolorTla;
        }

        // System.out.print("Dla x= " + x + ", y= " + y + ": ");

        double minimum_z;
//        minimum_z = wielokaty.get(0).rozwiarzDla(x, y);

        double wspPunktuNaWidoku[] = widok.wrocWspolrzedneDoKamery(x, (double) y);
//        System.out.println(x + " ; " + y + " \t" + wspPunktuNaWidoku[0] + "; " + wspPunktuNaWidoku[1]);
//        minimum_z = wielokaty.get(0).rozwiarzDla((int) Math.round(x), y);
//         minimum_z = wielokaty.get(0).wspZPrzecieciaZProsta(0, 0, 0, wspPunktuNaWidoku[0], wspPunktuNaWidoku[1], d);
        minimum_z = wielokaty.get(0).odlegloscPunktuPrzecieciaZProsta(0, 0, 0, wspPunktuNaWidoku[0], wspPunktuNaWidoku[1], d);

        Color kolor = wielokaty.get(0).kolor;
        for (ListaWielokatow.Wielokat w : wielokaty) {

//            if 
////                    (w.rozwiarzDla(x, y) < minimum_z) 
//                    (w.wspZPrzecieciaZProsta(0, 0, 0, wspPunktuNaWidoku[0], wspPunktuNaWidoku[1], d) < minimum_z)
//            {
//                kolor = w.kolor;
//            }
            //System.out.println(minimum_z + "  ");

//            double z = w.wspZPrzecieciaZProsta(0, 0, 0, wspPunktuNaWidoku[0], wspPunktuNaWidoku[1], d);
            double z = w.odlegloscPunktuPrzecieciaZProsta(0, 0, 0, wspPunktuNaWidoku[0], wspPunktuNaWidoku[1], d);
//            double z = w.rozwiarzDla((int) Math.round(x), y);
//            System.out.print(z + ": " + w.kolor + "  ");
            if (z < minimum_z) {
                minimum_z = z;
                kolor = w.kolor;
            }
        }

        //System.out.println("\n");
        return kolor;
    }

    public class Segment {

        int y;
        float xLewy;
        float xPrawy;
        ArrayList<Wielokat> listaWielokatowAktywnych;
        Color kolor;

        @Override
        public String toString() {
            return "Segment{" + "y=" + y + ", xLewy=" + xLewy + ", xPrawy=" + xPrawy + ", ileWA=" + listaWielokatowAktywnych.size() + ", kolor=" + getColor(kolor) + '}';
        }

        public Segment(int y, float xLewy, float xPrawy, ArrayList<Wielokat> listaWielokatowAktywnych) {
            this.y = y;
            this.xLewy = xLewy;
            this.xPrawy = xPrawy;
            this.listaWielokatowAktywnych = listaWielokatowAktywnych;
            this.kolor = kolorWidocznejSciany((xLewy + xPrawy) / 2, y, listaWielokatowAktywnych);

        }
    }

    String getColor(Color kolor) {


        if (kolor == (Color.BLUE)) {
            return "BLUE";
        } else if (kolor == Color.CYAN) {
            return "CYAN";
        } else if (kolor == Color.GRAY) {
            return "GRAY";
        } else if (kolor == Color.GREEN) {
            return "GREEN";
        } else if (kolor == Color.MAGENTA) {
            return "MAGENTA";
        } else if (kolor == Color.ORANGE) {
            return "ORANGE";
        } else if (kolor == Color.PINK) {
            return "PINK";
        } else if (kolor == Color.YELLOW) {
            return "YELLOW";
        } else {
            return kolor.toString();
        }


    }
}
