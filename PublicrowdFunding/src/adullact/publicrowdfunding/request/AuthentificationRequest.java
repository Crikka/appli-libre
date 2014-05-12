package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;
import adullact.publicrowdfunding.reply.AuthentificationReply;
import adullact.publicrowdfunding.reply.Reply;
import adullact.publicrowdfunding.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this request when a user needs to be authenticate.
 */
public class AuthentificationRequest extends AnonymousRequest {
	private String m_username;
	private String m_password;

	public AuthentificationRequest(String username, String password) {
		super();
		this.m_username = username;
		this.m_password = password;
	}
	
	@Override
	public AuthentificationReply execute() throws AuthentificationRequiredException, AdministratorRequiredException {
		verifyUserType();
		
		ServerEmulator serverEmulator = ServerEmulator.instance();
		AuthentificationReply res;
		User serverUser = serverEmulator.authentificateUser(m_username, m_password);
		if(serverUser == null){
			res = new AuthentificationReply(); // fail
		}
		else{
			res = new AuthentificationReply(serverUser.pseudo(), serverUser.name(), serverUser.firstName());
			if(serverUser instanceof Administrator){
				res.setAdmin();
			}
		}
		
		return res;
	}

}
