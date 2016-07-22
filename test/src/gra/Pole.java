package gra;

public class Pole {
	
	private int wartosc;
	private String pionek;
	
	public Pole(int wartosc, String pionek)	{
		
		this.wartosc = wartosc;
		this.pionek = pionek;		
	}
	
	public int dajWartosc()	{
		
		return wartosc;
	}
	
	public String dajPionek() {
		
		return pionek;
	}
	
	public void ustawPionek(String nowyPionek) {
		
		pionek= nowyPionek;
	}	
	
	public void ustawWartosc(int nowaWartosc) {
		
		wartosc = nowaWartosc;
	}	
}
