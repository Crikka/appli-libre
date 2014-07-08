package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.UserRequester;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticatedErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;

public abstract class AuthenticatedEvent
<TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends Event<TRequest, TEvent, TErrorHandler> {
	final protected void retryWithAnotherLogin(String username, String password) { // DO NOT USE FOR THE MOMENT
		final Event<TRequest, TEvent, TErrorHandler> contextualEvent = this;
		UserRequester.authentificateUser(username, password, new AuthenticationEvent() {
			
			@Override
			public void ifUserIsAdministrator() {}
			
			@Override
			public void onAuthenticate() {
				contextualEvent.retry();
			}
			
			@Override
			public void errorUserNotExists(String pseudo, String password) {}
		});
	}
}
