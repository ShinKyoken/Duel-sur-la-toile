package Plateforme.Partie;

public class InvitPartie {
	private int idPartie;
	private int idProprietaire;
	private int idInviter;
	private int idJeu;
	public InvitPartie(int idPartie, int idProprietaire, int idInviter, int idJeu) {
		this.idPartie = idPartie;
		this.idProprietaire = idProprietaire;
		this.idInviter = idInviter;
		this.idJeu = idJeu;
	}
	public int getIdPartie() {
		return idPartie;
	}
	public int getIdProprietaire() {
		return idProprietaire;
	}
	public int getIdInviter() {
		return idInviter;
	}
	public int getIdJeu() {
		return idJeu;
	}
	
	
}
