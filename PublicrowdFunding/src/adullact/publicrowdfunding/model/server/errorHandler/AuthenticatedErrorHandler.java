package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.event.AuthenticatedEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import retrofit.RetrofitError;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class AuthenticatedErrorHandler
<TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends ErrorHandler<TRequest, TEvent, TErrorHandler> {
    private boolean m_authenticationRequired = false;

    @Override
    public void manageCallback() {
        super.manageCallback();
        if(m_authenticationRequired) {
            event().errorAuthenticationRequired();
        }
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        m_authenticationRequired = (error.getResponse().getStatus() == 401);
        return super.handleError(error);
    }
}