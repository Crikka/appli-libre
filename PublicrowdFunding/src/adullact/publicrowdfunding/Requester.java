package adullact.publicrowdfunding;

import java.util.Date;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;
import adullact.publicrowdfunding.exceptions.UserNotFoundException;
import adullact.publicrowdfunding.reply.AuthentificationReply;
import adullact.publicrowdfunding.request.AuthentificationRequest;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Share;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Requester {
	
	public static void authentificateUser(String username, String password) throws UserNotFoundException {
		AuthentificationRequest authentificationRequest = new AuthentificationRequest(username, password);
		AuthentificationReply reply;
		try {
			reply = authentificationRequest.execute();
		}
		catch(AuthentificationRequiredException exception) {
			reply = new AuthentificationReply(); // reply is failing
		}
		catch(AdministratorRequiredException exception) {
			reply = new AuthentificationReply(); // reply is failing
		}
		if(reply.ok()) {
			if(reply.isAdmin()) {
				Share.user = new Administrator();
			}
			Share.user.defineFields(reply.pseudo(), reply.name(), reply.firstName());
			Share.user.authentificate();
		}
		else {
			throw new UserNotFoundException(username, password);
		}
	}
	
	public static void addProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject) {
		
	}
}
