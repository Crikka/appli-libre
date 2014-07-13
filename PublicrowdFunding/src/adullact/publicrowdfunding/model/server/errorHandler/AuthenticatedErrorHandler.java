package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.event.AuthenticatedEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import retrofit.RetrofitError;

public abstract class AuthenticatedErrorHandler
<TRequest extends AuthenticatedRequest<TRequest, TEvent, TErrorHandler>,
TEvent extends AuthenticatedEvent<TRequest, TEvent, TErrorHandler>,
TErrorHandler extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler>>
extends ErrorHandler<TRequest, TEvent, TErrorHandler> {
    private boolean m_authenticationFail = false;

    @Override
    public void manageCallback() {
        super.manageCallback();
        if(m_authenticationFail) {
            event().errorAuthenticationFailed(request().username(), request().password());
        }
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        m_authenticationFail = (error.getResponse().getStatus() == 401);
        return super.handleError(error);
    }
}