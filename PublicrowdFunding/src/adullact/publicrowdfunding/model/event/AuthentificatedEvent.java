package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.Requester;
import adullact.publicrowdfunding.model.errorHandle.AuthentificatedErrorHandler;
import adullact.publicrowdfunding.model.request.AuthentificatedRequest;

public abstract class AuthentificatedEvent
<TEvent extends AuthentificatedEvent<TEvent, TRequest, TError>, TRequest extends AuthentificatedRequest<TRequest, TEvent, TError>, TError extends AuthentificatedErrorHandler<TError, TRequest, TEvent>>
extends Event<TEvent, TRequest, TError> {
	final protected void retryWithAnotherLogin(String username, String password) { // DO NOT USE FOR THE MOMENT
		final Event<TEvent, TRequest, TError> contextualEvent = this;
		Requester.authentificateUser(username, password, new AuthentificationEvent() {
			
			@Override
			public void ifUserIsAdministrator() {}
			
			@Override
			public void onAuthentificate() {
				contextualEvent.retry();
			}
			
			@Override
			public void errorUserNotExists(String pseudo, String password) {}
		});
	}
}
