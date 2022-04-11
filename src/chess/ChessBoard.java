package chess;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class is used to create the chess board
 * on the GUI, and set up all the event
 * handlers for dragging and dropping
 * the pieces.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class ChessBoard extends GridPane {
	/** Top section of the main screen that holds
	 * the settings button and player two name.
	 */
	private BorderPane topScreen; 
	/** Bottom section of the main screen that holds
	 * the player two name.
	 */
	private BorderPane bottomScreen;
	/** Right section of the main screen that holds
	 * the moves list, textfield, and flip board button.
	 */
	private BorderPane movesSection;
	/** Left section of the main screen that holds
	 * the clock labels and resign/draw buttons.
	 */
	private VBox gameInfo;
	/** Clocks for both players.*/
	private Clocks clocks;
	/** GridPane that holds the list of moves.
	 * The first column contains the move number.
	 * The second column contains white's move.
	 * The third column contains black's move.
	 */
	private MovesList movesList;
	/** Controls the logic for the piece movements.*/
	private GameLogic logic;
	/**	Label that opens the game over popup
	 * when the text is changed. This label is
	 * never displayed.*/
	private Label triggerGameOver;
	
	/**
	 * ChessBoard constructor.
	 * @param top BorderPane
	 * @param bottom BorderPane
	 * @param movesSec BorderPane
	 * @param left VBox
	 * @param playerClocks Clocks
	 * @param movesL MovesList
	 * @param gameLogic GameLogic
	 * @param trigger Label
	 */
	public ChessBoard(BorderPane top, 
			BorderPane bottom, BorderPane movesSec, 
			VBox left, Clocks playerClocks, MovesList movesL,
			GameLogic gameLogic, Label trigger) {
		this.topScreen = top;
		this.bottomScreen = bottom;
		this.movesSection = movesSec;
		this.gameInfo = left;
		this.clocks = playerClocks;
		this.movesList = movesL;
		this.logic = gameLogic;
		this.triggerGameOver = trigger;
		
		createBoard();
	}


	/**
	 * Creates the chess board gridpane with all
	 * of the pieces and event handling.
	 */
	public void createBoard() {		
		
		// Remove all squares and pieces to create new ones
		this.getChildren().clear();
		
		// Add css class
    	this.getStyleClass().add("chess-board");
    			
		double sqSize = 100;
		
		// Create 64 rectangles (squares) and add them to the grid
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				Rectangle square =
					new Rectangle(sqSize, sqSize, sqSize, sqSize);

				// Color square light brown
			    if (!(row % 2 == 0 ^ column % 2 == 0)) {
			    	square.setFill(BoardColors.getColorOne());
			    }
			    // Color square dark brown
			    else {
			    	square.setFill(BoardColors.getColorTwo());
			    }
			    
			    StackPane squareStack = new StackPane(); 
			    Text chessPiece = new Text();

			    // Set piece based on starting position
			    setPiece(chessPiece, row, column);
			    // Add piece and labels to the square
			    setStackLayout(square, row, column,
			    			squareStack, chessPiece);
			    
			    // Highlight square when clicked
			    squareStack.setOnMouseClicked((click) -> {
			    	int squareRow = GridPane.getRowIndex(squareStack);
			    	int squareCol = GridPane.getColumnIndex(squareStack);

			    	// Left click, highlight red
			    	if(click.getButton() == MouseButton.PRIMARY) {
			    		square.setFill(Color.RED);
			    	}
			    	// Right click, set square to original color
			    	else if(click.getButton() == MouseButton.SECONDARY) {
			    		if(!(squareRow % 2 == 0 ^ squareCol % 2 == 0)) {
				    		square.setFill(BoardColors.getColorOne());
				    	}
			    		else {
				    		square.setFill(BoardColors.getColorTwo());
				    	}
			    	}
			    });
			    
			    squareStack.setOnDragDetected((MouseEvent event) -> {	
			    	if(!BoardGUI.getIsGameOver()) {
				    	// Piece being dragged
				    	Text pieceText = (Text)
				    			squareStack.getChildren().get(3);
	
				    	// Don't drag square witout piece
				    	if(pieceText.getText().equals("")) {
				    		event.consume();
				    	}
				    	// Square has a piece, so it can be dragged
				    	else {
				    		Dragboard db = squareStack.startDragAndDrop(TransferMode.MOVE);
				    					    		
				    		// if player trys to pick up 
				    		// opponents pieces cancel the drag
				    		if((BoardGUI.getTurn() == 1
				    			&& pieceText.getFill().equals(
				    					Color.rgb(250, 249, 246)))
				    			|| (BoardGUI.getTurn() == 0 
				    			&& pieceText.getFill().equals(
				    					Color.rgb(0, 0, 0)))) {
				    			
				    			event.consume();
				    		}
				    		else {
						    	// Delete the piece from square when picked up
						    	squareStack.getChildren().remove(3);
		
						    	// Add a blank placeholder to the square
						    	Text blankText = new Text();
						    	squareStack.getChildren().add(blankText);
						    	
						    	ClipboardContent content = new ClipboardContent();
					            content.putString(pieceText.getText());
					            db.setContent(content);
					            
					            // Set the icon during the drag
					            setDragIcon(db, pieceText);
				    		}
				    	}
			    	}
			    });
			    
			    squareStack.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });

			    squareStack.setOnDragDropped((DragEvent event) -> {
		            Dragboard db = event.getDragboard();
		            
		            if(event.getGestureSource() instanceof StackPane) {
			            if (db.hasString()) {
			            	// Get the square the piece
			            	// started on before the move
				            StackPane squareFrom = (StackPane) 
				            		event.getGestureSource();
				            
			                String fullMove = getMoveNotation(
			                		db.getString(), squareFrom,
			                		squareStack);
			            	
				            Move move = logic.isInputMoveValid(fullMove);
				            int moveStatus = -1;
				            
				            if(move != null) {
				            	moveStatus = logic.isMoveLegal(move);
				            }
				            
		    	    		if(moveStatus == -1) {
		    	    			resetPiece(event);
				            	event.setDropCompleted(false);
		    	    		}
		    	    		else {
		    	    			placePiece(move);
					            
					            clocks.updatePlayerClocks();
			    	    		movesList.updateMovesList(fullMove);
			    	    		
			    	    		// Checkmate
			    	    		if(moveStatus == 1) {
			    	    			triggerGameOver.setText("checkmate");
			    	    		}
			    	    		// Stalemate
			    	    		else if(moveStatus == 2) {
			    	    			triggerGameOver.setText("stalemate");
			    	    		}
			    	    		
					            event.setDropCompleted(true);
				            }
			            } 
			            else {
			                event.setDropCompleted(false);
			            }
		            }
		            else {
		            	event.setDropCompleted(false);
		            }
		            
		            event.consume();
		        });
			    
			    squareStack.setOnDragDone((DragEvent event) -> {
		            event.consume();
		        });

			    // Listen for illegal drop
			    checkDropOutside();
			}
		}
	}
	
	
	/**
	 * Places the piece back on the square it was moved from. 
	 *
	 * @param event DragEvent for the piece that was just dragged
	 */
	private void resetPiece(DragEvent event) {
		if(event.getGestureSource() instanceof StackPane) {
	    	Dragboard db = event.getDragboard();
	    	
	    	// Get the square the piece started on before the move
            StackPane squareFrom = (StackPane) event.getGestureSource();
            
            // Get the piece being dropped
            Text chessPieceDropped = new Text(db.getString());
            
            // Add css class
            chessPieceDropped.getStyleClass().add("chess-pieces");
            
            // Set piece color to white if it's white's BoardGUI.turn
            if(BoardGUI.getTurn() == 0) {
            	chessPieceDropped.setFill(Color.rgb(250, 249, 246));
            }
            
            squareFrom.getChildren().remove(3);
            squareFrom.getChildren().add(chessPieceDropped);
    	}
    	
        event.consume();
	}
	
	/**
	 * Sets the text for a piece based on the square.
	 *
	 * @param chessPiece Text of the piece
	 * @param row int row the square is in
	 * @param column int column the square is in
	 */
	private void setPiece(Text chessPiece, int row, int column) {
		// Black pieces
	    if(row == 0) {
	    	// Black Rooks in top corners
	    	if(column == 0 || column == 7) {
	    		chessPiece.setText(ChessPiece.ROOK);
	    	}
	    	// Black Knights on b and g files
	    	else if(column == 1 || column == 6) {
	    		chessPiece.setText(ChessPiece.KNIGHT);
	    	}
	    	// Black Bishops on c and f files
	    	else if(column == 2 || column == 5) {
	    		chessPiece.setText(ChessPiece.BISHOP);
	    	}
	    	// Black Queen on d file
	    	else if(column == 3) {
	    		chessPiece.setText(ChessPiece.QUEEN);
	    	}
	    	// Black King on e file
	    	else {
	    		chessPiece.setText(ChessPiece.KING);
	    	}
	    }
	    // White pieces
	    else if(row == 7) {
	    	// White Rooks in bottom corners
	    	if(column == 0 || column == 7) {
	    		chessPiece.setText(ChessPiece.ROOK);
	    	}
	    	// White Knights on b and g files
	    	else if(column == 1 || column == 6) {
	    		chessPiece.setText(ChessPiece.KNIGHT);
	    	}
	    	// White Bishops on c and f files
	    	else if(column == 2 || column == 5) {
	    		chessPiece.setText(ChessPiece.BISHOP);
	    	}
	    	// White Queen on d file
	    	else if(column == 3) {
	    		chessPiece.setText(ChessPiece.QUEEN);
	    	}
	    	// White King on e file
	    	else {
	    		chessPiece.setText(ChessPiece.KING);
	    	}
	    	
	    	// Color the piece white
	    	chessPiece.setFill(Color.rgb(250, 249, 246));
	    }
	    // Black Pawns
	    else if(row == 1) {
	    	chessPiece.setText(ChessPiece.PAWN);
	    }
	    // White Pawns
	    else if(row == 6) {
	    	chessPiece.setText(ChessPiece.PAWN);
	    	// Color the pawn white
	    	chessPiece.setFill(Color.rgb(250, 249, 246));
	    }
	}
	
	/**
	 * Gets the long algebraic notation of the move.
	 *
	 * @param pieceDragged String of the chess piece
	 * @param squareFrom StackPane original square
	 * @param squareTo StackPane target square
	 * @return String of the move in LAN
	 */
	private String getMoveNotation(String pieceDragged,
			StackPane squareFrom, StackPane squareTo) {

        // Get column matrix numbers
        int colFrom = GridPane.getColumnIndex(squareFrom);
        int colTo = GridPane.getColumnIndex(squareTo);
 
        // Get row matrix numbers
        int rowFrom = GridPane.getRowIndex(squareFrom);
        int rowTo = GridPane.getRowIndex(squareTo);

        // Convert column number to letter a - h
        char colLetterFrom = (char) (97 + colFrom);
        char colLetterTo = (char) (97 + colTo);

        // Convert the matrix row to the board row
        int boardRowFrom = 8 - rowFrom;
        int boardRowTo = 8 - rowTo;


        String pieceLetter;

		if(pieceDragged.equals(ChessPiece.BISHOP)) {
			pieceLetter = "B";
		}
		else if(pieceDragged.equals(ChessPiece.KNIGHT)) {
		 	pieceLetter = "N";
		}
		else if(pieceDragged.equals(ChessPiece.ROOK)) {
		 	pieceLetter = "R";
		}
		else if(pieceDragged.equals(ChessPiece.QUEEN)) {
			pieceLetter = "Q";
		}
		else if(pieceDragged.equals(ChessPiece.KING)) {
			pieceLetter = "K";
		}
		else {
			pieceLetter = "";
		}

		String fullMove;

		// Check if move is a Pawn Promotion
		if(pieceDragged.equals(ChessPiece.PAWN) 
		 		&& ((BoardGUI.getTurn() == 0 && rowTo == 0) 
		 		|| (BoardGUI.getTurn() == 1 && rowTo == 7))) {
		 					            	
		 	// Pick promotion piece here
			PawnPromotionDialog dialog = new PawnPromotionDialog();	
			
			// Player promotes to a rook
			if(dialog.getCloseStatus().equals(ChessPiece.ROOK)){
				fullMove = pieceLetter + colLetterFrom 
					+ boardRowFrom + colLetterTo
					+ boardRowTo + "R";
			}
			// Player promotes to a bishop
			else if(dialog.getCloseStatus().equals(ChessPiece.BISHOP)){
				fullMove = pieceLetter + colLetterFrom 
					+ boardRowFrom + colLetterTo
					+ boardRowTo + "B";
			}
			// Player promotes to a knight
			else if(dialog.getCloseStatus().equals(ChessPiece.KNIGHT)){
				fullMove = pieceLetter + colLetterFrom 
					+ boardRowFrom + colLetterTo
					+ boardRowTo + "N";
			}
			// Player promotes to a queen
			else {
				fullMove = pieceLetter + colLetterFrom 
					+ boardRowFrom + colLetterTo 
					+ boardRowTo + "Q";
			}
		}
		// Pawn move, but not a promotion
		else if(pieceDragged.equals(ChessPiece.PAWN)) {
		 	fullMove = "" + colLetterFrom
						+ boardRowFrom
						+ colLetterTo + boardRowTo;
		}
		// Check if move is Castle
		else if(pieceDragged.equals(ChessPiece.KING) 
		 		&& ((BoardGUI.getTurn() == 0 
		 		&& rowFrom == 7 && rowTo == 7 
		 		&& colFrom == 4 
		 		&& (colTo == 6 || colTo == 2)) 
		 		|| (BoardGUI.getTurn() == 1 
		 		&& rowFrom == 0 && rowTo == 0 
		            && colFrom == 4 
		            && (colTo == 6 || colTo == 2)))) {
		 			 	
		 	if(colTo == 6) {
		 		fullMove = "O-O";
		 	}
		 	else {
		 		fullMove = "O-O-O";
		 	}
		}
		// Regular piece move
		else {
			fullMove = pieceLetter + colLetterFrom 
						+ boardRowFrom 
						+ colLetterTo + boardRowTo;
		}
		
		return fullMove;
	}
	
	/**
	 * Moves the chess piece to the desired square.
	 *
	 * @param move Move object containing info about the move.
	 */
	public void placePiece(Move move) {
		// Get the squares used for the move
		StackPane squareFrom =
				(StackPane) this.getChildren()
				.get(move.getRowFrom() * 8
					+ move.getColumnFrom());
		StackPane squareTo =
				(StackPane) this.getChildren()
				.get(move.getRowTo() * 8
					+ move.getColumnTo());

		Text chessPieceMoved = setChessPiece(move);
		
		// Remove piece or placeholder
        squareTo.getChildren().remove(3);
        // Place the piece
        squareTo.getChildren().add(chessPieceMoved);
        
        
        boolean isCastle = false;
        boolean isCastleShort = false;
        
        // Check short castle
        if(move.getMoveLAN().equals("O-O")) {
        	isCastle = true;
        	isCastleShort = true;
        }
        // Check long castle
        else if(move.getMoveLAN().equals("O-O-O")) {
        	isCastle = true;
        }
        
        // Player wants to castle
        if(isCastle) {
        	
            StackPane rookSquareFrom;
            StackPane rookSquareTo;
            
            // Move Rook with King
            Text rookText = new Text(ChessPiece.ROOK);
            // Add css class
            rookText.getStyleClass().add("chess-pieces");

	    	// White's Castles
        	if(BoardGUI.getTurn() == 0) {
        		rookText.setFill(
        			Color.rgb(250, 249, 246));
		    	// Castle short side
        		if(isCastleShort) {
        			// Move Rook from bottom right square
        			rookSquareFrom = (StackPane)
        					this.getChildren().get(63);
			    	
			    	// Move Rook to row 8 column f
			    	rookSquareTo = (StackPane)
			    			this.getChildren().get(61);
        			rookSquareTo.getChildren().remove(3);
        			rookSquareTo.getChildren().add(rookText);
        		}
        		// Castle long side
        		else {
        			// Move Rook from bottom left square
        			rookSquareFrom = (StackPane)
        					this.getChildren().get(56);

			    	// Move Rook to row 8 column d
			    	rookSquareTo = (StackPane)
			    			this.getChildren().get(59);
        			rookSquareTo.getChildren().remove(3);
        			rookSquareTo.getChildren().add(rookText);
        		}
        	}
        	// Black Castles
        	else {
		    	// Castle short side
        		if(isCastleShort) {

        			// Move Rook from top right square
        			rookSquareFrom = (StackPane)
        					this.getChildren().get(7);
			    	
			    	// Move Rook to row 1 column f
			    	rookSquareTo = (StackPane)
			    			this.getChildren().get(5);
        			rookSquareTo.getChildren().remove(3);
        			rookSquareTo.getChildren().add(rookText);
        		}
        		// Castle long side
        		else {
        			// Move Rook from top left square
        			rookSquareFrom = (StackPane)
        					this.getChildren().get(0);

			    	// Move Rook to row 1 column d
			    	rookSquareTo = (StackPane)
			    			this.getChildren().get(3);
        			rookSquareTo.getChildren().remove(3);
        			rookSquareTo.getChildren().add(rookText);
        		}
        	}	
        	rookSquareFrom.getChildren().remove(3);
	    	rookSquareFrom.getChildren().add(new Text());
        }
        // Pawn capture by en passant
        else if(move.isEnPassant()) {

        	StackPane opponentPawnSquare = (StackPane)
					this.getChildren().get(
							move.getRowFrom() * 8 
							+ move.getColumnTo());

        	// Remove the catured pawn
        	opponentPawnSquare.getChildren().remove(3);
        	opponentPawnSquare.getChildren().add(new Text());
        }

        // Remove the piece from the original square
		squareFrom.getChildren().remove(3);
		// Add a placeholder to the square
		squareFrom.getChildren().add(new Text());
		// Reset square colors in case any are highlighted
		resetSquareColor();
	}
	
	/**
	 * Resets all square colors to what they
	 * were before any highlights.
	 */
	private void resetSquareColor() {
		// Loop through all squares
		for(int row = 0; row < 8; row++) {
			for(int column = 0; column < 8; column++) {
				StackPane stackPane = (StackPane)
						this.getChildren().get(row * 8 + column);
				Rectangle square = (Rectangle)
						stackPane.getChildren().get(0);
				
				// Set the square color
				if(!(row % 2 == 0 ^ column % 2 == 0)) {
		    		square.setFill(BoardColors.getColorOne());
		    	}
		    	else {
		    		square.setFill(BoardColors.getColorTwo());
		    	}
			}
		}
	}
	
	/**
	 * Sets the type of chess piece that is placed.
	 * 
	 * @param move Move object
	 * @return Text containing the ascii character of
	 * 				chess piece
	 */
	private Text setChessPiece(Move move) {
		Text piece = new Text();
		
		// Set the piece to a queen
		if(move.getPieceMoved() == PieceType.QUEEN) {
			piece.setText(ChessPiece.QUEEN);
		}
		// Set the piece to a rook
		else if(move.getPieceMoved() == PieceType.ROOK) {
			piece.setText(ChessPiece.ROOK);
		}
		// Set the piece to a bishop
		else if(move.getPieceMoved() == PieceType.BISHOP) {
			piece.setText(ChessPiece.BISHOP);
		}
		// Set the piece to a knight
		else if(move.getPieceMoved() == PieceType.KNIGHT) {
			piece.setText(ChessPiece.KNIGHT);
		}
		// Set the piece to a king
		else if(move.getPieceMoved() == PieceType.KING) {
			piece.setText(ChessPiece.KING);
		}
		// Set the piece based on the promotion
		else if(move.isPawnPromo()) {
			// Get the last letter from the move notation
    		String promoLetter =
    			move.getMoveLAN().substring(4, 5);
    		
			// Promote to Queen
    		if(promoLetter.equals("Q")) {
    			piece.setText(ChessPiece.QUEEN);
    		}
    		// Promote to Rook
    		else if(promoLetter.equals("R")) {
    			piece.setText(ChessPiece.ROOK);
    		}
    		// Promote to Knight
    		else if(promoLetter.equals("N")) {
    			piece.setText(ChessPiece.KNIGHT);
    		}
    		// Promote to Bishop
    		else {
    			piece.setText(ChessPiece.BISHOP);
    		}
		}
		// Set the piece to a pawn
		else {
			piece.setText(ChessPiece.PAWN);
		}
		
		// Add css class
		piece.getStyleClass().add("chess-pieces");
        
        // Set piece color to white
        // if it's white's BoardGUI.turn
        if(BoardGUI.getTurn() == 0) {
        	piece.setFill(Color.rgb(250, 249, 246));
        }
		
		return piece;
	}
	
	/**
	 * Set the piece and row and column labels on the square.
	 *
	 * @param square Rectangle for the square on the board
	 * @param row int matrix row of the board
	 * @param column int matrix column of the board
	 * @param squareStack StackPane to hold all the elements
	 * @param chessPiece Text of the chess piece
	 */
	private void setStackLayout(Rectangle square, int row, int column,
				StackPane squareStack, Text chessPiece) {
		
		Text colLet = new Text(" ");
	    Text rowNum = new Text(" ");
	    
	    // Label columns with letters a - h on the bottom row
	    if(row == 7) {
	    	
			switch(column) {
				case 0:
					colLet = new Text(" a ");
					rowNum = new Text(" 1 ");
					break;
				case 1:
					colLet = new Text(" b ");
					break;
				case 2:
					colLet = new Text(" c ");
					break;
				case 3:
					colLet = new Text(" d ");
					break;
				case 4:
					colLet = new Text(" e ");
					break;
				case 5:
					colLet = new Text(" f ");
					break;
				case 6:
					colLet = new Text(" g ");
					break;
				default:
					colLet = new Text(" h ");
			}
			
	    	squareStack.getChildren().addAll(square, colLet, rowNum, chessPiece);
	    	// Position column letter in bottom right
	    	// corner of the square
	    	StackPane.setAlignment(colLet, Pos.BOTTOM_RIGHT);
	    	// Position row number in top left 
	    	// corner of the square
			StackPane.setAlignment(rowNum, Pos.TOP_LEFT);
	    	this.add(squareStack, column, row);
	    }
	    // Add row numbers 2 - 8
	    // row 1 is added with columns
	    else if(column == 0) {
	    	switch(row) {
				case 0:
					rowNum = new Text(" 8 ");							
					break;
				case 1:
					rowNum = new Text(" 7 ");
					break;
				case 2:
					rowNum = new Text(" 6 ");
					break;
				case 3:
					rowNum = new Text(" 5 ");
					break;
				case 4:
					rowNum = new Text(" 4 ");
					break;
				case 5:
					rowNum = new Text(" 3 ");
					break;
				case 6:
					rowNum = new Text(" 2 ");
					break;
				default:
					rowNum = new Text(" ");
	    	}
	    	
	    	squareStack.getChildren().addAll(
	    			square, new Text(),
	    			rowNum, chessPiece);
	    	// Position row number in top left 
	    	// corner of the square
			StackPane.setAlignment(rowNum, Pos.TOP_LEFT);
	    	this.add(squareStack, column, row);
	    }
	    // Square without row/column labels
	    else {
	    	squareStack.getChildren().addAll(
	    			square, new Text(),
	    			new Text(), chessPiece);
	    	// Add the square to the grid
	    	this.add(squareStack, column, row);
	    }
	    
	    // Add css classes
	    chessPiece.getStyleClass().add("chess-pieces");
	    colLet.getStyleClass().add("square-coords");
		rowNum.getStyleClass().add("square-coords");
	}
	
	/**
	 * Sets the icon to the piece being dragged.
	 *
	 * @param db Dragboard contain what is being dragged
	 * @param piece Text of the piece being dragged
	 */
	private void setDragIcon(Dragboard db, Text piece) {
		 PieceIcon icn = new PieceIcon();
         // Set drag icon to corresponding Black piece
         if(BoardGUI.getTurn() == 1) {
         	if(piece.getText().equals(ChessPiece.PAWN)) {
         		db.setDragView(icn.getBP());
         	}
         	else if(piece.getText().equals(ChessPiece.BISHOP)) {
         		db.setDragView(icn.getBB());
         	}
         	else if(piece.getText().equals(ChessPiece.KNIGHT)) {
         		db.setDragView(icn.getBN());
         	}
         	else if(piece.getText().equals(ChessPiece.ROOK)) {
         		db.setDragView(icn.getBR());
         	}
         	else if(piece.getText().equals(ChessPiece.QUEEN)) {
         		db.setDragView(icn.getBQ());
         	}
         	else if(piece.getText().equals(ChessPiece.KING)) {
         		db.setDragView(icn.getBK());
         	}
         }
         // Set drag icon to corresponding White piece
         else {
         	if(piece.getText().equals(ChessPiece.PAWN)) {
         		db.setDragView(icn.getWP());
         	}
         	else if(piece.getText().equals(ChessPiece.BISHOP)) {
         		db.setDragView(icn.getWB());
         	}
         	else if(piece.getText().equals(ChessPiece.KNIGHT)) {
         		db.setDragView(icn.getWN());
         	}
         	else if(piece.getText().equals(ChessPiece.ROOK)) {
         		db.setDragView(icn.getWR());
         	}
         	else if(piece.getText().equals(ChessPiece.QUEEN)) {
         		db.setDragView(icn.getWQ());
         	}
         	else if(piece.getText().equals(ChessPiece.KING)) {
         		db.setDragView(icn.getWK());
         	}
         }
	}
	
	/**
	 * Resets the piece back to its original square
	 * if dropped outside of the board.
	 */
	private void checkDropOutside() {
		// Piece dragged above the board
		topScreen.setOnDragOver((DragEvent event) -> {
	    	event.acceptTransferModes(TransferMode.ANY);
        });
		// Piece dropped above the board
	    topScreen.setOnDragDropped((DragEvent event) -> {
	    	resetPiece(event);
        });
	    
	    // Piece dragged to the left of the board
	    gameInfo.setOnDragOver((DragEvent event) -> {
	    	event.acceptTransferModes(TransferMode.ANY);
        });
	    // Piece dropped to the left of the board
	    gameInfo.setOnDragDropped((DragEvent event) -> {
	    	resetPiece(event);
        });
	    
	    // Piece dragged to the right of the board
	    movesSection.setOnDragOver((DragEvent event) -> {
	    	event.acceptTransferModes(TransferMode.ANY);
        });
	    // Piece dropped to the right of the board
	    movesSection.setOnDragDropped((DragEvent event) -> {
	    	resetPiece(event);
        });

	    // Piece dragged below the board
	    bottomScreen.setOnDragOver((DragEvent event) -> {
	    	event.acceptTransferModes(TransferMode.ANY);
        });
	    // Piece dropped below the board
	    bottomScreen.setOnDragDropped((DragEvent event) -> {
	    	resetPiece(event);
        });
	}
}
