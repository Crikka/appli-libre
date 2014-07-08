package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.server.errorHandler.AuthenticatedErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthenticatedEvent;
import adullact.publicrowdfunding.shared.Share;

public abstract class AuthenticatedRequest
<TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends Request<TRequest, TEvent, TErrorHandler> {
	protected String m_username;
	protected String m_password;
	
	public AuthenticatedRequest(TEvent event, TErrorHandler errorHandler) {
		super(event, errorHandler);

        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(Share.user.pseudo(), Share.user.password());
        defineRequestInterceptor(securityRequestInterceptor);
		
		this.m_username = Share.user.pseudo();
		this.m_password = Share.user.password();
	}
	
	public AuthenticatedRequest(String username, String password, TEvent event, TErrorHandler errorHandler) {
		super(event, errorHandler);

        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(username, password);
        defineRequestInterceptor(securityRequestInterceptor);

        this.m_username = username;
		this.m_password = password;
		
	}

    public void changeAuthentication(String username, String password) {
        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(username, password);
        defineRequestInterceptor(securityRequestInterceptor);

        this.m_username = username;
        this.m_password = password;
    }

}
