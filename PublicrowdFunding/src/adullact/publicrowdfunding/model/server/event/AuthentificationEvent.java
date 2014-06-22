package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.AuthentificationErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthentificationRequest;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

public abstract class AuthentificationEvent extends AuthentificatedEvent<AuthentificationRequest, AuthentificationEvent, AuthentificationErrorHandler> implements AdministratorFavour {
	
	/* Callback functions */
	public abstract void errorUserNotExists(String pseudo, String password);
	public abstract void onAuthentificate();
	/* ----------------- */
	
	public User user() {
		return Share.user;
	}
}
