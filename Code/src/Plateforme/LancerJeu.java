package Plateforme;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import Jeux.IJeu;

public class LancerJeu {
	@SuppressWarnings("deprecation")
	public static IJeu run(String nomJeu, String nomLanceur) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		File ficJar = new File("./jar/"+nomJeu+".jar");
		System.out.println(ficJar.exists());
		URL[] listeURL = {ficJar.toURL()};
		ClassLoader loader = new URLClassLoader(listeURL);
		IJeu j = ((IJeu) Class.forName("Jeux."+nomJeu+"."+nomLanceur,true,loader).newInstance());
		return j;
	}
}
