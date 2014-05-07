package adullact.publicrowdfunding;

import adullact.publicrowdfunding.request.AuthentificationRequest;
import adullact.publicrowdfunding.requester.AuthentificationRequester;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Communicator {
	
	public boolean authentificateUser(String name, String firstName){
		User user = new User(name, firstName);
		AuthentificationRequester authentificationRequester = new AuthentificationRequester();
		authentificationRequester.post(new AuthentificationRequest(user));
		
		return user.isAuthentified();
	}
}
