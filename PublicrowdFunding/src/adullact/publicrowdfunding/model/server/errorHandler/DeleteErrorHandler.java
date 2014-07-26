package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import adullact.publicrowdfunding.model.server.request.DeleteRequest;

public class DeleteErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AuthenticatedErrorHandler<DeleteRequest<TResource, ?, ?>, DeleteEvent<TResource>, DeleteErrorHandler<TResource>>  {

}