package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.request.CreateRequest;

public class CreateErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AuthenticatedErrorHandler<CreateRequest<TResource,?,?>,CreateEvent<TResource>,CreateErrorHandler<TResource>> {
    private boolean m_resourceIdAlreadyExist = false;

    @Override
    public void manageCallback() {
        super.manageCallback();
        if(m_resourceIdAlreadyExist) {
            event().errorResourceIdAlreadyUsed();
        }
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        m_resourceIdAlreadyExist = (error.getResponse().getStatus() == 409);
        return super.handleError(error);
    }
}