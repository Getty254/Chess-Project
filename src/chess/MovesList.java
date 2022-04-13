package chess;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * This class represents the grid of moves
 * that is displayed under the heading Game Moves.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class MovesList extends GridPane {

	/** List of moves.*/
	private static ArrayList<Move> movesAL = new ArrayList<Move>();
	/** String of moves with move numbers.*/
	private static StringBuilder movesPGN = new StringBuilder();
	/** Label to listen for change in move number.*/
	private Label triggerMoveNum;

	/**
	 * MovesList constructor.
	 * @param trigger Label
	 */
	public MovesList(Label trigger) {
		// Add boundaries to the columns of the
		// moves list GridPane
		this.getColumnConstraints().add(
				new ColumnConstraints(30));
		this.getColumnConstraints().add(
				new ColumnConstraints(75));
		this.getColumnConstraints().add(
				new ColumnConstraints(75));
		this.triggerMoveNum = trigger;

	}

	/**
	 * Get the list of moves.
	 * 
	 * @return ArrayList of Move objects
	 */
	public static ArrayList<Move> getMovesAl() {
		return movesAL;
	}
	
	/**
	 * Gets the Portable Game Notation (PGN).
	 *
	 * @return String of all the moves of the game
	 */
	public static String getMovesPGN() {
		return movesPGN.toString();
	}

	/**
	 * Adds the player's move to the list of moves on the right
	 * side of the screen.
	 *
	 * @param move String containing the player's move in
	 * 				Long Algebraic Notation
	 */
	public void updateMovesList(String move) {
		if(BoardGUI.getTurn() == 0) {
        	// Add move number to move list
    		Label moveNumLabel = new Label(BoardGUI.getMoveNumber() + ".");
    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
    		this.add(moveNumLabel, 0, BoardGUI.getMoveNumber());

    		// Add white move to move list
    		Label whiteMove = new Label(move);
			GridPane.setHalignment(whiteMove, HPos.CENTER);
			this.add(whiteMove, 1, BoardGUI.getMoveNumber());

			triggerMoveNum.setText("moved");
			movesPGN.append(BoardGUI.getMoveNumber() + ". " + move);
    	}
    	else {
    		// Add black move to move list
    		Label blackMove = new Label(move);
			GridPane.setHalignment(blackMove, HPos.CENTER);
			this.add(blackMove, 2, BoardGUI.getMoveNumber());

			movesPGN.append(" " + move + " ");

			// Only increment move number after Black's turn
			BoardGUI.incMoveNumber();
    	}

		// Change whose turn it is
		BoardGUI.changeTurn();
	}

	/**
	 * Clears all the moves in the pgn.
	 */
	public void clearPGN() {
		movesPGN.setLength(0);
	}
}
