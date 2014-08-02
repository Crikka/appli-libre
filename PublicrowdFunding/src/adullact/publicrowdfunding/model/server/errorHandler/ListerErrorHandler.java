package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.request.ListerRequest;
import adullact.publicrowdfunding.model.server.event.ListerEvent;

public class ListerErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AnonymousErrorHandler<ListerRequest<TResource, ?, ?>, ListerEvent<TResource>, ListerErrorHandler<TResource>> {

    @Override
    public Throwable handleError(RetrofitError error) {
        System.out.println(error.getMessage());
        return null;
    }
}