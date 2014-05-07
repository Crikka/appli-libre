package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.shared.User;

public class ModifyAccountRequest extends Request<User> {
	private String m_newName;
	private String m_newFirstName;

	public ModifyAccountRequest(User user) {
		super(user);
		this.m_newName = user.name();
		this.m_newFirstName = user.firstName();
	}
	
	public void modifyName(String newName) {
		m_newName = newName;
	}
	
	public void modifyFirstName(String newFirstName) {
		m_newFirstName = newFirstName;
	}
	
	public String newFirstName() {
		return m_newFirstName;
	}
	
	public String newName() {
		return m_newName;
	}

}
