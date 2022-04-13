package chess;

import javafx.scene.image.Image;

/**
 * This class is used to get images of
 * every type of chess piece.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public final class PieceIcon {
	/** Height of the image.*/
	private final double imgHEIGHT = 80;
	/** Width of the image.*/
	private final double imgWIDTH = 80;

	/**
	 * Gets an image of a white pawn.
	 * @return Image of a white pawn
	 */
	public Image getWP() {
		return new Image("PieceImages/WhitePawn.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a black pawn.
	 * @return Image of a black pawn
	 */
	public Image getBP() {
		return new Image("PieceImages/BlackPawn.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a white bishop.
	 * @return Image of a white bishop
	 */
	public Image getWB() {
		return new Image("PieceImages/WhiteBishop.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a black bishop.
	 * @return Image of a black bishop
	 */
	public Image getBB() {
		return new Image("PieceImages/BlackBishop.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a white knight.
	 * @return Image of a white knight
	 */
	public Image getWN() {
		return new Image("PieceImages/WhiteKnight.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a black knight.
	 * @return Image of a black knight
	 */
	public Image getBN() {
		return new Image("PieceImages/BlackKnight.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a white rook.
	 * @return Image of a white rook
	 */
	public Image getWR() {
		return new Image("PieceImages/WhiteRook.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a black rook.
	 * @return Image of a black rook
	 */
	public Image getBR() {
		return new Image("PieceImages/BlackRook.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a white queen.
	 * @return Image of a white queen
	 */
	public Image getWQ() {
		return new Image("PieceImages/WhiteQueen.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a black queen.
	 * @return Image of a black queen
	 */
	public Image getBQ() {
		return new Image("PieceImages/BlackQueen.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a white king.
	 * @return Image of a white king
	 */
	public Image getWK() {
		return new Image("PieceImages/WhiteKing.png",
				imgWIDTH, imgHEIGHT, true, true);
	}

	/**
	 * Gets an image of a black king.
	 * @return Image of a black king
	 */
	public Image getBK() {
		return new Image("PieceImages/BlackKing.png",
				imgWIDTH, imgHEIGHT, true, true);
	}
}
