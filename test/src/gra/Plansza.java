package gra;

import java.util.Arrays;
import static gra.Stale.*;

// przechowuje stan planszy (gry)
public class Plansza {                                                                                                            

	private Pole[][] plansza = new Pole[ROZMIAR_PLANSZY][ROZMIAR_PLANSZY];  
	private boolean bicie = false;                                                                                         
                                                                                                                                
	public Plansza()                                                                                                              
	{   
		// null = puste pole
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) 
            	plansza[x][y] = new Pole(0, null);	
	}	                                                                                                                        
	                                                                                                                            
	public Pole[][] dajPlansze()                                                                                                  
	{                                                                                                                           
		return plansza;                                                                                                           
	}                                                                                                                           
	                               
	public void ustawPlansze(Pole[][] aPlansza)
	{
		this.plansza = aPlansza;
	}

	public void ustawBicie(boolean bicie) {
		
		this.bicie = bicie;		
	}
	
	public boolean dajBicie()
	{
		return bicie;
	}
	
	public void wypiszPlansze() {
		for (int y = 0; y < ROZMIAR_PLANSZY; y++) {
            System.out.println();
			for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            	System.out.print(plansza[x][y].dajPionek()+" ");
		}     
		
	}
	
	public void rozstawPionki() {
		// null = puste pole
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) 
            	plansza[x][y].ustawPionek(null);	
		
    	// true = biale
    	plansza[1][0].ustawPionek(false);
    	plansza[3][0].ustawPionek(false);
    	plansza[5][0].ustawPionek(false);
    	plansza[7][0].ustawPionek(false);
    	plansza[0][1].ustawPionek(false);
    	plansza[2][1].ustawPionek(false);
    	plansza[4][1].ustawPionek(false);
    	plansza[6][1].ustawPionek(false);
    	plansza[1][2].ustawPionek(false);
    	plansza[3][2].ustawPionek(false);
    	plansza[5][2].ustawPionek(false);
    	plansza[7][2].ustawPionek(false);
    	// false = czarne
    	plansza[1][6].ustawPionek(true);
    	plansza[3][6].ustawPionek(true);
    	plansza[5][6].ustawPionek(true);
    	plansza[7][6].ustawPionek(true);
    	plansza[0][7].ustawPionek(true);
    	plansza[2][7].ustawPionek(true);
    	plansza[4][7].ustawPionek(true);
    	plansza[6][7].ustawPionek(true);
    	plansza[0][5].ustawPionek(true);
    	plansza[2][5].ustawPionek(true);
    	plansza[4][5].ustawPionek(true);
    	plansza[6][5].ustawPionek(true);
    }   
	
	public class Pole {
		
		private int wartosc;
		private Boolean pionek;
		
		public Pole(int wartosc, Boolean pionek)	{
			
			this.wartosc = wartosc;
			this.pionek = pionek;		
		}
		
		public int dajWartosc()	{
			
			return wartosc;
		}
		
		public Boolean dajPionek() {
			
			return pionek;
		}
		
		public void ustawPionek(Boolean nowyPionek) {
			
			pionek= nowyPionek;
		}	
		
		public void ustawWartosc(int nowaWartosc) {
			
			wartosc = nowaWartosc;
		}	
	}
}                                                                                                                               
                                                                                                                                