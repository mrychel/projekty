/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

import java.util.ArrayList;

/**
 *
 * @author rafal
 */
public class Widok {
    int wysokosc;
    int szerokosc;
    
    ArrayList<Krawedz2D> krawedzieNaWidoku;
    Kamera kamera;

    public Widok(int wysokosc, int szerokosc, Kamera kamera) {
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        this.kamera = kamera;
        
        this.krawedzieNaWidoku = przesunKrawedzieDoWidoku(kamera.krawedzieNaKamerze);
    }



    public Widok(int wysokosc, int szerokosc, ArrayList<Krawedz3D> krawedzieNaKamerze, Kamera kamera) {
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        this.krawedzieNaWidoku = przesunKrawedzieDoWidoku(krawedzieNaKamerze);
        this.kamera = kamera;
    }
    

    Punkt2D przesunPunktDoWidoku(Punkt3D p) {
//        System.out.println(p.x + "\t" + p.y);
        int x = (int) ((p.x - kamera.x_min) * szerokosc / (kamera.x_max - kamera.x_min));
//        int y = (int) ((p.y - kamera.y_min) * wysokosc / (kamera.y_max - kamera.y_min));    //zamienic wsp - os oy w druga strone
        int y = wysokosc - ((int) ((p.y - kamera.y_min) * wysokosc / (kamera.y_max - kamera.y_min)));   //zamienione
        return new Punkt2D(x, y, p.z);        
    }
    
    Krawedz2D przesunKrawedzDoWidoku(Krawedz3D kr) {
        return new Krawedz2D(przesunPunktDoWidoku(kr.punkt1), przesunPunktDoWidoku(kr.punkt2), kr.nr_sciany1, kr.nr_sciany2);
    }
    
    ArrayList<Krawedz2D> przesunKrawedzieDoWidoku(ArrayList<Krawedz3D> krawedzieNaKamerze) {
        ArrayList<Krawedz2D> knw = new ArrayList<Krawedz2D>();
        
        for (Krawedz3D kr : krawedzieNaKamerze) {
            knw.add(przesunKrawedzDoWidoku(kr));
        }
        return knw;
        
    }

    public ArrayList<Krawedz2D> getKrawedzieNaWidoku() {
        return krawedzieNaWidoku;
    }
    
    public String wypiszKrawedzieNaWidoku() {
        String ret = new String();
        
        for (Krawedz2D kr :krawedzieNaWidoku) {
            ret += kr.toString();
            ret += "\n";
        }
        
        return ret;
    }
    
    public double[] wrocWspolrzedneDoKamery(double x, double y) {
        double ret[] = new double[2];
        ret[0] = x * (kamera.x_max - kamera.x_min) / szerokosc + kamera.x_min;      //wsp x
//        ret[1] = kamera.y_min - (x - wysokosc) * (kamera.y_max - kamera.y_min) / wysokosc;      //wsp y
//        ret[1] = y * (kamera.y_max - kamera.y_min) / wysokosc + kamera.y_min;
        ret[1] = (wysokosc - y) * (kamera.y_max - kamera.y_min) / wysokosc + kamera.y_min;
        
        return ret;
        
    }
    
}
    
