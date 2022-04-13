package chess;

import java.util.ArrayList;

/**
 * This class represents a spot on the chess board
 * that does not contain a piece.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class EmptyPiece extends ChessPiece{

	/**
	 * EmptyPiece constructor.
	 */
	public EmptyPiece() {
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
	 * No piece, so it cannot attack a king.
	 *
	 * @return boolean false
	 */
	@Override
	public boolean isAttackingKing() {
		return false;
	}
}
