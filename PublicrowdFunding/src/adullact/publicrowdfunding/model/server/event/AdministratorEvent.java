package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.AdministratorErrorHandler;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticatedErrorHandler;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.request.AdministratorRequest;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import adullact.publicrowdfunding.model.server.request.CreateRequest;

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
