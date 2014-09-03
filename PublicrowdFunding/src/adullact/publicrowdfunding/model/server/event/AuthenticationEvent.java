package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;

/**
 * @author Ferrand and Nelaupe
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
