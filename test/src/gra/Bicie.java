package gra;
import gra.Ruch;
import gra.Plansza.Pole;

import static gra.Stale.ROZMIAR_PLANSZY;

import java.awt.Point;
import java.util.ArrayList;


public class Bicie {
	
	// zwraca listê pól, dla danego gracza i danej planszy, majacych bicie
	public static ArrayList<Point> dajPolaMajaceBicie(Boolean kto, Pole[][] plansza) {
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
	
	// zwraca listê pól, dla danego gracza i planszy oraz pola, na które mo¿e byæ wykonane bicie (max. dwa pola, w lewo lub w prawo)
	public static ArrayList<Point> dajBicia(Boolean kto, Pole[][] plansza, Point p) {
		ArrayList<Point> polaGdzieMoznaBic = new ArrayList<Point>();
		
		return null;
	}
	/*
	public static boolean mogeBic(String ruch, String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
		
		if (Stale.BICIE_LEWO.equals(ruch)) {
				
			System.out.println("Bicie na lewo");
			return mogeBicLewo(kto, plansza, wiersz, kolumna, czyGracz);
		} else if (Stale.BICIE_PRAWO.equals(ruch)) {
	
			System.out.println("Bicie na prawo");
			return mogeBicPrawo(kto, plansza, wiersz, kolumna, czyGracz);
		} 
		
		return false;
	}

	public static Point mogeBicLewo(String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
		
		String[] pionki = new String[12];
		int skokWiersz;	
		int skokKolumna = -1; 
		
		if (Stale.GRACZ.equals(kto)) { 
			skokWiersz = -1; 
			pionki = Stale.BIALE;
		} else {
			skokWiersz = 1;
			pionki = Stale.CZARNE;
		}
				
		boolean jestPokonany = false;
		if(Ruch.czyNaPlanszy(wiersz+skokWiersz, kolumna+skokKolumna) 
		   && plansza[wiersz+skokWiersz][kolumna+skokKolumna]!= null
		   && Arrays.asList(pionki).contains(plansza[wiersz+skokWiersz][kolumna+skokKolumna].dajPionek()))
		{
			jestPokonany = true;
		}
				
		boolean bezpiecznaPrzestrzen = Ruch.czyNaPlanszy(wiersz+(2*skokWiersz), kolumna+(2*skokKolumna))
									   && Ruch.mogeRuszycLewo(kto, plansza, wiersz+skokWiersz, kolumna+skokKolumna, czyGracz);
				
		if(jestPokonany && bezpiecznaPrzestrzen)
		   		    
			return null;
		else
		
			return null;
	}

	public static boolean mogeBicPrawo(String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
	
		String[] pionki = new String[12];
		int skokWiersz;	
		int skokKolumna = 1; 
		
		if (Stale.GRACZ.equals(kto)) { 
			skokWiersz = -1; 
			pionki = Stale.BIALE;
		} else {
			skokWiersz = 1;
			pionki = Stale.CZARNE;	
		}
		
		boolean jestPokonany = false;
		if(Ruch.czyNaPlanszy(wiersz+skokWiersz, kolumna+skokKolumna) 
				   && plansza[wiersz+skokWiersz][kolumna+skokKolumna]!= null
				   && Arrays.asList(pionki).contains(plansza[wiersz+skokWiersz][kolumna+skokKolumna].dajPionek()))
				{
					jestPokonany = true;
				}
		
		boolean bezpiecznaPrzestrzen = Ruch.czyNaPlanszy(wiersz+(2*skokWiersz), kolumna+(2*skokKolumna))
				   && Ruch.mogeRuszycPrawo(kto, plansza, wiersz+skokWiersz, kolumna+skokKolumna, czyGracz);
		
		if(jestPokonany && bezpiecznaPrzestrzen)	   
			
			return true;
		else
		
			return false;
	}


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
