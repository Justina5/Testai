package testai;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Rezultatai {
	void rodytiRezultatus(Stage primaryStage) {
		Text rez = new Text("Jūsų reakcija - " + Bendri.rezultatai[1] + " s\nIlgiausiai išlaikėte koncentraciją - " + Bendri.rezultatai[2] +
				" s\nAtsiminėte skaičių, kurio ilgis - " + (int) Bendri.rezultatai[3]);
		/*System.out.println("Jūsų reakcija - " + Bendri.rezultatai[1]);
		System.out.println("Ilgiausiai išlaikėte koncentraciją - " + Bendri.rezultatai[2]);
		System.out.println("Atsiminėte skaičių, kurio ilgis - " + Bendri.rezultatai[3]);*/
		StackPane a = new StackPane();
		a.getChildren().add(rez);
		Scene b = new Scene(a, 1000, 250);
		primaryStage.setScene(b);
	}
}
