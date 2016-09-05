package interfejs_uzytkownika;

import java.awt.Point;
import java.util.ArrayList;

import narzedzia.GeneratorPrzestrzeni;
import narzedzia.Parametr3D;
import narzedzia.Punkt3D;

public class Matryca {

    private ArrayList<Punkt3D> przestrzen;
    private Parametr3D H, S, R, T;
    private Integer D;

    public Matryca() {
        this.przestrzen = GeneratorPrzestrzeni.dajPrzestrzen();
        H = new Parametr3D("Pochylenie");
        S = new Parametr3D("Powiêkszenie");
        R = new Parametr3D("Obrót");
        T = new Parametr3D("Przesuniêcie");
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
    
    private Punkt3D przeksztalc3D(Punkt3D punkt3D) {    	
    	int x = Math.round(punkt3D.x + T.x);
		int y = Math.round(punkt3D.y + T.y);
		int z = Math.round(punkt3D.z + T.z);
    	return new Punkt3D(x, y, z);
    }
    
    private Point przeksztalc2D(Punkt3D punkt3D) {    	   	
    	float W = 1 + punkt3D.z/D.floatValue();
		int x = Math.round(punkt3D.x/W);
		int y = Math.round(punkt3D.y/W);
		return new Point(x, y);
    }
    
    public ArrayList<Punkt3D> dajPunktyPrzeksztalcone() {
    	ArrayList<Punkt3D> punktyPrzksztalcone = new ArrayList<Punkt3D>();
    	
    	for (Punkt3D punkt3D : przestrzen) 
    		punktyPrzksztalcone.add(przeksztalc3D(punkt3D));
    	
		return punktyPrzksztalcone;
    }
    
    public ArrayList<Point> dajPunkty2D() {
    	ArrayList<Point> punkty = new ArrayList<Point>();
    	
    	for (Punkt3D punkt3D : dajPunktyPrzeksztalcone())
    		punkty.add(przeksztalc2D(punkt3D));
    	
    	return punkty;
    }
    
    public Point dajPunkt2D(Punkt3D punkt3D) {
    	return przeksztalc2D(punkt3D);
    }
    
    public void dodajPunkt3D(Punkt3D punkt) {
    	przestrzen.add(punkt);
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
