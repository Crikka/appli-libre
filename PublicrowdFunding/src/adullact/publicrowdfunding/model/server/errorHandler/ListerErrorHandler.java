package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import adullact.publicrowdfunding.model.server.request.ListerRequest;

public class ListerErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends ErrorHandler<ListerRequest<TResource,?,?>,ListerEvent<TResource>,ListerErrorHandler<TResource>> {
    
}