package Jeux.Mastermind;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Classe permettant d'afficher la page d'aide du mastermind
 *
 */
public class ApplicationAide extends Application{

	/**
	 * Le corp de la page
	 */
	private BorderPane fin;

	/**
	 * Le texte des differentes page de l'aide
	 */
	private TextFlow intro;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Methode permettant de creer la scene de la page d'aide
	 * @return
	 */
	public Scene getS() {
		fin = new BorderPane();

		Scene windows = new Scene(fin, 600,200);

		this.afficherIntro();

		VBox lsPage = getListePage();

		fin.setLeft(lsPage);

		return windows;
	}

	/**
	 * Methode permettant de recuperer la liste des pages
	 * @return
	 */
	public VBox getListePage() {
		VBox res = new VBox();

		ActionHyperlink act = new ActionHyperlink(this);

		Hyperlink intro = new Hyperlink("Introduction");
		Hyperlink jouer = new Hyperlink("Jouer un tours");
		Hyperlink partie = new Hyperlink("La partie");
		Hyperlink autre = new Hyperlink("Autre");

		intro.setUserData("intro");
		jouer.setUserData("jouer");
		partie.setUserData("partie");
		autre.setUserData("autre");

		intro.setOnAction(act);
		jouer.setOnAction(act);
		partie.setOnAction(act);
		autre.setOnAction(act);

		res.getChildren().add(intro);
		res.getChildren().add(jouer);
		res.getChildren().add(partie);
		res.getChildren().add(autre);

		res.setAlignment(Pos.CENTER);

		return res;
	}

	/**
	 * Methode permettant d'afficher l'intro de l'aide
	 */
	public void afficherIntro() {
		Text titre = new Text("INTRODUCTION\n\n");
		titre.setStyle("-fx-font-weight: bold");
		Text corpTexte = new Text("Bienvenu sur le jeu du Mastermind !\n"
				+ "Ce jeu vous est proposer par le groupe 1A31B de l'IUT d'Orleans pour des heures de fun !\n"
				+ "Dans cette page d'aide vous trouverez tout ce que vous avez besoin pour voir jouer !");

		intro = new TextFlow(titre, corpTexte);
		intro.setPrefWidth(500);
		intro.setPadding(new Insets(20,20,20,20));
		intro.setTextAlignment(TextAlignment.JUSTIFY);
		FlowPane pan = new FlowPane();
		pan.getChildren().add(intro);
		pan.setAlignment(Pos.CENTER);
		fin.setRight(pan);
	}

	/**
	 * Methode permettant d'expliquer comment jouer un tour
	 */
	public void afficherJouerUnTours() {
		Text titre = new Text("JOUER UN TOUR\n\n");
		titre.setStyle("-fx-font-weight: bold");
		Text corpTexte = new Text("- sélectionner les couleurs voulue via la barre de sélection en bas du Mastermind.\n"
				+ "- si la combinaison choisie ne vous plait pas vous pouvez cliquez sur le bouton \"Annuler\", ce qui réinitialisera la combinaison pour que vous puissiez en choisir une nouvelle.\n"
				+ "- pour valider une combinaison vous n'aurait qu'a cliquez sur le bouton \"valider\".");

		intro = new TextFlow(titre, corpTexte);
		intro.setPrefWidth(500);
		intro.setPadding(new Insets(20,20,20,20));
		intro.setTextAlignment(TextAlignment.JUSTIFY);
		FlowPane pan = new FlowPane();
		pan.getChildren().add(intro);
		pan.setAlignment(Pos.CENTER);
		fin.setRight(pan);
	}

	/**
	 * Methode permettant d'expliquer comment fonctionne une partie de mastermind
	 */
	public void afficherLaPartie() {
		Text titre = new Text("LA PARTIE\n\n");
		titre.setStyle("-fx-font-weight: bold");
		Text corpTexte = new Text("- La partie se fini quand l'un des joueurs a gagné 3 manches.\n"
				+ "- Pour gagné une manche il faudra que le joueur trouve la combinaison caché.\n"
				+ "- les 2 joueurs joue leur tour à tour de rôle l'un après l'autre.");

		intro = new TextFlow(titre, corpTexte);
		intro.setPrefWidth(500);
		intro.setPadding(new Insets(20,20,20,20));
		intro.setTextAlignment(TextAlignment.JUSTIFY);
		FlowPane pan = new FlowPane();
		pan.getChildren().add(intro);
		pan.setAlignment(Pos.CENTER);
		fin.setRight(pan);
	}

	/**
	 * Methode permettant d'afficher d'autre explication
	 */
	public void afficherAutre() {
		Text titre = new Text("AUTRE\n\n");
		titre.setStyle("-fx-font-weight: bold");
		Text corpTexte = new Text("- Vous pouvez abandonnez en cliquant sur le bouton \"abandonné\" en bas à droite.\n"
				+ "- Vous pouvez quittez une partie et la suspendre en cliquant sur le bouton \"quitter\" en bas à droite.\n"
				+ "- Le carré autour des 2 joueurs indique qui doit jouer.\n"
				+ "- En dessous de la photo des 2 joueurs il y a un compteur qui indique le nombre de manche gagné dans la partie.\n"
				+ "- Vous pouvez sélectionnez le bouton option pour changer quelque réglage.");

		intro = new TextFlow(titre, corpTexte);
		intro.setPrefWidth(500);
		intro.setPadding(new Insets(20,20,20,20));
		intro.setTextAlignment(TextAlignment.JUSTIFY);
		FlowPane pan = new FlowPane();
		pan.getChildren().add(intro);
		pan.setAlignment(Pos.CENTER);
		fin.setRight(pan);
	}

	@Override
	public void start(Stage a) throws Exception {
		a.setTitle("Aide du mastermind");
		a.setScene(getS());
		a.setResizable(false);
		a.show();

	}

}
