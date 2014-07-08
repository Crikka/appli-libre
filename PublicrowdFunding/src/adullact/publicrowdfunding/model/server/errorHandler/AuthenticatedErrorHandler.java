package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.event.AuthenticatedEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;

public abstract class AuthenticatedErrorHandler
<TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends ErrorHandler<TRequest, TEvent, TErrorHandler> {

}