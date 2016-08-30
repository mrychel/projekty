/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zaslanianie;

import java.awt.Color;
import java.util.ArrayList;
import wirtualnakamera.Sciana;

/**
 *
 * @author rafal
 */
public class ListaWielokatow {
    //zawiera wielokaty przed rzutowaniem, ale po przekształceniach

    ArrayList<Wielokat> lista;

    public ListaWielokatow(ArrayList<Sciana> sc) {
        lista = new ArrayList<Wielokat>();
        for (Sciana s : sc) {
            Wielokat t = new Wielokat(s);
            lista.add(t);
//            System.out.println("Wielokat " + sc.indexOf(s) + ";" + s.pkt1.x + ";" + s.pkt1.y + ";" + s.pkt1.z + ";" + s.pkt2.x + ";" + s.pkt2.y + ";" + s.pkt2.z + ";" + s.pkt3.x + ";" + s.pkt3.y + ";" + s.pkt3.z + ";" + t.wspSciany());
//            System.out.println("Wielokat " + sc.indexOf(s) + ": " + t.wspSciany()); 
        }
        
    }

    public class Wielokat {

//        @Override
//        public boolean equals(Object obj) {
//            if (obj == null) {
//                return false;
//            }
////            if (getClass() != obj.getClass()) {
////                return false;
////            }
//            final Wielokat other = (Wielokat) obj;
//            if (this.nr_sciany != other.nr_sciany) {
//                return false;
//            }
//            return true;
//        }
//        public boolean equals(Wielokat w) {
//            if (this.nr_sciany == w.nr_sciany) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        int nr_sciany;
        double A;
        double B;
        double C;
        double D;
        Color kolor;
        boolean we_wy;


        public Wielokat(Sciana s) {
            this.we_wy = false;

            this.kolor = s.kolor;
            this.A = s.A;
            this.B = s.B;
            this.C = s.C;
            this.D = s.D;
            
//            double u1,u2,u3,v1,v2,v3;
//            u1 = s.pkt1.x - s.pkt2.x;
//            u2 = s.pkt1.y - s.pkt2.y;
//            u3 = s.pkt1.z - s.pkt2.z;
//            v1 = s.pkt1.x - s.pkt3.x;
//            v2 = s.pkt1.y - s.pkt3.y;
//            v3 = s.pkt1.z - s.pkt3.z;
//            this.A = u2*v3 - u3*v2;
//            this.B = u3*v1 - u1*v3;
//            this.C = u1*v2 - u2*v1;
//            this.D = -(this.A * s.pkt1.x + this.B * s.pkt1.y + this.C * s.pkt1.z);

//            double x1, x2, x3, y1, y2, y3, z1, z2, z3;
//            x1 = s.pkt1.x;
//            y1 = s.pkt1.y;
//            z1 = s.pkt1.z;
//
//            x2 = s.pkt2.x;
//            y2 = s.pkt2.y;
//            z2 = s.pkt2.z;
//
//            x3 = s.pkt3.x;
//            y3 = s.pkt3.y;
//            z3 = s.pkt3.z;

//            this.A = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
//            this.B = (x3 - x1) * (z2 - z1) - (x2 - x1) * (z3 - z1);
//            this.C = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
//            this.D = -(this.A * x1 + this.B * y1 + this.C * z1);


//            this.A = y1 * z2 - y1 * z3 - y2 * z1 + y2 * z3 + y3 * z1 - y3 * z2;
//            this.B = -x1 * z2 + x1 * z3 + x2 * z1 - x2 * z3 - x3 * z1 + x3 * z2;
//            this.C = x1 * y2 - x1 * y3 - x2 * y1 + x2 * y3 + x3 * y1 - x3 * y2;
//            this.D = -x1 * y2 * z3 + x1 * y3 * z2 + x2 * y1 * z3 - x2 * y3 * z1 - x3 * y1 * z2 + x3 * y2 * z1;

            

        }

        public double rozwiarzDla(int x, int y) {
            double z = -(this.A * x + this.B * y + this.D)/this.C;
            
            return z;
        }
        public double wspZPrzecieciaZProsta(double x1, double y1, double z1, double x2, double y2, double z2) {
            double t = -(A * x1 + B * y1 + C * z1 + D) / (A * (x2 - x1) + B * (y2 - y1) + C * (z2 - z1));
            double z = z1 + t * (z2 - z1);
            return z;
        }

        public double odlegloscPunktuPrzecieciaZProsta(double x1, double y1, double z1, double x2, double y2, double z2) {
            double t = -(A * x1 + B * y1 + C * z1 + D) / (A * (x2 - x1) + B * (y2 - y1) + C * (z2 - z1));
            double x = x1 + t * (x2 - x1);
            double y = y1 + t * (y2 - y1);
            double z = z1 + t * (z2 - z1);
//            double odl = Math.sqrt(x * x + y * y + z * z);
            double odl = Math.sqrt((x - x1)*(x - x1) + (y - y1)*(y - y1) + (z - z1)* (z - z1));
//            System.out.println("odl: " + odl + " z: " + z);
            return odl;
        }
//        public double rozwiarzDlaZPoprawka(int x, int y)  {
//            
//        }
        public String wspSciany() {
            return A + ";" + B + ";" + C + ";";
        }
    }
}
