package adullact.publicrowdfunding.shared;

/**
 * TODO
 * @author Ferrand
 *
 */
public class User {
	private String m_name;
	private String m_firstName;
	private boolean m_authentified;
	
	public User(String name, String firstName) {
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
	
	public boolean isAuthentified() {
		return m_authentified;
	}
	
	public void authenticate() {
		m_authentified = true;
	}
}
