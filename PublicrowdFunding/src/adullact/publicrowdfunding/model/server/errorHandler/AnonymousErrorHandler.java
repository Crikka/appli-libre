package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.event.AnonymousEvent;
import adullact.publicrowdfunding.model.server.request.AnonymousRequest;

public abstract class AnonymousErrorHandler
<TRequest extends AnonymousRequest<TRequest, TEvent, TErrorHandler>, 
TEvent extends AnonymousEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AnonymousErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ErrorHandler<TRequest, TEvent, TErrorHandler> {

}
