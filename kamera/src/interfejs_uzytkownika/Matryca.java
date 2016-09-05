package interfejs_uzytkownika;

import java.awt.Point;
import java.util.ArrayList;

import narzedzia.Parametr3D;
import narzedzia.Plaszczyzny;
import narzedzia.Punkt3D;

public class Matryca extends Plaszczyzny {
   
    private Parametr3D H, S, R, T;
    private Integer D;

    public Matryca() {
    	H = new Parametr3D("Pochylenie");
        S = new Parametr3D("Powi�kszenie");
        R = new Parametr3D("Obr�t");
        T = new Parametr3D("Przesuni�cie");
        D = new Integer(10);
    }

    public Parametr3D dajH() {
    	return H;
    }
    
    public Parametr3D dajS() {
    	return S;
    }
    
    public Parametr3D dajR() {
    	return R;
    }
    
    public Parametr3D dajT() {
    	return T;
    }
    
    public Integer dajD() {
    	return D;
    }
    
    public ArrayList<Punkt3D> dajPunktyPrzeksztalcone() {
    	ArrayList<Punkt3D> punktyPrzksztalcone = new ArrayList<Punkt3D>();
    	int x, y, z;
    	
    	for (Punkt3D punkt3D : przestrzen) {
    		x = Math.round(punkt3D.x + T.x);
    		y = Math.round(punkt3D.y + T.y);
    		z = Math.round(punkt3D.z + T.z);
    		punktyPrzksztalcone.add(new Punkt3D(x, y, z));
    	}
		return punktyPrzksztalcone;
    }
    
    public ArrayList<Point> dajPunkty2D() {
    	ArrayList<Point> punkty = new ArrayList<Point>();
    	int x, y;
    	float W;
    	
    	for (Punkt3D punkt3D : dajPunktyPrzeksztalcone()) {
    		W = 1 + punkt3D.z/D.floatValue();
    		x = Math.round(punkt3D.x/W);
    		y = Math.round(punkt3D.y/W);
    		punkty.add(new Point(x, y));
    	}
    	return punkty;
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
