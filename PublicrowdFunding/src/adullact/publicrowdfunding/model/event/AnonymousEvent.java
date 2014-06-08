package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.AnonymousErrorHandler;
import adullact.publicrowdfunding.model.request.AnonymousRequest;

public abstract class AnonymousEvent
<TEvent extends AnonymousEvent<TEvent, TRequest, TError>, TRequest extends AnonymousRequest<TRequest, TEvent, TError>, TError extends AnonymousErrorHandler<TError, TRequest, TEvent>> 
extends Event<TEvent, TRequest, TError> {

}
