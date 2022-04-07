package chess;

import java.util.ArrayList;


/**
 * This class represents a knight and is used to
 * identify all of the moves a knight can make.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class Knight extends ChessPiece {

	/** Color of the piece.*/
	private int pieceColor;
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	/** Indicates if the opponent's king is attacked.*/
	private boolean isAttackingKing = false;

	/**
	 * Creates a knight.
	 * 
	 * @param color int color of the piece
	 * @param r int row the piece is placed on
	 * @param col int column the piece is placed on
	 */
	public Knight(int color, int r, int col) {
		this.pieceColor = color;
		this.row = r;
		this.column = col;
	}
	
	/**
	 * Finds all the moves the knight can make.
	 * 
	 * @param board ChessPiece 2d array
	 * @return ArrayList of Moves of all the
	 * 			 moves the knight can make
	 */
	@Override
	public ArrayList<Move> getMoves(ChessPiece[][] board) {
		isAttackingKing = false;
		ArrayList<Move> moves = new ArrayList<Move>();
		
		// Northwest, 2 up, 1 left
		if(row - 2 >= 0 && column - 1 >= 0) {
			moves.addAll(checkMove(board, row-2, column-1));
		}
				
		// Northeast, 2 up, 1 right
		if(row - 2 >= 0 && column + 1 <= 7) {
			moves.addAll(checkMove(board, row-2, column+1));
		}
		
		// Eastnorth, 2 right, 1 up
		if(row - 1 >= 0 && column + 2 <= 7) {
			moves.addAll(checkMove(board, row-1, column+2));
		}
		
		// Eastsouth, 2 right, 1 down
		if(row + 1 <= 7 && column + 2 <= 7) {
			moves.addAll(checkMove(board, row+1, column+2));
		}
		
		// Southeast, 2 down, 1 right
		if(row + 2 <= 7 && column + 1 <= 7) {
			moves.addAll(checkMove(board, row+2, column+1));
		}
		
		// Southwest, 2 down, 1 left
		if(row + 2 <= 7 && column - 1 >= 0) {
			moves.addAll(checkMove(board, row+2, column-1));
		}
		
		// Westsouth, 2 left, 1 down
		if(row + 1 <= 7 && column - 2 >= 0) {
			moves.addAll(checkMove(board, row+1, column-2));
		}
		
		// Westnorth, 2 left, 1 up
		if(row - 1 >= 0 && column - 2 >= 0) {
			moves.addAll(checkMove(board, row-1, column-2));
		}
		
		
		return moves;
	}

	/**
	 * Gets the color of the piece.
	 * 0 for white. 1 for black.
	 * 
	 * @return int color of the piece
	 */
	@Override
	public int getPieceColor() {
		return pieceColor;
	}

	/**
	 * Gets if the knight is attacking the opponent's king.
	 * 
	 * @return boolean true is the piece is attacking
	 * 			the opponent's king, false if not
	 */
	@Override
	public boolean isAttackingKing() {
		return isAttackingKing;
	}

	/**
	 * Checks if the knight move is possible.
	 * 
	 * @param board ChessPiece 2d array of the chess board
	 * @param rowTo int of the row the piece can move to
	 * @param colTo int of the column the piece can move to
	 * @return ArrayList of the possible moves
	 */
	private ArrayList<Move> checkMove(ChessPiece[][] board, int rowTo, int colTo) {

		ArrayList<Move> moves = new ArrayList<Move>();
		
		char colFromLetter = (char) (column + 97);
		char colToLetter = (char) (colTo + 97);
		String moveLAN = "N" + colFromLetter + (8-row)
						+ colToLetter + (8-rowTo);
		
		Move knightMove = new Move(moveLAN, row, column,
				rowTo, colTo, PieceType.KNIGHT);
		
		// Move to empty square
		if(board[rowTo][colTo] instanceof EmptyPiece) {
			moves.add(knightMove);
		}
		// Capture opponent's piece
		else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
			moves.add(knightMove);
			
			if(board[rowTo][colTo] instanceof King) {
				isAttackingKing = true;
			}
		}
		
		return moves;
	}
}
