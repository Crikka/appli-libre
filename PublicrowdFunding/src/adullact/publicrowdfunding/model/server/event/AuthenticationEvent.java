package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

public abstract class AuthenticationEvent extends AuthenticatedEvent<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> implements AdministratorFavour {
	
	/* Callback functions */
	public abstract void errorUserNotExists(String pseudo, String password);
	public abstract void onAuthenticate();
	/* ----------------- */
	
	public User user() {
		return Share.user;
	}
}
