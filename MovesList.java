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
 * @version 1.0
 */
public class MovesList extends GridPane {
	private StringBuilder movesPGN = new StringBuilder();
	public static ArrayList<Move> movesAL = new ArrayList<Move>();
	
	public MovesList() {
		// Add boundaries to the columns of the
		// moves list GridPane
		this.getColumnConstraints().add(
				new ColumnConstraints(30));
		this.getColumnConstraints().add(
				new ColumnConstraints(75));
		this.getColumnConstraints().add(
				new ColumnConstraints(75));
	}
	
	
	/**
	 * Adds the player's move to the list of moves on the right
	 * side of the screen.
	 *
	 * @param move String containing the player's move in
	 * 				Long Algebraic Notation
	 */
	public void updateMovesList(String move) {
		if(BoardGUI.turn == 0) {
        	// Add move number to move list
    		Label moveNumLabel = new Label(BoardGUI.moveNumber + ".");
    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
    		this.add(moveNumLabel, 0, BoardGUI.moveNumber);
    		
    		// Add white move to move list
    		Label whiteMove = new Label(move);
			GridPane.setHalignment(whiteMove, HPos.CENTER);
			this.add(whiteMove, 1, BoardGUI.moveNumber);
			
			BoardGUI.triggerMoveNum.setText("moved");
			movesPGN.append(BoardGUI.moveNumber + ". " + move);
    	}
    	else {
    		// Add black move to move list
    		Label blackMove = new Label(move);
			GridPane.setHalignment(blackMove, HPos.CENTER);
			this.add(blackMove, 2, BoardGUI.moveNumber);
			
			movesPGN.append(" " + move + " ");

			// Only increment move number after Black's turn
			BoardGUI.moveNumber++;
    	}
    	
		// Change whose turn it is
		BoardGUI.turn = (BoardGUI.turn == 0) ? 1 : 0;
	}
	
	/**
	 * Gets the Portable Game Notation (PGN).
	 * 
	 * @return String of all the moves of the game
	 */
	public String getPGN() {
		return movesPGN.toString();
	}
	
	/**
	 * Clears all the moves in the pgn
	 */
	public void clearPGN() {
		movesPGN.setLength(0);
	}
}
