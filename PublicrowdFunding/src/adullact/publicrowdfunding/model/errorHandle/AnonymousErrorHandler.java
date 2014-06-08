package adullact.publicrowdfunding.model.errorHandle;

import adullact.publicrowdfunding.model.event.AnonymousEvent;
import adullact.publicrowdfunding.model.request.AnonymousRequest;

public abstract class AnonymousErrorHandler
<TError extends AnonymousErrorHandler<TError, TRequest, TEvent>, TRequest extends AnonymousRequest<TRequest, TEvent, TError>, TEvent extends AnonymousEvent<TEvent, TRequest, TError>> 
extends ErrorHandler<TError, TRequest, TEvent> {

}
