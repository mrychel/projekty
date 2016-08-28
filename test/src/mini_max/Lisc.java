package mini_max;

import static gra.Stale.*;
import java.lang.Math;
import java.util.LinkedList;
import java.util.List;
import gra.Plansza;

public class Lisc extends Plansza {
    private LinkedList<Lisc>  potomkowie = new LinkedList<Lisc>();   
    private Integer wartosc= 0;    

   public Lisc(Plansza plansza) {
	   
	   super(plansza);
   }
       
   public List<Lisc> dajLiscie() {
	   
    	return this.potomkowie;
   }
    
   public Integer dajWartosc() {
	   
		return wartosc;    	
   }
    
   public void ustawWartosc(Integer wartosc) {
    	
    	this.wartosc = wartosc;
   }
    
   public Integer minimax() {
   	   
	   if (this.potomkowie.isEmpty()) 
   		   return this.dajWartosc();
   	      		
   	   // je¿eli komputer maksymalizyje punkty
       // je¿eli gracz - minimalizuje
    	   
       Integer wartosc = GRACZ.equals(this.czyjRuch()) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
       for (Lisc lisc : potomkowie) {        	   
    	   wartosc = GRACZ.equals(this.czyjRuch()) ? Math.min(wartosc, lisc.minimax()) 
    			   								   : Math.max(wartosc, lisc.minimax());
       }
       return wartosc;   	  
   }
    
   public Lisc dajNajRuch() {
       
	   if (potomkowie.isEmpty())
           return null;   
        
	   Lisc najLisc = potomkowie.getFirst();	 
	   Integer maxWartosc = GRACZ.equals(czyjRuch()) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
       
       for(Lisc potomek : potomkowie){    	   
    	   Integer wartosc = potomek.minimax();    	   
           if (wartosc * (GRACZ.equals(czyjRuch()) ? -1 : 1) > maxWartosc * (GRACZ.equals(czyjRuch()) ? -1 : 1)) {
            	maxWartosc = wartosc;
                najLisc = potomek;
            }
        }       
        return najLisc;
    }    
}