package adullact.publicrowdfunding;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.model.server.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.server.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;
import adullact.publicrowdfunding.model.server.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.server.event.ValidateProjectEvent;
import adullact.publicrowdfunding.model.server.request.AuthentificationRequest;
import adullact.publicrowdfunding.model.server.request.CreateProjectRequest;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;
import adullact.publicrowdfunding.model.server.request.ModifyAccountRequest;
import adullact.publicrowdfunding.model.server.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

/**
 * @author Ferrand
 * @brief Use this class like an interface between server and application.
 */
public class Requester {

	public static void authentificateUser(String pseudo, String password, AuthentificationEvent authentificationEvent) {
		new AuthentificationRequest(pseudo, password, authentificationEvent).execute();;
	}

	/**
	 * @brief Set argument to null if you want to keep old value.
	 */
	public static void modifyAccount(String newPassword, String newName, String newFirstName, ModifyAccountEvent modifyAccountEvent) {
		new ModifyAccountRequest(newPassword, newName, newFirstName, modifyAccountEvent).execute();
	}

	public static void createUser(String username, String password, String name, String firstName, CreateUserEvent createUserEvent) {
		new CreateUserRequest(username, password, name, firstName, createUserEvent).execute();
	}


	public static void createProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject, CreateProjectEvent createProjectEvent, LatLng position){
		new CreateProjectRequest(name, description, requestedFunding, beginOfProject, endOfProject, position, createProjectEvent).execute();
	}

	public static void validateProject(Project project, ValidateProjectEvent validateProjectEvent) {
		new ValidateProjectRequest(project, validateProjectEvent).execute();
	}
}
