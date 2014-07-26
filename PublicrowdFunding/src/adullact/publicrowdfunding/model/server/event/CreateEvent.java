package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticatedErrorHandler;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateRequest;

public abstract class CreateEvent<TResource extends Resource<TResource, ?, ?>>
        extends AuthenticatedEvent<CreateRequest<TResource, ?, ?>, CreateEvent<TResource>, CreateErrorHandler<TResource>> {

	/* Callback functions */
    public abstract void errorResourceIdAlreadyUsed(String id);
    public abstract void onCreate(TResource resource);
    /* ----------------- */

}