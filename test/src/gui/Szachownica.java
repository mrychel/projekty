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
import static gra.Ruch.*;
import static gra.Stale.*;


public class Szachownica {

	private final JPanel gui = new JPanel(new BorderLayout(3, 3));  
	private JFrame f;
	private NoneSelectedButtonGroup btn;	  
    private Plansza plansza = new Plansza();
    private JPanel chessBoard = new JPanel(new GridLayout(0, 9));
    private JButton btnNowaGra = new JButton("Nowa gra");
    public final JLabel message = new JLabel();    
    protected static final Point PUSTY_PUNKT = new Point(-1,-1); 
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
    	chessBoardSquares = new PoleSzachowe[ROZMIAR_PLANSZY][ROZMIAR_PLANSZY];
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
        // dodanie cyfr oznaczajacych pola szachownicy i pionkow na polach
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
    	message.setText(plansza.dajKomunikat());	
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
    	    	
    	public Point polozenie = new Point();
    	protected Point poprzednie_polozenie = PUSTY_PUNKT;
    	    	
    	public PoleSzachowe(int x, int y) {
    		super();
    		// zapamietuje swoje polozenie oraz podstawowe ustawienia pola
    		polozenie.x = x;
    		polozenie.y = y;
    		setMargin(new Insets(0,0,0,0));  
    		setEnabled(false);
    		setIcon(ikona_pusta);    
    		setRolloverIcon(ikona_szara);
    		// ustawienia pola planszy
    		if ((polozenie.x % 2 == 1 && polozenie.y % 2 == 1)                    
                 || (polozenie.x % 2 == 0 && polozenie.y % 2 == 0)) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.BLACK);                 
            }   
    		// ustawienie pionkow
    		Boolean pionek = plansza.dajPlansze()[polozenie.x][polozenie.y].dajPionek();
    		
        	if (GRACZ.equals(pionek)) {
        		setIcon(ikona_biala);
        		setSelectedIcon(ikona_biala);    
        		setDisabledIcon(ikona_biala);   
        		setEnabled(dajRuchy(plansza).contains(new Point(polozenie.x, polozenie.y)));        		
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
			
			PoleSzachowe zrodlo_zdarzenia = (PoleSzachowe) e.getSource();		
				
			if (((PoleSzachowe)e.getSource()).isSelected()) {				
				if (zrodlo_zdarzenia.poprzednie_polozenie != PUSTY_PUNKT) {
					wykonajRuch(plansza, zrodlo_zdarzenia.poprzednie_polozenie, zrodlo_zdarzenia.polozenie);
					zrodlo_zdarzenia.poprzednie_polozenie = PUSTY_PUNKT;
					ruchKomputera(plansza);
					przerysuj();
				} else {
					plansza.czyjRuch(GRACZ);
					for (Point k : dajRuchy(plansza, zrodlo_zdarzenia.polozenie)) {					
						PoleSzachowe pole = chessBoardSquares[k.x][k.y];
						pole.setRolloverEnabled(true); 
						pole.setEnabled(true);						
						pole.poprzednie_polozenie = zrodlo_zdarzenia.polozenie;
					}	
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
		    		}		    		
		    	}
		    	clearSelection();
		    }
		}
	}
}