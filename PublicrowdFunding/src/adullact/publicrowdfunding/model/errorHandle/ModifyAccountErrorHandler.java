package adullact.publicrowdfunding.model.errorHandle;

import adullact.publicrowdfunding.model.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.request.ModifyAccountRequest;
import retrofit.RetrofitError;

public class ModifyAccountErrorHandler extends AuthentificatedErrorHandler<ModifyAccountErrorHandler, ModifyAccountRequest, ModifyAccountEvent>{

	@Override
	public Throwable handleError(RetrofitError error) {
		// TODO Auto-generated method stub
		return null;
	}

}
