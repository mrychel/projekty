package narzedzia;

import java.awt.Color;
import java.util.Random;

import algorytmy.Algorytmy;

public class GeneratorPrzestrzeni {
			
	public GeneratorPrzestrzeni(Algorytmy m) {
		dajPrzestrzen(m, new Punkt3D(0, 0, 0), new Punkt3D(20, 20, 20));		
		dajPrzestrzen(m, new Punkt3D(980, 0, 0), new Punkt3D(20, 20, 20));
		dajPrzestrzen(m, new Punkt3D(980, 0, 980), new Punkt3D(20, 20, 20));
		dajPrzestrzen(m, new Punkt3D(0, 0, 980), new Punkt3D(20, 20, 20));
		
		dajPrzestrzen(m, new Punkt3D(0, 0, 40), new Punkt3D(20, 20, 20));		
		dajPrzestrzen(m, new Punkt3D(40, 0, 0), new Punkt3D(250, 10, 10));
		
		dajPrzestrzen(m, new Punkt3D(500, 0, 260), new Punkt3D(250, 350, 440));
		dajPrzestrzen(m, new Punkt3D(500, 0, 20), new Punkt3D(100, 100, 100));
		dajPrzestrzen(m, new Punkt3D(500, 0, 750), new Punkt3D(450, 500, 250));
	}
	
	public void dajPrzestrzen(Algorytmy pl, Punkt3D poczatek, Punkt3D wymiar) {
		
		// Obie ponizsze tablice stalych zawieraja schematy numerow wierzcholkow
		// dla odcinkow i plaszczyzn.
		// Pozwala to w bardzo latwy sposob przejsc z ksztaltu szescianow
		// do dowolnych ukladow odcinkow lub bryl geometrycznych.
		// Reszta programu nie ogranicza mozliwosci wyboru wierzcholkow.
		
		final int[][] SCHEMAT_KONTUROW_SZESCIAN = {
			{1, 2}, {2, 4}, {3, 4}, {3, 1},
			{5, 6}, {6, 8}, {7, 8}, {7, 5},
			{1, 5}, {2, 6}, {3, 7}, {4, 8}
		};
		
		final int[][] SCHEMAT_POWIERZCHNI_SZESCIAN = {
			{1, 2, 4, 3}, // przod
			{1, 2, 6, 5}, // spod
			{4, 3, 7, 8}, // gora
			{2, 4, 8, 6}, // prawy bok
			{1, 3, 7, 5}, // lewy bok
			{5, 6, 8, 7}  // tyl
		};
		
		Random rand = new Random();
		Color kolor_konturu = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		Color kolor_wypelnienia = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		
		for (int i = 0; i < 8; i++) {			
			pl.punkty.add(new Punkt3D(poczatek.x+( (i&1)==0 ? wymiar.x : 0),
									  poczatek.y+( (i&2)==0 ? wymiar.y : 0),
									  poczatek.z+( (i&4)==0 ? wymiar.z : 0)));
		}
		
		int m = pl.punkty.size();
		
		for (int k = 0; k < SCHEMAT_KONTUROW_SZESCIAN.length; k++) {
			Odcinek o = new Odcinek(m-SCHEMAT_KONTUROW_SZESCIAN[k][0], m-SCHEMAT_KONTUROW_SZESCIAN[k][1]);
			o.kolor_konturu = Color.RED;
			pl.odcinki.add(o);
		}
		
		for (int k = 0; k < SCHEMAT_POWIERZCHNI_SZESCIAN.length; k++) {
			Plaszczyzna p = new Plaszczyzna();			
			p.kolor_konturu = kolor_konturu;
			p.kolor_wypelnienia = kolor_wypelnienia;
			for (int w = 0; w < SCHEMAT_POWIERZCHNI_SZESCIAN[k].length; w++)
				p.plaszczyzna.add(m-SCHEMAT_POWIERZCHNI_SZESCIAN[k][w]);
			pl.plaszczyzny.add(p);
		};		
	}	
}
