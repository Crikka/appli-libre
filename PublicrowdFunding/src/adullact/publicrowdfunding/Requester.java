package adullact.publicrowdfunding;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.event.CreateUserEvent;
import adullact.publicrowdfunding.model.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.event.ValidateProjectEvent;
import adullact.publicrowdfunding.model.request.AuthentificationRequest;
import adullact.publicrowdfunding.model.request.CreateProjectRequest;
import adullact.publicrowdfunding.model.request.CreateUserRequest;
import adullact.publicrowdfunding.model.request.ModifyAccountRequest;
import adullact.publicrowdfunding.model.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Requester {
	
	public static void authentificateUser(String pseudo, String password, AuthentificationEvent authentificationEvent) {
		AuthentificationRequest authentificationRequest = new AuthentificationRequest(pseudo, password, authentificationEvent);
		authentificationEvent.defineContextRequest(authentificationRequest);
		authentificationRequest.execute();
	}
	
	/**
	 * @brief Set argument to null if you want to keep old value.
	 */
	public static void modifyAccount(String newPassword, String newName, String newFirstName, ModifyAccountEvent modifyAccountEvent) {
		ModifyAccountRequest modifyAccountRequest = new ModifyAccountRequest(newPassword, newName, newFirstName, modifyAccountEvent);
		modifyAccountEvent.defineContextRequest(modifyAccountRequest);
		modifyAccountRequest.execute();
	}
	
	public static void createUser(String username, String password, String name, String firstName, CreateUserEvent createUserEvent) {
		CreateUserRequest createUserRequest = new CreateUserRequest(username, password, name, firstName, createUserEvent);
		createUserEvent.defineContextRequest(createUserRequest);
		createUserRequest.execute();
	}

	
	public static void createProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject, CreateProjectEvent createProjectEvent, LatLng position){
		CreateProjectRequest createProjectRequest = new CreateProjectRequest(name, description, requestedFunding, beginOfProject, endOfProject, position, createProjectEvent);
		createProjectEvent.defineContextRequest(createProjectRequest);
		createProjectRequest.execute();
	}
	
	public static void validateProject(Project project, ValidateProjectEvent validateProjectEvent) {
		ValidateProjectRequest validateProjectRequest = new ValidateProjectRequest(project, validateProjectEvent);
		validateProjectEvent.defineContextRequest(validateProjectRequest);
		validateProjectRequest.execute();
	}
}
