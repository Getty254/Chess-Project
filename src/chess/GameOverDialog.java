package chess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used to create a pop up window
 * to display the results when the game has concluded.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class GameOverDialog extends Alert {

	/** What button is pressed in the
	 *  game over popup.
	 */
	private int closeStatus;
	
	/** Close status of the popup to start a new game.*/
	public static final int NEWGAME = 0;
	/** Close status of the popup to exit the popup.*/
	public static final int EXIT = 1;

	/**
	 * GameOverDialog constructor.
	 * @param result int
	 */
	public GameOverDialog(int result) {
		super(AlertType.NONE);
		Stage stageGameOver = new Stage();
		
		BorderPane layoutGameOver = new BorderPane();
		
		Label resultHeading = new Label();
		Label gameResult = new Label();
		
		// Add css class
		resultHeading.getStyleClass().add("result-label");
		gameResult.getStyleClass().add("result-label");
		
		// 0 draw
		if(result == 0) {
			gameResult.setText("Player One ½ - ½ Player Two");
			resultHeading.setText("Game drawn by agreement");
		}
		// 1 checkmate
		else if(result == 1) {
			if(BoardGUI.turn == 0) {
				resultHeading.setText(
						"Player Two won by checkmate!!");
				gameResult.setText(
						"Player One 0 - 1 Player Two");
			}
			else {
				resultHeading.setText(
						"Player One won by checkmate!!");
				gameResult.setText(
						"Player One 1 - 0 Player Two");
			}
		}
		// 2 stalemate
		else if(result == 2) {
			gameResult.setText("Player One ½ - ½ Player Two");
			resultHeading.setText("Game drawn by stalemate");
		}
		// 3 resign
		else if(result == 3) {
			if(BoardGUI.turn == 0) {
				resultHeading.setText(
						"Player Two won by resignation!!");
				gameResult.setText(
						"Player One 0 - 1 Player Two");
			}
			else {
				resultHeading.setText(
						"Player One won by resignation!!");
				gameResult.setText(
						"Player One 1 - 0 Player Two");
			}
		}
		// 4 timeout
		else {
			if(BoardGUI.turn == 0) {
				resultHeading.setText(
						"Player Two won on time!!");
				gameResult.setText(
						"Player One 0 - 1 Player Two");
			}
			else {
				resultHeading.setText(
						"Player One won on time!!");
				gameResult.setText(
						"Player One 1 - 0 Player Two");
			}
		}
		
		// Layout for the New Game and Exit buttons
		HBox buttonOptions = new HBox();
		
		Button newGame = new Button("New Game");
		Button exitPopup = new Button("Exit");
		Button downloadPGN = new Button("Download PGN");
		
		// Add css class
		newGame.getStyleClass().add("result-buttons");
		exitPopup.getStyleClass().add("result-buttons");
		downloadPGN.getStyleClass().add("result-buttons");
		
		// Add buttons to the layout
		buttonOptions.getChildren().addAll(
				newGame, exitPopup, downloadPGN);
		
		newGame.setOnAction((event) -> {
			closeStatus = NEWGAME;
			
			// Close the popup window
			stageGameOver.hide();
    	});
		
		exitPopup.setOnAction((event) -> {
			closeStatus = EXIT;
			
			// Close the popup window
			stageGameOver.hide();
    	});
		
		downloadPGN.setOnAction((event) -> {
			// Choose a file name and place to save the file
			FileChooser fileChooser = new FileChooser();
			
			// Set the default file name
			fileChooser.setInitialFileName("chess-game");
			
			// Set the file extension to pgn
			FileChooser.ExtensionFilter extFilter =
					new FileChooser.ExtensionFilter(
						"Portable Game Notation (*.pgn)", "*.pgn");
            fileChooser.getExtensionFilters().add(extFilter);
            
            // Get the file
			File fileSelected =
					fileChooser.showSaveDialog(stageGameOver);
			
			// if player picked a file
			if(fileSelected != null) {
				try {
					FileWriter fileWriter =
							new FileWriter(fileSelected);
										
					// Write the moves to the file
					fileWriter.write(MovesList.movesPGN.toString());
					
					// Close the file
					fileWriter.close();
				}
				catch(IOException e) {
					System.out.println("Error writing to the file");
				}
			}			
    	});

		// Add labels and buttons to the layout
		layoutGameOver.setTop(resultHeading);
		layoutGameOver.setCenter(gameResult);
		layoutGameOver.setBottom(buttonOptions);
		
		// Spacing/Alignment for the heading
		BorderPane.setAlignment(resultHeading, Pos.CENTER);
		BorderPane.setMargin(layoutGameOver.getTop(),
				new Insets(15,0,0,0));
		
		// Spacing/Alignment for the buttons
		BorderPane.setMargin(layoutGameOver.getBottom(),
				new Insets(0,0,15,0));
		HBox.setMargin(newGame, new Insets(0,25,0,0));
		HBox.setMargin(downloadPGN, new Insets(0,0,0,25));
		buttonOptions.setAlignment(Pos.CENTER);
		
		
		Scene sceneGameOver = new Scene(layoutGameOver);
		
		// Use css file to style elements
		sceneGameOver.getStylesheets().add("stylesheet.css");
		
		// Color Change Window Title
		stageGameOver.setTitle("Game Result");
		
		stageGameOver.setScene(sceneGameOver); 

		// Popup window width and height
		stageGameOver.setHeight(250);
		stageGameOver.setWidth(425);
		
		// Force focus to stay on the popup
		stageGameOver.initModality(Modality.APPLICATION_MODAL);
		
		// Display Game Result Window
		stageGameOver.showAndWait();
		
		// Set the width of the exit button 
		// to be the same width as the New Game button
		exitPopup.setPrefWidth(newGame.getWidth());
	}
	
	/**
	 * Get what button was pressed.
	 * 
	 * @return int 0 for new game, 1 for cancel
	 */
	public int getCloseStatus() {
		return closeStatus;
	}
}
