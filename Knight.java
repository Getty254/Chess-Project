package chess;

import java.util.ArrayList;

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
	 * @param row int row the piece is placed on
	 * @param column int column the piece is placed on
	 */
	public Knight(int color, int row, int column) {
		this.pieceColor = color;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Finds all the moves the knight can make.
	 * 
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
		return ChessPiece.KNIGHT.charAt(0);
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
		String moveLAN = "N" + colFromLetter + (8-row) + colToLetter + (8-rowTo);
		
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
		}
		
		return moves;
	}
}
