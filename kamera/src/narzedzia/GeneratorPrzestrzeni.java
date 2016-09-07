package narzedzia;

import interfejs_uzytkownika.Matryca;

public class GeneratorPrzestrzeni {
			
	public GeneratorPrzestrzeni(Matryca m) {		
		dajPrzestrzen(m, new Punkt3D(500, 500, 60), new Punkt3D(250, 350, 460));
		dajPrzestrzen(m, new Punkt3D(40, 30, 20), new Punkt3D(500, 10, 10));
		dajPrzestrzen(m, new Punkt3D(-100, -100, -20), new Punkt3D(100, 100, 100));
		//dajPrzestrzen(m, new Punkt3D(-500, -500, -500), new Punkt3D(500, 500, 500));
	}
	
	public void dajPrzestrzen(Matryca pl, Punkt3D poczatek, Punkt3D wymiar) {
		
		for (int i = 0; i<8; i++) {			
			pl.punkty.add(new Punkt3D(poczatek.x+( (i&1)==0 ? wymiar.x : 0),
										  poczatek.y+( (i&2)==0 ? wymiar.y : 0),
										  poczatek.z+( (i&4)==0 ? wymiar.z : 0)));
		}
		int m = pl.punkty.size();
		
		pl.odcinki.add(new Odcinek(m-1, m-2));
		pl.odcinki.add(new Odcinek(m-2, m-4));
		pl.odcinki.add(new Odcinek(m-3, m-4));
		pl.odcinki.add(new Odcinek(m-3, m-1));
		
		pl.odcinki.add(new Odcinek(m-5, m-6));
		pl.odcinki.add(new Odcinek(m-6, m-8));
		pl.odcinki.add(new Odcinek(m-7, m-8));
		pl.odcinki.add(new Odcinek(m-7, m-5));
		
		pl.odcinki.add(new Odcinek(m-1, m-5));
		pl.odcinki.add(new Odcinek(m-2, m-6));
		pl.odcinki.add(new Odcinek(m-3, m-7));
		pl.odcinki.add(new Odcinek(m-4, m-8));
	}	
}
