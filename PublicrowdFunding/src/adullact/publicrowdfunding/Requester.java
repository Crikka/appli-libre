package adullact.publicrowdfunding;

import java.util.Date;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.reply.AuthentificationReply;
import adullact.publicrowdfunding.model.reply.CreateProjectReply;
import adullact.publicrowdfunding.model.request.AuthentificationRequest;
import adullact.publicrowdfunding.model.request.CreateProjectRequest;
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
				Share.user.defineFields(reply.pseudo(), password, reply.name(), reply.firstName());
				Share.user.authentificate();
				authentificationEvent.onAuthentificate();
				authentificationEvent.ifUserIsAdministrator();
			}
			else {
				Share.user.defineFields(reply.pseudo(), password, reply.name(), reply.firstName());
				Share.user.authentificate();
				authentificationEvent.onAuthentificate();
			}
		}
		else {
			authentificationEvent.errorUserNotExists(pseudo, password);
		}
	}
	
	public static void createProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject, CreateProjectEvent createProjectEvent){
		CreateProjectRequest createProjectRequest = new CreateProjectRequest(name, description, requestedFunding, beginOfProject, endOfProject);
		createProjectEvent.defineContextRequest(createProjectRequest);
		CreateProjectReply reply = createProjectRequest.execute();
		if(reply.ok()) {
			createProjectEvent.onProjectAdded(createProjectRequest.project());
			if(Share.user.isAdmin()) {
				createProjectEvent.ifUserIsAdministrator();;
			}
		}
		else {
			if(reply.isAuthentificationFailing()) {
				createProjectEvent.errorAuthentificationRequired();
			}
		}
	}
}
