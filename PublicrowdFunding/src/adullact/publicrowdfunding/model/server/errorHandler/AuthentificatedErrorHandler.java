package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.event.AuthentificatedEvent;
import adullact.publicrowdfunding.model.server.request.AuthentificatedRequest;

public abstract class AuthentificatedErrorHandler
<TRequest extends AuthentificatedRequest<TRequest, TEvent, TErrorHandler>, 
TEvent extends AuthentificatedEvent<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends AuthentificatedErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ErrorHandler<TRequest, TEvent, TErrorHandler> {

}