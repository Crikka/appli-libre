package adullact.publicrowdfunding.model.server.errorHandler;

import android.util.Log;

import java.io.IOException;

import adullact.publicrowdfunding.model.server.event.AdministratorEvent;
import adullact.publicrowdfunding.model.server.request.AdministratorRequest;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import retrofit.RetrofitError;

/**
 * Created by Ferrand on 15/08/2014.
 */
public class AdministratorErrorHandler<
        TRequest extends AdministratorRequest<TRequest, TEvent, TErrorHandler>,
        TEvent extends AdministratorEvent<TRequest, TEvent, TErrorHandler>,
        TErrorHandler extends AdministratorErrorHandler<TRequest, TEvent, TErrorHandler>>
        extends AuthenticatedErrorHandler<TRequest, TEvent, TErrorHandler> {
    boolean m_administratorRequired = false;

    @Override
    public void manageCallback() {
        super.manageCallback();

        if(m_administratorRequired) {
            event().errorAdministratorRequired();
        }
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        m_administratorRequired = (error.getResponse().getStatus() == 403);
        return super.handleError(error);
    }
}
