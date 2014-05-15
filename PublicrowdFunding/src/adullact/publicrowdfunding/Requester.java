package adullact.publicrowdfunding;

import java.util.Date;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.event.ValidateProjectEvent;
import adullact.publicrowdfunding.model.request.AuthentificationRequest;
import adullact.publicrowdfunding.model.request.CreateProjectRequest;
import adullact.publicrowdfunding.model.request.ModifyAccountRequest;
import adullact.publicrowdfunding.model.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Requester {
	
	public static void authentificateUser(String pseudo, String password, AuthentificationEvent authentificationEvent) {
		AuthentificationRequest authentificationRequest = new AuthentificationRequest(pseudo, password);
		authentificationEvent.defineContextRequest(authentificationRequest);
		authentificationRequest.execute(authentificationEvent);
	}
	
	/**
	 * @brief Set argument to null if you want to keep old value.
	 */
	public static void modifyAccount(String newPseudo, String newPassword, String newName, String newFirstName, ModifyAccountEvent modifyAccountEvent) {
		ModifyAccountRequest modifyAccountRequest = new ModifyAccountRequest(newPseudo, newPassword, newName, newFirstName);
		modifyAccountEvent.defineContextRequest(modifyAccountRequest);
		modifyAccountRequest.execute(modifyAccountEvent);
	}
	
	public static void createProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject, CreateProjectEvent createProjectEvent){
		CreateProjectRequest createProjectRequest = new CreateProjectRequest(name, description, requestedFunding, beginOfProject, endOfProject);
		createProjectEvent.defineContextRequest(createProjectRequest);
		createProjectRequest.execute(createProjectEvent);
	}
	
	public static void validateProject(Project project, ValidateProjectEvent validateProjectEvent) {
		ValidateProjectRequest validateProjectRequest = new ValidateProjectRequest(project);
		validateProjectEvent.defineContextRequest(validateProjectRequest);
		validateProjectRequest.execute(validateProjectEvent);
	}
}
