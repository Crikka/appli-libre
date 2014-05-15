package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.event.Event;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request<TRequest extends Request<TRequest, TEvent>, TEvent extends Event<TEvent, TRequest>> {
	
	public Request(){
	}
		
	public abstract void execute(TEvent event);
	
}
