<<<<<<< HEAD
package mini_max;
import gra.Plansza.Pole;
import static gra.Ruch.*;
import static gra.Stale.*;
import java.awt.Point;
import mini_max.Heurystyka;
import mini_max.Lisc;

 

public class GeneratorRuchow {	
	
    public static void budujPodDrzewo(Boolean kto, Lisc korzen, int aktualnyPoziom) {
    		
		if (aktualnyPoziom > GLEBOKOSC_DRZEWA) return; 		
		
		// biore wszystkie pola mogace wykonac jakis ruch/bicie
		for (Point skad : dajRuchy(kto, korzen.dajPlansze()))
		
			// dla kazdego z tych pol pobieram wszystkie mozliwe ruchy/bicia
			for (Point dokad : dajRuchy(kto, korzen.dajPlansze(), skad)) {
			
				// tworze nowy lisc zawierajacy plansze po wykonaniu tego ruchu, 
				// a jesli jestem na najnizszym poziomie to obliczam wartosc mini-maksowa takiej planszy
        		Lisc nowyLisc = new Lisc(korzen.dajPlansze());
        		wykonajRuch(nowyLisc.dajPlansze(), skad, dokad);
        		korzen.getChildren().add(nowyLisc);
        		if (aktualnyPoziom == GLEBOKOSC_DRZEWA) 
        			nowyLisc.ustawWartosc(
        					Heurystyka.wyliczWartoscPlanszy(kto, nowyLisc));	
        	}
		
		for (Lisc lisc : korzen.getChildren()) 
		     budujPodDrzewo(!kto, lisc, aktualnyPoziom++);
	}  
}
=======
package mini_max;
import gra.Plansza.Pole;
import static gra.Ruch.*;
import static gra.Stale.*;
import java.awt.Point;
import mini_max.Heurystyka;
import mini_max.Lisc;

 

public class GeneratorRuchow {	
	
    public static void budujPodDrzewo(Boolean kto, Lisc korzen, int aktualnyPoziom) {
    		
		if (aktualnyPoziom > GLEBOKOSC_DRZEWA) return; 		
		
		// biore wszystkie pola mogace wykonac jakis ruch/bicie
		for (Point skad : dajRuchy(kto, korzen.dajPlansze()))
		
			// dla kazdego z tych pol pobieram wszystkie mozliwe ruchy/bicia
			for (Point dokad : dajRuchy(kto, korzen.dajPlansze(), skad)) {
			
				// tworze nowy lisc zawierajacy plansze po wykonaniu tego ruchu, 
				// a jesli jestem na najnizszym poziomie to obliczam wartosc mini-maksowa takiej planszy
        		Lisc nowyLisc = new Lisc(korzen.dajPlansze());
        		wykonajRuch(nowyLisc.dajPlansze(), skad, dokad);
        		korzen.getChildren().add(nowyLisc);
        		if (aktualnyPoziom == GLEBOKOSC_DRZEWA) 
        			nowyLisc.ustawWartosc(
        					Heurystyka.wyliczWartoscPlanszy(kto, nowyLisc));	
        	}
		
		for (Lisc lisc : korzen.getChildren()) 
		     budujPodDrzewo(!kto, lisc, aktualnyPoziom++);
	}  
}
	
	

>>>>>>> branch 'master' of https://github.com/mrychel/test.git
