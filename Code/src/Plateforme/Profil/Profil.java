package Plateforme.Profil;

import Plateforme.Application.DuelSurLaToile;
import Plateforme.Application.Utilisateur;
import Plateforme.Connexion.ConnexionMySQL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TitledPane;

/**
 * Cette classe gère l'affichage des informations du joueur courant, de sa liste d'amis et de l'IHM lui permettant
 * de modifier sont mot de passe, son adresse e-mail et son avatar.
 * @author theodore
 */
public class Profil extends BorderPane{
    /**
     * la liste des amis de l'utilisateur
     * */
    private ListeAmis listeAmis;
    /**
     * la zone de texte dans laquelle l'utilisateur entrera le pseudo du joueur rechecher
     */
    private javafx.scene.control.TextField rechercher;
    /**
     * la Vbox dans laquelle seront afficher les informations
     */
    private VBox panelProfil, panelEditer;

    /**
     * L'utilisateur associé au profil
     */
    private Utilisateur utilisateur;

    /**
     * l'application
     * @param app
     */
    private DuelSurLaToile app;
    private PasswordField saisie_ancien_mdp;
    private PasswordField saisie_nouveau_mdp;
    private PasswordField saisie_confirmation_mdp;
    private TextField saisie_nouvelle_email;
    private TextField saisie_confirmation_email;
    private ImageView avatar, avatarInfo;

    /**
     * Ce constructeur crée un BorderPane qui contiendra deux parties :
     * la première contient des informations de l'utilisateur (Pseudo, mail, droits, ratio, avatar)
     * la seconde contient la liste de ses amis
     * @param app
     */
    public Profil(DuelSurLaToile app){
        this.app = app;
        this.panelProfil = new VBox();
        this.panelEditer = new VBox();
        this.panelEditer.setPadding(new Insets(10));
        this.panelProfil.setPadding(new Insets(10));
        
        try {
            this.utilisateur = app.getConnexion().chercherUtilisateur(app.getUtilisateur().getId());
            this.listeAmis = new ListeAmis(this, app.getConnexion().getAmis(this.utilisateur.getPseudoUt()));
        }
        catch(SQLException e){
            System.out.println("Erreur de syntaxe SQL");
        }
        panelProfil.getChildren().add(this.getInfo());
        try {
            panelProfil.getChildren().add(getListeAmi());
        }
        catch(SQLException e){
            System.out.println("Erreur de syntaxe SQL");
        }
        Label titre = new Label("Editer votre profil");
        panelEditer.getChildren().add(titre);
        panelEditer.getChildren().add(this.changerMotDePasse());
        panelEditer.getChildren().add(this.changerAdresseEmail());
        panelEditer.getChildren().add(this.changerAvatar());
        panelEditer.setAlignment(Pos.CENTER);

        panelEditer.setSpacing(10);
        panelEditer.setMaxWidth(DuelSurLaToile.longueur*0.52);
        panelEditer.setMinWidth(DuelSurLaToile.longueur*0.52);
        this.setCenter(panelEditer);
        panelProfil.setSpacing(50);
        panelProfil.setMaxWidth(DuelSurLaToile.longueur*0.47);
        panelProfil.setMinWidth(DuelSurLaToile.longueur*0.47);
        this.setLeft(panelProfil);
    }

    /**
     * @return un BorderPane contenant les informations du joueur
     */
    public BorderPane getInfo() {
        BorderPane bpInfo = new BorderPane();
        VBox info = new VBox();
        javafx.scene.image.Image av = this.utilisateur.getAvatarUt();
        this.avatarInfo = new ImageView(av);
        this.avatarInfo.setFitWidth(100);
        this.avatarInfo.setPreserveRatio(true);

        javafx.scene.control.Label pseudo = new javafx.scene.control.Label("Pseudo : "+this.utilisateur.getPseudoUt());
        javafx.scene.control.Label email = new javafx.scene.control.Label("Email : "+this.utilisateur.getEmailUt());
        javafx.scene.control.Label role = new javafx.scene.control.Label("Rôle : "+this.utilisateur.isAdmin());
        javafx.scene.control.Label ratio = new javafx.scene.control.Label("Ratio : "+this.utilisateur.getRatio()+" %");

        info.getChildren().add(pseudo);
        info.getChildren().add(email);
        info.getChildren().add(role);
        info.getChildren().add(ratio);

        info.setSpacing(10);
        info.setAlignment(Pos.TOP_CENTER);

        bpInfo.setLeft(this.avatarInfo);
        bpInfo.setCenter(info);

        BorderPane.setMargin(info, new Insets(0,0,0,10));

        return bpInfo;
    }

    /**
     * @return un borderPane contenant la liste des amis de l'utilisateur
     * @throws SQLException
     */
    public BorderPane getListeAmi() throws SQLException {
        BorderPane bpListeAmi = new BorderPane();
        HBox top = new HBox();
        HBox center = new HBox();

        javafx.scene.control.Button az = new javafx.scene.control.Button("A-Z");
        az.setUserData("az");
        az.setStyle("-fx-background-color:   #ffffff  ;");
        javafx.scene.control.Button enLigne = new javafx.scene.control.Button("En ligne");
        enLigne.setUserData("online");
        enLigne.setStyle("-fx-background-color:   #ffffff  ;");
        javafx.scene.control.Button chercher = new javafx.scene.control.Button("chercher");
        chercher.setUserData("search");
        chercher.setStyle("-fx-background-color:   #ffffff  ;");

        ActionBoutonProfil act = new ActionBoutonProfil(this);
        chercher.setOnAction(act);
        az.setOnAction(act);
        enLigne.setOnAction(act);


        javafx.scene.control.Label titre = new javafx.scene.control.Label("Liste Amis ("+listeAmis.getNbAmis()+")");

        rechercher = new javafx.scene.control.TextField();

        top.getChildren().add(titre);
        top.getChildren().add(az);
        top.getChildren().add(enLigne);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(5);

        center.getChildren().add(rechercher);
        center.getChildren().add(chercher);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(5);

        bpListeAmi.setTop(top);
        bpListeAmi.setCenter(center);
        bpListeAmi.setBottom(listeAmis);

        BorderPane.setMargin(center, new Insets(10,0,0,0));
        BorderPane.setMargin(listeAmis, new Insets(10,0,0,10));

        return bpListeAmi;
    }

    public ListeAmis getListeAmis() {
        return listeAmis;
    }

    public void setListeAmis(ListeAmis val) {

        this.listeAmis = val;
    }

    public TextField getRechercher() {
        return rechercher;
    }

    public VBox getPanels() {
        return panelProfil;
    }

    /**
     * Met à jour l'affichage en cas de suppression d'un ami
     * @throws SQLException
     */
    public void maj() throws SQLException {
        panelProfil.getChildren().remove(1);
        panelProfil.getChildren().add(getListeAmi());
        this.setLeft(panelProfil);
    }

    public TextField getrechercher() {
        return this.rechercher;
    }

    public ConnexionMySQL getConnexion() {
        return app.getConnexion();
    }

    public void setNouvelleListe(ListeAmis val) {
        this.listeAmis = val;
    }

    public DuelSurLaToile getPlateforme() {
        return this.app;
    }
    
    /**
     * @return Un TitledPane contenant les boutons et les textfield permettant de modifier le mot de passe de 
     * l'utilisateur courant.
     */ 
    public TitledPane changerMotDePasse() {
        TitledPane tp = new TitledPane();

        BorderPane bp = new BorderPane();

        VBox label = new VBox();
        VBox saisie = new VBox();
        VBox boutton = new VBox();

        javafx.scene.control.Label ancien_mdp = new javafx.scene.control.Label("Ancien mot de passe : ");
        javafx.scene.control.Label nouveau_mdp = new javafx.scene.control.Label("Nouveau mot de passe : ");
        javafx.scene.control.Label confirmation_mdp = new javafx.scene.control.Label("Confirmation du\nnouveau mot de passe :");

        saisie_ancien_mdp = new PasswordField();
        saisie_nouveau_mdp = new PasswordField();
        saisie_confirmation_mdp = new PasswordField();

        javafx.scene.control.Button valider = new javafx.scene.control.Button("Valider");
        valider.setUserData("validerMDP");
        valider.setStyle("-fx-background-color:   #cdcdcd  ;");
        ActionBoutonEditionProfil act = new ActionBoutonEditionProfil(this);
        valider.setOnAction(act);

        label.getChildren().add(ancien_mdp);
        saisie.getChildren().add(saisie_ancien_mdp);

        label.getChildren().add(nouveau_mdp);
        saisie.getChildren().add(saisie_nouveau_mdp);

        label.getChildren().add(confirmation_mdp);
        saisie.getChildren().add(saisie_confirmation_mdp);

        label.setSpacing(15);
        saisie.setSpacing(10);

        boutton.getChildren().add(valider);
        boutton.setAlignment(Pos.CENTER);

        bp.setLeft(label);
        bp.setCenter(saisie);
        bp.setRight(boutton);

        tp.setContent(bp);
        tp.setCollapsible(false);
        tp.setExpanded(true);        tp.setText("Changer de mot de passe :");

        BorderPane.setMargin(label, new Insets(0, 10, 0, 0));
        BorderPane.setMargin(saisie, new Insets(0, 0, 0, 10));
        BorderPane.setMargin(boutton, new Insets(0, 10, 0, 10));

        tp.setPadding(new Insets(10,10,0,10));

        return tp;
    }

    /**
     * @return le TitledPane qui contient l'interface permettant de changer l'adresse mail de l'utilisateur
     */
    public TitledPane changerAdresseEmail() {
        TitledPane tp = new TitledPane();

        BorderPane bp = new BorderPane();

        VBox label = new VBox();
        VBox saisie = new VBox();
        VBox boutton = new VBox();

        javafx.scene.control.Label nouvelle_Email = new javafx.scene.control.Label("Nouvelle adresse email : ");
        javafx.scene.control.Label confirmation_nouvelle_email = new javafx.scene.control.Label("Confirmation du\nnouvelle adresse email : ");

        saisie_nouvelle_email = new TextField();
        saisie_confirmation_email = new TextField();

        javafx.scene.control.Button valider = new javafx.scene.control.Button("valider");
        valider.setUserData("validerEmail");
        valider.setStyle("-fx-background-color:   #cdcdcd  ;");
        ActionBoutonEditionProfil act = new ActionBoutonEditionProfil(this);
        valider.setOnAction(act);

        label.getChildren().add(nouvelle_Email);
        label.getChildren().add(confirmation_nouvelle_email);

        saisie.getChildren().add(saisie_nouvelle_email);
        saisie.getChildren().add(saisie_confirmation_email);

        boutton.getChildren().add(valider);

        label.setSpacing(15);
        saisie.setSpacing(10);
        boutton.setAlignment(Pos.CENTER);

        bp.setLeft(label);
        bp.setCenter(saisie);
        bp.setRight(boutton);

        tp.setContent(bp);
        tp.setCollapsible(false);
        tp.setExpanded(true);
        tp.setText("Changer d'adresse email :");

        BorderPane.setMargin(label, new Insets(0, 10, 0, 0));
        BorderPane.setMargin(saisie, new Insets(0, 0, 0, 10));
        BorderPane.setMargin(boutton, new Insets(0, 10, 0, 10));

        tp.setPadding(new Insets(10,10,0,10));

        return tp;
    }

    /**
     * @return le TitledPane qui contient l'interface permettant de changer l'avatar de l'utilisateur
     */
    public TitledPane changerAvatar() {
        TitledPane tp = new TitledPane();

        BorderPane bp = new BorderPane();

        Image av = this.app.getUtilisateur().getAvatarUt();
        avatar = new ImageView(av);
        avatar.setFitWidth(100);
        avatar.setPreserveRatio(true);

        ActionBoutonEditionProfil act = new ActionBoutonEditionProfil(this);
        javafx.scene.control.Button recherche = new javafx.scene.control.Button("Changer d'avatar");
        recherche.setUserData("changerAvatar");
        recherche.setOnAction(act);
        recherche.setStyle("-fx-background-color:   #cdcdcd  ;");

        bp.setLeft(avatar);
        bp.setCenter(recherche);

        tp.setContent(bp);
        tp.setText("Changer son avatar");
        tp.setCollapsible(false);
        tp.setExpanded(true);
        tp.setPadding(new Insets(10,10,0,10));

        return tp;
    }

    public PasswordField getSaisie_ancien_mdp() {
        return saisie_ancien_mdp;
    }

    public PasswordField getSaisie_nouveau_mdp() {
        return saisie_nouveau_mdp;
    }

    public PasswordField getSaisie_confirmation_mdp() {
        return saisie_confirmation_mdp;
    }

    public TextField getSaisie_nouvelle_email() {
        return saisie_nouvelle_email;
    }

    public TextField getSaisie_confirmation_email() {
        return saisie_confirmation_email;
    }

    public ImageView getImageViewer() {
        return this.avatar;
    }

    public ImageView getAvatarInfo() {
        return avatarInfo;
    }
    
    
}
