package Plateforme.Conversation;

import java.util.Date;

public class Message {

    private Date dateMessage;
    private String expediteur;
    private String destinataire;
    private String contenu;

    /**
     * Le constructeur
     * @param date la date
     * @param expediteur l'expediteur
     * @param destinataire le destinataire
     * @param contenu le contenu
     */
    public Message(Date date, String expediteur, String destinataire, String contenu) {
        this.dateMessage = date;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.contenu = contenu;
    }

    /**
     * Retourne la date du message
     * @return la date du message
     */
    public Date getDateMessage() {
        return dateMessage;
    }

    /**
     * Retourne l'expediteur
     * @return l'expediteur
     */
    public String getExpediteur() {
        return expediteur;
    }

    /**
     * Retourne le destinataire
     * @return le destinataire
     */
    public String getDestinataire() {
        return destinataire;
    }

    /**
     * Retourne le contenu
     * @return le contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Le toString du message
     * @return un string
     */
    @Override
    public String toString() {
        return this.expediteur+" envoie à "+this.destinataire+" : "+this.contenu + " à " + this.dateMessage.toString();
    }
}
