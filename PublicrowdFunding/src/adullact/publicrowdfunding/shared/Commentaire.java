package adullact.publicrowdfunding.shared;

public class Commentaire {

	private User utilisateur;
	private String idprojet;
	private String message;
	private double notation; // Sur 5
	
	public User getUtilisateur() {
		return utilisateur;
	}

	public String getIdprojet() {
		return idprojet;
	}

	public String getMessage() {
		return message;
	}

	public double getNotation() {
		return notation;
	}

	public Commentaire(User utilisateur, String idprojet, String message,
			double notation) {
		super();
		this.utilisateur = utilisateur;
		this.idprojet = idprojet;
		this.message = message;
		this.notation = notation;
	}
	

}
