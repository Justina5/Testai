package testai;

import java.util.concurrent.ThreadLocalRandom;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Reakcija implements Bendri {
	private static final String[][] ZODZIAI = {{"RAUDONA","0xff0000ff"}, {"GELTONA", "0xffd700ff"}, {"JUODA", "0x000000ff"}, {"ŽALIA", "0x008000ff"}, {"MĖLYNA", "0x0000ffff"}, {"PILKA", "0x808080ff"}, {"VIOLETINĖ", "0xee82eeff"}};
	HBox erdveRodytiZodzius;
	private int pozicija = 0;
	final int AMOUNT = 10;
	private long a;
	private long b;
	private boolean test;
	private boolean[] results = new boolean[AMOUNT];
	private long[] time = new long[AMOUNT];
	private int riba = 8;
	private Text izanga = new Text("Šis testas skirtas matuoti Jūsų reakcijos laiką. Programa parodys 2 žodžius.\n Jums reikės pastebėti, ar bent vienas žodis įvardina vieno iš jų spalvą.\n"
			+ "Jei pastebėsite, kad TAIP - spauskite mygtuką \"T\", jei NE - spauskite \"N\".\n Šią užduotį Jums reikės atlikti 10 kartų. Kai būsite pasiruošę spauskite mygtuką Pradėti.");
	private Text klaidosPranesimas = new Text("Teisingų atsakymų skaičius mažiau nei " + riba + ". Turite atsakyti bent " + riba + " kartus teisingai.");

	public void setResults(boolean result, int i) {
		this.results[i] = result;
	}

	public void setTime(long time, int i) {
		this.time[i] = time;
	}

	public long[] getTime() {
		return this.time;
	}

	public String[][] get2Zodziai() {
		int  max = ZODZIAI.length;
		String[] zodis1 = ZODZIAI[ThreadLocalRandom.current().nextInt(0, max)];
		String[] zodis2 = ZODZIAI[ThreadLocalRandom.current().nextInt(0, max)];
		while (zodis1[0].equals(zodis2[0])){
			zodis2 = ZODZIAI[ThreadLocalRandom.current().nextInt(0, max)];
		}
		return new String[][] {zodis1, zodis2};
	}

	public void setZodisRandColor(Text zodis) {
		int rnd = ThreadLocalRandom.current().nextInt(0, ZODZIAI.length);
		switch (rnd) {
		case 0:
			zodis.setFill(Color.BLACK);
			break;
		case 1:
			zodis.setFill(Color.GOLD);
			break;
		case 2:
			zodis.setFill(Color.GREEN);
			break;
		case 3:
			zodis.setFill(Color.BLUE);
			break;
		case 4:
			zodis.setFill(Color.GRAY);
			break;
		case 5:
			zodis.setFill(Color.VIOLET);
			break;
		default:
			zodis.setFill(Color.RED);
			break;
		}		
	}

	public HBox set2Words () {
		String[][] zodziai2 = this.get2Zodziai();
		Text zodis1 = new Text(zodziai2[0][0]);
		Text zodis2 = new Text(zodziai2[1][0]);
		this.setZodisRandColor(zodis1);
		this.setZodisRandColor(zodis2);
		zodis1.setStyle("-fx-font: bold 30 arial;");
		zodis2.setStyle("-fx-font: bold 30 arial;");
		this.test(zodziai2, zodis1, zodis2);
		HBox zodziai = new HBox(10, zodis1, zodis2);
		zodziai.setAlignment(Pos.CENTER);
		return zodziai;
	}

	public void test(String[][] zodziai2, Text z1, Text z2) {
		this.test = (zodziai2[0][1].equals(z1.getFill().toString())
				|| zodziai2[1][1].equals(z2.getFill().toString())
				|| zodziai2[0][1].equals(z2.getFill().toString())
				|| zodziai2[1][1].equals(z1.getFill().toString()));
	}

	@Override
	public void rodytiPranesima(Stage primaryStage, Text pranesimas) {
		Stage pranesimoLaukas = new Stage();
		pranesimoLaukas.setTitle("Pranešimas");
		pranesimoLaukas.centerOnScreen();
		pranesimoLaukas.initModality(Modality.APPLICATION_MODAL);
		VBox dialogVbox = new VBox();
		dialogVbox.getChildren().add(pranesimas);
		dialogVbox.getChildren().add(testi);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.setPadding(new Insets(10));		
		VBox.setMargin(pranesimas, new Insets(50, 20, 50, 20));

		testi.setOnAction(action -> {
			a = System.currentTimeMillis();
			pranesimoLaukas.close();
		});

		Scene dialogScene = new Scene(dialogVbox);
		pranesimoLaukas.setScene(dialogScene);
		pranesimoLaukas.show();
	}

	@Override
	public void rodytiTestoInstrukcija(Stage primaryStage) {
		izanga.setFont(fontas);
		start.setOnAction(action -> {
			this.pradetiTesta(primaryStage);
		});

		GridPane hb1 = new GridPane();
		hb1.add(izanga, 0, 0);
		hb1.add(start, 0, 1);
		hb1.setPadding(new Insets(10));
		GridPane.setMargin(start, new Insets(20, 100, 0, 400));
		Scene testoErdve = new Scene(hb1, 900, 250);
		primaryStage.setScene(testoErdve);
	}

	@Override
	public void pradetiTesta(Stage primaryStage) {
		erdveRodytiZodzius = set2Words();
		Scene testoErdve = primaryStage.getScene();
		testoErdve.setRoot(erdveRodytiZodzius);
		a = System.currentTimeMillis();

		testoErdve.setOnKeyPressed(event -> {
			b = System.currentTimeMillis();
			setTime(b - a, pozicija);
			switch (event.getCode()) {
			case T:
				setResults(test == true, pozicija++ );
				break;
			case N:
				setResults(test == false, pozicija++ );
				break;
			default:
				Text pr = new Text("Galite spausti tik T arba N klaviatūroje.");
				rodytiPranesima(primaryStage, pr);
			}
			if (pozicija < AMOUNT) {
				erdveRodytiZodzius = set2Words();
				testoErdve.setRoot(erdveRodytiZodzius);
				a = System.currentTimeMillis();
			} else if (pozicija == AMOUNT) {
				int kiekTeisingu = 0;
				for (Boolean b : results) {
					if (b) kiekTeisingu++;
				}
				if (kiekTeisingu >= riba) {
					long timeSum = 0;
					for (long b : getTime()) {
						timeSum += b;
					}
					Bendri.rezultatai[1] = (((double) timeSum / AMOUNT) / 1000);
					new Koncentracija().rodytiTestoInstrukcija(primaryStage);
				} else {
					rodytiPranesima(primaryStage, klaidosPranesimas);
					pozicija = 0;
				}
			}
		});
	}
}
