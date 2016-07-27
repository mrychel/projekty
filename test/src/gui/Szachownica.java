package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import gra.Plansza;
import gra.Plansza.Pole;

public class Szachownica implements ActionListener {

	JFrame f;
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    public PoleSzachowe[][] chessBoardSquares = new PoleSzachowe[8][8];
    private Plansza plansza = new Plansza();
    private JPanel chessBoard;
    JButton bc, bb;
    private final JLabel message = new JLabel("Plansza gotowa do gry!");
    private static final String COLS = "ABCDEFGH";
    private boolean bbb = true;
    ActionListener ww;
    protected ImageIcon ikona_czarna, ikona_biala, ikona_szara;    
    
    public Szachownica() {    	
        initializeGui();        
    }

    private final void initializeGui() {
        
        pobierzIkony();
    	gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        bb = new JButton("Nowa gra");
        
        		ww = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (bbb)
				chessBoardSquares[1][1].setEnabled(true);
				System.out.println("xxxx");
				bb.setText("hhh");			
				
			}
		};
		bb.addActionListener(ww);
       
        tools.add(bb); // TODO - add functionality!        
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel(" "), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        przerysuj();
        
    }       
    
    private void przerysuj() {
    	chessBoard.removeAll();
    	for (int x = 0; x < chessBoardSquares.length; x++) {
            for (int y = 0; y < chessBoardSquares[x].length; y++) {                
                chessBoardSquares[x][y] = new PoleSzachowe(x, y);
            }
        }
        
        chessBoard.add(new JLabel(""));
        for (int x = 0; x < 8; x++) {
            chessBoard.add(
                    new JLabel(COLS.substring(x, x + 1),
                    SwingConstants.CENTER));
        }
        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                switch (y) {
                    case 0:
                        chessBoard.add(new JLabel("" + (x + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[x][y]);
                }
            }
        }
        chessBoard.validate();
        chessBoard.repaint();
    }
    
    private final JComponent getGui() {
        return gui;
    }
    
    public void s() {
    	
    	Szachownica cb =
                new Szachownica();

        f = new JFrame("Warcaby");
        f.add(cb.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true); 
    }
    
    
    public Runnable pobierzSzachownice() {
    	
    	Runnable r = new Runnable() {

            @Override
            public void run() {
                Szachownica cb =
                        new Szachownica();

                f = new JFrame("Warcaby");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);                
            }
        };
        return r;        
    }
    
    public void ustawPionki(Plansza p) {        
    	this.plansza = p;
    	przerysuj();
    	
    }
    
    public void pobierzRuch() {
    	
    }
        
    private void pobierzIkony() {
    			
		ikona_czarna = pobierzIkone("czarny.png");
		ikona_biala = pobierzIkone("bialy.png");		
		ikona_szara = pobierzIkone("zolty.png");
    }
    
    private ImageIcon pobierzIkone(String plikIkony) {
    	
    	BufferedImage im_nowy = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB); 		
		BufferedImage im_stary;
		try {
			im_stary = ImageIO.read((getClass().getResource(plikIkony)));
		} catch (IOException e) {
			im_stary = im_nowy;
			e.printStackTrace();
		}
		Graphics2D g = im_nowy.createGraphics();
		g.drawImage(im_stary, 0, 0, 64, 64, null);
		g.dispose();
		
		return new ImageIcon(im_nowy);
    }
    
    public void mmm() {
    	chessBoardSquares[3][3].getModel().setEnabled(true);
    	gui.invalidate();
    }
    
    class PoleSzachowe extends JToggleButton {
    	
    	public int polozenieX, polozenieY;
    	
    	public PoleSzachowe(int x, int y) {
    		super();
    		polozenieX = x;
    		polozenieY = y;
    		this.setMargin(new Insets(0,0,0,0));            
            
    		ImageIcon icon = new ImageIcon(
                    new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    		Boolean mm = plansza.dajPlansze()[polozenieX][polozenieY].dajPionek();
            if (mm != null)
            	if (Boolean.TRUE.equals(mm))
            		this.setIcon(ikona_biala);
            	else
            		this.setIcon(ikona_czarna);
            else	
            	this.setIcon(icon);    		
    		
            if ((polozenieX % 2 == 1 && polozenieY % 2 == 1)                    
                 || (polozenieX % 2 == 0 && polozenieY % 2 == 0)) {
                this.setBackground(Color.WHITE);
            } else {
                this.setBackground(Color.BLACK);
            }   		
    		
            //this.setEnabled(false);
            //this.setDisabledIcon(ikona_szara);
//    		this.setSelectedIcon(ikona_szara);	
//    		this.setDisabledIcon(ikona_biala);
//    		if (ikona_czarna != null && polozenieX==1 && polozenieY==1) {
//            	this.setIcon(ikona_czarna);  
//            	this.setEnabled(true);
//            }
    		this.addItemListener(new ListenerPola());
    		this.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (arg0.getActionCommand()=="test") {
						System.out.println("000000000000000000");
						((JToggleButton) arg0.getSource()).getModel().setEnabled(true);
						mmm();	
						
					}
					
				}
			});
    	}
    	/*
    	public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.isEnabled())
            this.setBackground(Color.BLUE);
    	}*/
    	
    	public void setBialy() {
    		ActionEvent act = new ActionEvent(this, 0, "test");
    	
    		for(ActionListener a: bc.getActionListeners()) {
    		    a.actionPerformed(act);
    		}
    		//this.getModel().setEnabled(true);
    		//this.setIcon(ikona_biala);
    		//this.invalidate();
    		//this.validate();
    		//this.repaint();
    		System.out.println("bialy");
    	}
    }
    
    class ListenerPola implements ItemListener {
    	
    	public ListenerPola() {
    		super();
    	}
    	
    	public void itemStateChanged(ItemEvent ev) {
	        
    		if(ev.getStateChange()==ItemEvent.SELECTED){
	        	System.out.println("button is selected");
	        } else if(ev.getStateChange()==ItemEvent.DESELECTED){
	        	System.out.println("button is not selected");
	        }
	        
	        ((PoleSzachowe) ev.getItem()).setEnabled(true);
	        
	        
		}
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}
}