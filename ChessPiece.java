package chess;

import java.util.ArrayList;

public abstract class ChessPiece {
	/** Unicode character for a pawn.*/
	public static String PAWN = "♟";
	/** Unicode character for a knight.*/
	public static String KNIGHT = "♞";
	/** Unicode character for a bishop.*/
	public static String BISHOP = "♝";
	/** Unicode character for a rook.*/
	public static String ROOK = "♜";
	/** Unicode character for a queen.*/
	public static String QUEEN = "♛";
	/** Unicode character for a king.*/
	public static String KING = "♚";
	
	public abstract ArrayList<Move> getMoves(ChessPiece[][] board);
	public abstract int getPieceColor();
	public abstract char getPieceChar();
	public abstract boolean isAttackingKing();
	
}
