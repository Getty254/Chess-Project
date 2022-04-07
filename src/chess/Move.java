package chess;

/**
 * This class represents and contains
 * all of the details about a particular move.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class Move {

	/** Move in long algebraic notation.*/
	private String moveLAN;
	/** Row the piece is moving from.*/
	private int rowFrom;
	/** Column the piece is moving from.*/
	private int columnFrom;
	/** Row the piece is moving to.*/
	private int rowTo;
	/** Column the piece is moving to.*/
	private int columnTo;
	/** Indicates if the move is en passant.*/
	private boolean enPassant = false;
	/** Indicates if the move is a pawn promotion.*/
	private boolean pawnPromo = false;
	/** Type of piece that was moved.*/
	private PieceType piece;
	
	/**
	 * Creates a new move
	 * 
	 * @param moveLAN String of the move in long algebraic notation
	 * @param rowFrom int row the piece is moving from
	 * @param columnFrom int column the piece is moving from
	 * @param rowTo int row the piece is moving to
	 * @param columnTo int column the piece is moving to
	 * @param piece PieceType for what piece was moved
	 */
	public Move(String moveLAN, int rowFrom, 
			int columnFrom, int rowTo, int columnTo, PieceType piece) {
		this.moveLAN = moveLAN;
		this.rowFrom = rowFrom;
		this.columnFrom = columnFrom;
		this.rowTo = rowTo;
		this.columnTo = columnTo;
		this.piece = piece;
	}
	
	/**
	 * Gets the move in long algebraic notation.
	 * 
	 * @return String of the move in long algebraic notation
	 */
	public String getMoveLAN() {
		return moveLAN;
	}
	
	/**
	 * Gets the row the piece moved from.
	 * 
	 * @return int row the piece moved from
	 */
	public int getRowFrom() {
		return rowFrom;
	}
	
	/**
	 * Gets the column the piece moved from.
	 * 
	 * @return int column the piece moved from
	 */
	public int getColumnFrom() {
		return columnFrom;
	}
	
	/**
	 * Gets the row the piece moved to.
	 * 
	 * @return int row the piece moved to
	 */
	public int getRowTo() {
		return rowTo;
	}
	
	/**
	 * Gets the column the piece moved to.
	 * 
	 * @return int column the piece moved to
	 */
	public int getColumnTo() {
		return columnTo;
	}
	
	/**
	 * Gets the type of piece that was moved.
	 * 
	 * @return PieceType for what piece was moved
	 */
	public PieceType getPieceMoved() {
		return piece;
	}
	
	/**
	 * Sets the en passant flag to true.
	 */
	public void setEnPassant() {
		enPassant = true;
	}
	
	/**
	 * Gets if the move was en passant.
	 * 
	 * @return boolean true if the move was en passant,
	 * 					false if otherwise
	 */
	public boolean isEnPassant() {
		return enPassant;
	}
	
	/**
	 * Sets the pawn promotion flag to true.
	 */
	public void setPawnPromo() {
		pawnPromo = true;
	}
	
	/**
	 * Gets if the move was a pawn promotion.
	 * 
	 * @return boolean true if the move was a pawn promotion,
	 * 					false if otherwise
	 */
	public boolean isPawnPromo() {
		return pawnPromo;
	}
}
