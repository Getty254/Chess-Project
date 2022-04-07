package chess;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ChessTesting {

	@Test
	public void legalMoveTest() {
		GameLogic logic = new GameLogic();
		
		Move move = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		BoardGUI.turn = 0;
		
		assertEquals(0,logic.isMoveLegal(move));
	}

	@Test
	public void illegalMoveTest() {
		GameLogic logic = new GameLogic();
		
		Move move = new Move("e2e5", 6, 4, 3, 4, PieceType.PAWN);
		BoardGUI.turn = 0;
		
		assertEquals(-1,logic.isMoveLegal(move));
	}
	
	@Test
	public void validStringMoveTest() {
		GameLogic logic = new GameLogic();
		
		BoardGUI.turn = 0;
		Move move1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move move2 = logic.isInputMoveValid("e2e4");

		assertEquals(move1.getMoveLAN(), move2.getMoveLAN());
		assertEquals(move1.getRowFrom(), move2.getRowFrom());
		assertEquals(move1.getColumnFrom(), move2.getColumnFrom());
		assertEquals(move1.getRowTo(), move2.getRowTo());
		assertEquals(move1.getColumnTo(), move2.getColumnTo());
	}
	
	@Test
	public void invalidStringMoveTest() {
		GameLogic logic = new GameLogic();
		
		assertEquals(null,logic.isInputMoveValid("e2e5"));
	}
	
	@Test
	public void whiteCastleShortTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB2 = new Move("e5e4", 3, 4, 4, 4, PieceType.PAWN);
		Move moveW3 = new Move("Bf1d3", 7, 5, 5, 3, PieceType.BISHOP);
		Move moveB3 = new Move("e4d3", 4, 4, 5, 3, PieceType.PAWN);
		Move moveW4 = new Move("O-O", 7, 4, 7, 6, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
	}
	
	@Test
	public void whiteCastleLongTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("d2d4", 6, 3, 4, 3, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("Qd1d3", 7, 3, 5, 3, PieceType.QUEEN);
		Move moveB2 = new Move("e7e6", 1, 4, 2, 4, PieceType.PAWN);
		Move moveW3 = new Move("Bc1e3", 7, 2, 5, 4, PieceType.BISHOP);
		Move moveB3 = new Move("e6e5", 2, 4, 3, 4, PieceType.PAWN);
		Move moveW4 = new Move("Nb1c3", 7, 1, 5, 2, PieceType.KNIGHT);
		Move moveB4 = new Move("e5e4", 3, 4, 4, 4, PieceType.PAWN);
		Move moveW5 = new Move("O-O-O", 7, 4, 7, 2, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void blackCastleShortTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Bf1d3", 7, 5, 5, 3, PieceType.BISHOP);
		Move moveB2 = new Move("Bf8b4", 0, 5, 4, 1, PieceType.BISHOP);
		Move moveW3 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB3 = new Move("Ng8f6", 0, 6, 2, 5, PieceType.KNIGHT);
		Move moveW4 = new Move("Rh1g1", 7, 7, 7, 6, PieceType.ROOK);
		Move moveB4 = new Move("O-O", 0, 4, 0, 6, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
	}
	
	@Test
	public void blackCastleLongTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("d2d4", 6, 3, 4, 3, PieceType.PAWN);
		Move moveB2 = new Move("Qd8d6", 0, 3, 2, 3, PieceType.QUEEN);
		Move moveW3 = new Move("c2c3", 6, 2, 5, 2, PieceType.PAWN);
		Move moveB3 = new Move("Bc8e6", 0, 2, 2, 4, PieceType.BISHOP);
		Move moveW4 = new Move("c3c4", 5, 2, 4, 2, PieceType.PAWN);
		Move moveB4 = new Move("Nb8c6", 0, 1, 2, 2, PieceType.KNIGHT);
		Move moveW5 = new Move("c4c5", 4, 2, 3, 2, PieceType.PAWN);
		Move moveB5 = new Move("O-O-O", 0, 4, 0, 2, PieceType.KING);

		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
	}
	
	@Test
	public void illegalWhiteCastleShortTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("b7b6", 1, 1, 2, 1, PieceType.PAWN);
		Move moveW2 = new Move("Bf1a6", 7, 5, 2, 0, PieceType.BISHOP);
		Move moveB2 = new Move("Bc8a6", 0, 2, 2, 0, PieceType.BISHOP);
		Move moveW3 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB3 = new Move("c7c6", 1, 2, 2, 2, PieceType.PAWN);
		Move moveW4 = new Move("O-O", 7, 4, 7, 6, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(-1,logic.isMoveLegal(moveW4));
	}
	
	@Test
	public void illegalWhiteCastleShortTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Bf1c4", 7, 5, 4, 2, PieceType.BISHOP);
		Move moveB2 = new Move("Qd8g5", 0, 3, 3, 6, PieceType.QUEEN);
		Move moveW3 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB3 = new Move("Qg5e3", 3, 6, 5, 4, PieceType.QUEEN);
		Move moveW4 = new Move("O-O", 7, 4, 7, 6, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(-1,logic.isMoveLegal(moveW4));
	}
	
	@Test
	public void illegalWhiteCastleShortTest3() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Bf1c4", 7, 5, 4, 2, PieceType.BISHOP);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB3 = new Move("c7c6", 1, 2, 2, 2, PieceType.PAWN);
		Move moveW4 = new Move("h2h4", 6, 7, 4, 7, PieceType.PAWN);
		Move moveB4 = new Move("h7h5", 1, 7, 3, 7, PieceType.PAWN);
		Move moveW5 = new Move("Rh1h2", 7, 7, 6, 7, PieceType.ROOK);
		Move moveB5 = new Move("Rh8h7", 0, 7, 1, 7, PieceType.ROOK);
		Move moveW6 = new Move("Rh2h1", 6, 7, 7, 7, PieceType.ROOK);
		Move moveB6 = new Move("c6c5", 2, 2, 3, 2, PieceType.PAWN);
		Move moveW7 = new Move("O-O", 7, 4, 7, 6, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
		BoardGUI.turn = 0;
		assertEquals(-1,logic.isMoveLegal(moveW7));
	}
	
	@Test
	public void illegalWhiteCastleShortTest4() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("h7h5", 1, 7, 3, 7, PieceType.PAWN);
		Move moveW2 = new Move("Bf1c4", 7, 5, 4, 2, PieceType.BISHOP);
		Move moveB2 = new Move("h5h4", 3, 7, 4, 7, PieceType.PAWN);
		Move moveW3 = new Move("Bc4d3", 4, 2, 5, 3, PieceType.BISHOP);
		Move moveB3 = new Move("h4h3", 4, 7, 5, 7, PieceType.PAWN);
		Move moveW4 = new Move("Ng1h3", 7, 6, 5, 7, PieceType.KNIGHT);
		Move moveB4 = new Move("Rh8h3", 0, 7, 5, 7, PieceType.ROOK);
		Move moveW5 = new Move("Bd3b5", 5, 3, 3, 1, PieceType.BISHOP);
		Move moveB5 = new Move("Rh3h2", 5, 7, 6, 7, PieceType.ROOK);
		Move moveW6 = new Move("Bb5d3", 3, 1, 5, 3, PieceType.BISHOP);
		Move moveB6 = new Move("Rh2h1", 6, 7, 7, 7, PieceType.ROOK);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
	}
	
	@Test
	public void illegalBlackCastleShortTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("h7h5", 1, 7, 3, 7, PieceType.PAWN);
		Move moveW2 = new Move("e3e4", 5, 4, 4, 4, PieceType.PAWN);
		Move moveB2 = new Move("Rh8h7", 0, 7, 1, 7, PieceType.ROOK);
		Move moveW3 = new Move("e4e5", 4, 4, 3, 4, PieceType.PAWN);
		Move moveB3 = new Move("Ng8f6", 0, 6, 2, 5, PieceType.KNIGHT);
		Move moveW4 = new Move("d2d3", 6, 3, 5, 3, PieceType.PAWN);
		Move moveB4 = new Move("e7e6", 1, 4, 2, 4, PieceType.PAWN);
		Move moveW5 = new Move("d3d4", 5, 3, 4, 3, PieceType.PAWN);
		Move moveB5 = new Move("Bf8c5", 0, 5, 3, 2, PieceType.BISHOP);
		Move moveW6 = new Move("d4c5", 4, 3, 3, 2, PieceType.PAWN);
		Move moveB6 = new Move("Rh7h8", 1, 7, 0, 7, PieceType.ROOK);
		Move moveW7 = new Move("c5c6", 3, 2, 2, 2, PieceType.PAWN);
		Move moveB7 = new Move("O-O", 0, 4, 0, 6, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW7));
		BoardGUI.turn = 1;
		assertEquals(-1,logic.isMoveLegal(moveB7));
	}
	
	@Test
	public void illegalWhiteCastleLongTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("d2d4", 6, 3, 4, 3, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Qd1d3", 7, 3, 5, 3, PieceType.QUEEN);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("Bc1g5", 7, 2, 3, 6, PieceType.BISHOP);
		Move moveB3 = new Move("Qd8g5", 0, 3, 3, 6, PieceType.QUEEN);
		Move moveW4 = new Move("Nb1c3", 7, 1, 5, 2, PieceType.KNIGHT);
		Move moveB4 = new Move("d6d5", 2, 3, 3, 3, PieceType.PAWN);
		Move moveW5 = new Move("O-O-O", 7, 4, 7, 2, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(-1,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void illegalWhiteCastleLongTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("Qd1g4", 7, 3, 4, 6, PieceType.QUEEN);
		Move moveB2 = new Move("Bc8g4", 0, 2, 4, 6, PieceType.BISHOP);
		Move moveW3 = new Move("d2d3", 6, 3, 5, 3, PieceType.PAWN);
		Move moveB3 = new Move("c7c6", 1, 2, 2, 2, PieceType.PAWN);
		Move moveW4 = new Move("Bc1e3", 7, 2, 5, 4, PieceType.BISHOP);
		Move moveB4 = new Move("c6c5", 2, 2, 3, 2, PieceType.PAWN);
		Move moveW5 = new Move("Nb1c3", 7, 1, 5, 2, PieceType.KNIGHT);
		Move moveB5 = new Move("c5c4", 3, 2, 4, 2, PieceType.PAWN);
		Move moveW6 = new Move("O-O-O", 7, 4, 7, 2, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(-1,logic.isMoveLegal(moveW6));
	}
	
	@Test
	public void illegalWhiteCastleLongTest3() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("a2a4", 6, 0, 4, 0, PieceType.PAWN);
		Move moveB1 = new Move("a7a6", 1, 0, 2, 0, PieceType.PAWN);
		Move moveW2 = new Move("Ra1a2", 7, 0, 6, 0, PieceType.ROOK);
		Move moveB2 = new Move("a6a5", 2, 0, 3, 0, PieceType.PAWN);
		Move moveW3 = new Move("Nb1c3", 7, 1, 5, 2, PieceType.KNIGHT);
		Move moveB3 = new Move("b7b6", 1, 1, 2, 1, PieceType.PAWN);
		Move moveW4 = new Move("d2d4", 6, 3, 4, 3, PieceType.PAWN);
		Move moveB4 = new Move("c7c6", 1, 2, 2, 2, PieceType.PAWN);
		Move moveW5 = new Move("Bc1f4", 7, 2, 4, 5, PieceType.BISHOP);
		Move moveB5 = new Move("c6c5", 2, 2, 3, 2, PieceType.PAWN);
		Move moveW6 = new Move("Qd1d2", 7, 3, 6, 3, PieceType.QUEEN);
		Move moveB6 = new Move("b6b5", 2, 1, 3, 1, PieceType.PAWN);
		Move moveW7 = new Move("Ra2a1", 6, 0, 7, 0, PieceType.ROOK);
		Move moveB7 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW8 = new Move("O-O-O", 7, 4, 7, 2, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW7));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB7));
		BoardGUI.turn = 0;
		assertEquals(-1,logic.isMoveLegal(moveW8));
	}
	
	@Test
	public void illegalWhiteCastleLongTest4() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("d2d4", 6, 3, 4, 3, PieceType.PAWN);
		Move moveB1 = new Move("a7a5", 1, 0, 3, 0, PieceType.PAWN);
		Move moveW2 = new Move("Qd1d3", 7, 3, 5, 3, PieceType.QUEEN);
		Move moveB2 = new Move("a5a4", 3, 0, 4, 0, PieceType.PAWN);
		Move moveW3 = new Move("Bc1e3", 7, 2, 5, 4, PieceType.BISHOP);
		Move moveB3 = new Move("a4a3", 4, 0, 5, 0, PieceType.PAWN);
		Move moveW4 = new Move("Nb1a3", 7, 1, 5, 0, PieceType.KNIGHT);
		Move moveB4 = new Move("Ra8a3", 0, 0, 5, 0, PieceType.ROOK);
		Move moveW5 = new Move("Qd3c4", 5, 3, 4, 2, PieceType.QUEEN);
		Move moveB5 = new Move("Ra3a2", 5, 0, 6, 0, PieceType.ROOK);
		Move moveW6 = new Move("Qc4c3", 4, 2, 5, 2, PieceType.QUEEN);
		Move moveB6 = new Move("Ra2a1", 6, 0, 7, 0, PieceType.ROOK);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
	}
	
	@Test
	public void illegalBlackCastleLongTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("h2h3", 6, 7, 5, 7, PieceType.PAWN);
		Move moveB2 = new Move("Bc8g4", 0, 2, 4, 6, PieceType.BISHOP);
		Move moveW3 = new Move("Qd1g4", 7, 3, 4, 6, PieceType.QUEEN);
		Move moveB3 = new Move("Nb8c6", 0, 1, 2, 2, PieceType.KNIGHT);
		Move moveW4 = new Move("d2d3", 6, 3, 5, 3, PieceType.PAWN);
		Move moveB4 = new Move("Qd8d6", 0, 3, 2, 3, PieceType.QUEEN);
		Move moveW5 = new Move("d3d4", 5, 3, 4, 3, PieceType.PAWN);
		Move moveB5 = new Move("O-O-O", 0, 4, 0, 2, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(-1,logic.isMoveLegal(moveB5));
	}
	
	@Test
	public void illegalBlackCastleLongTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("d2d3", 6, 3, 5, 3, PieceType.PAWN);
		Move moveB2 = new Move("Qd8d6", 0, 3, 2, 3, PieceType.QUEEN);
		Move moveW3 = new Move("d3d4", 5, 3, 4, 3, PieceType.PAWN);
		Move moveB3 = new Move("Bc8f5", 0, 2, 3, 5, PieceType.BISHOP);
		Move moveW4 = new Move("e3e4", 5, 4, 4, 4, PieceType.PAWN);
		Move moveB4 = new Move("Nb8c6", 0, 1, 2, 2, PieceType.KNIGHT);
		Move moveW5 = new Move("e4f5", 4, 4, 3, 5, PieceType.PAWN);
		Move moveB5 = new Move("a7a5", 1, 0, 3, 0, PieceType.PAWN);
		Move moveW6 = new Move("c2c3", 6, 2, 5, 2, PieceType.PAWN);
		Move moveB6 = new Move("Ra8a7", 0, 0, 1, 0, PieceType.ROOK);
		Move moveW7 = new Move("b2b3", 6, 1, 5, 1, PieceType.PAWN);
		Move moveB7 = new Move("Ra7a8", 1, 0, 0, 0, PieceType.ROOK);
		Move moveW8 = new Move("b3b4", 5, 1, 4, 1, PieceType.PAWN);
		Move moveB8 = new Move("O-O-O", 0, 4, 0, 2, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW7));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB7));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW8));
		BoardGUI.turn = 1;
		assertEquals(-1,logic.isMoveLegal(moveB8));
	}
	
	@Test
	public void illegalBlackCastleLongTest3() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("a2a4", 6, 0, 4, 0, PieceType.PAWN);
		Move moveB1 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW2 = new Move("a4a5", 4, 0, 3, 0, PieceType.PAWN);
		Move moveB2 = new Move("Bc8g4", 0, 2, 4, 6, PieceType.BISHOP);
		Move moveW3 = new Move("a5a6", 3, 0, 2, 0, PieceType.PAWN);
		Move moveB3 = new Move("Nb8a6", 0, 1, 2, 0, PieceType.KNIGHT);
		Move moveW4 = new Move("Ra1a6", 7, 0, 2, 0, PieceType.ROOK);
		Move moveB4 = new Move("Qd8d7", 0, 3, 1, 3, PieceType.QUEEN);
		Move moveW5 = new Move("Ra6a7", 2, 0, 1, 0, PieceType.ROOK);
		Move moveB5 = new Move("Qd7c6", 1, 3, 2, 2, PieceType.QUEEN);
		Move moveW6 = new Move("Ra7a8", 1, 0, 0, 0, PieceType.ROOK);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
	}
	
	@Test
	public void whitePawnPromotionQueenTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("f2f4", 6, 5, 4, 5, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("f4e5", 4, 5, 3, 4, PieceType.PAWN);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("e5d6", 3, 4, 2, 3, PieceType.PAWN);
		Move moveB3 = new Move("Qd8e7", 0, 3, 1, 4, PieceType.QUEEN);
		Move moveW4 = new Move("d6c7", 2, 3, 1, 2, PieceType.PAWN);
		Move moveB4 = new Move("Bc8d7", 0, 2, 1, 3, PieceType.BISHOP);
		Move moveW5 = new Move("c7b8Q", 1, 2, 0, 1, PieceType.PAWN);
		moveW5.setPawnPromo();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void whitePawnPromotionRookTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("f2f4", 6, 5, 4, 5, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("f4e5", 4, 5, 3, 4, PieceType.PAWN);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("e5d6", 3, 4, 2, 3, PieceType.PAWN);
		Move moveB3 = new Move("Qd8e7", 0, 3, 1, 4, PieceType.QUEEN);
		Move moveW4 = new Move("d6c7", 2, 3, 1, 2, PieceType.PAWN);
		Move moveB4 = new Move("Bc8d7", 0, 2, 1, 3, PieceType.BISHOP);
		Move moveW5 = new Move("c7b8R", 1, 2, 0, 1, PieceType.PAWN);
		moveW5.setPawnPromo();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void whitePawnPromotionBishopTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("f2f4", 6, 5, 4, 5, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("f4e5", 4, 5, 3, 4, PieceType.PAWN);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("e5d6", 3, 4, 2, 3, PieceType.PAWN);
		Move moveB3 = new Move("Qd8e7", 0, 3, 1, 4, PieceType.QUEEN);
		Move moveW4 = new Move("d6c7", 2, 3, 1, 2, PieceType.PAWN);
		Move moveB4 = new Move("Bc8d7", 0, 2, 1, 3, PieceType.BISHOP);
		Move moveW5 = new Move("c7b8B", 1, 2, 0, 1, PieceType.PAWN);
		moveW5.setPawnPromo();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void whitePawnPromotionKnightTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("f2f4", 6, 5, 4, 5, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("f4e5", 4, 5, 3, 4, PieceType.PAWN);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("e5d6", 3, 4, 2, 3, PieceType.PAWN);
		Move moveB3 = new Move("Qd8e7", 0, 3, 1, 4, PieceType.QUEEN);
		Move moveW4 = new Move("d6c7", 2, 3, 1, 2, PieceType.PAWN);
		Move moveB4 = new Move("Bc8d7", 0, 2, 1, 3, PieceType.BISHOP);
		Move moveW5 = new Move("c7b8N", 1, 2, 0, 1, PieceType.PAWN);
		moveW5.setPawnPromo();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void whitePawnCheckTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("c2c4", 6, 2, 4, 2, PieceType.PAWN);
		Move moveB1 = new Move("g7g6", 1, 6, 2, 6, PieceType.PAWN);
		Move moveW2 = new Move("c4c5", 4, 2, 3, 2, PieceType.PAWN);
		Move moveB2 = new Move("g6g5", 2, 6, 3, 6, PieceType.PAWN);
		Move moveW3 = new Move("c5c6", 3, 2, 2, 2, PieceType.PAWN);
		Move moveB3 = new Move("g5g4", 3, 6, 4, 6, PieceType.PAWN);
		Move moveW4 = new Move("c6d7", 2, 2, 1, 3, PieceType.PAWN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
	}
	
	@Test
	public void blackPawnCheckTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("c2c3", 6, 2, 5, 2, PieceType.PAWN);
		Move moveB1 = new Move("g7g5", 1, 6, 3, 6, PieceType.PAWN);
		Move moveW2 = new Move("c3c4", 5, 2, 4, 2, PieceType.PAWN);
		Move moveB2 = new Move("g5g4", 3, 6, 4, 6, PieceType.PAWN);
		Move moveW3 = new Move("c4c5", 4, 2, 3, 2, PieceType.PAWN);
		Move moveB3 = new Move("g4g3", 4, 6, 5, 6, PieceType.PAWN);
		Move moveW4 = new Move("c5c6", 3, 2, 2, 2, PieceType.PAWN);
		Move moveB4 = new Move("g3f2", 5, 6, 6, 5, PieceType.PAWN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
	}
	
	@Test
	public void blackPawnCheckTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("g2g3", 6, 6, 5, 6, PieceType.PAWN);
		Move moveB1 = new Move("c7c5", 1, 2, 3, 2, PieceType.PAWN);
		Move moveW2 = new Move("g3g4", 5, 6, 4, 6, PieceType.PAWN);
		Move moveB2 = new Move("c5c4", 3, 2, 4, 2, PieceType.PAWN);
		Move moveW3 = new Move("g4g5", 4, 6, 3, 6, PieceType.PAWN);
		Move moveB3 = new Move("c4c3", 4, 2, 5, 2, PieceType.PAWN);
		Move moveW4 = new Move("g5g6", 3, 6, 2, 6, PieceType.PAWN);
		Move moveB4 = new Move("c3d2", 5, 2, 6, 3, PieceType.PAWN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
	}
	
	@Test
	public void rookCheckTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("a2a4", 6, 0, 4, 0, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Ra1a3", 7, 0, 5, 0, PieceType.ROOK);
		Move moveB2 = new Move("e5e4", 3, 4, 4, 4, PieceType.PAWN);
		Move moveW3 = new Move("Ra3e3", 5, 0, 5, 4, PieceType.ROOK);
		Move moveB3 = new Move("a7a6", 1, 0, 2, 0, PieceType.PAWN);
		Move moveW4 = new Move("Re3e4", 5, 4, 4, 4, PieceType.ROOK);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
	}
	
	@Test
	public void rookCheckTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("a7a5", 1, 0, 3, 0, PieceType.PAWN);
		Move moveW2 = new Move("e4e5", 4, 4, 3, 4, PieceType.PAWN);
		Move moveB2 = new Move("Ra8a6", 0, 0, 2, 0, PieceType.ROOK);
		Move moveW3 = new Move("a2a3", 6, 0, 5, 0, PieceType.PAWN);
		Move moveB3 = new Move("Ra6e6", 2, 0, 2, 4, PieceType.ROOK);
		Move moveW4 = new Move("a3a4", 5, 0, 4, 0, PieceType.PAWN);
		Move moveB4 = new Move("Re6e5", 2, 4, 3, 4, PieceType.ROOK);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
	}
	
	@Test
	public void rookCheckTest3() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("h2h4", 6, 7, 4, 7, PieceType.PAWN);
		Move moveB1 = new Move("g7g5", 1, 6, 3, 6, PieceType.PAWN);
		Move moveW2 = new Move("h4g5", 4, 7, 3, 6, PieceType.PAWN);
		Move moveB2 = new Move("Ng8h6", 0, 6, 2, 7, PieceType.KNIGHT);
		Move moveW3 = new Move("Rh1h6", 7, 7, 2, 7, PieceType.ROOK);
		Move moveB3 = new Move("f7f6", 1, 5, 2, 5, PieceType.PAWN);
		Move moveW4 = new Move("Rh6h7", 2, 7, 1, 7, PieceType.ROOK);
		Move moveB4 = new Move("Bf8h6", 0, 5, 2, 7, PieceType.BISHOP);
		Move moveW5 = new Move("Rh7h8", 1, 7, 0, 7, PieceType.ROOK);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
	}
	
	@Test
	public void queenCheckTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e6", 1, 4, 2, 4, PieceType.PAWN);
		Move moveW2 = new Move("Qd1g4", 7, 3, 4, 6, PieceType.QUEEN);
		Move moveB2 = new Move("d7d6", 1, 3, 2, 3, PieceType.PAWN);
		Move moveW3 = new Move("Qg4e6", 4, 6, 2, 4, PieceType.QUEEN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
	}
	
	@Test
	public void queenCheckTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e6", 1, 4, 2, 4, PieceType.PAWN);
		Move moveW2 = new Move("d2d3", 6, 3, 5, 3, PieceType.PAWN);
		Move moveB2 = new Move("Qd8g5", 0, 3, 3, 6, PieceType.QUEEN);
		Move moveW3 = new Move("g2g3", 6, 6, 5, 6, PieceType.PAWN);
		Move moveB3 = new Move("Qg5e3", 3, 6, 5, 4, PieceType.QUEEN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
	}
	
	@Test
	public void queenCheckTest3() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("f7f5", 1, 5, 3, 5, PieceType.PAWN);
		Move moveW2 = new Move("Qd1f3", 7, 3, 5, 5, PieceType.QUEEN);
		Move moveB2 = new Move("f5e4", 3, 5, 4, 4, PieceType.PAWN);
		Move moveW3 = new Move("Qf3f8", 5, 5, 0, 5, PieceType.QUEEN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
	}
	
	@Test
	public void knightCheckTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB1 = new Move("f7f6", 1, 5, 2, 5, PieceType.PAWN);
		Move moveW2 = new Move("Nf3h4", 5, 5, 4, 7, PieceType.KNIGHT);
		Move moveB2 = new Move("e7e6", 1, 4, 2, 4, PieceType.PAWN);
		Move moveW3 = new Move("Nh4f5", 4, 7, 3, 5, PieceType.KNIGHT);
		Move moveB3 = new Move("e6e5", 2, 4, 3, 4, PieceType.PAWN);
		Move moveW4 = new Move("Nf5g7", 3, 5, 1, 6, PieceType.KNIGHT);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
	}
	
	@Test
	public void bishopCheckTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("Bf1b5", 7, 5, 3, 1, PieceType.BISHOP);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
	}
	
	@Test
	public void bishopCheckTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("f7f5", 1, 5, 3, 5, PieceType.PAWN);
		Move moveW2 = new Move("Bf1e2", 7, 5, 6, 4, PieceType.BISHOP);
		Move moveB2 = new Move("f5f4", 3, 5, 4, 5, PieceType.PAWN);
		Move moveW3 = new Move("Be2h5", 6, 4, 3, 7, PieceType.BISHOP);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
	}
	
	@Test
	public void whiteEnPassantTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("h7h6", 1, 7, 2, 7, PieceType.PAWN);
		Move moveW2 = new Move("e4e5", 4, 4, 3, 4, PieceType.PAWN);
		Move moveB2 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW3 = new Move("e5d6", 3, 4, 2, 3, PieceType.PAWN);
		moveW3.setEnPassant();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
	}
	
	@Test
	public void whiteEnPassantTest2() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("c2c4", 6, 2, 4, 2, PieceType.PAWN);
		Move moveB1 = new Move("f7f6", 1, 5, 2, 5, PieceType.PAWN);
		Move moveW2 = new Move("c4c5", 4, 2, 3, 2, PieceType.PAWN);
		Move moveB2 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW3 = new Move("c5d6", 3, 2, 2, 3, PieceType.PAWN);
		moveW3.setEnPassant();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
	}
	
	@Test
	public void blackEnPassantTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("f2f3", 6, 5, 5, 5, PieceType.PAWN);
		Move moveB1 = new Move("d7d5", 1, 3, 3, 3, PieceType.PAWN);
		Move moveW2 = new Move("f3f4", 5, 5, 4, 5, PieceType.PAWN);
		Move moveB2 = new Move("d5d4", 3, 3, 4, 3, PieceType.PAWN);
		Move moveW3 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB3 = new Move("d4e3", 4, 3, 5, 4, PieceType.PAWN);
		moveB3.setEnPassant();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
	}
	
	@Test
	public void illegalKingMoveTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("Ke1e2", 7, 4, 6, 4, PieceType.KING);
		Move moveB2 = new Move("Ke8e7", 0, 4, 1, 4, PieceType.KING);
		Move moveW3 = new Move("Ke2f3", 6, 4, 5, 5, PieceType.KING);
		Move moveB3 = new Move("Ke7f6", 1, 4, 2, 5, PieceType.KING);
		Move moveW4 = new Move("Kf3g4", 5, 5, 4, 6, PieceType.KING);
		Move moveB4 = new Move("Kf6f5", 2, 5, 3, 5, PieceType.KING);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(-1,logic.isMoveLegal(moveB4));
	}
	
	@Test
	public void emptyPieceMovesTest() {
		EmptyPiece ep = new EmptyPiece();
		ChessPiece[][] board = new ChessPiece[8][8];
		
		assertEquals(null, ep.getMoves(board));
	}
	
	@Test
	public void twoMoveCheckmateTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("g2g4", 6, 6, 4, 6, PieceType.PAWN);
		Move moveB1 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW2 = new Move("f2f3", 6, 5, 5, 5, PieceType.PAWN);
		Move moveB2 = new Move("Qd8h4", 0, 3, 4, 7, PieceType.QUEEN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(1,logic.isMoveLegal(moveB2));
	}
	
	@Test
	public void enPassantCheckmateTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e4", 6, 4, 4, 4, PieceType.PAWN);
		Move moveB1 = new Move("c7c5", 1, 2, 3, 2, PieceType.PAWN);
		Move moveW2 = new Move("Ng1f3", 7, 6, 5, 5, PieceType.KNIGHT);
		Move moveB2 = new Move("Nb8c6", 0, 1, 2, 2, PieceType.KNIGHT);
		Move moveW3 = new Move("d2d4", 6, 3, 4, 3, PieceType.PAWN);
		Move moveB3 = new Move("c5d4", 3, 2, 4, 3, PieceType.PAWN);
		Move moveW4 = new Move("Nf3d4", 5, 5, 4, 3, PieceType.KNIGHT);
		Move moveB4 = new Move("Nc6d4", 2, 2, 4, 3, PieceType.KNIGHT);
		Move moveW5 = new Move("Qd1d4", 7, 3, 4, 3, PieceType.QUEEN);
		Move moveB5 = new Move("Qd8c7", 0, 3, 1, 2, PieceType.QUEEN);
		Move moveW6 = new Move("Nb1c3", 7, 1, 5, 2, PieceType.KNIGHT);
		Move moveB6 = new Move("e7e5", 1, 4, 3, 4, PieceType.PAWN);
		Move moveW7 = new Move("Qd4d1", 4, 3, 7, 3, PieceType.QUEEN);
		Move moveB7 = new Move("Ng8f6", 0, 6, 2, 5, PieceType.KNIGHT);
		Move moveW8 = new Move("Bf1d3", 7, 5, 5, 3, PieceType.BISHOP);
		Move moveB8 = new Move("b7b6", 1, 1, 2, 1, PieceType.PAWN);
		Move moveW9 = new Move("O-O", 7, 4, 7, 6, PieceType.KING);
		Move moveB9 = new Move("Bc8b7", 0, 2, 1, 1, PieceType.BISHOP);
		Move moveW10 = new Move("Bc1g5", 7, 2, 3, 6, PieceType.BISHOP);
		Move moveB10 = new Move("Bf8e7", 0, 5, 1, 4, PieceType.BISHOP);
		Move moveW11 = new Move("Bg5f6", 3, 6, 2, 5, PieceType.BISHOP);
		Move moveB11 = new Move("Be7f6", 1, 4, 2, 5, PieceType.BISHOP);
		Move moveW12 = new Move("Nc3d5", 5, 2, 3, 3, PieceType.KNIGHT);
		Move moveB12 = new Move("Bb7d5", 1, 1, 3, 3, PieceType.BISHOP);
		Move moveW13 = new Move("e4d5", 4, 4, 3, 3, PieceType.PAWN);
		Move moveB13 = new Move("Qc7c5", 1, 2, 3, 2, PieceType.QUEEN);
		Move moveW14 = new Move("c2c4", 6, 2, 4, 2, PieceType.PAWN);
		Move moveB14 = new Move("O-O", 0, 4, 0, 6, PieceType.KING);
		Move moveW15 = new Move("Qd1h5", 7, 3, 3, 7, PieceType.QUEEN);
		Move moveB15 = new Move("g7g6", 1, 6, 2, 6, PieceType.PAWN);
		Move moveW16 = new Move("Qh5f3", 3, 7, 5, 5, PieceType.QUEEN);
		Move moveB16 = new Move("Qc5d6", 3, 2, 2, 3, PieceType.QUEEN);
		Move moveW17 = new Move("h2h4", 6, 7, 4, 7, PieceType.PAWN);
		Move moveB17 = new Move("h7h5", 1, 7, 3, 7, PieceType.PAWN);
		Move moveW18 = new Move("g2g3", 6, 6, 5, 6, PieceType.PAWN);
		Move moveB18 = new Move("Rf8e8", 0, 5, 0, 4, PieceType.ROOK);
		Move moveW19 = new Move("Kg1g2", 7, 6, 6, 6, PieceType.KING);
		Move moveB19 = new Move("Re8e7", 0, 4, 1, 4, PieceType.ROOK);
		Move moveW20 = new Move("Ra1e1", 7, 0, 7, 4, PieceType.ROOK);
		Move moveB20 = new Move("Ra8e8", 0, 0, 0, 4, PieceType.ROOK);
		Move moveW21 = new Move("Rf1h1", 7, 5, 7, 7, PieceType.ROOK);
		Move moveB21 = new Move("Qd6b4", 2, 3, 4, 1, PieceType.QUEEN);
		Move moveW22 = new Move("b2b3", 6, 1, 5, 1, PieceType.PAWN);
		Move moveB22 = new Move("a7a5", 1, 0, 3, 0, PieceType.PAWN);
		Move moveW23 = new Move("g3g4", 5, 6, 4, 6, PieceType.PAWN);
		Move moveB23 = new Move("a5a4", 3, 0, 4, 0, PieceType.PAWN);
		Move moveW24 = new Move("g4h5", 4, 6, 3, 7, PieceType.PAWN);
		Move moveB24 = new Move("a4b3", 4, 0, 5, 1, PieceType.PAWN);
		Move moveW25 = new Move("a2b3", 6, 0, 5, 1, PieceType.PAWN);
		Move moveB25 = new Move("Qb4b3", 4, 1, 5, 1, PieceType.QUEEN);
		Move moveW26 = new Move("h5g6", 3, 7, 2, 6, PieceType.PAWN);
		Move moveB26 = new Move("e5e4", 3, 4, 4, 4, PieceType.PAWN);
		Move moveW27 = new Move("Qf3h5", 5, 5, 3, 7, PieceType.QUEEN);
		Move moveB27 = new Move("Qb3d3", 5, 1, 5, 3, PieceType.QUEEN);
		Move moveW28 = new Move("Qh5h7", 3, 7, 1, 7, PieceType.QUEEN);
		Move moveB28 = new Move("Kg8f8", 0, 6, 0, 5, PieceType.KING);
		Move moveW29 = new Move("h4h5", 4, 7, 3, 7, PieceType.PAWN);
		Move moveB29 = new Move("Qd3f3", 5, 3, 5, 5, PieceType.QUEEN);
		Move moveW30 = new Move("Kg2g1", 6, 6, 7, 6, PieceType.KING);
		Move moveB30 = new Move("Bf6d4", 2, 5, 4, 3, PieceType.BISHOP);
		Move moveW31 = new Move("Re1f1", 7, 4, 7, 5, PieceType.ROOK);
		Move moveB31 = new Move("Qf3g4", 5, 5, 4, 6, PieceType.QUEEN);
		Move moveW32 = new Move("Kg1h2", 7, 6, 6, 7, PieceType.KING);
		Move moveB32 = new Move("Bd4e5", 4, 3, 3, 4, PieceType.BISHOP);
		Move moveW33 = new Move("f2f4", 6, 5, 4, 5, PieceType.PAWN);
		Move moveB33 = new Move("e4f3", 4, 4, 5, 5, PieceType.PAWN);
		moveB33.setEnPassant();
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW7));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB7));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW8));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB8));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW9));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB9));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW10));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB10));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW11));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB11));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW12));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB12));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW13));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB13));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW14));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB14));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW15));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB15));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW16));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB16));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW17));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB17));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW18));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB18));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW19));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB19));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW20));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB20));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW21));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB21));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW22));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB22));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW23));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB23));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW24));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB24));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW25));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB25));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW26));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB26));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW27));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB27));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW28));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB28));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW29));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB29));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW30));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB30));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW31));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB31));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW32));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB32));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW33));
		BoardGUI.turn = 1;
		assertEquals(1,logic.isMoveLegal(moveB33));
	}
	
	@Test
	public void stalemateTest() {
		GameLogic logic = new GameLogic();
		
		Move moveW1 = new Move("e2e3", 6, 4, 5, 4, PieceType.PAWN);
		Move moveB1 = new Move("a7a5", 1, 0, 3, 0, PieceType.PAWN);
		Move moveW2 = new Move("Qd1h5", 7, 3, 3, 7, PieceType.QUEEN);
		Move moveB2 = new Move("Ra8a6", 0, 0, 2, 0, PieceType.ROOK);
		Move moveW3 = new Move("Qh5a5", 3, 7, 3, 0, PieceType.QUEEN);
		Move moveB3 = new Move("h7h5", 1, 7, 3, 7, PieceType.PAWN);
		Move moveW4 = new Move("h2h4", 6, 7, 4, 7, PieceType.PAWN);
		Move moveB4 = new Move("Ra6h6", 2, 0, 2, 7, PieceType.ROOK);
		Move moveW5 = new Move("Qa5c7", 3, 0, 1, 2, PieceType.QUEEN);
		Move moveB5 = new Move("f7f6", 1, 5, 2, 5, PieceType.PAWN);
		Move moveW6 = new Move("Qc7d7", 1, 2, 1, 3, PieceType.QUEEN);
		Move moveB6 = new Move("Ke8f7", 0, 4, 1, 5, PieceType.KING);
		Move moveW7 = new Move("Qd7b7", 1, 3, 1, 1, PieceType.QUEEN);
		Move moveB7 = new Move("Qd8d3", 0, 3, 5, 3, PieceType.QUEEN);
		Move moveW8 = new Move("Qb7b8", 1, 1, 0, 1, PieceType.QUEEN);
		Move moveB8 = new Move("Qd3h7", 5, 3, 1, 7, PieceType.QUEEN);
		Move moveW9 = new Move("Qb8c8", 0, 1, 0, 2, PieceType.QUEEN);
		Move moveB9 = new Move("Kf7g6", 1, 5, 2, 6, PieceType.KING);
		Move moveW10 = new Move("Qc8e6", 0, 2, 2, 4, PieceType.QUEEN);
		
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW1));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB1));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW2));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB2));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW3));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB3));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW4));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB4));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW5));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB5));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW6));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB6));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW7));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB7));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW8));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB8));
		BoardGUI.turn = 0;
		assertEquals(0,logic.isMoveLegal(moveW9));
		BoardGUI.turn = 1;
		assertEquals(0,logic.isMoveLegal(moveB9));
		BoardGUI.turn = 0;
		assertEquals(2,logic.isMoveLegal(moveW10));
	}

}
