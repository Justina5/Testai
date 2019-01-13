package testai;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Savijauta implements Bendri {
	static Stage primaryStage;
	private int savijautaBalais;
	private Text izanginisTekstas = new Text("Ši programa pateiks Jums 4 psichologinius testus, kurie įvertins skirtingas psichines funkcijas.");

	private Text instrukcija = new Text("Šiame teste Jums reikia įvertinti dabartinę savijautą nuo 1 (visiškai ramu) iki 10 (labai susijaudinęs) balų.");
	private Text klaidosPranesimas = new Text("Įvedėte neteisingus duomenis. Prašome įvesti skaičių nuo 1 iki 10.");
	private Text ispejimas = new Text("Jūs pažymėjote, kad šiuo metu esate labai susijaudinęs. Tai gali trukdyti Jums pasiekti normalius psichologinių testų rezultatus,"
			+ " todėl prašome atlikti testą, kai jausitės ramesnis.");

	public int getSavijautaBalais() {
		return this.savijautaBalais;
	}

	public void start() {
		rodytiTestoInstrukcija(primaryStage);
	}

	@Override
	public void rodytiTestoInstrukcija(Stage primaryStage) {
		GridPane izangosLaukas = new GridPane();
		izangosLaukas.add(izanginisTekstas, 0, 0);
		izangosLaukas.add(start, 0, 1);
		GridPane.setMargin(izanginisTekstas, new Insets(10));
		GridPane.setMargin(start, new Insets(20, 100, 0, 500));
		izanginisTekstas.setFont(fontas);
		start.setAlignment(Pos.CENTER);
		start.setOnAction(action -> {
			this.pradetiTesta(primaryStage);
		});

		Scene testoErdve = new Scene(izangosLaukas, 1250, 250);
		primaryStage.setScene(testoErdve);
	}

	@Override
	public void rodytiPranesima(Stage primaryStage, Text pranesimas) {
		Stage pranesimoLaukas = new Stage();
		pranesimoLaukas.setTitle("Pranešimas");
		pranesimoLaukas.centerOnScreen();
		pranesimoLaukas.initModality(Modality.APPLICATION_MODAL);
		pranesimoLaukas.initOwner(primaryStage);
		VBox dialogVbox = new VBox();
		dialogVbox.getChildren().add(pranesimas);
		VBox.setMargin(pranesimas, new Insets(50, 20, 50, 20));
		Scene dialogScene = new Scene(dialogVbox);
		pranesimoLaukas.setScene(dialogScene);
		pranesimoLaukas.show();
	}



	@Override
	public void pradetiTesta(Stage primaryStage) {
		TextField ivedimoLaukas = new TextField();
		ivedimoLaukas.setMaxWidth(80);
		instrukcija.setFont(fontas);

		TilePane tp = new TilePane(instrukcija, ivedimoLaukas,testi);
		tp.setPadding(new Insets(10));
		tp.setVgap(10);

		testi.setOnAction(action -> {
			try {
				int sv = Integer.parseInt(ivedimoLaukas.getText());
				if (sv > 0 && sv < 10) {
					Bendri.rezultatai[0] = (double) sv;
					new Reakcija().rodytiTestoInstrukcija(primaryStage);
				} else if (sv == 10) {
					rodytiPranesima(primaryStage, ispejimas);
				} else {
					rodytiPranesima(primaryStage, klaidosPranesimas);
					ivedimoLaukas.clear();
				}
			} catch (Exception e) {
				rodytiPranesima(primaryStage, klaidosPranesimas);
				ivedimoLaukas.clear();
			}
			
		});		
		primaryStage.getScene().setRoot(tp);
	}
}