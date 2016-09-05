package narzedzia;

public class GeneratorPrzestrzeni {
	
	Punkt3D poczatek, wymiary;
		
	public GeneratorPrzestrzeni() {
		Plaszczyzny pp = new Plaszczyzny();
		dajPrzestrzen(pp, new Punkt3D(50, 50, 60), new Punkt3D(250, 350, 460));
	}
	
	public void dajPrzestrzen(Plaszczyzny pl, Punkt3D poczatek, Punkt3D wymiar) {
		
		for (int i = 0; i<8; i++) {			
			pl.przestrzen.add(new Punkt3D(poczatek.x+( i%1==0 ? wymiar.x : 0),
										  poczatek.y+( i%2==0 ? wymiar.y : 0),
										  poczatek.z+( i%4==0 ? wymiar.z : 0)));
		}
		int m = pl.przestrzen.size();
		System.out.println(pl.przestrzen.get(m-1));
		System.out.println(pl.przestrzen.get(m));
		pl.odcinki.add(new Odcinek(m-1, m-2));
		pl.odcinki.add(new Odcinek(m-2, m-3));
		pl.odcinki.add(new Odcinek(m-3, m-4));
		pl.odcinki.add(new Odcinek(m-4, m-1));
		
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
