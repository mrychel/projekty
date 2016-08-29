package mini_max;

import static gra.Stale.*;
import gra.Plansza;
import gra.Plansza.Pole;

public class Heurystyka {
		
	public static int wyliczWartoscPlanszy(Plansza plansza) {
		
		int suma = 0;
						
		// im bli¿ej koñca planszy tym wiêksza wartoœæ pola
		for (int x = 0; x < ROZMIAR_PLANSZY; x++) 
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) {
            	Pole pole = plansza.dajPlansze()[x][y];
            	if (GRACZ.equals(pole.dajPionek())) {
            		suma += pole.dajWartosc()*(-1);            	
            		// premia za potencjalna damke
            		if (y == 1) suma += pole.dajWartosc()*(-50);
            	}
            	if (KOMPUTER.equals(pole.dajPionek())) {
            		suma += (9-pole.dajWartosc())*(1);
            		// premia za potencjalna damke
            		if (y == 8) suma += pole.dajWartosc()*(50);
            	}
            }
		// premia za bicie		
		if (plansza.dajBicie())
			suma += 10*(GRACZ.equals(plansza.czyjRuch())?(-1):1);
		// premia za damki
		suma += (-50)*plansza.ilosc_damek_gracza;
		suma += 50*plansza.ilosc_damek_komputera;
		return suma;
	}
}