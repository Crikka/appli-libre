package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.reply.AuthentificationReply;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this request when a user needs to be authenticate.
 */
public class AuthentificationRequest extends Request {
	private String m_username;
	private String m_password;

	public AuthentificationRequest(String username, String password) {
		super();
		this.m_username = username;
		this.m_password = password;
	}
	
	@Override
	public AuthentificationReply execute() {
		
		ServerEmulator serverEmulator = ServerEmulator.instance();
		AuthentificationReply reply = new AuthentificationReply();
		User serverUser = serverEmulator.authentificateUser(m_username, m_password);
		if(serverUser == null){
			reply.declareFailed();
		}
		else{
			reply.declareSucceed(serverUser.pseudo(), serverUser.name(), serverUser.firstName());
			if(serverUser instanceof Administrator){
				reply.declareAdministrator();
			}
		}
		
		return reply;
	}

}
