package mini_max;
import gra.Bicie;
import gra.Ruch;
import gra.Pole;
import gra.Stale;

import java.util.Arrays;
import java.util.Iterator;

import mini_max.Heurystyka;
import mini_max.Lisc;

 

public class GeneratorRuchow {	
	
    public static void dajRuchy(String kto, Pole[][] plansza, Lisc drzewo, int aktualnyPoziom) {
    	
		String nastepnyRuch;
		int najwiekszyPoziom = Stale.GLEBOKOSC_DRZEWA;
		if (aktualnyPoziom <= najwiekszyPoziom) {
			
			String pionek = null;
			String[] pionki = new String[12];
			
			if (Stale.GRACZ.equals(kto))
				
				pionki = Stale.CZARNE;
			else 
				pionki = Stale.BIALE;	
			
			for (int k=1; k<=Stale.ROZMIAR_PLANSZY; k++)
	    		for (int w=1; w<=Stale.ROZMIAR_PLANSZY; w++) {
		        	
		        	Lisc potencjalnyRuchLewo;
		        	Lisc potencjalnyRuchPrawo;
		        	Lisc potencjalneBicieLewo;
		        	Lisc potencjalneBiciePrawo;
			        
		        	if (plansza[w][k]!=null) {
		        		
		        		pionek = plansza[w][k].dajPionek();
		        	
		        		if (Arrays.asList(pionki).contains(pionek)) {

			            	if (Ruch.mogeRuszycLewo(kto, plansza, w, k, false)) {
			            	
			            		Pole[][] x= copyBoard(plansza);		            		 
			            		x = Ruch.ruchLewo(kto, x, w, k);
			            		potencjalnyRuchLewo = new Lisc(x);
			            		drzewo.getChildren().add(potencjalnyRuchLewo);
			            		if (aktualnyPoziom == najwiekszyPoziom) 
			            			potencjalnyRuchLewo.ustawWartosc(
			            					Heurystyka.wyliczWartoscPlanszy(kto, potencjalnyRuchLewo, Stale.RUCH));	
			            	}
			        
			            	if (Ruch.mogeRuszycPrawo(kto, plansza, w, k, false)) {
			            		
			            		Pole[][] y= copyBoard(plansza);
			            		y = Ruch.ruchPrawo(kto, y, w, k);
			            		potencjalnyRuchPrawo = new Lisc(y);
			            		drzewo.getChildren().add(potencjalnyRuchPrawo); 
			            		if (aktualnyPoziom == najwiekszyPoziom) 
			            			potencjalnyRuchPrawo.ustawWartosc(
			            					Heurystyka.wyliczWartoscPlanszy(kto, potencjalnyRuchPrawo, Stale.RUCH));
			            	}
			            	
			            	if (Bicie.mogeBicLewo(kto, plansza, w, k, false)) {
			            		
			            		Pole[][] z = copyBoard(plansza);
			            		z= Bicie.bicieLewo(kto, z, w, k, false);
			            		potencjalneBicieLewo = new Lisc(z);
			            		potencjalneBicieLewo.ustawJestBicie(true);
			            		drzewo.getChildren().add(potencjalneBicieLewo);
			            		potencjalneBicieLewo.ustawWartosc(
			            				Heurystyka.wyliczWartoscPlanszy(kto, potencjalneBicieLewo, Stale.BICIE));
			
			            	}
			            	
			            	if (Bicie.mogeBicPrawo(kto, plansza, w, k, false)) {
			            		
			            		Pole[][]q= copyBoard(plansza);
			            		q= Bicie.biciePrawo(kto, q, w, k, false);		            		
			            		potencjalneBiciePrawo = new Lisc(q);
			            		potencjalneBiciePrawo.ustawJestBicie(true);
			            		drzewo.getChildren().add(potencjalneBiciePrawo);
			            		potencjalneBiciePrawo.ustawWartosc(
			            				Heurystyka.wyliczWartoscPlanszy(kto, potencjalneBiciePrawo, Stale.BICIE));
			            	} 
		        		}
		        	}
		        }
		         
		     }
			
		 nastepnyRuch = (Stale.GRACZ.equals(kto))? Stale.KOMPUTER: Stale.GRACZ;
		 for (Iterator<Lisc> i = drzewo.getChildren().iterator(); i.hasNext();) {
		 
			 Lisc potomek = (Lisc) i.next();
			 //if(!potomek.jestBicie()) 
				 dajRuchy(nastepnyRuch, potomek.dajPlansze(), potomek,  aktualnyPoziom+1);
		 }
	}
 
 
	public static Pole[][] copyBoard(Pole [][] plansza) {
		
	    Pole [][] nowaPlansza = new Pole[Stale.ROZMIAR_PLANSZY+1][Stale.ROZMIAR_PLANSZY+1];	
	    for (int k=0; k<=Stale.ROZMIAR_PLANSZY; k++)
    		for (int w=0; w<=Stale.ROZMIAR_PLANSZY; w++) {	       
	        	if (plansza[w][k]!=null) 	        	
	            	nowaPlansza[w][k] = new Pole(plansza[w][k].dajWartosc(), plansza[w][k].dajPionek());
	        	else	       
	        		nowaPlansza[w][k] = null;	        
	        }
	     
	    return nowaPlansza;
	}		
}
	
	

