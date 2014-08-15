package adullact.publicrowdfunding.model.server.errorHandler;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import adullact.publicrowdfunding.model.server.request.UpdateRequest;
import retrofit.RetrofitError;

public class UpdateErrorHandler<TResource extends Resource<TResource, ?, ?>>
        extends AdministratorErrorHandler<UpdateRequest<TResource, ?, ?>, UpdateEvent<TResource>, UpdateErrorHandler<TResource>>  {
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
