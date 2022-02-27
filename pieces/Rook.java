package pieces;

import board.BoardGUI;
import board.BoardUtils;
import board.Move;
import board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends ChessPiece {

	public Rook(int piecePosition, PieceColor pieceColor) {
		super(piecePosition, pieceColor);
	}
	private final static int[] POSSIBLE_MOVE_VECTOR_COORDINATES = { -8, -1,1, 8};

	@Override
	public Collection<Move> getLegalMoves(BoardGUI board) {

		final List<Move> legalMoves = new ArrayList<>();

		for( final int currentPossibleMove: POSSIBLE_MOVE_VECTOR_COORDINATES){
			int possibleDestinationCoordinate = this.piecePosition;

			while(BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
				possibleDestinationCoordinate += currentPossibleMove;
				if(isFirstColumnExclusion(possibleDestinationCoordinate, currentPossibleMove)
						|| isEighthExclusion(possibleDestinationCoordinate, currentPossibleMove)){
					break;
				}

				if(BoardUtils.isValidTileCoordinate(currentPossibleMove)){
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
						break;
					}
				}
			}
		}
		return legalMoves;
	}

	private static boolean isFirstColumnExclusion(final int currentPosition, final int possibleOffSet){
		return BoardUtils.FIRST_COLUMN[currentPosition] && (possibleOffSet == -1);
	}

	private static boolean isEighthExclusion(final int currentPosition, final int possibleOffSet){
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (possibleOffSet == 1);
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♜';
	}

}
