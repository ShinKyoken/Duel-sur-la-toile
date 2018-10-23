package Plateforme.Administration;

import Plateforme.Jeux.Jeu;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collections;
import java.util.List;

public class ListeJeux extends ScrollPane {
	
	private Jeu jeuSelectionner;
	
	private TableView<Jeu> table;
	
	private List<Jeu> ls;

	/**
	 * Méthode permettant de créer un tableView contenant les jeux
	 * @param app l'application
	 * @param ls la liste des jeux
	 */
	public ListeJeux(AdministrateurJeu app, List<Jeu> ls) {
		
		this.ls = ls;
		
		Collections.sort(ls);
		
		this.table=new TableView<Jeu>();
		this.table.setEditable(true);
		
		this.setJeuSelectionner(ls.get(0));
		
		TableColumn<Jeu, Integer> id= new TableColumn<Jeu, Integer>("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("Id"));
		
		TableColumn<Jeu, String> jeu= new TableColumn<Jeu, String>("Jeu");
		jeu.setCellValueFactory(new PropertyValueFactory<>("nom"));
		
		TableColumn<Jeu, String> type= new TableColumn<Jeu, String>("Type du jeu");
		type.setCellValueFactory(new PropertyValueFactory<>("descRapide"));
		
		TableColumn<Jeu, String> etat= new TableColumn<Jeu, String>("Etat");
		etat.setCellValueFactory(new PropertyValueFactory<>("activer"));
		
		TableColumn<Jeu, String> run= new TableColumn<Jeu, String>("Lanceur");
		run.setCellValueFactory(new PropertyValueFactory<>("ClasseLanceur"));
		
		this.table.getColumns().addAll(id,jeu,type,etat, run);
                
		ObservableList<Jeu> data = FXCollections.observableArrayList(ls);

		table.setItems(data);

		ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();

		selectedCells.addListener(new ChangeTableViewListener(this));
		
		table.setStyle("-fx-alignment: CENTER;");
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	/**
	 * Retourne le TableView contenant les jeux
	 * @return
	 */
	public TableView<Jeu> getListeJeux () {
		return this.table;
	}

	/**
	 * Retourne le jeu sélectionné
	 * @return
	 */
	public Jeu getJeuSelectionner() {
		return this.jeuSelectionner;
	}

	/**
	 * Modifie le jeu sélectionné
	 * @param val Le jeu
	 */
	public void setJeuSelectionner(Jeu val) {
		jeuSelectionner = val;
	}

	/**
	 * Affiche le jeu sélectionné
	 */
	public void afficherJeuSelectionner() {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setContentText(this.jeuSelectionner + " selectionner !");
		a.show();
	}

}
