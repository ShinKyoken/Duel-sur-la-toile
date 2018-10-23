package Jeux.Puissance4;

import Jeux.IJeu;
import Plateforme.LancerJeu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author Tomoko
 */
public class RunTest extends Application {
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    IJeu j = LancerJeu.run("Puissance4", "Run");
                    j.rejoindrePartieEnCours(8, 1, 37);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    IJeu j = LancerJeu.run("Puissance4", "Run");
                    j.rejoindrePartieEnCours(1, 1, 37);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }
}
