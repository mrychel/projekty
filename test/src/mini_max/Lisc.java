package mini_max;

import static gra.Stale.ROZMIAR_PLANSZY;

import java.lang.Math;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import gra.Plansza.Pole;

public class Lisc {
    private LinkedList<Lisc>  potomkowie = new LinkedList<Lisc>();
    private Pole[][] plansza;
    private double wartosc= 0;
    private boolean jestBicie;

   public Lisc(Pole[][] plansza) {
	   
	   this.plansza = kopiujPlansze(plansza);
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
    
   public double minimax() {
   	   	 
   	   /*
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
   	   }*/
	   return 10;
   }
    
   public Lisc dajNajRuch(){
       
	   if(potomkowie.isEmpty())
           return null;
   
        
	   Lisc naj = potomkowie.getFirst();
	   /*
	   for(Iterator<Lisc> i = potomkowie.iterator(); i.hasNext();){
           Lisc potomek = (Lisc) i.next();
           if (potomek.jestBicie()) return potomek;
       }*/
        //double maxPunkty = (czyKomputer ? Double.MIN_VALUE : Double.MAX_VALUE);
       int maxPunkty = Integer.MIN_VALUE;       
       for(Lisc potomek : potomkowie){    	   
    	   double wartosc = potomek.minimax();
    	   naj = potomek;
           /*if(naj == null || wartosc * (czyKomputer ? 1 : -1) > maxPunkty * (czyKomputer ? 1 : -1)){
            	maxPunkty = wartosc;
                naj = potomek;
            }*/
        }
        
        return naj;
    }
    
    public boolean jestBicie() {
    	
    	return jestBicie;
    }

    public void ustawJestBicie(boolean jestBicie) {
    	
    	this.jestBicie = jestBicie;
    }
    
    private Pole[][] kopiujPlansze(Pole[][] plansza) {
		Pole[][] nowaPlansza = new Pole[ROZMIAR_PLANSZY][ROZMIAR_PLANSZY];
		System.arraycopy(plansza, 0, nowaPlansza, 0, plansza.length);
		return nowaPlansza;
	}	
}