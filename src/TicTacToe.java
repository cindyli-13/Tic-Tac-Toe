import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

/**
 * This program is a Tic Tac Toe GUI. It also features 
 * a human vs. AI function.
 * 
 * @author Cindy Li
 * @since Thursday, July 13, 2017
 */
@SuppressWarnings("serial")
public class TicTacToe extends JFrame implements ActionListener {

	private JPanel board, everything;
	private JLabel xLabel, xScoreIndicator, oLabel, oScoreIndicator, playersTurnLabel;
	private JButton[] buttons = new JButton[9];
	private JButton dummy = new JButton();
	
	private boolean starter; // true = player X's turn, false = player O's turn
	private boolean turn; // true = player X's turn, false = player O's turn
	private boolean ai; // true = human vs AI, false = human vs human
	private int moves, xScore, oScore;
	private Timer tm;
	private char[] aiBoard = new char[9];
	
	private final int BUTTON_SIDE_LENGTH;
	private final Color BUTTON_COLOR;
	private final Font BUTTON_TEXT_FONT;
	private final Color X_TEXT_COLOR;
	private final Color O_TEXT_COLOR;
	private final Color BACKGROUND_COLOR;
	
	
	// main
	public static void main(String[] args) {
		new TicTacToe();
	}
	
	
	// constructor
	public TicTacToe() {
		
		super("Tic Tac Toe");
		
		
		// *************** CONSTANTS **************
		
		// set button side length
		BUTTON_SIDE_LENGTH = 110;
		
		// set button color
		BUTTON_COLOR = Color.WHITE;
		
		// set X text color
		X_TEXT_COLOR = Color.RED;
		
		// set O text color
		O_TEXT_COLOR = Color.BLUE;
		
		// set button text font
		BUTTON_TEXT_FONT = new Font("Arial", Font.BOLD, 90);
		
		// set background color
		BACKGROUND_COLOR = new Color(235, 235, 235);
		
		// ****************************************
		
		
		// set up JFrame
		setSize((BUTTON_SIDE_LENGTH * 3) + 50, ((BUTTON_SIDE_LENGTH * 3) + 245));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		// set up board and array of buttons		
		board = new JPanel();
		board.setLayout(new GridLayout(3, 3));
		board.setLocation(25, 80);
		board.setSize((BUTTON_SIDE_LENGTH * 3), (BUTTON_SIDE_LENGTH * 3));
		
		for (int i = 0; i < buttons.length; i++) {
			
			JButton b = new JButton();
			b.setBackground(BUTTON_COLOR);
			b.setFont(BUTTON_TEXT_FONT);
			b.setFocusPainted(false);
			b.addActionListener(this);
			
			buttons[i] = b;
			board.add(b);
		}
		
		
		// set up player's turn label
		playersTurnLabel = new JLabel();
		playersTurnLabel.setLocation(25, 25);
		playersTurnLabel.setSize(board.getWidth(), 30);
		playersTurnLabel.setFont(new Font("Arial", Font.BOLD, 18));
		playersTurnLabel.setBorder(BorderFactory.createEtchedBorder());
		playersTurnLabel.setBackground(BUTTON_COLOR);
		playersTurnLabel.setOpaque(true);
		playersTurnLabel.setHorizontalAlignment(JLabel.CENTER);
		playersTurnLabel.setVerticalAlignment(JLabel.CENTER);
		
		
		// set up player X label
		xLabel = new JLabel("PLAYER X");
		xLabel.setLocation(25, (25 + board.getHeight() + 80));
		xLabel.setSize((board.getWidth() / 2) - 20, 30);
		xLabel.setFont(new Font("Arial", Font.BOLD, 18));
		xLabel.setBorder(BorderFactory.createEtchedBorder());
		xLabel.setBackground(BUTTON_COLOR);
		xLabel.setForeground(X_TEXT_COLOR);
		xLabel.setOpaque(true);
		xLabel.setHorizontalAlignment(JLabel.CENTER);
		xLabel.setVerticalAlignment(JLabel.CENTER);
		
		// set up player X score indicator
		xScoreIndicator = new JLabel();
		xScoreIndicator.setLocation(25, (25 + board.getHeight() + 105));
		xScoreIndicator.setSize((board.getWidth() / 2) - 20, 60);
		xScoreIndicator.setFont(new Font("Arial", Font.BOLD, 50));
		xScoreIndicator.setBorder(BorderFactory.createEtchedBorder());
		xScoreIndicator.setBackground(BUTTON_COLOR);
		xScoreIndicator.setForeground(X_TEXT_COLOR);
		xScoreIndicator.setOpaque(true);
		xScoreIndicator.setHorizontalAlignment(JLabel.CENTER);
		xScoreIndicator.setVerticalAlignment(JLabel.CENTER);
		
		
		// set up player O label
		oLabel = new JLabel("PLAYER O");
		oLabel.setLocation((45 + (board.getWidth() / 2)), (25 + board.getHeight() + 80));
		oLabel.setSize((board.getWidth() / 2) - 20, 30);
		oLabel.setFont(new Font("Arial", Font.BOLD, 18));
		oLabel.setBorder(BorderFactory.createEtchedBorder());
		oLabel.setBackground(BUTTON_COLOR);
		oLabel.setForeground(O_TEXT_COLOR);
		oLabel.setOpaque(true);
		oLabel.setHorizontalAlignment(JLabel.CENTER);
		oLabel.setVerticalAlignment(JLabel.CENTER);
				
		// set up player O score indicator
		oScoreIndicator = new JLabel();
		oScoreIndicator.setLocation((45 + (board.getWidth() / 2)), (25 + board.getHeight() + 105));
		oScoreIndicator.setSize((board.getWidth() / 2) - 20, 60);
		oScoreIndicator.setFont(new Font("Arial", Font.BOLD, 50));
		oScoreIndicator.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		oScoreIndicator.setBackground(BUTTON_COLOR);
		oScoreIndicator.setForeground(O_TEXT_COLOR);
		oScoreIndicator.setOpaque(true);
		oScoreIndicator.setHorizontalAlignment(JLabel.CENTER);
		oScoreIndicator.setVerticalAlignment(JLabel.CENTER);
				
		
		
		// set up everything JPanel
		everything = new JPanel();
		everything.setLayout(null);
		everything.setBackground(BACKGROUND_COLOR);
		everything.add(playersTurnLabel);
		everything.add(board);
		everything.add(xLabel);
		everything.add(xScoreIndicator);
		everything.add(oLabel);
		everything.add(oScoreIndicator);
		
		add(everything);
		
		
		// set player scores
		xScore = 0;
		xScoreIndicator.setText("0");
		oScore = 0;
		oScoreIndicator.setText("0");
		
		
		// set starter player's turn
		starter = true;
		turn = true;
		
		setPlayersTurnLabel();
		
		
		// initialize moves
		moves = 0;
		
		// set up timer
		tm = new Timer(700, this);
		
		
		// reveal JFrame
		setVisible(true);
		
		
		// ask for human vs human or human vs AI
		Object[] options = {"Human vs. Human", "Human vs. AI"};
		int choice = JOptionPane.showOptionDialog(null, "Choose one:", "", 
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
		
		if (choice == 1)
			ai = true;
	}

	
	/**
	 * This method listens for when a button is clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// timer (only for AI)
		if (e.getSource() == tm) {
			
			JButton b = aiMakeMove();
			
			// turn off timer
			tm.stop();
			
			// set it to O
			b.setForeground(O_TEXT_COLOR);
			b.setText("O");
			
			// check if player has won
			if (playerWon()) {
				
				displayGameOver('o');
			}
			else {
				// increment moves
				moves++;
				
				// change to other player's turn
				turn = !turn;
				
				setPlayersTurnLabel();
			}
		}
		
		else {
			
			JButton b = (JButton) e.getSource();
			
			// check if the button isn't already filled
			if (b.getText().equals("")) {
				
				// if it's player X's turn
				if (turn) {
					
					// set it to X
					b.setForeground(X_TEXT_COLOR);
					b.setText("X");
					
					// check if player has won
					if (playerWon()) {
						
						displayGameOver('x');
					}
					else {
						// increment moves
						moves++;
						
						// change to other player's turn
						turn = !turn;
						
						setPlayersTurnLabel();
					}
				}
				
				// else it's player O's turn (make sure AI is not on)
				else if (!ai){
					
					// set it to O
					b.setForeground(O_TEXT_COLOR);
					b.setText("O");
					
					// check if player has won
					if (playerWon()) {
						
						displayGameOver('o');
					}
					else {
						// increment moves
						moves++;
						
						// change to other player's turn
						turn = !turn;
						
						setPlayersTurnLabel();
					}
				}
				
				// only if AI is turned on and its the AI's turn
				if (ai && !turn && moves < 9)
					startTimer();
			}
		}
		
		// check if it's a draw
		if (moves == 9) {
			
			displayGameOver('!');
		}
	}
	
	/**
	 * This method checks whether a player has won or not.
	 * @return true or false
	 */
	private boolean playerWon() {
		
		// check each win possibility
		if ((!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[1].getText()) 
				&& buttons[0].getText().equals(buttons[2].getText())) // top horizontal
				
			|| (!buttons[3].getText().equals("") && buttons[3].getText().equals(buttons[4].getText())
				&& buttons[3].getText().equals(buttons[5].getText())) // middle horizontal
			
			|| (!buttons[6].getText().equals("") && buttons[6].getText().equals(buttons[7].getText())
				&& buttons[6].getText().equals(buttons[8].getText())) // bottom horizontal
			
			|| (!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[3].getText())
					&& buttons[0].getText().equals(buttons[6].getText())) // left vertical
			
			|| (!buttons[1].getText().equals("") && buttons[1].getText().equals(buttons[4].getText())
					&& buttons[1].getText().equals(buttons[7].getText())) // middle vertical
			
			|| (!buttons[2].getText().equals("") && buttons[2].getText().equals(buttons[5].getText())
					&& buttons[2].getText().equals(buttons[8].getText())) // right vertical
			
			|| (!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[4].getText())
					&& buttons[0].getText().equals(buttons[8].getText())) // diagonal top left to bottom right
			
			|| (!buttons[2].getText().equals("") && buttons[2].getText().equals(buttons[4].getText())
					&& buttons[2].getText().equals(buttons[6].getText()))) // diagonal top right to bottom left
		{
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * This method displays the game over pop-up. 
	 * Either one player wins, or it is a draw.
	 * @param winner  The player who won, or "!" 
	 * for a draw
	 */
	private void displayGameOver(char winner) {
		
		// set turn label to empty
		playersTurnLabel.setText("");
		
		
		// if player X won
		if (winner == 'x') {
			
			JOptionPane.showMessageDialog(this, "Player X wins!");
			
			xScore++;
			xScoreIndicator.setText(Integer.toString(xScore));
		}
		
		// else if player O won
		else if (winner == 'o') {
					
			JOptionPane.showMessageDialog(this, "Player O wins!");
			
			oScore++;
			oScoreIndicator.setText(Integer.toString(oScore));
		}
		
		// else it was a draw
		else {
			
			JOptionPane.showMessageDialog(this, "Draw!");
		}
		
		// reset the game
		resetGame();
	}

	
	/**
	 * This method resets the game.
	 */
	private void resetGame() {
		
		// reset all buttons
		for (int i = 0; i < buttons.length; i++) {
			
			buttons[i].setText("");
		}
		
		// reset turn
		starter = !starter;
		turn = starter;
		
		// reset moves
		moves = 0;
				
		// set player's turn label
		setPlayersTurnLabel();
		
		
		// if AI is turned on and its the AI's turn, start timer
		if (ai && !turn)
			tm.restart();
	}
	
	
	/**
	 * This method sets the player's turn label 
	 * depending on whose turn it is.
	 */
	private void setPlayersTurnLabel() {
		
		// if it's player X's turn
		if (turn) {
						
			playersTurnLabel.setForeground(X_TEXT_COLOR);
			playersTurnLabel.setText("PLAYER X'S TURN");
		}
			
		// else it's player O's turn
		else {
				
			playersTurnLabel.setForeground(O_TEXT_COLOR);
				
			// if AI is turned on
			if (ai) {
					
				playersTurnLabel.setText("WAITING FOR PLAYER O...");
			}
				
			else {
				playersTurnLabel.setText("PLAYER O'S TURN");
			}
		}
		
	}
	
	
	/**
	 * This method is if AI is turned on. It 
	 * starts the timer.
	 */
	public void startTimer() {
		
		tm.restart();
	}
	
	
	/**
	 * This method contains all the algorithms for 
	 * the AI.
	 * @return The button where the AI decides to move
	 */
	public JButton aiMakeMove() {
		
		// store the board
		for (int i = 0; i < buttons.length; i++) {
			
			if (buttons[i].getText().equals("X"))
				aiBoard[i] = 'x';
			else if (buttons[i].getText().equals("O"))
				aiBoard[i] = 'o';
			else
				aiBoard[i] = '-';
		}
		
		
		// if there is an opportunity to win
		JButton b = winOrBlock('o');
		
		if (b != dummy)
			return b;
		
		
		// else if a block is needed
		b = winOrBlock('x');
		
		if (b != dummy)
			return b;
		
		
		// else if its the first move, go any corner
		if (moves == 0) {
			
			Random r = new Random();
			int x = r.nextInt(4);
			
			switch (x) {
				case 0:
					return buttons[0];
				case 1:
					return buttons[2];
				case 2:
					return buttons[6];
				case 3:
					return buttons[8];
			}
		}
			
		
		// else if it's the second move
		else if (moves == 1) {
			
			// if opponent went center, go any corner
			if (buttons[4].getText().equals("X")) {
						
				Random r = new Random();
				int x = r.nextInt(4);
							
				switch (x) {
					case 0:
						return buttons[0];
					case 1:
						return buttons[2];
					case 2:
						return buttons[6];
					case 3:
						return buttons[8];
				}
			}
			
			// else if opponent didn't go center, go center
			else {
				
				return buttons[4];
			}
		}
		
		
		// else if it's the third move
		else if (moves == 2) {
			
			// if opponent didn't go center, go the corner of the same row of first piece
			if (aiBoard[4] == '-') {
				
				int x = -1;
				
				// find your piece
				for (int i = 0; i < aiBoard.length; i+=2) {
					
					if (aiBoard[i] == 'o') {
						x = i;
						break;
					}
				}
				
				switch (x) {
				
					case 0:
						if (aiBoard[1] == '-' && aiBoard[2] == '-')
							return buttons[2];
						else
							return buttons[6];
					case 2:
						if (aiBoard[0] == '-' && aiBoard[1] == '-')
							return buttons[0];
						else
							return buttons[8];
					case 6:
						if (aiBoard[0] == '-' && aiBoard[3] == '-')
							return buttons[0];
						else
							return buttons[8];
					case 8:
						if (aiBoard[2] == '-' && aiBoard[5] == '-')
							return buttons[2];
						else
							return buttons[6];
				}
			}
			
			// else opponent went center; go corner opposite of first piece
			else {
				
				int x = -1;
				
				// find your piece
				for (int i = 0; i < aiBoard.length; i+=2) {
					
					if (aiBoard[i] == 'o') {
						x = i;
						break;
					}
				}
				
				switch (x) {
				
					case 0:
						return buttons[8];
					case 2:
						return buttons[6];
					case 6:
						return buttons[2];
					case 8:
						return buttons[0];
				}
			}
		}
		
		
		// else if it's the fourth move
		else if (moves == 3) {
			
			// if opponent went center-corner, go corner
			if (aiBoard[4] == 'x' && (aiBoard[0] == 'x' || aiBoard[2] == 'x' 
								   || aiBoard[6] == 'x' || aiBoard[8] == 'x')) {
				
				for (int i = 0; i < aiBoard.length; i+=2) {
					
					if (aiBoard[i] == '-')
						return buttons[i];
				}
			}
			
			// else if opponent went corner-corner, go edge
			else if ((aiBoard[0] == 'x' && aiBoard[8] == 'x') || (aiBoard[2] == 'x' && aiBoard[6] == 'x')) {
				
				Random r = new Random();
				int x = r.nextInt(4);
				
				return buttons[x * 2 + 1];
			}
			
			// else if opponent went corner-edge, go corner between their pieces
			else if ((aiBoard[3] == 'x' && aiBoard[2] == 'x') || (aiBoard[1] == 'x' && aiBoard[6] == 'x'))
				return buttons[0];
			else if ((aiBoard[0] == 'x' && aiBoard[5] == 'x') || (aiBoard[8] == 'x' && aiBoard[1] == 'x'))
				return buttons[2];
			else if ((aiBoard[0] == 'x' && aiBoard[7] == 'x') || (aiBoard[8] == 'x' && aiBoard[3] == 'x'))
				return buttons[6];
			else if ((aiBoard[2] == 'x' && aiBoard[7] == 'x') || (aiBoard[6] == 'x' && aiBoard[5] == 'x'))
				return buttons[8];
		
			// else if opponent went edge-edge (adjacent), go corner between their pieces
			else if (aiBoard[1] == 'x' && aiBoard[3] == 'x')
				return buttons[0];
			else if (aiBoard[1] == 'x' && aiBoard[5] == 'x') 
				return buttons[2];
			else if (aiBoard[3] == 'x' && aiBoard[7] == 'x')
				return buttons[6];
			else if (aiBoard[5] == 'x' && aiBoard[7] == 'x') 
				return buttons[8];
			
			// else opponent went edge-edge (not adjacent); go any corner
			else {
				
				Random r = new Random();
				int x = r.nextInt(4);
				
				switch (x) {
					case 0:
						return buttons[0];
					case 1:
						return buttons[2];
					case 2:
						return buttons[6];
					case 3:
						return buttons[8];
				}
			}
					
		}
		
		
		// else if it's the fifth move
		else if (moves == 4) {
			
			// if opponent didn't go center, secure a win
			if (aiBoard[4] == '-') {
				
				// if opponent went corner, go other corner
				if (aiBoard[0] == 'x' || aiBoard[8] == 'x') {
					
					if (aiBoard[2] == '-')
						return buttons[2];
					else if (aiBoard[6] == '-')
						return buttons[6];
				}
				else if (aiBoard[2] == 'x' || aiBoard[6] == 'x') {
					
					if (aiBoard[0] == '-')
						return buttons[0];
					else if (aiBoard[8] == '-')
						return buttons[8];
				}
				
				// else go center
				else
					return buttons[4];
			}
			
			// else if opponent went center-corner
			else if (aiBoard[0] == 'x' && aiBoard[8] == '-')
				return buttons[8];
			else if (aiBoard[2] == 'x' && aiBoard[6] == '-')
				return buttons[6];
			else if (aiBoard[6] == 'x' && aiBoard[2] == '-')
				return buttons[2];
			else if (aiBoard[8] == 'x' && aiBoard[0] == '-')
				return buttons[0];
		}
		
		
		// the rest
		if (aiBoard[4] == 'o') {
				
			Random r = new Random();
			int x = r.nextInt(2);
			
			if (aiBoard[1] == '-' && aiBoard[7] == '-') {
				
				if (x == 0)
					return buttons[1];
				else
					return buttons[7];
			}
			
			else if (aiBoard[3] == '-' && aiBoard[5] == '-') {
				
				if (x == 0)
					return buttons[3];
				else
					return buttons[5];
			}
			
			else if (aiBoard[0] == '-' && aiBoard[8] == '-') {
				
				if (x == 0)
					return buttons[0];
				else
					return buttons[8];
			}
			
			else if (aiBoard[2] == '-' && aiBoard[6] == '-') {
				
				if (x == 0)
					return buttons[2];
				else
					return buttons[6];
			}
		}
		if (aiBoard[1] == '-') {
			
			if (aiBoard[0] == '-' && aiBoard[2] == 'o')
				return buttons[0];
			else if (aiBoard[0] == 'o' && aiBoard[2] == '-')
				return buttons[2];
		}
		if (aiBoard[3] == '-') {
			
			if (aiBoard[0] == '-' && aiBoard[6] == 'o')
				return buttons[0];
			else if (aiBoard[0] == 'o' && aiBoard[6] == '-')
				return buttons[6];
		}
		if (aiBoard[5] == '-') {
			
			if (aiBoard[2] == '-' && aiBoard[8] == 'o')
				return buttons[2];
			else if (aiBoard[2] == 'o' && aiBoard[8] == '-')
				return buttons[8];
		}
		if (aiBoard[7] == '-') {
			
			if (aiBoard[6] == '-' && aiBoard[8] == 'o')
				return buttons[6];
			else if (aiBoard[6] == 'o' && aiBoard[8] == '-')
				return buttons[8];
		}
		for (int i = 0; i < aiBoard.length; i++) {
			
			if (aiBoard[i] == '-')
				return buttons[i];
		}
		
		
		// ***** THE PROGRAM SHOULDN"T REACH THIS POINT *****
		return dummy;
		
	}
	
	
	/**
	 * This method is a helper method for the AI 
	 * algorithm. It checks for an opportunity to 
	 * win, then checks if a block is needed.
	 * @return
	 */
	public JButton winOrBlock(char player) {
		
		if (aiBoard[0] == '-' && ((aiBoard[1] == player && aiBoard[2] == player) 
				   || (aiBoard[3] == player && aiBoard[6] == player) 
				   || (aiBoard[4] == player && aiBoard[8] == player)))
			return buttons[0];

		else if (aiBoard[2] == '-' && ((aiBoard[0] == player && aiBoard[1] == player) 
	   					|| (aiBoard[5] == player && aiBoard[8] == player) 
	   					|| (aiBoard[4] == player && aiBoard[6] == player)))
			return buttons[2];

		else if (aiBoard[6] == '-' && ((aiBoard[7] == player && aiBoard[8] == player) 
						|| (aiBoard[0] == player && aiBoard[3] == player) 
						|| (aiBoard[2] == player && aiBoard[4] == player)))
			return buttons[6];

		else if (aiBoard[8] == '-' && ((aiBoard[6] == player && aiBoard[7] == player) 
						|| (aiBoard[2] == player && aiBoard[5] == player) 
						|| (aiBoard[0] == player && aiBoard[4] == player)))
			return buttons[8];

		else if (aiBoard[1] == '-' && ((aiBoard[0] == player && aiBoard[2] == player) 
						|| (aiBoard[4] == player && aiBoard[7] == player)))
			return buttons[1];

		else if (aiBoard[3] == '-' && ((aiBoard[0] == player && aiBoard[6] == player) 
						|| (aiBoard[4] == player && aiBoard[5] == player)))
			return buttons[3];

		else if (aiBoard[5] == '-' && ((aiBoard[2] == player && aiBoard[8] == player) 
						|| (aiBoard[3] == player && aiBoard[4] == player)))
			return buttons[5];

		else if (aiBoard[7] == '-' && ((aiBoard[1] == player && aiBoard[4] == player) 
						|| (aiBoard[6] == player && aiBoard[8] == player)))
			return buttons[7];

		else if (aiBoard[4] == '-' && ((aiBoard[0] == player && aiBoard[8] == player) 
						|| (aiBoard[2] == player && aiBoard[6] == player)
						|| (aiBoard[1] == player && aiBoard[7] == player)
						|| (aiBoard[3] == player && aiBoard[5] == player)))
			return buttons[4];
		
		
		// return dummy if no win or block detected
		return dummy;
	}
	
} // end class
