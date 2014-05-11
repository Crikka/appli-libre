package adullact.publicrowdfunding.exceptions;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String m_pseudo;
	private String m_password;

	public UserNotFoundException(String pseudo, String password){
		super();
		this.m_pseudo = pseudo;
		this.m_password = password;
	}
	
	public String pseudo() {
		return m_pseudo;
	}
	
	public String password() {
		return m_password;
	}
}
