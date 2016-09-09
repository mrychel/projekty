package algorytmy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import narzedzia.GeneratorPrzestrzeni;
import narzedzia.Odcinek;
import narzedzia.Odcinki;
import narzedzia.Parametr3D;
import narzedzia.Plaszczyzna;
import narzedzia.Plaszczyzny;
import narzedzia.Punkt3D;

public class Algorytmy extends Plaszczyzny {

	public static final int POCZATEK_OBSZARU_RYSOWANIA_X = 0;
	public static final int POCZATEK_OBSZARU_RYSOWANIA_Y = 0;
	public static final int OBSZAR_RYSOWANIA_X = 450;
    public static final int OBSZAR_RYSOWANIA_Y = 300;
    
    private static final int BIT_0 = 1, BIT_1 = 2, BIT_2 = 4, BIT_3 = 8;
	private Parametr3D H, S, R, T;
    private Integer D;
    private ArrayList<Parametr3D> parametry3D = new ArrayList<>(); 
    private ArrayList<Integer> parametry = new ArrayList<>();
    
    public Algorytmy() {   
    	
    	new GeneratorPrzestrzeni(this);
    	
    	utworzParametry();        
    }

    private void utworzParametry() {
    	
    	T = new Parametr3D("Przesuniêcie");
        T.ustawWartosci(50, -1500, 1500, 10d);
        parametry3D.add(T);
    	
        S = new Parametr3D("Powiêkszenie");
        S.ustawWartosci(1, 0.1, 5.0, 0.1);
        parametry3D.add(S);
        
        R = new Parametr3D("Obrót");
        R.ustawWartosci(0, -1, 361, 1d);
        R.ustawPrzeliczanieFunkcjiTryg(true);
        parametry3D.add(R);
            	
        H = new Parametr3D("Pochylenie");
        H.ustawWartosci(0, -3.0, 3.0, 0.1);
        parametry3D.add(H);
        
        D = new Integer(-500);
        parametry.add(D);
    }
    
    public ArrayList<Parametr3D> dajParametry3D() {
    	return parametry3D;
    }
    
    public ArrayList<Integer> dajParametry() {
    	return parametry;
    }    
    
    public ArrayList<Odcinek> dajLinie2D() {
    	
    	Odcinki linie = new Odcinki();
    	
    	for (Odcinek odcinek : odcinki) 
    		linie.odcinki.add(przeksztalc2D(odcinek));
    	
    	while (linie.odcinki.remove(null));
    	
    	return linie.odcinki;
    }
    
    public ArrayList<Plaszczyzna> dajPlaszczyzny2D() {
    	
    	Plaszczyzny ksztalty = new Plaszczyzny();
    	    	
    	for (Plaszczyzna plaszczyzna : plaszczyzny) 
    		ksztalty.plaszczyzny.add(przeksztalc2D(plaszczyzna));
    	
    	Collections.sort(ksztalty.plaszczyzny);
    	
    	return ksztalty.plaszczyzny;
    }
    

    private Odcinek przeksztalc2D(Odcinek odcinek) {    	   	
    	
		Point[] p = przytnij(
		    			rzutuj(przeksztalc3D(punkty.get(odcinek.a))),
		    			rzutuj(przeksztalc3D(punkty.get(odcinek.b)))
		    		);
		
		if (p != null) {
			odcinek.rzut_a = p[0];
			odcinek.rzut_b = p[1];
			return odcinek;
		} else
			return null;
    }    
    
    private Plaszczyzna przeksztalc2D(Plaszczyzna pl) {    	   	
    	
    	Parametr3D punktPoTransformacjach3D;
    	Point punktPoTransformacjach2D;
    	double suma_z = 0;
    	
    	pl.reset();
    	for (Integer i_punktu : pl.plaszczyzna) {
    		punktPoTransformacjach3D = przeksztalc3D(punkty.get(i_punktu));
    		suma_z += punktPoTransformacjach3D.z;
    		punktPoTransformacjach2D = rzutuj(punktPoTransformacjach3D);
    		pl.rzut.addPoint(punktPoTransformacjach2D.x, punktPoTransformacjach2D.y);
    	}
    	pl.z_rzutu = (int) Math.round(suma_z/pl.plaszczyzna.size());
    	
    	return pl;
    }  
    
    public void ustawD(Integer d) {
    	this.D = d;
    }
        
    private Parametr3D przeksztalc3D(Punkt3D punkt3D) {    	
    	
    	double x = punkt3D.x,
    		   y = -punkt3D.y, 
    		   z = -punkt3D.z;
     	
     	x = x * S.x + H.y*y + H.z*z - T.x;
     	y = y * S.y + H.x*x + H.z*z + T.y;
     	z = z * S.z + H.x*x + H.y*y + T.z;
     	
     	x = x*R.mo[0][0]+y*R.mo[0][1]+z*R.mo[0][2];
     	y = x*R.mo[1][0]+y*R.mo[1][1]+z*R.mo[1][2];
     	z = x*R.mo[2][0]+y*R.mo[2][1]+z*R.mo[2][2];
    	
     	Parametr3D p = new Parametr3D("");
     	p.x = x; p.y = y; p.z = z;
     	return p;
    }
    
    private Point rzutuj(Parametr3D p) {
    	
    	double W = 1 + p.z/D.floatValue();
		int x = (int) (Math.round(p.x/W)+OBSZAR_RYSOWANIA_X/2);
		int y = (int) (Math.round(p.y/W)+OBSZAR_RYSOWANIA_Y/2);
		return new Point(x, y);
    }
        
    private int zakoduj(Point p) {
    	
    	int kod = 0;
    	    	
    	if (p.x < 0) kod = kod | BIT_0;
    	if (p.x >= OBSZAR_RYSOWANIA_X) kod = kod | BIT_1;
    	if (p.y < 0) kod = kod | BIT_2;
    	if (p.y >= OBSZAR_RYSOWANIA_Y) kod = kod | BIT_3;
    	return kod;
    }
    
    private Point przytnijOdcinek(Point a, Point b, int kod) {
    	
    	int[] bity = {BIT_3, BIT_2, BIT_1, BIT_0};
    	
    	for (int bit : bity)
    	    if ((kod & bit) != 0) {    	    
    	    	switch (bit) {
    	    		case BIT_3 :
    	    			if (b.y-a.y != 0)
    	    				a.x = a.x+(a.x-b.x)*(a.y-POCZATEK_OBSZARU_RYSOWANIA_Y-OBSZAR_RYSOWANIA_Y+1)/(b.y-a.y);
    	    			a.y = POCZATEK_OBSZARU_RYSOWANIA_Y+OBSZAR_RYSOWANIA_Y-1;
    	    			return a;
    	    		case BIT_2 :
    	    			if (b.y-a.y != 0)
    	    				a.x = a.x+(a.x-b.x)*(a.y-POCZATEK_OBSZARU_RYSOWANIA_Y)/(b.y-a.y);
    	    			a.y = POCZATEK_OBSZARU_RYSOWANIA_Y;
    	    			return a;
    	    		case BIT_1 :    	    			
    	    			a.x = POCZATEK_OBSZARU_RYSOWANIA_X+OBSZAR_RYSOWANIA_X-1;
    	    			if (a.x-b.x != 0)
    	    				a.y = a.y+(b.y-a.y)*(a.x-POCZATEK_OBSZARU_RYSOWANIA_X-OBSZAR_RYSOWANIA_X+1)/(a.x-b.x);
    	    			return a;
    	    		case BIT_0 :
    	    			a.x = POCZATEK_OBSZARU_RYSOWANIA_X;
    	    			if (b.x-a.x != 0)
    	    				a.y = a.y+(b.y-a.y)*(POCZATEK_OBSZARU_RYSOWANIA_X-a.x)/(b.x-a.x);
    	    			return a;
	    			default:
	    				return a;
    	    	}}
    	return a;
    }
    
    private Point[] przytnij(Point a, Point b) {
    	
    	int kod_a, kod_b;
    	Point[] wynik = {new Point(), new Point()};
    	
    	while (true) {
	    	kod_a = zakoduj(a);
	    	kod_b = zakoduj(b);
	    	
	    	if ((kod_a | kod_b) == 0) {
	    		wynik[0] = a;
	    		wynik[1] = b;
	    		return wynik;
	    	}
	    	if ((kod_a & kod_b) != 0)
	    		return null;
	    	
	    	if (kod_a == 0) {
	    		kod_a = kod_b;
	    		kod_b = 0;
	    		Point pomocniczy = a;
	    		a = b;
	    		b = pomocniczy;
	    	}
	    	a = przytnijOdcinek(a, b, kod_a);
    	}
    }          
}
