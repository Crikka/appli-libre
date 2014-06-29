package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.CreateUserErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

public abstract class CreateUserEvent extends AnonymousEvent<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler> {

	/* Callback functions */
	public abstract void errorUsernameAlreadyExists(String username);
	public abstract void onCreateUser();
	/* ----------------- */

	protected void authentificate() {
		if(request().isDone()){
			Share.user = new User();
			Share.user.defineFields(request().username(), request().password(), request().name(), request().firstName());
			Share.user.authentificate();
		}
	}

}
