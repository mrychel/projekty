/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import javax.swing.JPanel;
import wirtualnakamera.Krawedz2D;

/**
 *
 * @author rafal
 */
public class PanelNaRysunek extends JPanel {

    Color kolorTla;
    Color kolorLinii;
    int szerokosc;
    int wysokosc;
    public ArrayList<Krawedz2D> krawedzieDoNarysowania;
    BufferedImage bufor;
    boolean rysujBufor = true;  

    public PanelNaRysunek(Color kolorTla, Color kolorLinii, int szerokosc, int wysokosc, ArrayList<Krawedz2D> krawedzieDoNarysowania) throws HeadlessException {
        this.kolorTla = kolorTla;
        this.kolorLinii = kolorLinii;
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.krawedzieDoNarysowania = krawedzieDoNarysowania;
        init();
    }

    public void init() {
        setBackground(kolorTla);
        setForeground(kolorTla);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //      this.repaint();

        //      Graphics2D g2 = (Graphics2D) g;
        //      g2.setColor(kolorLinii);
        g.setColor(kolorLinii);
        
        if (rysujBufor) {
            rysujBufor(g);
            rysujKrawedzie(g);
        } else {
            rysujKrawedzie(g);
        }
        
        
        //       sterowanie.Rysuj.rysuj(krawedzieDoNarysowania, kolorLinii, g);
    }

    public ArrayList<Krawedz2D> getKrawedzieDoNarysowania() {
        return krawedzieDoNarysowania;
    }

    public void setKrawedzieDoNarysowania(ArrayList<Krawedz2D> krawedzieDoNarysowania) {
        this.krawedzieDoNarysowania = krawedzieDoNarysowania;
    }

    public void rysujKrawedzie(Graphics g) {
 //       Rysuj.rysuj(krawedzieDoNarysowania, kolorTla, g);
        for (Krawedz2D kr : krawedzieDoNarysowania) {
            
            g.drawLine(kr.punkt1.x, kr.punkt1.y, kr.punkt2.x, kr.punkt2.y);
        }
    }
    
    public void NarysujKrawedzieNaRysunku(ArrayList<Krawedz2D> krw) {
        this.krawedzieDoNarysowania = krw;
        this.repaint();
    }
    
    public void NarysujBufferedImage(BufferedImage bufimg) {
        this.krawedzieDoNarysowania = new ArrayList<Krawedz2D>();
//        this.rysujBufor = true;
        this.bufor  = bufimg;
        this.repaint();
        
    }
    
    
    
    public void rysujBufor(Graphics g) {
        g.drawImage(bufor, 0, 0, new ImageObserver() {

            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        //g = bufor.createGraphics();
    }
    
    
}
