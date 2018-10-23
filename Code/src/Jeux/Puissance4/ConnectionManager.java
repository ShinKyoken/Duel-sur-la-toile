package Jeux.Puissance4;

import Plateforme.Connexion.ConnexionMySQL;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Tomoko
 */
public class ConnectionManager {
    
    private VuePuissance4 main;
    private ConnexionMySQL connection;
    
    public ConnectionManager(VuePuissance4 v) throws ClassNotFoundException, IOException, SQLException {
        this.connection = new ConnexionMySQL();
        this.connection.connecter();
        this.main = v;
        System.out.println(this.connection);
    }
    
    /**
     * Receives the save file from the server.
     * @throws SQLException
     * @throws IOException 
     */
    public void getStatus() throws SQLException, IOException {
        Statement s = this.connection.createStatement();
        System.err.println(s);
        ResultSet r = s.executeQuery("SELECT * FROM PARTIE WHERE idPa = " + String.valueOf(this.main.getGameID()));
        r.next();
        this.main.getGame().setId1(r.getInt("idUt1"));
        this.main.getGame().setId2(r.getInt("idUt2"));
        String[] p = this.getUsernames(r.getInt("idUt1"), r.getInt("idUt2"));
        this.main.getGame().setJ1(p[0]);
        this.main.getGame().setJ2(p[1]);
        if(this.main.getGame().getListeJ() == null) {
            this.main.getGame().initPlayers();
        }
        if (r.getInt("numEtape") >= 0) {
            this.main.setNbManches(r.getInt("numEtape") / 2);
            this.main.setNbPtsJ1(r.getInt("score1"));
            this.main.setNbPtsJ2(r.getInt("score2"));
            this.main.getGame().setUpMap(r.getString("etatPartie"));
            this.main.getGame().setJoueurSuivant(r.getInt("numEtape") % 2);
        }
        this.main.maj();
    }
    
    /**
     * Sends the matrice to the server.
     * @throws SQLException if the upload failed.
     * @throws IOException the file is missing.
     */
    public void sendStatus() throws SQLException, IOException {
        Statement sender = this.connection.createStatement();
        sender.executeUpdate("UPDATE PARTIE SET etatPartie = '" + this.main.getGame().getPlat().toString() + "' WHERE idPa = " + String.valueOf(this.main.getGameID()));
        sender.executeUpdate("UPDATE PARTIE SET score1 = " + String.valueOf(this.main.getNbPtsJ1()) + " WHERE idPa = " + String.valueOf(this.main.getGameID()));
        sender.executeUpdate("UPDATE PARTIE SET score2 = " + String.valueOf(this.main.getNbPtsJ2()) + " WHERE idPa = " + String.valueOf(this.main.getGameID()));
        if(this.main.getGame().isFinPartie()) {
            sender.executeUpdate("UPDATE PARTIE SET numEtape = -1 WHERE idPa = " + String.valueOf(this.main.getGameID()));
        }
        else {
            int t = 0;
            if (this.main.getGame().getJoueurEnJeu() == this.main.getGame().getListeJ().get(1)) {
                t = 1;
            }
            sender.executeUpdate("UPDATE PARTIE SET numEtape = " + String.valueOf(this.main.getNbManches() + t) + " WHERE idPa = " + String.valueOf(this.main.getGameID()));
        }
    }
    
    public static void createGame(int idPC, int idJeu, int idJoueur1, int idJoueur2) {
        try {
            ConnexionMySQL connect = new ConnexionMySQL();
            connect.connecter();
            Statement s = connect.createStatement();
            ResultSet r = s.executeQuery("SELECT max(idPa) pa FROM PARTIE");
            r.next();
            int idPartie = r.getInt(1) + 1;
            s.executeUpdate("INSERT INTO PARTIE VALUES(" + idPartie + ", NOW(), 0, '" +
                                                          new Matrice(6, 7, 0).toString() + "', " +
                                                          String.valueOf(idJeu) + ", " +
                                                          String.valueOf(idJoueur1) +
                                                          ", 0, " +
                                                          String.valueOf(idJoueur2) +
                                                          ", 0)");
            connect.close();
        }
        catch(ClassNotFoundException e) {
            ConnectionManager.showException(e);
        }
        catch(IOException e) {
            ConnectionManager.showException(e);
        }
        catch(SQLException e) {
            ConnectionManager.showException(e);
        }
    }
    
    public static void showException(ClassNotFoundException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "The Java Database Driver has not been loaded.\n\nDetails: " + e.getMessage(), ButtonType.CLOSE);
        alert.showAndWait();
        e.printStackTrace();
    }
    
    public static void showException(IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "A disc error has occurred.\n\nDetails: " + e.getMessage(), ButtonType.CLOSE);
        alert.showAndWait();
        e.printStackTrace();
    }
    
    public static void showException(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Yikes! A connection error just occurred.\n\nError code: " + e.getErrorCode() + "\nDetails:" + e.getMessage(), ButtonType.CLOSE);
        alert.showAndWait();
        System.err.println(e.getErrorCode());
        e.printStackTrace();
    }
    
    /**
     * Provides the player names from their IDs.
     * @param idPA
     * @param idPB
     * @return 
     */
    public String[] getUsernames(int idPA, int idPB) {
        String[] res = new String[2];
        try {
            Statement s = this.connection.createStatement();
            ResultSet r = s.executeQuery("SELECT p1.pseudoUt, p2.pseudoUt FROM UTILISATEUR p1, UTILISATEUR p2 WHERE p1.idUt = " + String.valueOf(idPA) + " AND p1.idUt != p2.idUt AND p2.idUt= " + String.valueOf(idPB));
            r.next();
            res[0] = r.getString("p1.pseudoUt");
            res[1] = r.getString("p2.pseudoUt");
        }
        catch(SQLException e) {
            System.err.println(e.getErrorCode());
            e.printStackTrace();
        }
        return res;
    }
}
