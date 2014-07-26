package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.errorHandler.UpdateErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import adullact.publicrowdfunding.model.server.request.UpdateRequest;

public abstract class UpdateEvent<TResource extends Resource<TResource, ?, ?>>
        extends AuthenticatedEvent<UpdateRequest<TResource, ?, ?>, UpdateEvent<TResource>, UpdateErrorHandler<TResource>>  {
	
	/* Callback functions */
	public abstract void onModifyAccount();
	public abstract void errorUsernameDoesNotExist(String username);
	/* ----------------- */
	
}
	