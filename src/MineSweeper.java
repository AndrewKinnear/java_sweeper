import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//Andrew Kinnear 300176911
public class MineSweeper extends Application{
	//boolean firstClick = true;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		BorderPane border = new BorderPane();
		VBox menubarBox = new VBox();
		HBox topHBox = new HBox();
		HBox topRight = new HBox();
		HBox topLeft = new HBox();
		HBox topMiddle = new HBox();
		HBox bottomHBox = new HBox();
		Region r1 = new Region();
		Region r2 = new Region();
	

		topHBox.setAlignment(Pos.CENTER);
		topRight.setAlignment(Pos.CENTER);

		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		Menu diff = new Menu("Difficulty");
		menuBar.getMenus().addAll(file, diff);
		MenuItem beginner = new MenuItem("Beginner");
		MenuItem intermediate = new MenuItem("Intermediate");
		MenuItem expert = new MenuItem("Expert");
		MenuItem exit = new MenuItem("Exit");
		SeparatorMenuItem separator = new SeparatorMenuItem();
		SeparatorMenuItem separator2 = new SeparatorMenuItem();
		GridPane paneTrain = new GridPane();
		SmileButton smileButton = new SmileButton(0);

		menuBar.setStyle("-fx-background-color: #BABABA; -fx-border-color:black;");
		file.getItems().add(exit);
		diff.getItems().addAll(beginner, separator2,  intermediate, separator, expert);
		menubarBox.getChildren().add(menuBar);
		topHBox.setStyle("-fx-background-color: #bfbfbf; -fx-border-color: #787878 #fafafa #fafafa #787878; -fx-border-width:3; -fx-border-radius: 0.001;");
		bottomHBox.setStyle("-fx-background-color: #bfbfbf; -fx-border-color: #787878 #fafafa #fafafa #787878; -fx-border-width:3; -fx-border-radius: 0.001;");
		border.setStyle("-fx-background-color: #A9A9A9;");
		topHBox.setSpacing(19);
		topHBox.setPadding(new Insets(5, 5, 5, 5));
		topMiddle.getChildren().add(smileButton);

		Image[] mineAndTimerNumbers = new Image[10];
		for(int i = 0; i < 10; i++) {
			mineAndTimerNumbers[i] = new Image("file:res/digits/"+ i +".png");	
		}

		topRight.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
		topRight.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
		topRight.getChildren().add(new ImageView(mineAndTimerNumbers[0]));

		topHBox.getChildren().add(topLeft);
		topHBox.getChildren().add(r1);
		topHBox.getChildren().add(topMiddle);
		topHBox.getChildren().add(r2);
		topHBox.getChildren().add(topRight);
		bottomHBox.getChildren().add(paneTrain);

		border.setTop(menubarBox);
		border.setCenter(new CustomPane(topHBox));
		border.setBottom(new CustomPane(bottomHBox));

		int setWidth = setWidth(16);
		int setHeight = setHeight(16);
		int mineAmount = 40;
		int[][] mineAndCounterArray = new int[setWidth][setHeight];

		MineButton buttons[][] = new MineButton[setWidth][setHeight];

		//setNumbers(setWidth, setHeight,mineAndCounterArray);

		makeButtons(setWidth, setHeight,mineAmount,buttons,smileButton,paneTrain, topLeft, mineAndTimerNumbers, mineAndCounterArray);
		updateBombs(mineAmount,mineAndTimerNumbers,topLeft);

		smileButton.setOnAction(e->{
			smileButton.setGraphic(smileButton.smile);
			
			try {
				firstClick = true;
				start(primaryStage);
			}catch(Exception ex) {	
			}
		});

		exit.setOnAction(e -> {
			System.exit(0);
		});

		beginner.setOnAction(e -> {
			System.out.println("beginner");
		});
		intermediate.setOnAction(e -> {
			System.out.println("intermediate");
		});
		expert.setOnAction(e -> {
			System.out.println("expert");
		});

		primaryStage.setTitle("Mine Sweeper");
		primaryStage.setScene(new Scene(border));
		primaryStage.show();

	}

	private int setHeight(int height) {
		return height;
	}

	private int setWidth(int height) {
		return height;
	}

	public static void clear8(MineButton[][] buttons, int row, int col, SmileButton smileButton) {
		
		
		if(isValid(row+1, col-1, buttons) && !buttons[row+1][col-1].clicked && buttons[row+1][col-1].state < 100) {
			if(buttons[row+1][col-1].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row+1][col-1].setGraphic(buttons[row+1][col-1].mineRed);
			}else {
				buttons[row+1][col-1].clicked=true;
				buttons[row+1][col-1].setGraphic(buttons[row+1][col-1].mineCountNumbers.get(buttons[row+1][col-1].getState()));
			}
		}

		if(isValid(row, col-1, buttons) && !buttons[row][col-1].clicked && buttons[row][col-1].state < 100) {
			if(buttons[row][col-1].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row][col-1].setGraphic(buttons[row][col-1].mineRed);
			}else {
				buttons[row][col-1].clicked=true;
				buttons[row][col-1].setGraphic(buttons[row][col-1].mineCountNumbers.get(buttons[row][col-1].getState()));
			}
		}
		if(isValid(row-1, col-1, buttons) && !buttons[row-1][col-1].clicked && buttons[row-1][col-1].state < 100) {
			if(buttons[row-1][col-1].state >= 0) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row-1][col-1].setGraphic(buttons[row-1][col-1].mineRed);
			}else {
				buttons[row-1][col-1].clicked=true;
				buttons[row-1][col-1].setGraphic(buttons[row-1][col-1].mineCountNumbers.get(buttons[row-1][col-1].getState()));
			}
		}
		if(isValid(row+1, col, buttons) && !buttons[row+1][col].clicked && buttons[row+1][col].state < 100) {
			if(buttons[row+1][col].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row+1][col].setGraphic(buttons[row+1][col].mineRed);
			}else {
				buttons[row+1][col].clicked=true;
				buttons[row+1][col].setGraphic(buttons[row+1][col].mineCountNumbers.get(buttons[row+1][col].getState()));
			}
		}

		if(isValid(row+1, col+1, buttons) && !buttons[row+1][col+1].clicked && buttons[row+1][col+1].state < 100) {
			if(buttons[row+1][col+1].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row+1][col+1].setGraphic(buttons[row+1][col+1].mineRed);
			}else {
				buttons[row+1][col+1].clicked=true;	
				buttons[row+1][col+1].setGraphic(buttons[row+1][col+1].mineCountNumbers.get(buttons[row+1][col+1].getState()));
			}
		}
		if(isValid(row, col+1, buttons)&& !buttons[row][col+1].clicked && buttons[row][col+1].state < 100) {
			if(buttons[row][col+1].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row][col+1].setGraphic(buttons[row][col+1].mineRed);
			}else {
				buttons[row][col+1].clicked=true;
				buttons[row][col+1].setGraphic(buttons[row][col+1].mineCountNumbers.get(buttons[row][col+1].getState()));
			}
		}
		if(isValid(row-1, col+1, buttons) && !buttons[row-1][col+1].clicked && buttons[row-1][col+1].state < 100) {
			if(buttons[row-1][col+1].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row-1][col+1].setGraphic(buttons[row-1][col+1].mineRed);
			}else{
				buttons[row-1][col+1].clicked=true;
				buttons[row-1][col+1].setGraphic(buttons[row-1][col+1].mineCountNumbers.get(buttons[row-1][col+1].getState()));
			}

		}

		if(isValid(row-1, col, buttons) && !buttons[row-1][col].clicked && buttons[row-1][col].state < 100) {
			if(buttons[row-1][col].state >= 9) {
				showOtherMines(buttons);
				endGame(buttons);
				smileButton.setGraphic(smileButton.dead);
				buttons[row-1][col].setGraphic(buttons[row-1][col].mineRed);
			}else {
				buttons[row-1][col].clicked=true;
				buttons[row-1][col].setGraphic(buttons[row-1][col].mineCountNumbers.get(buttons[row-1][col].getState()));
			}
		}

	}

	private static void showOtherMines(MineButton[][] buttons) {
		System.out.println("Test showOther");
		for(int z = 0; z < buttons.length ; z++){
			for(int x = 0; x < buttons[z].length; x++){
				if(buttons[z][x].previousState < 9 && buttons[z][x].state == 100) {
					buttons[z][x].setGraphic(buttons[z][x].mineX);
				}
				if(buttons[z][x].state >= 9 && buttons[z][x].state < 100)
					buttons[z][x].setGraphic(buttons[z][x].mineGrey);
			
				
				
			}
		}
	}


	public static boolean shouldClear8(MineButton[][] buttons, int row, int col) {

		int flagCount = 0;
		System.out.println("check for clear");
		if(isValid(row+1, col-1, buttons) && buttons[row+1][col-1].state >= 100){
			flagCount++;
		}
		if(isValid(row, col-1, buttons) && buttons[row][col-1].state >= 100){
			flagCount++;
		}
		if(isValid(row-1, col-1, buttons) && buttons[row-1][col-1].state >= 100){
			flagCount++;
		}
		if(isValid(row+1, col, buttons) && buttons[row+1][col].state >= 100){
			flagCount++;
		}

		if(isValid(row+1, col+1, buttons) && buttons[row+1][col+1].state >= 100){
			flagCount++;
		}
		if(isValid(row, col+1, buttons)&& buttons[row][col+1].state >= 100){
			flagCount++;
		}
		if(isValid(row-1, col+1, buttons) && buttons[row-1][col+1].state >= 100){
			flagCount++;
		}
		if(isValid(row-1, col, buttons) && buttons[row-1][col].state >= 100){
			flagCount++;
		}

		if(flagCount >= buttons[row][col].state)
			return true;

		return false;
	}

	public static void recursionAttempt(MineButton[][] buttons, int row, int col) {

		if(buttons[row][col].state < 0 || buttons[row][col].clicked) {
			buttons[row][col].clicked=true;
			buttons[row][col].setGraphic(buttons[row][col].mineCountNumbers.get(buttons[row][col].getState()));
		}

		if(isValid(row+1, col-1, buttons) && !buttons[row+1][col-1].clicked) {
			buttons[row+1][col-1].clicked=true;
			buttons[row+1][col-1].setGraphic(buttons[row+1][col-1].mineCountNumbers.get(buttons[row+1][col-1].getState()));
			if(buttons[row+1][col-1].getState() == 0)
				recursionAttempt(buttons, row+1, col-1);
		}

		if(isValid(row, col-1, buttons) && !buttons[row][col-1].clicked) {
			buttons[row][col-1].clicked=true;
			buttons[row][col-1].setGraphic(buttons[row][col-1].mineCountNumbers.get(buttons[row][col-1].getState()));
			if(buttons[row][col-1].getState() == 0 )
				recursionAttempt(buttons, row, col-1);
		}
		if(isValid(row-1, col-1, buttons) && !buttons[row-1][col-1].clicked ) {
			buttons[row-1][col-1].clicked=true;
			buttons[row-1][col-1].setGraphic(buttons[row-1][col-1].mineCountNumbers.get(buttons[row-1][col-1].getState()));
			if(buttons[row-1][col-1].getState() == 0)
				recursionAttempt(buttons, row-1, col-1);
		}
		if(isValid(row+1, col, buttons) && !buttons[row+1][col].clicked) {
			buttons[row+1][col].clicked=true;
			buttons[row+1][col].setGraphic(buttons[row+1][col].mineCountNumbers.get(buttons[row+1][col].getState()));
			if(buttons[row+1][col].getState() == 0)
				recursionAttempt(buttons, row+1, col);
		}

		if(isValid(row+1, col+1, buttons) && !buttons[row+1][col+1].clicked) {
			buttons[row+1][col+1].clicked=true;
			buttons[row+1][col+1].setGraphic(buttons[row+1][col+1].mineCountNumbers.get(buttons[row+1][col+1].getState()));
			if(buttons[row+1][col+1].getState() == 0)
				recursionAttempt(buttons, row+1, col+1);
		}
		if(isValid(row, col+1, buttons)&& !buttons[row][col+1].clicked) {
			buttons[row][col+1].clicked=true;
			buttons[row][col+1].setGraphic(buttons[row][col+1].mineCountNumbers.get(buttons[row][col+1].getState()));
			if(buttons[row][col+1].getState() == 0 )
				recursionAttempt(buttons, row, col+1);
		}
		if(isValid(row-1, col+1, buttons) && !buttons[row-1][col+1].clicked) {
			buttons[row-1][col+1].clicked=true;
			buttons[row-1][col+1].setGraphic(buttons[row-1][col+1].mineCountNumbers.get(buttons[row-1][col+1].getState()));
			if(buttons[row-1][col+1].getState() == 0 )
				recursionAttempt(buttons, row-1, col+1);
		}
		if(isValid(row-1, col, buttons) && !buttons[row-1][col].clicked) {
			buttons[row-1][col].clicked=true;
			buttons[row-1][col].setGraphic(buttons[row-1][col].mineCountNumbers.get(buttons[row-1][col].getState()));
			if(buttons[row-1][col].getState() == 0)
				recursionAttempt(buttons, row-1, col);
		}

	}


	private static boolean isValid(int row, int col	,MineButton[][] buttons) {
		if(row < 16 && col < 16 && row >= 0 && col >= 0) {
			return true;
		}
		return false;
	}


	private static void assignState(int[][] mineAndCounterArray, MineButton[][] buttons,int setWidth, int setHeight) {
		for(int i = 0; i < setWidth; i++){
			for(int j = 0; j < setHeight; j++){
				buttons[i][j].setState(mineAndCounterArray[i][j]);
			}
		}
		
	}

	public static void isGameOver(SmileButton smileButton, MineButton[][] buttons, int setHeight, int setWidth,int mineAmount) {
		int counter = 0;
		for(int z = 0; z < setWidth; z++){
			for(int x = 0; x < setHeight; x++){
				if(buttons[z][x].clicked) {
					counter++;
				}
				if(setWidth*setHeight-mineAmount == counter) {
					smileButton.setGraphic(smileButton.glasses);
					endGame(buttons);
				}
			}
		}
	}


	public static void makeBombs(int mineAmount, int setWidth, int setHeight, int[][] mineAndCounterArray, int row, int col, MineButton[][] buttons) {

		boolean[][] isBomb = new boolean[setWidth][setHeight];
		if(isValid(row, col, buttons)){buttons[row][col].firstClickZone = true;}
		if(isValid(row+1, col-1, buttons)){	buttons[row+1][col-1].firstClickZone = true;}
		if(isValid(row, col-1, buttons)){buttons[row][col-1].firstClickZone = true;}
		if(isValid(row-1, col-1, buttons)){buttons[row-1][col-1].firstClickZone = true;}
		if(isValid(row+1, col, buttons)){buttons[row+1][col].firstClickZone = true;}
		if(isValid(row+1, col+1, buttons)){buttons[row+1][col+1].firstClickZone = true;}
		if(isValid(row, col+1, buttons)){buttons[row][col+1].firstClickZone = true;}
		if(isValid(row-1, col+1, buttons)){buttons[row-1][col+1].firstClickZone = true;}
		if(isValid(row-1, col, buttons)){buttons[row-1][col].firstClickZone = true;}


		int bombCounter = 0;
		while(bombCounter < mineAmount) {
			int randomRow = (int)(Math.random() * setWidth);
			int randomCol = (int)(Math.random() * setHeight);
			if(!isBomb[randomRow][randomCol] && !buttons[randomRow][randomCol].firstClickZone) {
				mineAndCounterArray[randomRow][randomCol] = 9;
				isBomb[randomRow][randomCol] = true;
				bombCounter++;
			}
		}
		setNumbers(setWidth, setHeight,mineAndCounterArray);
		assignState(mineAndCounterArray, buttons, setWidth, setHeight);
	}

	public static void setNumbers(int setWidth, int setHeight, int[][] mineAndCounterArray) {
		for(int row = 0; row < setWidth; row++){
			for(int col = 0; col < setHeight; col++){
				if(mineAndCounterArray[row][col] >= 9) {
					if(row-1 >= 0 && col-1 >= 0)
						mineAndCounterArray[row-1][col-1] += 1;
					if(row-1 >= 0)
						mineAndCounterArray[row-1][col] += 1;
					if(row-1 >= 0 && col+1 <= setHeight-1)
						mineAndCounterArray[row-1][col+1] += 1;
					if(col-1 >= 0)
						mineAndCounterArray[row][col-1] += 1;
					if(col+1 <= setHeight-1)
						mineAndCounterArray[row][col+1] += 1;
					if(row+1 <= setWidth-1 && col+1 <= setHeight-1)
						mineAndCounterArray[row+1][col+1] += 1;
					if(row+1 <= setWidth-1)
						mineAndCounterArray[row+1][col] += 1;
					if(row+1 <= setWidth-1 && col-1 >= 0)
						mineAndCounterArray[row+1][col-1] += 1;
				}
			}
		}
	}
	
	static boolean firstClick = true;

	public static void makeButtons(int setWidth, int setHeight, int mineAmount, MineButton[][] buttons, SmileButton smileButton, GridPane paneTrain, HBox topLeft, Image[] mineAndTimerNumbers, int[][] mineAndCounterArray){
		
		
		for(int i = 0; i < setWidth; i++){
			for(int j = 0; j < setHeight; j++){
				buttons[i][j] = new MineButton(0, false, i, j);//clicked[i][j]
				MineButton b = buttons[i][j];

				b.setOnMouseClicked(event ->{
					int flagged = 0;
					for(int z = 0; z < setWidth; z++){
						for(int x = 0; x < setHeight; x++){
							if(buttons[z][x].state >= 100)
								flagged++;
						}
					}
					MouseButton button = event.getButton();
					if (button == MouseButton.SECONDARY) {
						System.out.println(b.state);	
						if(b.getState() < 100 && !b.clicked && flagged < mineAmount) {
							b.setGraphic(b.flag);
							b.setState(100);
						}else if(!b.clicked && b.getState() < 1000) {
							b.setGraphic(b.imageCover);
							b.setState(b.previousState);
						}	
					}
					flagged = 0;
					for(int z = 0; z < setWidth; z++){
						for(int x = 0; x < setHeight; x++){
							if(buttons[z][x].state >= 100)
								flagged++;
						}
					}
					updateBombs(mineAmount-flagged, mineAndTimerNumbers,topLeft);
				});
				b.setOnAction( e->{

					
					if(firstClick) {
						makeBombs(mineAmount, buttons.length, buttons.length, mineAndCounterArray, b.getRow(), b.getCol(), buttons);
						firstClick = false;
					}
				

					 if(b.state >= 9 && b.state <= 100) {
						showOtherMines(buttons);
						endGame(buttons);
						b.setGraphic(b.mineRed);
						smileButton.setGraphic(smileButton.dead);
					}else if(b.state < 100){
						if(b.state == 0) {
							recursionAttempt(buttons,b.getRow(),b.getCol());
							isGameOver(smileButton, buttons, setHeight, setWidth, mineAmount);
						}else {
							b.setGraphic(b.mineCountNumbers.get(b.state));
							b.clicked = true;
							isGameOver(smileButton, buttons, setHeight, setWidth, mineAmount);
						}
						if(shouldClear8(buttons,b.getRow(),b.getCol()) && b.state > 0 && b.clicked){
							clear8(buttons,b.getRow(),b.getCol(), smileButton);
						}

					}
				});
				paneTrain.add(buttons[i][j], i, j);
			}
		}
	}

	public static void endGame(MineButton[][] buttons) {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons.length; j++) {
				if(buttons[i][j].state < 1000)
					buttons[i][j].setState(1000);	
			}
		}

	}

	public static void updateBombs(int x, Image[] mineAndTimerNumbers, HBox topLeft) {
		topLeft.getChildren().clear();
		if(x >= 10) {
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[x/10]));
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[x%10]));
		}else if(x >0) {
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[x]));
		}else {
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
			topLeft.getChildren().add(new ImageView(mineAndTimerNumbers[0]));
		}
		topLeft.setAlignment(Pos.CENTER);
	}

}

class CustomPane extends StackPane{
	public CustomPane(Pane pane){
		getChildren().add(pane);
		setPadding(new Insets(10,5,5,5));
	}
}
