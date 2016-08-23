package gra;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import gra.Plansza.Pole;
import mini_max.GeneratorRuchow;
import mini_max.Lisc;

import static gra.Stale.*;

import java.awt.Point;
import java.util.ArrayList;

public class Ruch {
 
	// zwraca listê pól, dla danego gracza i danej planszy, majacych ruch
	public static ArrayList<Point> dajRuchy(Boolean kto, Pole[][] plansza) {
		ArrayList<Point> polaMajaceRuch = new ArrayList<Point>();
		Point p = new Point();
		
		// TO DO ----- if dajBicia(kto, plansza)
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) {
            	p.x = x;
    			p.y = y;
		    	if (kto.equals(plansza[x][y].dajPionek())
		    		&& !dajRuchy(kto, plansza, p).isEmpty())
						polaMajaceRuch.add(p);
		    }
		return polaMajaceRuch;
	}
	
	// zwraca listê pól, dla danego gracza i planszy oraz pola, na które mo¿e byæ wykonane bicie (max. dwa pola, w lewo lub w prawo)
	public static ArrayList<Point> dajRuchy(Boolean kto, Pole[][] plansza, Point p) {
		ArrayList<Point> polaGdzieMoznaRuszyc = new ArrayList<Point>();
		polaGdzieMoznaRuszyc.add(dajRuchPrawo(kto, plansza, p));
		return polaGdzieMoznaRuszyc;
	}
	
	public static boolean mogeRuszyc(String ruch, String kto, Pole[][] plansza, int wiersz, int kolumna, boolean czyGracz) {
		/*		
		if(Stale.RUCH_LEWO.equals(ruch))
		{
			return mogeRuszycLewo(kto, plansza, wiersz, kolumna,  czyGracz);
		}
		else if(Stale.RUCH_PRAWO.equals(ruch))
		{
			return mogeRuszycPrawo(kto, plansza, wiersz, kolumna, czyGracz);
		}
		else
		{
			return false;
		}*/return true;
	}

	public static Point dajRuchPrawo(Boolean kto, Pole[][] plansza, Point p) {
			
		int docelowyWiersz;	// przesuniecie w wierszu i kolumnie,		
		int docelowaKolumna; // ¿eby ka¿dy gracz móg³ widzieæ planszê "z przodu"
		
		docelowaKolumna = GRACZ.equals(kto) ? p.x+1 : p.x-1;
		docelowyWiersz = GRACZ.equals(kto) ? p.y-1 : p.y+1; 
		
		return (czyNaPlanszy((docelowyWiersz), (docelowaKolumna))
				&& plansza[docelowyWiersz][docelowaKolumna].dajPionek() == null) ? 
				new Point(docelowyWiersz, docelowaKolumna) : null;		
	}
	/*
	public static boolean mogeRuszycLewo(String kto, Pole[][] plansza, int pionekWiersz, int pionekKolumna, boolean czyGracz) {
	
		boolean moge;
		int skokWiersz;	// przesuniecie w wierszu i kolumnie,		
		int skokKolumna; // ¿eby ka¿dy gracz móg³ widzieæ planszê "z przodu"
	
		skokKolumna = -1;
		if (Stale.GRACZ.equals(kto)) 
			skokWiersz = -1; 
		else
			skokWiersz = 1;
		
		if(pionekKolumna == 1) {
			
			if(czyGracz) System.out.println("Nie mo¿na wykonaæ tego ruchu "+
											plansza[pionekWiersz][pionekKolumna].dajPionek()+
											", jestes na koncu planszy.");
			moge = false; 
		} else {
			
			if(czyNaPlanszy((pionekWiersz+skokWiersz), (pionekKolumna+skokKolumna))
					   && plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna]!= null
					   && Stale.POLE_PUSTE.equals(plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna].dajPionek()))	{
						
			   moge = true;
			} else {
				if (czyGracz && !Stale.POLE_PUSTE.equals(plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna].dajPionek())) 
					System.out.println("Lewe pole pionka "+
										plansza[pionekWiersz][pionekKolumna].dajPionek()+
										", jest zajête.");
				moge = false;
			}
		}
		
		return moge;
	}

	public static Pole[][]  move(String ruch, String kto, Pole[][] plansza, int pionekWiersz, int pionekKolumna) {
	
		if (Stale.RUCH_LEWO.equals(ruch))
		{
			System.out.println("Ruch w lewo!");
			return ruchLewo(kto, plansza, pionekWiersz, pionekKolumna);	
		}
		else
		{
			System.out.println("Ruch w prawo!");
			return ruchPrawo(kto, plansza, pionekWiersz, pionekKolumna);
		}
	}
	
	public static Pole[][] ruchLewo(String kto, Pole[][] plansza, int pionekWiersz, int pionekKolumna) {
		
		int skokWiersz;	
		int skokKolumna = -1; 
			
		if (Stale.GRACZ.equals(kto)) 
			skokWiersz = -1; 
		else
			skokWiersz = 1;
		
		plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna].ustawPionek(
				plansza[pionekWiersz][pionekKolumna].dajPionek());
		plansza[pionekWiersz][pionekKolumna].ustawPionek(Stale.POLE_PUSTE);
		
		if (pionekWiersz+skokWiersz == (Stale.GRACZ.equals(kto)?1:Stale.ROZMIAR_PLANSZY)) {
			plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna].ustawPionek(Stale.POLE_PUSTE);
			plansza[0][Stale.GRACZ.equals(kto)?1:2].ustawWartosc(
					plansza[0][Stale.GRACZ.equals(kto)?1:2].dajWartosc()+1); 
		} 
		
		return plansza;
	}

	public static Pole[][] ruchPrawo(String kto, Pole[][] plansza, int pionekWiersz, int pionekKolumna) {
		
		int skokWiersz;			
		int skokKolumna = 1;
		
		if (Stale.GRACZ.equals(kto)) 
			skokWiersz = -1; 
		else
			skokWiersz = 1;
		
		plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna].ustawPionek(
				plansza[pionekWiersz][pionekKolumna].dajPionek());
		plansza[pionekWiersz][pionekKolumna].ustawPionek(Stale.POLE_PUSTE);
		
		if (pionekWiersz+skokWiersz == (Stale.GRACZ.equals(kto)?1:Stale.ROZMIAR_PLANSZY)) {
			plansza[pionekWiersz+skokWiersz][pionekKolumna+skokKolumna].ustawPionek(Stale.POLE_PUSTE);
			plansza[0][Stale.GRACZ.equals(kto)?1:2].ustawWartosc(
					plansza[0][Stale.GRACZ.equals(kto)?1:2].dajWartosc()+1); 
		} 
			
		return plansza;
	}
	
	public static Plansza ruchKomputera(Plansza aPlansza) {
		
		System.out.println("\n\n Ruch komputera:" );
		Lisc drzewo = new Lisc(aPlansza.dajPlansze());
		GeneratorRuchow.dajRuchy(Stale.KOMPUTER, aPlansza.dajPlansze(), drzewo, 0);
		if (drzewo.dajNajRuch(true) == null) return null;
		aPlansza.ustawPlansze(drzewo.dajNajRuch(true).dajPlansze());
		aPlansza.ustawBicie(drzewo.dajNajRuch(true).jestBicie());
				
		return aPlansza;		
	}

	public static Plansza ruchGracza(Plansza aPlansza) throws IOException {
				
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		while (true) {
		System.out.println("Podaj ruch w nastêpuj¹cy sposób:  'b1 lewo' ");
		System.out.println("Poprawne ruch: prawo, lewo, bicie_prawo, bicie_lewo, exit, pokaz");
			
		String command = br.readLine();
		StringTokenizer commandTokens = new StringTokenizer(command);				
		
		String pole = commandTokens.nextToken().toUpperCase();
		
		if (pole.length() < 2) {
			System.out.println("Podaj poprawne pole planszy.");
			continue;
		} else if (Stale.POKAZ.equals(pole)) {
			aPlansza.pokazPlansze();	
			continue;
		} else if (Stale.WYJSCIE.equals(pole)) {
			System.out.println("Koniec.");
			System.exit(0);
		} else if ("12345678".indexOf(pole.substring(1, 2)) == -1
				   || "ABCDEFGH".indexOf(pole.substring(0, 1)) == -1
				   || aPlansza.dajPlansze()["12345678".indexOf(pole.substring(1, 2))+1]["ABCDEFGH".indexOf(pole.substring(0, 1))+1] == null
				   || Arrays.asList(Stale.BIALE).contains(
						   aPlansza.dajPlansze()["12345678".indexOf(pole.substring(1, 2))+1]["ABCDEFGH".indexOf(pole.substring(0, 1))+1].dajPionek())
				   ) {
			System.out.println("Mo¿esz poruszaæ tylko pionkami x.");
			continue;
		} else if(commandTokens.countTokens() != 1) {
			System.out.println("Proszê podaj pionka i komendê.");
			continue;
		}

		String pionek = aPlansza.dajPlansze()["12345678".indexOf(pole.substring(1, 2))+1]["ABCDEFGH".indexOf(pole.substring(0, 1))+1].dajPionek();
		String ruch = commandTokens.nextToken();
				
		if (Stale.RUCH_PRAWO.equals(ruch) || Stale.RUCH_LEWO.equals(ruch)) {
			
			for (int k=1; k<=Stale.ROZMIAR_PLANSZY; k++)
	    		for (int w=1; w<=Stale.ROZMIAR_PLANSZY; w++) 
	        	    if (aPlansza.dajPlansze()[w][k]!=null && 
	        		    aPlansza.dajPlansze()[w][k].dajPionek().equals(pionek)) {
	        		 	        		 
	        		    if (Ruch.mogeRuszyc(ruch, Stale.GRACZ, aPlansza.dajPlansze(), w, k, true)) {		        			
		        			aPlansza.ustawPlansze(Ruch.move(ruch, Stale.GRACZ, aPlansza.dajPlansze(), w, k));
		        			return aPlansza;	        			 
	        		    }
		            }	        	 
	    } else if (Stale.BICIE_PRAWO.equals(ruch) || Stale.BICIE_LEWO.equals(ruch)) {
			
	    	for (int k=1; k<=Stale.ROZMIAR_PLANSZY; k++)
	    		for (int w=1; w<=Stale.ROZMIAR_PLANSZY; w++) 
	        	    if (aPlansza.dajPlansze()[w][k]!=null && 
	        		    aPlansza.dajPlansze()[w][k].dajPionek().equals(pionek)) {
	        		 	        		 
		        		if (Bicie.mogeBic(ruch, Stale.GRACZ, aPlansza.dajPlansze(), w, k, true)) {
		        			
		        			 Bicie.bicie(ruch, Stale.GRACZ, aPlansza.dajPlansze(), w, k, true);		        			
		        			 aPlansza.ustawBicie(true);
		        			 return aPlansza;
		        		 } else 
		        			 System.out.println("Nie mo¿esz biæ.");
	        	    }		
		} else 
			System.out.println("Nierozpoznana komenda.");	
		}			
	}
*/
	public static boolean czyNaPlanszy(int wiersz, int kolumna) {
		
		return wiersz<=Stale.ROZMIAR_PLANSZY  && kolumna <=Stale.ROZMIAR_PLANSZY 
				&& wiersz>=0 && kolumna>=0;
	}
}
