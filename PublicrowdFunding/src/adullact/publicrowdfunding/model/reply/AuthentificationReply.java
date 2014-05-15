package adullact.publicrowdfunding.model.reply;

public class AuthentificationReply extends Reply {
	private boolean m_admin; // La réponse comprend le fait que l'utilisateur est un admin ou non.
	private String m_pseudo;
	private String m_name;
	private String m_firstName;

	/**
	 * @param request
	 * @brief for fail reply
	 */
	public AuthentificationReply() {
		super();
		
		this.m_admin = false;
	}
	
	public void declareSucceed(String pseudo, String name, String firstName) {
		super.declareSucceed();
		m_pseudo = pseudo;
		m_name = name;
		m_firstName = firstName;
	}
	
	public boolean isAdmin() {
		return m_admin;
	}
	
	public void declareAdministrator() {
		m_admin = true;
	}
	
	public String pseudo() {
		return m_pseudo;
	}
	
	public String name() {
		return m_name;
	}

	public String firstName() {
		return m_firstName;
	}

}
