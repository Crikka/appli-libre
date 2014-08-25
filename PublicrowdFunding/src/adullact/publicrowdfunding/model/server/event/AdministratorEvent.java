package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.AdministratorErrorHandler;
import adullact.publicrowdfunding.model.server.request.AdministratorRequest;

/**
 * Created by Ferrand on 15/08/2014.
 */
public abstract class AdministratorEvent<
        TRequest extends AdministratorRequest<TRequest, TEvent, TErrorHandler>,
        TEvent extends AdministratorEvent<TRequest, TEvent, TErrorHandler>,
        TErrorHandler extends AdministratorErrorHandler<TRequest, TEvent, TErrorHandler>>
        extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler> {


    /* Callback functions */
    public abstract void errorAdministratorRequired();
	/* ----------------- */
}
