package narzedzia;

import java.util.ArrayList;

import kamera.GeneratorPrzestrzeni;

public class Macierz {

    ArrayList<Punkt3D> macierz;
    Parametr3D H, S, R, T;

    public Macierz() {
        this.macierz = GeneratorPrzestrzeni.dajPrzestrzen();
    }

    public void ustawH(Parametr3D parametry) {
    	H.ustaw(parametry);
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
