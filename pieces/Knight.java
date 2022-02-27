package pieces;

import board.BoardGUI;
import board.BoardUtils;
import board.Move;
import board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Knight extends ChessPiece {

	private final static int[] POSSIBLE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17};

	public Knight(final int piecePosition, final PieceColor pieceColor) {
		super(piecePosition, pieceColor);
	}

	/*Gets all possible moves of a chess piece */
	@Override
	public Collection<Move> getLegalMoves(final BoardGUI board) {
		final List<Move> legalMoves = new ArrayList<>();

		for(final int currentPossibleMove : POSSIBLE_MOVE_COORDINATES){
			int possibleDestinationCoordinate;
            possibleDestinationCoordinate = this.piecePosition + currentPossibleMove;
			/* edge cases for knight on the 1st, 2nd, 7th and 8th column */
		   if(BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
			   if(isFirstColumnExclusion(this.piecePosition, currentPossibleMove) ||
					   isSecondColumnExclusion(this.piecePosition, currentPossibleMove) ||
					   isSeventhColumnExclusion(this.piecePosition, currentPossibleMove) ||
					   isEighthExclusion(this.piecePosition, currentPossibleMove)){
				   continue;
			   }
			   final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);
			   if(!possibleDestinationTile.isTileOccupied()){
				   /* Store all possible moves*/
                   legalMoves.add(new Move.BasicMove(board, this,possibleDestinationCoordinate ));
			   }else{
				   final ChessPiece pieceAtDestination = possibleDestinationTile.getPiece();
				   final PieceColor pieceColor = pieceAtDestination.getPieceColor();

				   if(this.pieceColor != pieceColor){
					   legalMoves.add(new Move.AttackMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
				   }
			   }
		   }
		}

		return legalMoves;
	}

	/* applies if piece is on the edge of the board */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int possibleOffSet){

		return BoardUtils.FIRST_COLUMN[currentPosition] && ((possibleOffSet == -17) || (possibleOffSet == -10)
				 ||  (possibleOffSet == 6) || (possibleOffSet == 15) );
	}

	private static boolean isSecondColumnExclusion(final int currentPosition, final int possibleOffSet){
		return BoardUtils.SECOND_COLUMN[currentPosition] && (possibleOffSet == 6) || (possibleOffSet == -10);
	}

	private static boolean isSeventhColumnExclusion(final int currentPosition, final int possibleOffSet){
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (possibleOffSet == -6) || (possibleOffSet == 10);
	}

	private static boolean isEighthExclusion(final int currentPosition, final int possibleOffSet){
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (possibleOffSet == -15 || possibleOffSet == -6 || possibleOffSet == 10 || possibleOffSet == 17);
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♞';
	}

}
