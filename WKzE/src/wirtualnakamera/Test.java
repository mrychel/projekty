/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera;

/**
 *
 * @author rafal
 */
public class Test {
    
    public static void wykonaj_test() {
        TestowaScena testowaScena = new TestowaScena();
        double odl_rzutni = 0.6;
        Rzutowanie rzut = new Rzutowanie(odl_rzutni);
        int wysokosc = 600;
        int szerokosc = 600;
        
//        ArrayList<Punkt> zrzutowane_punkty = new ArrayList<Punkt>();
//        
//        
//        for (Krawedz kr : testowaScena.krawedzie) {
//            Punkt pkt;
//            pkt = rzut.rzutuj_punkt(kr.punkt1);
//            zrzutowane_punkty.add(pkt);
//            pkt = rzut.rzutuj_punkt(kr.punkt2);
//            zrzutowane_punkty.add(pkt);            
//        }
//        
//        for (Punkt p : zrzutowane_punkty) {
//            System.out.println(p.toString());
//        }
        
//        ArrayList<Krawedz> zrzutowaneKrawedzie = rzut.rzutujKrawedzie(testowaScena.krawedzie);
//        
//        for (Krawedz kr : zrzutowaneKrawedzie) {
//            System.out.println(kr.toString());
//        }
        
        Kamera kam = new Kamera(rzut.rzutujKrawedzie(testowaScena.krawedzie));
        
        for (Krawedz3D kr : kam.krawedzieNaKamerze) {
            System.out.println(kr.toString());
        }
        
        Widok widok = new Widok(wysokosc, szerokosc, kam);
        
        for (Krawedz2D kr : widok.krawedzieNaWidoku) {
            System.out.println(kr.toString());
        }
        
        
    }
    
}
