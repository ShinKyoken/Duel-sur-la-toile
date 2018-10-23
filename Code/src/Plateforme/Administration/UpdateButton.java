package Plateforme.Administration;

import Plateforme.Administration.Administrateur;
import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import java.sql.SQLException;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.util.converter.IntegerStringConverter;

public class UpdateButton implements EventHandler<ActionEvent>{


        private Administrateur app;
	private String indic;
        private String valid;
	public UpdateButton(Administrateur app){
		this.app = app;
                this.valid="t";

	}

	public void handle(ActionEvent a) {
        /**
         * Méthode prenant comme parametre un ActionEvent et qui permet de définir les évênements
         */
            if (!this.app.getIdLabel().getText().equals("ID")){
                if (this.app.getadminButton().isSelected()){
                    this.indic="Admin";
                }
                else if (this.app.getJoueurButton().isSelected()){
                    this.indic="Joueur";
                }
                if (!this.app.getadminButton().isSelected()){
                    if (this.app.getEtatBox().isSelected()){
                        this.valid="t";
                    }
                    else{
                        this.valid="f";
                        try{
                            this.app.getApp().getConnexion().destactiveUtilisateur(Integer.valueOf(this.app.getIdLabel().getText()));
                        }
                        catch (SQLException exception){
                            System.out.println("Erreur SQL : " + exception.getMessage());
			}
                    }
                    
                }
                else if (!this.app.getEtatBox().isSelected()) {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Un admin ne peu être désactivé");
                    alert.showAndWait();
                }
                try{
                    this.app.getApp().getConnexion().modifierUtilisateur(Integer.valueOf(app.getIdLabel().getText()),app.getPseudoField().getText(), app.getMailField().getText(), this.valid, this.indic);
                    }
                    catch (SQLException exception){
			System.out.println("Erreur SQL : " + exception.getMessage());
                    }
                this.app.majAffichage();
            }
            else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("un utilisateur doit etre selectionné");
                alert.showAndWait();
            }

        }

}
