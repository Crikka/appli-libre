package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticatedErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthenticatedEvent;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class AuthenticatedRequest
        <TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
                TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
                TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
        extends Request<TRequest, TEvent, TErrorHandler> {
    private String m_username;
    private String m_password;

    public AuthenticatedRequest(TEvent event, TErrorHandler errorHandler) {
        super(event, errorHandler);

        this.m_username = Account.getOwnOrAnonymous().getUsername();
        this.m_password = Account.getOwnOrAnonymous().getPassword();

        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(m_username, m_password);
        defineRequestInterceptor(securityRequestInterceptor);

    }

    public AuthenticatedRequest(String username, String password, TEvent event, TErrorHandler errorHandler) {
        super(event, errorHandler);

        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(username, password);
        defineRequestInterceptor(securityRequestInterceptor);

        this.m_username = username;
        this.m_password = password;

    }

    public String username() {
        return m_username;
    }

    public String password() {
        return m_password;
    }

    public void changeAuthentication(String username, String password) {
        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(username, password);
        defineRequestInterceptor(securityRequestInterceptor);

        this.m_username = username;
        this.m_password = password;
    }

}
