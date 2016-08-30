/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sterowanie;

import gui.Okno;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import javax.swing.JTextArea;
import wirtualnakamera.Scena;
import wirtualnakamera.TestowaScena;
import wirtualnakamera.WirtualnaKamera;
import zaslanianie.TablicaKrawedzi;

/**
 *
 * @author rafal
 */
public class Main {

    static final int SZEROKOSC_OBRAZU = 650;
    static final int WYSOKOSC_OBRAZU = 650;
    static final Color kolorTla = Color.WHITE;
    static final Color kolorLinii = Color.BLACK;
    static final double POCZATKOWA_ODLEGLOSC_RZUTOWANIA = 1;

    public static void main(String Args[]) {
        gui.Okno okno = new Okno("Wirtualna kamera");
        gui.Klawisze2 klawisze = new gui.Klawisze2();
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(klawisze);
        
        
        
        /*
        JTextArea label = new JTextArea(instrukcja);   
        label.setEditable(false);
        
        okno.getContentPane().add(label, BorderLayout.SOUTH);
        */
        String instrukcja = "Sterowanie: Translacje: oś OX - a d , oś OY - w s , oś OZ - q e ; Obroty: oś OX - l j , oś OY - i k, oś OZ - o u ; Przyblizanie: = -";

        

    //    TestowaScena testowaScena = new TestowaScena();
        Scena scena = TestowaScena.stworzScene();
//        System.out.println(scena.toString());

        WirtualnaKamera wk = new WirtualnaKamera(scena, POCZATKOWA_ODLEGLOSC_RZUTOWANIA, WYSOKOSC_OBRAZU, SZEROKOSC_OBRAZU);
        wk.przetworzScene();
        
        //TablicaKrawedzi tabkrw = new TablicaKrawedzi(wk.getKrawedzieDoNarysowania(), WYSOKOSC_OBRAZU);
        //System.out.println(tabkrw.lista);


        gui.PanelNaRysunek rys = new gui.PanelNaRysunek(kolorTla, kolorLinii, SZEROKOSC_OBRAZU, WYSOKOSC_OBRAZU, wk.getKrawedzieDoNarysowania());
        rys.setSize(SZEROKOSC_OBRAZU, WYSOKOSC_OBRAZU);
        
        JTextArea text= new JTextArea(instrukcja);
        text.setEditable(false);
        okno.getContentPane().add(text,BorderLayout.SOUTH);
        okno.setFocusable(true);
        
        
        
        okno.getContentPane().add(rys, BorderLayout.CENTER);
        rys.init();

        Kontroler kontroler = new Kontroler(rys, wk);
        klawisze.setKontroler(kontroler);
        
        

        okno.pack();
        okno.setSize(new Dimension(SZEROKOSC_OBRAZU + 120, WYSOKOSC_OBRAZU + 50));
        okno.setVisible(true);


        //Kontroler.przetworzINarysujRysunek(rys, wk);
        kontroler.narysujRysunek();


    }
}
