package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.RegistrationErrorHandler;
import adullact.publicrowdfunding.model.server.request.RegistrationRequest;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class RegistrationEvent extends Event<RegistrationRequest, RegistrationEvent, RegistrationErrorHandler> {

    /* Callback functions */
    public abstract void onRegister();
    public abstract void errorUsernameAlreadyUsed();
    public abstract void errorPseudoAlreadyUsed();
    /* ----------------- */
}
