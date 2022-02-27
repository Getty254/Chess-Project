package board;

import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
import javafx.geometry.Rectangle2D;
import java.util.regex.Pattern;

// How to setup javafx on eclipse
// https://www.youtube.com/watch?v=N6cZcw8_XtM


public class BoardGUI extends Application {
	
	private Timeline timelineP1, timelineP2;
	/** Starting time in seconds for each player's clock.*/
	private long timeControl = 5; // 10 minutes in seconds
	/** Time added to player's clock after they make a move.*/
	private long timeIncrement = 0;
	private long minP1, minP2, secP1, secP2; 
	private long timerP1 = timeControl, timerP2 = timeControl;
	
	/** Used when rotating the board, true if the board is viewed
	 * from white's POV and false if black's POV.*/
	private boolean isWhitesPerspective = true;
	/** True if the game is finished, false if game is still going*/
	private boolean isGameOver = false;
	
	/** 0 if white's turn, 1 if black's turn.*/
	public int turn = 0;
	
	/** Keeps track of the current move number.*/
	public int moveNumber = 1;
	
	// Unicode Chess Pieces
	String Pawn = "♟";
	String Knight = "♞";
	String Bishop = "♝";
	String Rook = "♜";
	String Queen = "♛";
	String King = "♚";
	
	
	/** GridPane that holds all the squares and 
	 * pieces of the chess board.
	 */
	private GridPane chessBoard = new GridPane();
	/** Top section of the main screen that holds
	 * the settings button and player two name.
	 */
	private BorderPane topScreen = new BorderPane();
	/** Bottom section of the main screen that holds
	 * the player two name.
	 */
	private BorderPane bottomScreen = new BorderPane();
	/** Label that shows the time left on 
	 * player one's clock.
	 */
	private Label timerLabelP1 = new Label();
	/** Label that shows the time left on 
	 * player two's clock.
	 */
	private Label timerLabelP2 = new Label();
	/** Button for players to resign the game.*/
	private Button resignButton = new Button("Resign ⚑");
	/** Button for players to offer a draw to their opponent.*/
	private Button drawButton = new Button("Draw ½");
	/** Left section of the main screen that holds
	 * the clock labels and resign/draw buttons.
	 */
	private VBox gameInfo = new VBox();
	/** Layout that holds the resign/draw buttons.*/
	private HBox endGameButtons = new HBox();
	/** Label that shows the name for player one.*/
	private Label playerOneLabel = new Label("Player 1 (1200)");
	/** Label that shows the name for player two.*/
	private Label playerTwoLabel = new Label("Player 2 (1200)");
	/** Right section of the main screen that holds
	 * the moves list, textfield, and flip board button.
	 */
	private BorderPane movesSection = new BorderPane();
	/** GridPane that holds the list of moves.
	 * The first column contains the move number.
	 * The second column contains white's move.
	 * The third column contains black's move.
	 */
	private GridPane movesList = new GridPane();
	/** Label headings to indicate the list of moves section.*/
	private Label movesLabel = new Label("Game Moves");
	/** Layout that holds the textfield and flip board button.*/
	private VBox inputAndFlip = new VBox();
	/** Textfield where players can type in their moves.*/
	private TextField inputMove = new TextField();
	/** Button for players flip the board perspective.*/
	private Button flipBoard = new Button("↑↓");

	public Tile getTile(final int tileCoordinate ){
		return null;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			// Application Title
			primaryStage.setTitle("Chess");
			
			//**************************************
			//	Left Column Timers and Buttons
			//**************************************
			
			timerLabelP1.setPrefWidth(125);
			timerLabelP2.setPrefWidth(125);
			
			// Add css class
			resignButton.getStyleClass().add("resign-button");
			drawButton.getStyleClass().add("draw-button");
			
			// Resign and Draw Buttons height and width
			resignButton.setPrefWidth(75);
			drawButton.setPrefWidth(75);
			resignButton.setPrefHeight(35);
			drawButton.setPrefHeight(35);
			
			// Add css class
			gameInfo.getStyleClass().add("game-info");
			
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
			
			initializeClocks();
			
			//**************************************
			//		Player Names and Ratings
			//**************************************
			
			// Add css class
			playerOneLabel.getStyleClass().add("player-names");
			playerTwoLabel.getStyleClass().add("player-names");
			
			topScreen.setCenter(playerTwoLabel);
			BorderPane.setAlignment(playerTwoLabel, Pos.CENTER_LEFT);
			
			bottomScreen.setCenter(playerOneLabel);
			BorderPane.setAlignment(playerOneLabel, Pos.CENTER_LEFT);
			
			
			//**************************************
			//			Move List
			//**************************************
			
			
			// Add boundaries to the columns of the 
			// moves list GridPane
	    	movesList.getColumnConstraints().add(new ColumnConstraints(30));
	    	movesList.getColumnConstraints().add(new ColumnConstraints(75));
	    	movesList.getColumnConstraints().add(new ColumnConstraints(75));
	    	
	    	// Allows players to scroll through the 
	    	// moves list if it goes down too far
	    	ScrollPane scrollPane = new ScrollPane();

	    	// Adds the moves list to the ScrollPane
	    	scrollPane.setContent(movesList);
	    	// Add css class
	    	scrollPane.getStyleClass().add("scroll-pane");
	    		    	
	    	// Add css id
	    	movesLabel.setId("moves-label");
	    	movesSection.setTop(movesLabel);
	    	
	    	// Center Moves Heading
	    	BorderPane.setAlignment(movesLabel, Pos.CENTER);	    	
	    		    	
	    	// Input box for players to enter their move
	    	inputMove.setPromptText("Enter your move here");
	    	Tooltip moveTip = new Tooltip("Use long algebraic notation (e2e4, Ng1f3)");
	    	moveTip.getStyleClass().add("tool-tip");
	    	inputMove.setTooltip(moveTip);
	    	
	    	inputAndFlip.getChildren().add(inputMove);
	    	
	    	BorderPane.setMargin(scrollPane, new Insets(10,10,100,10));
	    	VBox.setMargin(inputMove, new Insets(0,30,80,30));
	    	
	    	movesSection.setCenter(scrollPane);
	    	movesSection.setBottom(inputAndFlip);
	    	
	    	// Add css class
	    	movesSection.getStyleClass().add("moves-section");
	    	
	    	
			//**************************************
			//		Create the board
			//**************************************
	    	
	    	setupBoard();
			
	    	
	    	//**************************************
			//		Textfield Input
			//**************************************
	    	
	    	inputMove.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    	    @Override
	    	    public void handle(KeyEvent ke) {
	    	    	// Press enter to execute move
	    	        if (ke.getCode().equals(KeyCode.ENTER)) {
	    	        	
	    	        	// Textfield only works if the game is not over
	    	        	if(!isGameOver) {
		    	        	boolean isValidMove = true;
		    	        	int rowFrom, colFrom, rowTo, colTo;
		    	        	
		    	        	// Verify if input is valid
		    	        	
		    	        	// Pieces.Pawn Move
		    	        	if(Pattern.matches("[a-h][1-8][a-h][1-8]", inputMove.getText())) {
		    	        		System.out.println("Pieces.Pawn Move!");
		    	        		
		    	        		colFrom = inputMove.getText().charAt(0) - 97;
		    	        		rowFrom = 8 - Integer.parseInt(inputMove.getText().substring(1, 2));
		    	        		
		    	        		colTo = inputMove.getText().charAt(2) - 97;
		    	        		rowTo = 8 - Integer.parseInt(inputMove.getText().substring(3, 4));
		    	        			    	        		
		    	        		isValidMove = moveFromInput(rowFrom*8+colFrom, rowTo*8+colTo);
		    	        	}
		    	        	// Pieces.Pawn Promotion
		    	        	else if(Pattern.matches("[a-h][1-8][a-h][1-8][QRNB]", inputMove.getText())) {
		    	        		System.out.println("Pieces.Pawn Promo!");
		    	        		
		    	        		colFrom = inputMove.getText().charAt(0) - 97;
		    	        		rowFrom = 8 - Integer.parseInt(inputMove.getText().substring(1, 2));
		    	        		
		    	        		colTo = inputMove.getText().charAt(2) - 97;
		    	        		rowTo = 8 - Integer.parseInt(inputMove.getText().substring(3, 4));
		    	        		
		    	        		StackPane squareFrom = (StackPane) chessBoard.getChildren().get(rowFrom*8+colFrom);
		    	        		StackPane squareTo = (StackPane) chessBoard.getChildren().get(rowTo*8+colTo);
		    	        		
		    	        		Text promoPiece = (Text) squareFrom.getChildren().get(3);
		    	        		
		    	        		// Prevent player from moving oppopent's pieces
		    	        		if((turn == 0 && promoPiece.getFill().equals(Color.rgb(250, 249, 246)))
		    	        				|| (turn == 1 && promoPiece.getFill().equals(Color.rgb(0, 0, 0)))) {
			    	        		// Get the last letter of the input
			    	        		String promoLetter = inputMove.getText().substring(4, 5);
			    	        		
			    	        		// Promote to Pieces.Queen
			    	        		if(promoLetter.equals("Q")) {
			    	        			promoPiece.setText(Queen);
			    	        		}
			    	        		// Promote to Pieces.Rook
			    	        		else if(promoLetter.equals("R")) {
			    	        			promoPiece.setText(Rook);
			    	        		}
			    	        		// Promote to Pieces.Knight
			    	        		else if(promoLetter.equals("N")) {
			    	        			promoPiece.setText(Knight);
			    	        		}
			    	        		// Promote to Pieces.Bishop
			    	        		else {
			    	        			promoPiece.setText(Bishop);
			    	        		}
			    	        		
			    	        		// Delete the piece from square when picked up
			    	        		squareFrom.getChildren().remove(3);
		
			    	            	// Add a blank placeholder to the square
			    	            	Text blankText = new Text();
			    	            	squareFrom.getChildren().add(blankText);
			    	            	
			    	            	 // Add css class
			    	            	promoPiece.getStyleClass().add("chess-pieces");
			    	                
			    	                // Set piece color to white if it's white's turn
			    	                if(turn == 0) {
			    	                	promoPiece.setFill(Color.rgb(250, 249, 246));
			    	                }
			    	                squareTo.getChildren().remove(3);
			    	                // Place the piece
			    	                squareTo.getChildren().add(promoPiece);
			    	                
			    	                isValidMove = true;
		    	        		}
		    	        		else {
		    	        			isValidMove = false;
		    	        		}
		    	        	}
		    	        	// Castle
		    	        	else if(Pattern.matches("O(-O){1,2}", inputMove.getText())) {
		    	        		System.out.println("Castles!");
		    	        		
		    	        		colFrom = 4;
		    	        		int rookRowFrom, rookColFrom, rookRowTo, rookColTo;
		    	        		
		    	        		boolean isLongCastle = false;
		    	        		if(Pattern.matches("O-O-O", inputMove.getText())) {
		    	        			isLongCastle = true;
		    	        		}
		    	        		
		    	        		
		    	        		if(turn == 0) {
			    	        		rowFrom = 7;
			    	        		rowTo = 7;
			    	        		rookRowFrom = 7;
			    	        		rookRowTo = 7;
			    	        		
			    	        		if(isLongCastle) {
			    	        			colTo = 2;
			    	        			rookColFrom = 0;
			    	        			rookColTo = 3;
			    	        		}
			    	        		else {
			    	        			colTo = 6;
			    	        			rookColFrom = 7;
			    	        			rookColTo = 5;
			    	        		}
		    	        		}
		    	        		else {
		    	        			rowFrom = 0;
		    	        			rowTo = 0;
		    	        			rookRowFrom = 0;
			    	        		rookRowTo = 0;
		    	        			
		    	        			if(isLongCastle) {
			    	        			colTo = 2;
			    	        			rookColFrom = 0;
			    	        			rookColTo = 3;
			    	        		}
			    	        		else {
			    	        			colTo = 6;
			    	        			rookColFrom = 7;
			    	        			rookColTo = 5;
			    	        		}
		    	        		}
		    	        		
		    	        		// Move Pieces.King
		    	        		isValidMove = moveFromInput(rowFrom*8+colFrom, rowTo*8+colTo);
		    	        		
		    	        		// Move Pieces.Rook
		    	        		isValidMove = moveFromInput(rookRowFrom*8+rookColFrom, rookRowTo*8+rookColTo);
		    	        	}
		    	        	// Piece move
		    	        	else if(Pattern.matches("[KQRBN][a-h][1-8][a-h][1-8]", inputMove.getText())) {
		    	        		System.out.println("Piece Move!");
		    	        		
		    	        		colFrom = inputMove.getText().charAt(1) - 97;
		    	        		rowFrom = 8 - Integer.parseInt(inputMove.getText().substring(2, 3));
		    	        		
		    	        		colTo = inputMove.getText().charAt(3) - 97;
		    	        		rowTo = 8 - Integer.parseInt(inputMove.getText().substring(4, 5));
		    	        		
		    	        		isValidMove = moveFromInput(rowFrom*8+colFrom, rowTo*8+colTo);
		    	        	}
		    	        	// Not a valid move
		    	        	else {
		    	        		System.out.println("That's not a move!");
		    	        		isValidMove = false;
		    	        	}
		    	        	
		    	        	
		    	        	if(isValidMove) {
		    	        		updatePlayerClocks();
		    	        		
		    	        		updateMovesList(inputMove.getText());
		    	        	}
	    	        	}
	    	        	
	    	        	// Clear input field
	    	    		inputMove.clear();
	    	        }
	    	    }
	    	});
			
						
			flipBoard.setPrefWidth(40);
			flipBoard.setPrefHeight(40);
			
			inputAndFlip.getChildren().add(flipBoard);
			
			Tooltip flipBoardTip = new Tooltip("Flip the board perspective");
			flipBoardTip.getStyleClass().add("tool-tip");
	    	flipBoard.setTooltip(flipBoardTip);
			
			VBox.setMargin(flipBoard, new Insets(0,30,0,30));
			
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
			
			
			resignButton.setOnAction((event) -> {
				// Button only works if the game is not over
				if(!isGameOver) {
					Alert alertResign = new Alert(AlertType.NONE);
					alertResign.setTitle("Resignation Confirmation");
					alertResign.setContentText("Are you sure you want to resign?");		
					
					ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
					ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
					
					alertResign.getButtonTypes().add(buttonTypeNo);
					alertResign.getButtonTypes().add(buttonTypeYes);
					
					alertResign.showAndWait();
					
					// Player clicks yes
					if(alertResign.getResult() == buttonTypeYes) {
						isGameOver = true;
						gameOverPopup(false);
					}
	
					// TODO: open end of game window if yes
				}
			});
			
			drawButton.setOnAction((event) -> {
				// Button only works if the game is not over
				if(!isGameOver) {
					Alert alertDraw = new Alert(AlertType.NONE);
					alertDraw.setTitle("Draw Offer");
					alertDraw.setContentText("Accept draw offer?");		
					
					ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
					ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
					
					alertDraw.getButtonTypes().add(buttonTypeNo);
					alertDraw.getButtonTypes().add(buttonTypeYes);
					
					alertDraw.showAndWait();
					
					// Player clicks yes
					if(alertDraw.getResult() == buttonTypeYes) {
						isGameOver = true;
						gameOverPopup(true);
					}
					
					// TODO: open end of game window if yes
				}
			});
			
			
			//**************************************
			//			Settings
			//**************************************
			
			MenuItem menuNewGame = new MenuItem("New Game");
	        MenuItem menuBoardColor = new MenuItem("Change Board Colors");
			
			MenuButton settingsButton = new MenuButton("⚙", null, menuNewGame, menuBoardColor);
			
			settingsButton.getStyleClass().add("settings-button");
			settingsButton.setPrefWidth(50);
			settingsButton.setPrefHeight(50);
			
			topScreen.setLeft(settingsButton);			
			
			menuNewGame.setOnAction((event) -> {
				startNewGame(event);
	    	});
			
			
			Stage stageColorChange = new Stage();
			
			menuBoardColor.setOnAction((event) -> {
				// Close the window in case there is already one open
				stageColorChange.hide();
				
				// Get the first and second StackPanes of the board
				StackPane stackOne = (StackPane) chessBoard.getChildren().get(0);
				StackPane stackTwo = (StackPane) chessBoard.getChildren().get(1);
				
				// Get the first and second squares of the board
				Rectangle squareOne = (Rectangle) stackOne.getChildren().get(0);
				Rectangle squareTwo = (Rectangle) stackTwo.getChildren().get(0);
				
				// Set color pickers' starting colors to the colors of 
				// the first two squares
				ColorPicker squareColorOne = new ColorPicker((Color) squareOne.getFill());
				ColorPicker squareColorTwo = new ColorPicker((Color) squareTwo.getFill());
				
				// Button to change colors back to light/dark brown
				Button defaultColors = new Button("Change back to default colors");

				VBox colorLayout = new VBox();
				HBox colorSelectors = new HBox();
				
				colorLayout.setAlignment(Pos.CENTER);
				VBox.setMargin(colorSelectors, new Insets(10,10,10,10));
				HBox.setMargin(squareColorOne, new Insets(0,10,0,0));
				VBox.setMargin(defaultColors, new Insets(0,0,10,0));
				
				colorSelectors.getChildren().addAll(squareColorOne, squareColorTwo);
				colorLayout.getChildren().addAll(colorSelectors, defaultColors);
				
				Scene sceneColorChange = new Scene(colorLayout);
				
				// Color Change Window Title
				stageColorChange.setTitle("Chess Board Colors");
				
				stageColorChange.setScene(sceneColorChange); 
				
				// Position the window appears at on screen 
				stageColorChange.setX(250);
				stageColorChange.setY(150);
				
				// Display Color Change Window
				stageColorChange.show();

				// Close the color change window if it loses focus
				stageColorChange.focusedProperty().addListener((eventColor) -> {
					if(stageColorChange.isFocused()) {
						primaryStage.focusedProperty().addListener((eventPrim) -> {
							if(primaryStage.isFocused()) {
								stageColorChange.hide();
							}
						});
					}
				});
				
				squareColorOne.setOnAction((eventColor) -> {
					int numSquares = 0;
					
					// Loop through each square of the board
					for(int i = 0; i < 8; i++) {
						numSquares++;
						for(int j = 0; j < 8; j++) {
							StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
							Rectangle square = (Rectangle) stack.getChildren().get(0);
							
							// Alternate squares
						    if (numSquares % 2 != 0) {
						    	// Set square color
						    	square.setFill(squareColorOne.getValue());
						    }

						    numSquares++;
						}
					}
				});
				
				squareColorTwo.setOnAction((eventColor) -> {
					int numSquares = 0;
					
					// Loop through each square of the board
					for(int i = 0; i < 8; i++) {
						numSquares++;
						for(int j = 0; j < 8; j++) {
							StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
							Rectangle square = (Rectangle) stack.getChildren().get(0);
							
							// Alternate squares
						    if (numSquares % 2 == 0) {
						    	// Set square color
						    	square.setFill(squareColorTwo.getValue());
						    }

						    numSquares++;
						}
					}
				});
				
				// Change board colors back to light/dark brown
				defaultColors.setOnAction((eventDefault) -> {
					int numSquares = 0;
					// Loop through each square of the board
					for(int i = 0; i < 8; i++) {
						numSquares++;
						for(int j = 0; j < 8; j++) {
							StackPane stack = (StackPane) chessBoard.getChildren().get(i*8+j);
							Rectangle square = (Rectangle) stack.getChildren().get(0);
							
							// Alternate squares
						    if (numSquares % 2 != 0) {
						    	// Light brown
						    	square.setFill(Color.rgb(222,184,135));
						    }
						    else {
						    	// Dark Brown
						    	square.setFill(Color.rgb(139, 69, 19));
						    }

						    numSquares++;
						}
					}
				});
			});
			
			
			// Move player names when window width is adjusted
			primaryStage.widthProperty().addListener((eventResize, oldX, newX) -> {
				BorderPane.setMargin(playerTwoLabel, new Insets(0,0,0,chessBoard.getLayoutX()-settingsButton.getWidth()));
				BorderPane.setMargin(playerOneLabel, new Insets(15,0,20,chessBoard.getLayoutX()));
			});
			
			root.setLeft(gameInfo);
			root.setBottom(bottomScreen);
			root.setTop(topScreen);
			root.setRight(movesSection);
			
			// Place chess board in center of app
			root.setCenter(chessBoard);
			// Add css class
			root.getStyleClass().add("root");
			
			Scene scene = new Scene(root);

			// Use css file to style elements
			scene.getStylesheets().add("board/stylesheet.css");
			primaryStage.setScene(scene);
			
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setHeight(screenBounds.getHeight());
			
			primaryStage.show();
			
			// Constrain the board dimensions so the border 
			// stays with the board when window is adjusted
			chessBoard.setMaxHeight(chessBoard.getHeight());
			chessBoard.setMaxWidth(chessBoard.getWidth());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * Sets up everything needed to create a new
	 * chess game.
	 * 
	 * @param event ActionEvent that will be used to click
	 * 				the flip board button
	 */
	private void startNewGame(ActionEvent event) {
		
		// TODO: create new instance of game logic
		
		isGameOver = false;
		// Reset board
		setupBoard();
		
		// Reset moves list	    		
		movesList.getChildren().clear();
		
		moveNumber = 1;
		turn = 0;
		
		// Reset clocks
		timelineP1.stop();
		timelineP2.stop();
		initializeClocks();
		
		// Reset board perspective to white's POV
		isWhitesPerspective = false;
		Event.fireEvent(flipBoard, event);
	}
	
	/**
	 * Displays the Game Over Screen
	 * 
	 * @param isDraw boolean true if the game ended
	 * 				in a draw, false if otherwise
	 */
	private void gameOverPopup(boolean isDraw) {
		Stage stageGameOver = new Stage();
		
		BorderPane layoutGameOver = new BorderPane();
		
		Label resultHeading = new Label();
		Label gameResult = new Label();
		
		// Add css class
		resultHeading.getStyleClass().add("result-label");
		gameResult.getStyleClass().add("result-label");
		
		if(isDraw) {
			gameResult.setText("Player One ½ - ½ Player Two");
			resultHeading.setText("Game ended in a draw");
		}
		else {
			if(turn == 0) {
				resultHeading.setText("Player Two Won!!");
				gameResult.setText("Player One 0 - 1 Player Two");
			}
			else {
				resultHeading.setText("Player One Won!!");
				gameResult.setText("Player One 1 - 0 Player Two");
			}
		}
		
		// Layout for the New Game and Exit buttons
		HBox buttonOptions = new HBox();
		
		Button newGame = new Button("New Game");
		Button exitPopup = new Button("Exit");
		
		// Add css class
		newGame.getStyleClass().add("result-buttons");
		exitPopup.getStyleClass().add("result-buttons");
		
		// Add buttons to the layout
		buttonOptions.getChildren().addAll(newGame, exitPopup);
		
		newGame.setOnAction((event) -> {
			// Create a new chess game
			startNewGame(event);
			// Close the popup window
			stageGameOver.hide();
    	});
		
		exitPopup.setOnAction((event) -> {
			// Close the popup window
			stageGameOver.hide();
    	});

		// Add labels and buttons to the layout
		layoutGameOver.setTop(resultHeading);
		layoutGameOver.setCenter(gameResult);
		layoutGameOver.setBottom(buttonOptions);
		
		// Spacing/Alignment for the heading
		BorderPane.setAlignment(resultHeading, Pos.CENTER);
		BorderPane.setMargin(layoutGameOver.getTop(), new Insets(15,0,0,0));
		
		// Spacing/Alignment for the buttons
		BorderPane.setMargin(layoutGameOver.getBottom(), new Insets(0,0,15,0));
		HBox.setMargin(newGame, new Insets(0,25,0,0));
		buttonOptions.setAlignment(Pos.CENTER);
		
		
		Scene sceneGameOver = new Scene(layoutGameOver);
		
		// Use css file to style elements
		sceneGameOver.getStylesheets().add("board/stylesheet.css");
		
		// Color Change Window Title
		stageGameOver.setTitle("Game Result");
		
		stageGameOver.setScene(sceneGameOver); 

		// Popup window width and height
		stageGameOver.setHeight(250);
		stageGameOver.setWidth(400);
		
		// Display Game Result Window
		stageGameOver.show();
		
		// Set the width of the exit button 
		// to be the same width as the New Game button
		exitPopup.setPrefWidth(newGame.getWidth());

		// Close the game over window if it loses focus
		stageGameOver.focusedProperty().addListener((eventColor) -> {
			if(!stageGameOver.isFocused()) {
				stageGameOver.hide();
			}
		});
	}
	
	/**
	 * Called at the end of a player's turn. Stops the clock of the player
	 * whose turn just ended and starts the clock for their opponent.
	 * Updates the clock labels.
	 */
	private void updatePlayerClocks() {
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
            
            // Set piece color to white if it's white's turn
            if(turn == 0) {
            	chessPieceDropped.setFill(Color.rgb(250, 249, 246));
            }
            
            squareFrom.getChildren().remove(3);
            squareFrom.getChildren().add(chessPieceDropped);
    	}
    	
        event.consume();
	}
	
	/**
	 * Displays the move from the input textfield on the board.
	 * 
	 * @param squareNumFrom int contains what number child of the GridPane 
	 * 				that the square the piece moved from is
	 * @param squareNumTo int contains what number child of the GridPane 
	 * 				that the square the piece moved to is
	 * @return boolean true if the move is valid, false if not valid
	 */
	private boolean moveFromInput(int squareNumFrom, int squareNumTo) {
		StackPane squareFrom = (StackPane) chessBoard.getChildren().get(squareNumFrom);
		StackPane squareTo = (StackPane) chessBoard.getChildren().get(squareNumTo);
		
		Text chessPieceMoved = (Text) squareFrom.getChildren().get(3);
		
		// Prevent player from moving oppopent's pieces
		if((turn == 0 && chessPieceMoved.getFill().equals(Color.rgb(250, 249, 246)))
				|| (turn == 1 && chessPieceMoved.getFill().equals(Color.rgb(0, 0, 0)))) {
			
			// Delete the piece from square when picked up
			squareFrom.getChildren().remove(3);
	
	    	// Add a blank placeholder to the square
	    	Text blankText = new Text();
	    	squareFrom.getChildren().add(blankText);
	    	
	    	 // Add css class
	        chessPieceMoved.getStyleClass().add("chess-pieces");
	        
	        // Set piece color to white if it's white's turn
	        if(turn == 0) {
	        	chessPieceMoved.setFill(Color.rgb(250, 249, 246));
	        }
	        squareTo.getChildren().remove(3);
	        // Place the piece
	        squareTo.getChildren().add(chessPieceMoved);
	        
	        return true;
		}
		
		return false;
	}
	
	/**
	 * Adds the player's move to the list of moves on the right
	 * side of the screen.
	 * 
	 * @param move String containing the player's move in 
	 * 				Long Algebraic Notation
	 */
	private void updateMovesList(String move) {
		if(turn == 0) {
        	// Add move number to move list
    		Label moveNumLabel = new Label(moveNumber + ".");
    		GridPane.setHalignment(moveNumLabel, HPos.CENTER);
    		movesList.add(moveNumLabel, 0, moveNumber);
    		
    		// Add white move to move list
    		Label whiteMove = new Label(move);
			GridPane.setHalignment(whiteMove, HPos.CENTER);
			movesList.add(whiteMove, 1, moveNumber);
    	}
    	else {
    		// Add black move to move list
    		Label blackMove = new Label(move);
			GridPane.setHalignment(blackMove, HPos.CENTER);
			movesList.add(blackMove, 2, moveNumber);
			
			// Only increment move number after Black's turn
			moveNumber++;
    	}
    	
		// Change whose turn it is
    	turn = (turn==0)?1:0;
	}
	
	/**
	 * Creates the instances of the timelines for the player
	 * clocks to function.
	 */
	private void initializeClocks() {
		// Set remaining clock time to the starting time
		timerP1 = timeControl;
		timerP2 = timeControl;
		minP1 = TimeUnit.SECONDS.toMinutes(timeControl);
		secP1 = timeControl - (minP1 * 60);
		
		// Starting clock values
		timerLabelP1.setText(String.format("%02d:%02d", minP1, secP1));
		timerLabelP2.setText(String.format("%02d:%02d", minP1, secP1));
		
		timelineP1 = new Timeline(new KeyFrame(Duration.seconds(1), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						// Stop the clock if the game is over
						if(isGameOver) {
					    	timelineP1.stop();
					    }
						// Player one's clock runs out
					    if(timerP1 <= 0) {
					    	timelineP1.stop();
					    	isGameOver = true;
					    	gameOverPopup(false);
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
						// Stop the clock if the game is over
					    if(isGameOver) {
					    	timelineP2.stop();
					    }
						// Player two's clock runs out
					    if(timerP2 <= 0) {
					    	timelineP2.stop();
					    	isGameOver = true;
					    	gameOverPopup(false);
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
	}
	
	/**
	 * Creates the chess board GridPane. Sets up all squares, 
	 * pieces, row/column labels, and drag/drop functions
	 * for the pieces.
	 */
	private void setupBoard() {
		
		// Remove all squares and pieces to create new ones
		chessBoard.getChildren().clear();
		
		// Add css class
    	chessBoard.getStyleClass().add("chess-board");
    	
		
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
			    	// Black Pieces.Queen
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
			    	// White Pieces.Queen
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
			    	if(!isGameOver) {
				    	exitedWindow[0] = false;
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
			    	}
			    });
			    
			    squareStack.setOnDragOver((DragEvent event) -> {
			    	event.acceptTransferModes(TransferMode.ANY);
		        });

			    squareStack.setOnDragDropped((DragEvent event) -> {
		            Dragboard db = event.getDragboard();
		            
		            if(!exitedWindow[0] && event.getGestureSource() instanceof StackPane) {
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
				            
				            // Check if move is a Pieces.Pawn Promotion
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
		    	    		String fullMove;
		    	    		
		    	    		// Check if move is a Pieces.Pawn Promotion
				            if(isPawnPromotion) {
				            	
				            	updatePlayerClocks();
				            	
				            	// Move that will be displayed in the moves list
			    	    		fullMove = colLetterFrom + (8 - rowFrom)
			    	    					+ colLetterTo + (8 - rowTo) + "Q"; // Promo Piece letter at end
			    	    		
			    	    		updateMovesList(fullMove);
				            }
			                // Player wants to castle
				            else if(isCastle) {
			                	updatePlayerClocks();
			                	
					            StackPane rookSquareFrom;
					            StackPane rookSquareTo;
					            
					            // Move Pieces.Rook with Pieces.King
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
			                			fullMove = "O-O";
			                			
			                			// Move Pieces.Rook from bottom right square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(63);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Pieces.Rook to row 8 column f
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(61);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                		// Castle long side
			                		else {
			                			// Castle long notation
			                			fullMove = "O-O-O";
			                			
			                			// Move Pieces.Rook from bottom left square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(56);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Pieces.Rook to row 8 column d
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(59);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                	}
			                	// Black Castles
			                	else {
							    	// Castle short side
			                		if(isCastleShort) {
			                			// Castle short notation
			                			fullMove = "O-O";
			                			
			                			// Move Pieces.Rook from top right square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(7);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Pieces.Rook to row 1 column f
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(5);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                		// Castle long side
			                		else {
			                			// Castle long notation
			                			fullMove = "O-O-O";
			                			
			                			// Move Pieces.Rook from top left square
			                			rookSquareFrom = (StackPane) chessBoard.getChildren().get(0);
			                			rookSquareFrom.getChildren().remove(3);
			                			
								    	rookSquareFrom.getChildren().add(blankText);
								    	
								    	// Move Pieces.Rook to row 1 column d
								    	rookSquareTo = (StackPane) chessBoard.getChildren().get(3);
			                			rookSquareTo.getChildren().remove(3);
			                			rookSquareTo.getChildren().add(rookText);
			                		}
			                	}
			                	
			                	updateMovesList(fullMove);
			                }
				            // if the piece does NOT get dropped on the 
				            // square it was moved from
			                else if(colFrom != colTo || rowFrom != rowTo) {
				            	updatePlayerClocks();
				            
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
			    	    		fullMove = pieceLetter + colLetterFrom + (8 - rowFrom) 
			    	    					+ colLetterTo + (8 - rowTo);
			    	    		
			    	    		updateMovesList(fullMove);
				            }
				            
				            event.setDropCompleted(true);
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
			    
			    // TODO: fix drop outside board
//			    root.setOnDragExited((event) -> {
//			    	
//			    });

			    
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
	
	public static void main(String[] args) {
		launch(args);
	}
}