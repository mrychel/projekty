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
public class Sciana {

    public Punkt3D pkt1;
    public Punkt3D pkt2;
    public Punkt3D pkt3;
    public Punkt3D pkt4;
    public Color kolor;
    public Krawedz3D krawedz1;
    public Krawedz3D krawedz2;
    public Krawedz3D krawedz3;
    public Krawedz3D krawedz4;
    public double A;        //wspolczynniki 
    public double B;        //rownania plaszczyzny
    public double C;        //zawierajacej
    public double D;        //te sciane

    public Sciana(Punkt3D pkt1, Punkt3D pkt2, Punkt3D pkt3, Punkt3D pkt4, Color kolor) {
        this.pkt1 = pkt1;
        this.pkt2 = pkt2;
        this.pkt3 = pkt3;
        this.pkt4 = pkt4;
        this.kolor = kolor;

        double x1, x2, x3, y1, y2, y3, z1, z2, z3;
        x1 = pkt1.x;
        y1 = pkt1.y;
        z1 = pkt1.z;

        x2 = pkt2.x;
        y2 = pkt2.y;
        z2 = pkt2.z;

        x3 = pkt3.x;
        y3 = pkt3.y;
        z3 = pkt3.z;

        this.A = y1 * z2 - y1 * z3 - y2 * z1 + y2 * z3 + y3 * z1 - y3 * z2;
        this.B = -x1 * z2 + x1 * z3 + x2 * z1 - x2 * z3 - x3 * z1 + x3 * z2;
        this.C = x1 * y2 - x1 * y3 - x2 * y1 + x2 * y3 + x3 * y1 - x3 * y2;
        this.D = -x1 * y2 * z3 + x1 * y3 * z2 + x2 * y1 * z3 - x2 * y3 * z1 - x3 * y1 * z2 + x3 * y2 * z1;
        
        this.krawedz1 = new Krawedz3D(pkt1, pkt2);
        this.krawedz2 = new Krawedz3D(pkt2, pkt3);
        this.krawedz3 = new Krawedz3D(pkt3, pkt4);
        this.krawedz4 = new Krawedz3D(pkt4, pkt1);
        

    }

//    ArrayList<Krawedz3D> getKrawedzie() {
//        ArrayList<Krawedz3D> krw = new ArrayList<Krawedz3D>();
//        krw.add(krawedz1);
//        krw.add(krawedz2);
//        krw.add(krawedz3);
//        krw.add(krawedz4);
//        return krw;
//                
//    }
    
    @Override
    public String toString() {
        return "Sciana{" + "pkt1=" + pkt1 + ", pkt2=" + pkt2 + ", pkt3=" + pkt3 + ", pkt4=" + pkt4 + '}' + "\n";
    }
}
