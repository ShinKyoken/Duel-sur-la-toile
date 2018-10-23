package Plateforme.Administration;

import java.io.File;
import java.sql.SQLException;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Jeux.TypeJeu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ModifierJeu extends BorderPane{
	
	private DuelSurLaToile app;
	
	private TextField nom;
	private TextArea description;
	private TextField classePrincipale;

	private Label fichierS;
	private Label imageS;
	
	private int idType;
	private String nomVal;
	private String descriptionVal;
	private String classeVal;
	private File imageVal;
	private File fichierVal;
	private int idJeu;
	
	private ComboBox<TypeJeu> type;

	/**
	 * Méthode permettant de gérer l'affichage de la page de modifiaction du jeu.
	 * @param app l'application
	 * @param idJeu l'id du jeu
	 * @param idType l'id du type du jeu
	 * @param nomVal le nom du jeu
	 * @param descriptionVal la description du jeu
	 * @param classeVal la classe principale du jeu
	 * @throws SQLException
	 */
	public ModifierJeu(DuelSurLaToile app, int idJeu, int idType, String nomVal, String descriptionVal, String classeVal) throws SQLException {
		this.app=app;
		
		this.idJeu = idJeu;
		
		ModifierJeuAction act = new ModifierJeuAction(this);
		
		Label nomL = new Label("Nom du jeu : ");
		Label descrL = new Label("Description du jeu : ");
		Label classe = new Label("Classe principale");
		this.fichierS = new Label("Aucun fichier");
		this.imageS = new Label("Aucune image");

		this.nom=new TextField(nomVal);
		this.description=new TextArea(descriptionVal);
		this.classePrincipale=new TextField(classeVal);

		Button image=new Button("Ajouter l'image");
		Button fichier=new Button("Ajouter le fichier du jeu");
		Button valider = new Button("Valider");
		Button annuler = new Button("Retour");

		image.setUserData("image");
		fichier.setUserData("fichier");
		valider.setUserData("valider");
		annuler.setUserData("annuler");

		image.setOnAction(act);
		fichier.setOnAction(act);
		valider.setOnAction(act);
		annuler.setOnAction(act);

		HBox n = new HBox();
		n.getChildren().add(nomL);
		n.getChildren().add(nom);
		n.setAlignment(Pos.CENTER);

		HBox d = new HBox();
		d.getChildren().add(descrL);
		d.getChildren().add(description);
		d.setAlignment(Pos.CENTER);

		HBox c = new HBox();
		c.getChildren().add(classe);
		c.getChildren().add(classePrincipale);
		c.setAlignment(Pos.CENTER);

		HBox b = new HBox();
		b.getChildren().addAll(image, fichier);
		b.setAlignment(Pos.CENTER);

		HBox avert = new HBox();
		avert.getChildren().addAll(imageS, fichierS);
		avert.setSpacing(20);
		avert.setAlignment(Pos.CENTER);

		type = new ComboBox<TypeJeu>();
		type.getItems().addAll(app.getConnexion().getTypeJeu());
		type.getSelectionModel().select(idType-1);

		VBox center = new VBox();
		center.getChildren().addAll(n, d, c, type, b, avert);
		center.setAlignment(Pos.CENTER);
		center.setSpacing(20);
		center.setPadding(new Insets(10,10,10,10));

		HBox bas = new HBox();
		bas.getChildren().addAll(valider, annuler);
		bas.setAlignment(Pos.CENTER);

		this.setCenter(center);
		this.setBottom(bas);

		BorderPane.setMargin(center, new Insets(10,10,10,10));
	}

	/**
	 * Retourne l'application
	 * @return
	 */
	public DuelSurLaToile getApp() {
		return app;
	}

	/**
	 * Retourne le nom du jeu
	 * @return
	 */
	public TextField getNom() {
		return nom;
	}

	/**
	 * Retourne la description du jeu
	 * @return
	 */
	public TextArea getDescription() {
		return description;
	}

	/**
	 * Retourne la classe principale du jeu
	 * @return
	 */
	public TextField getClassePrincipale() {
		return classePrincipale;
	}

	/**
	 * Retourne le fichier du jeu
	 * @return
	 */
	public Label getFichierS() {
		return fichierS;
	}

	/**
	 * Retourne l'image du jeu
	 * @return
	 */
	public Label getImageS() {
		return imageS;
	}

	/**
	 * Retourne l'id du type du jeu
	 * @return
	 */
	public int getIdType() {
		return idType;
	}

	/**
	 * Retourne le type du jeu
	 * @return
	 */
	public ComboBox<TypeJeu> getType() {
		return type;
	}

	/**
	 * Retourne l'id du jeu
	 * @return
	 */
	public int getIdJeu() {
		return this.idJeu;
	}
}
