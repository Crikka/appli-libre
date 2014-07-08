package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.ModifyAccountErrorHandler;
import adullact.publicrowdfunding.model.server.request.ModifyAccountRequest;

public abstract class ModifyAccountEvent extends AuthenticatedEvent<ModifyAccountRequest, ModifyAccountEvent, ModifyAccountErrorHandler> implements AuthenticationRequired {
	
	/* Callback functions */
	public abstract void onModifyAccount();
	public abstract void errorUsernameDoesNotExist(String username);
	/* ----------------- */
	
}
	