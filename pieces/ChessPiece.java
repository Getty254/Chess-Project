package pieces;

import board.BoardGUI;
import board.Move;

import java.util.Collection;

public abstract class ChessPiece {

	public abstract char getPieceChar();
	protected final int piecePosition;
    protected final PieceColor pieceColor;
	public abstract Collection<Move> getLegalMoves(final BoardGUI board);
	protected final boolean isFirstMove;

	public PieceColor getPieceColor(){
		return this.pieceColor;
	}

	ChessPiece(final int piecePosition, final PieceColor pieceColor){
		this.piecePosition = piecePosition;
		this.pieceColor = pieceColor;
		this.isFirstMove = false;
	}

	public boolean isFirstMove(){
		return this.isFirstMove;
	}

}
