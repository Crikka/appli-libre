package adullact.publicrowdfunding.model.errorHandle;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.request.CreateProjectRequest;

public class CreateProjectErrorHandler extends AuthentificatedErrorHandler<CreateProjectErrorHandler, CreateProjectRequest, CreateProjectEvent>{

	@Override
	public Throwable handleError(RetrofitError error) {
		// TODO Auto-generated method stub
		return null;
	}

}
