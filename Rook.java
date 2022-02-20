
public class Rook extends ChessPieces {

	private int pieceColor;
	public Rook(int color) {
		this.pieceColor = color;
	}
	
	@Override
	public void getLegalMoves() {
		// TODO Auto-generated method stub
		
		// Check north
		// Check south
		// Check east
		// Check west
		
	}

	@Override
	public int getPieceColor() {
		// TODO Auto-generated method stub
		return pieceColor;
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♜';
	}

}
