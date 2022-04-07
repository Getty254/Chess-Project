package chess;

import java.util.ArrayList;


/**
 * This class represents a rook and is used to
 * identify all of the moves a rook can make.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
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
			
			// Move to empty square
			if(board[rowTo][column] instanceof EmptyPiece) {
				moves.add(rookMove);
			}
			// Capture opponent's piece
			else if(board[rowTo][column].getPieceColor() != this.pieceColor) {
				moves.add(rookMove);
				
				if(board[rowTo][column] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
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
			
			// Move to empty square
			if(board[rowTo][column] instanceof EmptyPiece) {
				moves.add(rookMove);
			}
			// Capture opponent's piece
			else if(board[rowTo][column].getPieceColor() != this.pieceColor) {
				moves.add(rookMove);
				
				if(board[rowTo][column] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
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
			
			// Move to empty square
			if(board[row][colTo] instanceof EmptyPiece) {
				moves.add(rookMove);
			}
			// Capture opponent's piece
			else if(board[row][colTo].getPieceColor() != this.pieceColor) {
				moves.add(rookMove);
				
				if(board[row][colTo] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
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
			
			// Move to empty square
			if(board[row][colTo] instanceof EmptyPiece) {
				moves.add(rookMove);
			}
			// Capture opponent's piece
			else if(board[row][colTo].getPieceColor() != this.pieceColor) {
				moves.add(rookMove);
				
				if(board[row][colTo] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
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
}
