/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Profil;

import java.util.Comparator;

/**
 * Cette classe implémente Comparator et permet de comparer des HistoriquePartie en fonction de leur jeu.
 * @author theodore
 */
public class ComparateurJeu implements Comparator<HistoriquePartie>{
    
    public ComparateurJeu(){
        
    }
    
    @Override
    public int compare(HistoriquePartie h1, HistoriquePartie h2){
        return h1.getNom().compareTo(h2.getNom());
    }
    
}
