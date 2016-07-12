package aa;

	import java.awt.Dimension;
import java.awt.EventQueue;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import javax.swing.JFrame;
	import javax.swing.JPanel;

	

	public class LinesEx extends JFrame {

	    public LinesEx() {

	        initUI();
	    }

	    private void initUI() {

	    	add(new Surface());

	        setTitle("Lines");
	        setSize(350, 250);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }

	    public static void main(String[] args) {

	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                
	                LinesEx ex = new LinesEx();
	                ex.setVisible(true);
	            }
	        });
	    }
	}


