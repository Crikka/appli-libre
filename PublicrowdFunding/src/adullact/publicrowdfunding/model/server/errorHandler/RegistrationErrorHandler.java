package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.entities.ServerRegistrationResponse;
import adullact.publicrowdfunding.model.server.event.RegistrationEvent;
import adullact.publicrowdfunding.model.server.request.RegistrationRequest;

/**
 * Created by Ferrand on 16/08/2014.
 */
public class RegistrationErrorHandler extends ErrorHandler<RegistrationRequest, RegistrationEvent, RegistrationErrorHandler> {

    public void manageCallback(ServerRegistrationResponse response) {
        super.manageCallback();
        if(response.userOK == 0) {
            event().errorPseudoAlreadyUsed();
        }
        if(response.accountOK == 0) {
            event().errorUsernameAlreadyUsed();
        }
    }
}
