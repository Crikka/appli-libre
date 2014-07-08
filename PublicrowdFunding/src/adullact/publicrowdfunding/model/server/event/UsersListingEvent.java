package adullact.publicrowdfunding.model.server.event;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.server.errorHandler.UsersListingErrorHandler;
import adullact.publicrowdfunding.model.server.request.UsersListingRequest;
import adullact.publicrowdfunding.shared.User;

public abstract class UsersListingEvent extends AuthenticatedEvent<UsersListingRequest, UsersListingEvent, UsersListingErrorHandler> {
	public abstract void onUsersReceived(ArrayList<User> users);

}
