package gra;

public final class Stale {
	/*
	warto zwrocic uwage, ze dajRuchy to dwie metody zwracajace zupelnie inne dane, 
	widac to w klasie GeneratorRuchow tam gdzie buduje sie drzewo i sa dwie petle
	jedna metoda zwraca polozenie pol ktore maja jakikolwiek ruch
	druga zwraca pola na ktore ten ruch mozna wykonac z zadanego pola
	obie metody realizuja tez "przymusowe bicia", tzn. jezeli jest bicie to 
	zawsze wybierane jest bicie a "zwykle" ruchy ignorowane
	
	procedura GenerowaniaDrzewa jest rekurencyjna
	*/
	
	public final static Boolean GRACZ = true;
	public final static Boolean KOMPUTER = false;
	public final static Boolean PUSTE_POLE = null;
	public static final int ROZMIAR_PLANSZY = 8;	
	public static final int GLEBOKOSC_DRZEWA = 2;
	

	public static final String WYJSCIE = "EXIT";
	public static final String POKAZ = "POKAZ";
}