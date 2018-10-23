package Plateforme.Application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

public class Utilisateur implements Comparable<Utilisateur>{
    /**
     * Numéro de l'utilisateur dans la base de données, elle est différente pour chaque utilisateur
     * */
    private int idUt;
    /**
     * Pseudo de l'utilisateur, il est différent pour chaque utilisateur
     * */
    private String pseudoUt;
    /**
     * l'adresse email de l'utilisateur, c'est à cette adresse que seront envoyer les messages de la plateforme (mot de passe oublié...
     * */
    private String emailUt;
    /**
     * Un booléen indiquant si l'utilisateur est activé(banni)
     * */
    private boolean activeUt;
    /**
     * Le fichier contenant l'avatar du joueur
     * */
    private Image avatarUt;
    /**
     * Un boolean indiquant si le joueur est un administrateur
     * */
    private String admin;
    /**
     * Le ratio victoire/nbpartie de l'utilisateur
     * */
    private int ratio;
    
    

    public Utilisateur(int idUt, String pseudo, String email, boolean active, Image avatar, String admin, int ratio){
        this.idUt = idUt;
        this.pseudoUt = pseudo;
        this.emailUt = email;
        this.avatarUt = avatar;
        this.activeUt = active;
        this.admin = admin;
        this.ratio = ratio;
    }

    public int getId() {
        return this.idUt;
    }

    public int getRatio() {
        return this.ratio;
    }

    public String getPseudoUt() {
        return this.pseudoUt;
    }

    public String getEmailUt() {
        return this.emailUt;
    }

    public boolean isActiveUt() {
        
        return this.activeUt;
    }
    
    
    public Image getAvatarUt() {
        return avatarUt;
    }
    
    public void setAvatarUt(Image image){
        this.avatarUt = image;
    }

    public String isAdmin() {
        
        return this.admin;
    }

    @Override
    public String toString() {
        return this.getPseudoUt();
    }

    public void setEmailUt(String emailUt) {
        this.emailUt = emailUt;
    }

    @Override
    public int compareTo(Utilisateur arg0) {
        return this.pseudoUt.toLowerCase().compareTo(arg0.getPseudoUt().toLowerCase());
    }

   // public boolean isAtiveUt() {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   // }
}
