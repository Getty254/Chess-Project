package chess;

import java.util.ArrayList;


/**
 * This is the parent class of each chess piece class.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public abstract class ChessPiece {
	/** Unicode character for a pawn.*/
	public static final String PAWN = "♟";
	/** Unicode character for a knight.*/
	public static final String KNIGHT = "♞";
	/** Unicode character for a bishop.*/
	public static final String BISHOP = "♝";
	/** Unicode character for a rook.*/
	public static final String ROOK = "♜";
	/** Unicode character for a queen.*/
	public static final String QUEEN = "♛";
	/** Unicode character for a king.*/
	public static final String KING = "♚";


	/**
	 * Finds all the moves a piece can make.
	 * 
	 * @param board ChessPiece 2d array
	 * @return ArrayList of Moves of all the
	 * 			 moves a pice can make
	 */
	public abstract ArrayList<Move> getMoves(ChessPiece[][] board);

	/**
	 * Gets the color of the piece.
	 * 0 for white. 1 for black.
	 * 
	 * @return int color of the piece
	 */
	public abstract int getPieceColor();
	
	/**
	 * Gets if a piece is attacking the opponent's king.
	 * 
	 * @return boolean true is a piece is attacking
	 * 			the opponent's king, false if not
	 */
	public abstract boolean isAttackingKing();
	
}
