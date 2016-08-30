/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

import java.awt.Color;
import java.util.ArrayList;
import zaslanianie.Algorytm;
import zaslanianie.BuforEkranu;

/**
 *
 * @author rafal
 */
public class WirtualnaKamera {

    Scena scena;
    public double odl_rzutni;
    double poczatkowa_odl_rzutni;
    Rzutowanie rzut;
    public Kamera kamera;
    public Widok widok;
    int wysokosc;
    int szerokosc;
    //   ArrayList<Krawedz3D> krawedzie;
    static final double KROK_PRZYBLIZANIA = 0.01;
    static final double KROK_TRANSLACJI = 0.5;
    static final double KROK_OBROTU = 0.01;
    public BuforEkranu ekran;
    Color kolorTla = Color.YELLOW;

    public WirtualnaKamera(Scena scena, double odl_rzutni, int wysokosc, int szerokosc) {
        this.poczatkowa_odl_rzutni = odl_rzutni;
        this.scena = scena;
        this.odl_rzutni = odl_rzutni;
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
    }

    public void przetworzScene() {
        /*
         * for (Krawedz3D kr : scena.krawedzie) {
         * kr.punkt1.przemnozPrzezMacierzINormalizuj(scena.macierz);
         * kr.punkt2.przemnozPrzezMacierzINormalizuj(scena.macierz); }
         */
//        ArrayList<Krawedz3D> krawedzie = new ArrayList<Krawedz3D>();
//        for (Krawedz3D kr : scena.krawedzie) {
//
//            Punkt3D pkt1 = new Punkt3D(Macierz.multiply(scena.macierz.macierz, kr.punkt1.wektorWsp()));
//            Punkt3D pkt2 = new Punkt3D(Macierz.multiply(scena.macierz.macierz, kr.punkt2.wektorWsp()));
//            pkt1.normalizuj();
//            pkt2.normalizuj();
//            Krawedz3D krw = new Krawedz3D(pkt1, pkt2, kr.nr_sciany1, kr.nr_sciany2);
//            krawedzie.add(krw);
//
//
//        }
//        
        ArrayList<Sciana> sciany = new ArrayList<Sciana>();
        for (Sciana s : scena.sciany) {         //transformacje scian

            Punkt3D npkt1 = new Punkt3D(Macierz.multiply(scena.macierz.macierz, s.pkt1.wektorWsp()));
            npkt1.normalizuj();
            Punkt3D npkt2 = new Punkt3D(Macierz.multiply(scena.macierz.macierz, s.pkt2.wektorWsp()));
            npkt2.normalizuj();
            Punkt3D npkt3 = new Punkt3D(Macierz.multiply(scena.macierz.macierz, s.pkt3.wektorWsp()));
            npkt3.normalizuj();
            Punkt3D npkt4 = new Punkt3D(Macierz.multiply(scena.macierz.macierz, s.pkt4.wektorWsp()));
            npkt4.normalizuj();
            Sciana ns = new Sciana(npkt1, npkt2, npkt3, npkt4, s.kolor);
            sciany.add(ns);
        }

        sciany = Scena.backfaceCulling(sciany);
//        System.out.println(sciany);
//        ArrayList<Krawedz3D> krawedzie = new ArrayList<Krawedz3D>();


//        rzut = new Rzutowanie(odl_rzutni);
//        kamera = new Kamera(rzut.rzutujKrawedzie(krawedzie));
//        widok = new Widok(wysokosc, szerokosc, kamera);

        rzut = new Rzutowanie(odl_rzutni);
        sciany = rzut.rzutujSciany(sciany);
        kamera = new Kamera();
        sciany = kamera.przytnijScianyDoKamery(sciany);
        widok = new Widok(wysokosc, szerokosc, kamera);


        Algorytm algorytm = new Algorytm(widok.krawedzieNaWidoku, sciany, wysokosc, szerokosc, kolorTla, odl_rzutni, widok);
        ekran = algorytm.eliminuj();
        //System.out.println(getKrawedzieDoNarysowania());
        //  System.out.println(algorytm.tablicaKrawedzi.lista);
        //        for (int i = 0 ; i < algorytm.tablicaKrawedzi.lista.size(); i++) {
        //            System.out.println(Integer.toString(i) + ": " + algorytm.tablicaKrawedzi.lista.get(i).toString() + "\n");
        //        }

    }

    public ArrayList<Krawedz2D> getKrawedzieDoNarysowania() {
        return this.widok.getKrawedzieNaWidoku();
    }

    public BuforEkranu getBuforEkranu() {
        return this.ekran;
    }

    public void przybliz() {
        this.odl_rzutni += KROK_PRZYBLIZANIA;
        przetworzScene();

    }

    public void oddal() {
        if (this.odl_rzutni > KROK_PRZYBLIZANIA) {
            this.odl_rzutni -= KROK_PRZYBLIZANIA;
            przetworzScene();
        }
    }

    public void translacjaMinusOX() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzTranslacji(-1 * KROK_TRANSLACJI, 0, 0), scena.macierz.macierz);
        przetworzScene();

    }

    public void translacjaPlusOX() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzTranslacji(1 * KROK_TRANSLACJI, 0, 0), scena.macierz.macierz);
        przetworzScene();

    }

    public void translacjaMinusOY() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzTranslacji(0, -1 * KROK_TRANSLACJI, 0), scena.macierz.macierz);
        przetworzScene();

    }

    public void translacjaPlusOY() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzTranslacji(0, 1 * KROK_TRANSLACJI, 0), scena.macierz.macierz);
        przetworzScene();

    }

    public void translacjaMinusOZ() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzTranslacji(0, 0, -1 * KROK_TRANSLACJI), scena.macierz.macierz);
        przetworzScene();

    }

    public void translacjaPlusOZ() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzTranslacji(0, 0, 1 * KROK_TRANSLACJI), scena.macierz.macierz);
        przetworzScene();

    }

    public void obrotPlusOX() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzObrOX(KROK_OBROTU), scena.macierz.macierz);
        przetworzScene();
    }

    public void obrotMinusOX() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzObrOX(-1 * KROK_OBROTU), scena.macierz.macierz);
        przetworzScene();
    }

    public void obrotPlusOY() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzObrOY(KROK_OBROTU), scena.macierz.macierz);
        przetworzScene();
    }

    public void obrotMinusOY() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzObrOY(-1 * KROK_OBROTU), scena.macierz.macierz);
        przetworzScene();
    }

    public void obrotPlusOZ() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzObrOZ(KROK_OBROTU), scena.macierz.macierz);
        przetworzScene();
    }

    public void obrotMinusOZ() {
        scena.macierz.macierz = Macierz.multiply(Macierz.macierzObrOZ(-1 * KROK_OBROTU), scena.macierz.macierz);
        przetworzScene();
    }

    public void resetujTransformacje() {
        scena.macierz.macierz = Macierz.macierzJedynkowa();
        this.odl_rzutni = this.poczatkowa_odl_rzutni;
        przetworzScene();
    }
}
