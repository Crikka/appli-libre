package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.server.errorHandler.AdministratorErrorHandler;
import adullact.publicrowdfunding.model.server.event.AdministratorEvent;

/**
 * Created by Ferrand on 15/08/2014.
 */
public abstract class AdministratorRequest<
        TRequest extends AdministratorRequest<TRequest, TEvent, TErrorHandler>,
        TEvent extends AdministratorEvent<TRequest, TEvent, TErrorHandler>,
        TErrorHandler extends AdministratorErrorHandler<TRequest, TEvent, TErrorHandler>>
        extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler> {

    public AdministratorRequest(TEvent event, TErrorHandler errorHandler) {
        super(event, errorHandler);
    }
}
