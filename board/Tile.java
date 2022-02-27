package board;

import pieces.ChessPiece;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public abstract class Tile {
    protected final int tileCoordinate;
    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    /* creates 64 empty tiles on the board */
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile > emptyTileMap = new HashMap<>();

        for(int i = 0; i< BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return Collections.unmodifiableMap(emptyTileMap);
    }

   /* constructor for the tile class */
    public static Tile createTile(final int tileCoordinate, final ChessPiece piece){
        return piece != null? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }

      /*returns tile coordinate */
    private Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    /* instance object of a chess piece */
    public abstract ChessPiece getPiece();


    /* Child class of Tile class that keeps track of an empty tile */
    public static final class EmptyTile  extends Tile{
        private EmptyTile( final int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public ChessPiece getPiece(){
            return null;
        }
    }

    /* Child class of Tile class that keeps track of an occupied tile */
    public static final class OccupiedTile extends Tile{
        private final ChessPiece pieceOnTile;

        private OccupiedTile( int tileCoordinate, final ChessPiece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied(){
            return true;
        }

        @Override
        public ChessPiece getPiece(){
          return  this.pieceOnTile;
        }
    }

}
