package Plateforme.Profil;

import Plateforme.Application.Alerte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class ActionBoutonTriInvit implements EventHandler<ActionEvent> {

    private InvitationAmiProfil profil;

    public ActionBoutonTriInvit(InvitationAmiProfil profil){
        this.profil = profil;
    }

    public void handle(ActionEvent a){
        Button b = (Button) a.getSource();
        if(b.getUserData().equals("date")){
            this.profil.getListeInvit().triDate();
            try{
                this.profil.majInvit();
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
        else if(b.getUserData().equals("az")){
            this.profil.getListeInvit().triAZ();
            try{
                this.profil.majInvit();
            }
            catch(SQLException e){
                Alerte.creationAlerte(e);
            }
        }
    }
}
