package narzedzia;

import interfejs_uzytkownika.Matryca;

public class GeneratorPrzestrzeni {
	
	Punkt3D poczatek, wymiary;
		
	public GeneratorPrzestrzeni(Matryca m) {
		//Plaszczyzny pp = new Plaszczyzny();
		dajPrzestrzen(m, new Punkt3D(500, 500, 60), new Punkt3D(250, 350, 460));
		dajPrzestrzen(m, new Punkt3D(40, 30, 20), new Punkt3D(-100, -100, -100));
	}
	
	public void dajPrzestrzen(Matryca pl, Punkt3D poczatek, Punkt3D wymiar) {
		
		for (int i = 0; i<8; i++) {			
			pl.punkty.add(new Punkt3D(poczatek.x+( (i&1)==0 ? wymiar.x : 0),
										  poczatek.y+( (i&2)==0 ? wymiar.y : 0),
										  poczatek.z+( (i&4)==0 ? wymiar.z : 0)));
		}
		int m = pl.punkty.size();
		System.out.println(pl.punkty.get(m-1));
		//System.out.println(pl.punkty.get(m));
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
		
		//pl.plaszczyzny.add(null);
		/*
		Punkt3D p1 = new Punkt3D(poczatek.x, poczatek.y, poczatek.z);
		pl.przestrzen.add(p1);
		int i_p1 = pl.przestrzen.indexOf(p1);
		
		Punkt3D p2 = new Punkt3D(poczatek.x+wymiar.x, poczatek.y, poczatek.z);
		pl.przestrzen.add(p2);
		int i_p2 = pl.przestrzen.indexOf(p2);
		
		Punkt3D p3 = new Punkt3D(poczatek.x+wymiar.x, poczatek.y+wymiar.y, poczatek.z);
		pl.przestrzen.add(p3);
		int i_p3 = pl.przestrzen.indexOf(p3);*/
		
		
	}	
}
