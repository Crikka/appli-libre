package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.request.RetrieveRequest;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;

public class RetrieveErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AnonymousErrorHandler<RetrieveRequest<TResource, ?, ?>, RetrieveEvent<TResource>, RetrieveErrorHandler<TResource>> {

    @Override
    public Throwable handleError(RetrofitError error) {
        System.out.println(error.getMessage());
        return null;
    }
}