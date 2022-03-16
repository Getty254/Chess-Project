package chess;

import java.util.ArrayList;

public class Rook extends ChessPiece {

	/** Color of the piece.*/
	private int pieceColor;
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	/** Indicates if the opponent's king is attacked.*/
	private boolean isAttackingKing = false;
	
	/**
	 * Creates a rook.
	 * 
	 * @param color int color of the piece
	 * @param row int row the piece is placed on
	 * @param column int column the piece is placed on
	 */
	public Rook(int color, int row, int column) {
		this.pieceColor = color;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Finds all the moves the rook can make.
	 * 
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
		
		// North moves
		for(int rowTo = row - 1; rowTo >= 0; rowTo--) {
			
			colToLetter = (char) (column + 97);
			moveLAN = "R" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
			
			// Move to empty square
			if(board[rowTo][column] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, rowTo, column));
			}
			// Capture opponent's piece
			else if(board[rowTo][column].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, rowTo, column));
				
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
			moveLAN = "R" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
			
			// Move to empty square
			if(board[rowTo][column] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, rowTo, column));
			}
			// Capture opponent's piece
			else if(board[rowTo][column].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, rowTo, column));
				
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
			moveLAN = "R" + colFromLetter + (8-row) + colToLetter + (8-row);
			
			// Move to empty square
			if(board[row][colTo] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, row, colTo));
			}
			// Capture opponent's piece
			else if(board[row][colTo].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, row, colTo));
				
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
			moveLAN = "R" + colFromLetter + (8-row) + colToLetter + (8-row);
			
			// Move to empty square
			if(board[row][colTo] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, row, colTo));
			}
			// Capture opponent's piece
			else if(board[row][colTo].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, row, colTo));
				
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
	 * @return int color of the piece
	 */
	@Override
	public int getPieceColor() {
		return pieceColor;
	}

	/**
	 * @return boolean true is the piece is attacking
	 * 			the opponent's king, false if not
	 */
	@Override
	public boolean isAttackingKing() {
		return isAttackingKing;
	}
	
	@Override
	public char getPieceChar() {
		return ChessPiece.ROOK.charAt(0);
	}
}
