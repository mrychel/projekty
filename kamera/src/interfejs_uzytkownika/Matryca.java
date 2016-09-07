package interfejs_uzytkownika;

import java.awt.Point;
import java.util.ArrayList;

import narzedzia.GeneratorPrzestrzeni;
import narzedzia.Odcinek;
import narzedzia.Parametr3D;
import narzedzia.Plaszczyzny;
import narzedzia.Punkt3D;

public class Matryca extends Plaszczyzny{

    private Parametr3D H, S, R, T;
    private Integer D;

    public Matryca() {   
    	new GeneratorPrzestrzeni(this);
        H = new Parametr3D("Pochylenie");
        S = new Parametr3D("Powiêkszenie");
        R = new Parametr3D("Obrót");
        T = new Parametr3D("Przesuniêcie");
        D = new Integer(10);
    }

    public void tst() {
    	for (Punkt3D h : punkty)
    		System.out.println(h.x+" "+h.y+" "+h.z);
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
    
    public void ustawD(Integer d) {
    	this.D = d;
    }
    
    private Parametr3D obroc3D(Punkt3D p) {
    	
    	double x, y, z;
    	
    	double sinx = Math.sin(R.x);
    	double cosx = Math.cos(R.x);
    	double one_cosx = 1 - cosx;
    	
    	double sinz = Math.sin(R.z);
    	double cosz = Math.cos(R.z);
    	double one_cosz = 1 - cosz;
    	
    	x = p.x*cosz + p.y*sinz;
    	y = p.y*cosz - p.x*sinz;
    	z = p.z*cosz + p.z*one_cosz;
    	
    	Parametr3D h = new Parametr3D("");
    	h.x = (float)x;
    	h.y = (float)y;
    	h.z = (float)z;
    	return h;
    }
    
    private Punkt3D przeksztalc3D(Punkt3D punkt3D) {    	
    	
    	int x = Math.round(punkt3D.x + T.x);
		int y = Math.round(punkt3D.y + T.y);
		int z = Math.round(punkt3D.z + T.z);
    	return new Punkt3D(x, y, z);
    }
    
    private Point przeksztalc2D(Punkt3D punkt3D) {    	   	
    	float W = 1 + punkt3D.z/D.floatValue();
		int x = Math.round(punkt3D.x/W)+150;
		int y = Math.round(punkt3D.y/W)+225;
		return new Point(x, y);
    }
    /*
    public ArrayList<Punkt3D> dajPunktyPrzeksztalcone() {
    	ArrayList<Punkt3D> punktyPrzksztalcone = new ArrayList<Punkt3D>();
    	
    	for (Punkt3D punkt3D : punkty) 
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
    */
    public ArrayList<Point> dajLinie2D() {
    	ArrayList<Point> linie = new ArrayList<Point>();
    	
    	for (Odcinek odcinek : odcinki) {
    		linie.add(przeksztalc2D(przeksztalc3D(punkty.get(odcinek.a))));
    		linie.add(przeksztalc2D(przeksztalc3D(punkty.get(odcinek.b))));
    	}
    	
    	return linie;
    }
    /*
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
    }*/
}
