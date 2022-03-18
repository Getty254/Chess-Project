package chess;

import java.util.ArrayList;

/**
 * This class controls the logic for the 
 * chess game such as verifying moves and identifying
 * checkmates and stalemates.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class GameLogic {
	
	/** Matrix of the chess board containing all the pieces.*/
	private ChessPiece[][] board = new ChessPiece[8][8];
	/** Indicates if the white king is in check.*/
	public static boolean isInCheckP1 = false;
	/** Indicates if the black king is in check.*/
	public static boolean isInCheckP2 = false;
	
	/**
	 * Creates a new instance of the logic.
	 */
	public GameLogic() {
		initBoard();
	}
	
	/**
	 * Finds all the possible moves.
	 * 
	 * @param board ChessPiece 2d array
	 * @param turn int to get that player's moves
	 * @return ArrayList of Moves of all the moves for that player
	 */
	private ArrayList<Move> getAllMoves(ChessPiece[][] board, int turn) {
		
		ArrayList<Move> allMoves = new ArrayList<Move>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(!(board[i][j] instanceof EmptyPiece)
						&& board[i][j].getPieceColor() == turn) {
					allMoves.addAll(board[i][j].getMoves(board));
				}
			}
		}
		
		return allMoves;
	}
	
	/**
	 * Checks if the move is one of the possible moves
	 * that the player can make.
	 * 
	 * @param move String move in long algebraic notation
	 * @return boolean true if the move is valid,
	 * 				false if otherwise
	 */
	private boolean isMoveValid(String move) {
		
		ArrayList<Move> allMoves = getAllMoves(board, BoardGUI.turn);

		for(int i = 0; i < allMoves.size(); i++) {
			if(allMoves.get(i).getMoveLAN().equals(move)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the move from the textfield is legal 
	 * and if it is check if there is a checkmate.
	 * 
	 * @param move String containing the move
	 * 				in long algebraic notation
	 * @return Move containing the move details
	 */
	public Move isInputMoveValid(String move) {
		
		ArrayList<Move> allMoves = getAllMoves(board, BoardGUI.turn);

		for(int i = 0; i < allMoves.size(); i++) {
			
			if(allMoves.get(i).getMoveLAN().equals(move)) {
				return allMoves.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if the move is legal and if it is
	 * check if there is a checkmate.
	 * 
	 * @param move Move containing the move details
	 * @return int 0 if the move is legal and game is not over.
	 * 				-1 if the move is illegal.
	 * 				1 if the move results in a checkmate.
	 * 				2 if the move results in a stalemate.
	 */
	public int isMoveLegal(Move move) {	
		
		// Valid move
		if(isMoveValid(move.getMoveLAN())) {
			
			// Set opposing king to be in check
			// to prevent castling
			if(BoardGUI.turn == 0) {
				isInCheckP1 = false;
			}
			else if(BoardGUI.turn == 1) {
				isInCheckP2 = false;
			}
			
			// Create a temporary board
			ChessPiece[][] tempBoard = new ChessPiece[8][8];
			tempBoard = dupeBoard(board);

			// Move is short castle
			if(move.getMoveLAN().equals("O-O")) {
				Move tempMove;
				if(BoardGUI.turn == 0) {
					tempMove = new Move("Ke1f1", 
							move.getRowFrom(),
							move.getColumnFrom(),
							move.getRowTo(),
							move.getColumnFrom()+1, PieceType.KING);
					// Make the move on the temp board
					updateBoard(tempBoard, tempMove, BoardGUI.turn);
				}
				else if(BoardGUI.turn == 1) {
					tempMove = new Move("Ke8f8", 
							move.getRowFrom(),
							move.getColumnFrom(),
							move.getRowTo(),
							move.getColumnFrom()+1, PieceType.KING);
					// Make the move on the temp board
					updateBoard(tempBoard, tempMove, BoardGUI.turn);
				}
					
				// Cannot castle if an opponent's piece
				// is attacking the square the king has
				// to pass through
				if(isOwnKingAttacked(tempBoard, BoardGUI.turn)) {
					return -1;
				}
				else {
					tempBoard = dupeBoard(board);
				}
			}
			// Move is long castle
			else if(move.getMoveLAN().equals("O-O-O")) {
				Move tempMove;
				if(BoardGUI.turn == 0) {
					tempMove = new Move("Ke1d1", 
							move.getRowFrom(),
							move.getColumnFrom(),
							move.getRowTo(),
							move.getColumnFrom()-1, PieceType.KING);
					// Make the move on the temp board
					updateBoard(tempBoard, tempMove, BoardGUI.turn);
				}
				else if(BoardGUI.turn == 1) {
					tempMove = new Move("Ke8d8", 
							move.getRowFrom(),
							move.getColumnFrom(),
							move.getRowTo(),
							move.getColumnFrom()-1, PieceType.KING);
					// Make the move on the temp board
					updateBoard(tempBoard, tempMove, BoardGUI.turn);
				}
					
				// Cannot castle if an opponent's piece
				// is attacking the square the king has
				// to pass through
				if(isOwnKingAttacked(tempBoard, BoardGUI.turn)) {
					return -1;
				}
				else {
					tempBoard = dupeBoard(board);
				}
			}
			
			
			// Make the move on the temp board
			updateBoard(tempBoard, move, BoardGUI.turn);
			
			// Move does not put yourself in check
			if(!isOwnKingAttacked(tempBoard, BoardGUI.turn)) {
				
				// Update the main board
				updateBoard(board, move, BoardGUI.turn);
				
				MovesList.movesAL.add(move);
				// Get all moves to see if you put your
				// opponent in check
				getAllMoves(board, BoardGUI.turn);
				boolean isCheck = false;
				
				// Figure out if a piece is attacking the
				// opposing king
				for(int i = 0; i<8; i++) {
					for(int j = 0; j<8; j++) {
						if(board[i][j].isAttackingKing()) {
							isCheck = true;
							
							// Set opposing king to be in check
							// to prevent castling
							if(BoardGUI.turn == 0) {
								isInCheckP2 = true;
							}
							else if(BoardGUI.turn == 1) {
								isInCheckP1 = true;
							}
						}
					}
				}
				
				return isMate(isCheck);
			}
		}

		return -1;
	}
	
	/**
	 * Checks if there is a checkmate or a stalemate.
	 * 
	 * @param isCheck boolean true if the move put
	 * 			the opposing king in check,
	 * 			false if the opposing king is not in check
	 * @return int 0 if the game is not over.
	 * 				1 if the move results in a checkmate.
	 * 				2 if the move results in a stalemate.
	 */
	private int isMate(boolean isCheck) {
		// All opponent moves
		ArrayList<Move> allMoves = getAllMoves(board, ((BoardGUI.turn==0)?1:0));

		// Create a temporary board
		ChessPiece[][] tempBoard = new ChessPiece[8][8];
		tempBoard = dupeBoard(board);

		for(int i = 0; i < allMoves.size(); i++) {
			Move move = allMoves.get(i);

			// Make the move on the temp board
			updateBoard(tempBoard, move, ((BoardGUI.turn==0)?1:0));
			
			// Move does not put yourself in check
			if(!isOwnKingAttacked(tempBoard, ((BoardGUI.turn==0)?1:0))) {
				return 0;
			}
			
			// Reset temp board to check next move
			tempBoard = dupeBoard(board);
			
		}
		
		// Checkmate
		if(isCheck) {
			BoardGUI.isGameOver = true;
			return 1;
		}
		// Stalemate
		else {
			BoardGUI.isGameOver = true;
			return 2;
		}
	}
	
	/**
	 * Checks if a player puts themself in check.
	 * 
	 * @param board ChessPiece 2d array
	 * @param turn int for who just made a move
	 * @return boolean true if the player puts
	 * 		themself in check, false if otherwise.
	 */
	private boolean isOwnKingAttacked(ChessPiece[][] board, int turn) {
		ArrayList<Move> opponentMoves = getAllMoves(board, ((turn==0)?1:0));

		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				
				if(board[i][j].getPieceColor() != turn
						&& board[i][j].isAttackingKing()) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Creates a duplicate of the main
	 * chess board matrix.
	 * 
	 * @param board ChessPiece 2d array that will be copied
	 * @return ChessPiece 2d array of the copied board
	 */
	private ChessPiece[][] dupeBoard(ChessPiece[][] board) {
		ChessPiece[][] tempBoard = new ChessPiece[8][8];
		
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				tempBoard[i][j] = board[i][j];
			}
		}
		
		return tempBoard;
	}
	
	/**
	 * Makes the move in the board matrix.
	 * 
	 * @param board ChessPiece 2d array
	 * @param move String move in long algebraic notation
	 * @param rowFrom int row the piece was moved from
	 * @param colFrom int column the piece was moved from
	 * @param rowTo int row the piece was moved to
	 * @param colTo int column the piece was moved to
	 * @param turn int whose turn it is
	 * @return ChessPiece 2d array of the updated board
	 */
	private ChessPiece[][] updateBoard(ChessPiece[][] board, 
			Move move, int turn) {

		int rowFrom = move.getRowFrom();
		int rowTo = move.getRowTo();
		int colFrom = move.getColumnFrom();
		int colTo = move.getColumnTo();
		
		// Short Castles
		if(move.getMoveLAN().equals("O-O")) {
			if(turn == 0) {
				Rook rk = new Rook(0, 7, 5);
				rk.removeCastleRights();
				board[7][4] = new EmptyPiece(7, 4);
				board[7][7] = new EmptyPiece(7, 7);
				board[7][5] = rk;
				board[7][6] = new King(0, 7, 6, false);
			}
			else {
				Rook rk = new Rook(1, 0, 5);
				rk.removeCastleRights();
				board[0][4] = new EmptyPiece(0, 4);
				board[0][7] = new EmptyPiece(0, 7);
				board[0][5] = rk;
				board[0][6] = new King(1, 0, 6, false);
			}
		}
		// Long Castle
		else if(move.getMoveLAN().equals("O-O-O")) {
			if(turn == 0) {
				Rook rk = new Rook(0, 7, 3);
				rk.removeCastleRights();
				board[7][4] = new EmptyPiece(7, 4);
				board[7][0] = new EmptyPiece(7, 0);
				board[7][3] = rk;
				board[7][2] = new King(0, 7, 2, false);
			}
			else {
				Rook rk = new Rook(1, 0, 3);
				rk.removeCastleRights();
				board[0][4] = new EmptyPiece(0, 4);
				board[0][0] = new EmptyPiece(0, 0);
				board[0][3] = rk;
				board[0][2] = new King(1, 0, 2, false);
			}
		}
		// Pawn Promotion
		else if(move.isPawnPromo()){
			String lastCharacter = move.getMoveLAN().
					substring(move.getMoveLAN().length() - 1);
			
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			
			if(lastCharacter.equals("Q")) {
				board[rowTo][colTo] = new Queen(turn, rowTo, colTo);
			}
			else if(lastCharacter.equals("R")) {
				Rook rk = new Rook(turn, rowTo, colTo);
				rk.removeCastleRights();
				board[rowTo][colTo] = rk;
			}
			else if(lastCharacter.equals("B")) {
				board[rowTo][colTo] = new Bishop(turn, rowTo, colTo);
			}
			else if(lastCharacter.equals("N")) {
				board[rowTo][colTo] = new Knight(turn, rowTo, colTo);
			}
		}
		else if(move.isEnPassant()) {
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowFrom][colTo] = new EmptyPiece(rowFrom, colTo);
			
			board[rowTo][colTo] = new Pawn(turn, rowTo, colTo);
		}
		// Queen move
		else if(move.getPieceMoved() == PieceType.QUEEN) {
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowTo][colTo] = new Queen(turn, rowTo, colTo);
		}
		// Rook move
		else if(move.getPieceMoved() == PieceType.ROOK) {
			Rook rk = new Rook(turn, rowTo, colTo);
			rk.removeCastleRights();
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowTo][colTo] = rk;
		}
		// Bishop move
		else if(move.getPieceMoved() == PieceType.BISHOP) {
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowTo][colTo] = new Bishop(turn, rowTo, colTo);
		}
		// Knight move
		else if(move.getPieceMoved() == PieceType.KNIGHT) {
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowTo][colTo] = new Knight(turn, rowTo, colTo);
		}
		// King move
		else if(move.getPieceMoved() == PieceType.KING) {
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowTo][colTo] = new King(turn, rowTo, colTo, false);
		}
		// Pawn move
		else {
			board[rowFrom][colFrom] = new EmptyPiece(rowFrom, colFrom);
			board[rowTo][colTo] = new Pawn(turn, rowTo, colTo);
		}
		
		
		return board;
	}
	
	/**
	 * Sets up the pieces in the starting positions.
	 */
	private void initBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(i == 6) {
					board[i][j] = new Pawn(0, i, j);
				}
				else if(i == 1) {
					board[i][j] = new Pawn(1, i, j);
				}
				else if(i == 0 || i == 7) {
					if(j == 0 || j == 7) {
						board[i][j] = (i==0)?new Rook(1, i, j):new Rook(0, i, j);
					}
					else if(j == 1 || j == 6) {
						board[i][j] = (i==0)?new Knight(1, i, j):new Knight(0, i, j);
					}
					else if(j == 2 || j == 5) {
						board[i][j] = (i==0)?new Bishop(1, i, j):new Bishop(0, i, j);
					}
					else if(j == 3) {
						board[i][j] = (i==0)?new Queen(1, i, j):new Queen(0, i, j);
					}
					else if(j == 4) {
						board[i][j] = (i==0)?new King(1, i, j, true):new King(0, i, j, true);
					}
				}
				else {
					board[i][j] = new EmptyPiece(i, j);
				}
			}
		}
	}
	
//	/**
//	 * Debug purposes
//	 * @param board
//	 */
//	public void printBoard(ChessPiece[][] board) {
//        char[] letters = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
//        System.out.println("  |-------------------------------|");
//
//		for(int i = 0; i < 9; i++) {
//			for(int j = 0; j < 8; j++) {
//				// Print Column letters
//				if(i == 8) {
//					if(j == 0) {
//						System.out.print("    " + letters[j] + "  ");
//					}
//					else {
//						System.out.print(" " + letters[j] + "  ");
//					}
//				}
//				// Print pieces, then new row
//				else if(j == 7) {
//					System.out.print(board[i][j].getPieceChar() + " |\n");
//					System.out.println("  |-------------------------------|");
//				}
//				// Print Row numbers
//				else if(j == 0) {
//					System.out.print((8-i) + " | " + board[i][j].getPieceChar() + " | ");
//				}
//				// Print pieces
//				else {
//					System.out.print(board[i][j].getPieceChar() + " | ");
//				}
//			}
//		}
//	}
//	
//	
//	/**
//	 * Debug purposes
//	 * Creates a starting position
//	 */
//	public void testBoard() {
//		for(int i = 0; i < 8; i++) {
//			for(int j = 0; j < 8; j++) {
//				if(i == 7 && j == 4) {
//					board[i][j] = new King(0, i, j);
//				}
//				else if(i == 4 && j == 2) {
//					board[i][j] = new Queen(1, i, j);
//				}
//				else if(i == 6 && j == 1) {
//					board[i][j] = new Rook(1, i, j);
//				}
//				else {
//					board[i][j] = new EmptyPiece(i, j);
//				}
//			}
//		}
//	}
}
