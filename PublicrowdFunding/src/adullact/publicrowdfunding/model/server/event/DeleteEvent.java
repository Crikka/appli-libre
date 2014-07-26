package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.errorHandler.DeleteErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import adullact.publicrowdfunding.model.server.request.DeleteRequest;

public abstract class DeleteEvent<TResource extends Resource<TResource, ?, ?>>
        extends AuthenticatedEvent<DeleteRequest<TResource, ?, ?>, DeleteEvent<TResource>, DeleteErrorHandler<TResource>>   {

	/* Callback functions */
    public abstract void errorUsernameDoesNotExist(String username);
    public abstract void onDelete(TResource resource);
    /* ----------------- */

}