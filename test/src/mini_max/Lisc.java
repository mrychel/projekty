package mini_max;
import gra.Pole;

import java.lang.Math;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Lisc {
    private LinkedList<Lisc>  potomkowie = new LinkedList<Lisc>();
    private Pole[][] plansza;
    private double wartosc= 0;
    private boolean jestBicie;

   public Lisc(Pole[][] plansza) {
	   
	   this.plansza = plansza;
   }
    
   public Pole[][] dajPlansze() {
	   
       return this.plansza;
   }
    
   public List<Lisc> getChildren() {
	   
    	return this.potomkowie;
   }
    
   public double dajWartosc() {
	   
		return wartosc;    	
   }
    
   public void ustawWartosc(double wartosc) {
    	
    	this.wartosc = wartosc;
   }
    
   public double minimax(boolean czyKomputer) {
   	   	 
   	   if(this.getChildren().isEmpty()) 
   		   return this.dajWartosc();
   	      		
   	   if(czyKomputer){  // je¿eli komputer maksymalizyje punkty
       
   		   double a = Double.MIN_VALUE;
           for(Iterator<Lisc> i = potomkowie.iterator(); i.hasNext();) {
               Lisc drzewo =(Lisc)i.next();
        	   a = Math.max(a, drzewo.minimax(!czyKomputer));
           }
           return a;
       } else {   // je¿eli gracz - minimalizuje
    	   
           double a = Double.MAX_VALUE;
           for(Iterator<Lisc> i = potomkowie.iterator(); i.hasNext();) {
        	   Lisc drzewo = (Lisc)i.next();
        	   a = Math.min(a, drzewo.minimax(!czyKomputer));
           }
           return a;
   	   }
   }
    
   public Lisc dajNajRuch(boolean czyKomputer){
       
	   if(potomkowie.isEmpty())
           return null;
   
        Lisc naj = null;
        for(Iterator<Lisc> i = potomkowie.iterator(); i.hasNext();){
        	Lisc potomek = (Lisc) i.next();
        	if (potomek.jestBicie()) return potomek;
    	}
        double maxPunkty = (czyKomputer ? Double.MIN_EXPONENT : Double.MAX_VALUE);
        for(Iterator<Lisc> i = potomkowie.iterator(); i.hasNext();){
        	Lisc potomek = (Lisc) i.next();
            double wartosc = potomek.minimax(czyKomputer);
            if(naj == null || wartosc * (czyKomputer ? 1 : -1) > maxPunkty * (czyKomputer ? 1 : -1)){
            	maxPunkty = wartosc;
                naj = potomek;
            }
        }
        
        return naj;
    }
    
    public boolean jestBicie() {
    	
    	return jestBicie;
    }

    public void ustawJestBicie(boolean jestBicie) {
    	
    	this.jestBicie = jestBicie;
    }
}