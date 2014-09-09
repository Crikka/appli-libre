package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
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
    private boolean m_authorized;

    public AuthenticatedRequest(TEvent event, TErrorHandler errorHandler) {
        super(event, errorHandler);

        if(Account.isConnect()) {
            try {
                this.m_username = Account.getOwn().getUsername();
                this.m_password = Account.getOwn().getPassword();
                this.m_authorized = true;
            } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
                this.m_authorized = false;
                event().errorAuthenticationRequired();
            }
        }
        else {
            this.m_authorized = false;
            event().errorAuthenticationRequired();
        }

        securityRequestCreate();
    }

    public AuthenticatedRequest(String username, String password, TEvent event, TErrorHandler errorHandler) {
        super(event, errorHandler);

        this.m_username = username;
        this.m_password = password;
        this.m_authorized = true;

        securityRequestCreate();
    }

    private void securityRequestCreate() {
        SecurityRequestInterceptor securityRequestInterceptor = new SecurityRequestInterceptor();
        securityRequestInterceptor.defineAuthenticator(m_username, m_password);
        defineRequestInterceptor(securityRequestInterceptor);
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

    @Override
    public void execute() {
        if(m_authorized) {
            secureExecute();
        }
    }

    public abstract void secureExecute();
}
