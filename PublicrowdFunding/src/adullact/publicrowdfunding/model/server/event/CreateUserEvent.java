package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;

public abstract class CreateUserEvent extends AnonymousEvent<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler> {
	
	/* Callback functions */
	public abstract void errorUsernameAlreadyExists(String username);
	public abstract void onCreateUser();
	/* ----------------- */
	
}
