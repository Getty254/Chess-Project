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
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;


/**
 * This class creates the GUI application
 * for a chess game.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class BoardGUI extends Application {

	/** True if the game is finished, false if game is still going.*/
	private static boolean isGameOver = true;
	/** 0 if white's turn, 1 if black's turn.*/
	private static int turn = 0;
	/** Keeps track of the current move number.*/
	private static int moveNumber = 1;
	
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
	private Label playerOneLabel = new Label("Player 1");
	/** Label that shows the name for player two.*/
	private Label playerTwoLabel = new Label("Player 2");
	/** ScrollPane to allow players to scroll through the
	* moves list if it goes down too far.
	*/
	private ScrollPane scrollPane = new ScrollPane();
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
	private Label movesHeading = new Label("Game Moves");
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
	 * Creates and brings together all of the 
	 * GUI elements for the chess application.
	 *
	 * @param primaryStage Stage for the main window
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// Application Title
			primaryStage.setTitle("Chess");

			// Resign and Draw Buttons height and width
			resignButton.setPrefWidth(75);
			drawButton.setPrefWidth(75);
			resignButton.setPrefHeight(35);
			drawButton.setPrefHeight(35);

			endGameButtons.getChildren().add(resignButton);
			endGameButtons.getChildren().add(drawButton);

			// Listener for player one to run out of time
			timerLabelP1.textProperty().addListener((changed) -> {
				timeOutGameOver(timerLabelP1.getText());
			});

			// Listener for player two to run out of time
			timerLabelP2.textProperty().addListener((changed) -> {
				timeOutGameOver(timerLabelP2.getText());
			});

			// Listener to show the game over popup
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

			// Spacings for resign/draw buttons
			VBox.setMargin(endGameButtons,
						new Insets(75, 50, 75, 50));
			HBox.setMargin(resignButton,
						new Insets(0, 25, 0, 0));

			gameInfo.setAlignment(Pos.CENTER);

			topScreen.setCenter(playerTwoLabel);
			BorderPane.setAlignment(playerTwoLabel,
								Pos.CENTER_LEFT);

			bottomScreen.setCenter(playerOneLabel);
			BorderPane.setAlignment(playerOneLabel,
								Pos.CENTER_LEFT);

	    	// Adds the moves list to the ScrollPane
	    	scrollPane.setContent(movesList);

	    	// Add and center the moves heading
	    	movesSection.setTop(movesHeading);
	    	BorderPane.setAlignment(movesHeading, Pos.CENTER);

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

	    	// Listen for textfield input
	    	inputMove.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    	    @Override
	    	    public void handle(KeyEvent ke) {
	    	    	// Press enter to execute move
	    	        if (ke.getCode().equals(KeyCode.ENTER)) {
	    	        	attemptMove(inputMove.getText());
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
				flipBoardAction();
			});

			// Resign button is clicked
			resignButton.setOnAction((event) -> {
				endGameButtonAction(false);
			});

			// Draw button is clicked
			drawButton.setOnAction((event) -> {
				endGameButtonAction(true);
			});

			// Create menu buttons
			MenuItem menuNewGame =
					new MenuItem("New Game");
	        MenuItem menuBoardColor =
	        		new MenuItem("Change Board Colors");
	        MenuItem menuLoadPGN =
	        		new MenuItem("Load PGN");

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
				loadPgnMenuAction();
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

			// Place all elements in main layout
			new MatchSettings(root, gameInfo, clocks);
			root.setBottom(bottomScreen);
			root.setTop(topScreen);
			root.setRight(movesSection);
			root.setCenter(chessBoard);

			Scene scene = new Scene(root);

			// Use css file to style elements
			scene.getStylesheets().add("stylesheet.css");
			setStyles();
			primaryStage.setScene(scene);

			Rectangle2D screenBounds =
					Screen.getPrimary().getVisualBounds();
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
	private void startNewGame() {
		logic = new GameLogic();

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

		BoardGUI.resetMoveNumber();
		BoardGUI.resetTurn();

		new MatchSettings(root, gameInfo, clocks);

		// Reset board perspective to white's POV
		if(!isWhitesPerspective) {
			Event.fireEvent(flipBoard, new ActionEvent());
		}
	}
	
	/**
	 * Flips the board perspective.
	 */
	private void flipBoardAction() {
		String nameP1 = playerOneLabel.getText();
		String nameP2 = playerTwoLabel.getText();

		if(isWhitesPerspective) {
			chessBoard.setRotate(180);

			// Switch names
			playerOneLabel.setText(nameP2);
			playerTwoLabel.setText(nameP1);
			
			// Switch clocks
			gameInfo.getChildren().clear();
			gameInfo.getChildren().addAll(timerLabelP1,
					endGameButtons, timerLabelP2);
			
			// Loop through each square of the board
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					// Flip pieces so they
					// are not upside down
					chessBoard.getChildren().get(
							i * 8 + j).setRotate(180);

					// Row with column letters
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
					// Column with row numbers
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

			// Switch names
			playerOneLabel.setText(nameP2);
			playerTwoLabel.setText(nameP1);
			
			// Switch clocks
			gameInfo.getChildren().clear();
			gameInfo.getChildren().addAll(timerLabelP2,
					endGameButtons, timerLabelP1);
			
			// Loop through each square of the board
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					// Flip pieces so they
					// are not upside down
					chessBoard.getChildren().get(i * 8 + j).setRotate(0);

					// Row with column letters
					if(i == 7) {
						StackPane stack = (StackPane)
								chessBoard.getChildren().get(i * 8 + j);
						Text colLet = (Text)
								stack.getChildren().get(1);

						// Align column letters to
						// the bottom right of the square
						StackPane.setAlignment(colLet,
								Pos.BOTTOM_RIGHT);
					}
					// Column with row numbers
					if(j == 0) {
						StackPane stack = (StackPane)
								chessBoard.getChildren().get(i * 8 + j);
						Text rowNum = (Text)
								stack.getChildren().get(2);

						// Align row numbers to the
						// top left of the square
						StackPane.setAlignment(rowNum,
								Pos.TOP_LEFT);
					}
				}
			}

			isWhitesPerspective = true;
		}
	}
	
	/**
	 * Displays the confirmation to resign or draw.
	 * Displays the game over popup if player confirms.
	 *
	 * @param isDraw boolean true if a draw is offered,
	 * 				false for a resignation
	 */
	private void endGameButtonAction(boolean isDraw) {
		// Buttons only works if the game is not over
		if(!isGameOver) {
			Alert alertWindow = new Alert(AlertType.NONE);
			
			// Player clicked draw
			if(isDraw) {
				alertWindow.setTitle("Draw Offer");
				alertWindow.setContentText("Accept draw offer?");
			}
			// Player clicked resign
			else {
				alertWindow.setTitle("Resignation Confirmation");
				alertWindow.setContentText("Are you sure you want to resign?");
			}

			ButtonType buttonTypeYes =
					new ButtonType("Yes", ButtonData.YES);
			ButtonType buttonTypeNo =
					new ButtonType("No", ButtonData.NO);

			alertWindow.getButtonTypes().add(buttonTypeNo);
			alertWindow.getButtonTypes().add(buttonTypeYes);

			alertWindow.showAndWait();

			// Player clicks yes
			if(alertWindow.getResult() == buttonTypeYes) {
				isGameOver = true;
				GameOverDialog dialog;
				// Game was drawn
				if(isDraw) {
					dialog = new GameOverDialog(0);
				}
				// A player resigned
				else {
					dialog = new GameOverDialog(3);
				}

				if(dialog.getCloseStatus() == GameOverDialog.NEWGAME){
					startNewGame();
		    	}
			}
		}
	}
	
	/**
	 * Display for the load pgn menu.
	 */
	private void loadPgnMenuAction() {
		// Start a new game if there is a game in progress
		if(moveNumber != 1 || turn != 0) {
			startNewGame();
		}

		// Displays the clocks
		root.setLeft(gameInfo);

		VBox loadPgnLayout = new VBox();
		HBox pgnButtons = new HBox();
		Button submitPGN = new Button("Submit");
		Button cancelPGN = new Button("Cancel");
		TextArea loadPgnTA = new TextArea();

		// Textarea settings
		loadPgnTA.setPromptText("Paste your pgn here\r\r\r"
				+ "Example:\r"
				+ "1. e2e4 e7e5 2. Ng1f3 Nb8c6 \r"
				+ "3. Bf1b5 Ng8f6 4. O-O Nf6e4");
		loadPgnTA.setPrefHeight(500);
		loadPgnTA.setMaxWidth(200);
		loadPgnTA.setWrapText(true);

		// Add elements to the layout
		pgnButtons.getChildren().addAll(
				submitPGN, cancelPGN);
		loadPgnLayout.getChildren().addAll(
				loadPgnTA, pgnButtons);

		// Center alignment for textfield and buttons
		pgnButtons.setAlignment(Pos.CENTER);
		loadPgnLayout.setAlignment(Pos.CENTER);

		// Spacing between textfield and buttons
		VBox.setMargin(loadPgnTA, new Insets(0, 0, 20, 0));
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
			submitPgnAction(loadPgnTA);
		});
	}
	
	/**
	 * Play the moves when a player submits a pgn.
	 * 
	 * @param txtArea TextArea where a player types or pastes a pgn
	 */
	private void submitPgnAction(TextArea txtArea) {
		// Set clocks to 10 minutes with no increment
		clocks.setDefaultTimes();
		clocks.resetClocks();

		// Allow moves to be made
		isGameOver = false;

		String gamePGN = txtArea.getText();

		// Remove all move numbers, periods,
		// and space directly after the periods
		gamePGN = gamePGN.replaceAll("[0-9]{1,2}\\p{Punct}\\s", "");

		// Turn the string into an array of strings with
		// each move being its own element
		String[] pgnArr = gamePGN.split(" ");

		// Type each move in the textfield
		for(String currentMove: pgnArr){
			// Stop playing moves if one is illegal
			if(attemptMove(currentMove) == -1) {
				break;
			}
		}

		// Display the moves list
		movesSection.setCenter(scrollPane);
	}
	
	/**
	 * Play the move if it legal.
	 * 
	 * @param moveStr String of the move
	 * @return int 0 if the move was played,
	 * 			-1 if the move was illegal
	 */
	private int attemptMove(String moveStr) {
		// Moves can be made if the game is not over
    	if(!isGameOver) {
    		// Verify if input is valid
    		Move move = logic.isInputMoveValid(moveStr);
    		
    		if(move != null) {
    			int moveStatus = logic.isMoveLegal(move);

    			// Illegal move
    			if(moveStatus == -1) {
    				return -1;
    			}
    			else {
    				chessBoard.placePiece(move);
    			}
    			
    			// Update clocks and moves list
    			// if the move was not illegal
    			if(moveStatus != -1) {
    				clocks.updatePlayerClocks();
	        		movesList.updateMovesList(moveStr);
    			}

    			// Checkmate
	    		if(moveStatus == 1) {
	    			triggerGameOver.setText("checkmate");
	    		}
	    		// Stalemate
	    		else if(moveStatus == 2) {
	    			triggerGameOver.setText("stalemate");
	    		}
    		}
    		
    		return 0;
    	}
    	
    	return -1;
	}
	
	/**
	 * Displays the game over popup if a player
	 * runs out of time.
	 *
	 * @param timeLeft String of the time left on the player's clock
	 */
	private void timeOutGameOver(String timeLeft) {
		if(timeLeft.equals("00:00")) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					GameOverDialog dialog =
							new GameOverDialog(4);
					if(dialog.getCloseStatus()
							== GameOverDialog.NEWGAME){
						startNewGame();
			    	}
				}
			});
		}
	}
	
	/**
	 * Sets the CSS styles for the elements within
	 * the main window.
	 */
	private void setStyles() {
		root.getStyleClass().add("root");
		playerOneLabel.getStyleClass().add("player-names");
		playerTwoLabel.getStyleClass().add("player-names");
		resignButton.getStyleClass().add("resign-button");
		drawButton.getStyleClass().add("draw-button");
		scrollPane.getStyleClass().add("scroll-pane");
    	movesHeading.getStyleClass().add("heading");
    	movesSection.getStyleClass().add("moves-section");
		gameInfo.getStyleClass().add("game-info");
	}
	
	/**
	 * Get if the game is over or not.
	 * 
	 * @return boolean true if the game is over,
	 * 			false if otherwise
	 */
	public static boolean getIsGameOver() {
		return isGameOver;
	}
	
	/**
	 * Get which player's turn it is.
	 * 
	 * @return int 0 if white's turn, 1 if black's turn
	 */
	public static int getTurn() {
		return turn;
	}
	
	/**
	 * Get the move number.
	 * 
	 * @return int of the move number
	 */
	public static int getMoveNumber() {
		return moveNumber;
	}
	
	/**
	 * Set if the game is over or not.
	 * 
	 * @param gameOver boolean true if the game is over,
	 * 			false if otherwise
	 */
	public static void setIsGameOver(boolean gameOver) {
		isGameOver = gameOver;
	}
	
	/**
	 * Change which player's turn it is.
	 */
	public static void changeTurn() {
		turn = (turn == 0) ? 1 : 0;
	}
	
	/**
	 * Reset the turn to white.
	 */
	public static void resetTurn() {
		turn = 0;
	}
	
	/**
	 * Increments the move number by one.
	 */
	public static void incMoveNumber() {
		moveNumber++;
	}
	
	/**
	 * Reset the move number to one.
	 */
	public static void resetMoveNumber() {
		moveNumber = 1;
	}

	/**
	 * Main method.
	 * @param args String array
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
