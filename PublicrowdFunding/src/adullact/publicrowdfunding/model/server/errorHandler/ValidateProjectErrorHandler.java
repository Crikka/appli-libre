package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.ValidateProjectEvent;
import adullact.publicrowdfunding.model.server.request.ValidateProjectRequest;

public class ValidateProjectErrorHandler extends AuthentificatedErrorHandler<ValidateProjectRequest, ValidateProjectEvent, ValidateProjectErrorHandler>{

	@Override
	public Throwable handleError(RetrofitError error) {
		// TODO Auto-generated method stub
		return null;
	}
}
