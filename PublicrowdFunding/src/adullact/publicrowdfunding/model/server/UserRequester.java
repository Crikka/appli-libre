package adullact.publicrowdfunding.model.server;

import adullact.publicrowdfunding.model.server.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;
import adullact.publicrowdfunding.model.server.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.model.server.request.AuthentificationRequest;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;
import adullact.publicrowdfunding.model.server.request.ModifyAccountRequest;
import adullact.publicrowdfunding.model.server.request.UsersListingRequest;
import adullact.publicrowdfunding.shared.Project;

public class UserRequester {
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
	
	public static void listOfAllUser(UsersListingEvent usersListingEvent) {
		new UsersListingRequest(null, usersListingEvent).execute();
	}
	
	public static void listOfUserThatHaveFundedProject(Project project, UsersListingEvent usersListingEvent) {
		new UsersListingRequest(project, usersListingEvent).execute();
	}

}
