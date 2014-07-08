package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.server.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.server.request.ModifyAccountRequest;
import retrofit.RetrofitError;

public class ModifyAccountErrorHandler extends AuthenticatedErrorHandler<ModifyAccountRequest, ModifyAccountEvent, ModifyAccountErrorHandler> {

	@Override
	public Throwable handleError(RetrofitError error) {
		// TODO Auto-generated method stub
		return null;
	}

}
