package adullact.publicrowdfunding.model.server.request;

import retrofit.RestAdapter;
import adullact.publicrowdfunding.model.server.errorHandler.AuthentificatedErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthentificatedEvent;
import adullact.publicrowdfunding.shared.Share;

public abstract class AuthentificatedRequest
<TRequest extends AuthentificatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthentificatedEvent<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends AuthentificatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends Request<TRequest, TEvent, TErrorHandler> {
	protected String m_username;
	protected String m_password;
	
	public AuthentificatedRequest(TEvent event, TErrorHandler errorHandler) {
		super(event, new RestAdapter.Builder().setRequestInterceptor(new AuthentificationRequestInterceptor(Share.user.pseudo(), Share.user.password())), errorHandler);
		
		this.m_username = Share.user.pseudo();
		this.m_password = Share.user.password();
	}
	
	public AuthentificatedRequest(String username, String password, TEvent event, TErrorHandler errorHandler) {
		super(event, new RestAdapter.Builder().setRequestInterceptor(new AuthentificationRequestInterceptor(username, password)), errorHandler);
		
		this.m_username = username;
		this.m_password = password;
		
	}

}
