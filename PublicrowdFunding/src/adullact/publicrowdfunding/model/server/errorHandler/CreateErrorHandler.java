package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import retrofit.RetrofitError;

/**
 * @author Ferrand and Nelaupe
 */
public class CreateErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AdministratorErrorHandler<CreateRequest<TResource,?,?>,CreateEvent<TResource>,CreateErrorHandler<TResource>> {
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