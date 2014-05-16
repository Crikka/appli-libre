package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.request.ModifyAccountRequest;

public abstract class ModifyAccountEvent extends Event<ModifyAccountEvent, ModifyAccountRequest> implements AuthentificationRequired {
	
	/* Callback functions */
	public abstract void onModifyAccount();
	/* ----------------- */
	
	@Override
	protected void retry() {
		if(!request().isDone()) {
			request().execute(this);
		}
	}
	
}
	