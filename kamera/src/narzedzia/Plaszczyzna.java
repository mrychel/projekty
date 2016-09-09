package narzedzia;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Plaszczyzna implements Comparable<Plaszczyzna> {
	public ArrayList<Integer> plaszczyzna = new ArrayList<Integer>();
	public Polygon rzut = new Polygon();
	public int z_rzutu;	
	public Color kolor_konturu = new Color(0, 0, 0), kolor_wypelnienia = new Color(0, 0, 0);
	
	public void reset() {
		rzut.reset();
		z_rzutu = 0;
	}

	@Override
	public int compareTo(Plaszczyzna o) {		
		return this.z_rzutu - o.z_rzutu;
	}
}
