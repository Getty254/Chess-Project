package chess;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ChessBoard extends GridPane {
	
	private BorderPane topScreen; 
	private BorderPane bottomScreen;
	private BorderPane movesSection;
	private VBox gameInfo;
	private Clocks clocks;
	private MovesList movesList;
	private GameLogic logic;
	private Label triggerGameOver;
	
	public ChessBoard(BorderPane topScreen, 
			BorderPane bottomScreen, BorderPane movesSection, 
			VBox gameInfo, Clocks clocks, MovesList movesList,
			GameLogic logic, Label triggerGameOver) {
		this.topScreen = topScreen;
		this.bottomScreen = bottomScreen;
		this.movesSection = movesSection;
		this.gameInfo = gameInfo;
		this.clocks = clocks;
		this.movesList = movesList;
		this.logic = logic;
		this.triggerGameOver = triggerGameOver;
		
		createBoard();
	}
	
	public void createBoard() {		
		
		// Remove all squares and pieces to create new ones
		this.getChildren().clear();
		
		// Add css class
    	this.getStyleClass().add("chess-board");
    	
		
		int numberOfSquares = 0;
		double squareDimensions = 100;
		boolean[] exitedWindow = new boolean[] {false};
		
		// Create 64 rectangles (squares) and add them to the grid
		for (int row = 0; row < 8; row++) {
			numberOfSquares++;
			for (int column = 0; column < 8; column++) {
				Rectangle square = new Rectangle(squareDimensions, 
				    							squareDimensions, 
				    							squareDimensions, 
				    							squareDimensions);
				
				// Alternate board colors
			    if (numberOfSquares % 2 != 0) {
			    	// Light brown
			    	square.setFill(BoardColors.colorOne);
			    }
			    else {
			    	// Dark brown
			    	square.setFill(BoardColors.colorTwo);
			    }
			    
			    StackPane squareStack = new StackPane(); 
			    Text chessPiece = new Text();

			    // Setup pieces in starting positions

			    // Black pieces
			    if(row == 0) {
			    	// Black Rooks in top corners
			    	if(column == 0 || column == 7) {
			    		chessPiece = new Text(ChessPiece.ROOK);
			    	}
			    	// Black Knights
			    	else if(column == 1 || column == 6) {
			    		chessPiece = new Text(ChessPiece.KNIGHT);
			    	}
			    	// Black Bishops
			    	else if(column == 2 || column == 5) {
			    		chessPiece = new Text(ChessPiece.BISHOP);
			    	}
			    	// Black Queen
			    	else if(column == 3) {
			    		chessPiece = new Text(ChessPiece.QUEEN);
			    	}
			    	else {
			    		chessPiece = new Text(ChessPiece.KING);
			    	}
			    }
			    // White pieces
			    else if(row == 7) {
			    	// White Rooks in top corners
			    	if(column == 0 || column == 7) {
			    		chessPiece = new Text(ChessPiece.ROOK);
			    	}
			    	// White Knights
			    	else if(column == 1 || column == 6) {
			    		chessPiece = new Text(ChessPiece.KNIGHT);
			    	}
			    	// White Bishops
			    	else if(column == 2 || column == 5) {
			    		chessPiece = new Text(ChessPiece.BISHOP);
			    	}
			    	// White Queen
			    	else if(column == 3) {
			    		chessPiece = new Text(ChessPiece.QUEEN);
			    	}
			    	else {
			    		chessPiece = new Text(ChessPiece.KING);
			    	}
			    	chessPiece.setFill(Color.rgb(250, 249, 246));
			    }
			    // Black Pawns
			    else if(row == 1) {
			    	chessPiece = new Text(ChessPiece.PAWN);
			    }
			    // White Pawns
			    else if(row == 6) {
			    	chessPiece = new Text(ChessPiece.PAWN);
			    	chessPiece.setFill(Color.rgb(250, 249, 246));
			    }
			    
			    
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
			    	
			    	squareStack.getChildren().addAll(square, new Text(), rowNum, chessPiece);
			    	// Position row number in top left 
			    	// corner of the square
					StackPane.setAlignment(rowNum, Pos.TOP_LEFT);
			    	this.add(squareStack, column, row);
			    }
			    else {
			    	squareStack.getChildren().addAll(square, new Text(), new Text(), chessPiece);
			    	// Add the square to the grid
			    	this.add(squareStack, column, row);
			    }
			    
			    
			    squareStack.setOnDragDetected((MouseEvent event) -> {	
			    	if(!BoardGUI.isGameOver) {
				    	exitedWindow[0] = false;
				    	// Piece being dragged
				    	Text pieceText = (Text)
				    			squareStack.getChildren().get(3);
	
				    	// Do not allow squares with
				    	// no pieces to be dragged
				    	if(pieceText.getText().equals("")) {
				    		event.consume();
				    	}
				    	// Square has a piece, so it can be dragged
				    	else {
				    		Dragboard db = squareStack.startDragAndDrop(TransferMode.MOVE);
				    					    		
				    		// if player trys to pick up 
				    		// opponents pieces cancel the drag
				    		if((BoardGUI.turn == 1
				    			&& pieceText.getFill().equals(
				    					Color.rgb(250, 249, 246)))
				    			|| (BoardGUI.turn == 0 
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
					            
					            // Set drag icon to corresponding Black piece
					            if(BoardGUI.turn == 1) {
					            	if(pieceText.getText().equals(ChessPiece.PAWN)) {
					            		db.setDragView(new Image(
					            			"PieceImages/BlackPawn.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.BISHOP)) {
					            		db.setDragView(new Image(
					            			"PieceImages/BlackBishop.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.KNIGHT)) {
					            		db.setDragView(new Image(
					            			"PieceImages/BlackKnight.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.ROOK)) {
					            		db.setDragView(new Image(
					            			"PieceImages/BlackRook.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.QUEEN)) {
					            		db.setDragView(new Image(
					            			"PieceImages/BlackQueen.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.KING)) {
					            		db.setDragView(new Image(
					            				"PieceImages/BlackKing.png",
					            				80, 80, true, true));
					            	}
					            }
					            // Set drag icon to corresponding White piece
					            else {
					            	if(pieceText.getText().equals(ChessPiece.PAWN)) {
					            		db.setDragView(new Image(
					            			"PieceImages/WhitePawn.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.BISHOP)) {
					            		db.setDragView(new Image(
					            			"PieceImages/WhiteBishop.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.KNIGHT)) {
					            		db.setDragView(new Image(
					            			"PieceImages/WhiteKnight.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.ROOK)) {
					            		db.setDragView(new Image(
					            			"PieceImages/WhiteRook.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.QUEEN)) {
					            		db.setDragView(new Image(
					            			"PieceImages/WhiteQueen.png",
					            			80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(ChessPiece.KING)) {
					            		db.setDragView(new Image(
					            			"PieceImages/WhiteKing.png",
					            			80, 80, true, true));
					            	}
					            }
				    		}
				    	}
			    	}
			    });
			    
			    squareStack.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });

			    squareStack.setOnDragDropped((DragEvent event) -> {
		            Dragboard db = event.getDragboard();
		            
		            if(!exitedWindow[0] 
		            	&& event.getGestureSource() instanceof StackPane) {
			            if (db.hasString()) {
			            	// Get the square the piece
			            	// started on before the move
				            StackPane squareFrom = (StackPane) 
				            		event.getGestureSource();
				            
				            // Get the piece being dropped
			                Text chessPieceDropped = new Text(db.getString());
				            
				            // Get column matrix numbers
				            int colFrom = GridPane.getColumnIndex(squareFrom);
				            int colTo = GridPane.getColumnIndex(squareStack);
				            
				            // Get row matrix numbers
				            int rowFrom = GridPane.getRowIndex(squareFrom);
				            int rowTo = GridPane.getRowIndex(squareStack);
				            
				            // Convert column number to letter a - h
				            char colLetterFrom = (char) (97 + colFrom);
				            char colLetterTo = (char) (97 + colTo);
				            
				            
				            String pieceLetter;
				            
				            if(db.getString().equals(ChessPiece.BISHOP)) {
				            	pieceLetter = "B";
				            }
				            else if(db.getString().equals(ChessPiece.KNIGHT)) {
				            	pieceLetter = "N";
				            }
				            else if(db.getString().equals(ChessPiece.ROOK)) {
				            	pieceLetter = "R";
				            }
				            else if(db.getString().equals(ChessPiece.QUEEN)) {
				            	pieceLetter = "Q";
				            }
				            else if(db.getString().equals(ChessPiece.KING)) {
				            	pieceLetter = "K";
				            }
				            else {
				            	pieceLetter = "";
				            }
				            
				            String fullMove;
				            
				            boolean isCastle = false;
				            boolean isCastleShort = false;
				            boolean isPawnPromotion = false;
				            
				            // Check if move is a Pawn Promotion
				            if(db.getString().equals(ChessPiece.PAWN) 
				            		&& ((BoardGUI.turn == 0 && rowTo == 0) 
				            		|| (BoardGUI.turn == 1 && rowTo == 7))) {
				            	
				            	isPawnPromotion = true;
				            	
				            	// Pick Promotion piece here
				            	chessPieceDropped = new Text(ChessPiece.QUEEN);
				            	
				            	// TODO: allow promotions for other piece
				            	fullMove = pieceLetter + colLetterFrom 
				    	    				+ (8 - rowFrom) 
				    	    				+ colLetterTo + (8 - rowTo)
				    	    				+ "Q";
				            }
				            // Pawn move, but not a promotion
				            else if(db.getString().equals(ChessPiece.PAWN)) {
				            	fullMove = "" + colLetterFrom 
			    	    				+ (8 - rowFrom) 
			    	    				+ colLetterTo + (8 - rowTo);
				            }
				            // Check if move is Castle
				            else if(db.getString().equals(ChessPiece.KING) 
				            		&& ((BoardGUI.turn == 0 
				            		&& rowFrom == 7 && rowTo == 7 
				            		&& colFrom == 4 
				            		&& (colTo == 6 || colTo == 2)) 
				            		|| (BoardGUI.turn == 1 
				            		&& rowFrom == 0 && rowTo == 0 
						            && colFrom == 4 
						            && (colTo == 6 || colTo == 2)))) {
				            	
				            	isCastle = true;
				            	
				            	if(colTo == 6) {
				            		isCastleShort = true;
				            		fullMove = "O-O";
				            	}
				            	else {
				            		isCastleShort = false;
				            		fullMove = "O-O-O";
				            	}
				            }
				            // Regular piece move
				            else {
					            fullMove = pieceLetter + colLetterFrom 
			    	    				+ (8 - rowFrom) 
			    	    				+ colLetterTo + (8 - rowTo);
				            }
			            	
				            Move move = new Move(fullMove, rowFrom,
				            		colFrom, rowTo, colTo);
				            
				            int moveStatus = logic.isMoveLegal(move);
				            
		    	    		if(moveStatus == -1) {
		    	    			resetPiece(event);
				            	event.setDropCompleted(false);
		    	    		}
		    	    		else {

				                // Add css class
				                chessPieceDropped.getStyleClass().add("chess-pieces");
				                
				                // Set piece color to white
				                // if it's white's BoardGUI.turn
				                if(BoardGUI.turn == 0) {
				                	chessPieceDropped.setFill(
				                			Color.rgb(250, 249, 246));
				                }
				                squareStack.getChildren().remove(3);
				                // Place the piece
				                squareStack.getChildren().add(chessPieceDropped);
				                
			    	    		
				                // Player wants to castle
					            if(isCastle) {
				                	
						            StackPane rookSquareFrom;
						            StackPane rookSquareTo;
						            
						            // Move Rook with King
					                Text rookText = new Text(ChessPiece.ROOK);
					                // Add css class
					                rookText.getStyleClass().add("chess-pieces");
					                
					                // Blank placeholder for the square
			                		// the rook was on
							    	Text blankText = new Text();
					                
							    	// White's Castles
				                	if(BoardGUI.turn == 0) {
				                		rookText.setFill(
				                			Color.rgb(250, 249, 246));
								    	
								    	// Castle short side
				                		if(isCastleShort) {

				                			// Move Rook from bottom right square
				                			rookSquareFrom = (StackPane)
				                					this.getChildren().get(63);
				                			rookSquareFrom.getChildren().remove(3);
				                			
									    	rookSquareFrom.getChildren().add(blankText);
									    	
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
				                			rookSquareFrom.getChildren().remove(3);
				                			
									    	rookSquareFrom.getChildren().add(blankText);
									    	
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
				                			rookSquareFrom.getChildren().remove(3);
				                			
									    	rookSquareFrom.getChildren().add(blankText);
									    	
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
				                			rookSquareFrom.getChildren().remove(3);
				                			
									    	rookSquareFrom.getChildren().add(blankText);
									    	
									    	// Move Rook to row 1 column d
									    	rookSquareTo = (StackPane)
									    			this.getChildren().get(3);
				                			rookSquareTo.getChildren().remove(3);
				                			rookSquareTo.getChildren().add(rookText);
				                		}
				                	}				    	    		
					            }
					            
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
			    
			    
			    //**************************************
				// Reset Piece When Dropped Oustide Board
				//**************************************
			    
			    topScreen.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });
			    
			    topScreen.setOnDragDropped((DragEvent event) -> {
			    	resetPiece(event);
		        });
			    
			    gameInfo.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });
			    
			    gameInfo.setOnDragDropped((DragEvent event) -> {
			    	resetPiece(event);
		        });
			    
			    movesSection.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });
			    
			    movesSection.setOnDragDropped((DragEvent event) -> {
			    	resetPiece(event);
		        });
			    
			    bottomScreen.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });
			    bottomScreen.setOnDragDropped((DragEvent event) -> {
			    	resetPiece(event);
		        });
			    
			    
			    // Add css class
			    chessPiece.getStyleClass().add("chess-pieces");
			    colLet.getStyleClass().add("square-coords");
				rowNum.getStyleClass().add("square-coords");
			     
			    numberOfSquares++;
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
            if(BoardGUI.turn == 0) {
            	chessPieceDropped.setFill(Color.rgb(250, 249, 246));
            }
            
            squareFrom.getChildren().remove(3);
            squareFrom.getChildren().add(chessPieceDropped);
    	}
    	
        event.consume();
	}
}
