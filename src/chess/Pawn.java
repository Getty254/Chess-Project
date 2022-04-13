package chess;

import java.util.ArrayList;


/**
 * This class represents a pawn and is used to
 * identify all of the moves a pawn can make.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class Pawn extends ChessPiece {

	/** Color of the piece.*/
	private int pieceColor;
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	/** Indicates if the opponent's king is attacked.*/
	private boolean isAttackingKing = false;
	/** Indicates if the pawn is still on its original square.*/
	private boolean isOriginalSquare = true;
	/** Array of letters of the pieces that the pawn can promote to.*/
	private String[] promotionLetters = 
			new String[] { "Q", "R", "B", "N" };
	
	/**
	 * Creates a pawn.
	 * 
	 * @param color int color of the piece
	 * @param r int row the piece is placed on
	 * @param col int column the piece is placed on
	 */
	public Pawn(int color, int r, int col) {
		this.pieceColor = color;
		this.row = r;
		this.column = col;
		
		// White pawn is not on the 2nd row
		if(color == 0 && row != 6) {
			isOriginalSquare = false;
		}
		// Black pawn is not on the 7th row
		else if(color == 1 && row != 1) {
			isOriginalSquare = false;
		}
	}
	
	/**
	 * Finds all the moves the pawn can make.
	 * 
	 * @param board ChessPiece 2d array
	 * @return ArrayList of Moves of all the
	 * 			 moves the pawn can make
	 */
	@Override
	public ArrayList<Move> getMoves(ChessPiece[][] board) {
		isAttackingKing = false;
		ArrayList<Move> moves = new ArrayList<Move>();
		
		
		// White Pawn Moves
		if(pieceColor == 0) {
			// North 1 square
			moves.addAll(checkMove(board, row-1, column, false, false));
			
			// Pawn has not been move yet
			if(isOriginalSquare
					&& board[row-1][column] instanceof EmptyPiece) {
				// North 2 squares
				moves.addAll(checkMove(board, row-2, column, false, false));
			}
			
			// Northwest capture
			if(row - 1 >= 0 && column - 1 >= 0
					&& board[row-1][column-1].getPieceColor() != this.pieceColor
					&& !(board[row-1][column-1] instanceof EmptyPiece)) {
				moves.addAll(checkMove(board, row-1, column-1, true, false));
				
				if(board[row-1][column-1] instanceof King) {
					isAttackingKing = true;
				}
			}
			// Northeast capture
			if(row - 1 >= 0 && column + 1 <= 7
					&& board[row-1][column+1].getPieceColor() != this.pieceColor
					&& !(board[row-1][column+1] instanceof EmptyPiece)) {
				moves.addAll(checkMove(board, row-1, column+1, true, false));
				
				if(board[row-1][column+1] instanceof King) {
					isAttackingKing = true;
				}
			}
		}
		// Black Pawn Moves
		else {
			// South 1 square
			moves.addAll(checkMove(board, row+1, column, false, false));
			
			// Pawn has not been move yet
			if(isOriginalSquare
					&& board[row+1][column] instanceof EmptyPiece) {
				// South 2 squares
				moves.addAll(checkMove(board, row+2, column, false, false));
			}
			
			// Southwest capture
			if(row + 1 <= 7 && column - 1 >= 0
					&& board[row+1][column-1].getPieceColor() != this.pieceColor
					&& !(board[row+1][column-1] instanceof EmptyPiece)) {
				moves.addAll(checkMove(board, row+1, column-1, true, false));
				
				if(board[row+1][column-1] instanceof King) {
					isAttackingKing = true;
				}
			}
			// Southeast capture
			if(row + 1 <= 7 && column + 1 <= 7
					&& board[row+1][column+1].getPieceColor() != this.pieceColor
					&& !(board[row+1][column+1] instanceof EmptyPiece)) {
				moves.addAll(checkMove(board, row+1, column+1, true, false));
				
				if(board[row+1][column+1] instanceof King) {
					isAttackingKing = true;
				}
			}
		}
		
		if(!MovesList.getMovesAl().isEmpty()) {
			Move prevMove = MovesList.getMovesAl()
					.get(MovesList.getMovesAl().size()-1);
			
			// Previous move was a pawn move
			if(prevMove.getPieceMoved() == PieceType.PAWN) {
				// White Pawn moved 2 spaces
				if(prevMove.getRowFrom() == 6
					&& prevMove.getRowTo() == 4
					&& row == prevMove.getRowTo()) {
					if(prevMove.getColumnTo() - 1 == column) {
						moves.addAll(checkMove(board, row+1, column+1, true, true));
					}
					else if(prevMove.getColumnTo() + 1 == column) {
						moves.addAll(checkMove(board, row+1, column-1, true, true));
					}
				}
				// Black Pawn moved 2 spaces
				else if(prevMove.getRowFrom() == 1
					&& prevMove.getRowTo() == 3
					&& row == prevMove.getRowTo()) {
					if(prevMove.getColumnTo() - 1 == column) {
						moves.addAll(checkMove(board, row-1, column+1, true, true));
					}
					else if(prevMove.getColumnTo() + 1 == column) {
						moves.addAll(checkMove(board, row-1, column-1, true, true));
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
	 * Gets if the pawn is attacking the opponent's king.
	 * 
	 * @return boolean true is the piece is attacking
	 * 			the opponent's king, false if not
	 */
	@Override
	public boolean isAttackingKing() {
		return isAttackingKing;
	}

	/**
	 * Checks if the pawn move is possible.
	 * 
	 * @param board ChessPiece 2d array of the chess board
	 * @param rowTo int of the row the piece can move to
	 * @param colTo int of the column the piece can move to
	 * @param isCapture boolean true is the move is a capture
	 * @param isEnPassant boolean true is the move en passant
	 * @return ArrayList of the possible moves
	 */
	private ArrayList<Move> checkMove(ChessPiece[][] board,
			int rowTo, int colTo, boolean isCapture, boolean isEnPassant) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		char colFromLetter = (char) (column + 97);
		char colToLetter = (char) (colTo + 97);
		String moveLAN = "" + colFromLetter + (8-row)
						+ colToLetter + (8-rowTo);
		
		Move pawnMove = new Move(moveLAN, row, column,
				rowTo, colTo, PieceType.PAWN);
		
		// Pawn Promotion
		if((pieceColor == 0 && rowTo == 0) 
				|| (pieceColor == 1 && rowTo == 7)) {
			for(String letter: promotionLetters) {
				// Add the promotion piece to the end of the move
				moveLAN = "" + colFromLetter + (8-row)
						+ colToLetter + (8-rowTo) + letter;
				pawnMove = new Move(moveLAN, row, column,
						rowTo, colTo, PieceType.PAWN);
				pawnMove.setPawnPromo();
				
				// Capture or Move to empty square
				if(isCapture 
					|| board[rowTo][colTo] instanceof EmptyPiece) {
					moves.add(pawnMove);
				}
			}
		}
		else {
			if(isCapture) {
				moves.add(pawnMove);
				
				if(board[rowTo][colTo] instanceof King) {
					isAttackingKing = true;
				}
			}
			// Move to empty square
			else if(board[rowTo][colTo] instanceof EmptyPiece) {
				moves.add(pawnMove);
			}
		}
		
		if(isEnPassant) {
			moves.get(0).setEnPassant();
		}
		
		return moves;
	}

}
