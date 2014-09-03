package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticatedErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class AuthenticatedEvent
<TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends Event<TRequest, TEvent, TErrorHandler> {

    /* Callback functions */
    public abstract void errorAuthenticationRequired();
	/* ----------------- */

    final protected boolean isAdmin() {
        try {
            return Account.getOwn().isAdmin();
        } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
            return false;
        }
    }

	final protected void retryWithAnotherAccount(String username, String password) {
		final AuthenticatedEvent<TRequest, TEvent, TErrorHandler> contextualEvent = this;
        request().changeAuthentication(username, password);
        AuthenticationRequest request = new AuthenticationRequest(username, password, new AuthenticationEvent() {
            @Override
            public void errorAuthenticationRequired() {
                contextualEvent.errorAuthenticationRequired();
            }

            @Override
            public void errorUsernamePasswordDoesNotMatch(String username, String password) {
                contextualEvent.errorAuthenticationRequired();
            }

            @Override
            public void onAuthentication() {
                contextualEvent.retry();
            }

            @Override
            public void errorNetwork() {
                contextualEvent.errorNetwork();
            }

            @Override
            public void errorServer() {
                contextualEvent.errorServer();
            }
        });
        request.execute();
	}
}
