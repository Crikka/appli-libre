package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;
import adullact.publicrowdfunding.model.server.request.RetrieveRequest;

public class RetrieveErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends ErrorHandler<RetrieveRequest<TResource,?,?>,RetrieveEvent<TResource>,RetrieveErrorHandler<TResource>> {

}