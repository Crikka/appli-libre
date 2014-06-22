package adullact.publicrowdfunding.model.server.request;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.ServerObject;
import adullact.publicrowdfunding.model.server.errorHandler.ErrorHandler;
import adullact.publicrowdfunding.model.server.event.Event;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ServerObject<TRequest, TEvent, TErrorHandler> {
	private boolean m_done;
	protected RestAdapter m_restAdapter;
	
	public Request(TEvent event, Builder restAdapaterBuilder, TErrorHandler errorHandler){
		super(event, errorHandler);

		m_restAdapter = restAdapaterBuilder.setErrorHandler(errorHandler).setEndpoint(ServerInfo.SERVER_URL).build();
		m_done = false;
	}
	
	public boolean isDone() {
		return m_done;
	}
	
	public void done() {
		m_done = true;
	}
	
	public abstract void execute();
	
}
