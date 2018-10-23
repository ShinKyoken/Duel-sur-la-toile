package Plateforme.Connexion;
import Plateforme.Application.*;
import java.io.ByteArrayInputStream;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ActionBoutonLogin implements EventHandler<ActionEvent>{

    private ApplicationLogin appli;

    public ActionBoutonLogin(ApplicationLogin appli){
        this.appli=appli;
    }


    @Override
    /**
     * Méthode gérant les évènements de la page de connexion.
     */
    public void handle(ActionEvent e) {

        Button b = (Button) e.getSource();
        if(b.getText().equals("Connexion")){  //Si on appuie sur le bouton "Connexion" on procéde à la connexion de l'utilisateur
            if (appli.getIDMDP().containsKey(appli.getPseudo().getText())){ //Si pseudo se trouve dans la BD
                if(appli.getIDMDP().get(appli.getPseudo().getText()).equals(appli.getConnexion().cryptPass(appli.getMDP().getText()))){ //Si le mot de passe correspond
                    appli.getStage().close();
                    try {
                        DuelSurLaToile a = new DuelSurLaToile();
                        ResultSet val = appli.getConnexion().getProfil(appli.getPseudo().getText());
                        Boolean activer;
                        if (val.getString(4).equals("t")){
                            activer = true;
                        }
                        else{
                            activer = false;
                        }
                        Image avatarImage = new Image(new ByteArrayInputStream(val.getBytes(5)));
                        Utilisateur user = new Utilisateur(val.getInt(1), val.getString(2), val.getString(3), activer, avatarImage, val.getString(6), val.getInt(7));
                        a.setUtilisateur(user);
                        a.start(new Stage());
                        a.getConnexion().changerConnexionUtilisateur(user.getId(), true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }




                }
                else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur !");
                    alert.setHeaderText("Erreur de connexion");
                    alert.setContentText("Le mode de passe entré n'est pas valider !");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur !");
                alert.setHeaderText("Erreur de connexion");
                alert.setContentText("Le pseudo entré n'est pas valider !");
                alert.showAndWait();
            }
        }
        else if (b.getText().equals("Créer un compte")) {
            appli.getStage().close();
            try {
                new ApplicationRegister().start(new Stage());
            } catch (Exception e1) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur !");
                alert.setHeaderText("Erreur de connexion");
                alert.setContentText("Veuillez vérifier votre connexion");
                alert.showAndWait();
            }
        }

    }

}

