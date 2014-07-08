package adullact.publicrowdfunding.model.server.request;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.ServerObject;
import adullact.publicrowdfunding.model.server.errorHandler.ErrorHandler;
import adullact.publicrowdfunding.model.server.event.Event;

/**
 * 
 * @author Ferrand
 *
 */
public abstract class Request
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ServerObject<TRequest, TEvent, TErrorHandler> {
	private boolean m_done;
    private ServerInfo.Service m_service;
	
	public Request(TEvent event, TErrorHandler errorHandler){
        super(event, errorHandler);

        this.m_service = new RestAdapter.Builder().setRequestInterceptor(new SecurityRequestInterceptor()).setErrorHandler(errorHandler()).setEndpoint(ServerInfo.SERVER_URL).build().create(ServerInfo.Service.class);
		m_done = false;
	}

    protected void defineRequestInterceptor(RequestInterceptor requestInterceptor) {
        m_service = new RestAdapter.Builder().setRequestInterceptor(requestInterceptor).setErrorHandler(errorHandler()).setEndpoint(ServerInfo.SERVER_URL).build().create(ServerInfo.Service.class);
    }

    protected ServerInfo.Service service() {

        return m_service;
    }
	
	public boolean isDone() {
		return m_done;
	}
	
	public void done() {
		m_done = true;
	}
	
	public abstract void execute();
	
}
