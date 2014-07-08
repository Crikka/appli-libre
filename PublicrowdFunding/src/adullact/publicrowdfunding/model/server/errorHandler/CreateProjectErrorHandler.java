package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.server.request.CreateProjectRequest;

public class CreateProjectErrorHandler extends AuthenticatedErrorHandler<CreateProjectRequest, CreateProjectEvent, CreateProjectErrorHandler> {

	@Override
	public Throwable handleError(RetrofitError error) {
		System.out.println(error.getMessage());
		return null;
	}

}
