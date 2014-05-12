package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;
import adullact.publicrowdfunding.reply.Reply;
import adullact.publicrowdfunding.shared.User;

public class ModifyAccountRequest extends AuthentificatedRequest {
	private String m_newName;
	private String m_newFirstName;

	public ModifyAccountRequest() {
		super();
		this.m_newName = null;
		this.m_newFirstName = null;
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

	@Override
	public Reply execute() throws AuthentificationRequiredException, AdministratorRequiredException {
		// TODO Auto-generated method stub
		return null;
	}

}
