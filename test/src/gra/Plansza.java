package gra;

import static gra.Stale.*;

// przechowuje stan planszy (gry)
public class Plansza {                                                                                                            

	private Pole[][] plansza = new Pole[ROZMIAR_PLANSZY][ROZMIAR_PLANSZY];  
	private String komunikat = "";
	private Boolean czyjRuch = GRACZ; 
	private boolean bicie = false;  
	private final String strPlanszaGotowa = "Plansza gotowa do gry!";    
                                                                                                                                
	public Plansza() {		
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) 
            	plansza[x][y] = new Pole(0, PUSTE_POLE);	
	}	
	
	public Plansza(Plansza plansza) {		
		ustawPlansze(plansza);	
	}
	                                                                                                                            
	public Pole[][] dajPlansze() {                                                                                                                           
		return plansza;                                                                                                           
	}                                                                                                                           
	                               
	public void ustawPlansze(Plansza plansza) {
		this.komunikat = plansza.komunikat;
		this.czyjRuch = plansza.czyjRuch;
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) 
            	this.plansza[x][y] = new Pole(0, plansza.dajPlansze()[x][y].dajPionek());
	}

	public void ustawBicie(boolean bicie) {		
		this.bicie = bicie;		
	}
	
	public boolean dajBicie() {
		return bicie;
	}
	
	public void ustawKomuniakt(String komunikat) {
		this.komunikat = komunikat;		
	}
	
	public String dajKomunikat() {
		return komunikat;
	}
	
	public void czyjRuch(Boolean czyjRuch) {
		this.czyjRuch = czyjRuch;		
	}
	
	public Boolean czyjRuch() {
		return czyjRuch;
	}
	
	public void wypiszPlansze() {
		for (int y = 0; y < ROZMIAR_PLANSZY; y++) {
            System.out.println();
			for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            	System.out.print(plansza[x][y].dajPionek()+" ");
		}     		
	}
		
	public void rozstawPionki() {
		this.komunikat = strPlanszaGotowa;
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) 
            	plansza[x][y].ustawPionek(PUSTE_POLE);	
		    	
    	plansza[1][0].ustawPionek(KOMPUTER);
    	plansza[3][0].ustawPionek(KOMPUTER);
    	plansza[5][0].ustawPionek(KOMPUTER);
    	plansza[7][0].ustawPionek(KOMPUTER);
    	plansza[0][1].ustawPionek(KOMPUTER);
    	plansza[2][1].ustawPionek(KOMPUTER);
    	plansza[4][1].ustawPionek(KOMPUTER);
    	plansza[6][1].ustawPionek(KOMPUTER);
    	plansza[1][2].ustawPionek(KOMPUTER);
    	plansza[3][2].ustawPionek(KOMPUTER);
    	plansza[5][2].ustawPionek(KOMPUTER);
    	plansza[7][2].ustawPionek(KOMPUTER);
    	
    	plansza[1][6].ustawPionek(GRACZ);
    	plansza[3][6].ustawPionek(GRACZ);
    	plansza[5][6].ustawPionek(GRACZ);
    	plansza[7][6].ustawPionek(GRACZ);
    	plansza[0][7].ustawPionek(GRACZ);
    	plansza[2][7].ustawPionek(GRACZ);
    	plansza[4][7].ustawPionek(GRACZ);
    	plansza[6][7].ustawPionek(GRACZ);
    	plansza[0][5].ustawPionek(GRACZ);
    	plansza[2][5].ustawPionek(GRACZ);
    	plansza[4][5].ustawPionek(GRACZ);
    	plansza[6][5].ustawPionek(GRACZ);
    }   
	
	public class Pole {
		
		private int wartosc;
		private Boolean pionek;
		
		public Pole(int wartosc, Boolean pionek) {			
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
                                                                                                                                