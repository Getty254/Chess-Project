package chess;

import java.util.ArrayList;

/**
 * This class represents a king and is used to
 * identify all of the moves a king can make.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class King extends ChessPiece {

	/** Color of the piece.*/
	private int pieceColor;
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	/** Indicates if the opponent's king is attacked.*/
	private boolean isAttackingKing = false;
	/** Indicates if the king is allowed to castle.*/
	private boolean canCastle;


	/**
	 * Creates a king.
	 * 
	 * @param color int color of the piece
	 * @param r int row the piece is placed on
	 * @param col int column the piece is placed on
	 * @param castle boolean true if the king is
	 * 				allowed to castle, false if not
	 */
	public King(int color, int r, int col, boolean castle) {
		this.pieceColor = color;
		this.row = r;
		this.column = col;
		this.canCastle = castle;
	}
	
	/**
	 * Finds all the moves the king can make.
	 * 
	 * @param board ChessPiece 2d array
	 * @return ArrayList of Moves of all the
	 * 			 moves the king can make
	 */
	@Override
	public ArrayList<Move> getMoves(ChessPiece[][] board) {
		isAttackingKing = false;
		ArrayList<Move> moves = new ArrayList<Move>();
		
		// North, Northeast, Northwest moves
		if(row - 1 >= 0) {
			// North
			moves.addAll(checkMove(board, row-1, column));
			
			// Northeast
			if(column + 1 <= 7) {
				moves.addAll(checkMove(board, row-1, column+1));
			}
			
			// Northwest
			if(column - 1 >= 0) {
				moves.addAll(checkMove(board, row-1, column-1));
			}
		}
		
		//South, Southeast, Southwest moves
		if(row + 1 <= 7) {
			moves.addAll(checkMove(board, row+1, column));
			
			// Southeast
			if(column + 1 <= 7) {
				moves.addAll(checkMove(board, row+1, column+1));
			}
			
			// Southwest
			if(column - 1 >= 0) {
				moves.addAll(checkMove(board, row+1, column-1));
			}
			
		}
		
		// East move
		if(column + 1 <= 7) {
			moves.addAll(checkMove(board, row, column+1));
		}
		
		// West move
		if(column - 1 >= 0) {
			moves.addAll(checkMove(board, row, column-1));
		}

		// Castling
		if(canCastle) {
			// White's move
			if(this.pieceColor == 0 && !GameLogic.isInCheckP1) {
				// Short Castle
				if(row == 7 && column == 4 
					&& board[7][5] instanceof EmptyPiece
					&& board[7][6] instanceof EmptyPiece
					&& board[7][7] instanceof Rook
					&& board[7][7].getPieceColor() == this.pieceColor) {

					Rook rk = (Rook) board[7][7];
					// if the rook has never moved, the king can castle
					if(rk.canCastle()) {
						moves.add(new Move("O-O", row, column, row, 6, PieceType.KING));
					}
				}
				
				// Long Castle
				if(row == 7 && column == 4 
					&& board[7][3] instanceof EmptyPiece
					&& board[7][2] instanceof EmptyPiece
					&& board[7][1] instanceof EmptyPiece
					&& board[7][0] instanceof Rook
					&& board[7][0].getPieceColor() == this.pieceColor) {

					Rook rk = (Rook) board[7][0];
					// if the rook has never moved, the king can castle
					if(rk.canCastle()) {
						moves.add(new Move("O-O-O", row, column, row, 2, PieceType.KING));
					}
				}
			}
			// Black's move
			else if(this.pieceColor == 1 && !GameLogic.isInCheckP2){
				// Short Castle
				if(row == 0 && column == 4 
					&& board[0][5] instanceof EmptyPiece
					&& board[0][6] instanceof EmptyPiece
					&& board[0][7] instanceof Rook
					&& board[0][7].getPieceColor() == this.pieceColor) {
					
					Rook rk = (Rook) board[0][7];
					// if the rook has never moved, the king can castle
					if(rk.canCastle()) {
						moves.add(new Move("O-O", row, column, row, 6, PieceType.KING));
					}
				}
				
				// Long Castle
				if(row == 0 && column == 4 
					&& board[0][3] instanceof EmptyPiece
					&& board[0][2] instanceof EmptyPiece
					&& board[0][1] instanceof EmptyPiece
					&& board[0][0] instanceof Rook
					&& board[0][0].getPieceColor() == this.pieceColor) {
					
					Rook rk = (Rook) board[0][0];
					// if the rook has never moved, the king can castle
					if(rk.canCastle()) {
						moves.add(new Move("O-O-O", row, column, row, 2, PieceType.KING));
					}
				}
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
	 * Gets if the king is attacking the opponent's king.
	 * 
	 * @return boolean true is the piece is attacking
	 * 			the opponent's king, false if not
	 */
	@Override
	public boolean isAttackingKing() {
		return isAttackingKing;
	}

	/**
	 * Checks if the king move is possible.
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
		String moveLAN = "K" + colFromLetter + (8-row)
						+ colToLetter + (8-rowTo);
		
		Move kingMove = new Move(moveLAN, row, column,
				rowTo, colTo, PieceType.KING);
		
		// Move to empty square
		if(board[rowTo][colTo] instanceof EmptyPiece) {
			moves.add(kingMove);
		}
		// Capture opponent's piece
		else if(board[rowTo][colTo].getPieceColor() != this.pieceColor) {
			moves.add(kingMove);
			
			if(board[rowTo][colTo] instanceof King) {
				isAttackingKing = true;
			}
		}
		
		return moves;
	}

}
