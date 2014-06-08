package adullact.publicrowdfunding.model.errorHandle;

import adullact.publicrowdfunding.model.event.AuthentificatedEvent;
import adullact.publicrowdfunding.model.request.AuthentificatedRequest;

public abstract class AuthentificatedErrorHandler
<TError extends AuthentificatedErrorHandler<TError, TRequest, TEvent>, TRequest extends AuthentificatedRequest<TRequest, TEvent, TError>, TEvent extends AuthentificatedEvent<TEvent, TRequest, TError>> 
extends ErrorHandler<TError, TRequest, TEvent> {

}