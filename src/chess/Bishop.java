package chess;

import java.util.ArrayList;


/**
 * This class represents a bishop and is used to
 * identify all of the moves a bishop can make.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class Bishop extends ChessPiece {

	/** Color of the piece.*/
	private int pieceColor;
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	/** Indicates if the opponent's king is attacked.*/
	private boolean isAttackingKing = false;
	
	/**
	 * Creates a bishop.
	 * 
	 * @param color int color of the piece
	 * @param r int row the piece is placed on
	 * @param col int column the piece is placed on
	 */
	public Bishop(int color, int r, int col) {
		this.pieceColor = color;
		this.row = r;
		this.column = col;
	}
	
	/**
	 * Finds all the moves the bishop can make.
	 * 
	 * @param board ChessPiece 2d array
	 * @return ArrayList of Moves of all the
	 * 			 moves the bishop can make
	 */
	@Override
	public ArrayList<Move> getMoves(ChessPiece[][] board) {
		isAttackingKing = false;
		ArrayList<Move> moves = new ArrayList<Move>();
		
		char colFromLetter = (char) (column + 97);
		char colToLetter;
		String moveLAN;
		
		Move bishopMove;
		
		// Northwest moves
		for(int rowTo = row - 1, colTo = column - 1; 
				rowTo >= 0 && colTo >= 0; rowTo--, colTo--) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row)
					+ colToLetter + (8-rowTo);
			
			bishopMove = new Move(moveLAN, row, column,
					rowTo, colTo, PieceType.BISHOP);
			// Check if move is possible
			if(checkMove(board, rowTo, colTo, 
					moves, bishopMove) == -1) {
				break;
			}
		}
		
		// Northeast moves
		for(int rowTo = row - 1, colTo = column + 1; 
				rowTo >= 0 && colTo <= 7; rowTo--, colTo++) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row)
					+ colToLetter + (8-rowTo);
			
			bishopMove = new Move(moveLAN, row, column,
					rowTo, colTo, PieceType.BISHOP);
			// Check if move is possible
			if(checkMove(board, rowTo, colTo, 
					moves, bishopMove) == -1) {
				break;
			}
		}
		
		// Southeast moves
		for(int rowTo = row + 1, colTo = column + 1; 
				rowTo <= 7 && colTo <= 7; rowTo++, colTo++) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row)
					+ colToLetter + (8-rowTo);
			
			bishopMove = new Move(moveLAN, row, column,
					rowTo, colTo, PieceType.BISHOP);
			// Check if move is possible
			if(checkMove(board, rowTo, colTo, 
					moves, bishopMove) == -1) {
				break;
			}
		}
		
		// Southwest moves
		for(int rowTo = row + 1, colTo = column - 1; 
				rowTo <= 7 && colTo >= 0; rowTo++, colTo--) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row)
					+ colToLetter + (8-rowTo);
			
			bishopMove = new Move(moveLAN, row, column,
					rowTo, colTo, PieceType.BISHOP);
			// Check if move is possible
			if(checkMove(board, rowTo, colTo, 
					moves, bishopMove) == -1) {
				break;
			}
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
	 * Gets if the bishop is attacking the opponent's king.
	 * 
	 * @return boolean true is the piece is attacking
	 * 			the opponent's king, false if not
	 */
	@Override
	public boolean isAttackingKing() {
		return isAttackingKing;
	}
	
	/**
	 * Checks if the bishop move is possible.
	 *
	 * @param board ChessPiece 2d array of the chess board
	 * @param rowTo int of the row the piece can move to
	 * @param colTo int of the column the piece can move to
	 * @param moves ArrayList of the possible moves
	 * @param bishopMove Move containing the move details
	 * @return int 0 if moving to an empty square, -1 if
	 * 			there is already a piece on the square
	 */
	private int checkMove(ChessPiece[][] board, int rowTo,
			int colTo, ArrayList<Move> moves, Move bishopMove) {
	
		// Move to empty square
		if(board[rowTo][colTo] instanceof EmptyPiece) {
			moves.add(bishopMove);
			return 0;
		}
		// Capture opponent's piece
		else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
			moves.add(bishopMove);
			
			if(board[rowTo][colTo] instanceof King) {
				isAttackingKing = true;
			}
			return -1;
		}
		// Same color piece, so cannot go to the square
		else {
			return -1;
		}
	}
}
