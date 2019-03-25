import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MineButton extends Button{

	public ArrayList<ImageView> mineCountNumbers = new ArrayList<ImageView>();
	public int state;
	public int previousState;
	public ImageView imageCover, mineRed, mineGrey, flag, mineX;
	public boolean clicked;
	public boolean firstClickZone;

	public int row;
	public int col;

	public MineButton(int state, boolean clicked, int row, int col){
		this.state = state;
		this.clicked = clicked;
		previousState = state;
		firstClickZone = false;
		this.row = row;
		this.col = col;
		double size = 25;
		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);
		imageCover = new ImageView(new Image("file:res/cover.png"));
		mineRed = new ImageView(new Image("file:res/mine-red.png"));
		mineGrey = new ImageView(new Image("file:res/mine-grey.png"));
		flag = new ImageView(new Image("file:res/flag.png"));
		mineX = new ImageView(new Image("file:res/mine-misflagged.png"));

		for (int i = 0; i < 8; i++) {
			mineCountNumbers.add(new ImageView(new Image("file:res/"+ i +".png")));
			mineCountNumbers.get(i).setFitWidth(size);
			mineCountNumbers.get(i).setFitHeight(size);
		}

		imageCover.setFitWidth(size);
		imageCover.setFitHeight(size);
		mineRed.setFitWidth(size);
		mineRed.setFitHeight(size);
		mineGrey.setFitWidth(size);
		mineGrey.setFitHeight(size);
		flag.setFitWidth(size);
		flag.setFitHeight(size);
		mineX.setFitWidth(size);
		mineX.setFitHeight(size);
		setGraphic(imageCover);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {		
		this.state=state;		
	}

	public boolean getClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked=clicked;
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
}
