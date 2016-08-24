package gra;

import static gra.Ruch.*;
import static gra.Stale.*;
import gra.Plansza.Pole;
import java.awt.Point;
import java.io.PushbackInputStream;
import java.util.ArrayList;


public class Bicie {
	
	// zwraca listê pól, dla danego gracza i danej planszy, majacych bicie
	public static ArrayList<Point> dajBicia(Boolean kto, Pole[][] plansza) {
		ArrayList<Point> polaMajaceBicie = new ArrayList<Point>();
		Point p = new Point();
		
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) {            	
            	p.x = x;
            	p.y = y;
            	if (kto.equals(plansza[x][y].dajPionek())
            		&& !dajBicia(kto, plansza, p).isEmpty())
						polaMajaceBicie.add(p);
            }
		return polaMajaceBicie;
	}	
	
	// zwraca listê pól, dla danego gracza i planszy, na które mo¿e byæ wykonane bicie (max. dwa pola, w lewo lub w prawo)
	public static ArrayList<Point> dajBicia(Boolean kto, Pole[][] plansza, Point p) {
		ArrayList<Point> polaGdzieMoznaRuszyc = new ArrayList<Point>();
		polaGdzieMoznaRuszyc.add(dajBiciePrawo(kto, plansza, p));
		polaGdzieMoznaRuszyc.add(dajBicieLewo(kto, plansza, p));
		while (polaGdzieMoznaRuszyc.remove(null));	
		return polaGdzieMoznaRuszyc;
	}

	public static Point dajBiciePrawo(Boolean kto, Pole[][] plansza, Point p) {
		
		int docelowaKolumna = GRACZ.equals(kto) ? p.x+2 : p.x-2;
		int docelowyWiersz = GRACZ.equals(kto) ? p.y-2 : p.y+2; 
		
		return (czyNaPlanszy(docelowaKolumna, docelowyWiersz)
				&& plansza[docelowaKolumna][docelowyWiersz].dajPionek() == PUSTE_POLE
				&& plansza[GRACZ.equals(kto) ? p.x+1 : p.x-1][GRACZ.equals(kto) ? p.y-1 : p.y+1].dajPionek() == GRACZ.equals(kto) ? KOMPUTER : GRACZ) ? 
				new Point(docelowaKolumna, docelowyWiersz) : null;		
	}
	
	public static Point dajBicieLewo(Boolean kto, Pole[][] plansza, Point p) {
		
		int docelowaKolumna = GRACZ.equals(kto) ? p.x-2 : p.x+2;
		int docelowyWiersz = GRACZ.equals(kto) ? p.y-2 : p.y+2; 
		
		return (czyNaPlanszy(docelowaKolumna, docelowyWiersz)
				&& plansza[docelowaKolumna][docelowyWiersz].dajPionek() == PUSTE_POLE
				&& plansza[GRACZ.equals(kto) ? p.x-1 : p.x+1][GRACZ.equals(kto) ? p.y-1 : p.y+1].dajPionek() == GRACZ.equals(kto) ? KOMPUTER : GRACZ) ? 
				new Point(docelowaKolumna, docelowyWiersz) : null;		
	}
	

/*
	public static void bicie(String ruch, String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
		
		if (Stale.BICIE_LEWO.equals(ruch))
		
			bicieLewo(kto, plansza, wiersz, kolumna, czyGracz);
		
		else if (Stale.BICIE_PRAWO.equals(ruch))
		
			biciePrawo(kto, plansza, wiersz, kolumna, czyGracz);		
	}


	public static Pole[][] bicieLewo(String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
		
		int skokWiersz;	
		int skokKolumna = -1; 
		
		if (Stale.GRACZ.equals(kto)) 
			skokWiersz = -1; 
		else
			skokWiersz = 1;
		
		// zdejmij pokonanego
		plansza[wiersz+skokWiersz][kolumna+skokKolumna].ustawPionek(Stale.POLE_PUSTE);
		// zmieñ pozycjê zbijaj¹cego
		plansza[wiersz+(2*skokWiersz)][kolumna+(2*skokKolumna)].ustawPionek(plansza[wiersz][kolumna].dajPionek());
		plansza[wiersz][kolumna].ustawPionek(Stale.POLE_PUSTE);
		// damka
		if (wiersz+(2*skokWiersz) == (Stale.GRACZ.equals(kto)?1:Stale.ROZMIAR_PLANSZY)) {
			plansza[wiersz+(2*skokWiersz)][kolumna+(2*skokKolumna)].ustawPionek(Stale.POLE_PUSTE);
			plansza[0][Stale.GRACZ.equals(kto)?1:2].ustawWartosc(
					plansza[0][Stale.GRACZ.equals(kto)?1:2].dajWartosc()+1); 
		} 
		return plansza;
	}

	public static Pole[][] biciePrawo(String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
		
		int skokWiersz;	
		int skokKolumna = 1; 
		
		if (Stale.GRACZ.equals(kto)) 
			skokWiersz = -1; 
		else
			skokWiersz = 1;
		
		// zdejmij pokonanego
		plansza[wiersz+skokWiersz][kolumna+skokKolumna].ustawPionek(Stale.POLE_PUSTE);
		// zmieñ pozycjê zbijaj¹cego
		plansza[wiersz+(2*skokWiersz)][kolumna+(2*skokKolumna)].ustawPionek(plansza[wiersz][kolumna].dajPionek());
		plansza[wiersz][kolumna].ustawPionek(Stale.POLE_PUSTE);
		// damka
		if (wiersz+(2*skokWiersz) == (Stale.GRACZ.equals(kto)?1:Stale.ROZMIAR_PLANSZY)) {
			plansza[wiersz+(2*skokWiersz)][kolumna+(2*skokKolumna)].ustawPionek(Stale.POLE_PUSTE);
			plansza[0][Stale.GRACZ.equals(kto)?1:2].ustawWartosc(
					plansza[0][Stale.GRACZ.equals(kto)?1:2].dajWartosc()+1); 
		} 
		return plansza;
	}*/
}
