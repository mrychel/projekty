package narzedzia;

public class Parametr3D {
	
	public double x,y,z;	
	public double sinx, siny, sinz,
				  cosx, cosy, cosz,
				  jedenMinusCosx, jedenMinusCosy, jedenMinusCosz;
	public double wPoczatkowa = 0,
			      wMinimalna  = -999,
			      wMaksymalna = 999,
			      wSkoku = 10d;
	private String nazwa = "";
	private boolean czyPrzeliczacFunkcjeTryg = false;
		
	public Parametr3D(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public String dajNazwe() {
		return this.nazwa;
	}
	
	public void ustawWartosci(double wPoczatkowa, double wMinimalna, double wMaksymalna, double wSkoku) {
		this.wPoczatkowa = wPoczatkowa;
		this.wMinimalna = wMinimalna;
		this.wMaksymalna = wMaksymalna;
		this.wSkoku = wSkoku;
		this.x = (float)wPoczatkowa;
		this.y = (float)wPoczatkowa;
		this.z = (float)wPoczatkowa;
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
		przeliczFunkcjeTryg();
	}
}
