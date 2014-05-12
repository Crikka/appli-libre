package adullact.publicrowdfunding.reply;

import adullact.publicrowdfunding.request.AuthentificationRequest;

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
		super(false);
		this.m_admin = false;
	}
	
	public AuthentificationReply(String pseudo, String name, String firstName) {
		super();
		this.m_admin = false;
		this.m_pseudo = pseudo;
		this.m_name = name;
		this.m_firstName = firstName;
	}
	
	public boolean isAdmin() {
		return m_admin;
	}
	
	public void setAdmin() {
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
