package Plateforme.Profil;

import Plateforme.Application.Utilisateur;

import java.util.Date;

/**
 * Cette classe contient les informations associées aux invitations.
 * @author theodore
 */
public class Invitation implements Comparable<Invitation>{
    /**
     * L'identifiant unique de l'invitation
     */
    private int id;
    /**
     * La date d'envoi de l'invitation
     */
    private Date date;
    /**
     * L'envoyeur
     */
    private Utilisateur envoyeur;
    /**
     * Le destinataire
     */
    private Utilisateur destinataire;

    /**
     * ce constructeur contient tout les attributs de Invitation en paramêtre et les définis.
     * @param id
     * @param date
     * @param envoyeur
     * @param destinataire 
     */
    public Invitation(int id, Date date, Utilisateur envoyeur, Utilisateur destinataire){
        this.id = id;
        this.date = date;
        this.envoyeur = envoyeur;
        this.destinataire = destinataire;
    }

    public Utilisateur getDestinataire() {
        return destinataire;
    }

    public Utilisateur getEnvoyeur() {
        return envoyeur;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int compareTo(Invitation invit){
        return this.date.compareTo(invit.getDate());
    }
}
