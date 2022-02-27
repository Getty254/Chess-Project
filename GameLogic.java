import pieces.*;

public class GameLogic {
	public ChessPiece[][] board = new ChessPiece[8][8];
	
//	public Collection<ArrayList> getPossibleMoves() {
//		for each piece add getLegalMoves to a master list
//	}
	
	public boolean isCheck() {

		return false;
	}
	
	public void updateBoard() {
		
	}
	
	public void initBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(i == 6) {
					board[i][j] = new Pawn(0);
				}
				else if(i == 1) {
					board[i][j] = new Pawn(1);
				}
				else if(i == 0 || i == 7) {
					if(j == 0 || j == 7) {
						board[i][j] = (i==0)?new Rook(1):new Rook(0);
					}
					else if(j == 1 || j == 6) {
						board[i][j] = (i==0)?new Knight(1):new Knight(0);
					}
					else if(j == 2 || j == 5) {
						board[i][j] = (i==0)?new Bishop(1):new Bishop(0);
					}
					else if(j == 3) {
						board[i][j] = (i==0)?new Queen(1):new Queen(0);
					}
					else if(j == 4) {
						board[i][j] = (i==0)?new King(1):new King(0);
					}
				}
				else {
					board[i][j] = new EmptyPiece();
				}
			}
		}
	}
	public void printBoard() {
        char[] letters = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h','i'};
        System.out.println("  |-------------------------------|");

		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 8; j++) {
				// Print Column letters
				if(i == 8) {
					if(j == 0) {
						System.out.print("    " + letters[j] + "  ");
					}
					else {
						System.out.print(" " + letters[j] + "  ");
					}
				}
				// Print pieces, then new row
				else if(j == 7) {
					System.out.print(board[i][j].getPieceChar() + " |\n");
					System.out.println("  |-------------------------------|");
				}
				// Print Row numbers
				else if(j == 0) {
					System.out.print((8-i) + " | " + board[i][j].getPieceChar() + " | ");
				}
				// Print pieces
				else {
					System.out.print(board[i][j].getPieceChar() + " | ");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.initBoard();
		logic.printBoard();
	}
}
