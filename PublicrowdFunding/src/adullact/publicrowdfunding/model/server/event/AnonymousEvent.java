package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.AnonymousErrorHandler;
import adullact.publicrowdfunding.model.server.request.AnonymousRequest;

public abstract class AnonymousEvent
<TRequest extends AnonymousRequest<TRequest, TEvent, TErrorHandler>, 
TEvent extends AnonymousEvent<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends AnonymousErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends Event<TRequest, TEvent, TErrorHandler> {

}
