package pieces;

import board.BoardGUI;
import board.Move;

import java.util.List;

public class Pawn extends ChessPiece {

	Pawn(int piecePosition, PieceColor pieceColor) {
		super(piecePosition, pieceColor);
	}

	@Override
	public List<Move> getLegalMoves(BoardGUI board) {
		return null;
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♟';
	}

}
