package interfejs_uzytkownika;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
public class Kamera extends JFrame {
	
    private Matryca matryca = new Matryca();
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
        tools.add(new Przycisk3D(matryca.dajT()));        
        tools.add(new Przycisk3D(matryca.dajR()));
        tools.add(new Przycisk3D(matryca.dajS()));
        tools.add(new Przycisk3D(matryca.dajH()));
        tools.add(new Przycisk("Ogniskowa", matryca.dajD()));
                       
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
	        JSpinner js = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1d));
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
					parametr.x = ((Double)((JSpinner) e.getSource()).getValue()).floatValue();
					parametr.przeliczFunkcjeTryg();
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
					parametr.y = ((Double)((JSpinner) e.getSource()).getValue()).floatValue();
					parametr.przeliczFunkcjeTryg();
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
					parametr.z = ((Double)((JSpinner) e.getSource()).getValue()).floatValue();
					parametr.przeliczFunkcjeTryg();
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