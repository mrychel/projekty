package interfejs_uzytkownika;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorytmy.Algorytmy;
import narzedzia.Odcinek;
import narzedzia.Parametr3D;
import narzedzia.Plaszczyzna;

public class Kamera extends JFrame {
	
    private Algorytmy algorytmy = new Algorytmy();
    private Obiektyw obiektyw = new Obiektyw();
    
    private JCheckBox malowaniePowierzchni = new JCheckBox("Malowanie powierzchni");
    private JButton ustawDomyslne = new JButton("Reset");
	
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
            	    	
    	JToolBar menu = new JToolBar();
    	menu.setLayout(new GridLayout(0, 1));
        menu.setFloatable(false);
        menu.setPreferredSize(new Dimension(370, algorytmy.OBSZAR_RYSOWANIA_Y));
        
        JPanel pnl = new JPanel(new FlowLayout());
    	pnl.setBorder(BorderFactory.createTitledBorder("Inne"));	
        
    	malowaniePowierzchni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				obiektyw.repaint();
			}
		});
        
        ustawDomyslne.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Component c : menu.getComponents()) 
		        	if (c instanceof Przycisk3D) ((Przycisk3D) c).ustawDomyslne();
				for (Component c : pnl.getComponents())
					if (c instanceof Przycisk) ((Przycisk) c).ustawDomyslne();
			}
		});
        
        pnl.add(ustawDomyslne);
        pnl.add(malowaniePowierzchni);
        
        for (Parametr3D par : algorytmy.dajParametry3D())
        	menu.add(new Przycisk3D(par));    
        for (Integer par : algorytmy.dajParametry())
        	pnl.add(new Przycisk("d:", par));
        
        menu.add(pnl);
        
        JFrame f = new JFrame("Kamera");
        f.add(menu, BorderLayout.EAST);
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
    		setMinimumSize(new Dimension(Algorytmy.OBSZAR_RYSOWANIA_X, Algorytmy.OBSZAR_RYSOWANIA_Y));
    		setPreferredSize(new Dimension(Algorytmy.OBSZAR_RYSOWANIA_X, Algorytmy.OBSZAR_RYSOWANIA_Y));    		
    	}
    	
	    private void przerysuj(Graphics g) {

	        Graphics2D g2d = (Graphics2D) g;
	        
	        if (malowaniePowierzchni.isSelected())
	        	for (Plaszczyzna p : algorytmy.dajPlaszczyzny2D()) {
		        	g2d.setColor(p.kolor_konturu);
		        	g2d.drawPolygon(p.rzut);	        	
		        	g2d.setColor(p.kolor_wypelnienia);	        	
		            g2d.fillPolygon(p.rzut);
		        }	
	        else	
		        for (Odcinek o : algorytmy.dajLinie2D()) {
		        	g2d.setColor(o.kolor_konturu);
		        	g2d.drawLine(o.rzut_a.x, o.rzut_a.y, o.rzut_b.x, o.rzut_b.y);
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
    	private JSpinner js;
    	private final static double wPoczatkowa = -500;
    	   
	    public Przycisk(String nazwa, Integer parametr) {
	    	super();
	    	this.parametr = parametr;
	    	init(nazwa);
	    }
	    
	    private void init(String nazwa) {
	    	setLayout(new FlowLayout(FlowLayout.TRAILING));
	    	
	        JLabel label = new JLabel(nazwa, JLabel.TRAILING);
	        js = new JSpinner(new SpinnerNumberModel(wPoczatkowa, -1000, 1000, 1d));
	        js.setEditor(new JSpinner.NumberEditor(js, "000"));	        	        
	        js.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {					
					algorytmy.ustawD(((Double)((JSpinner) e.getSource()).getValue()).intValue());
					obiektyw.repaint();
				}
			});
	        
	        add(label);
	        add(js); 	        
	    }	
	    
	    public void ustawDomyslne() {
	        js.setValue(wPoczatkowa);
	    }
	}
    
    private class Przycisk3D extends JPanel {

	    private Parametr3D parametr;
	    private JSpinner jsX, jsY, jsZ;
    	
	    public Przycisk3D(Parametr3D parametr) {
	    	super();
	    	this.parametr = parametr;
	    	init(parametr.dajNazwe());
	    }
	    
	    private void init(String nazwa) {
	    	setLayout(new FlowLayout());
	    	setBorder(BorderFactory.createTitledBorder(nazwa));	    	
	    	
	        jsX = new JSpinner(new SpinnerNumberModel(
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
	        
	        jsY = new JSpinner(new SpinnerNumberModel(
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
	        
	        jsZ = new JSpinner(new SpinnerNumberModel(
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
	    
	    public void ustawDomyslne() {
	    	jsX.setValue(parametr.wPoczatkowa);
	    	jsY.setValue(parametr.wPoczatkowa);
	    	jsZ.setValue(parametr.wPoczatkowa);
	    }
	}
}