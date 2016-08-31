package interfejs_uzytkownika;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

public class Kamera {
	
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
        
    	JToolBar tools = new JToolBar("dd");
    	tools.setLayout(new GridLayout(0, 1));
        tools.setFloatable(false);
        tools.add(new Przycisk3D("Przesuni�cie:"));
        //tools.addSeparator();
        tools.add(new Przycisk3D("Obr�t:"));
        tools.add(new Przycisk3D("Powi�kszenie:"));
        tools.add(new Przycisk3D("Pochylenie:"));
        tools.add(new Przycisk("Ogniskowa"));
                
        JFrame f = new JFrame("Kamera");
        f.add(tools, BorderLayout.EAST);
        f.add(new Obiektyw());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();  
        f.setVisible(true);
        f.setResizable(false);
    }       
    
    public class Obiektyw extends JPanel {

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
	        g2d.drawLine(0, 0, 100, 100);
            
            //System.out.println(pr.na3D(a[0]).x +" "+ pr.na3D(a[0]).y +" "+ pr.na3D(a[1]).x +" "+ pr.na3D(a[1]).y);
            //}
	        
	    }
	    
	    public void paintComponent(Graphics g) {

	        super.paintComponent(g);
	        setBackground(Color.LIGHT_GRAY);	        
	        przerysuj(g);
	    }
	}
    
    public class Przycisk extends JPanel {

	    public Przycisk() {
	    	super();
	    	init("");
	    }
	    
	    public Przycisk(String nazwa) {
	    	super();
	    	init(nazwa);
	    }
	    
	    private void init(String nazwa) {
	    	setLayout(new FlowLayout(FlowLayout.TRAILING));
	    	
	        JLabel label = new JLabel(nazwa, JLabel.TRAILING);
	        JSpinner js = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        js.setEditor(new JSpinner.NumberEditor(js, "000"));
	        add(label);
	        add(js);
	        setMaximumSize(getPreferredSize());
	    }
	    
	    public int getValue() {
	    	return 10;
	    }
	}
    
    public class Przycisk3D extends JPanel {

	    public Przycisk3D() {
	    	super();
	    	init("");
	    }
	    
	    public Przycisk3D(String nazwa) {
	    	super();
	    	init(nazwa);
	    }
	    
	    private void init(String nazwa) {
	    	setLayout(new FlowLayout());
	    	setBorder(BorderFactory.createTitledBorder(nazwa));
	    	   
	        JSpinner jsX = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        jsX.setEditor(new JSpinner.NumberEditor(jsX, "000"));
	        
	        JSpinner jsY = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        jsY.setEditor(new JSpinner.NumberEditor(jsY, "000"));
	        
	        JSpinner jsZ = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1d));
	        jsZ.setEditor(new JSpinner.NumberEditor(jsZ, "000"));
	        
	        add(new JLabel("x:"));
	        add(jsX);
	        add(new JLabel("y:"));
	        add(jsY);
	        add(new JLabel("z:"));
	        add(jsZ);
	        
	        //setSize(getPreferredSize());
	        //validate();
	       // setMaximumSize(getPreferredSize());
	    }
	    
	    public int getValue() {
	    	return 10;
	    }
	}
}