
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;





// How to setup javafx on eclipse
// https://www.youtube.com/watch?v=N6cZcw8_XtM


public class BoardGUI extends Application {
	
	private Timeline timelineP1, timelineP2;
	/**Starting time in seconds for each player's clock*/
	private long timeControl = 600; // 10 minutes in seconds
	/**Time added to player's clock after they make a move*/
	private long timeIncrement = 0;
	private long minP1, minP2, secP1, secP2; 
	private long timerP1 = timeControl, timerP2 = timeControl;
	
	/**Used when rotating the board, true if the board is viewed
	 * from white's POV and false if black's POV*/
	private boolean isWhitesPerspective = true;
	
	/**0 if white's turn, 1 if black's turn*/
	public int turn = 0;
	
	/**Keeps track of the current move number*/
	public int moveNumber = 1;
	
	// Unicode Chess Pieces
	String Pawn = "♟";
	String Knight = "♞";
	String Bishop = "♝";
	String Rook = "♜";
	String Queen = "♛";
	String King = "♚";
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			// Application Title
			primaryStage.setTitle("Chess");
			
			
			//**************************************
			//	Left Column Timers and Buttons
			//**************************************
			
			Label timerLabelP1 = new Label();
			Label timerLabelP2 = new Label();
			
			timerLabelP1.setPrefWidth(125);
			timerLabelP2.setPrefWidth(125);
			
			Button resignButton = new Button("Resign ⚑");
			Button drawButton = new Button("Draw ½");
			
			// Add css class
			resignButton.getStyleClass().add("resign-button");
			drawButton.getStyleClass().add("draw-button");
			
			// Resign and Draw Buttons height and width
			resignButton.setPrefWidth(75);
			drawButton.setPrefWidth(75);
			resignButton.setPrefHeight(35);
			drawButton.setPrefHeight(35);
			
			VBox gameInfo = new VBox();
			// Add css class
			gameInfo.getStyleClass().add("game-info");
			
			HBox endGameButtons = new HBox();
			endGameButtons.getChildren().add(resignButton);
			endGameButtons.getChildren().add(drawButton);
			
			gameInfo.getChildren().add(timerLabelP2);
			gameInfo.getChildren().add(endGameButtons);
			gameInfo.getChildren().add(timerLabelP1);
			
			
			// Spacings for left column
			VBox.setMargin(endGameButtons, new Insets(75,50,75,50));
			HBox.setMargin(resignButton, new Insets(0,25,0,0));

			gameInfo.setAlignment(Pos.CENTER);

			// Add css class
			timerLabelP1.getStyleClass().add("clocks");
			timerLabelP2.getStyleClass().add("clocks");
			
			
			minP1 = TimeUnit.SECONDS.toMinutes(timerP1);
			secP1 = timerP1 - (minP1 * 60);
			
			// Starting clock values
			timerLabelP1.setText(String.format("%02d:%02d", minP1, secP1));
			timerLabelP2.setText(String.format("%02d:%02d", minP1, secP1));
			
			
			timelineP1 = new Timeline(new KeyFrame(Duration.seconds(1), 
					new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
						    if(timerP1 <= 0) {
						    	timelineP1.stop();
						    }
						    minP1 = TimeUnit.SECONDS.toMinutes(timerP1);
						    secP1 = timerP1 - (minP1 * 60);
							
							timerLabelP1.setText(String.format("%02d:%02d", minP1, secP1));
							timerP1--;
						}
			}));
			
			timelineP2 = new Timeline(new KeyFrame(Duration.seconds(1), 
					new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
						    if(timerP2 <= 0) {
						    	timelineP2.stop();
						    }
						    minP2 = TimeUnit.SECONDS.toMinutes(timerP2);
						    secP2 = timerP2 - (minP2 * 60);
							
							timerLabelP2.setText(String.format("%02d:%02d", minP2, secP2));
							timerP2--;
						}
			}));

			// Clocks run until stopped
			timelineP1.setCycleCount(Timeline.INDEFINITE);
			timelineP2.setCycleCount(Timeline.INDEFINITE);
			
			//**************************************
			//		Player Names and Ratings
			//**************************************
			Label playerOneLabel = new Label("Player 1 (1200)");
			Label playerTwoLabel = new Label("Player 2 (1200)");
			
			// Add css class
			playerOneLabel.getStyleClass().add("player-names");
			playerTwoLabel.getStyleClass().add("player-names");
			
			
			// Insets(top, right, bottom, left)
			BorderPane.setMargin(playerOneLabel, new Insets(15,0,20,280));
			BorderPane.setMargin(playerTwoLabel, new Insets(20,20,15,280));
			
			
			//**************************************
			//			Move List
			//**************************************
			
			BorderPane movesSection = new BorderPane();
			
			// Create a GridPane
			// Column 1 is the move number
			// Column 2 is white's move
			// Column 3 is black's move
	    	GridPane movesList = new GridPane();
	    	movesList.getColumnConstraints().add(new ColumnConstraints(30));
	    	movesList.getColumnConstraints().add(new ColumnConstraints(75));
	    	movesList.getColumnConstraints().add(new ColumnConstraints(75));
	    	
	    	// Scroll through moves
	    	ScrollPane scrollPane = new ScrollPane();	    	
	    	scrollPane.setContent(movesList);
	    	// Add css class
	    	scrollPane.getStyleClass().add("scroll-pane");
	    	
	    	// Create 10 move placeholders
	    	for(int row = 0; row < 10; row++) {
	    		for(int column = 0; column < 3; column++) {
	    			if(column == 0) {
	    				Label moveNumLabel = new Label(moveNumber + ".");
	    				// Center move number in grid
	    				GridPane.setHalignment(moveNumLabel, HPos.CENTER);
	    				movesList.add(moveNumLabel, column, row);
	    			}
	    			else if(column == 1) {
	    				Label whiteMove = new Label("Qd1d3");
	    				// Center white's move in grid
	    				GridPane.setHalignment(whiteMove, HPos.CENTER);
	    				movesList.add(whiteMove, column, row);
	    			}
	    			else {
	    				Label blackMove = new Label("Ng1f3");
	    				// Center black's move in grid
	    				GridPane.setHalignment(blackMove, HPos.CENTER);
	    				movesList.add(blackMove, column, row);
	    			}
	    		}
	    		moveNumber++;
	    	}
	    	
	    	// Moves list title/heading
	    	Label movesLabel = new Label("Game Moves");
	    	// Add css id
	    	movesLabel.setId("moves-label");
	    	movesSection.setTop(movesLabel);
	    	// Center Moves Heading
	    	BorderPane.setAlignment(movesLabel, Pos.CENTER);	    	
	    	
	    	// Input box for players to enter their move
	    	TextField inputMove = new TextField();
	    	inputMove.setPromptText("Enter your move here");
	    	Tooltip moveTip = new Tooltip("Use long algebraic notation (e2e4, Ng1f3)");
	    	moveTip.getStyleClass().add("tool-tip");
	    	inputMove.setTooltip(moveTip);
	    	
	    	// Get player's move from textfield
	    	inputMove.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    	    @Override
	    	    public void handle(KeyEvent ke) {
	    	    	// Press enter to execute move
	    	        if (ke.getCode().equals(KeyCode.ENTER)) {
	    	        	updatePlayerClocks(timerLabelP1, timerLabelP2);
	    	        	
	    	        	if(turn == 0) {
		    	        	// Add move to move list
		    	    		Label moveNumLabel = new Label("  " + moveNumber + ".");
		    	    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
		    	    		movesList.add(moveNumLabel, 0, moveNumber);
		    	    		
		    	    		Label whiteMove = new Label(inputMove.getText());
		    				GridPane.setHalignment(whiteMove, HPos.CENTER);
		    				movesList.add(whiteMove, 1, moveNumber);
	    	        	}
	    	        	else {
	    	        		Label blackMove = new Label(inputMove.getText());
	    	    			GridPane.setHalignment(blackMove, HPos.CENTER);
	    	    			movesList.add(blackMove, 2, moveNumber);
	    	    			
	    	    			// Only increment move number after Black's turn
	    	    			moveNumber++;
	    	    			// Change to White's turn
	    	        	}
	    	        	
	    	        	turn = (turn==0)?1:0;
	    	        	
	    	        	// Clear input field
	    	    		inputMove.clear();
	    	        }
	    	    }
	    	});
	    	
	    	BorderPane.setMargin(scrollPane, new Insets(10,10,100,10));
	    	BorderPane.setMargin(inputMove, new Insets(0,30,100,30));
	    	movesSection.setCenter(scrollPane);
	    	movesSection.setBottom(inputMove);
	    	// Add css class
	    	movesSection.getStyleClass().add("moves-section");
	    	
	    	
			//**************************************
			//		Create the board
			//**************************************
			
			// Create a GridPane
	    	GridPane chessBoard = new GridPane();
	    	// Add css class
	    	chessBoard.getStyleClass().add("chess-board");
	    	
			
			int numberOfSquares = 0;
			double squareDimensions = 100;
			
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
				    	square.setFill(Color.rgb(222,184,135));
				    }
				    else {
				    	// Dark brown
				    	square.setFill(Color.rgb(139, 69, 19));
				    }
				    
				    StackPane squareStack = new StackPane(); 
				    Text chessPiece = new Text();

				    // Setup pieces in starting positions

				    // Black pieces
				    if(row == 0) {
				    	// Black Rooks in top corners
				    	if(column == 0 || column == 7) {
				    		chessPiece = new Text(Rook);
				    	}
				    	// Black Knights
				    	else if(column == 1 || column == 6) {
				    		chessPiece = new Text(Knight);
				    	}
				    	// Black Bishops
				    	else if(column == 2 || column == 5) {
				    		chessPiece = new Text(Bishop);
				    	}
				    	// Black Queen
				    	else if(column == 3) {
				    		chessPiece = new Text(Queen);
				    	}
				    	else {
				    		chessPiece = new Text(King);
				    	}
				    }
				    // White pieces
				    else if(row == 7) {
				    	// White Rooks in top corners
				    	if(column == 0 || column == 7) {
				    		chessPiece = new Text(Rook);
				    	}
				    	// White Knights
				    	else if(column == 1 || column == 6) {
				    		chessPiece = new Text(Knight);
				    	}
				    	// White Bishops
				    	else if(column == 2 || column == 5) {
				    		chessPiece = new Text(Bishop);
				    	}
				    	// White Queen
				    	else if(column == 3) {
				    		chessPiece = new Text(Queen);
				    	}
				    	else {
				    		chessPiece = new Text(King);
				    	}
				    	chessPiece.setFill(Color.rgb(250, 249, 246));
				    }
				    // Black Pawns
				    else if(row == 1) {
				    	chessPiece = new Text(Pawn);
				    }
				    // White Pawns
				    else if(row == 6) {
				    	chessPiece = new Text(Pawn);
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
				    	chessBoard.add(squareStack, column, row);
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
				    	chessBoard.add(squareStack, column, row);
				    }
				    else {
				    	squareStack.getChildren().addAll(square, new Text(), new Text(), chessPiece);
				    	// Add the square to the grid
				    	chessBoard.add(squareStack, column, row);
				    }
				    
				    
				    
				    squareStack.setOnDragDetected((MouseEvent event) -> {				    	
				    	// Piece being dragged
				    	Text pieceText = (Text) squareStack.getChildren().get(3);
				    	
				    	// Do not allow squares with no pieces to be dragged
				    	if(pieceText.getText().equals("")) {
				    		event.consume();
				    	}
				    	// Square has a piece, so it can be dragged
				    	else {
				    		Dragboard db = squareStack.startDragAndDrop(TransferMode.MOVE);
				    					    		
				    		// if player trys to pick up opponents pieces
				    		// cancel the drag
				    		if((turn == 1 && pieceText.getFill().equals(Color.rgb(250, 249, 246)))
				    				|| (turn == 0 && pieceText.getFill().equals(Color.rgb(0, 0, 0)))) {
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
					            if(turn == 1) {
					            	if(pieceText.getText().equals(Pawn)) {
					            		db.setDragView(new Image("PieceImages/BlackPawn.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Bishop)) {
					            		db.setDragView(new Image("PieceImages/BlackBishop.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Knight)) {
					            		db.setDragView(new Image("PieceImages/BlackKnight.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Rook)) {
					            		db.setDragView(new Image("PieceImages/BlackRook.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Queen)) {
					            		db.setDragView(new Image("PieceImages/BlackQueen.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(King)) {
					            		db.setDragView(new Image("PieceImages/BlackKing.png", 80, 80, true, true));
					            	}
					            }
					            // Set drag icon to corresponding White piece
					            else {
					            	if(pieceText.getText().equals(Pawn)) {
					            		db.setDragView(new Image("PieceImages/WhitePawn.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Bishop)) {
					            		db.setDragView(new Image("PieceImages/WhiteBishop.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Knight)) {
					            		db.setDragView(new Image("PieceImages/WhiteKnight.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Rook)) {
					            		db.setDragView(new Image("PieceImages/WhiteRook.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(Queen)) {
					            		db.setDragView(new Image("PieceImages/WhiteQueen.png", 80, 80, true, true));
					            	}
					            	else if(pieceText.getText().equals(King)) {
					            		db.setDragView(new Image("PieceImages/WhiteKing.png", 80, 80, true, true));
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
			            
			            if (db.hasString()) {
			            	// Get the square the piece started on before the move
				            StackPane squareFrom = (StackPane) event.getGestureSource();
				            
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
				            
				            
				            boolean isCastle = false;
				            boolean isCastleShort = false;
				            boolean isPawnPromotion = false;
				            
				            // Check if move is a Pawn Promotion
				            if(db.getString().equals(Pawn) 
				            		&& ((turn == 0 && rowTo == 0) 
				            		|| (turn == 1 && rowTo == 7))) {
				            	
				            	isPawnPromotion = true;
				            	
				            	// Pick Promotion piece here
				            	chessPieceDropped = new Text(Queen);
				            }
				            // Check if move is Castle
				            else if(db.getString().equals(King) 
				            		&& ((turn == 0 
				            		&& rowFrom == 7 && rowTo == 7 
				            		&& colFrom == 4 && (colTo == 6 || colTo == 2)) 
				            		|| (turn == 1 
				            		&& rowFrom == 0 && rowTo == 0 
						            && colFrom == 4 && (colTo == 6 || colTo == 2)))) {
				            	
				            	isCastle = true;
				            	
				            	if(colTo == 6) {
				            		isCastleShort = true;
				            	}
				            	else {
				            		isCastleShort = false;
				            	}
				            }
			                
			                // Add css class
			                chessPieceDropped.getStyleClass().add("chess-pieces");
			                
			                // Set piece color to white if it's white's turn
			                if(turn == 0) {
			                	chessPieceDropped.setFill(Color.rgb(250, 249, 246));
			                }
			                squareStack.getChildren().remove(3);
			                // Place the piece
			                squareStack.getChildren().add(chessPieceDropped);
			                
			                
			                // Move that will be displayed in the moves list
		    	    		Label fullMove = new Label();
		    	    		
		    	    		// Center the move label
		    	    		GridPane.setHalignment(fullMove, HPos.CENTER);
		    	    		
		    	    		// Check if move is a Pawn Promotion
				            if(isPawnPromotion) {
				            	
				            	updatePlayerClocks(timerLabelP1, timerLabelP2);
				            	
				            	// Move that will be displayed in the moves list
			    	    		fullMove.setText(colLetterFrom + "" + (8 - rowFrom) + 
			    	    				colLetterTo + "" + (8 - rowTo) + "Q"); // Promo Piece letter at end
			    	    		
			    	    		if(turn == 0) {
				    	    		// Add move number to move list
				    	    		Label moveNumLabel = new Label("  " + moveNumber + ".");
				    	    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
				    	    		movesList.add(moveNumLabel, 0, moveNumber);
				    	    		
				    	    		// Add move to move list
				    				movesList.add(fullMove, 1, moveNumber);
			    	    		}
			    	    		else {
			    	    			// Add move to move list
				    				movesList.add(fullMove, 2, moveNumber);
				    				
				    				// Only increment move number after Black's turn
			    	    			moveNumber++;
			    	    		}
		                		
			    	    		// Change whose turn it is
					            turn = (turn==0)?1:0;
				            }
			                // Player wants to castle
				            else if(isCastle) {
			                	updatePlayerClocks(timerLabelP1, timerLabelP2);
			                	
					            StackPane rookSquareFrom;
					            StackPane rookSquareTo;
					            
					            // Move Rook with King
				                Text rookText = new Text(Rook);
				                // Add css class
				                rookText.getStyleClass().add("chess-pieces");
				                
				                // Blank placeholder for the square
		                		// the rook was on
						    	Text blankText = new Text();
				                
						    	// White's Castles
			                	if(turn == 0) {
			                		rookText.setFill(Color.rgb(250, 249, 246));
							    	
							    	// Castle short side
			                		if(isCastleShort) {
			                			// Castle short notation
			                			fullMove.setText("O-O");
			                			
			                			// Move Rook from bottom right square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(63);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Rook to row 8 column f
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(61);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                		// Castle long side
			                		else {
			                			// Castle long notation
			                			fullMove.setText("O-O-O");
			                			
			                			// Move Rook from bottom left square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(56);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Rook to row 8 column d
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(59);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                		
			                		// Add move number to move list
				    	    		Label moveNumLabel = new Label("  " + moveNumber + ".");
				    	    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
				    	    		movesList.add(moveNumLabel, 0, moveNumber);
				    	    		
			                		// Add move to move list
				    				movesList.add(fullMove, 1, moveNumber);
			                	}
			                	// Black Castles
			                	else {
							    	// Castle short side
			                		if(isCastleShort) {
			                			// Castle short notation
			                			fullMove.setText("O-O");
			                			
			                			// Move Rook from top right square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(7);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Rook to row 1 column f
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(5);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                		// Castle long side
			                		else {
			                			// Castle long notation
			                			fullMove.setText("O-O-O");
			                			
			                			// Move Rook from top left square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(0);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Rook to row 1 column d
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(3);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
				    	    		
			                		// Add move to move list
				    				movesList.add(fullMove, 2, moveNumber);
				    				
				    				// Only increment move number after Black's turn
			    	    			moveNumber++;
			                	}
			                	
			                	// Change whose turn it is
					            turn = (turn==0)?1:0;
			                }
				            // if the piece does NOT get dropped on the 
				            // square it was moved from
			                else if(colFrom != colTo || rowFrom != rowTo) {
				            	updatePlayerClocks(timerLabelP1, timerLabelP2);
				            
				            	String pieceLetter;
					            
					            if(db.getString().equals(Bishop)) {
					            	pieceLetter = "B";
					            }
					            else if(db.getString().equals(Knight)) {
					            	pieceLetter = "N";
					            }
					            else if(db.getString().equals(Rook)) {
					            	pieceLetter = "R";
					            }
					            else if(db.getString().equals(Queen)) {
					            	pieceLetter = "Q";
					            }
					            else if(db.getString().equals(King)) {
					            	pieceLetter = "K";
					            }
					            else {
					            	pieceLetter = "";
					            }
				            	
			    	    		// Move that will be displayed in the moves list
			    	    		fullMove.setText(pieceLetter + colLetterFrom + "" + (8 - rowFrom) + 
			    	    				colLetterTo + "" + (8 - rowTo));
			    	    		
			    	    		// White's turn
					            if(turn == 0) {
				    	        	// Add move number to move list
				    	    		Label moveNumLabel = new Label("  " + moveNumber + ".");
				    	    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
				    	    		movesList.add(moveNumLabel, 0, moveNumber);
				    	    		
				    	    		// Add move to move list
				    				movesList.add(fullMove, 1, moveNumber);
			    	        	}
					            // Black's Turn
			    	        	else {
			    	        		// Add move to move list
			    	    			movesList.add(fullMove, 2, moveNumber);
			    	    			
			    	    			// Only increment move number after Black's turn
			    	    			moveNumber++;	
			    	        	}
					            
					            // Change whose turn it is
					            turn = (turn==0)?1:0;
				            }
				            
				            event.setDropCompleted(true);
			            } 
			            else {
			                event.setDropCompleted(false);
			            }
			            
			            event.consume();
			        });
				    
				    squareStack.setOnDragDone((DragEvent event) -> {
			            event.consume();
			        });
				    
				    chessBoard.setOnDragExited((DragEvent event) -> {
				    	System.out.println("Outside board");
			            event.consume();
			        });
				    
				    // Add css class
				    chessPiece.getStyleClass().add("chess-pieces");
				    colLet.getStyleClass().add("square-coords");
					rowNum.getStyleClass().add("square-coords");
				     
				    numberOfSquares++;
				}
			}
			
			
			Button flipBoard = new Button("↑↓");
			
			flipBoard.setPrefWidth(50);
			flipBoard.setPrefHeight(50);
			
			gameInfo.getChildren().add(flipBoard);
			
			
			flipBoard.setOnAction((event) -> { 
				if(isWhitesPerspective) {
					chessBoard.setRotate(180);
					
					// Loop through each square of the board
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							// Flip pieces so they are not upside down
							chessBoard.getChildren().get(i*8+j).setRotate(180);
							
							// Bottom row of the board when viewed before the flip
							if(i == 7) {
								StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
								Text colLet = (Text) stack.getChildren().get(1);
								// Align column letters to the top left of the square
								StackPane.setAlignment(colLet, Pos.TOP_LEFT);
							}
							// Leftmost column of the board when viewed before the flip
							if(j == 0) {
								StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
								Text rowNum = (Text) stack.getChildren().get(2);
								// Align row numbers to the bottom right of the square
								StackPane.setAlignment(rowNum, Pos.BOTTOM_RIGHT);
							}
							
						}
					}
					
					isWhitesPerspective = false;
				}
				else {
					chessBoard.setRotate(0);
					
					// Loop through each square of the board
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							// Flip pieces so they are not upside down
							chessBoard.getChildren().get(i*8+j).setRotate(0);
							
							// Top row of the board when viewed before the flip
							if(i == 7) {
								StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
								Text colLet = (Text) stack.getChildren().get(1);
								// Align column letters to the top left of the square
								StackPane.setAlignment(colLet, Pos.BOTTOM_RIGHT);
							}
							// Leftmost column of the board when viewed before the flip
							if(j == 0) {
								StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
								Text rowNum = (Text) stack.getChildren().get(2);
								// Align row numbers to the bottom right of the square
								StackPane.setAlignment(rowNum, Pos.TOP_LEFT);
							}
							
						}
					}
					
					isWhitesPerspective = true;
				}
				
			});
			
			root.setLeft(gameInfo);
			root.setBottom(playerOneLabel);
			root.setTop(playerTwoLabel);
			root.setRight(movesSection);
			
			// Place chess board in center of app
			root.setCenter(chessBoard);
			// Add css class
			root.getStyleClass().add("root");
			
			
			Scene scene = new Scene(root);
			// Use css file to style elements
			scene.getStylesheets().add("stylesheet.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	private void updatePlayerClocks(Label timerLabelP1, Label timerLabelP2) {
		// White's turn
    	if(turn == 0) {
    		// Stop player one's timer
    		timelineP1.stop();
    		// Start player two's timer
    		timelineP2.play();
    		
    		// if there is increment update clock
    		if(timeIncrement > 0) {
        		// Add time to player one's clock
        		timerP1 += timeIncrement;
        		
        		minP1 = TimeUnit.SECONDS.toMinutes(timerP1);
			    secP1 = timerP1 - (minP1 * 60);
			    // Update clock label
				timerLabelP1.setText(String.format("%02d:%02d", minP1, secP1));
    		}
    	}
    	else {
    		// Stop player two's timer
    		timelineP2.stop();
    		// Start player one's timer
    		timelineP1.play();
    		
    		// if there is increment update clock
    		if(timeIncrement > 0) {
        		// Add time to player two's clock
        		timerP2 += timeIncrement;
        		
        		minP2 = TimeUnit.SECONDS.toMinutes(timerP2);
			    secP2 = timerP2 - (minP2 * 60);
			    // Update clock label
				timerLabelP2.setText(String.format("%02d:%02d", minP2, secP2));
    		}	
    	}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}