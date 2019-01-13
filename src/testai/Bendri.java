package testai;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public interface Bendri {
	
	Button start = new Button("Pradėti");
	Button testi = new Button("Tęsti");
	double[] rezultatai = new double[4];
	Font fontas = new Font("Verdana", 18);
		
	abstract void rodytiTestoInstrukcija(Stage primaryStage);
	abstract void pradetiTesta(Stage primaryStage);
	abstract void rodytiPranesima(Stage primaryStage, Text pranesimas);
}