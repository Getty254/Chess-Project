package pieces;

import board.BoardGUI;
import board.BoardUtils;
import board.Move;
import board.Tile;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

	private final static int[] POSSIBLE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
	public King(int piecePosition, PieceColor pieceColor) {
		super(piecePosition, pieceColor);
	}

	@Override
	public List<Move> getLegalMoves(BoardGUI board) {
		final List<Move> legalMoves = new ArrayList<>();

		for( final int currentPossibleMove: POSSIBLE_MOVE_COORDINATES){
			if (isFirstColumnExclusion(this.piecePosition, currentPossibleMove) ||
					isEighthColumnExclusion(this.piecePosition, currentPossibleMove)) {
				continue;
			}
			int possibleDestinationCoordinate = this.piecePosition + currentPossibleMove;

			if(BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)){
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

	private static boolean isFirstColumnExclusion(final int currentCandidate,
												  final int candidateDestinationCoordinate) {
		return BoardUtils.FIRST_COLUMN[currentCandidate]
				&& ((candidateDestinationCoordinate == -9) || (candidateDestinationCoordinate == -1) ||
				(candidateDestinationCoordinate == 7));
	}

	private static boolean isEighthColumnExclusion(final int currentCandidate,
												   final int candidateDestinationCoordinate) {
		return BoardUtils.EIGHTH_COLUMN[currentCandidate]
				&& ((candidateDestinationCoordinate == -7) || (candidateDestinationCoordinate == 1) ||
				(candidateDestinationCoordinate == 9));
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♚';
	}

}
