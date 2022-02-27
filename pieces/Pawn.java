package pieces;

import board.BoardGUI;
import board.BoardUtils;
import board.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

	Pawn(int piecePosition, PieceColor pieceColor) {
		super(piecePosition, pieceColor);
	}
	private final static int[] POSSIBLE_MOVE_VECTOR_COORDINATES = { 8, 16, 7, 9};

	@Override
	public List<Move> getLegalMoves( final BoardGUI board) {

		final List<Move> legalMoves = new ArrayList<>();
		for( final int currentPossibleMove: POSSIBLE_MOVE_VECTOR_COORDINATES) {
			final int possibleDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection() * currentPossibleMove);
			if(BoardUtils.isValidTileCoordinate(currentPossibleMove)){
				continue;
			}

			if(currentPossibleMove == 8 && !board.getTile(possibleDestinationCoordinate).isTileOccupied()){
				//TODO pawn promotion should be covered
				legalMoves.add(new Move.BasicMove(board, this,possibleDestinationCoordinate ));
				/* First pawn move to either one or two tiles */
			}else if( currentPossibleMove == 16 && this.isFirstMove() && (BoardUtils.SECOND_ROW[this.piecePosition] &&
					this.getPieceColor().isBlack() || BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite()) ){

               final int behindPossibleDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
			   if(!board.getTile(behindPossibleDestinationCoordinate).isTileOccupied() &&
			       !board.getTile(possibleDestinationCoordinate).isTileOccupied()){
				   legalMoves.add(new Move.BasicMove(board, this,possibleDestinationCoordinate ));
			   }
			}
			/* White pawn on 8th column attack and Black pawn on 1st column edge case */
			else if(currentPossibleMove == 7 &&
					!(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
			        ( BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())){
				/*Check if pawn can attack an opponent piece */
               if(board.getTile(possibleDestinationCoordinate).isTileOccupied()){
				   final ChessPiece pieceOnCandidate = board.getTile(possibleDestinationCoordinate).getPiece();
				   if(this.pieceColor != pieceOnCandidate.getPieceColor()){
					   legalMoves.add(new Move.BasicMove(board, this,possibleDestinationCoordinate ));
				   }
			   }

			}
			else if(currentPossibleMove == 9 &&
				!(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
						( BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack())){
				final ChessPiece pieceOnCandidate = board.getTile(possibleDestinationCoordinate).getPiece();
				if(this.pieceColor != pieceOnCandidate.getPieceColor()){
					legalMoves.add(new Move.BasicMove(board, this,possibleDestinationCoordinate ));
				}
			}
		}
		return legalMoves;
	}

	@Override
	public char getPieceChar() {
		// TODO Auto-generated method stub
		return '♟';
	}

}
