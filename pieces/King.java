package pieces;

import board.BoardGUI;
import board.Move;

import java.util.List;

public class King extends ChessPiece {

	private final static int{} POSSIBLE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17};
	public King(int piecePosition, PieceColor pieceColor) {
		super(piecePosition, pieceColor);
	}

	@Override
	public List<Move> getLegalMoves(BoardGUI board) {
		return null;
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♚';
	}

}
