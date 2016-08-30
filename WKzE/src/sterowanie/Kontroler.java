/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sterowanie;

import gui.PanelNaRysunek;
import wirtualnakamera.WirtualnaKamera;

/**
 *
 * @author rafal
 */
public class Kontroler {
    /*
     * public static void rysuj(ArrayList<Krawedz2D> krawedzieDoNarysowania,
     * Color kolor, Graphics2D g) { // Graphics grap = new Graphics();
     *
     * for (Krawedz2D kr : krawedzieDoNarysowania) { g.drawLine(kr.punkt1.x,
     * kr.punkt1.y, kr.punkt2.x, kr.punkt2.y); } }
     */

    PanelNaRysunek rysunek;
    WirtualnaKamera wirtualnaKamera;
    boolean czyRysowacKrawedzie = false;

    public Kontroler(PanelNaRysunek rysunek, WirtualnaKamera wirtualnaKamera) {
        this.rysunek = rysunek;
        this.wirtualnaKamera = wirtualnaKamera;
    }

    public void przybliz() {
        this.wirtualnaKamera.przybliz();
        this.narysujRysunek();
    }

    public void oddal() {
        this.wirtualnaKamera.oddal();
        this.narysujRysunek();
    }

    public void translacjaMinusOX() {
        this.wirtualnaKamera.translacjaMinusOX();
        this.narysujRysunek();
    }

    public void translacjaPlusOX() {
        this.wirtualnaKamera.translacjaPlusOX();
        this.narysujRysunek();
    }

    public void translacjaMinusOY() {
        this.wirtualnaKamera.translacjaMinusOY();
        this.narysujRysunek();
    }

    public void translacjaPlusOY() {
        this.wirtualnaKamera.translacjaPlusOY();
        this.narysujRysunek();
    }

    public void translacjaMinusOZ() {
        this.wirtualnaKamera.translacjaMinusOZ();
        this.narysujRysunek();
    }

    public void translacjaPlusOZ() {
        this.wirtualnaKamera.translacjaPlusOZ();
        this.narysujRysunek();
    }

    public void obrotPlusOX() {
        this.wirtualnaKamera.obrotPlusOX();
        this.narysujRysunek();
    }

    public void obrotMinusOX() {
        this.wirtualnaKamera.obrotMinusOX();
        this.narysujRysunek();
    }

    public void obrotPlusOY() {
        this.wirtualnaKamera.obrotPlusOY();
        this.narysujRysunek();
    }

    public void obrotMinusOY() {
        this.wirtualnaKamera.obrotMinusOY();
        this.narysujRysunek();
    }

    public void obrotPlusOZ() {
        this.wirtualnaKamera.obrotPlusOZ();
        this.narysujRysunek();
    }

    public void obrotMinusOZ() {
        this.wirtualnaKamera.obrotMinusOZ();
        this.narysujRysunek();
    }

    public void resetujTransformacje() {
        this.wirtualnaKamera.resetujTransformacje();
        this.narysujRysunek();
    }
    
    public void wlaczWylaczRysowanieKrawedzi() {
        this.czyRysowacKrawedzie = ! this.czyRysowacKrawedzie;
        this.narysujRysunek();
    }

//    public static void przybliz(PanelNaRysunek rys, WirtualnaKamera wk) {
//        wk.przybliz();
//        przetworzINarysujRysunek(rys, wk);
//    }
//    public static void przetworzINarysujRysunek(PanelNaRysunek rys, WirtualnaKamera wk) {
//        wk.przetworzScene();
//        rys.NarysujKrawedzieNaRysunku(wk.getKrawedzieDoNarysowania());
//      //  System.out.println( wk.widok.wypiszKrawedzieNaWidoku() );
//    }
    public void narysujRysunek() {
        //this.wirtualnaKamera.przetworzScene();

        this.rysunek.NarysujBufferedImage(this.wirtualnaKamera.getBuforEkranu().przeksztalcNaBufferedImage());
        if (czyRysowacKrawedzie) {
            this.rysunek.NarysujKrawedzieNaRysunku(wirtualnaKamera.getKrawedzieDoNarysowania());
        }
        
        //System.out.println(        this.wirtualnaKamera.widok.wypiszKrawedzieNaWidoku() );

    }
//
//    public static void rysujKrawedzieNaRysunku(ArrayList<Krawedz2D> krw, PanelNaRysunek rys) {
////        rys.NarysujKrawedzieNaRysunku(krw);
//    }

    public static void waitMiliSec(long s) {
        try {
            Thread.currentThread().sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
