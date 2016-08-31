package kamera;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Surface extends JPanel {

	    private void doDrawing(Graphics g) {

	        Graphics2D g2d = (Graphics2D) g;
            GeneratorPrzestrzeni p = new GeneratorPrzestrzeni();
	        //System.out.println(p.P[0][1].x);
	        //System.out.println(p.P[0][0].y);
	        Przeksztalcenia pr = new Przeksztalcenia();
            for (Punkt3D[] a : p.P){
	        g2d.drawLine(pr.na3D(a[0]).x, pr.na3D(a[0]).y, pr.na3D(a[1]).x, pr.na3D(a[1]).y);
            
            System.out.println(pr.na3D(a[0]).x +" "+ pr.na3D(a[0]).y +" "+ pr.na3D(a[1]).x +" "+ pr.na3D(a[1]).y);
            }
	        
	    }

	    @Override
	    public void paintComponent(Graphics g) {

	        super.paintComponent(g);
	        doDrawing(g);
	    }
	}