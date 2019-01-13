package testai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Koncentracija implements Bendri {
	private long trukme;
	private Text instrukcija = new Text("Šis testas skirtas įvertinti Jūsų dėmesio koncentraciją.\n Testo metu žaisite žaidimą, kuriame pelės pagalba galėsite valdyti rutuliuką.\n"
			+ " Kitos figūros judės automatiškai turėsite stengtis, kuo ilgiau jų išvengti.\n Šį žaidimą galėsite žaisti ne daugiau kaip 10 kartų.");

	double orgSceneX, orgSceneY;
	long a, b;
	static int bandymuSkaicius = 10;
	int greitis = 4;

	boolean arPriliete = false;
	// Mano figura
	Circle apskritimas = new Circle(20, Color.ORANGE);
	
	// Kitos figuros
	Circle apskritimas2 = new Circle(60);
	Rectangle kvadratas = new Rectangle(250, 250, 100, 100);
	Ellipse elipse = new Ellipse(500, 500, 35, 50);
	Ellipse elipse2 = new Ellipse(10, 50, 80.0, 50.0);
	Polygon poligonas = new Polygon(200.0, 50.0, 400.0, 50.0, 450.0, 150.0, 400.0, 250.0, 200.0, 250.0);
	
	PathTransition transitionApskritimas = new PathTransition();
	PathTransition transitionElipse = new PathTransition();
	PathTransition transitionElipse2 = new PathTransition();
	PathTransition transitionKvadratas = new PathTransition();
	PathTransition transitionPoligonas = new PathTransition();

	@Override
	public void rodytiPranesima(Stage primaryStage, Text pranesimas) {
		
		Stage pranesimoLaukas = new Stage();
		pranesimoLaukas.setTitle("Pranešimas");
		pranesimoLaukas.centerOnScreen();
		pranesimoLaukas.initModality(Modality.APPLICATION_MODAL);
		pranesimoLaukas.initOwner(primaryStage);
		Button pakartoti = new Button("Pakartoti");
		GridPane dialogHbox = new GridPane();
		dialogHbox.add(pranesimas, 0, 0);
		dialogHbox.add(pakartoti, 0, 1);
		pakartoti.setOnAction(action -> {
			arPriliete = false;
			pranesimoLaukas.close();
			pradetiTesta(primaryStage);
		});
		if (bandymuSkaicius == 0) {
			pakartoti.setDisable(true);
		}
		testi.setOnAction(action -> {
			Bendri.rezultatai[2] = (double) trukme / 1000;
			pranesimoLaukas.close();
			new Atmintis().rodytiTestoInstrukcija(primaryStage);
		});
		dialogHbox.add(testi, 1, 1);
		VBox.setMargin(pranesimas, new Insets(50, 20, 50, 20));
		Scene dialogScene = new Scene(dialogHbox);
		pranesimoLaukas.setScene(dialogScene);
		pranesimoLaukas.show();
	}

	private void stop(Stage primary, Circle apskritimas, PathTransition[] array) {
		b = System.currentTimeMillis();
		for (int i = 0; i < array.length; i++) {
			array[i].stop();
		}
		apskritimas.setDisable(true);
		DateFormat formatter = new SimpleDateFormat("ss.SSS");
		long skirtumas = b-a;
		if (this.trukme < skirtumas) {
			this.trukme = skirtumas;
		}
		
		Text tekstasPranesimui = new Text("Jums pavyko išsilaikyti: " + formatter.format(skirtumas).toString() + "\nJei norite pakartoti testą spauskite \"Pakartoti\", jei norite tęsti programą - spauskite \"Tęsti\".\n"
				+ "Jums liko " + String.valueOf(--bandymuSkaicius) + " bandymai.");
		rodytiPranesima(primary, tekstasPranesimui);
	}


	//@Override
	public void rodytiTestoInstrukcija(Stage primaryStage) {
		instrukcija.setFont(fontas);
		GridPane izanginis = new GridPane();
		izanginis.add(instrukcija, 0, 0);
		izanginis.add(start, 0, 1);
		GridPane.setMargin(start, new Insets(20, 100, 0, 500));
		Scene testoErdve = new Scene(izanginis, 1000, 250);
		start.setOnAction(action -> {
			pradetiTesta(primaryStage);
		});
		primaryStage.setScene(testoErdve);
	}


	@Override
	public void pradetiTesta(Stage primaryStage) {
		apskritimas.setCenterX(975);
		apskritimas.setCenterY(100);
		apskritimas.setDisable(false);
		AnchorPane root = new AnchorPane();
		root.setPadding(new Insets(100));
		
		//StackPane figura1 = new StackPane(elipse);
		StackPane figura2 = new StackPane(kvadratas);
		StackPane figura3 = new StackPane(elipse2);
		StackPane figura4 = new StackPane(poligonas);
		StackPane figura5 = new StackPane(apskritimas2);
		//root.getChildren().add(figura1);
		root.getChildren().add(figura2);
		root.getChildren().add(figura3);
		root.getChildren().add(figura4);
		root.getChildren().add(figura5);
		root.getChildren().add(apskritimas);
		root.getChildren().add(elipse);
		Scene zaidimoErdve = new Scene(root, 1000, 600, Color.CORNSILK);

		this.a = System.currentTimeMillis();
		
		transitionApskritimas.setNode(apskritimas2);
		transitionApskritimas.setDuration(Duration.seconds(greitis));
		transitionApskritimas.setAutoReverse(true);
		transitionApskritimas.setPath(new Rectangle(850, 400));
		transitionApskritimas.setCycleCount(PathTransition.INDEFINITE);
		transitionApskritimas.play();
		
		transitionElipse.setNode(elipse);
		transitionElipse.setDuration(Duration.seconds(greitis));
		transitionElipse.setPath(new Polyline(100, 100, 268, 575, 575, 100, 575, 500, 100, 500));
		transitionElipse.setAutoReverse(true);
		transitionElipse.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		transitionElipse.setCycleCount(PathTransition.INDEFINITE);
		transitionElipse.play();

		transitionElipse2.setNode(elipse2);
		transitionElipse2.setDuration(Duration.seconds(greitis+1));
		transitionElipse2.setPath(new Polyline(500, 100, 268, 575, 975, 100, 875, 500, 500, 500));
		transitionElipse2.setAutoReverse(true);
		transitionElipse2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		transitionElipse2.setCycleCount(PathTransition.INDEFINITE);
		transitionElipse2.play();

		transitionKvadratas.setNode(kvadratas);
		transitionKvadratas.setDuration(Duration.seconds(greitis));
		transitionKvadratas.setPath(new Polyline(400, 300, 800, 575, 200, 570, 300, 300, 400, 300));
		transitionKvadratas.setCycleCount(PathTransition.INDEFINITE);
		transitionKvadratas.play();
		
		transitionPoligonas.setNode(poligonas);
		transitionPoligonas.setDuration(Duration.seconds(greitis));
		transitionPoligonas.setPath(new Circle(600, 300, 350));
		transitionPoligonas.setCycleCount(PathTransition.INDEFINITE);
		transitionPoligonas.play();

		PathTransition[] pta = {transitionElipse, transitionElipse2, transitionKvadratas, transitionPoligonas, transitionApskritimas};
		Shape[] visosFiguros = {apskritimas2, elipse, elipse2, kvadratas, poligonas};
		
		apskritimas.setCursor(Cursor.HAND);
		apskritimas.setOnMousePressed((t) -> {
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();
		});
		apskritimas.setOnMouseDragged((t) -> {
			double offsetX = t.getSceneX() - orgSceneX;
			double offsetY = t.getSceneY() - orgSceneY;

			Circle r = (Circle) (t.getSource());
			
			if ((r.getCenterX() >= apskritimas.getRadius())
					&& r.getCenterY() >= apskritimas.getRadius()
					&& r.getCenterX() + apskritimas.getRadius() < zaidimoErdve.getWidth()
					&& r.getCenterY() + apskritimas.getRadius() < zaidimoErdve.getHeight()				
					)
			{
				r.setCenterX(r.getCenterX() + offsetX);
				r.setCenterY(r.getCenterY() + offsetY);
				orgSceneX = t.getSceneX();
				orgSceneY = t.getSceneY();
			} else {
				stop(primaryStage, apskritimas, pta);
			}
		});

		for (Shape figura: visosFiguros) {
			figura.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
				@Override
				public void changed(ObservableValue<? extends Bounds> observable,
						Bounds oldValue, Bounds newValue) {
					if (((Path) Shape.intersect(apskritimas, figura)).getElements().size() > 0 && !arPriliete) {
						arPriliete = true;
						stop(primaryStage, apskritimas, pta);
					}
				}
			});
		}
		primaryStage.setScene(zaidimoErdve);		
	}
}