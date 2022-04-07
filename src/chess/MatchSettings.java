package chess;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * This class creates the section for players to
 * select the time that each player starts with.
 *
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class MatchSettings extends BorderPane {

	/**
	 * MatchSettings constructor.
	 * @param root BorderPane
	 * @param gameInfo VBox
	 * @param clocks Clocks
	 */
	public MatchSettings(BorderPane root, VBox gameInfo, Clocks clocks) {
		// Prevent pieces from being moved
		BoardGUI.isGameOver = true;

		VBox centerLayout = new VBox();

		Label heading = new Label("Time Control");

		Label playerLabel1 = new Label("Player 1");
		Label playerLabel2 = new Label("Player 2");

		Label minutesLabel = new Label("Minutes:");
		Label secondsLabel = new Label("Seconds:");
		Label incrementLabel = new Label("Increment:");
		Label minutesLabel2 = new Label("Minutes:");
		Label secondsLabel2 = new Label("Seconds:");
		Label incrementLabel2 = new Label("Increment:");

		// Spinners to pick times so no user input is needed
        Spinner<Integer> minSpinner = new Spinner<Integer>();
        Spinner<Integer> secSpinner = new Spinner<Integer>();
        Spinner<Integer> incSpinner = new Spinner<Integer>();
        Spinner<Integer> minSpinner2 = new Spinner<Integer>();
        Spinner<Integer> secSpinner2 = new Spinner<Integer>();
        Spinner<Integer> incSpinner2 = new Spinner<Integer>();

        // Minutes min: 0, max: 30, default: 10
        SpinnerValueFactory<Integer> minValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 10);
        // Seconds min: 0, max: 59, default: 0
        SpinnerValueFactory<Integer> secValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        // Increment min: 0, max: 60, default: 0
        SpinnerValueFactory<Integer> incValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);

        // Minutes min: 0, max: 30, default: 10
        SpinnerValueFactory<Integer> minValueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 10);
        // Seconds min: 0, max: 59, default: 0
        SpinnerValueFactory<Integer> secValueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        // Increment min: 0, max: 60, default: 0
        SpinnerValueFactory<Integer> incValueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);

        // Set the ranges for the spinners
        minSpinner.setValueFactory(minValueFactory);
        secSpinner.setValueFactory(secValueFactory);
        incSpinner.setValueFactory(incValueFactory);
        minSpinner2.setValueFactory(minValueFactory2);
        secSpinner2.setValueFactory(secValueFactory2);
        incSpinner2.setValueFactory(incValueFactory2);

        Button startGame = new Button("Start Game");
        CheckBox checkBox = new CheckBox("Time odds");

        VBox bottomLayout = new VBox();

        bottomLayout.getChildren().addAll(checkBox, startGame);


        // Event handler to allow for time odds
        checkBox.setOnAction((event) -> {
        	// If time odds is checked
        	if(checkBox.isSelected()) {

        		Label lineBreak = new Label(
        				"-------------------------------");

                centerLayout.getChildren().clear();
                centerLayout.getChildren().addAll(
                		heading, playerLabel2, minutesLabel2,
        				minSpinner2, secondsLabel2, secSpinner2,
        				incrementLabel2, incSpinner2, lineBreak,
        				playerLabel1, minutesLabel, minSpinner,
        				secondsLabel, secSpinner,
        				incrementLabel, incSpinner);

                // Spacings between labels and spinners
                VBox.setMargin(lineBreak,
                		new Insets(35, 0, 35, 0));
                VBox.setMargin(minutesLabel2,
                		new Insets(15, 0, 0, 0));
                VBox.setMargin(secondsLabel2,
                		new Insets(10, 0, 0, 0));
        		VBox.setMargin(incrementLabel2,
        				new Insets(10, 0, 0, 0));
        	}
        	// No time odds
        	else {
        		centerLayout.getChildren().clear();
        		centerLayout.getChildren().addAll(
        				heading, minutesLabel, minSpinner,
        				secondsLabel, secSpinner,
        				incrementLabel, incSpinner);
        	}

        	this.setTop(heading);
    		this.setCenter(centerLayout);
    		this.setBottom(bottomLayout);
    	});

		// Event handler to start the game
		startGame.setOnAction((event) -> {
			// Allow game to begin
			BoardGUI.isGameOver = false;

			// Set starting times
			clocks.setTimePlayerOne(
					minSpinner.getValue(),
					secSpinner.getValue());
			// Set increment
			clocks.setIncrementPlayerOne(
					incSpinner.getValue());

			// If time odds is checked
			if(checkBox.isSelected()) {
				// Set starting times
				clocks.setTimePlayerTwo(
						minSpinner2.getValue(),
						secSpinner2.getValue());
				// Set increment
				clocks.setIncrementPlayerTwo(
						incSpinner2.getValue());
			}
			// No time odds
			else {
				// Set starting times
				clocks.setTimePlayerTwo(
						minSpinner.getValue(),
						secSpinner.getValue());
				// Set increment
				clocks.setIncrementPlayerTwo(
						incSpinner.getValue());
			}


			clocks.resetClocks();

			// Displays the clocks
			root.setLeft(gameInfo);
    	});


		// Add css classes
		heading.getStyleClass().add("heading");
		playerLabel1.getStyleClass().add("subheading");
		playerLabel2.getStyleClass().add("subheading");
		checkBox.getStyleClass().add("check-box");
		startGame.getStyleClass().add("check-box");

		centerLayout.getChildren().addAll(
				heading, minutesLabel, minSpinner,
				secondsLabel, secSpinner,
				incrementLabel, incSpinner);

		// Spacings between labels, spinners, and buttons
		BorderPane.setMargin(heading, new Insets(70, 0, 0, 0));
		VBox.setMargin(minutesLabel, new Insets(15, 0, 0, 0));
		VBox.setMargin(secondsLabel, new Insets(10, 0, 0, 0));
		VBox.setMargin(incrementLabel, new Insets(10, 0, 0, 0));
		VBox.setMargin(startGame, new Insets(40, 0, 50, 0));

		// Center elements
    	BorderPane.setAlignment(heading, Pos.CENTER);
		centerLayout.setAlignment(Pos.CENTER);
		bottomLayout.setAlignment(Pos.CENTER);


		this.setTop(heading);
		this.setCenter(centerLayout);
		this.setBottom(bottomLayout);

		this.setPrefWidth(275);
		root.setLeft(this);
	}
}
