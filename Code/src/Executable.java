import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import Plateforme.Connexion.ConnexionMySQL;
import Plateforme.Jeux.Jeu;

public class Executable {
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConnexionMySQL sql = new ConnexionMySQL();
		sql.connecter();
		
		sql.getJar(3, "Jeu");
		
		
	}
}
