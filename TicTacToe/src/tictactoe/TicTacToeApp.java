package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToeApp extends Application {
	
	private Button[][] buttons = new Button[3][3];
	private boolean playerX = true;
	private Text statusText = new Text();	
	
	@Override
	public void start(Stage primaryStage) {
		// Create the root node
		BorderPane root = new BorderPane();		
		
		// Create the game board
		GridPane gameBoard = new GridPane();
		gameBoard.getStyleClass().add("game-board");
		gameBoard.setPadding(new Insets(20));
		gameBoard.setHgap(5);
		gameBoard.setVgap(5);
		
		// Add buttons to the game board
		for (int row=0; row < 3; row++) {
			for (int col=0; col < 3; col++) {
				Integer innerRow = row;
				Integer innerCol = col;
				buttons[row][col] = new Button();
				buttons[row][col].setPrefSize(100, 100);
				buttons[row][col].getStyleClass().add("game-button"); // Apply CSS class
				buttons[row][col].setOnAction(e -> handleButtonClick(buttons[innerRow][innerCol])); // Set event handler
				gameBoard.add(buttons[row][col], row, col);
			}
		}
		
		// Create reset button
		Button resetButton = new Button("Reset");
		resetButton.setOnAction(e -> resetGame());
		resetButton.getStyleClass().add("reset-button");
		resetButton.setPadding(new Insets(8, 16, 8, 16));
		BorderPane.setMargin(resetButton, new Insets(8,0,0,10));
		
		// Create status text
        StackPane statusPane = new StackPane(statusText);
        statusPane.setPadding(new Insets(10));
        statusPane.getStyleClass().add("stack-pane"); // Apply CSS class
        statusText.getStyleClass().add("status-text"); // Apply CSS class
        
		
		// Add the game board to the centre of the root pane
		root.setCenter(gameBoard);
		root.setTop(resetButton);
		root.setBottom(statusPane);
		
		//Create the scene
		Scene scene = new Scene(root, 320, 320);
		
		 // Load the CSS file
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		
		//Set the scene for the primary stage
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tic Tac Toe");
		primaryStage.show();
	}
	
	private void handleButtonClick (Button button) {
		if (!button.getText().isEmpty()) {
			// Cell not empty
			return;
		}
		
		// Set X or O based on current player
		button.setText(playerX ? "X" : "O");
		
		// Check for winner
		if (checkWinner()) {
			statusText.setText("Player " + (playerX ? "X" : "O") + " wins!");
			disableButtons();
		}
		else if (isBoardFull()) {
			statusText.setText("It's a draw!");
		}
		else {
			playerX = !playerX;
			statusText.setText("Player " + (playerX ? "X": "O") + "'s turn");
		}
	}
	
	private boolean checkWinner() {
		// Check rows
		for (int i = 0; i < 3; i++) {
			if (!buttons[i][0].getText().isEmpty()&&
					buttons[i][0].getText().equals(buttons[i][1].getText()) && 
					buttons[i][0].getText().equals(buttons[i][2].getText())) {
				return true;
			}
		}
		
		// Check columns
		for (int i = 0; i < 3; i++) {
			if (!buttons[0][i].getText().isEmpty() &&
					buttons[0][i].getText().equals(buttons[1][i].getText()) && 
					buttons[0][i].getText().equals(buttons[2][i].getText())) {
				return true;
			}
		}
		
		// Check diagonals
		if (!buttons[0][0].getText().isEmpty() && 
				buttons[0][0].getText().equals(buttons[1][1].getText()) && 
				buttons[0][0].getText().equals(buttons[2][2].getText())) {
			return true;
		}
		if (!buttons[0][2].getText().isEmpty() && 
				buttons[0][2].getText().equals(buttons[1][1].getText()) && 
				buttons[0][2].getText().equals(buttons[2][0].getText())) {
			return true;
		}
		
		return false;
	}
	
	private boolean isBoardFull() {
		// Check if board is full
		for (Button[] row : buttons) {
			for (Button button : row) {
				if (button.getText().isEmpty()) {
					return false;
				}
			}
		}
		// All cells are filled
		return true;
	}
	
	private void resetGame() {
		for (Button[] row : buttons) {
			for (Button button : row) {
				if (button != null) {
					button.setText("");
					button.setDisable(false);
				}
			}
		}
	}
	
	private void disableButtons() {
		// Disable buttons at game end
		for (Button[] row : buttons) {
			for (Button button : row) {
				button.setDisable(true);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
