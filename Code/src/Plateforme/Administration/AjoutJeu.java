package Plateforme.Administration;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Jeux.TypeJeu;

import java.sql.SQLException;
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

public class AjoutJeu extends BorderPane{

	private TextField nom;
	private TextArea description;
	private TextField classePrincipale;

	private Label fichierS;
	private Label imageS;

	private ComboBox<TypeJeu> type;
	
	private DuelSurLaToile app;

	/**
	 * Méthode gérant l'affichage de la page d'ajout de jeu.
	 * @param app l'application
	 * @throws SQLException
	 */
	public AjoutJeu(DuelSurLaToile app) throws SQLException{
		this.app=app;

		AjoutJeuAction act = new AjoutJeuAction(this);

		Label nomL = new Label("Nom du jeu : ");
		Label descrL = new Label("Description du jeu : ");
		Label classe = new Label("Classe principale");
		this.fichierS = new Label("Aucun fichier");
		this.imageS = new Label("Aucune image");

		this.nom=new TextField();
		this.description=new TextArea();
		this.classePrincipale=new TextField();

		Button image=new Button("Ajouter l'image");
		Button fichier=new Button("Ajouter le fichier du jeu");
		Button valider = new Button("Valider");
		Button annuler = new Button("Annuler");

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
		type.getSelectionModel().selectFirst();

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
	 * Retourne le nom du jeu
	 * @return
	 */
	public TextField getNom() {
		return nom;
	}

	/**
	 * Retourne le description du jeu
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
	 * Retourne l'application
	 * @return
	 */
	public DuelSurLaToile getApp() {
		return app;
	}

	/**
	 * Retourne le type du jeu
	 * @return
	 */
	public ComboBox<TypeJeu> getType() {
		return type;
	}

	/**
	 * Retourne l'application
	 * @return
	 */
	public DuelSurLaToile getIHM() {
		return this.app;
	}
	
}