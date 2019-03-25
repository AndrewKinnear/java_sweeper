import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SmileButton extends Button{

	public int state;
	public ImageView smile, glasses, ohFace, dead;

	public SmileButton(int state) {
		this.state = state;
		double size = 35;
		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);
		smile = new ImageView(new Image("file:res/face-smile.png"));
		glasses = new ImageView(new Image("file:res/face-win.png"));
		ohFace = new ImageView(new Image("file:res/face-O.png"));
		dead = new ImageView(new Image("file:res/face-dead.png"));
		smile.setFitWidth(size);
		smile.setFitHeight(size);
		glasses.setFitWidth(size);
		glasses.setFitHeight(size);
		ohFace.setFitWidth(size);
		ohFace.setFitHeight(size);
		dead.setFitWidth(size);
		dead.setFitHeight(size);
		setGraphic(smile);
	}

	public void setState(int state) {		
		this.state=state;		
	}
}
