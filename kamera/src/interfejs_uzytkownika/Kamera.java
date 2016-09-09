package interfejs_uzytkownika;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorytmy.Algorytmy;
import narzedzia.Parametr3D;


//http://mst.mimuw.edu.pl/lecture.php?lecture=gk1&part=Ch3
//http://eduinf.waw.pl/inf/utils/002_roz/2008_07.php
//http://www.asawicki.info/productions/artykuly/Zaawansowana_kamera_3D.php5
// przerobica Matryce na Algorytmy i tam wydzielic algorytmy.
// potem przerobiæ ¿eby zwracal liste parametrow 3d i 2d oraz liste odcinkow/scian
// w instrukcji opisac te listy oraz dziedziczenie narzedzi.
//https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
//http://homepages.inf.ed.ac.uk/rbf/CVonline/LOCAL_COPIES/AV0405/REDSTONE/AxisAngleRotation.html

//https://en.wikipedia.org/wiki/Rotation_formalisms_in_three_dimensions

// OPIS ZROBIÆ W PRACY
// ZDEBUGOWAÆ NA WARTOSCI MNIEJSZE OD ZERA
// b³¹d przy obracaniu, b³¹d przy wchodzeniu do wnêtrza figury.
//i to takie dziwne co siê pojawia byle gdzie (zbieg linii na œrodek ekranu)

public class Kamera extends JFrame {
	
    private Algorytmy matryca = new Algorytmy();
    private Obiektyw obiektyw = new Obiektyw();
	
	private Kamera() {
    	initializeGui();      
    }
        
    public static void main(String[]args) throws IOException
	{
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Kamera();  
            }
        });
	}
    
    private final void initializeGui() {
            	    	
    	JToolBar tools = new JToolBar();
    	tools.setLayout(new GridLayout(0, 1));
        tools.setFloatable(false);
        for (Parametr3D par : matryca.dajParametry3D())
        	tools.add(new Przycisk3D(par));        
        /*
        	tools.add(new Przycisk3D(matryca.dajR()));
        tools.add(new Przycisk3D(matryca.dajS()));
        tools.add(new Przycisk3D(matryca.dajH()));*/
        for (Integer par : matryca.dajParametry())
        	tools.add(new Przycisk("d:", par));
                       
        JFrame f = new JFrame("Kamera");
        f.add(tools, BorderLayout.EAST);
        f.add(obiektyw);        
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);        
    }  
        
    private class Obiektyw extends JPanel {

    	public Obiektyw() {
    		super();
    		setMinimumSize(new Dimension(matryca.OBSZAR_RYSOWANIA_X, matryca.OBSZAR_RYSOWANIA_Y));
    		setPreferredSize(new Dimension(matryca.OBSZAR_RYSOWANIA_X, matryca.OBSZAR_RYSOWANIA_Y));    		
    	}
    	
	    private void przerysuj(Graphics g) {

	        Graphics2D g2d = (Graphics2D) g;
	                   
	        g2d.setColor(Color.RED);	        
	        ArrayList<Point> kk = matryca.dajLinie2D();
	        for (int i = 0; i<kk.size(); i=i+2) {
	        
	        	g2d.drawLine(kk.get(i).x, kk.get(i).y, kk.get(i+1).x, kk.get(i+1).y);
	       
	        }
	    }

	    public void paintComponent(Graphics g) {

	        super.paintComponent(g);
	        setBackground(Color.LIGHT_GRAY);	        
	        obiektyw.przerysuj(g);
	    }
	}
    
    private class Przycisk extends JPanel {

    	private Integer parametr;
    	   
	    public Przycisk(String nazwa, Integer parametr) {
	    	super();
	    	this.parametr = parametr;
	    	init(nazwa);
	    }
	    
	    private void init(String nazwa) {
	    	setLayout(new FlowLayout(FlowLayout.TRAILING));
	    	
	        JLabel label = new JLabel(nazwa, JLabel.TRAILING);
	        JSpinner js = new JSpinner(new SpinnerNumberModel(-500, -1000, 1000, 1d));
	        js.setEditor(new JSpinner.NumberEditor(js, "000"));	        	        
	        js.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {					
					matryca.ustawD(((Double)((JSpinner) e.getSource()).getValue()).intValue());
					obiektyw.repaint();
				}
			});
	        
	        add(label);
	        add(js);
	        setMaximumSize(getPreferredSize());
	    }	    
	}
    
    private class Przycisk3D extends JPanel {

	    private Parametr3D parametr;
    	
	    public Przycisk3D(Parametr3D parametr) {
	    	super();
	    	this.parametr = parametr;
	    	init(parametr.dajNazwe());
	    }
	    
	    private void init(String nazwa) {
	    	setLayout(new FlowLayout());
	    	setBorder(BorderFactory.createTitledBorder(nazwa));	    	
	    	
	        JSpinner jsX = new JSpinner(new SpinnerNumberModel(
	        									parametr.wPoczatkowa,
	        									parametr.wMinimalna,
	        									parametr.wMaksymalna,
	        									parametr.wSkoku));
	        jsX.setEditor(new JSpinner.NumberEditor(jsX, "000.0"));
	        jsX.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					
					double x = (Double)((JSpinner) e.getSource()).getValue();
										
					if (parametr.czyPrzeliczacFunkcjeTryg() && x == 360)
						((JSpinner) e.getSource()).setValue(0.0);						
					
					if (parametr.czyPrzeliczacFunkcjeTryg() && x == -1)
						((JSpinner) e.getSource()).setValue(359.0);						
					
					parametr.ustawX((Double)((JSpinner) e.getSource()).getValue());
					
					obiektyw.repaint();
				}
			});
	        
	        JSpinner jsY = new JSpinner(new SpinnerNumberModel(
								        		parametr.wPoczatkowa,
												parametr.wMinimalna,
												parametr.wMaksymalna,
												parametr.wSkoku));
	        jsY.setEditor(new JSpinner.NumberEditor(jsY, "000.0"));
	        jsY.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					
					double y = (Double)((JSpinner) e.getSource()).getValue();
										
					if (parametr.czyPrzeliczacFunkcjeTryg() && y == 360)
						((JSpinner) e.getSource()).setValue(0.0);
						
					if (parametr.czyPrzeliczacFunkcjeTryg() && y == -1)
						((JSpinner) e.getSource()).setValue(359.0);
					
					parametr.ustawY((Double)((JSpinner) e.getSource()).getValue());
						
					obiektyw.repaint();
				}
			});
	        
	        JSpinner jsZ = new JSpinner(new SpinnerNumberModel(
								        		parametr.wPoczatkowa,
												parametr.wMinimalna,
												parametr.wMaksymalna,
												parametr.wSkoku));
	        jsZ.setEditor(new JSpinner.NumberEditor(jsZ, "000.0"));
	        jsZ.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					
					double z = (Double)((JSpinner) e.getSource()).getValue();	
					
					if (parametr.czyPrzeliczacFunkcjeTryg() && z == 360)
						((JSpinner) e.getSource()).setValue(0.0);
					
					if (parametr.czyPrzeliczacFunkcjeTryg() && z == -1)
						((JSpinner) e.getSource()).setValue(359.0);
					
					parametr.ustawZ(((Double)((JSpinner) e.getSource()).getValue()).floatValue());
					
					obiektyw.repaint();
				}
			});
	        
	        add(new JLabel("x:"));
	        add(jsX);
	        add(new JLabel("y:"));
	        add(jsY);
	        add(new JLabel("z:"));
	        add(jsZ);	        
	    }	    
	}
}