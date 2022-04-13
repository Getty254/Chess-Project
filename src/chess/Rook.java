package chess;

import java.util.ArrayList;


/**
 * This class represents a rook and is used to
 * identify all of the moves a rook can make.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class Rook extends ChessPiece {

	/** Color of the piece.*/
	private int pieceColor;
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	/** Indicates if the opponent's king is attacked.*/
	private boolean isAttackingKing = false;
	/** Indicates if the king is allowed to castle.*/
	private boolean canCastle = true;
	
	/**
	 * Creates a rook.
	 * 
	 * @param color int color of the piece
	 * @param r int row the piece is placed on
	 * @param col int column the piece is placed on
	 */
	public Rook(int color, int r, int col) {
		this.pieceColor = color;
		this.row = r;
		this.column = col;
	}
	
	/**
	 * Finds all the moves the rook can make.
	 * 
	 * @param board ChessPiece 2d array
	 * @return ArrayList of Moves of all the
	 * 			 moves the rook can make
	 */
	@Override
	public ArrayList<Move> getMoves(ChessPiece[][] board) {
		isAttackingKing = false;
		ArrayList<Move> moves = new ArrayList<Move>();

		char colFromLetter = (char) (column + 97);
		char colToLetter;
		String moveLAN;

		Move rookMove;

		// North moves
		for(int rowTo = row - 1; rowTo >= 0; rowTo--) {

			colToLetter = (char) (column + 97);
			moveLAN = "R" + colFromLetter + (8-row)
					+ colToLetter + (8-rowTo);

			rookMove = new Move(moveLAN, row, column,
					rowTo, column, PieceType.ROOK);
			// Check if move is possible
			if(checkMove(board, rowTo, column, 
					moves, rookMove) == -1) {
				break;
			}
		}

		// South moves
		for(int rowTo = row + 1; rowTo <= 7; rowTo++) {

			colToLetter = (char) (column + 97);
			moveLAN = "R" + colFromLetter + (8-row)
					+ colToLetter + (8-rowTo);

			rookMove = new Move(moveLAN, row, column,
					rowTo, column, PieceType.ROOK);
			// Check if move is possible
			if(checkMove(board, rowTo, column, 
					moves, rookMove) == -1) {
				break;
			}
		}

		// East moves
		for(int colTo = column + 1; colTo <= 7; colTo++) {

			colToLetter = (char) (colTo + 97);
			moveLAN = "R" + colFromLetter + (8-row)
					+ colToLetter + (8-row);

			rookMove = new Move(moveLAN, row, column,
					row, colTo, PieceType.ROOK);
			// Check if move is possible
			if(checkMove(board, row, colTo, 
					moves, rookMove) == -1) {
				break;
			}
		}

		// West moves
		for(int colTo = column - 1; colTo >= 0; colTo--) {

			colToLetter = (char) (colTo + 97);
			moveLAN = "R" + colFromLetter + (8-row)
					+ colToLetter + (8-row);

			rookMove = new Move(moveLAN, row, column,
					row, colTo, PieceType.ROOK);
			// Check if move is possible
			if(checkMove(board, row, colTo, 
					moves, rookMove) == -1) {
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
	 * Gets if the rook is attacking the opponent's king.
	 *
	 * @return boolean true is the piece is attacking
	 * 			the opponent's king, false if not
	 */
	@Override
	public boolean isAttackingKing() {
		return isAttackingKing;
	}

	/**
	 * Removes the castling rights to the side
	 * the rook was on.
	 */
	public void removeCastleRights() {
		canCastle = false;
	}

	/**
	 * Gets if the king is allow to castle to the
	 * side that rook is on.
	 *
	 * @return boolean true if the rook has never moved,
	 * 					false if otherwise
	 */
	public boolean canCastle() {
		return canCastle;
	}

	/**
	 * Checks if the rook move is possible.
	 *
	 * @param board ChessPiece 2d array of the chess board
	 * @param rowTo int of the row the piece can move to
	 * @param colTo int of the column the piece can move to
	 * @param moves ArrayList of the possible moves
	 * @param rookMove Move containing the move details
	 * @return int 0 if moving to an empty square, -1 if
	 * 			there is already a piece on the square
	 */
	private int checkMove(ChessPiece[][] board, int rowTo,
			int colTo, ArrayList<Move> moves, Move rookMove) {

		// Move to empty square
		if(board[rowTo][colTo] instanceof EmptyPiece) {
			moves.add(rookMove);
			return 0;
		}
		// Capture opponent's piece
		else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
			moves.add(rookMove);
			
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
