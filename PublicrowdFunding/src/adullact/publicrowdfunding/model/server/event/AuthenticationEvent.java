package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;

/**
 * Created by Ferrand on 26/07/2014.
 */
public abstract class AuthenticationEvent extends AuthenticatedEvent<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> {

    /* Callback functions */
    public abstract void errorUsernamePasswordDoesNotMatch(String username, String password);
    public abstract void onAuthentication();
    /* ----------------- */

    @Override
    public void errorAuthenticationRequired() {
    }
}
