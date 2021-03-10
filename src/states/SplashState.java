package states;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

import java.util.List;

import constants.Constants;
import constants.Images;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.GameFrame;
import main.World;

public class SplashState extends GameState {
	private long startNano = 0;

	public SplashState(GameModel model) {
		super(model);
	}

	@Override
	public void update() {
		if (Constants.RAND.nextInt(10) > 2) {
			universe.add(new World());
		}
		for (int i = 0; i < universe.size(); i++) {
			if (universe.get(i).getPosY() > Constants.SCREEN_HEIGHT)
				universe.remove(i);
		}
	}

	@Override
	public void draw(GraphicsContext g) {
		g.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		g.drawImage(Images.SPLASH_BG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//		
//		g.setFont(Constants.mainFont);
//		g.setFill(Color.web("#6671DA"));		
//		g.fillText(model.getScore().toString(), Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 1.4);
//		g.fillText("LEVEL \n COMPLETE", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 3);
//		
//		g.setFont(Constants.subFont);
//		g.setFill(Color.WHITE);
//		g.fillText("Your Score", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 1.8);
//		g.fillText("Entering an unkown Galaxy...", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 1.1);
		
		for (World world : universe) {
			world.draw(g);
		}
	}

	@Override
	public void init(GameFrame frame) {
		Text title = new Text("\n LEVEL COMPLETE");
		title.setFont(Constants.mainFont);
		title.setFill(Color.web("#6671DA"));
		
		Text yourScore = new Text("Your Current Score");
		yourScore.setFont(Constants.subFont);
		yourScore.setFill(Color.WHITE);
		
		Text score = new Text(model.getScore().toString());
		score.setFont(Constants.mainFont);
		score.setFill(Color.web("#6671DA"));
		
		VBox textBox = new VBox(30, title, yourScore, score);
		textBox.setAlignment(Pos.CENTER);
		
		Text subTitle = new Text("Entering an unkown Galaxy...");
		subTitle.setFont(Constants.miscFont);
		subTitle.setFill(Color.WHITE);
		VBox desc = new VBox(subTitle);
		desc.setAlignment(Pos.CENTER);
		
		Glow glow = new Glow();   
        glow.setLevel(1.0);  
        title.setEffect(glow);
        score.setEffect(glow);
		
		VBox splashElements = new VBox(100, textBox, desc);
		contents.getChildren().add(splashElements);
		frame.getChildren().add(contents);		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void handleInput(List<String> input) {
		if (input.contains("SPACE"))
			model.switchState(new LevelTwo(model));
	}

	@Override
	public void activate(GameFrame frame) {
		init(frame);
	}

	@Override
	public void deactivate(GameFrame frame) {
		frame.getChildren().remove(contents);
	}

}
