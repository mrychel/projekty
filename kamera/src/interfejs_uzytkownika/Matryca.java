package interfejs_uzytkownika;

import java.awt.Point;
import java.util.ArrayList;

import narzedzia.GeneratorPrzestrzeni;
import narzedzia.Odcinek;
import narzedzia.Parametr3D;
import narzedzia.Plaszczyzny;
import narzedzia.Punkt3D;

public class Matryca extends Plaszczyzny{

    public static final int OBSZAR_RYSOWANIA_X = 450;
    public static final int OBSZAR_RYSOWANIA_Y = 300;
    private static final int BIT_0 = 1, BIT_1 = 2, BIT_2 = 4, BIT_3 = 8;
	private Parametr3D H, S, R, T;
    private Integer D;
    
    public Matryca() {   
    	new GeneratorPrzestrzeni(this);
        H = new Parametr3D("Pochylenie");
        S = new Parametr3D("Powiêkszenie");
        S.ustawWartosci(1, 0.1, 5.0, 0.1);
        R = new Parametr3D("Obrót");
        R.ustawPrzeliczanieFunkcjiTryg(true);
        T = new Parametr3D("Przesuniêcie");
        D = new Integer(10);
        przytnijOdcinek(null, null, 0);
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
    
    public ArrayList<Point> dajLinie2D() {
    	ArrayList<Point> linie = new ArrayList<Point>();
    	
    	for (Odcinek odcinek : odcinki) 
    		linie.addAll(przeksztalc2D(odcinek));
    	
    	//while (linie.remove(null));	
    	
    	return linie;
    }
    
    private Parametr3D przeksztalc3D(Punkt3D punkt3D) {    	
    	
    	Parametr3D p = new Parametr3D("");
    	
    	double x = punkt3D.x, 
     		   y = punkt3D.y, 
     		   z = punkt3D.z;
     	    	
     	x = x*R.cosz + y*R.sinz;
     	y = y*R.cosz - x*R.sinz;
     	z = z*R.cosz + z*R.jedenMinusCosz;
     	
     	p.x = x * S.x + T.x;
     	p.y = y * S.y + T.y;
     	p.z = z * S.z + T.z;
    	 
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
    	    if ((kod | bit) == 0) 
    	    	switch (bit) {
    	    		case BIT_3 :
    	    			a.x = 0;
    	    			a.y = 1;
    	    			return a;
    	    		case BIT_2 :
    	    			a.x = 0;
    	    			a.y = 1;
    	    			return a;
    	    		case BIT_1 :
    	    			a.x = 0;
    	    			a.y = 1;
    	    			return a;
    	    		case BIT_0 :
    	    			a.x = 0;
    	    			a.y = 1;
    	    			return a;
	    			default:
	    				return a;
    	    	}
    	return a;
    }
    
    private ArrayList<Point> przytnij(Point a, Point b) {
    	
    	int kod_a, kod_b;
    	ArrayList<Point> wynik = new ArrayList<Point>();
    	
    	while (true) {
	    	kod_a = zakoduj(a);
	    	kod_b = zakoduj(b);
	    	
	    	if ((kod_a | kod_b) == 0) {
	    		wynik.add(a);
	    		wynik.add(b);
	    		return wynik;
	    	}
	    	if ((kod_a & kod_b) != 0)
	    		return wynik;
	    	
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
    
    
    private ArrayList<Point> przeksztalc2D(Odcinek odcinek) {    	   	
    	
    	return przytnij(
    			   rzutuj(przeksztalc3D(punkty.get(odcinek.a))),
    			   rzutuj(przeksztalc3D(punkty.get(odcinek.b)))
    		   );    	
    }    
}
