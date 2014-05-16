package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.request.Request;

/**
 * @author Ferrand
 *
 * @param <TRequest>
 * @Brief Event is a abstract functions handle to requester.
 */
public abstract class Event<TEvent extends Event<TEvent, TRequest>, TRequest extends Request<TRequest, TEvent>> {
	private TRequest m_request;
	
	public Event() {
		this.m_request = null;
	}
	
	public void defineContextRequest(TRequest request){
		m_request = request;
	}
	
	protected abstract void retry();
	
	public TRequest request() {
		return m_request;
	}
}
