package adullact.publicrowdfunding;

import java.util.Date;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.reply.AuthentificationReply;
import adullact.publicrowdfunding.model.request.AuthentificationRequest;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Share;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Requester {
	
	public static void authentificateUser(String pseudo, String password, AuthentificationEvent authentificationEvent) {
		AuthentificationRequest authentificationRequest = new AuthentificationRequest(pseudo, password);
		authentificationEvent.defineContextRequest(authentificationRequest);
		AuthentificationReply reply = authentificationRequest.execute();
		if(reply.ok()) {
			if(reply.isAdmin()) {
				Share.user = new Administrator();
				Share.user.defineFields(pseudo, password, reply.name(), reply.firstName());
				Share.user.authentificate();
				authentificationEvent.onAuthentificate();
				authentificationEvent.ifUserIsAdministrator();
			}
			else {
				Share.user.defineFields(pseudo, password, reply.name(), reply.firstName());
				Share.user.authentificate();
				authentificationEvent.onAuthentificate();
			}
		}
		else {
			authentificationEvent.errorUserNotExists(pseudo, password);
		}
	}
	
	public static void addProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject) {
		
	}
}
