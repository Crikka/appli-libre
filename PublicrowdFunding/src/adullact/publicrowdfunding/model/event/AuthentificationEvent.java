package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.request.AuthentificationRequest;
import adullact.publicrowdfunding.shared.Share;
import adullact.publicrowdfunding.shared.User;

public abstract class AuthentificationEvent extends Event<AuthentificationEvent, AuthentificationRequest> implements AdministratorFavour {
	
	/* Callback functions */
	public abstract void errorUserNotExists(String pseudo, String password);
	public abstract void onAuthentificate();
	/* ----------------- */
	
	@Override
	protected void retry() {
		if(!request().isDone()) {
			request().execute(this);
		}
	}
	
	public User user() {
		return Share.user;
	}
}
