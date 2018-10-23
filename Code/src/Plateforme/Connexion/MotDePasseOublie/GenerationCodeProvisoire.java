package Plateforme.Connexion.MotDePasseOublie;

import java.util.Random;

public class GenerationCodeProvisoire {

    /**
     * Méthode qui génere un code provisoire
     * @return le code provisoire
     */
    public static String genere() {
        Random r = new Random();
        String res = "";
        for(int i = 0; i<10; i++) {
            int valeur = 65 + r.nextInt(90 - 65);
            String strAsciiTab = Character.toString((char) valeur);
            res+=strAsciiTab;
        }
        return res;
    }
}
