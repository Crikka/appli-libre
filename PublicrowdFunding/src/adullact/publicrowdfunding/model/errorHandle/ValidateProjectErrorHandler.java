package adullact.publicrowdfunding.model.errorHandle;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.event.ValidateProjectEvent;
import adullact.publicrowdfunding.model.request.ValidateProjectRequest;

public class ValidateProjectErrorHandler extends AuthentificatedErrorHandler<ValidateProjectErrorHandler, ValidateProjectRequest, ValidateProjectEvent>{

	@Override
	public Throwable handleError(RetrofitError error) {
		// TODO Auto-generated method stub
		return null;
	}
}
