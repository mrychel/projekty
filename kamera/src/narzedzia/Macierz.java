/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package narzedzia;

/**
 *
 * @author rafal
 */
public class Macierz {

    double macierz[][];

    public Macierz(double[][] macierz) {
        this.macierz = macierz;
    }

    public static double[][] macierzJedynkowa() {
        double[][] mac = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
        return mac;
    }

    public static double[][] macierzRzutowania(double d) {
        double[][] mac = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 1 / d, 0}
        };
        return mac;

    }

//        public static double[][] macierzRzutowania2(double d) {
//        double[][] mac = {
//            {1, 0, 0, 0},
//            {0, 1, 0, 0},
//            {0, 0, 0, 0},
//            {0, 0, 1 / d, 1}
//        };
//        return mac;
//
//    }
    
    public static double[][] macierzPp(double d, double z_min) {
        double[][] mac = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1/(1-z_min), -z_min/(1-z_min)},
            {0, 0, 1 / d , 0}
        };
        return mac;
        
    }
    
    public static double[][] macierzTranslacji(double Tx, double Ty, double Tz) {
        double[][] mac = {
            {1, 0, 0, Tx},
            {0, 1, 0, Ty},
            {0, 0, 1, Tz},
            {0, 0, 0, 1}
        };
        return mac;
    }

    public static double[][] macierzObrOX(double fi) {
        double[][] mac = {
            {1, 0, 0, 0},
            {0, Math.cos(fi), -1 * Math.sin(fi), 0},
            {0, Math.sin(fi), Math.cos(fi), 0},
            {0, 0, 0, 1}
        };
        return mac;
    }

    public static double[][] macierzObrOY(double fi) {
        double[][] mac = {
            {Math.cos(fi), 0, Math.sin(fi), 0},
            {0, 1, 0, 0},
            {-1 * Math.sin(fi), 0, Math.cos(fi), 0},
            {0, 0, 0, 1}
        };
        return mac;
    }

    public static double[][] macierzObrOZ(double fi) {
        double[][] mac = {
            {Math.cos(fi), -1 * Math.sin(fi), 0, 0},
            {Math.sin(fi), Math.cos(fi), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
        return mac;
    }

    public static double[][] multiply(double a[][], double b[][]) {

        int aRows = a.length,
                aColumns = a[0].length,
                bRows = b.length,
                bColumns = b[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] resultant = new double[aRows][bColumns];

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    resultant[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return resultant;
    }
}
