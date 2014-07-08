package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.server.errorHandler.AnonymousErrorHandler;
import adullact.publicrowdfunding.model.server.event.AnonymousEvent;

public abstract class AnonymousRequest
<TRequest extends AnonymousRequest<TRequest, TEvent, TErrorHandler>, 
TEvent extends AnonymousEvent<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends AnonymousErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends Request<TRequest, TEvent, TErrorHandler> {
	public AnonymousRequest(TEvent event, TErrorHandler errorHandler) {
		super(event, errorHandler);
	}
}
