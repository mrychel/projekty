package narzedzia;

import java.awt.Color;
import java.awt.Point;

public class Odcinek {
	public int a, b;
	public Point rzut_a, rzut_b;
	public Color kolor_konturu = new Color(0, 0, 0);
	
	public Odcinek(int a, int b) {
		this.a=a;
		this.b=b;
	}
}