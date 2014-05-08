package adullact.publicrowdfunding;

import adullact.publicrowdfunding.reply.AuthentificationReply;
import adullact.publicrowdfunding.request.AuthentificationRequest;
import adullact.publicrowdfunding.requester.AuthentificationRequester;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Communicator {
	
	public User authentificateUser(String username, String password){
		User user;
		AuthentificationRequester authentificationRequester = new AuthentificationRequester(username, password);
		AuthentificationReply reply = authentificationRequester.post(new AuthentificationRequest(null));
		if(reply.ok()) {
			if(reply.isAdmin()) {
				user = new Administrator(reply.pseudo(), reply.name(), reply.firstName());
			}
			else{
				user = new User(reply.pseudo(), reply.name(), reply.firstName());
			}
			user.authentificate();
			return user;
		}
		else {
			return null;
		}
	}
}
