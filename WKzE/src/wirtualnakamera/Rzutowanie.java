/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author rafal
 */
public class Rzutowanie {
    double d;
    Macierz macierz;
    double z_min = 0.1;

    
            

    public Rzutowanie(double d) {
        this.d = d;
        macierz = new Macierz(Macierz.macierzRzutowania(d));
        //macierz = new Macierz(Macierz.macierzPp(d,z_min));        
    }
    
//    Punkt3D rzutuj_punkt(Punkt3D p) {
//        double[][] wsp = Macierz.multiply(this.macierz.macierz, p.wektorWsp());
//        Punkt3D pkt = new Punkt3D(wsp[0][0], wsp[1][0], wsp[2][0], wsp[3][0]);
//        pkt.normalizuj();
//        return pkt;
//        
//    } 
    
    Punkt3D rzutuj_punkt(Punkt3D p) {
        Punkt3D pkt = new Punkt3D(p.x * this.d / p.z, p.y * this.d / p.z, this.d);
        return pkt;
    }
    
    Krawedz3D rzutuj_krawedz(Krawedz3D kr) {
        return new Krawedz3D(rzutuj_punkt(kr.punkt1), rzutuj_punkt(kr.punkt2), kr.nr_sciany1, kr.nr_sciany2);
        
    }
    
    ArrayList<Krawedz3D> rzutujKrawedzie(ArrayList<Krawedz3D> krawedzie) {
        ArrayList<Krawedz3D> zrzutowaneKrawedzie = new ArrayList<Krawedz3D>();
        for (Krawedz3D kr : krawedzie) {
            zrzutowaneKrawedzie.add(rzutuj_krawedz(kr));
        }
        return zrzutowaneKrawedzie;
    }
    
    ArrayList<Sciana> rzutujSciany(ArrayList<Sciana> sciany) {
        ArrayList<Sciana> zrzutowane = new ArrayList<Sciana>();
        for (Sciana sc : sciany) {
            zrzutowane.add(rzutujSciane(sc));
        }
        return zrzutowane;
    }
    
    Sciana rzutujSciane(Sciana sciana) {
        sciana.krawedz1 = rzutuj_krawedz(sciana.krawedz1);
        sciana.krawedz2 = rzutuj_krawedz(sciana.krawedz2);
        sciana.krawedz3 = rzutuj_krawedz(sciana.krawedz3);
        sciana.krawedz4 = rzutuj_krawedz(sciana.krawedz4);
        return sciana;
    }
}
