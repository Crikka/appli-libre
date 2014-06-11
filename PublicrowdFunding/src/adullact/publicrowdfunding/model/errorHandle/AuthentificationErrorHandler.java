package adullact.publicrowdfunding.model.errorHandle;

import retrofit.RetrofitError;
import retrofit.client.Response;
import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.request.AuthentificationRequest;

public class AuthentificationErrorHandler extends AuthentificatedErrorHandler<AuthentificationErrorHandler, AuthentificationRequest, AuthentificationEvent>{

	@Override
	public Throwable handleError(RetrofitError error) {
	    Response response = error.getResponse();
	    if (response != null && response.getStatus() == 401) {
	    	fail();
	    }
	    return error;
	}

}
