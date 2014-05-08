package adullact.publicrowdfunding.shared;

/**
 * TODO
 * 
 * @author Ferrand
 * 
 */
public class User {
	private String m_pseudo;
	private String m_name;
	private String m_firstName;
	private boolean m_authentified;
	
	public User() {
		this.m_authentified = false;
	}

	public User(String pseudo, String name, String firstName) {
		this.m_pseudo = pseudo;
		this.m_name = name;
		this.m_firstName = firstName;
		this.m_authentified = false;
	}

	public String name() {
		return m_name;
	}

	public String firstName() {
		return m_firstName;
	}

	public String pseudo() {
		return m_pseudo;
	}

	public boolean isAuthentified() {
		return m_authentified;
	}

	public void authentificate() {
		m_authentified = true;
	}
	
	/**
	 * @param pseudo
	 * @param name
	 * @param firstName
	 * @brief setter for all fiels.
	 */
	public void defineFields(String pseudo, String name, String firstName) {
		this.m_pseudo = pseudo;
		this.m_name = name;
		this.m_firstName = firstName;
	}
	
	/**
	 * @return
	 * @brief downcast le type si on le peut, sinon renvoie null.
	 */
	public Administrator toAdmin() {
		if(this instanceof Administrator){
			return (Administrator) this;
		}
		else {
			return null;
		}
	}
}
