/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import sterowanie.Kontroler;

/**
 *
 * @author rafal
 */
public class Klawisze implements KeyListener {

    Kontroler kontroler;

    public void setKontroler(Kontroler kontroler) {
        this.kontroler = kontroler;
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.toString());     //wypisyywanie nacisnietego klawisza

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
            kontroler.obrotPlusOX();
        }     
        if (e.getKeyChar() == 'j') {
            kontroler.obrotMinusOX();
        }
        if (e.getKeyChar() == 'i') {
            kontroler.obrotPlusOY();
        }     
        if (e.getKeyChar() == 'k') {
            kontroler.obrotMinusOY();
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
