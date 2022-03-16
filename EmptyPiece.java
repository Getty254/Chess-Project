package chess;

import java.util.ArrayList;

public class EmptyPiece extends ChessPiece{
	
	/** Row the piece is placed on.*/
	private int row;
	/** Column the piece is placed on.*/
	private int column;
	
	public EmptyPiece(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * No moves can be made by an empty piece.
	 * 
	 * @return ArrayList null, this is not an
	 * 			actual piece
	 */
	@Override
	public ArrayList<Move> getMoves(ChessPiece[][] board) {
		return null;
	}

	/**
	 * No piece, so color is neither white nor black.
	 * 
	 * @return int 2
	 */
	@Override
	public int getPieceColor() {
		return 2;
	}

	/**
	 * @return boolean false
	 */
	@Override
	public boolean isAttackingKing() {
		return false;
	}
	

	@Override
	public char getPieceChar() {
		return ' ';
	}
}
