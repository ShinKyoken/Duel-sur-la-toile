package Plateforme.Application;

import Plateforme.Connexion.ConnexionMySQL;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RunDuelSurLaToile extends Application{

    private Stage s;
    private ConnexionMySQL mysql;

    public static void main(String[] args){
		launch(args);
	}

    /**
     * Permet de lancer l'application
     * @param primaryStage
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
	public void start(Stage primaryStage) throws ClassNotFoundException, SQLException{
        this.s = primaryStage;
        s.setTitle("Duel Sur La Toile");
        s.setScene(run());
        s.setResizable(false);
        s.show();
        try {
            mysql = new ConnexionMySQL();
            mysql.connecter();
        }
        catch(SQLException | ClassNotFoundException | IOException err) {
            err.printStackTrace();
            Alerte.creationAlerte(err,"Erreur de Connexion !", "Il se pourrait que vous ne soyez\npas connecter à internet");
        }
	}

    /**
     *
     * @return Stage s
     */
    public Stage getStage() {
        return s;
    }

    /**
     * Crée la page d'accueil de l'application
     * @return windows
     */
    public Scene run(){
        //LABEL
        Label ltitre = new Label("DUEL SUR LA TOILE");
        ltitre.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Label lintro = new Label("Duel sur la Toile c'est :\n"
                                   + "> Une plateforme de jeux en ligne\n"
                                   + "> Des heures et des heures d'amusement\n"
                                   + "> Un moyen de rencontrer d'autre joueur comme vous\n"
                                   + "> Un service sécurisé et sûre\n"
                                   + "> Une application en continuel évolution\n"
                                   + "> Duel en 1vs1");

        //BUTTON
        Button bInscription = new Button("Pas encore de compte ?");
        Button bConnexion = new Button("Connectez-vous !");
        bInscription.setStyle("-fx-background-color:  #ffffff   ;");
        bConnexion.setStyle("-fx-background-color:  #ffffff   ;");

        //PANEL
        BorderPane panMain = new BorderPane();
        FlowPane panTOP = new FlowPane();
        FlowPane panCenter = new FlowPane();
        FlowPane panDown = new FlowPane();

        //SCENE
        Scene windows = new Scene(panMain, 600, 300);

        //TOP
        panTOP.getChildren().add(ltitre);
        panTOP.setAlignment(Pos.CENTER);
        panTOP.setPadding(new Insets(20));

        //CENTER
        panCenter.getChildren().add(lintro);
        panCenter.setAlignment(Pos.CENTER);

        //DOWN
        panDown.getChildren().add(bConnexion);
        panDown.getChildren().add(bInscription);
        bConnexion.setMaxWidth(Double.MAX_VALUE);
        bInscription.setMaxWidth(Double.MAX_VALUE);
        panDown.setOrientation(Orientation.VERTICAL);
        panDown.setAlignment(Pos.TOP_CENTER);
        panDown.setPadding(new Insets(15,5,6,11));
        panDown.setVgap(5);

        //ADD
        panMain.setTop(panTOP);
        panMain.setCenter(panCenter);
        panMain.setBottom(panDown);
        panMain.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));

        ActionBouttonRun act = new ActionBouttonRun(this);
        bConnexion.setOnAction(act);
        bInscription.setOnAction(act);

        return windows;
    }
}
