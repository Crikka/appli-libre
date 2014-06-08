package adullact.publicrowdfunding.model.request;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import adullact.publicrowdfunding.model.errorHandle.ErrorHandler;
import adullact.publicrowdfunding.model.event.Event;
import adullact.publicrowdfunding.model.server.ServerInfo;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request
<TRequest extends Request<TRequest, TEvent, TError>, TEvent extends Event<TEvent, TRequest, TError>,TError extends ErrorHandler<TError, TRequest, TEvent>> {
	private boolean m_done;
	private TError m_errorHandler;
	private TEvent m_event;
	protected RestAdapter m_restAdapter;
	
	public Request(TEvent event, Builder restAdapaterBuilder, TError errorHandler){
		super();
		this.m_errorHandler = errorHandler;
		this.m_event = event;
		
		m_restAdapter = restAdapaterBuilder.setErrorHandler(errorHandler).setEndpoint(ServerInfo.SERVER_URL).build();
		m_done = false;
	}
	
	public boolean isDone() {
		return m_done;
	}
	
	public void done() {
		m_done = true;
	}
	
	protected final TError errorHandler() {
		return m_errorHandler;
	}
	
	protected final TEvent event() {
		return m_event;
	}
		
	public abstract void execute();
	
}
