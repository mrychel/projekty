package narzedzia;

public class Parametr3D {
	
	public double x,y,z;				  
	public double wPoczatkowa = 0,
			      wMinimalna  = -999,
			      wMaksymalna = 999,
			      wSkoku = 10d;
	public double[][] mo = new double[3][3];
	
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
			double sinx = Math.sin(x);
			double siny = Math.sin(y);
			double sinz = Math.sin(z);
			
			double cosx = Math.cos(x);
			double cosy = Math.cos(y);
			double cosz = Math.cos(z);
						
			mo[0][0] = cosy*cosz; 
			mo[1][0] = (-cosx)*sinz-sinx*siny*cosz;
			mo[2][0] = sinx*sinz-cosx*siny*cosz;
			
			mo[0][1] = cosy*sinz; 
			mo[1][1] = cosx*cosz-sinx*siny*sinz;
			mo[2][1] = (-sinx)*cosz-cosx*siny*sinz;
			
			mo[0][2] = -siny; 
			mo[1][2] = sinx*cosy;
			mo[2][2] = cosx*cosy;			
		}
	}
	
	public void ustawPrzeliczanieFunkcjiTryg(boolean czyPrzeliczacFunkcjeTryg) {
		this.czyPrzeliczacFunkcjeTryg = czyPrzeliczacFunkcjeTryg;
		przeliczFunkcjeTryg();
	}
}
