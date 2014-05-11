package adullact.publicrowdfunding.shared;

import java.util.ArrayList;

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
	private ArrayList<Project> m_supportedProjects;
	private ArrayList<Project> m_financedProjects;
	
	public User() {
		this.m_pseudo = null;
		this.m_name = null;
		this.m_firstName = null;
		this.m_authentified = false;
		this.m_supportedProjects = new ArrayList<Project>();
		this.m_financedProjects = new ArrayList<Project>();
	}

	/* Getter */
	public String name() {
		return m_name;
	}

	public String firstName() {
		return m_firstName;
	}

	public String pseudo() {
		return m_pseudo;
	}
	
	public ArrayList<Project> supportedProjects() {
		return m_supportedProjects;
	}
	
	public ArrayList<Project> financedProjects() {
		return m_financedProjects;
	}
	/* ------ */
	

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
