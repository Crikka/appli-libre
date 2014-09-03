package adullact.publicrowdfunding.model.server.errorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.ServerObject;
import adullact.publicrowdfunding.model.server.event.Event;
import adullact.publicrowdfunding.model.server.request.Request;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class ErrorHandler
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ServerObject<TRequest, TEvent, TErrorHandler> implements retrofit.ErrorHandler {
    private boolean m_networkError = false;
    private boolean m_serverError = false;

    public void manageCallback() {
        if(m_networkError) {
            event().errorNetwork();
        }
        if(m_serverError) {
            event().errorServer();
        }
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        m_networkError = error.isNetworkError();

        if(error.getResponse().getStatus() == 500) {
            m_serverError = true;
        }

        return error;
    }

    private String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
