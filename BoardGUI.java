package chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import java.util.regex.Pattern;


/**
 * This class creates the GUI application
 * for a chess game.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class BoardGUI extends Application {
	
	/** True if the game is finished, false if game is still going.*/
	public static boolean isGameOver = false;
	/** 0 if white's turn, 1 if black's turn.*/
	public static int turn = 0;
	/** Keeps track of the current move number.*/
	public static int moveNumber = 1;

	
	/** Controls the logic for the piece movements.*/
	private GameLogic logic = new GameLogic();
	/** Used when rotating the board, true if the board is viewed
	 * from white's POV and false if black's POV.*/
	private boolean isWhitesPerspective = true;
	/** Main layout for the window.*/
	private BorderPane root = new BorderPane();
	/** Top section of the main screen that holds
	 * the settings button and player two name.
	 */
	private BorderPane topScreen = new BorderPane();
	/** Bottom section of the main screen that holds
	 * the player two name.
	 */
	private BorderPane bottomScreen = new BorderPane();
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
	/** ScrollPane to allow players to scroll through the
	* moves list if it goes down too far
	*/
	ScrollPane scrollPane = new ScrollPane();
	/** Right section of the main screen that holds
	 * the moves list, textfield, and flip board button.
	 */
	private BorderPane movesSection = new BorderPane();
	/** Label used to listen for a move to be made.
	 * This label is never displayed.*/
	private Label triggerMoveNum = new Label();
	/** GridPane that holds the list of moves.
	 * The first column contains the move number.
	 * The second column contains white's move.
	 * The third column contains black's move.
	 */
	private MovesList movesList = new MovesList(triggerMoveNum);
	/** Label headings to indicate the list of moves section.*/
	private Label movesLabel = new Label("Game Moves");
	/** Layout that holds the textfield and flip board button.*/
	private VBox inputAndFlip = new VBox();
	/** Textfield where players can type in their moves.*/
	private TextField inputMove = new TextField();
	/** Button for players flip the board perspective.*/
	private Button flipBoard = new Button("↑↓");
	
	/** Label that shows the time left on
	 * player one's clock.
	 */
	private Label timerLabelP1 = new Label();
	/** Label that shows the time left on
	 * player two's clock.
	 */
	private Label timerLabelP2 = new Label();
	/**	Label that opens the game over popup 
	 * when the text is changed. This label is
	 * never displayed.*/
	private Label triggerGameOver = new Label();
	/** Clocks for both players.*/
	private Clocks clocks = new Clocks(gameInfo,
			endGameButtons, timerLabelP1, timerLabelP2);
	
	/** GridPane that holds all the squares and
	 * pieces of the chess board.
	 */
	private ChessBoard chessBoard = new 
			ChessBoard(topScreen, bottomScreen, 
					movesSection, gameInfo, clocks,
					movesList, logic, triggerGameOver);
	
	
	/**
	 * Creates all the GUI elements for the
	 * chess application.
	 *
	 * @param primaryStage Stage for the main window
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Application Title
			primaryStage.setTitle("Chess");
			
			//**************************************
			//	Left Column Timers and Buttons
			//**************************************
			
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
			
			
			// Game over if player one has no time left
			timerLabelP1.textProperty().addListener((changed) -> {
				if(timerLabelP1.getText().equals("00:00")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							GameOverDialog dialog =
									new GameOverDialog(4);
							if(dialog.getCloseStatus() ==
									GameOverDialog.NEWGAME){
								startNewGame();
					    	}
						}
					});
				}
			});

			// Game over if player two has no time left
			timerLabelP2.textProperty().addListener((changed) -> {
				if(timerLabelP2.getText().equals("00:00")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							GameOverDialog dialog =
									new GameOverDialog(4);
							if(dialog.getCloseStatus() ==
									GameOverDialog.NEWGAME){
								startNewGame();
					    	}
						}
					});
				}
			});
			
			
			// if the label changes then display
			// the game over popup
			triggerGameOver.textProperty().addListener((changed) -> {
				// Game ended in checkmate
				if(triggerGameOver.getText().equals("checkmate")) {
					GameOverDialog dialog = new GameOverDialog(1);
	    			if(dialog.getCloseStatus() == GameOverDialog.NEWGAME){
						startNewGame();
			    	}
				}
				// Game ended in stalemate
				else if(triggerGameOver.getText().equals("stalemate")) {
					GameOverDialog dialog = new GameOverDialog(2);
	    			if(dialog.getCloseStatus() == GameOverDialog.NEWGAME){
						startNewGame();
			    	}
				}
				// Clear the label
				triggerGameOver.setText("");
			});

			
			// Spacings for left column
			VBox.setMargin(endGameButtons,
						new Insets(75, 50, 75, 50));
			HBox.setMargin(resignButton,
						new Insets(0, 25, 0, 0));

			gameInfo.setAlignment(Pos.CENTER);
			
			
			//**************************************
			//		Player Names and Ratings
			//**************************************
			
			// Add css class
			playerOneLabel.getStyleClass().add("player-names");
			playerTwoLabel.getStyleClass().add("player-names");
			
			topScreen.setCenter(playerTwoLabel);
			BorderPane.setAlignment(playerTwoLabel,
								Pos.CENTER_LEFT);
			
			bottomScreen.setCenter(playerOneLabel);
			BorderPane.setAlignment(playerOneLabel,
								Pos.CENTER_LEFT);
			
			
			//**************************************
			//			Move List
			//**************************************

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
	    	Tooltip moveTip = new Tooltip("Use long algebraic "
	    						+ "notation (e2e4, Ng1f3)");
	    	moveTip.getStyleClass().add("tool-tip");
	    	inputMove.setTooltip(moveTip);
	    	
	    	inputAndFlip.getChildren().add(inputMove);
	    	
	    	BorderPane.setMargin(scrollPane,
	    			new Insets(10, 10, 100, 10));
	    	VBox.setMargin(inputMove,
	    			new Insets(0, 30, 80, 30));
	    	
	    	movesSection.setCenter(scrollPane);
	    	movesSection.setBottom(inputAndFlip);
	    	
	    	// Add css class
	    	movesSection.getStyleClass().add("moves-section");
			
	    	
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
	    	        		
	    	        		// Verify if input is valid
	    	        		Move move =
	    	        				logic.isInputMoveValid(
	    	        				inputMove.getText());
	    	        		if(move != null) {
	    	        			
	    	        			int moveStatus =
	    	        					logic.isMoveLegal(move);

	    	        			// Illegal move
	    	        			if(moveStatus == -1) {
	    	        				System.out.println("illegal");
	    	        			}
			    	        	// Pawn Promotion
	    	        			else if(Pattern.matches(
			    	        			"[a-h][1-8][a-h][1-8][QRNB]",
			    	        			inputMove.getText())) {
			    	        		
			    	        		StackPane squareFrom = (StackPane)
			    	        				chessBoard.getChildren().get(
			    	        						move.getRowFrom() * 8
			    	        						+ move.getColumnFrom());
			    	        		StackPane squareTo = (StackPane)
			    	        				chessBoard.getChildren().get(
			    	        						move.getRowTo() * 8
			    	        						+ move.getColumnTo());
			    	        		
			    	        		Text promoPiece = (Text)
			    	        				squareFrom.getChildren().get(3);

			    	        		// Get the last letter of the input
			    	        		String promoLetter =
			    	        			inputMove.getText().substring(4, 5);
			    	        		
			    	        		// Promote to Queen
			    	        		if(promoLetter.equals("Q")) {
			    	        			promoPiece.setText(ChessPiece.QUEEN);
			    	        		}
			    	        		// Promote to Rook
			    	        		else if(promoLetter.equals("R")) {
			    	        			promoPiece.setText(ChessPiece.ROOK);
			    	        		}
			    	        		// Promote to Knight
			    	        		else if(promoLetter.equals("N")) {
			    	        			promoPiece.setText(ChessPiece.KNIGHT);
			    	        		}
			    	        		// Promote to Bishop
			    	        		else {
			    	        			promoPiece.setText(ChessPiece.BISHOP);
			    	        		}
			    	        		
			    	        		// Delete the piece from 
			    	        		// square when picked up
			    	        		squareFrom.getChildren().remove(3);
		
			    	            	// Add a blank placeholder to the square
			    	            	Text blankText = new Text();
			    	            	squareFrom.getChildren().add(blankText);
			    	            	
			    	            	 // Add css class
			    	            	promoPiece.getStyleClass().add(
			    	            			"chess-pieces");
			    	                
			    	                // Set piece color to white
			    	            	// if it's white's turn
			    	                if(turn == 0) {
			    	                	promoPiece.setFill(
			    	                			Color.rgb(250, 249, 246));
			    	                }
			    	                squareTo.getChildren().remove(3);
			    	                // Place the piece
			    	                squareTo.getChildren().add(promoPiece);			    	                
			    	        	}
			    	        	// Castle
			    	        	else if(Pattern.matches(
			    	        			"O(-O){1,2}",
			    	        			inputMove.getText())) {
			    	        		
			    	        		int rookRowFrom;
			    	        		int rookColFrom;
			    	        		int rookRowTo;
			    	        		int rookColTo;
			    	        		
			    	        		boolean isLongCastle = false;
			    	        		if(Pattern.matches(
			    	        			"O-O-O",
			    	        			inputMove.getText())) {
			    	        			
			    	        			isLongCastle = true;
			    	        		}
			    	        		
			    	        		// White's turn
			    	        		if(turn == 0) {
				    	        		rookRowFrom = 7;
				    	        		rookRowTo = 7;
				    	        		
				    	        		if(isLongCastle) {
				    	        			rookColFrom = 0;
				    	        			rookColTo = 3;
				    	        		}
				    	        		else {
				    	        			rookColFrom = 7;
				    	        			rookColTo = 5;
				    	        		}
			    	        		}
			    	        		// Black's turn
			    	        		else {
			    	        			rookRowFrom = 0;
				    	        		rookRowTo = 0;
			    	        			
			    	        			if(isLongCastle) {
				    	        			rookColFrom = 0;
				    	        			rookColTo = 3;
				    	        		}
				    	        		else {
				    	        			rookColFrom = 7;
				    	        			rookColTo = 5;
				    	        		}
			    	        		}
			    	        		
			    	        		// Move King
			    	        		moveFromInput(
			    	        				move.getRowFrom() * 8
			    	        				+ move.getColumnFrom(),
			    	        				move.getRowTo() * 8
			    	        				+ move.getColumnTo());
			    	        		
			    	        		// Move Rook
			    	        		moveFromInput(
			    	        				rookRowFrom * 8 + rookColFrom,
			    	        				rookRowTo * 8 + rookColTo);
			    	        	}
			    	        	// Piece or Normal Pawn move
			    	        	else {
	
			    	        		moveFromInput(
			    	        				move.getRowFrom() * 8
			    	        				+ move.getColumnFrom(),
			    	        				move.getRowTo() * 8
			    	        				+ move.getColumnTo());
			    	        	}
			    	        	
	    	        			
	    	        			// Update clocks and moves list
	    	        			// if the move was not illegal
	    	        			if(moveStatus != -1) {
	    	        				clocks.updatePlayerClocks();
			    	        		movesList.updateMovesList(inputMove.getText());
	    	        			}	
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
			
			Tooltip flipBoardTip = new Tooltip(
					"Flip the board perspective");
			flipBoardTip.getStyleClass().add("tool-tip");
	    	flipBoard.setTooltip(flipBoardTip);
			
			VBox.setMargin(flipBoard,
					new Insets(0, 30, 0, 30));
			
			// Flip board button is clicked
			flipBoard.setOnAction((event) -> { 
				if(isWhitesPerspective) {
					chessBoard.setRotate(180);
					
					// Loop through each square of the board
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							// Flip pieces so they
							// are not upside down
							chessBoard.getChildren().get(
									i * 8 + j).setRotate(180);
							
							// Bottom row of the board
							// when viewed before the flip
							if(i == 7) {
								StackPane stack = (StackPane)
										chessBoard.getChildren().get(i * 8 + j);
								Text colLet = (Text)
										stack.getChildren().get(1);
								
								// Align column letters to
								// the top left of the square
								StackPane.setAlignment(colLet,
										Pos.TOP_LEFT);
							}
							// Leftmost column of the board
							// when viewed before the flip
							if(j == 0) {
								StackPane stack = (StackPane)
										chessBoard.getChildren().get(i * 8 + j);
								Text rowNum = (Text)
										stack.getChildren().get(2);
								
								// Align row numbers to the bottom right of the square
								StackPane.setAlignment(rowNum,
										Pos.BOTTOM_RIGHT);
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
							// Flip pieces so they
							// are not upside down
							chessBoard.getChildren().get(i * 8 + j).setRotate(0);
							
							// Top row of the board
							// when viewed before the flip
							if(i == 7) {
								StackPane stack = (StackPane)
										chessBoard.getChildren().get(i * 8 + j);
								Text colLet = (Text)
										stack.getChildren().get(1);
								
								// Align column letters to 
								// the top left of the square
								StackPane.setAlignment(colLet,
										Pos.BOTTOM_RIGHT);
							}
							// Leftmost column of the board
							// when viewed before the flip
							if(j == 0) {
								StackPane stack = (StackPane)
										chessBoard.getChildren().get(i * 8 + j);
								Text rowNum = (Text)
										stack.getChildren().get(2);
								
								// Align row numbers to the
								// bottom right of the square
								StackPane.setAlignment(rowNum,
										Pos.TOP_LEFT);
							}
						}
					}
					
					isWhitesPerspective = true;
				}
			});
			
			// Resign button is clicked
			resignButton.setOnAction((event) -> {
				// Button only works if the game is not over
				if(!isGameOver) {
					Alert alertResign = new Alert(AlertType.NONE);
					alertResign.setTitle("Resignation Confirmation");
					alertResign.setContentText("Are you sure you want to resign?");		
					
					ButtonType buttonTypeYes =
							new ButtonType("Yes", ButtonData.YES);
					ButtonType buttonTypeNo =
							new ButtonType("No", ButtonData.NO);
					
					alertResign.getButtonTypes().add(buttonTypeNo);
					alertResign.getButtonTypes().add(buttonTypeYes);
					
					alertResign.showAndWait();
					
					// Player clicks yes
					if(alertResign.getResult() == buttonTypeYes) {
						isGameOver = true;
						GameOverDialog dialog = new GameOverDialog(3);
						if(dialog.getCloseStatus() == GameOverDialog.NEWGAME){
							startNewGame();
				    	}
					}	
				}
			});
			
			// Draw button is clicked
			drawButton.setOnAction((event) -> {
				// Button only works if the game is not over
				if(!isGameOver) {
					Alert alertDraw = new Alert(AlertType.NONE);
					alertDraw.setTitle("Draw Offer");
					alertDraw.setContentText("Accept draw offer?");		
					
					ButtonType buttonTypeYes =
							new ButtonType("Yes", ButtonData.YES);
					ButtonType buttonTypeNo =
							new ButtonType("No", ButtonData.NO);
					
					alertDraw.getButtonTypes().add(buttonTypeNo);
					alertDraw.getButtonTypes().add(buttonTypeYes);
					
					alertDraw.showAndWait();
					
					// Player clicks yes
					if(alertDraw.getResult() == buttonTypeYes) {
						isGameOver = true;
						GameOverDialog dialog = new GameOverDialog(0);						
						if(dialog.getCloseStatus() == GameOverDialog.NEWGAME){
							startNewGame();
				    	}
					}	

				}
			});
			
			
			//**************************************
			//			Settings
			//**************************************
			
			MenuItem menuNewGame =
					new MenuItem("New Game");
	        MenuItem menuBoardColor =
	        		new MenuItem("Change Board Colors");
	        MenuItem menuLoadPGN =
	        		new MenuItem("Import PGN");
			
			MenuButton settingsButton = 
					new MenuButton("⚙", null, 
					menuNewGame, menuBoardColor, menuLoadPGN);
			
			settingsButton.getStyleClass().add("settings-button");
			settingsButton.setPrefWidth(50);
			settingsButton.setPrefHeight(50);
			
			topScreen.setLeft(settingsButton);			
			
			menuNewGame.setOnAction((event) -> {
				startNewGame();
	    	});
			
			menuBoardColor.setOnAction((event) -> {
				new BoardColors(primaryStage, chessBoard);
			});
			
			menuLoadPGN.setOnAction((event) -> {
				// Start a new game if there is a game in progress
				if(moveNumber != 1 || turn != 0) {
					startNewGame();
				}
				
				VBox loadPgnLayout = new VBox();
				HBox PgnButtons = new HBox();
				Button submitPGN = new Button("Submit");
				Button cancelPGN = new Button("Cancel");
				TextField loadPgnTF = new TextField();
				
				// Textfield settings
				loadPgnTF.setPromptText("Paste your pgn here\r\r\r"
						+ "Example:\r"
						+ "1. e2e4 e7e5 2. Ng1f3 Nb8c6 \r"
						+ "3. Bf1b5 Ng8f6 4. O-O Nf6e4");
				loadPgnTF.setPrefHeight(500);
				loadPgnTF.setMaxWidth(200);
				
				// Add elements to the layout
				PgnButtons.getChildren().addAll(
						submitPGN, cancelPGN);
				loadPgnLayout.getChildren().addAll(
						loadPgnTF, PgnButtons);
				
				// Center alignment for textfield and buttons
				PgnButtons.setAlignment(Pos.CENTER);
				loadPgnLayout.setAlignment(Pos.CENTER);
				
				// Spacing between textfield and buttons
				VBox.setMargin(loadPgnTF, new Insets(0, 0, 20, 0));
				HBox.setMargin(submitPGN, new Insets(0, 20, 0, 0));
				
				movesSection.setCenter(loadPgnLayout);
				
				// Reset trigger
				triggerMoveNum.setText("");
				
				// Listener to switch the load pgn textfield
				// back to the moves list
				triggerMoveNum.textProperty().addListener((changed) -> {
					// if player made a move instead of loading a pgn
					if(triggerMoveNum.getText().equals("moved")) {
						movesSection.setCenter(scrollPane);
					}
				});

				// Player cancels the pgn
				cancelPGN.setOnAction((click) -> {
					movesSection.setCenter(scrollPane);
				});
				
				// Player submits pgn
				submitPGN.setOnAction((click) -> {
					String gamePGN = loadPgnTF.getText();
					
					// Remove all move numbers, periods, 
					// and space directly after the periods
					gamePGN = gamePGN.replaceAll("[0-9]{1,2}\\p{Punct}\\s", "");

					// Turn the string into an array of strings with
					// each move being its own element
					String pgnArr[] = gamePGN.split(" ");
					
					// Type each move in the textfield
					for(String currentMove: pgnArr){
						// Puts the textfield in focus
						inputMove.requestFocus();
						
						// Enters the move into the textfield
						inputMove.setText(currentMove);
						
						// Presses the enter key to perform the move
						inputMove.fireEvent(new KeyEvent(
								KeyEvent.KEY_PRESSED, " ", " ",
								KeyCode.ENTER, false, false,
								false, false));
					}
					
					// Display the moves list
					movesSection.setCenter(scrollPane);
				});
			});
			
			
			// Move player names when window width is adjusted
			primaryStage.widthProperty().addListener(
					(eventResize, oldX, newX) -> {
				BorderPane.setMargin(playerTwoLabel,
						new Insets(0, 0, 0, 
							chessBoard.getLayoutX()
							- settingsButton.getWidth()));
				BorderPane.setMargin(playerOneLabel,
						new Insets(15, 0, 20,
							chessBoard.getLayoutX()));
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
			scene.getStylesheets().add("stylesheet.css");
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
	 */
	public void startNewGame() {
		System.out.println(movesList.getPGN());
		logic = new GameLogic();

		isGameOver = false;
		
		// Reset board
		chessBoard = new 
				ChessBoard(topScreen, bottomScreen, 
						movesSection, gameInfo, clocks,
						movesList, logic, triggerGameOver);
		root.setCenter(chessBoard);
		
		// Reset moves list
		movesList.getChildren().clear();
		movesList.clearPGN();
		
		// Display moves list if not already showing
		if(!(movesSection.getCenter() instanceof ScrollPane)) {
			movesSection.setCenter(scrollPane);
		}
		
		moveNumber = 1;
		turn = 0;
		
		// Reset clocks
		clocks.resetClocks();
		
		// Reset board perspective to white's POV
		isWhitesPerspective = false;
		Event.fireEvent(flipBoard, new ActionEvent());
	}
	
	
	/**
	 * Displays the move from the input textfield on the board.
	 * 
	 * @param squareNumFrom int contains what number child of the GridPane 
	 * 				that the square the piece moved from is
	 * @param squareNumTo int contains what number child of the GridPane 
	 * 				that the square the piece moved to is
	 */
	private void moveFromInput(int squareNumFrom, int squareNumTo) {
		StackPane squareFrom = (StackPane) chessBoard.getChildren().get(squareNumFrom);
		StackPane squareTo = (StackPane) chessBoard.getChildren().get(squareNumTo);
		
		Text chessPieceMoved = (Text) squareFrom.getChildren().get(3);
		
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
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
