package Plateforme;

import java.io.IOException;
import java.sql.SQLException;

import Jeux.IJeu;
import javafx.application.Platform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ExecutableErwan extends Application {
	public static void main(String[] args) throws Exception {
		launch(args);	
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Thread(() -> {
			Platform.runLater(() -> {
				try {
					IJeu j = LancerJeu.run("Mastermind", "LeJeu");
					j.rejoindrePartieEnCours(1, 2, 30);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
		}).start();
		
	}
}
