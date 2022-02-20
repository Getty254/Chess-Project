
public class Knight extends ChessPieces {

	private int pieceColor;
	public Knight(int color) {
		this.pieceColor = color;
	}
	
	@Override
	public void getLegalMoves() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPieceColor() {
		// TODO Auto-generated method stub
		return pieceColor;
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♞';
	}

}
