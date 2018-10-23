/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plateforme.Profil;

import java.util.Comparator;

/**
 * Cette classe implémente Comparator et permet de comparer des HistoriqueParties on fonction de leur adversaire.
 * @author theodore
 */
public class ComparateurAdversaire implements Comparator<HistoriquePartie>{
    
    public ComparateurAdversaire(){
        
    }
    
    @Override
    public int compare(HistoriquePartie h1, HistoriquePartie h2){
        return h1.getAdversaire().compareTo(h2.getAdversaire());
    }
    
}
