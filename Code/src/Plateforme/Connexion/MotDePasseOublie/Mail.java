package Plateforme.Connexion.MotDePasseOublie;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    /**
     * Méthode permettant d'envoyer un message
     * @param pseudo le pseudo
     * @param code le code
     * @param email l'e-mail
     */
    public static void send(String pseudo, String code, String email) {

        final String username = "duelsurlatoile@gmail.com";
        final String password = "duelsurlatoile45";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("duelsurlatoile@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Mot de passe oublié");
            message.setText("Bonjour "+pseudo+" ! \n"
                    + "Vous venez de perdre votre mot de passe ? Pas grave voici un mot de passe provisoire à taper sur la fenêtre qui vient d'apparaitre sur votre écrans !\n"
                    + code);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
