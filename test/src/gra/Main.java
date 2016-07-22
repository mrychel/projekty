package gra;
import java.io.*;

public class Main {	
	
	public static void main(String[]args) throws IOException
	{
		startGry();		
	}
	
	public static void startGry() throws IOException
	{
		Plansza aPlansza = new Plansza();		
		
		aPlansza.pokazPlansze();
		while(true)
		{
			aPlansza = Ruch.ruchGracza(aPlansza);
			/*
			if(aPlansza.dajBicie()) 
			{aPlansza.pokazPlansze(); }*///System.out.println("wygra³ gracz?"+aPlansza.dajBicie()); koniecGry(Stale.GRACZ);}
			aPlansza.pokazPlansze();
			aPlansza = Ruch.ruchKomputera(aPlansza);
			if (aPlansza == null) koniecGry(Stale.GRACZ);
			/*if(aPlansza.dajBicie()) 
			{aPlansza.pokazPlansze(); }*///System.out.println("wygra³ komputer?"+aPlansza.dajBicie()); ;koniecGry(Stale.KOMPUTER);}
			aPlansza.pokazPlansze();		
		}		
	}
	
	public static void koniecGry(String wygrany)
	{
		if (Stale.GRACZ.equals(wygrany))
		{
			System.out.println("Wygra³eœ :D");
			System.exit(0);
		}
		else
		{
			System.out.println("Przegra³eœ :(");
			System.exit(0);
		} 
	}
}
