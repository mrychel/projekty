package narzedzia;

public class Parametr3D {
	
	public float x,y,z;
	private String nazwa = "";
	public double sinx, siny, sinz,
				   cosx, cosy, cosz,
				   jedenMinusCosx, jedenMinusCosy, jedenMinusCosz;
	private boolean czyPrzeliczacFunkcjeTryg = false;
	
	/*
	public Parametr3D() {		
	}
	*/
	public Parametr3D(String nazwa) {
		this.nazwa = nazwa;
	}
	/*
	public Parametr3D(String nazwa, float x, float y, float z) {
		this.nazwa = nazwa;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public void ustaw(Parametr3D parametry) {
    	this.x = parametry.x;
    	this.y = parametry.y;
    	this.z = parametry.z;
    }
	*/
	public String dajNazwe() {
		return this.nazwa;
	}
	
	public void przeliczFunkcjeTryg() {
		if (czyPrzeliczacFunkcjeTryg) {
			sinx = Math.sin(x);
			siny = Math.sin(y);
			sinz = Math.sin(z);
			
			cosx = Math.cos(x);
			cosy = Math.cos(y);
			cosz = Math.cos(z);
			
			jedenMinusCosx = 1-Math.cos(x);
			jedenMinusCosy = 1-Math.cos(y);
			jedenMinusCosz = 1-Math.cos(z);
		}
	}
	
	public void ustawPrzeliczanieFunkcjiTryg(boolean czyPrzeliczacFunkcjeTryg) {
		this.czyPrzeliczacFunkcjeTryg = czyPrzeliczacFunkcjeTryg;
	}
}
