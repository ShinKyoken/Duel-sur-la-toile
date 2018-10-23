package Plateforme.Connexion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * Classe de la fenêtre d'inscription
 *
 */

public class ApplicationRegister extends Application{

    private TextField tpseudo;

    private TextField tmail;

    private TextField tremail;

    private PasswordField tmdp;

    private PasswordField tremdp;

    private static List<String> listeUsers;

    private static PreparedStatement state;

    private Stage s;

    private ConnexionMySQL conn;


    /**
     * Méthode permettant de lancer l'ihm
     * @param args
     */
    public static void main(String[] args){
        //Lancement de l'IHM
        launch(args);
    }

    /**
     * Méthode gérant l'affichage de la fenètre d'inscription
     * @return
     */
    public Scene Register(){

        //LABEL
        Label lpseudo = new Label("Pseudo : ");
        Label lmail = new Label("Adresse email : ");
        Label lremail = new Label("Confirmation\nadresse email : ");
        Label lmdp = new Label("Mot de passe : ");
        Label lremdp = new Label("Confirmation\nmot de passe : ");

        //TEXTFIELD
        tpseudo = new TextField();
        tmail = new TextField();
        tremail = new TextField();
        tmdp = new PasswordField();
        tremdp = new PasswordField();

        //BUTTON
        Button bConnexion = new Button("Déjà inscrit ?");
        Button bValider = new Button("Inscription");
        bConnexion.setMaxWidth(Double.MAX_VALUE);
        bValider.setMaxWidth(Double.MAX_VALUE);
        bConnexion.setStyle("-fx-background-color:  #ffffff   ;");
        bValider.setStyle("-fx-background-color:  #ffffff   ;");

        //PANEL
        BorderPane panMain = new BorderPane();
        FlowPane panTOP = new FlowPane();
        GridPane panCenter = new GridPane();

        //SCENE
        Scene windows = new Scene(panMain, 600,300);


        //CENTER
        bValider.setPrefWidth(100);
        panCenter.add(lpseudo, 2, 1);
        panCenter.add(lmail, 2, 2);
        panCenter.add(lremail, 2, 3);
        panCenter.add(lmdp, 2, 4);
        panCenter.add(lremdp, 2, 5);
        panCenter.add(tpseudo, 3, 1);
        panCenter.add(tmail, 3, 2);
        panCenter.add(tremail, 3, 3);
        panCenter.add(tmdp, 3, 4);
        panCenter.add(tremdp, 3, 5);
        panCenter.add(bValider, 3, 6);
        panCenter.add(bConnexion, 3, 7);
        panCenter.setAlignment(Pos.CENTER);
        panCenter.setVgap(5);

        //ADD
        panMain.setTop(panTOP);
        panMain.setCenter(panCenter);
        panMain.setBackground(new Background(new BackgroundFill(Color.rgb( 205, 205, 205 ), null, null)));

        ActionBouttonRegister abo = new ActionBouttonRegister(this);
        bValider.setOnAction(abo);
        bConnexion.setOnAction(abo);

        return windows;
    }

    /**
     * Lancement de l'IHM final
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws FileNotFoundException
     */

    @Override
    /**
     * Paramétrage de l'affichage
     */
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{

        conn = new ConnexionMySQL();
        conn.connecter();
        listeUsers = conn.getListeUser();
        s = primaryStage;
        s.setTitle("Duel Sur La Toile [Register] (ALPHA VERSION)");
        s.setScene(Register());
        s.setResizable(false);
        s.show();
    }

    /**
     * Méthode qui permet de recuperer plus tard le pseudo entrée dans le champs de texte
     */

    public TextField getPseudo(){return this.tpseudo;}

    /**
     * Méthode qui permet de recuperer plus tard le mail entrée dans le champs de texte
     */

    public TextField getMail(){return this.tmail;}

    /**
     * Méthode qui permet de recuperer plus tard le mail pour confirmation entrée dans le champs de texte
     */

    public TextField getReMail(){return this.tremail;}

    /**
     * Méthode qui permet de recuperer plus tard le mot de passe entré dans le champs de texte
     */

    public TextField getMDP(){return this.tmdp;}

    /**
     * Méthode qui permet de recuperer plus tard le mot de passe de confirmation entré dans le champs de texte
     */

    public TextField getReMDP(){return this.tremdp;}

    /**
     * Méthode qui permet de recuperer plus tard la liste d'utilisation récupérer au lancement de l'IHM
     */

    public List<String> getListeUser(){System.out.println(listeUsers);return ApplicationRegister.listeUsers;}

    /**
     * Méthode qui permet de recuperer plus tard le support de connexion vers la base de donnée
     */

    public PreparedStatement getStat(){return ApplicationRegister.state;}

    /**
     * Méthode qui permet de récuperer le Stage ( c'est a dire la fenetre)
     */

    public Stage getStage() {
        return s;
    }

    /**
     * Méthode qui permet de récuperer la connexion
     */
    public ConnexionMySQL getConnexion() {
        return this.conn;
    }
}