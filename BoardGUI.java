
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;



// How to setup javafx on eclipse
// https://www.youtube.com/watch?v=N6cZcw8_XtM


public class BoardGUI extends Application {
	
	private Timeline timelineP1, timelineP2;
	private long timeControl = 600; // 10 minutes in seconds
	private long minP1, minP2, secP1, secP2; 
	private long timerP1 = timeControl, timerP2 = timeControl;
	
	// Not used yet
	String wPawn = "♙";
	String wKnight = "♘";
	String wBishop = "♗";
	String wRook = "♖";
	String wQueen = "♕";
	String wKing = "♔";
	
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
					new EventHandler() {
						public void handle(Event event) {
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
					new EventHandler() {
						public void handle(Event event) {
						    if(timerP2 <= 0) {
						    	timelineP2.stop();
						    }
						    minP2 = TimeUnit.SECONDS.toMinutes(timerP2);
						    secP2 = timerP2 - (minP2 * 60);
							
							timerLabelP2.setText(String.format("%02d:%02d", minP2, secP2));
							timerP2--;
						}
			}));

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
	    				Label moveNum = new Label((row+1) + ".");
	    				// Center move number in grid
	    				GridPane.setHalignment(moveNum, HPos.CENTER);
	    				movesList.add(moveNum, column, row);
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
	    	        	timelineP1.play();
	    	        	inputMove.clear();
	    	        	
	    	        	// Add move to move list
	    	        	// this is just a placeholder
	    	        	Label moveNum = new Label("  " + (11) + ".");
	    	        	GridPane.setHalignment(moveNum, HPos.CENTER);
	    				movesList.add(moveNum, 0, 11);
	    				Label whiteMove = new Label("Bc1b2");
	    				GridPane.setHalignment(whiteMove, HPos.CENTER);
	    				movesList.add(whiteMove, 1, 11);
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
	    	
			// Create 64 rectangles (squares) and add them to the grid
			int numberOfSquares = 0;
			double squareDimensions = 100;
			
			
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
				    Text colLet = new Text(" ");;
				    Text rowNum = new Text(" ");
				    
				    // Label columns with letters a - h on the bottom row
				    if(row == 7) {
				    	
						switch(column) {
							case 0:
								colLet = new Text("a ");
								rowNum = new Text(" 1");
								break;
							case 1:
								colLet = new Text("b ");
								break;
							case 2:
								colLet = new Text("c ");
								break;
							case 3:
								colLet = new Text("d ");
								break;
							case 4:
								colLet = new Text("e ");
								break;
							case 5:
								colLet = new Text("f ");
								break;
							case 6:
								colLet = new Text("g ");
								break;
							default:
								colLet = new Text("h ");
						}
						
				    	squareStack.getChildren().addAll(square, colLet, rowNum);
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
								rowNum = new Text(" 8");							
								break;
							case 1:
								rowNum = new Text(" 7");
								break;
							case 2:
								rowNum = new Text(" 6");
								break;
							case 3:
								rowNum = new Text(" 5");
								break;
							case 4:
								rowNum = new Text(" 4");
								break;
							case 5:
								rowNum = new Text(" 3");
								break;
							case 6:
								rowNum = new Text(" 2");
								break;
							default:
								rowNum = new Text(" ");
				    	}
				    	
				    	squareStack.getChildren().addAll(square, rowNum);
				    	// Position row number in top left 
				    	// corner of the square
						StackPane.setAlignment(rowNum, Pos.TOP_LEFT);
				    	chessBoard.add(squareStack, column, row);
				    }
				    else {
				    	// Add the square to the grid
				    	chessBoard.add(square, column, row);
				    }
				    
				    // Add css class
				    colLet.getStyleClass().add("square-coords");
					rowNum.getStyleClass().add("square-coords");
				     
				    numberOfSquares++;
				}
			}
			
			
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
	
	
	public static void main(String[] args) {
		launch(args);
	}
}