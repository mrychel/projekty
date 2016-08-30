/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zaslanianie;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author rafal
 */
public class BuforEkranu {

    Color[][] ekran;
    int szerokosc;
    int wysokosc;
    

    public BuforEkranu(int szerokosc, int wysokosc, Color kolorTla) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.ekran = new Color[wysokosc][szerokosc];
        for (int y = 0; y < wysokosc ; y++) {
            for (int x = 0; x < szerokosc ; x++) {
                ekran[y][x] = kolorTla;
            }
        }
        
//        for (Color[] t : this.ekran) {
//            for (Color p : t) {
//                p = kolorTla;
//            }
//        }
    }

    public void pomaluj(int y, int xMin, int xMax, Color kolor) {
        for (int x = xMin ; x <= xMax; x++) {
            ekran[y][x] = kolor;
        }
    }
    
    public BufferedImage przeksztalcNaBufferedImage() {
        BufferedImage bufimg = new BufferedImage(szerokosc, wysokosc, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < wysokosc ; y++) {
            for (int x = 0 ; x < szerokosc ; x++) {
               // System.out.println("x=" + x + " y=" + y + " k=" + ekran[y][x].toString());
                bufimg.setRGB(x, y, ekran[y][x].getRGB());
//                bufimg.setRGB(x, y, Color.BLUE.getRGB());
            }
        }
        return bufimg;
    }
}
