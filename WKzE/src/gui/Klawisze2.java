/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import sterowanie.Kontroler;

/**
 *
 * @author rafal
 */
public class Klawisze2 implements KeyEventDispatcher {

    Kontroler kontroler;

    public void setKontroler(Kontroler kontroler) {
        this.kontroler = kontroler;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet.");
        //System.out.println(e.toString());     //wypisyywanie nacisnietego klawisza

        if (e.getKeyChar() == '=') {
            kontroler.przybliz();

        }
        if (e.getKeyChar() == '-') {
            kontroler.oddal();
        }
        if (e.getKeyChar() == 'd') {
            kontroler.translacjaMinusOX();
        }
        if (e.getKeyChar() == 'a') {
            kontroler.translacjaPlusOX();
        }
        if (e.getKeyChar() == 'q') {
            kontroler.translacjaPlusOY();
        }
        if (e.getKeyChar() == 'e') {
            kontroler.translacjaMinusOY();
        }
        if (e.getKeyChar() == 'w') {
            kontroler.translacjaMinusOZ();
        }
        if (e.getKeyChar() == 's') {
            kontroler.translacjaPlusOZ();
        }
        if (e.getKeyChar() == 'l') {
            kontroler.obrotMinusOY();
        }     
        if (e.getKeyChar() == 'j') {
            kontroler.obrotPlusOY();
        }
        if (e.getKeyChar() == 'i') {
            kontroler.obrotPlusOX();
        }     
        if (e.getKeyChar() == 'k') {
            kontroler.obrotMinusOX();
        }
        if (e.getKeyChar() == 'o') {
            kontroler.obrotPlusOZ();
        }
        if (e.getKeyChar() == 'u') {
            kontroler.obrotMinusOZ();
        }
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
            kontroler.resetujTransformacje();
        }
        if (e.getKeyChar() == java.awt.event.KeyEvent.VK_SPACE) {
            kontroler.wlaczWylaczRysowanieKrawedzi();
        }
        return false;
    }
}
