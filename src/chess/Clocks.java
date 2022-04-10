package chess;

import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * This class controls the clocks for both players.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 1.0
 */
public class Clocks {
	
	/** Clock for player one.*/
	private Timeline timelineP1;
	/** Clock for player two.*/
	private Timeline timelineP2;
	/** Starting time in seconds for player one's clock.*/
	private long timeControlP1;
	/** Starting time in seconds for player two's clock.*/
	private long timeControlP2;
	/** Time added to player one's clock after they make a move.*/
	private long timeIncrementP1;
	/** Time added to player two's clock after they make a move.*/
	private long timeIncrementP2;
	/** Minutes left on player one's clock.*/
	private long minP1;
	/** Minutes left on player two's clock.*/
	private long minP2;
	/** Seconds left on player one's clock.*/
	private long secP1;
	/** Seconds left on player two's clock.*/
	private long secP2;
	/** Time in seconds left on player one's clock.*/
	private long timerP1;
	/** Time in seconds left on player two's clock.*/
	private long timerP2;
	
	/** Label that shows the time left on
	 * player one's clock.
	 */
	private Label timerLabelP1;
	/** Label that shows the time left on
	 * player two's clock.
	 */
	private Label timerLabelP2;
	
	/**
	 * Clocks constructor.
	 * @param gameInfo VBox
	 * @param endGameButtons HBox
	 * @param tLabelP1 Label
	 * @param tLabelP2 Label
	 */
	public Clocks(VBox gameInfo, HBox endGameButtons,
			Label tLabelP1, Label tLabelP2) {
		this.timerLabelP1 = tLabelP1;
		this.timerLabelP2 = tLabelP2;
		
		timerLabelP1.setPrefWidth(125);
		timerLabelP2.setPrefWidth(125);
		
		// Add css class
		timerLabelP1.getStyleClass().add("clocks");
		timerLabelP2.getStyleClass().add("clocks");
		
		gameInfo.getChildren().addAll(timerLabelP2,
				endGameButtons, timerLabelP1);
		
		
		initializeClocks();
	}
	
	
	/**
	 * Creates the instances of the timelines for the player
	 * clocks to function.
	 */
	private void initializeClocks() {
		// Set remaining clock time to the starting time
		timerP1 = timeControlP1;
		timerP2 = timeControlP2;
		minP1 = TimeUnit.SECONDS.toMinutes(timeControlP1);
		secP1 = timeControlP1 - (minP1 * 60);
		minP2 = TimeUnit.SECONDS.toMinutes(timeControlP2);
		secP2 = timeControlP2 - (minP2 * 60);
		
		// Starting clock values
		timerLabelP1.setText(String.format("%02d:%02d", minP1, secP1));
		timerLabelP2.setText(String.format("%02d:%02d", minP2, secP2));
		
		timelineP1 = new Timeline(new KeyFrame(Duration.seconds(1), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						// Stop the clock if the game is over
						if(BoardGUI.isGameOver) {
					    	timelineP1.stop();
					    }
						// Player one's clock runs out
					    if(timerP1 <= 0) {
					    	timelineP1.stop();
					    	BoardGUI.isGameOver = true;
					    }
					    
					    minP1 = TimeUnit.SECONDS.toMinutes(timerP1);
					    secP1 = timerP1 - (minP1 * 60);
						
						timerLabelP1.setText(
								String.format("%02d:%02d",
										minP1, secP1));
						timerP1--;
					}
		}));
		
		timelineP2 = new Timeline(new KeyFrame(Duration.seconds(1), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						// Stop the clock if the game is over
					    if(BoardGUI.isGameOver) {
					    	timelineP2.stop();
					    }
						// Player two's clock runs out
					    if(timerP2 <= 0) {
					    	timelineP2.stop();
					    	BoardGUI.isGameOver = true;
					    }
					    
					    minP2 = TimeUnit.SECONDS.toMinutes(timerP2);
					    secP2 = timerP2 - (minP2 * 60);
						
						timerLabelP2.setText(
								String.format("%02d:%02d",
										minP2, secP2));
						timerP2--;
					}
		}));
		
		// Clocks run until stopped
		timelineP1.setCycleCount(Timeline.INDEFINITE);
		timelineP2.setCycleCount(Timeline.INDEFINITE);
	}
	
	/**
	 * Called at the end of a player's turn. Stops the 
	 * clock of the player whose turn just ended and 
	 * starts the clock for their opponent.
	 * Also updates the clock labels.
	 */
	public void updatePlayerClocks() {
		// White's turn
    	if(BoardGUI.turn == 0) {
    		// Stop player one's timer
    		timelineP1.stop();
    		// Start player two's timer
    		timelineP2.play();
    		
    		// if there is increment update clock
    		if(timeIncrementP1 > 0) {
        		// Add time to player one's clock
        		timerP1 += timeIncrementP1;
        		
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
    		if(timeIncrementP2 > 0) {
        		// Add time to player two's clock
        		timerP2 += timeIncrementP2;
        		
        		minP2 = TimeUnit.SECONDS.toMinutes(timerP2);
			    secP2 = timerP2 - (minP2 * 60);
			    // Update clock label
				timerLabelP2.setText(String.format("%02d:%02d", minP2, secP2));
    		}	
    	}
	}
	
	/**
	 * Stops both clocks and resets them.
	 */
	public void resetClocks() {
		timelineP1.stop();
		timelineP2.stop();
		initializeClocks();
	}
	
	/**
	 * Set the time control for player one.
	 * 
	 * @param min int minutes to start with
	 * @param sec int seconds to start with
	 */
	public void setTimePlayerOne(int min, int sec) {
		timeControlP1 = min * 60 + sec;
	}
	
	/**
	 * Set the time control for player two.
	 * 
	 * @param min int minutes to start with
	 * @param sec int seconds to start with
	 */
	public void setTimePlayerTwo(int min, int sec) {
		timeControlP2 = min * 60 + sec;
	}
	
	/**
	 * Set the increment for player one.
	 * 
	 * @param inc int increment in seconds
	 */
	public void setIncrementPlayerOne(int inc) {
		timeIncrementP1 = inc;
	}
	
	/**
	 * Set the increment for player two.
	 * 
	 * @param inc int increment in seconds
	 */
	public void setIncrementPlayerTwo(int inc) {
		timeIncrementP2 = inc;
	}
	
	/**
	 * Set both clocks to 10 minutes with no increment.
	 */
	public void setDefaultTimes() {
		timeControlP1 = 600;
		timeControlP2 = 600;
		timeIncrementP1 = 0;
		timeIncrementP2 = 0;
	}
}
