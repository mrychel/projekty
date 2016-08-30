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
public class Scena {
    ArrayList<Krawedz3D> krawedzie;
    ArrayList<Sciana> sciany;
    Macierz macierz;
    WirtualnaKamera wk;
    int numerOstatniejSciany;
    

    public Scena(ArrayList<Krawedz3D> krawedzie, Macierz macierz) {
        this.krawedzie = krawedzie;
        this.macierz = macierz;
        numerOstatniejSciany = 0;
        sciany = new ArrayList<Sciana>();
    }

    public ArrayList<Krawedz3D> nowyProstopadloscian(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color kolor) {
        ArrayList<Krawedz3D> krawedzie = new ArrayList<Krawedz3D>();
        Punkt3D pkt1 = new Punkt3D(minX, minY, minZ);
        Punkt3D pkt2 = new Punkt3D(maxX, minY, minZ);
        Punkt3D pkt3 = new Punkt3D(maxX, minY, maxZ);
        Punkt3D pkt4 = new Punkt3D(minX, minY, maxZ);
        Punkt3D pkt5 = new Punkt3D(minX, maxY, minZ);
        Punkt3D pkt6 = new Punkt3D(maxX, maxY, minZ);
        Punkt3D pkt7 = new Punkt3D(maxX, maxY, maxZ);
        Punkt3D pkt8 = new Punkt3D(minX, maxY, maxZ);
        
        //sciany maja punkty zorientowane zgodnie z ruchem wskazowek zegara
        //normalne scian sa skierowane na zewnatrz bryly
        
        Sciana scianaA = new Sciana(pkt1, pkt2, pkt3, pkt4, kolor);     //dolna podstawa
        Sciana scianaB = new Sciana(pkt8, pkt7, pkt6, pkt5, kolor);     //gorna podstawa
        Sciana scianaC = new Sciana(pkt5, pkt6, pkt2, pkt1, kolor);     //przednia sciana
        Sciana scianaD = new Sciana(pkt6, pkt7, pkt3, pkt2, kolor);     //prawa sciana
        Sciana scianaE = new Sciana(pkt7, pkt8, pkt4, pkt3, kolor);     //tylna sciana
        Sciana scianaF = new Sciana(pkt8, pkt5, pkt1, pkt4, kolor);     //lewa sciana
        
        sciany.add(scianaA);
        sciany.add(scianaB);
        sciany.add(scianaC);
        sciany.add(scianaD);
        sciany.add(scianaE);
        sciany.add(scianaF);


        Krawedz3D kra = new Krawedz3D(pkt1, pkt2, sciany.indexOf(scianaA), sciany.indexOf(scianaC));
        Krawedz3D krb = new Krawedz3D(pkt2, pkt3, sciany.indexOf(scianaA), sciany.indexOf(scianaD));
        Krawedz3D krc = new Krawedz3D(pkt3, pkt4, sciany.indexOf(scianaA), sciany.indexOf(scianaE));
        Krawedz3D krd = new Krawedz3D(pkt4, pkt1, sciany.indexOf(scianaA), sciany.indexOf(scianaF));
        
        Krawedz3D kre = new Krawedz3D(pkt5, pkt6, sciany.indexOf(scianaB), sciany.indexOf(scianaC));
        Krawedz3D krf = new Krawedz3D(pkt6, pkt7, sciany.indexOf(scianaB), sciany.indexOf(scianaD));
        Krawedz3D krg = new Krawedz3D(pkt7, pkt8, sciany.indexOf(scianaB), sciany.indexOf(scianaE));
        Krawedz3D krh = new Krawedz3D(pkt8, pkt5, sciany.indexOf(scianaB), sciany.indexOf(scianaF));
        
        Krawedz3D kri = new Krawedz3D(pkt1, pkt5, sciany.indexOf(scianaC), sciany.indexOf(scianaF));
        Krawedz3D krj = new Krawedz3D(pkt2, pkt6, sciany.indexOf(scianaC), sciany.indexOf(scianaD));
        Krawedz3D krk = new Krawedz3D(pkt3, pkt7, sciany.indexOf(scianaD), sciany.indexOf(scianaE));
        Krawedz3D krl = new Krawedz3D(pkt4, pkt8, sciany.indexOf(scianaE), sciany.indexOf(scianaF));
        
//        sciany.get(sciany.indexOf(scianaA)).krawedz1 = kra;
//        sciany.get(sciany.indexOf(scianaA)).krawedz2 = krb;
//        sciany.get(sciany.indexOf(scianaA)).krawedz3 = krc;
//        sciany.get(sciany.indexOf(scianaA)).krawedz4 = krd;
//        
//        sciany.get(sciany.indexOf(scianaB)).krawedz1 = kre;
//        sciany.get(sciany.indexOf(scianaB)).krawedz2 = krf;
//        sciany.get(sciany.indexOf(scianaB)).krawedz3 = krg;
//        sciany.get(sciany.indexOf(scianaB)).krawedz4 = krh;
//        
//        sciany.get(sciany.indexOf(scianaC)).krawedz1 = kra;
//        sciany.get(sciany.indexOf(scianaC)).krawedz2 = krj;
//        sciany.get(sciany.indexOf(scianaC)).krawedz3 = kre;
//        sciany.get(sciany.indexOf(scianaC)).krawedz4 = kri;
//        
//        sciany.get(sciany.indexOf(scianaD)).krawedz1 = kre;
//        sciany.get(sciany.indexOf(scianaD)).krawedz2 = krf;
//        sciany.get(sciany.indexOf(scianaD)).krawedz3 = krg;
//        sciany.get(sciany.indexOf(scianaD)).krawedz4 = krh;
        
        krawedzie.add(kra);
        krawedzie.add(krb);
        krawedzie.add(krc);
        krawedzie.add(krd);
        
        krawedzie.add(kre);
        krawedzie.add(krf);
        krawedzie.add(krg);
        krawedzie.add(krh);
        
        krawedzie.add(kri);
        krawedzie.add(krj);
        krawedzie.add(krk);
        krawedzie.add(krl);
        
        
        /*
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, minY, minZ), new Punkt3D(maxX, minY, minZ) )); //a
        krawedzie.add(new Krawedz3D(new Punkt3D(maxX, minY, minZ), new Punkt3D(maxX, minY, maxZ) )); //b
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, minY, maxZ), new Punkt3D(maxX, minY, maxZ))); //c
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, minY, minZ), new Punkt3D(minX, minY, maxZ))); //d

        
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, maxY, minZ), new Punkt3D(maxX, maxY, minZ))); //e
        krawedzie.add(new Krawedz3D(new Punkt3D(maxX, maxY, minZ), new Punkt3D(maxX, maxY, maxZ))); //f
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, maxY, maxZ), new Punkt3D(maxX, maxY, maxZ))); //g
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, maxY, minZ), new Punkt3D(minX, maxY, maxZ))); //h
        
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, minY, minZ), new Punkt3D(minX, maxY, minZ))); //i
        krawedzie.add(new Krawedz3D(new Punkt3D(maxX, minY, minZ), new Punkt3D(maxX, maxY, minZ))); //j
        krawedzie.add(new Krawedz3D(new Punkt3D(maxX, minY, maxZ), new Punkt3D(maxX, maxY, maxZ))); //k
        krawedzie.add(new Krawedz3D(new Punkt3D(minX, minY, maxZ), new Punkt3D(minX, maxY, maxZ))); //l
        */
        
        return krawedzie;
        
    }
    
    public void dodajNowyProstopadloscian(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color kolor) {
        this.krawedzie.addAll(this.nowyProstopadloscian(minX, minY, minZ, maxX, maxY, maxZ, kolor));
        
    }

    @Override
    public String toString() {
        return "Scena{" + "krawedzie=" + krawedzie + ", sciany=" + sciany + '}';
    }
    
    public static ArrayList<Sciana> backfaceCulling(ArrayList<Sciana> sciany) {
        ArrayList<Sciana> noweSciany = new ArrayList<Sciana>();
        for (Sciana s : sciany) {
            double wsp = s.A * s.pkt1.x + s.B * s.pkt1.y + s.C * s.pkt1.z;
            if (wsp < 0) {
                noweSciany.add(s);
            }
        }
        
        
        
        return noweSciany;
    }
}
