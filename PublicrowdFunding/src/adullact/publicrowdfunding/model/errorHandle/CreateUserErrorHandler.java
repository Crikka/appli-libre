package adullact.publicrowdfunding.model.errorHandle;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.event.CreateUserEvent;
import adullact.publicrowdfunding.model.request.CreateUserRequest;

public class CreateUserErrorHandler extends AnonymousErrorHandler<CreateUserErrorHandler, CreateUserRequest, CreateUserEvent>  {

	@Override
	public Throwable handleError(RetrofitError error) {
		System.out.println(error.getMessage());
		return null;
	}

}
