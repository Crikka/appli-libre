package adullact.publicrowdfunding.model.request;

import retrofit.RestAdapter;
import adullact.publicrowdfunding.model.errorHandle.AnonymousErrorHandler;
import adullact.publicrowdfunding.model.event.AnonymousEvent;
import adullact.publicrowdfunding.model.server.ServerInfo;

public abstract class AnonymousRequest
<TRequest extends AnonymousRequest<TRequest, TEvent, TError>, TEvent extends AnonymousEvent<TEvent, TRequest, TError>, TError extends AnonymousErrorHandler<TError, TRequest, TEvent>> 
extends Request<TRequest, TEvent, TError> {
	public AnonymousRequest(TEvent event, TError errorHandler) {
		super(event, new RestAdapter.Builder().setEndpoint(ServerInfo.SERVER_URL), errorHandler);
	}
}
