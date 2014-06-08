package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.AuthentificatedErrorHandler;
import adullact.publicrowdfunding.model.request.AuthentificatedRequest;

public abstract class AuthentificatedEvent
<TEvent extends AuthentificatedEvent<TEvent, TRequest, TError>, TRequest extends AuthentificatedRequest<TRequest, TEvent, TError>, TError extends AuthentificatedErrorHandler<TError, TRequest, TEvent>>
extends Event<TEvent, TRequest, TError> {

}
