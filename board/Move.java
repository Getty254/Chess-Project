package board;

import pieces.ChessPiece;

public abstract class Move {

    final BoardGUI board;
    final ChessPiece movedPiece;
    final int destinationCoordinate;

    Move( final BoardGUI board, final ChessPiece movedPiece, final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class BasicMove extends Move {

        public BasicMove(final BoardGUI board, final ChessPiece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move{

        final ChessPiece attackedPiece;

        public AttackMove(final BoardGUI board, final  ChessPiece movedPiece, final int destinationCoordinate, final ChessPiece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }

}
