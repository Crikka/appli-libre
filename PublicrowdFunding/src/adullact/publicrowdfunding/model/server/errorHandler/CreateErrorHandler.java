package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.request.AuthenticatedRequest;
import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import adullact.publicrowdfunding.model.server.event.CreateEvent;

public class CreateErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AnonymousErrorHandler<CreateRequest<TResource, ?, ?>, CreateEvent<TResource>, CreateErrorHandler<TResource>> {

    @Override
    public Throwable handleError(RetrofitError error) {
        System.out.println(error.getMessage());
        return null;
    }
}