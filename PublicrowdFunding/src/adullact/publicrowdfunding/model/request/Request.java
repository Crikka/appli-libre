package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.event.Event;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request<TRequest extends Request<TRequest, TEvent>, TEvent extends Event<TEvent, TRequest>> {
	private boolean m_done;
	
	public Request(){
		super();
		
		m_done = false;
	}
	
	public boolean isDone() {
		return m_done;
	}
	
	public void done() {
		m_done = true;
	}
		
	public abstract void execute(TEvent event);
	
}
