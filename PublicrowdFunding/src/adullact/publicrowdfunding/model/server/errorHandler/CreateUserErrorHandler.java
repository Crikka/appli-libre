package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;

public class CreateUserErrorHandler extends AnonymousErrorHandler<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler>  {

	@Override
	public Throwable handleError(RetrofitError error) {
		System.out.println(error.getMessage());
		return null;
	}

}
