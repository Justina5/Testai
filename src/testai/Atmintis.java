package testai;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Atmintis extends Thread implements Bendri  {
	private Text instrukcija = new Text("Šioje užduotyje Jums reikės atsiminti, kaip galima ilgesnį skaičių (t.y. iš kuo daugiau skaitmenų.\n"
			+ "Jums bus parodytas atsitiktinis skaičius ir tada turėsite jį įvesti ir spausti ENTER.\n Kaskart suvedinėdami naują skaičių, galite 1 kartą suklysti.\n"
			+ "Suklydus antrą kartą užduotis bus baigta.");

	private int skaitmenuKiekis = 1;
	private long rodymoTrukme = 1500;
	private boolean klaida = false;
	Thread t1;
	private Text pranesimas = new Text("Suklydote! Bandykite dar 1 kartą.");	
	Button toliau = new Button("Toliau");


	char randomWithRange(int min, int max)
	{
		int range = (max - min) + 1;
		return (char) ((Math.random() * range) + min);
	}

	public String gautiSkaiciu(int ilgis) {
		char[] randSkaitmenys = new char[ilgis];
		randSkaitmenys[0] = randomWithRange(49, 57);
		for (int i = 2; i <= ilgis; i++) {
			randSkaitmenys[i-1] = randomWithRange(48, 57);
		}
		return String.copyValueOf(randSkaitmenys);
	}

	@SuppressWarnings("deprecation")
	private void tikrinti(String skaicius, String ivedimas, Stage primaryStage) {
		t1.stop();	
		if (skaicius.equals(ivedimas)) {
			klaida = false;
			this.skaitmenuKiekis++;
			this.rodymoTrukme += 500;
			this.pradetiTesta(primaryStage);
		} else {
			if (klaida == true) {
				toliau.setDisable(true);
				testi.setVisible(true);
				//this.rodytiPranesima(primaryStage, pabaigosPranesimas);
				Bendri.rezultatai[3] = (double) (skaitmenuKiekis - 1);
				new Rezultatai().rodytiRezultatus(primaryStage);
			} else {
				klaida = true;
				toliau.setVisible(true);
				testi.setVisible(false);
				this.rodytiPranesima(primaryStage, pranesimas);
			}
		}
	}

	@Override
	public void rodytiPranesima(Stage primaryStage, Text pranesimas) {
		Stage pranesimoLaukas = new Stage();
		VBox laukas = new VBox();
		pranesimoLaukas.setTitle("Pranešimas");
		pranesimoLaukas.centerOnScreen();
		pranesimoLaukas.initOwner(primaryStage);
		laukas.getChildren().add(pranesimas);
		laukas.getChildren().add(testi);
		laukas.getChildren().add(toliau);
		toliau.setOnAction(action -> {
			pradetiTesta(primaryStage);
			pranesimoLaukas.close();
		});
		pranesimoLaukas.setScene(new Scene(laukas));
		pranesimoLaukas.show();
	}

	@Override
	public void rodytiTestoInstrukcija(Stage primaryStage) {
		instrukcija.setFont(fontas);
		GridPane izanginis = new GridPane();
		izanginis.add(instrukcija, 0, 0);
		izanginis.add(start, 0, 1);
		izanginis.setPadding(new Insets(10));
		GridPane.setMargin(start, new Insets(20, 100, 0, 500));
		Scene testoErdve = new Scene(izanginis, 900, 250);
		start.setOnAction(action -> {
			pradetiTesta(primaryStage);
		});	
		primaryStage.setScene(testoErdve);
		primaryStage.show();
	}

	@Override
	public void pradetiTesta(Stage primaryStage) {
		t1 = new Thread(new Runnable() {
			public void run() {
				TextField ivedimoLaukas = new TextField();
				ivedimoLaukas.setVisible(false);
				Text skaicius = new Text(gautiSkaiciu(skaitmenuKiekis));
				HBox hb1 = new HBox();
				hb1.getChildren().add(skaicius);
				hb1.getChildren().add(ivedimoLaukas);
				hb1.setPadding(new Insets(50));
				hb1.setAlignment(Pos.CENTER);
				primaryStage.getScene().setRoot(hb1);
				try {
					sleep(rodymoTrukme);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hb1.setOnKeyPressed(event -> {
					if (event.getCode().equals(KeyCode.ENTER)) {
						tikrinti(skaicius.getText(), ivedimoLaukas.getText(), primaryStage);
					};
				});
				skaicius.setVisible(false);
				ivedimoLaukas.setVisible(true);
			}
		});
		t1.start();
	}
}