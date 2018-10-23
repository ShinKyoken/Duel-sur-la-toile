package Plateforme.Profil;

import Plateforme.Application.Utilisateur;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
/**
 * Cette classe implémente EventHandler et contient la fonction handle associée aux boutons servant à l'édition
 * du profil de l'utilisateur.
 * @author theodore
 */
public class ActionBoutonEditionProfil implements EventHandler<ActionEvent> {
    /**
     * le profil auquel est associé l'ActionBoutonProfil
     */
    private Profil profil;

    public ActionBoutonEditionProfil(Profil profil) {
        this.profil = profil;
    }

    /**
     * Cette fonction gère les clics des boutons servant aux modifications des informations du profil :
     *      -avatar
     *      -mot de passe
     *      -adresse e-mail
     * @param a
     */
    @Override
    public void handle(ActionEvent a) {
        Button b = (Button) a.getSource();

        if (b.getUserData().equals("validerMDP")) {
            try {
                if (profil.getConnexion().getMDP(profil.getPlateforme().getUtilisateur().getPseudoUt()).equals(profil.getConnexion().cryptPass(profil.getSaisie_ancien_mdp().getText()))) {
                    if (profil.getSaisie_nouveau_mdp().getText().equals(profil.getSaisie_confirmation_mdp().getText())) {
                        profil.getConnexion().modifierMDP(profil.getPlateforme().getUtilisateur().getPseudoUt(), profil.getSaisie_nouveau_mdp().getText());
                        profil.getSaisie_ancien_mdp().clear();
                        profil.getSaisie_nouveau_mdp().clear();
                        profil.getSaisie_confirmation_mdp().clear();
                        Alert i = new Alert(Alert.AlertType.INFORMATION);
                        i.setTitle("Information");
                        i.setHeaderText("Modification du mot de passe");
                        i.setContentText("Votre mot de passe a bien été modifier !");
                        i.showAndWait();
                    }
                    else {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erreur");
                        error.setHeaderText("Erreur de saisie !");
                        error.setContentText("Il semblerait que vous n'avez pas \nsaisie deux fois le même mot de passe");
                        error.showAndWait();
                    }
                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Erreur");
                    error.setHeaderText("Erreur de saisie !");
                    error.setContentText("Il semblerait que vous n'avez pas \nsaisie le bon ancien mot de passe");
                    error.showAndWait();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (b.getUserData().equals("validerEmail")) {
            try {
                if (profil.getSaisie_nouvelle_email().getText().equals(profil.getSaisie_confirmation_email().getText())) {
                    profil.getConnexion().modifierEmail(profil.getPlateforme().getUtilisateur().getPseudoUt(), profil.getSaisie_nouvelle_email().getText());
                    profil.getSaisie_nouvelle_email().clear();
                    profil.getSaisie_confirmation_email().clear();
                    Alert i = new Alert(Alert.AlertType.INFORMATION);
                    i.setTitle("Information");
                    i.setHeaderText("Modification de l'adresse e-mail");
                    i.setContentText("Votre adresse e-mail a bien été modifié !");
                    i.showAndWait();
                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Erreur");
                    error.setHeaderText("Erreur de saisie !");
                    error.setContentText("Il semblerait que vous n'avez pas \nsaisie deux fois la même adresse e-mail");
                    error.showAndWait();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (b.getUserData().equals("changerAvatar")) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Choix de l'image");
            fc.setInitialDirectory(new File("../"));
            FileChooser.ExtensionFilter et = new FileChooser.ExtensionFilter("picture", "*.gif", "*.png", "*.jpeg" ,"*.jpg");
            fc.getExtensionFilters().add(et);
            File f = fc.showOpenDialog(null);
            if(f != null) {
                try {
                    byte[] img = Files.readAllBytes(f.toPath());
                    this.profil.getConnexion().modifierAvatar(this.profil.getPlateforme().getUtilisateur().getPseudoUt(), img);
                    this.profil.getPlateforme().getUtilisateur().setAvatarUt(new Image(new ByteArrayInputStream(img)));
                    Image av = this.profil.getPlateforme().getUtilisateur().getAvatarUt();
                    this.profil.getImageViewer().setImage(av);
                    this.profil.getAvatarInfo().setImage(av);

                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Confirmation");
                    confirmation.setHeaderText("Changement de votre avatar");
                    confirmation.setContentText("Votre avatar a bien été changé");
                    confirmation.showAndWait();
                }
                catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public byte[] getArrayByteFromFile(File f) throws IOException {

        int length = (int) f.length();
        byte[] data = new byte[length];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
        int result = in.read(data, 0, length);
        return data;
    }
}