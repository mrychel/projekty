package gra;

import static gra.Ruch.*;
import static gra.Stale.*;

import java.awt.Point;
import java.util.ArrayList;


public class Bicie {
	
	// zwraca listê pól, dla danego gracza i danej planszy, majacych bicie
	public static ArrayList<Point> dajBicia(Plansza plansza) {
		ArrayList<Point> polaMajaceBicie = new ArrayList<Point>();
				
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) {
            	if (plansza.czyjRuch() == plansza.dajPlansze()[x][y].dajPionek()) {
		    		Point m = new Point(x, y);	
		    		if (!dajBicia(plansza, m).isEmpty()) 
		    			polaMajaceBicie.add(m);
		    	}						
            }
		return polaMajaceBicie;
	}	
	
	// zwraca listê pól, dla danego gracza i planszy, na które mo¿e byæ wykonane bicie (max. dwa pola, w lewo lub w prawo)
	public static ArrayList<Point> dajBicia(Plansza plansza, Point p) {
		ArrayList<Point> polaGdzieMoznaRuszyc = new ArrayList<Point>();
		polaGdzieMoznaRuszyc.add(dajBiciePrawo(plansza, p));
		polaGdzieMoznaRuszyc.add(dajBicieLewo(plansza, p));
		while (polaGdzieMoznaRuszyc.remove(null));	
		return polaGdzieMoznaRuszyc;
	}

	public static Point dajBiciePrawo(Plansza plansza, Point p) {
		
		Point docelowePole = GRACZ.equals(plansza.czyjRuch()) ? new Point(p.x+2, p.y-2) : new Point(p.x-2,p.y+2);
		Point bitePole = GRACZ.equals(plansza.czyjRuch()) ? new Point(p.x+1, p.y-1) : new Point(p.x-1,p.y+1);
		Boolean pionNaBitymPolu = czyNaPlanszy(bitePole) ? plansza.dajPlansze()[bitePole.x][bitePole.y].dajPionek() : null;
		
		return (czyNaPlanszy(docelowePole)
				&& plansza.dajPlansze()[docelowePole.x][docelowePole.y].dajPionek() == PUSTE_POLE				
				&& (GRACZ.equals(plansza.czyjRuch()) ? KOMPUTER.equals(pionNaBitymPolu) : GRACZ.equals(pionNaBitymPolu))) ? 
						new Point(docelowePole) : null;		
	}
	
	public static Point dajBicieLewo(Plansza plansza, Point p) {
		
		Point docelowePole = GRACZ.equals(plansza.czyjRuch()) ? new Point(p.x-2, p.y-2) : new Point(p.x+2,p.y+2);
		Point bitePole = GRACZ.equals(plansza.czyjRuch()) ? new Point(p.x-1, p.y-1) : new Point(p.x+1,p.y+1);		
		Boolean pionNaBitymPolu = czyNaPlanszy(bitePole) ? plansza.dajPlansze()[bitePole.x][bitePole.y].dajPionek() : null;
		
		return (czyNaPlanszy(docelowePole)
				&& plansza.dajPlansze()[docelowePole.x][docelowePole.y].dajPionek() == PUSTE_POLE
				&& (GRACZ.equals(plansza.czyjRuch()) ? KOMPUTER.equals(pionNaBitymPolu) : GRACZ.equals(pionNaBitymPolu))) ? 
				new Point(docelowePole) : null;		
	}
}
