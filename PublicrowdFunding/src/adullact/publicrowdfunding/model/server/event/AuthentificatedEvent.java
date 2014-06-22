package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.Requester;
import adullact.publicrowdfunding.model.server.errorHandler.AuthentificatedErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthentificatedRequest;

public abstract class AuthentificatedEvent
<TRequest extends AuthentificatedRequest<TRequest, TEvent, TErrorHandler>, 
TEvent extends AuthentificatedEvent<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends AuthentificatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends Event<TRequest, TEvent, TErrorHandler> {
	final protected void retryWithAnotherLogin(String username, String password) { // DO NOT USE FOR THE MOMENT
		final Event<TRequest, TEvent, TErrorHandler> contextualEvent = this;
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
