package testai;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public void setStage(Stage stage) {
		Savijauta.primaryStage = stage;
	}
	public static void start() {
		new Savijauta().start();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Psychology Invaders");
			setStage(primaryStage);
			start();
			//new Koncentracija().pradetiTesta(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}