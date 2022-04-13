package chess;

import java.awt.MouseInfo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class is used to create a pop up window
 * to display the pieces a player can promote a pawn to.
 * 
 * @author Seth Steinbrook and Getty Muthiani
 * @version 2.0
 */
public class PawnPromotionDialog extends Stage {

	/** What piece is selected in the
	 *  pawn promotion popup.
	 */
	private String closeStatus;

	/**
	 * PawnPromotionDialog constructor.
	 */
	public PawnPromotionDialog() {
		
		Label queen = new Label(ChessPiece.QUEEN);
		Label rook = new Label(ChessPiece.ROOK);
		Label bishop = new Label(ChessPiece.BISHOP);
		Label knight = new Label(ChessPiece.KNIGHT);
		
		// Center the pieces in the labels
		queen.setAlignment(Pos.CENTER);
		rook.setAlignment(Pos.CENTER);
		bishop.setAlignment(Pos.CENTER);
		knight.setAlignment(Pos.CENTER);
		
		// Set label dimensions
		queen.setMinHeight(100);
		queen.setMinWidth(90);
		rook.setMinHeight(100);
		rook.setMinWidth(90);
		bishop.setMinHeight(100);
		bishop.setMinWidth(90);
		knight.setMinHeight(100);
		knight.setMinWidth(90);
		
		// Add css class
		queen.getStyleClass().add("promotion");
		rook.getStyleClass().add("promotion");
		bishop.getStyleClass().add("promotion");
		knight.getStyleClass().add("promotion");
		
		// Set promo piece color to white
		if(BoardGUI.getTurn() == 0) {
			queen.getStyleClass().add("white-piece");
			rook.getStyleClass().add("white-piece");
			bishop.getStyleClass().add("white-piece");
			knight.getStyleClass().add("white-piece");
		}
		// Set promo piece color to black
		else {
			queen.getStyleClass().add("black-piece");
			rook.getStyleClass().add("black-piece");
			bishop.getStyleClass().add("black-piece");
			knight.getStyleClass().add("black-piece");
		}

		
		// Handlers for clicking on the pieces 
		queen.setOnMouseClicked((click) -> {
			closeStatus = ChessPiece.QUEEN;
			
			// Close the popup window
			this.hide();
    	});
		rook.setOnMouseClicked((click) -> {
			closeStatus = ChessPiece.ROOK;
			
			// Close the popup window
			this.hide();
    	});
		bishop.setOnMouseClicked((click) -> {
			closeStatus = ChessPiece.BISHOP;
			
			// Close the popup window
			this.hide();
    	});
		knight.setOnMouseClicked((click) -> {
			closeStatus = ChessPiece.KNIGHT;
			
			// Close the popup window
			this.hide();
    	});
		
        
		HBox promoPiecesLayout = new HBox();

		// Add the labels to the layout
		promoPiecesLayout.getChildren().addAll(
				queen, rook, bishop, knight);
		
		Scene scenePromotion = new Scene(promoPiecesLayout);
		
		// Use css file to style elements
		scenePromotion.getStylesheets().add("stylesheet.css");
		
		this.setScene(scenePromotion); 
		
		// Position the window to appear
		// where the mouse is on screen 
		this.setX(MouseInfo.getPointerInfo().getLocation().getX());
		this.setY(MouseInfo.getPointerInfo().getLocation().getY());
		
		// Removes the close, minimize, and maximize buttons
		this.initStyle(StageStyle.UNDECORATED);
		
		// Does not allow player to click off the popup
		this.initModality(Modality.APPLICATION_MODAL);
		
		// Display Pawn Promotion Window
		this.showAndWait();
	}
	
	/**
	 * Get what piece was selected.
	 * 
	 * @return String promotion piece
	 */
	public String getCloseStatus() {
		return closeStatus;
	}
}
