package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateRequest;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class CreateEvent<TResource extends Resource<TResource, ?, ?>>
        extends AdministratorEvent<CreateRequest<TResource,?,?>,CreateEvent<TResource>,CreateErrorHandler<TResource>> {

	/* Callback functions */
    public abstract void errorResourceIdAlreadyUsed();
    public abstract void onCreate(TResource resource);
    /* ----------------- */

}