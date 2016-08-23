package gra;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.Szachownica;

public class Main {	
	
	public static void main(String[]args) throws IOException
	{
		Szachownica szachownica = new Szachownica();
	}
	
	public static void startGry() throws IOException
	{
				    
		//SwingUtilities.invokeLater(szachownica);
		//szachownica.s();
		//Plansza aPlansza = new Plansza();		
//		
		//szachownica.ustawPionki(aPlansza);
		
//		aPlansza.pokazPlansze();
//		while(true)  // to na Runnable??????????????????????
//		{
//			aPlansza = Ruch.ruchGracza(aPlansza);
//			/*
//			if(aPlansza.dajBicie()) 
//			{aPlansza.pokazPlansze(); }*///System.out.println("wygra³ gracz?"+aPlansza.dajBicie()); koniecGry(Stale.GRACZ);}
//			aPlansza.pokazPlansze();
//			aPlansza = Ruch.ruchKomputera(aPlansza);
//			if (aPlansza == null) koniecGry(Stale.GRACZ);
//			/*if(aPlansza.dajBicie()) 
//			{aPlansza.pokazPlansze(); }*///System.out.println("wygra³ komputer?"+aPlansza.dajBicie()); ;koniecGry(Stale.KOMPUTER);}
//			aPlansza.pokazPlansze();		
//		}		
	}
	
	public static void koniecGry(String wygrany)
	{
		/*
		if (Stale.GRACZ.equals(wygrany))
		{
			System.out.println("Wygra³eœ :D");
			System.exit(0);
		}
		else
		{
			System.out.println("Przegra³eœ :(");
			System.exit(0);
		} */
	}
}
