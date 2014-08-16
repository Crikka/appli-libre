package adullact.publicrowdfunding.model.server.event;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.server.errorHandler.RegistrationErrorHandler;
import adullact.publicrowdfunding.model.server.request.RegistrationRequest;

/**
 * Created by Ferrand on 16/08/2014.
 */
public abstract class RegistrationEvent extends Event<RegistrationRequest, RegistrationEvent, RegistrationErrorHandler> {

    /* Callback functions */
    public abstract void onRegister();
    public abstract void errorUsernameAlreadyUsed();
    public abstract void errorPseudoAlreadyUsed();
    /* ----------------- */
}
