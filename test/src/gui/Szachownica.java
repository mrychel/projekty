package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import gra.Plansza;
import gra.Ruch;
import static gra.Stale.*;


public class Szachownica {

	private final JPanel gui = new JPanel(new BorderLayout(3, 3));  
	private JFrame f;
	private NoneSelectedButtonGroup btn;	  
    private Plansza plansza = new Plansza();
    private JPanel chessBoard = new JPanel(new GridLayout(0, 9));
    private JButton btnNowaGra = new JButton("Nowa gra");
    private final JLabel message = new JLabel();
    private final String strPlanszaGotowa = "Plansza gotowa do gry!";
    private static final String COLS = "ABCDEFGH";
    protected PoleSzachowe[][] chessBoardSquares = new PoleSzachowe[ROZMIAR_PLANSZY][ROZMIAR_PLANSZY];
    protected ImageIcon ikona_czarna, ikona_biala, ikona_szara, ikona_pusta;   
        
    private Szachownica() {    	
    	initializeGui();      
    }

    public static void main(String[]args) throws IOException
	{
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Szachownica();  
            }
        });
	}
    
    private final void initializeGui() {
        
        pobierzIkony();
    	gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
       
        btnNowaGra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				plansza.rozstawPionki(); 				
				przerysuj();	
				message.setText(strPlanszaGotowa);				
			}
		});
		       
        tools.add(btnNowaGra);      
        tools.addSeparator();
        tools.add(message);
        gui.add(new JLabel(" "), BorderLayout.LINE_START);
        
        // szachownica        
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);        
        przerysuj();
        
        f = new JFrame("Warcaby");
        f.add(gui);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();        
        f.setMinimumSize(f.getSize());
        f.setVisible(true);  
        chessBoard.validate();
    }       
    
    private void przerysuj() {
    	chessBoard.removeAll();
    	btn = new NoneSelectedButtonGroup();
    	// przygotowanie pol szachownicy
    	for (int x = 0; x < ROZMIAR_PLANSZY; x++) {
            for (int y = 0; y < ROZMIAR_PLANSZY; y++) {                
                chessBoardSquares[x][y] = new PoleSzachowe(x, y);
                btn.add(chessBoardSquares[x][y]);
            }
        }
        // dodanie liter oznaczajacych pola szachownicy        
    	chessBoard.add(new JLabel(""));
        for (int x = 0; x < 8; x++) {
            chessBoard.add(
                    new JLabel(COLS.substring(x, x + 1),
                    SwingConstants.CENTER));
        }
        // dodanie cyfr oznaczajacych pola szachownicy i pionkow 
    	for (int y = 0; y < 8; y++) {
    		for (int x = 0; x < 8; x++) { 
        		switch (x) {
	                case 0:
	                    chessBoard.add(new JLabel("" + (y + 1),
	                            SwingConstants.CENTER));	                    
	                default:
	                	chessBoard.add(chessBoardSquares[x][y]);
	            }
            }
        }
        chessBoard.validate();       
    }   
    
    // ikony pionkow    
    private void pobierzIkony() {
    			
		ikona_czarna = pobierzIkone("czarny.png");
		ikona_biala = pobierzIkone("bialy.png");		
		ikona_szara = pobierzIkone("zolty.png");
		ikona_pusta = pobierzIkone("");
    }
    
    private ImageIcon pobierzIkone(String plikIkony) {
    	
    	BufferedImage im_nowy = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
    	BufferedImage im_stary;
    	
    	if (!plikIkony.isEmpty()) {			
			try {
				im_stary = ImageIO.read((getClass().getResource(plikIkony)));
			} catch (IOException e) {
				im_stary = im_nowy;
				e.printStackTrace();
			}
    	} else 
    		im_stary = im_nowy;
    	
		Graphics2D g = im_nowy.createGraphics();
		g.drawImage(im_stary, 0, 0, 64, 64, null);
		g.dispose();
		
		return new ImageIcon(im_nowy);
    }
    
    private class PoleSzachowe extends JToggleButton {
    	
    	public int polozenieX, polozenieY;
    	
    	public PoleSzachowe(int x, int y) {
    		super();
    		// zapamietuje swoje polozenie oraz podstawowe ustawienia pola
    		polozenieX = x;
    		polozenieY = y;
    		setMargin(new Insets(0,0,0,0));  
    		setEnabled(false);    		
    		setIcon(ikona_pusta);
    		// ustawienia pola planszy
    		if ((polozenieX % 2 == 1 && polozenieY % 2 == 1)                    
                 || (polozenieX % 2 == 0 && polozenieY % 2 == 0)) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.BLACK);                
            }   
    		// ustawienie pionkow
    		Boolean pionek = plansza.dajPlansze()[polozenieX][polozenieY].dajPionek();
    		
        	if (GRACZ.equals(pionek)) {
        		setIcon(ikona_biala);
        		setSelectedIcon(ikona_biala);    
        		setDisabledIcon(ikona_biala);
        		setRolloverIcon(ikona_szara);
    			setEnabled(Ruch.dajRuchy(GRACZ, plansza.dajPlansze()).contains(new Point(polozenieX, polozenieY)));    			
        	} else if (KOMPUTER.equals(pionek)) {		
        		setDisabledIcon(ikona_czarna);        		
        	}
            // dodanie logiki wykonywania ruchow
            addActionListener(new RuchGracza());          
    	}
	}    
    
    private class RuchGracza implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				
			if (((PoleSzachowe)e.getSource()).isSelected()) {
				int px = ((PoleSzachowe) e.getSource()).polozenieX;
				int py = ((PoleSzachowe) e.getSource()).polozenieY;				
				for (Point k : Ruch.dajRuchy(GRACZ, plansza.dajPlansze(), new Point(px,py))) {					
					chessBoardSquares[k.x][k.y].setRolloverEnabled(true); 
					chessBoardSquares[k.x][k.y].setRolloverIcon(ikona_szara);
				    chessBoardSquares[k.x][k.y].setEnabled(true);
				}		
			} 
		}    	
    } 
    
    // podklasa techniczna potrzebna, zeby przy ruchu bylo zaznaczane tylko jedno pole
    private class NoneSelectedButtonGroup extends ButtonGroup {

		@Override
		public void setSelected(ButtonModel model, boolean selected) {

		    if (selected) {              
		        super.setSelected(model, selected);		       
		    } else {		    	
		    	Enumeration<AbstractButton> pola = getElements();
		    	while (pola.hasMoreElements()) {
		    		PoleSzachowe pole = (PoleSzachowe)pola.nextElement();
		    		if (pole.isRolloverEnabled() && pole.getSelectedIcon() != ikona_biala) {
			    		pole.setRolloverEnabled(false);	
			    		pole.setRolloverIcon(ikona_pusta);
			    		pole.setEnabled(false);
		    		}
		    	}
		    	clearSelection();
		    }
		}
	}
}