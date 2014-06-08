package adullact.publicrowdfunding.model.errorHandle;

import adullact.publicrowdfunding.model.event.Event;
import adullact.publicrowdfunding.model.request.Request;

public abstract class ErrorHandler
<TError extends ErrorHandler<TError, TRequest, TEvent>, TRequest extends Request<TRequest, TEvent, TError>, TEvent extends Event<TEvent, TRequest, TError>> implements retrofit.ErrorHandler {
	private boolean m_ok = true; 
	private TEvent m_event;
	private TRequest m_request;
	
	final protected void fail() {
		m_ok = false;
	}
	
	final public boolean isOk() {
		return m_ok;
	}

	public void defineEvent(TEvent event){
		this.m_event = event;
	}
	
	public TEvent event() {
		return m_event;
	}
	
	public void defineRequest(TRequest request){
		this.m_request = request;
	}
	
	public TRequest request() {
		return m_request;
	}
}
