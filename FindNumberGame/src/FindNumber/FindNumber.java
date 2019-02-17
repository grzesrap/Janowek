package FindNumber;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FindNumber extends JFrame {				
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar = new JMenuBar();					//MENU
	JMenu m1File = new JMenu("Plik");
	JMenu m2Help = new JMenu("Pomoc");
	JMenuItem m1newGame = new JMenuItem("Nowa gra");
	JMenuItem m1End = new JMenuItem("Koniec");
	JMenuItem m2Best = new JMenuItem("Najlepsze wyniki");
	JMenuItem m2About = new JMenuItem("Informacje");		
	JButton checkButton = new JButton();				//BUTTON
	JButton newGameButton = new JButton("Zgaduj");
	JButton exitButton = new JButton();
	JButton clearListButton = new JButton();
	JTextField titleTextField = new JTextField();		//TEXTFIELD
	JTextField inputTextField = new JTextField();
	JTextField messageTextField = new JTextField();
	JTextField outputTextField = new JTextField();
	JTextField userTitleTextField = new JTextField();
	JOptionPane aboutPanel = new JOptionPane();
	Date dat = new Date();
	User[] user = new User[5];
	LinkedList<User> usersLinkList = new LinkedList<User>();
//	LinkedList<User> newUserList = new LinkedList<User>();
    FileIO fileRW = new FileIO();
	JPanel gamePanel = new JPanel();
	JTextField[] boxTextField = new JTextField[2]; 
	JTextField[] userTextField = new JTextField[5]; 
	JLabel[] gridLabel = new JLabel[4]; 
	JPanel playersPanel = new JPanel(); 
	JPanel buttonsPanel = new JPanel(); 
//--------------------------------- POLA  --------------------------- 
	Pattern wzorzecLiczby = Pattern.compile("[0-9]?[0-9]?");
	static double randomNumber;
	double yoursNumber; 
	double maxNumber = 100.0d;
	double minNumber = 0.0d;
	String strNumbers;
	String temp = "Twoje próby: ";
	int czyOk, ktoryRaz, nrWins, iTime;
	double elapsedTime;
	float sTime;
	long startTime, stopTime;
	boolean hit;
//-------------------------------- MAIN ---------------	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws IOException {	
		losujLiczbe();
		//fileSet();
		new FindNumber().show();
	}
//---------------------------- KONSTRUKTOR ---------------------------	
	public FindNumber() {							
		setTitle("Znajdz liczbê");
		setResizable(false);
		addWindowListener( new WindowAdapter() {
			@SuppressWarnings("unchecked")
			public void windowOpened( WindowEvent e ){
				usersLinkList = (LinkedList<User>) fileRW.ReadObjectToFile();		//wczytaj listê z pliku
				Collections.sort(usersLinkList, new TimeAttemptsComparator());		//wypisz posortowan¹ liste 	
			    User us;
			    for (int i=0; i < usersLinkList.size(); i++) {
			    	us = usersLinkList.get(i);
		    		userTextField[i].setText(us.toString());
		    	} 	  
				inputTextField.requestFocus();
			}
		}); 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitForm(e);
			}
		});
		
		getContentPane().setLayout(new GridBagLayout()); 
		
		titleTextField = new JTextField();
		titleTextField.setPreferredSize(new Dimension(280, 50));
		titleTextField.setEditable(false); 
		titleTextField.setBackground(Color.YELLOW);
		titleTextField.setForeground(Color.BLUE); 
		titleTextField.setText("Znajd¿ wylosowan¹ liczbê z przedzia³u 1-99");
		titleTextField.setHorizontalAlignment(SwingConstants.CENTER);
		titleTextField.setFont(new Font("Arial", Font.BOLD, 12)); 
		GridBagConstraints gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.insets = new Insets(10, 10, 10, 10); 
		getContentPane().add(titleTextField, gridConstraints); 
		
		gamePanel.setPreferredSize(new Dimension(180, 300)); //150
		gamePanel.setBackground(Color.WHITE); 
		gamePanel.setLayout(new GridBagLayout());
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		gridConstraints.gridheight = 3;
		gridConstraints.insets = new Insets(10, 10, 10, 10); 
		getContentPane().add(gamePanel, gridConstraints); 	
		inputTextField = new JTextField();
		inputTextField.setPreferredSize(new Dimension(80, 80)); 
		inputTextField.setEditable(true); 
		inputTextField.setBackground(Color.WHITE);
		inputTextField.setHorizontalAlignment(SwingConstants.CENTER);
		inputTextField.setFont(new Font("Arial", Font.BOLD, 48));
		inputTextField.setBorder(null);
		inputTextField.setText("");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 1 ;
		gridConstraints.gridy = 2 ; 
		gamePanel.add(inputTextField, gridConstraints);
		inputTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				keyboardKeyPressed(e);
			}
		});
		boxTextField[0] = new JTextField(); 
		boxTextField[0].setPreferredSize(new Dimension(80, 80)); 
		boxTextField[0].setEditable(true); 
		boxTextField[0].setBackground(Color.RED);
		boxTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
		boxTextField[0].setFont(new Font("Arial", Font.BOLD, 38));
		boxTextField[0].setBorder(null);
		boxTextField[0].setText("100");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 1 ;
		gridConstraints.gridy = 0 ; 
		gamePanel.add(boxTextField[0], gridConstraints);
			
		boxTextField[1] = new JTextField(); 
		boxTextField[1].setPreferredSize(new Dimension(80, 80)); 
		boxTextField[1].setEditable(false); 
		boxTextField[1].setBackground(Color.RED);
		boxTextField[1].setHorizontalAlignment(SwingConstants.CENTER);
		boxTextField[1].setFont(new Font("Arial", Font.BOLD, 38));
		boxTextField[1].setBorder(null);
		boxTextField[1].setText("0");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 1 ;
		gridConstraints.gridy = 4 ; 
		gamePanel.add(boxTextField[1], gridConstraints);
		
		gridLabel[0] = new JLabel();
		gridLabel[0].setPreferredSize(new Dimension(110, 10)); 
		gridLabel[0].setOpaque(true);
		gridLabel[0].setBackground(Color.BLUE); 
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		gridConstraints.gridwidth = 5;
		gridConstraints.insets = new Insets(5, 0, 5, 0); 
		gamePanel.add(gridLabel[0], gridConstraints);
		gridLabel[1] = new JLabel();
		gridLabel[1].setPreferredSize(new Dimension(110, 10)); 
		gridLabel[1].setOpaque(true);
		gridLabel[1].setBackground(Color.BLUE); 
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 3;
		gridConstraints.gridwidth = 5;
		gridConstraints.insets = new Insets(5, 0, 5, 0); 
		gamePanel.add(gridLabel[1], gridConstraints);
		
		gridLabel[2] = new JLabel();
		gridLabel[2].setPreferredSize(new Dimension(10, 110)); 
		gridLabel[2].setOpaque(true);
		gridLabel[2].setBackground(Color.BLUE); 
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 5;
		gridConstraints.insets = new Insets(0, 5, 0, 5); 
		gamePanel.add(gridLabel[2], gridConstraints);
		
		gridLabel[3] = new JLabel();
		gridLabel[3].setPreferredSize(new Dimension(10, 110)); 
		gridLabel[3].setOpaque(true);
		gridLabel[3].setBackground(Color.BLUE); 
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 3;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 5;
		gridConstraints.insets = new Insets(0, 5, 0, 5); 
		gamePanel.add(gridLabel[3], gridConstraints);
//---------------------------------------------------------------
		messageTextField = new JTextField();
		messageTextField.setPreferredSize(new Dimension(280, 50));
		messageTextField.setEditable(false); 
		messageTextField.setBackground(Color.YELLOW);
		messageTextField.setForeground(Color.BLUE); 
		messageTextField.setText("Wprowad¿ liczbê");
		messageTextField.setHorizontalAlignment(SwingConstants.CENTER);
		messageTextField.setFont(new Font("Arial", Font.BOLD, 16)); 
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 5;
		gridConstraints.insets = new Insets(10, 10, 10, 10); 
		getContentPane().add(messageTextField, gridConstraints); 
		
		outputTextField = new JTextField();
		outputTextField.setPreferredSize(new Dimension(280, 50));
		outputTextField.setEditable(false); 
		outputTextField.setBackground(Color.YELLOW);
		outputTextField.setForeground(Color.BLUE); 
		outputTextField.setText("");
		outputTextField.setHorizontalAlignment(SwingConstants.CENTER);
		outputTextField.setFont(new Font("Arial", Font.BOLD, 12)); 
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 7;
		gridConstraints.insets = new Insets(10, 10, 10, 10); 
		getContentPane().add(outputTextField, gridConstraints); 
//------------------------------------------------------------
		playersPanel.setPreferredSize(new Dimension(260, 240));
		playersPanel.setBackground(Color.WHITE);
		playersPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		playersPanel.setLayout(new GridBagLayout()); 
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 2;
		gridConstraints.insets = new Insets(5, 10, 5, 10); 
		getContentPane().add(playersPanel, gridConstraints); 
//-----------------------------------------------
		userTitleTextField.setPreferredSize(new Dimension(250, 20));
		userTitleTextField.setEditable(false); 
		userTitleTextField.setBackground(Color.CYAN);
		userTitleTextField.setHorizontalAlignment(SwingConstants.CENTER);
		userTitleTextField.setFont(new Font("Arial", Font.BOLD, 16));
		userTitleTextField.setBorder(null);
		userTitleTextField.setText("Najlepsze wyniki:");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0 ;
		gridConstraints.gridy = 0 ; 
		gridConstraints.gridheight = 2;
		gridConstraints.anchor = GridBagConstraints.WEST;
//		gridConstraints.insets = new Insets(5, 5, 5, 5); 
		playersPanel.add(userTitleTextField, gridConstraints);
		
		userTextField[0] = new JTextField(); 
		userTextField[0].setPreferredSize(new Dimension(250, 20));
		userTextField[0].setEditable(false); 
		userTextField[0].setBackground(Color.WHITE);
		userTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
		userTextField[0].setFont(new Font("Arial", Font.BOLD, 14));
		userTextField[0].setBorder(null);
		userTextField[0].setText("");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0 ;
		gridConstraints.gridy = 2 ; 
		gridConstraints.anchor = GridBagConstraints.WEST;
		playersPanel.add(userTextField[0], gridConstraints);
		
		userTextField[1] = new JTextField();  
		userTextField[1].setPreferredSize(new Dimension(250, 20));
		userTextField[1].setEditable(false); 
		userTextField[1].setBackground(Color.WHITE);
		userTextField[1].setHorizontalAlignment(SwingConstants.CENTER);
		userTextField[1].setFont(new Font("Arial", Font.BOLD, 14));
		userTextField[1].setBorder(null);
		userTextField[1].setText("");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0 ;
		gridConstraints.gridy = 3 ; 
		gridConstraints.anchor = GridBagConstraints.WEST;
		playersPanel.add(userTextField[1], gridConstraints);
		
		userTextField[2] = new JTextField();
		userTextField[2].setPreferredSize(new Dimension(250, 20));
		userTextField[2].setEditable(false); 
		userTextField[2].setBackground(Color.WHITE);
		userTextField[2].setHorizontalAlignment(SwingConstants.CENTER);
		userTextField[2].setFont(new Font("Arial", Font.BOLD, 14));
		userTextField[2].setBorder(null);
		userTextField[2].setText("");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0 ;
		gridConstraints.gridy = 4 ; 
		gridConstraints.anchor = GridBagConstraints.WEST;
		playersPanel.add(userTextField[2], gridConstraints);
		
		userTextField[3] = new JTextField();
		userTextField[3].setPreferredSize(new Dimension(250, 20));
		userTextField[3].setEditable(false); 
		userTextField[3].setBackground(Color.WHITE);
		userTextField[3].setHorizontalAlignment(SwingConstants.CENTER);
		userTextField[3].setFont(new Font("Arial", Font.BOLD, 14));
		userTextField[3].setBorder(null);
		userTextField[3].setText("");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0 ;
		gridConstraints.gridy = 5 ; 
		gridConstraints.anchor = GridBagConstraints.WEST;
		playersPanel.add(userTextField[3], gridConstraints);
		
		userTextField[4] = new JTextField();
		userTextField[4].setPreferredSize(new Dimension(250, 20));
		userTextField[4].setEditable(false); 
		userTextField[4].setBackground(Color.WHITE);
		userTextField[4].setHorizontalAlignment(SwingConstants.CENTER);
		userTextField[4].setFont(new Font("Arial", Font.BOLD, 14));
		userTextField[4].setBorder(null);
		userTextField[4].setText("");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0 ;
		gridConstraints.gridy = 6 ; 
		gridConstraints.anchor = GridBagConstraints.WEST;
		playersPanel.add(userTextField[4], gridConstraints);
		
		clearListButton.setText("Czyœæ listê wyników");
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 7;
		gridConstraints.insets = new Insets(10, 10, 10, 10); 		
		playersPanel.add(clearListButton, gridConstraints); 
		clearListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearListActionPerformed(e);
			}
		});
//------------------------------ BUTONY --------------------------------		
		buttonsPanel.setPreferredSize(new Dimension(260, 120));
		buttonsPanel.setBackground(Color.WHITE); 
		buttonsPanel.setLayout(new GridBagLayout()); 
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 3;
		getContentPane().add(buttonsPanel, gridConstraints); 
		checkButton.setText("Sprawd¿");
		gridConstraints = new GridBagConstraints(); 
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.insets = new Insets(5, 5, 5, 5); 
		buttonsPanel.add(checkButton, gridConstraints);
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkActionPerformed(e);
			}
		});
		newGameButton.setText("Nowa gra");
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		gridConstraints.insets = new Insets(5, 5, 5, 5); 		
		buttonsPanel.add(newGameButton, gridConstraints); 
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGameActionPerformed(e);
			}
		});
		exitButton.setText("Koniec");
		gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 2;
		gridConstraints.insets = new Insets(5, 5, 5, 5); 
		buttonsPanel.add(exitButton, gridConstraints);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
//------------------------- MENU -----------------------------------		
		m1File.add(m1newGame);
		m1newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGameActionPerformed(e);
			}
		});
		m1File.add(m1End);
		m1End.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
		m2Help.add(m2Best);
		m2Best.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bestActionPerformed(e);
			}
		});
		m2Help.add(m2About);
		m2About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutActionPerformed(e);
			}
		});
		menuBar.add(m1File);							
		menuBar.add(m2Help);
		setJMenuBar(menuBar);
		pack(); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) (0.5 * (screenSize.width -getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());
	}
//------------------------------------------------------------------------------
	private void bestActionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "Autor: Grzegorz Rapacz", "Znajd¿ liczbê by GR", JOptionPane.INFORMATION_MESSAGE);
	}
	private void checkActionPerformed(ActionEvent e) {	
		sprawdzLiczbe();
	}	
	private void newGameActionPerformed(ActionEvent e) {		
		nowaGra();
	}
	private void keyboardKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			sprawdzLiczbe();
		}
	}
	private void clearListActionPerformed(ActionEvent e) {
		usersLinkList.clear();
	    for (int i=0; i < 5; i++) {
    		userTextField[i].setText("");
    	} 
	}
//---------------------------------------------------------------------------------
	static void losujLiczbe() {
		Random r = new Random();
		randomNumber = Math.round(100 * (r.nextDouble()));
//		System.out.println(randomNumber);
	}	
	void nowaGra () {
		losujLiczbe();
		inputTextField.setText("");
		outputTextField.setText("");
		boxTextField[0].setText("100");
		boxTextField[1].setText("0");
		messageTextField.setText("Nowa gra!");
		ktoryRaz = 0;
		temp = "Twoje próby: ";
		maxNumber = 100.0d;
		minNumber = 0;
		hit = false;
	}
		
	void sprawdzLiczbe() {
		if (hit)							//blokada sprawdzania po wygranej
			return;
		if (ktoryRaz == 0) {				//za pierwszym razem pobierz czas
			startTime = System.currentTimeMillis();
		}
		if (inputTextField.getText().equals("")) {		//jak pole puste to return
			messageTextField.setText("Wprowad¿ liczbe z przedzia³u 1-99");
        	return;
		}
    	strNumbers = inputTextField.getText();			//jedna lub dwie cyfry 0-9
    	if (wzorzecLiczby.matcher(strNumbers).matches()) {
       		yoursNumber = Integer.parseInt(strNumbers);
        } 
    	else {
    		inputTextField.setText("");
    		messageTextField.setText("Wprowad¿ liczbe z przedzia³u 1-99");
        	return;
        }
    	if (ktoryRaz<1) {				//spacja po pierwszym razie
    		temp = temp + strNumbers;
    	}
    	else {
    		temp = temp + " " + strNumbers;
    	}
		outputTextField.setText(temp); 	//wyœwietl próby
		inputTextField.setText("");		//czyœæ input
    	ktoryRaz++;	
    	yoursNumber = Integer.parseInt(strNumbers);
    	if (yoursNumber == randomNumber) {				//zgad³
    		hit = true;
    		stopTime = System.currentTimeMillis();
    		iTime = (int) (stopTime-startTime);
    		sTime = (float) ((iTime/1000)+((iTime%1000)/10)*0.01);
    		messageTextField.setText("Zgad³es za " + ktoryRaz + " razem.\n Czas: " + sTime + " s");
    		inputTextField.setText(strNumbers);
//-------------------------------------------------------- 
    		if (usersLinkList.size()<5) { 		//nie pe³na tablica wyników 5
    			user[nrWins]= new User("Player "+nrWins, ktoryRaz, sTime);
    			usersLinkList.add(user[nrWins]);
			}
    		else {							//pe³na lista
    			Collections.sort(usersLinkList, new TimeAttemptsComparator());
				if (sTime<=usersLinkList.get(4).getTimeAttempts()) { //4
					usersLinkList.remove(4);
					user[4]= new User("Player "+nrWins, ktoryRaz, sTime);
					usersLinkList.add(user[4]);
				}
    		}
    		Collections.sort(usersLinkList, new TimeAttemptsComparator());		//wypisz posortowan¹ liste 	
    		User us;
    		for (int i=0; i < usersLinkList.size(); i++) {
    			us = usersLinkList.get(i);
    			userTextField[i].setText(us.toString());
    		} 		
//--------------------------------------------------------
    		nrWins++;
    		return;
    	}
    	if (yoursNumber < randomNumber) {				//Za malo
    		messageTextField.setText("ZA MA£O. Spróbuj jeszcze raz");
    		if (yoursNumber > minNumber) {
    			boxTextField[1].setText(strNumbers);
    			minNumber = yoursNumber;
    		}
    		
    		return;
    	}
    	if (yoursNumber > randomNumber)	{				//Za duzo
    		messageTextField.setText("ZA DU¯O. Spróbuj jeszcze raz");
    		if (yoursNumber < maxNumber) {
    			boxTextField[0].setText(strNumbers);
    			maxNumber = yoursNumber;
    		}
    		return;
    	}
		return;	
    }
		
	private void aboutActionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "Znajd¿ wylosowan¹ liczbê z przedzia³u 1-99\nAutor: Grzegorz Rapacz", "Znajd¿ liczbê by GR", JOptionPane.INFORMATION_MESSAGE);
	}
	private void exitActionPerformed(ActionEvent e) {
		fileRW.WriteObjectToFile(usersLinkList);
		System.exit(0); 
	}
	private void exitForm(WindowEvent e) {
		fileRW.WriteObjectToFile(usersLinkList);
		System.exit(0); 
	}	
}