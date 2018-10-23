/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Administration;

import Plateforme.Application.Utilisateur;
import Plateforme.Profil.ActionBoutonAjouterAmi;
import java.io.ByteArrayInputStream;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author cizeau
 */
public class ListeUtilisateur extends ScrollPane {

    private TableView<Utilisateur> table;

    private Administrateur app;

    /**
     * Méthode permettant de créer un TableView contenant les utilisateurs.
     * @param app l'application
     * @param ls la liste d'utilisateur
     */
    public ListeUtilisateur(Administrateur app,List<Utilisateur> ls){
       
        this.app=app;
        this.table=new TableView<>();
        this.table.setEditable(true);
        
        TableColumn<Utilisateur, String> idU= new TableColumn<>("ID");
        idU.setCellValueFactory(
                new PropertyValueFactory<>("Id"));
        idU.setMinWidth(50);
        TableColumn<Utilisateur, String> psd= new TableColumn<>("Pseudo");
        psd.setCellValueFactory(
                new PropertyValueFactory<>("pseudoUt"));
        psd.setMinWidth(100);
        TableColumn<Utilisateur, String> email= new TableColumn<>("Email");
        email.setCellValueFactory(
                new PropertyValueFactory<>("emailUt"));
        email.setMinWidth(300);
        TableColumn<Utilisateur, Boolean> etat= new TableColumn<>("Etat");
        etat.setCellValueFactory(
                new PropertyValueFactory<>("activeUt"));
        etat.setMinWidth(100);
        
        TableColumn<Utilisateur, String> role= new TableColumn("Role");
        role.setCellValueFactory(
                new PropertyValueFactory<>("admin"));
        role.setCellFactory(ComboBoxTableCell.forTableColumn());
        role.setMinWidth(100);
               
        this.table.getColumns().addAll(idU,psd,email,etat,role);
        
        ObservableList<Utilisateur> data = FXCollections.observableArrayList(ls);
        
        this.table.setItems(data);
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.table.setTableMenuButtonVisible(true);
        
        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();

		selectedCells.addListener(new ChangeTableViewListenerUser(this));
    }

    /**
     * Retourne le TableView contenant les utilisateurs
     * @return
     */
    public TableView getListeUtil(){
        return this.table;
    }

    public Administrateur getAdministrateur(){
        return this.app;
    }
    
    
}
