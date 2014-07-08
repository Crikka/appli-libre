package adullact.publicrowdfunding.model.server;

import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;
import adullact.publicrowdfunding.model.server.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;
import adullact.publicrowdfunding.model.server.request.ModifyAccountRequest;
import adullact.publicrowdfunding.model.server.request.UsersListingRequest;
import adullact.publicrowdfunding.shared.Commentary;
import adullact.publicrowdfunding.shared.Project;

import java.util.HashMap;
import java.util.Map;

public class UserRequester {
	public static void authenticateUser(String pseudo, String password, AuthenticationEvent authenticationEvent) {
		new AuthenticationRequest(pseudo, password, authenticationEvent).execute();
	}

	/**
	 *  Set argument to null if you want to keep old value.
	 */
	public static void modifyAccount(String newPassword, String newName, String newFirstName, ModifyAccountEvent modifyAccountEvent) {
		new ModifyAccountRequest(newPassword, newName, newFirstName, modifyAccountEvent).execute();
	}

	public static void createUser(String username, String password, String name, String firstName, CreateUserEvent createUserEvent) {
		new CreateUserRequest(username, password, name, firstName, createUserEvent).execute();
	}
	
	public static void listAllUsers(UsersListingEvent usersListingEvent) {
		new UsersListingRequest(new HashMap<String, String>(), usersListingEvent).execute();
	}
	
	public static void listUsersThatHaveFundedProject(Project project, UsersListingEvent usersListingEvent) {
        HashMap<String, String> filter = new HashMap<String, String>();
        filter.put("projectID", project.id());
        new UsersListingRequest(filter, usersListingEvent).execute();
	}

    public static void listUsersThatHaveCommentedProject(Commentary commentary, UsersListingEvent usersListingEvent) {
        HashMap<String, String> filter = new HashMap<String, String>();
        filter.put("commentaryID", Integer.toString(commentary.id()));
        new UsersListingRequest(filter, usersListingEvent).execute();
    }
}
