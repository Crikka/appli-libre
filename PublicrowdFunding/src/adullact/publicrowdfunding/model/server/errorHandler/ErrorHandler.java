package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.ServerObject;
import adullact.publicrowdfunding.model.server.event.Event;
import adullact.publicrowdfunding.model.server.request.Request;

public abstract class ErrorHandler
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> 
extends ServerObject<TRequest, TEvent, TErrorHandler> implements retrofit.ErrorHandler {
	private boolean m_ok = true;

    @Override
    public Throwable handleError(RetrofitError error) {
        fail();
        System.out.println(error.getMessage());
        return error;
    }
	
	final protected void fail() {
		m_ok = false;
	}
	
	final public boolean isOk() {
		return m_ok;
	}
}
