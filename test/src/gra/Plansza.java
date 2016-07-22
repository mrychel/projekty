package gra;

import java.util.Arrays;

public class Plansza {                                                                                                            

	private Pole[][] plansza = new Pole[Stale.ROZMIAR_PLANSZY+1][Stale.ROZMIAR_PLANSZY+1];            
	
	private static final String SPACE = " ";
    private static final String LABEL_Y = "  A B C D E F G H ";
    private static final char WHITE_PIECE = 'o';
    private static final char BLACK_PIECE = 'x';   
    private static final char PLAYABLE_FIELD = '-';
    private static final char UNPLAYABLE_FIELD = ' ';
	
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
	                                                                                                                            
	public void pokazPlansze() {		
		
        StringBuilder tableString = new StringBuilder();
        tableString.append("==== TWÓJ RUCH ====").append(System.lineSeparator());
        //tableString.append(" score ").append(table.playerB.getPoints()).append(System.lineSeparator());
        tableString.append(LABEL_Y).append(System.lineSeparator());
        for (int y = 1; y <= Stale.ROZMIAR_PLANSZY; y++) {
            tableString.append(y).append(SPACE);
            for(int x = 1; x <= Stale.ROZMIAR_PLANSZY; x++) {
                tableString.append(
                		getFigureRepresentation(plansza[y][x] == null?"":plansza[y][x].dajPionek())).append(SPACE);
            }
            tableString.append(y).append(System.lineSeparator());
        }
        tableString.append(LABEL_Y).append(System.lineSeparator());
        //tableString.append(table.playerA.getName()).append(" score ").append(table.playerA.getPoints()).append(System.lineSeparator());
        
        System.out.println(tableString.toString());	
        // -------------------------------------------------------
        String pionek = "";
		int sB = 0; int sC = 0;
		for (int k=1; k<=Stale.ROZMIAR_PLANSZY; k++)
    		for (int w=1; w<=Stale.ROZMIAR_PLANSZY; w++) {
	        	pionek = (plansza[w][k]!=null)?plansza[w][k].dajPionek():"";		            
	        	if (plansza[w][k]!=null && !Stale.POLE_PUSTE.equals(pionek))
	            	if(Arrays.asList(Stale.BIALE).contains(pionek))		            		            		
	            		sB += plansza[w][k].dajWartosc()*1;		            	
	        		if(Arrays.asList(Stale.CZARNE).contains(pionek))
	            		sC += (9-plansza[w][k].dajWartosc())*(-1);
            }
		
		System.out.println("C: "+sC+" B: "+sB);
		System.out.println("C damki: "+plansza[0][1].dajWartosc()+" B damki: "+plansza[0][2].dajWartosc());
        /*
        for (int row =0; row <= Stale.ROZMIAR_PLANSZY; row++)
	      {
			 System.out.println();
	         for (int column =0; column <= Stale.ROZMIAR_PLANSZY; column++)
	         {
	            if(plansza!= null && plansza[row][column]!= null)
	            {
	            	System.out.print("["+plansza[row][column].dajPionek()+"]");
	            } 
	            else
	            {
	            	System.out.print(" * ");
	            }  
	         }
	      }
		 System.out.println("\n___________________________________________"); */
	}   
	
	private char getFigureRepresentation(String figura) {
        if (figura == "") return UNPLAYABLE_FIELD;
        if (Arrays.asList(Stale.BIALE).contains(figura)) return WHITE_PIECE;
        if (Arrays.asList(Stale.CZARNE).contains(figura)) return BLACK_PIECE;
        if (Stale.POLE_PUSTE.equals(figura)) return PLAYABLE_FIELD; 
        return UNPLAYABLE_FIELD;
    }
	
	public void rozstawPionki() {
    	for (int w=1; w<=Stale.ROZMIAR_PLANSZY; w++)
    		for (int k=1; k<=Stale.ROZMIAR_PLANSZY; k++)
    			if (!((w%2==0 && k%2==0) || (w%2!=0 && k%2!=0))) {    	
    				int t = ((8*(w-1))+k)/2;    			
    				if (w == 1) plansza[w][k] = new Pole(1, Stale.BIALE[t-1]);
    				if (w == 2) plansza[w][k] = new Pole(2, Stale.BIALE[t]);
    				if (w == 3) plansza[w][k] = new Pole(3, Stale.BIALE[t-1]);
    				if (w == 4) plansza[w][k] = new Pole(4, Stale.POLE_PUSTE);
    				if (w == 5) plansza[w][k] = new Pole(5, Stale.POLE_PUSTE);
    				if (w == 6) plansza[w][k] = new Pole(6, Stale.CZARNE[t-20]);    		
    				if (w == 7) plansza[w][k] = new Pole(7, Stale.CZARNE[t-21]);
    				if (w == 8) plansza[w][k] = new Pole(8, Stale.CZARNE[t-20]);   				    				
    			} else {
    				plansza[w][k] = null;
    			}
    	plansza[0][1] = new Pole(0, "");
    	plansza[0][2] = new Pole(0, "");
    }   
}                                                                                                                               
                                                                                                                                