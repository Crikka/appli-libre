package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.RetrieveErrorHandler;
import adullact.publicrowdfunding.model.server.request.RetrieveRequest;

public abstract class RetrieveEvent<TResource extends Resource<TResource, ?, ?>>
        extends Event<RetrieveRequest<TResource,?,?>,RetrieveEvent<TResource>,RetrieveErrorHandler<TResource>> {
	/* Callback functions */
    public abstract void errorResourceIdDoesNotExists(String id);
    public abstract void onRetrieve(TResource resource);
    /* ----------------- */

}