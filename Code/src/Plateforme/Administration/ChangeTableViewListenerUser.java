package Plateforme.Administration;

import Plateforme.Administration.Administrateur;
import Plateforme.Application.Utilisateur;
import javafx.collections.ListChangeListener;

public class ChangeTableViewListenerUser implements ListChangeListener<Object>{
	
	private ListeUtilisateur app;
	private Utilisateur v;

    /**
     * Le constructeur
     * @param app l'application
     */
	public ChangeTableViewListenerUser(ListeUtilisateur app){
		this.app = app;
	}
	
	@Override
    /**
     * Méthode permettant de récuperer une valeur de la table
     */
	public void onChanged(Change<? extends Object> c) {
                
		int index = app.getListeUtil().getSelectionModel().getSelectedIndex();
		this.v = (Utilisateur) app.getListeUtil().getItems().get(index); 
		app.getAdministrateur().getIdLabel().setText(String.valueOf(this.v.getId()));
                app.getAdministrateur().getPseudoField().setText(this.v.getPseudoUt());
                app.getAdministrateur().getMailField().setText(this.v.getEmailUt());
                if(this.v.isAdmin().equals("Admin")){
                    app.getAdministrateur().getadminButton().setSelected(true);
                }
                else {
                    app.getAdministrateur().getJoueurButton().setSelected(true);
                }
                
                if (this.v.isActiveUt()){
                    app.getAdministrateur().getEtatBox().setSelected(true);
                }
                else {
                    app.getAdministrateur().getEtatBox().setSelected(false);
                }
                    
	}
        public Utilisateur getuser(){
            return this.v;
        }
        

}
