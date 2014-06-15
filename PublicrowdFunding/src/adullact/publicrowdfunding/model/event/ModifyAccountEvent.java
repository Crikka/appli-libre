package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.ModifyAccountErrorHandler;
import adullact.publicrowdfunding.model.request.ModifyAccountRequest;

public abstract class ModifyAccountEvent extends AuthentificatedEvent<ModifyAccountEvent, ModifyAccountRequest, ModifyAccountErrorHandler> implements AuthentificationRequired {
	
	/* Callback functions */
	public abstract void onModifyAccount();
	public abstract void errorUsernameDoesNotExist(String username);
	/* ----------------- */
	
}
	