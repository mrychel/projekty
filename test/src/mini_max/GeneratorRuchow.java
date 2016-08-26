package mini_max;
import gra.Plansza.Pole;
import static gra.Ruch.*;
import static gra.Stale.*;

import java.awt.Point;
import java.util.ArrayList;

import mini_max.Heurystyka;
import mini_max.Lisc;

 

public class GeneratorRuchow {	
	
    public static void budujPodDrzewo(Lisc korzen, int aktualnyPoziom) {
    		
		if (aktualnyPoziom > GLEBOKOSC_DRZEWA) return; 		
		
		ArrayList<Point> mmm = dajRuchy(korzen);
		
		// biore wszystkie pola mogace wykonac jakis ruch/bicie
		for (Point skad : dajRuchy(korzen))
		
			// dla kazdego z tych pol pobieram wszystkie mozliwe ruchy/bicia
			for (Point dokad : dajRuchy(korzen, skad)) {
			
				// tworze nowy lisc zawierajacy plansze po wykonaniu tego ruchu, 
				// a jesli jestem na najnizszym poziomie to obliczam wartosc mini-maksowa takiej planszy
				
				Lisc nowyLisc = new Lisc(korzen);
				nowyLisc.wypiszPlansze("1");
        		wykonajRuch(nowyLisc, skad, dokad);
        		nowyLisc.wypiszPlansze("2");
        		korzen.dajLiscie().add(nowyLisc);
        		
        		if (aktualnyPoziom == GLEBOKOSC_DRZEWA) 
        			nowyLisc.ustawWartosc(
        					Heurystyka.wyliczWartoscPlanszy(nowyLisc.czyjRuch(), nowyLisc));	
        	}
		/*
		for (Lisc lisc : korzen.getChildren()) 
		     budujPodDrzewo(!kto, lisc, aktualnyPoziom++);*/
	}  
}