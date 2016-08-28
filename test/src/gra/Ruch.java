package gra;

import mini_max.GeneratorRuchow;
import mini_max.Lisc;
import static gra.Bicie.*;
import static gra.Stale.*;
import java.awt.Point;
import java.util.ArrayList;

public class Ruch {
 
	// zwraca listê pól, dla danego gracza i danej planszy, majacych ruch lub bicie
	public static ArrayList<Point> dajRuchy(Plansza plansza) {
		ArrayList<Point> polaMajaceRuch = new ArrayList<Point>();
		
		if (!dajBicia(plansza).isEmpty()) 
            return dajBicia(plansza);
		
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++)            	
		    	if (plansza.czyjRuch() == plansza.dajPlansze()[x][y].dajPionek()) {
		    		Point m = new Point(x, y);	
		    		if (!dajRuchy(plansza, m).isEmpty()) polaMajaceRuch.add(m);						
		    	}
		    
		return polaMajaceRuch;
	}
	
	// zwraca listê pól, dla danego gracza i planszy, na które mo¿e byæ wykonany ruch lub bicie
	public static ArrayList<Point> dajRuchy(Plansza plansza, Point p) {
		if (!dajBicia(plansza, p).isEmpty())
			return dajBicia(plansza, p);
		
		ArrayList<Point> polaGdzieMoznaRuszyc = new ArrayList<Point>();
		polaGdzieMoznaRuszyc.add(dajRuchPrawo(plansza, p));
		polaGdzieMoznaRuszyc.add(dajRuchLewo(plansza, p));
		while (polaGdzieMoznaRuszyc.remove(null));	
		return polaGdzieMoznaRuszyc;
	}
	
	private static Point dajRuchPrawo(Plansza plansza, Point p) {
	
		Point docelowePole = GRACZ.equals(plansza.czyjRuch()) ? new Point(p.x+1, p.y-1) : new Point (p.x-1, p.y+1);
		
		return (czyNaPlanszy(docelowePole)
				&& plansza.dajPlansze()[docelowePole.x][docelowePole.y].dajPionek() == PUSTE_POLE) ? 
				new Point(docelowePole) : null;		
	}
	
	private static Point dajRuchLewo(Plansza plansza, Point p) {	
		
		Point docelowePole = GRACZ.equals(plansza.czyjRuch()) ? new Point(p.x-1, p.y-1) : new Point (p.x+1, p.y+1);
		
		return (czyNaPlanszy(docelowePole)
				&& plansza.dajPlansze()[docelowePole.x][docelowePole.y].dajPionek() == PUSTE_POLE) ? 
				new Point(docelowePole) : null;	
	}
	
	public static void wykonajRuch(Plansza plansza, Point skad, Point dokad) {
	
		plansza.dajPlansze()[dokad.x][dokad.y].ustawPionek(
			plansza.dajPlansze()[skad.x][skad.y].dajPionek());
		plansza.dajPlansze()[skad.x][skad.y].ustawPionek(PUSTE_POLE);
		// bicie
		if (Math.abs(skad.x - dokad.x) > 1) {
			int x = (skad.x - dokad.x) > 0 ? skad.x - 1 : skad.x + 1;
		    int y = (skad.y - dokad.y) > 0 ? skad.y - 1 : skad.y + 1;		    
		    plansza.dajPlansze()[x][y].ustawPionek(PUSTE_POLE);
		    plansza.ustawBicie(true);
		} else 
			plansza.ustawBicie(false);
		// koniec bicia
		plansza.czyjRuch(!plansza.czyjRuch());
		String komunikat = GRACZ.equals(!plansza.czyjRuch()) ? "GRACZ" : "KOMPUTER"
						   +" wykona³ "+ (plansza.dajBicie() ? "bicie: " : "ruch: ")
						   +dajNazweRuchu(skad)+" na "+dajNazweRuchu(dokad);
		plansza.ustawKomuniakt(komunikat);
	}		
	
	public static Plansza ruchKomputera(Plansza plansza) {
			
		Lisc drzewo = new Lisc(plansza);
		GeneratorRuchow.budujPodDrzewo(drzewo, 0);
		if (drzewo.dajNajRuch() == null) return null;
		plansza.ustawPlansze(drzewo.dajNajRuch());	
				
		return plansza;		
	}

	public static boolean czyNaPlanszy(Point p) {
		
		return p.x < ROZMIAR_PLANSZY  && p.y < ROZMIAR_PLANSZY 
				&& p.x >= 0 && p.y >= 0;
	}
	
	private static String dajNazweRuchu(Point p) {	 
		
	    return COLS.substring(p.x, p.x + 1) + (p.y+1);	    
	}
}