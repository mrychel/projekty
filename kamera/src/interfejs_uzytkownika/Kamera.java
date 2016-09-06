package interfejs_uzytkownika;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import narzedzia.Parametr3D;

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
    		setMinimumSize(new Dimension(450, 300));
    		setPreferredSize(new Dimension(450, 300));    		
    	}
    	
	    private void przerysuj(Graphics g) {

	        Graphics2D g2d = (Graphics2D) g;
	       
            //Przestrzen p = new Przestrzen();
	        //System.out.println(p.P[0][1].x);
	        //System.out.println(p.P[0][0].y);
	        //Przeksztalcenia pr = new Przeksztalcenia();
            //for (Punkt3D[] a : p.P){
	        //g2d.drawLine(pr.na3D(a[0]).x, pr.na3D(a[0]).y, pr.na3D(a[1]).x, pr.na3D(a[1]).y);
	        g2d.setColor(Color.RED);
	        //for (Point punkt : matryca.dajLinie2D()) {
	        ArrayList<Point> kk = matryca.dajLinie2D();
	        for (int i = 0; i<kk.size(); i=i+2) {
	        
	        	g2d.drawLine(kk.get(i).x, kk.get(i).y, kk.get(i+1).x, kk.get(i+1).y);
	        //System.out.println(punkt.x+" "+punkt.y);
	        }
            
            //System.out.println(pr.na3D(a[0]).x +" "+ pr.na3D(a[0]).y +" "+ pr.na3D(a[1]).x +" "+ pr.na3D(a[1]).y);
            //}
	        
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
	        JSpinner js = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
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
	    	
	        JSpinner jsX = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        jsX.setEditor(new JSpinner.NumberEditor(jsX, "000"));
	        jsX.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					parametr.x = ((Double)((JSpinner) e.getSource()).getValue()).floatValue();
					obiektyw.repaint();
				}
			});
	        
	        JSpinner jsY = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        jsY.setEditor(new JSpinner.NumberEditor(jsY, "000"));
	        jsY.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					parametr.y = ((Double)((JSpinner) e.getSource()).getValue()).floatValue();
					obiektyw.repaint();
				}
			});
	        
	        JSpinner jsZ = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        jsZ.setEditor(new JSpinner.NumberEditor(jsZ, "000"));
	        jsZ.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					parametr.z = ((Double)((JSpinner) e.getSource()).getValue()).floatValue();	
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