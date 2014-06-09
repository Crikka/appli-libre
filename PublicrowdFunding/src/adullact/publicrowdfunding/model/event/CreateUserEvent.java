package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.request.CreateUserRequest;

public abstract class CreateUserEvent extends AnonymousEvent<CreateUserEvent, CreateUserRequest, CreateUserErrorHandler> {
	
	/* Callback functions */
	public abstract void errorUsernameAlreadyExists(String username);
	public abstract void onCreateUser();
	/* ----------------- */
	
}
