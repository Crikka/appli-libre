package adullact.publicrowdfunding.requester;

import adullact.publicrowdfunding.reply.AuthentificationReply;
import adullact.publicrowdfunding.request.AuthentificationRequest;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.User;

public class AuthentificationRequester extends Requester<AuthentificationRequest> {
	private String m_username;
	private String m_password;
	
	public AuthentificationRequester(String username, String password) {
		super();
		this.m_username = username;
		this.m_password = password;
	}

	@Override
	public AuthentificationReply post(AuthentificationRequest request) {
		/*
		 * TODO
		 * Simulacre
		 */
		ServerEmulator serverEmulator = ServerEmulator.instance();
		AuthentificationReply res;
		User serverUser = serverEmulator.authentificateUser(m_username, m_password);
		if(serverUser == null){
			res = new AuthentificationReply(request); // fail
		}
		else{
			res = new AuthentificationReply(request, serverUser.pseudo(), serverUser.name(), serverUser.firstName());
			if(serverUser instanceof Administrator){
				res.setAdmin();
			}
		}
		
		return res;
	}

}
