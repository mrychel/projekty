package gra;

import java.util.Arrays;

public class Plansza {                                                                                                            

	private Pole[][] plansza = new Pole[8][8];  
	private boolean bicie = false;                                                                                         
                                                                                                                                
	public Plansza()                                                                                                              
	{                                                                                   
		rozstawPionki();                                                                                    
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
	
	private char getFigureRepresentation(String figura) {
       /*
		if (figura == "") return UNPLAYABLE_FIELD;
        if (Arrays.asList(Stale.BIALE).contains(figura)) return WHITE_PIECE;
        if (Arrays.asList(Stale.CZARNE).contains(figura)) return BLACK_PIECE;
        if (Stale.POLE_PUSTE.equals(figura)) return PLAYABLE_FIELD; 
        return UNPLAYABLE_FIELD;*/
		return 0;
    }
	
	public void rozstawPionki() {
		// null = puste pole
		for (int x = 0; x < 8; x++) 
            for (int y = 0; y < 8; y++) 
            	plansza[x][y] = new Pole(0, null);		
		
    	// true = biale
    	plansza[1][0].ustawPionek(true);
    	plansza[3][0].ustawPionek(true);
    	plansza[5][0].ustawPionek(true);
    	plansza[7][0].ustawPionek(true);
    	plansza[0][1].ustawPionek(true);
    	plansza[2][1].ustawPionek(true);
    	plansza[4][1].ustawPionek(true);
    	plansza[6][1].ustawPionek(true);
    	// false = czarne
    	plansza[1][6].ustawPionek(false);
    	plansza[3][6].ustawPionek(false);
    	plansza[5][6].ustawPionek(false);
    	plansza[7][6].ustawPionek(false);
    	plansza[0][7].ustawPionek(false);
    	plansza[2][7].ustawPionek(false);
    	plansza[4][7].ustawPionek(false);
    	plansza[6][7].ustawPionek(false);
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
                                                                                                                                