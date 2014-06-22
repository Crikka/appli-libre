package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.ModifyAccountErrorHandler;
import adullact.publicrowdfunding.model.server.request.ModifyAccountRequest;

public abstract class ModifyAccountEvent extends AuthentificatedEvent<ModifyAccountRequest, ModifyAccountEvent, ModifyAccountErrorHandler> implements AuthentificationRequired {
	
	/* Callback functions */
	public abstract void onModifyAccount();
	public abstract void errorUsernameDoesNotExist(String username);
	/* ----------------- */
	
}
	