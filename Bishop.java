
public class Bishop extends ChessPieces {

	private int pieceColor;
	public Bishop(int color) {
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

	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♝';
	}

}
