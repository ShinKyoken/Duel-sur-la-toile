package Plateforme.Partie;

import Plateforme.Application.DuelSurLaToile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.List;

public class ActionBouttonInvitationPartie implements EventHandler<ActionEvent> {

    private InvitationPartie invitationPartie;
    private DuelSurLaToile duelSurLaToile;

    public ActionBouttonInvitationPartie(DuelSurLaToile duelSurLaToile, InvitationPartie invitationPartie){
        this.invitationPartie=invitationPartie;
        this.duelSurLaToile=duelSurLaToile;
    }

    /**
     *
     * @param a
     */
    @Override
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();
        if(b.getText().equals("Accepter")) {
            int indice = 0;
            List<InvitationPartie> liste = this.duelSurLaToile.getPartie().getListeInvitationPartie().getListe();
            for(int i=0; i<liste.size();i++){
                if(liste.get(i).getIdentifiant()==(Integer)(b.getUserData())){
                    indice = i;
                }
            }
            this.duelSurLaToile.getPartie().getListeInvitationPartie().getListe().remove(indice);

            try {
                this.duelSurLaToile.getConnexion().creationPartie((Integer) b.getUserData(), this.duelSurLaToile);
                this.duelSurLaToile.getConnexion().refuserInvitPartie((Integer) b.getUserData());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}
            this.duelSurLaToile.getPartie().maj();
        }
        else{
            if(b.getText().equals("Refuser")){
                int indice = 0;
                List<InvitationPartie> liste = this.duelSurLaToile.getPartie().getListeInvitationPartie().getListe();
                for(int i=0; i<liste.size();i++){
                    if(liste.get(i).getIdentifiant()==(Integer)(b.getUserData())){
                        indice = i;
                    }
                }
                this.duelSurLaToile.getPartie().getListeInvitationPartie().getListe().remove(indice);
                try {
					this.duelSurLaToile.getConnexion().refuserInvitPartie((Integer) b.getUserData());
				} catch (SQLException e) {
					e.printStackTrace();
				}
                this.duelSurLaToile.getPartie().maj();
            }
        }
    }
}
