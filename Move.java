package chess;

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
	
	/**
	 * Creates a new move
	 * 
	 * @param moveLAN String of the move in long algebraic notation
	 * @param rowFrom int row the piece is moving from
	 * @param columnFrom int column the piece is moving from
	 * @param rowTo int row the piece is moving to
	 * @param columnTo int column the piece is moving to
	 */
	public Move(String moveLAN, int rowFrom, 
			int columnFrom, int rowTo, int columnTo) {
		this.moveLAN = moveLAN;
		this.rowFrom = rowFrom;
		this.columnFrom = columnFrom;
		this.rowTo = rowTo;
		this.columnTo = columnTo;
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
}
