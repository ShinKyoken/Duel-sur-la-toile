package Plateforme.Jeux;

public class TypeJeu {
	
	private int idType;
	
	private String nomType;

	public TypeJeu(int idType, String nomType) {
		this.idType = idType;
		this.nomType = nomType;
	}

	public int getIdType() {
		return idType;
	}

	public String getNomType() {
		return nomType;
	}
	
	@Override
	public String toString() {
		return idType + " - " + nomType;
	}
}
