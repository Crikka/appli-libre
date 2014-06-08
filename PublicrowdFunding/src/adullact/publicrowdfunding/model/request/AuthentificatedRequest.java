package adullact.publicrowdfunding.model.request;

import retrofit.RestAdapter;
import adullact.publicrowdfunding.model.errorHandle.AuthentificatedErrorHandler;
import adullact.publicrowdfunding.model.event.AuthentificatedEvent;
import adullact.publicrowdfunding.shared.Share;

public abstract class AuthentificatedRequest
<TRequest extends AuthentificatedRequest<TRequest, TEvent, TError>, TEvent extends AuthentificatedEvent<TEvent, TRequest, TError>, TError extends AuthentificatedErrorHandler<TError, TRequest, TEvent>>
extends Request<TRequest, TEvent, TError> {
	
	public AuthentificatedRequest(TEvent event, TError errorHandler) {
		super(event, new RestAdapter.Builder().setRequestInterceptor(new AuthentificationRequestInterceptor(Share.user.pseudo(), Share.user.password())), errorHandler);
	}
	
	public AuthentificatedRequest(String username, String password, TEvent event, TError errorHandler) {
		super(event, new RestAdapter.Builder().setRequestInterceptor(new AuthentificationRequestInterceptor(username, password)), errorHandler);
		
	}

}
