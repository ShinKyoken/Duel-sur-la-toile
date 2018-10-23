package Jeux.Puissance4;


import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Interface graphique du puissance 4
 *
 */

public class VuePuissance4 extends Application {

    
    /**
     * Game info pane.
     */
    private TitledPane gameInfo;
        /**
         * Game ID.
         */
        private int idGame;
        
	/**
	 * Le plateau "game" nous permettra de recuperer les informations indispensable de la partie en cours
	 */
	private Plateau game;

	/**
	 * Le Group "matrice" correspond au plateau afficher sur l'IHM
	 */
	private Group matrice;

	/**
	 * Le Borderpane "c" correspond à l'organisation des differentes partie de l'IHM.
	 */
	private BorderPane c;

	/**
	 * Le Label "couleur" indique quelle couleur de pion le joueur possède.
	 */
	private Label couleur;

	/**
	 * Le VBox "center" correspond aux éléments au centre de l'interface, c'est-à-dire le plateau.
	 */
	private VBox center;

	/**
	 * Le label "resultatPartie" indique le gagnant de la partie.
	 */
	private Label resultatPartie;

	/**
	 * Le Button "rejouer" permet de lancer une nouvelle manche.
	 */
	private Button rejouer;

	/**
	 * L'entier "nbManches" est le nombre de manche jouées (traité en interne).
	 */
	private long nbManches = 1;

	/**
	 * Le HBox "nombreColonnes" correspond aux boutons pour chaque colonne.
	 */
	private HBox nombreColonnes;

        /**
	 * Le HBox "parametrePartie" correspond aux boutons pour abandoné et quitter la partie.
	 */
	private HBox parametrePartie;
        
        /**
         * Le Button "surrender" correspond au bouton pour déclarer forfait.
         */
        private Button surrender;
        
        /**
         * Le Button "leave" correspond au bouton pour quitter la partie en sauvegardant.
         */
        private Button leave;
	/**
	 * La liste de bouton "colonneButton" est la liste des bouttons en dessous du plateau.
	 */
	private List<Button> colonneButton;

	/**
	 * L'entier "nbPtsJ1" correspond au nombre de point du joueur 1
	 */
	private long nbPtsJ1 = 0;

	/**
	 * L'entier "nbPtsJ2" correspond au nombre de point du joueur 2
	 */
	private long nbPtsJ2 = 0;
    
	/**
	* L'objet gérant les boutons du jeu.
	*/
        private ActionButton act;
	
        /**
         * label that show who is the player who will played
         */
        private Label tour;
        
        /**
         * label who show how many roud had been played
         */
        private Label round;
        
        /**
         * label who show the score of the player 1
         */
        private Label scoreJ1;
        
        /**
         * label who show the score of the player 2
         */
        private Label scoreJ2;
        
        /**
         * The Label used for connection status report.
         */
        private Label connection;
        
        /**
         * 
         */
        private Label triche; 
        
        /**
         * The SQL connection to the server.
         */
        private ConnectionManager share;
        
        private VBox leaveButtons;
        
        /**
         * The application's settings.
         */
        private String[] parameters;
        
        public VuePuissance4(String[] args) {
            this.parameters = args;
        }

	/**
	 * Cette methode permet de placer les differents objets sur une scene pour pouvoir les affichers dans l'IHM
	 * @return
	 */
	private Scene scene() {
		// The left pane where the game's infos are shown.
		c = new BorderPane();
		c.setCenter(this.PlateauDuJeu());
		c.setLeft(this.leaveButtons);
                c.setBottom(this.createConnectionText());
		return new Scene(c, 500, 350);
	}

	/**
	 * Initialise de plateau du jeu.
	 * @return le plateau en question.
	 */
	private VBox PlateauDuJeu() {
                this.createButtons();
		//Configuration de la partie central de l'IHM
                this.center = new VBox(this.getJeton(), this.nombreColonnes, this.rejouer);
		this.center.setAlignment(Pos.CENTER);
		this.center.setSpacing(10);
		this.center.setPadding(new Insets(15,5,6,11));
		return center;
	}
        
        /**
         * Creates the buttons for each column.
         */
        private void createButtons() {
            ActionColonne actC = new ActionColonne(this);
            this.nombreColonnes = new HBox();
            // Generating the buttons underneath the game space.
            for(int i = 0; i < this.game.getPlat().getNbColonnes(); i++) {
                Button b = new Button(String.valueOf(i+1));
                b.setOnAction(actC);
		b.setUserData(i+1);
		this.nombreColonnes.getChildren().add(b);
		this.colonneButton.add(b);
            }

            this.nombreColonnes.setSpacing(16.65);
            this.nombreColonnes.setPadding(new Insets(10,0,0,0));
            this.nombreColonnes.setAlignment(Pos.CENTER);
            this.rejouer.setOnAction(this.act);
        }
        
        /**
         * Creates the text box info on the left.
         * @return that text box.
         */
        private VBox createGameInfo() {
            
            this.surrender = new Button("Surrender");
            this.surrender.setUserData("surrender");
            this.surrender.setOnAction(act);
            this.leave = new Button("Leave game");
            this.leave.setUserData("leave");
            this.leave.setOnAction(act);
            this.parametrePartie = new HBox(surrender, leave);
            this.parametrePartie.setSpacing(16.5);
            VBox res = new VBox(this.gameInfo, parametrePartie);
            res.setSpacing(5);
            res.setAlignment(Pos.CENTER);
            res.setPadding(new Insets(15,5,6,11));
            return res;
        }
        
	/**
	 * Cette methode retourne une zone d'information sur la partie
	 * @return le conteneur du texte.
	 */
	private TitledPane infoPartie() {
            this.tour = new Label();
            this.round = new Label();
            this.scoreJ1 = new Label();
            this.scoreJ2 = new Label();
            this.resultatPartie = new Label("");
            this.couleur = new Label();
            this.triche = new Label();
            VBox infoPartie = new VBox(tour, round, scoreJ1, scoreJ2, resultatPartie, triche);
            infoPartie.setSpacing(5);
            TitledPane info = new TitledPane("Informations", infoPartie);
            info.setCollapsible(false);
            return info;
	}

	/**
	 * Cette methode met à jour l'affichage aprés chaque action sur les bouttons
	 */
	public void maj() {
		if(this.getGame().isFinPartie()) {
			this.win(this.getGame().getJoueurEnJeu().getPseudo());
		}
		this.matrice = new Group();
		for(int i = 0; i<this.game.getPlat().getNbColonnes(); i++) {
			for(int k = 0; k<this.game.getPlat().getNbLignes();k++) {
				Circle jeton= new Circle(i*40,k*40,10);
				if(this.game.getPlat().getVal(k, i)==1){
					jeton.setFill(new Color(1,0,0,1));
				}
				else if(this.game.getPlat().getVal(k, i)==2) {
					jeton.setFill(new Color(0,0,1,1));
				}
				matrice.getChildren().add(jeton);
			}
		}
                this.tour.setText("It is " + this.getGame().getJoueurEnJeu().getPseudo() + "'s turn.");
                this.round.setText("Round no. " + this.nbManches);
                this.scoreJ1.setText(this.getGame().getJ1() + "'s score: " + this.nbPtsJ1);
                this.scoreJ2.setText(this.getGame().getJ2() + "'s score: " + this.nbPtsJ2);
		this.couleur.setText(String.valueOf(this.game.getJoueurEnJeu().getCouleur()));
                this.triche.setText("                                            ");
                if (this.center != null) {
                    this.center.getChildren().set(0, this.matrice);
                }
                if (Integer.valueOf(parameters[0]) == this.game.getId1()) {
                    if(this.game.getListeJ().get(0).getJoue()) {
                        this.localPlayerTurn();
                    }
                    else {
                        this.opponentTurn();
                    }
                }
                else {
                    if(this.game.getListeJ().get(1).getJoue()) {
                        this.localPlayerTurn();
                    }
                    else {
                        this.opponentTurn();
                    }
                }
	}

        /**
         * Allows the player to use the game's buttons.
         * (IT's his turn).
         */
        public void localPlayerTurn() {
            for(Button b : this.getLsButtonColonne()) {
                b.setDisable(false);
            }
            this.surrender.setDisable(false);
            this.leave.setDisable(false);
        }
        
        /**
         * Disables controls for the local player.
         */
        public void opponentTurn() {
            for(Button b : this.getLsButtonColonne()) {
                b.setDisable(true);
            }
            this.surrender.setDisable(true);
            this.leave.setDisable(true);
        }
        
        /**
         * Cette méthode termine la partie en déclarant le vainqueur.
         * @param winner the player who won.
         */
        public void win(String winner) {
            this.rejouer.setVisible(true);
            this.leave.setDisable(true);
            this.surrender.setDisable(true);
            for(Button d : this.getLsButtonColonne()) {
                d.setDisable(true);
            }
            if(winner.equals(this.getGame().getJ1())) {
		this.nbPtsJ1 += 1;
            }
            else if(winner.equals(this.getGame().getJ2())) {
		this.nbPtsJ2 += 1;
            }
            if (winner.equals("Draw!")) {
                this.resultatPartie.setText(winner);
            }
            else {
                this.resultatPartie.setText(winner + " won.");
            }
            this.game.setFinPartie(false);
        }
        
	/**
	 * Cette methode retourne le plateau.
	 * @return le plateau.
	 */
	private Group getMatrice() {
		Group slot = new Group();
		for(int i = 0; i<this.game.getPlat().getNbColonnes(); i++) {
			for(int k = 0; k<this.game.getPlat().getNbLignes();k++) {
				Circle jeton= new Circle(i*40,k*40,10);
				if(this.game.getPlat().getVal(k, i)==1){
					jeton.setFill(new Color(1,0,0,1));
				}
				else if(this.game.getPlat().getVal(k, i)==2) {
					jeton.setFill(new Color(0,0,1,1));
				}
				slot.getChildren().add(jeton);
			}
		}
		return slot;
	}

	/**
	 * Cette methode retourne le plateau vide.
	 * @return 
	 */
	private Group getJeton() {
		Group slot = new Group();
		for(int i = 0; i<this.game.getPlat().getNbColonnes(); i++) {
			for(int k = 0; k<this.game.getPlat().getNbLignes();k++) {
				slot.getChildren().add(new Circle(i*40,k*40,10));
			}
		}
		return slot;
	}

	/**
	 * Cette methode permet de remettre l'interface à zero lorsque l'on desire de rejouer
	 */
	public void recommencer() {
		for(Button d : this.getLsButtonColonne()) {
			d.setDisable(false);
		}
		this.game = new Plateau();
		this.couleur = new Label();
		this.rejouer.setVisible(false);
		this.game.setFinPartie(false);
                this.getStatus();
                this.nbManches += 2;
                this.sendStatus();
	}



	/**
	 * Configuration de l'IHM au premier lancement
         * @param arg0
	 */
	@Override
	public void start(Stage arg0) {
            this.idGame = Integer.valueOf(this.parameters[1]);
            System.out.println(this.idGame);
            this.share = this.setUpConnection();
            this.act = new ActionButton(this);
            this.game = new Plateau();
            this.gameInfo = infoPartie();
            this.leaveButtons = this.createGameInfo();
            this.colonneButton = new ArrayList<>();
            this.getStatus();
            this.startNetworkService();
            this.matrice = this.getMatrice();
            this.rejouer = new Button("New Game");
            this.rejouer.setUserData("newgame");
            this.rejouer.setVisible(false);
            arg0.setScene(scene());
            arg0.setTitle("Connect Four");
            arg0.setResizable(false);
            arg0.show();
            this.maj();
	}
        
        /**
         * Sets up the SQL connection between the game and the DSLT server.
         * 
         * Rather than using a given SQL connection object, we will use
         * a new one distinct from the Plateforme's.
         */
        private ConnectionManager setUpConnection() {
            ConnectionManager res = null;
            try {
                res = new ConnectionManager(this);
            }
            catch(ClassNotFoundException e) {
                ConnectionManager.showException(e);
                Platform.exit();
            }
            catch(IOException e) {
                ConnectionManager.showException(e);
                Platform.exit();
            }
            catch(SQLException e) {
                ConnectionManager.showException(e);
                Platform.exit();
            }
            return res;
        }
        
        /**
         * Creates the connection text display.
         * @return the FlowPane with the Label inside.
         */
        private FlowPane createConnectionText() {
            this.connection = new Label("");
            FlowPane bottom = new FlowPane(this.connection);
            return bottom;
        }
        
        /**
         * Returns the current game's ID.
         * @return the game's identifier number.
         */
        public int getGameID() {
            return this.idGame;
        }
        
        public ConnectionManager getCM() {
            return this.share;
        }
        
        public void getStatus() {
            try {
                this.share.getStatus();
            }
            catch(SQLException e) {
                ConnectionManager.showException(e);
            }
            catch(IOException e) {
                ConnectionManager.showException(e);
            }
        }
        
        public void sendStatus() {
            try {
                this.share.sendStatus();
            }
            catch(SQLException e) {
                ConnectionManager.showException(e);
            }
            catch(IOException e) {
                ConnectionManager.showException(e);
            }
        }
        
        public void startNetworkService() {
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> this.getStatus()));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
        }
        
	public Plateau getGame() {
		return game;
	}

	public Label getResultatPartie() {
		return resultatPartie;
	}

	public Button getRejouer() {
		return rejouer;
	}

	public List<Button> getLsButtonColonne(){
		return this.colonneButton;
	}

	public long getNbPtsJ1() {
		return nbPtsJ1;
	}

	public long getNbPtsJ2() {
		return nbPtsJ2;
	}
        
        public long getNbManches() {
            return this.nbManches;
        }
        
        public void setNbPtsJ1(long pts) {
            this.nbPtsJ1 = pts;
        }
        
        public void setNbPtsJ2(long pts) {
            this.nbPtsJ2 = pts;
        }
        
        public void setNbManches(long nb) {
            this.nbManches = nb;
        }
}
