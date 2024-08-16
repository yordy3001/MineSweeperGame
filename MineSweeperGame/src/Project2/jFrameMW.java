package Project2;
//https://youtu.be/NfCQ1iptZjY
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class jFrameMW extends JFrame{
	
	
	Grid grid;
//	mines mines;
//	Score score;
	private int rows;
	private int columns;
	private int numbombs;
	 
		public jFrameMW(){	
		startGame();
	    setupUI();
	}
		
	private void startGame() {
		grid = mode();
		rows = grid.getNumRows();
        columns = grid.getNumColumns();
        numbombs = grid.getNumBombs();
        
	}
	
	 private void setupUI() {
		 	Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\latest.png");  
		 	Score s1 = new Score();
		 	mines m1 = new mines(s1);
		 	JPanel mainJP = new JPanel(new BorderLayout());

		 	mainJP.setBackground(Color.white);
		 	mainJP.add(m1, BorderLayout.CENTER);
		 	mainJP.add(s1, BorderLayout.NORTH);
		 	add(mainJP);
	        
	        setTitle("Minesweeper");
	        setSize(500,500);
	        setIconImage(icon);
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        grid.printEveryting(); //Allow cheating
	    }
	
	 private Grid mode() {
			ImageIcon controller = new ImageIcon ("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\Controller.png");
			ImageIcon controllerScaled = new ImageIcon(controller.getImage().getScaledInstance(85,70,Image.SCALE_FAST));
		    String[] options = {"Easy", "Normal", "Hard"};
		    int result = JOptionPane.showOptionDialog(
		            null, "Choose a GameMode:", "MineSweeper",
		            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
		            controllerScaled, options, options[0]);
		    		

		    Grid grid = new Grid(); 
		    switch (result) {
		        case 0:
		            System.out.println("Easy mode selected \n");
		            grid.initializeEasyMode();
		            break;
		        case 1:
		            int r = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows"));
		            int c = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns"));
		            System.out.println("Normal mode selected \n");
		            grid.initializeNormalMode(r, c);
		            break;
		        case 2:
		            int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows"));
		            int columns = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns"));
		            int bombs = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of bombs"));
		            System.out.println("Hard mode selected \n");
		            grid.initializeHardMode(rows, columns, bombs);
		            break;
		        default:
		            System.out.println("Dialog closed without selection \n");
		            System.exit(0);
		            break;
		    }
			return grid;
		}

	 

	private class mines extends JPanel implements ActionListener{
		private Score scorePanel;
		private JButton [][] board;

		
		ImageIcon bombImg = new ImageIcon("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\newbomb.png");
		ImageIcon cell = new ImageIcon("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\cell.png");
		
		ImageIcon bombImgScaled = new ImageIcon(bombImg.getImage().getScaledInstance(30,30, Image.SCALE_FAST));
		ImageIcon cellScaled = new ImageIcon(cell.getImage().getScaledInstance(45,45, Image.SCALE_FAST));
		
		public mines(Score scorePanel) { //Constructor
		    this.scorePanel = scorePanel;
		    setLayout(new GridLayout(rows, columns));
		    displayBoard();
		}
		
		
		public void displayBoard() {
			board = new JButton[rows][columns];//initializing 
					
			for(int row=0; row<rows; row++){
				for(int col=0; col<columns; col++){
					Color color = Color.decode("#c6c6c6");
					board[row][col] = new JButton(cellScaled);	
					board[row][col].setForeground(RCG());
					board[row][col].setBackground(color);
					board[row][col].setFont(new Font("Dialog", Font.PLAIN,  20));
	                board[row][col].addActionListener(this);
	                board[row][col].setEnabled(true);          
	                this.add(board[row][col]);

			        }
			    }
			}
		
		public void actionPerformed(ActionEvent e) {
			//determine button clicked
			JButton btnClicked = (JButton)e.getSource();
			
			int row = -1;
		    int col = -1;
		    for (int i = 0; i < rows; i++) {
		        for (int j = 0; j < columns; j++) {
		            if (board[i][j] == btnClicked) {
		                row = i;
		                col = j;
		                break;
		            }
		        }
		    }
    
		    if (grid.isBombAtLocation(row, col)) {
		        loser();
		    } else {
		        int bombCount = grid.getCountAtLocation(row, col);
		        board[row][col].setText(Integer.toString(bombCount));
		        board[row][col].setFocusable(false);
		        board[row][col].setIcon(null);
		        
		        if (!board[row][col].isFocusable()) {
		        	System.out.println("Checking for winner...");
		        	scorePanel.updateScore(calculateRemainingCells());
		            winner();
		        }
		    }
		}
		
		
		private int calculateRemainingCells() {
		    int revealedNonBombCells = 0;
		    for (int row = 0; row < rows; row++) {
		        for (int col = 0; col < columns; col++) {
		            if (!grid.isBombAtLocation(row, col) && !board[row][col].isFocusable()) {
		                revealedNonBombCells++;
		            }
		        }
		    }
		    return (rows * columns) - numbombs - revealedNonBombCells;
		}
		
		
		public void showCells() {
			for (int i = 0; i < rows; i++) {
		        for (int j = 0; j < columns; j++) {
		            JButton currentButton = board[i][j];
		            if (grid.isBombAtLocation(i, j)) {
		                currentButton.setIcon(bombImgScaled);;
		                
		            } if (!grid.isBombAtLocation(i, j)) {
		                int bombCount = grid.getCountAtLocation(i, j);
		                currentButton.setText(Integer.toString(bombCount));
		                board[i][j].setIcon(null);
		                board[i][j].setFocusable(false);
		            }
		        }
		    }
		}
		
		public void loser() {
			ImageIcon loser = new ImageIcon ("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\LOSER.png");
			ImageIcon loserScaled = new ImageIcon(loser.getImage().getScaledInstance(90,90,Image.SCALE_FAST));
			JOptionPane.showMessageDialog(null, "LOSER!", "Game Over!", DISPOSE_ON_CLOSE, loserScaled);
			//JOptionPane.showMessageDialog(null, "Game Over!");
			showCells();
			pAgain();
			
		}
		
		public void winner() {
		    int revealedNonBombCells = 0;
		    int totalNonBombCells = (rows * columns) - numbombs;

		    for (int row = 0; row < rows; row++) {
		        for (int col = 0; col < columns; col++) {
		            if (!grid.isBombAtLocation(row, col) && !board[row][col].isFocusable()) {
		                revealedNonBombCells++;
		            }
		        }
		    }

		    if (revealedNonBombCells == totalNonBombCells) {
		    	 
		        ImageIcon cookie = new ImageIcon("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\cookie2.png");
		        ImageIcon cookieScaled = new ImageIcon(cookie.getImage().getScaledInstance(150,150, Image.SCALE_FAST));

		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		        JLabel label1 = new JLabel("You Won!!");
		        JLabel label2 = new JLabel("This is your prize:");
		        JLabel iconLabel = new JLabel(cookieScaled);

		        panel.add(label1);
		        panel.add(label2);
		        panel.add(iconLabel);

		        JOptionPane.showMessageDialog(null, panel, "Congratulations!", JOptionPane.PLAIN_MESSAGE);
		        showCells();
		        pAgain();
		        
		    }
		}
		
		public void pAgain() {
			ImageIcon icon = new ImageIcon ("C:\\Users\\yordi\\eclipse-workspace\\CMP 168\\src\\Images\\latest.png");
			ImageIcon iconScaled = new ImageIcon(icon.getImage().getScaledInstance(50,50,Image.SCALE_FAST));
			 String[] YN= {"Yes","No"};

			  int result = JOptionPane.showOptionDialog(null, "Do you want to play again?", "Loser!", 
					  DO_NOTHING_ON_CLOSE, JOptionPane.PLAIN_MESSAGE, iconScaled, YN, YN);
					  
			switch (result) {
			case 0:
				System.out.println("\nYou want to play again. \n");
				restartGame();
				break;
				
			case 1:
				System.out.println("\nYou dont want to play bye bye! \n");
				closeApplication();
				break;
				
			default:
				System.out.println("\nYou closed the tab bye bye! \n");
				closeApplication();
				break;
			}		  
		}
		
		private void restartGame() {
	        getContentPane().removeAll();
	        startGame();
	        setupUI();
	        revalidate();
	        repaint();
	    }
		
		private void closeApplication() { //I look it up in the Internet
		    dispose();  
		    System.exit(0);  
		}
		
		
		private Color RCG() {
		    Random random = new Random();
		    int i = random.nextInt(3);

		    if (i == 0) {
		        return new Color(238, 59, 59); // Red
		    }
		    if (i == 1) {
		        return new Color(27, 27, 247); // Blue
		    }
		    if (i == 2) {
		        return new Color(0, 128, 0); // Green
		    }
		    return null;
		}

	}//End of the jPanel.	
	
	

	
	private class Score extends JPanel {
	    private JLabel score;
	    private int remainingCells; 

	    Score() {
	        setLayout(new BorderLayout());
	        setSize(300, 300);

	        remainingCells = (rows * columns) - numbombs;
	        score = new JLabel("Remaining cells: 0");
	        score.setForeground(Color.red);
	        score.setFont(new Font("Dialog", Font.PLAIN, 20));
	        updateScoreLabel();
	        add(score, BorderLayout.CENTER);
	    }

	    public void updateScore(int remainingCells) {
	        this.remainingCells = remainingCells;
	        updateScoreLabel();
	    }

	    private void updateScoreLabel() {
	        score.setText("Remaining cells: " + remainingCells);
	    }
	}
	
} //End of the Jframe.



	
	

	
	
	
	
	
	
	
	
	
	
	
	