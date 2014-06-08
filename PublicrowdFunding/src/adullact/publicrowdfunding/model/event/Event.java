package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.ErrorHandler;
import adullact.publicrowdfunding.model.request.Request;

/**
 * @author Ferrand
 *
 * @param <TRequest>
 * @Brief Event is a abstract functions handle to requester.
 */
public abstract class Event
<TEvent extends Event<TEvent, TRequest, TError>, TRequest extends Request<TRequest, TEvent, TError>, TError extends ErrorHandler<TError, TRequest, TEvent>> {
	private TRequest m_request;
	
	public Event() {
		this.m_request = null;
	}
	
	public void defineContextRequest(TRequest request){
		m_request = request;
	}
	
	final protected void retry(){
		if(!m_request.isDone()){
			m_request.execute();
		}
	}
	
	public TRequest request() {
		return m_request;
	}
}
