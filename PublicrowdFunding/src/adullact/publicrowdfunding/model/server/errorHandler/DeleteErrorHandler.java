package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;
import adullact.publicrowdfunding.model.server.request.DeleteRequest;

/**
 * @author Ferrand and Nelaupe
 */
public class DeleteErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AdministratorErrorHandler<DeleteRequest<TResource, ?, ?>, DeleteEvent<TResource>, DeleteErrorHandler<TResource>>  {
    private boolean m_resourceIdDoesNotExist = false;

    @Override
    public void manageCallback() {
        super.manageCallback();
        if(m_resourceIdDoesNotExist) {
            event().errorResourceIdDoesNotExist();
        }
    }

    @Override
    public Throwable handleError(RetrofitError error) {
        m_resourceIdDoesNotExist = (error.getResponse().getStatus() == 404);
        return super.handleError(error);
    }
}