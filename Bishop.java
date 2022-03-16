package chess;

import java.util.ArrayList;

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
	 * @param row int row the piece is placed on
	 * @param column int column the piece is placed on
	 */
	public Bishop(int color, int row, int column) {
		this.pieceColor = color;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Finds all the moves the bishop can make.
	 * 
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
		
		// Northwest moves
		for(int rowTo = row - 1, colTo = column - 1; 
				rowTo >= 0 && colTo >= 0; rowTo--, colTo--) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
			
			// Move to empty square
			if(board[rowTo][colTo] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
			}
			// Capture opponent's piece
			else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
				
				if(board[rowTo][colTo] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
				break;
			}
		}
		
		// Northeast moves
		for(int rowTo = row - 1, colTo = column + 1; 
				rowTo >= 0 && colTo <= 7; rowTo--, colTo++) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
			
			// Move to empty square
			if(board[rowTo][colTo] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
			}
			// Capture opponent's piece
			else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
				
				if(board[rowTo][colTo] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
				break;
			}
		}
		
		// Southeast moves
		for(int rowTo = row + 1, colTo = column + 1; 
				rowTo <= 7 && colTo <= 7; rowTo++, colTo++) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
			
			// Move to empty square
			if(board[rowTo][colTo] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
			}
			// Capture opponent's piece
			else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
				
				if(board[rowTo][colTo] instanceof King) {
					isAttackingKing = true;
				}
				break;
			}
			// Same color piece, so cannot go to the square
			else {
				break;
			}
		}
		
		// Southwest moves
		for(int rowTo = row + 1, colTo = column - 1; 
				rowTo <= 7 && colTo >= 0; rowTo++, colTo--) {
			
			colToLetter = (char) (colTo + 97);
			moveLAN = "B" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
			
			// Move to empty square
			if(board[rowTo][colTo] instanceof EmptyPiece) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
			}
			// Capture opponent's piece
			else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
				moves.add(new Move(moveLAN, row, column, rowTo, colTo));
				
				if(board[rowTo][colTo] instanceof King) {
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
		return ChessPiece.BISHOP.charAt(0);
	}

}
