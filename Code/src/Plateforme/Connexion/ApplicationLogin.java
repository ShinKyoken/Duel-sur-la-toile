package Plateforme.Connexion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ApplicationLogin extends Application {

    private static TextField tpseudo;

    private PasswordField tmdp;

    private static HashMap<String, String> idMDP;

    private Stage s;

    private ConnexionMySQL conn;


    /**
     * Méthode permettant de lancer l'ihm
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Méthode permettant de créer les différents objets qui sont utile dans l'affichage de la page de connexion.
     * @return
     */
    public Scene login() {

        //LABEL
        Label lpseudo = new Label("Pseudo : ");
        Label lmdp = new Label("Mot de passe : ");

        Hyperlink lmdpO = new Hyperlink("Mot de passe oublié");
        ActionHyperLink acth = new ActionHyperLink(this);
        lmdpO.setOnAction(acth);

        //TEXTFIELD
        tpseudo = new TextField();
        tmdp = new PasswordField();

        //BUTTON
        Button bInscription = new Button("Créer un compte");
        Button bValider = new Button("Connexion");
        bInscription.setStyle("-fx-background-color:  #ffffff   ;");
        bValider.setStyle("-fx-background-color:  #ffffff   ;");
        bInscription.setMaxWidth(Double.MAX_VALUE);
        bValider.setMaxWidth(Double.MAX_VALUE);

        //PANEL
        BorderPane panMain = new BorderPane();
        FlowPane panTOP = new FlowPane();
        GridPane panCenter = new GridPane();

        //SCENE
        Scene windows = new Scene(panMain, 600,300);

        //CENTER
        panCenter.add(lpseudo, 2, 1);
        panCenter.add(lmdp, 2, 2);
        panCenter.add(tpseudo, 3, 1);
        panCenter.add(tmdp, 3, 2);
        panCenter.add(bValider, 3, 3);
        panCenter.add(bInscription, 3, 4);
        panCenter.add(lmdpO, 2, 5);
        panCenter.setAlignment(Pos.CENTER);
        panCenter.setVgap(5);

        //ADD
        panMain.setTop(panTOP);
        panMain.setCenter(panCenter);
        panMain.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));

        ActionBoutonLogin abo = new ActionBoutonLogin(this);
        bValider.setOnAction(abo);
        bInscription.setOnAction(abo);

        return windows;
    }

    @Override
    /**
     * Méthode permettant de paramétrer l'affichage
     */
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{

        conn = new ConnexionMySQL();
        conn.connecter();
        idMDP = conn.getListeIDEtMDP();
        this.s=primaryStage;
        s.setTitle("Duel Sur La Toile [LOGIN] (ALPHA VERSION)");
        s.setScene(login());
        s.setResizable(false);
        s.show();
    }

    /**
     * Retourne le Stage
     * @return
     */
    public Stage getStage() {
        return s;
    }

    /**
     * Retourne le pseudo
     * @return
     */
    public TextField getPseudo(){
        return ApplicationLogin.tpseudo;
    }

    /**
     * Retourne le mot de passe
     * @return
     */
    public TextField getMDP(){
        return this.tmdp;
    }

    /**
     * Retourne le dictionnaire contenant les id et mot de passe des utilisateurs
     * @return
     */
    public HashMap<String, String> getIDMDP(){
        return ApplicationLogin.idMDP;
    }

    /**
     * Retourne la connexion
     * @return
     */
    public ConnexionMySQL getConnexion() {
        return conn;
    }


}