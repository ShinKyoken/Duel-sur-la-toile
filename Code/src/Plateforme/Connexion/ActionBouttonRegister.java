package Plateforme.Connexion;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ActionBouttonRegister implements EventHandler<ActionEvent>{
    private ApplicationRegister appR;

    public ActionBouttonRegister(ApplicationRegister app){
        this.appR=app;
    }

    @Override
    /**
     * Méthode gérant les évènements de la page d'inscription.
     */
    public void handle(ActionEvent e) {
        Button b = (Button) e.getSource();
        if(b.getText().equals("Inscription")){ //Si on appuie sur le bouton "Inscription" on procéde à l'inscription de l'utilisateur
            if(!appR.getListeUser().contains(appR.getPseudo().getText()) && appR.getPseudo().getText().length()<10){ //Si le pseudo n'est pas déjà présent et qu'il comporte moins de 10 caractéres
                if(appR.getMail().getText().equals(appR.getReMail().getText())){ //Si le mail et égal au mail de confirmation
                    if(appR.getMDP().getText().equals(appR.getReMDP().getText())){ //Si le mdp est égal au mdp de confirmation
                        try {
                            //Alors on essaye d'inserer les donner dans la BD
                            byte[] img;
                            File f= new File("./avatar_defaut.png");
                            img=Files.readAllBytes(f.toPath());
                            PreparedStatement ps = appR.getConnexion().prepareStatement("INSERT INTO UTILISATEUR(idUt, pseudoUt, emailUt, mdpUt, activeUt, avatarUt, nomRole) SELECT MAX(idUt)+1, ?, ?, ?, ?, ?, ? FROM UTILISATEUR");
                            ps.setString(1, appR.getPseudo().getText());
                            ps.setString(2, appR.getMail().getText());
                            ps.setString(3, appR.getConnexion().cryptPass(appR.getMDP().getText()));
                            ps.setString(4, String.valueOf('t'));
                            ps.setBytes(5, img);
                            ps.setString(6, "Joueur");
                            ps.executeUpdate();
                            appR.getStage().close();
                            try {
                                new ApplicationLogin().start(new Stage());
                            } catch (Exception e1) {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Erreur !");
                                alert.setHeaderText("Erreur d'incription");
                                alert.setContentText("Veuillez vérifier votre connexion");
                                alert.showAndWait();
                            }
                        } catch (SQLException | IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur !");
                        alert.setHeaderText("Erreur d'inscription");
                        alert.setContentText("Problème de confirmation du mot de passe");
                        alert.showAndWait();
                    }
                }
                else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur !");
                    alert.setHeaderText("Erreur d'inscription");
                    alert.setContentText("Problème de confirmation de l'adresse e-mail");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur !");
                alert.setHeaderText("Erreur d'inscription");
                alert.setContentText("Le pseudo existe déjà ou\ndépasse la limite de 10 caractéres");
                alert.showAndWait();
            }
        }
        else if (b.getText().equals("Déjà inscrit ?")){
            appR.getStage().close();
            try {
                new ApplicationLogin().start(new Stage());
            } catch (Exception e1) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur !");
                alert.setHeaderText("Erreur d'incription");
                alert.setContentText("Veuillez vérifier votre connexion");
                alert.showAndWait();
            }
        }
    }
}

