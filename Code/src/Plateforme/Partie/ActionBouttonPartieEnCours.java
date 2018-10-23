package Plateforme.Partie;

import Plateforme.Application.DuelSurLaToile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActionBouttonPartieEnCours implements EventHandler<ActionEvent> {

    private PartieEnCours PartieEnCours;
    private DuelSurLaToile duelSurLaToile;

    public ActionBouttonPartieEnCours(DuelSurLaToile duelSurLaToile, PartieEnCours PartieEnCours){
        this.PartieEnCours=PartieEnCours;
        this.duelSurLaToile=duelSurLaToile;
    }

    @Override
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();
        if(b.getText().equals("Rejoindre")) {
            System.out.println(this.PartieEnCours.getHeight());
            System.out.println(this.PartieEnCours.getWidth());
            try {
                this.duelSurLaToile.getConnexion().rejoindrePartie((Integer) b.getUserData(), this.duelSurLaToile);
            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            this.duelSurLaToile.getPartie().maj();
        }
        else{
            if(b.getText().equals("Abandonner")){
                int indice = 0;
                List<PartieEnCours> liste = this.duelSurLaToile.getPartie().getListePartieEnCours().getListe();
                for(int i=0; i<liste.size();i++){
                    if(liste.get(i).getIdentifiant()==(Integer)(b.getUserData())){
                        indice = i;
                    }
                }
                this.duelSurLaToile.getPartie().getListePartieEnCours().getListe().remove(indice);
                try {
					this.duelSurLaToile.getConnexion().abandonnerJeu((Integer) b.getUserData());
				} catch (SQLException e) {
					e.printStackTrace();
				}
                this.duelSurLaToile.getPartie().maj();
            }
        }
    }
}
