package Plateforme.Administration;

import Plateforme.Jeux.Jeu;
import javafx.collections.ListChangeListener;

public class ChangeTableViewListener implements ListChangeListener<Object>{
	
	private ListeJeux app;

	/**
	 * Le constructeur
	 * @param app l'application
	 */
	public ChangeTableViewListener(ListeJeux app){
		this.app = app;
	}
	
	@Override
	/**
	 * Méthode permettant de récupérer une valeur de la table.
	 */
	public void onChanged(Change<? extends Object> c) {
		int index = app.getListeJeux().getSelectionModel().getSelectedIndex();
		Jeu v = app.getListeJeux().getItems().get(index); 
		app.setJeuSelectionner(v);
	}

}
