package Plateforme.Profil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.List;
/**
 * Cette classe implémente EventHandler et contient la fonction asociée aux boutons servant à répondre aux invitations
 * à être amis.
 * @author theodore
 */
public class ActionBoutonInvitation implements EventHandler<ActionEvent> {
    /**
     * le AjouterAmi auquel est associé l'ActionBoutonAjouterAmi
     */
    private InvitationAmiProfil invitationAmi;

    public ActionBoutonInvitation(InvitationAmiProfil invitationAmi){
        this.invitationAmi = invitationAmi;
    }

    /**
     * Cette fonction est appelée lorsqu'un bouton de InvitationProfil est utilisé,
     * Si c'est un bouton "Acceter", les deux utilisateurs lié à l'invitation devienne amis et l'invitation est supprimée
     * Sinon, l'invitation est seulement supprimé
     * @param a
     */
    @Override
    public void handle(ActionEvent a){
        Button b = (Button) a.getSource();
        Invitation invit = (Invitation) b.getUserData();
        if (b.getText().equals("Accepter")){
            try {
                invitationAmi.getConnexion().accepterInvit(invit);
            }
            catch(SQLException e){
                System.out.println("Erreur de syntaxe SQL");
            }
        }
        else{
            try {
                invitationAmi.getConnexion().refuserInvit(invit);
            }
            catch(SQLException e){
                System.out.println("Erreur de syntaxe SQL");
            }
        }
        try {
            List<Invitation> listeInvit = invitationAmi.getConnexion().getInvitations(invitationAmi.getPlateforme().getUtilisateur());
            ListeInvitation nLs = new ListeInvitation(invitationAmi, listeInvit);
            invitationAmi.setListeInvit(nLs);
            invitationAmi.majInvit();
        }
        catch(SQLException e){
            System.out.println("Erreur de syntaxe SQL");
        }
    }
}
