package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import retrofit.client.Response;
import adullact.publicrowdfunding.model.server.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.server.request.AuthentificationRequest;

public class AuthentificationErrorHandler extends AuthentificatedErrorHandler<AuthentificationRequest, AuthentificationEvent, AuthentificationErrorHandler>{

	@Override
	public Throwable handleError(RetrofitError error) {
	    Response response = error.getResponse();
	    if (response != null && response.getStatus() == 401) {
	    	fail();
	    }
	    return error;
	}

}
