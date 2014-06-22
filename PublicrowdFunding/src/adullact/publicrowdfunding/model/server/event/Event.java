package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.ServerObject;
import adullact.publicrowdfunding.model.server.errorHandler.ErrorHandler;
import adullact.publicrowdfunding.model.server.request.Request;

/**
 * @author Ferrand
 *
 * @param <TRequest>
 * @Brief Event is a abstract functions handle to requester.
 */
public abstract class Event
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ServerObject<TRequest, TEvent, TErrorHandler> {
	
	final protected void retry(){
		if(!request().isDone()){
			request().execute();
		}
	}
	
}
