package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.UpdateErrorHandler;
import adullact.publicrowdfunding.model.server.request.UpdateRequest;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class UpdateEvent<TResource extends Resource<TResource, ?, ?>>
        extends AdministratorEvent<UpdateRequest<TResource, ?, ?>, UpdateEvent<TResource>, UpdateErrorHandler<TResource>> {
	
	/* Callback functions */
	public abstract void onUpdate(TResource resource);
	public abstract void errorResourceIdDoesNotExist();
	/* ----------------- */
	
}
	