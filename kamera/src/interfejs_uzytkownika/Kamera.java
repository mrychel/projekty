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
        tools.setFloatable(true);
        tools.add(new JLabel("tst"));      
        tools.addSeparator();
        tools.add(new Przycisk("Przybli¿enie:"));
        
        JFrame f = new JFrame("Kamera");
        f.add(tools, BorderLayout.EAST);
        f.add(new Obiektyw());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();  
        f.setVisible(true);
        //f.setResizable(false);
    }       
    
    public class Obiektyw extends JPanel {

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
	        setBackground(Color.RED);
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
	    	init("nazwa");
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
}