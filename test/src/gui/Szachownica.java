package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    protected ImageIcon ikona_czarna, ikona_biala, ikona_szara;   
        
    public Szachownica() {    	
        initializeGui();        
    }

    public static void main(String[]args) throws IOException
	{
		Szachownica szachownica = new Szachownica();
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
				plansza.wypiszPlansze();
				Point m = new Point();
				m = Ruch.dajRuchPrawo(GRACZ, plansza.dajPlansze(), new Point(0,5));
				System.out.print(plansza.dajPlansze()[0][5].dajPionek());
				if (m!= null) System.out.println(m.x+" "+m.y);
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
        // dodanie cyfr oznaczajacych pola szachownicy i pol
        
        for (int x = 0; x < 9; x++) {
        	for (int y = 0; y < 8; y++) {
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
    
    private class PoleSzachowe extends JToggleButton {
    	
    	public int polozenieX, polozenieY;
    	
    	public PoleSzachowe(int x, int y) {
    		super();
    		// zapamietuje swoje polozenie oraz podstawowe ustawienia pola
    		polozenieX = x;
    		polozenieY = y;
    		setMargin(new Insets(0,0,0,0));  
    		setEnabled(false);
    		ImageIcon icon = new ImageIcon(
                    new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    		setIcon(icon);
    		// ustawienia pola planszy
    		if ((polozenieX % 2 == 1 && polozenieY % 2 == 1)                    
                 || (polozenieX % 2 == 0 && polozenieY % 2 == 0)) {
                this.setBackground(Color.WHITE);
            } else {
                this.setBackground(Color.BLACK);
            }   
    		// ustawienie pionkow
    		Boolean pionek = plansza.dajPlansze()[polozenieX][polozenieY].dajPionek();    		
           
        	if (GRACZ.equals(pionek)) {
        		setIcon(ikona_biala);
        		setSelectedIcon(ikona_biala);        		
    			setEnabled(true); 
        	} else if (KOMPUTER.equals(pionek)) {		
        		setDisabledIcon(ikona_czarna);
        		setIcon(ikona_czarna);            		
        	}
            // dodanie logiki wykonywania ruchow
            addActionListener(new RuchGracza());  
    	}   
    }    
    
    private class RuchGracza implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int px = ((PoleSzachowe) arg0.getSource()).polozenieX;
			int py = ((PoleSzachowe) arg0.getSource()).polozenieY;
			System.out.println(px+" "+py);
			chessBoardSquares[px-1][py-1].setRolloverEnabled(true);  		
		      chessBoardSquares[px-1][py-1].setRolloverIcon(ikona_szara);
			chessBoardSquares[px-1][py-1].setEnabled(true);			
		}
    	
    } 
    
    // podklasa techniczna potrzebna, zeby przy ruchu bylo zaznaczane tylko jedno pole
    private class NoneSelectedButtonGroup extends ButtonGroup {

		@Override
		public void setSelected(ButtonModel model, boolean selected) {

		    if (selected) {              
		        super.setSelected(model, selected);		     
		    } else {		    	
		        clearSelection();
		    }
		}
	}
}