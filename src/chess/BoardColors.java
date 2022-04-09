package chess;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class is used to create a pop up window
 * that allows the players to change the colors
 * of the squares on the chess board.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class BoardColors extends Stage {

	/** RGB color for half of squares.*/
	public static Color colorOne = Color.rgb(222, 184, 135);
	/** RGB color for half of squares.*/
	public static Color colorTwo = Color.rgb(139, 69, 19);
	
	/**
	 * BoardColors constructor.
	 * @param primaryStage Stage
	 * @param chessBoard GridPane
	 */
	public BoardColors(Stage primaryStage, GridPane chessBoard) {
		
		// Set the color pickers' starting colors
		ColorPicker squareColorOne = new ColorPicker(colorOne);
		ColorPicker squareColorTwo = new ColorPicker(colorTwo);
		
		// Button to change colors back to light/dark brown
		Button defaultColors =
				new Button("Change back to default colors");

		VBox colorLayout = new VBox();
		HBox colorSelectors = new HBox();
		
		colorLayout.setAlignment(Pos.CENTER);
		VBox.setMargin(colorSelectors, new Insets(10,10,10,10));
		HBox.setMargin(squareColorOne, new Insets(0,10,0,0));
		VBox.setMargin(defaultColors, new Insets(0,0,10,0));
		
		colorSelectors.getChildren().addAll(
				squareColorOne, squareColorTwo);
		colorLayout.getChildren().addAll(
				colorSelectors, defaultColors);
		
		Scene sceneColorChange = new Scene(colorLayout);
		
		// Color Change Window Title
		this.setTitle("Chess Board Colors");
		
		this.setScene(sceneColorChange); 
		
		// Position the window appears at on screen 
		this.setX(250);
		this.setY(150);
		
		// Close the color change window if it loses focus
		this.focusedProperty().addListener((eventColor) -> {
			if(this.isFocused()) {
				primaryStage.focusedProperty().addListener(
				(eventPrim) -> {
					if(primaryStage.isFocused()) {
						this.hide();
					}
				});
			}
		});
		
		// Display Color Change Window
		this.show();
		
		squareColorOne.setOnAction((eventColor) -> {
			int numSquares = 0;
			
			// Loop through each square of the board
			for(int i = 0; i < 8; i++) {
				numSquares++;
				for(int j = 0; j < 8; j++) {
					StackPane stack = (StackPane)
							chessBoard.getChildren()
							.get(i * 8 + j);
					Rectangle square = (Rectangle)
							stack.getChildren().get(0);
					
					// Alternate squares
				    if (numSquares % 2 != 0) {
				    	// Set square color
				    	colorOne = squareColorOne.getValue();
				    	square.setFill(colorOne);
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
					StackPane stack = (StackPane)
							chessBoard.getChildren()
							.get(i * 8 + j);
					Rectangle square = (Rectangle)
							stack.getChildren().get(0);
					
					// Alternate squares
				    if (numSquares % 2 == 0) {
				    	// Set square color
				    	colorTwo = squareColorTwo.getValue();
				    	square.setFill(colorTwo);
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
					StackPane stack = (StackPane)
							chessBoard.getChildren()
							.get(i * 8 + j);
					Rectangle square = (Rectangle)
							stack.getChildren().get(0);
					
					// Alternate squares
				    if (numSquares % 2 != 0) {
				    	// Light brown
				    	square.setFill(Color.rgb(222, 184, 135));
				    }
				    else {
				    	// Dark Brown
				    	square.setFill(Color.rgb(139, 69, 19));
				    }

				    numSquares++;
				}
			}
		});
	}
}
