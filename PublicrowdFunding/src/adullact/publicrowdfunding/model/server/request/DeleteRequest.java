package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.DeleteErrorHandler;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;

public class DeleteRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends AuthenticatedRequest<DeleteRequest<TResource, ?, ?>, DeleteEvent<TResource>, DeleteErrorHandler<TResource>>  {
    private TResource m_resource;

    public DeleteRequest(TResource resource, DeleteEvent event) {
        super(event, new DeleteErrorHandler<TResource>());

        this.m_resource = resource;
    }

    @Override
    public void execute() {
        m_resource.methodDELETE(service()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

                    @Override
                    public SimpleServerResponse call(Throwable throwable) {
                        return null;
                    }

                })
                .subscribe(new Action1<SimpleServerResponse>() {

                    @Override
                    public void call(SimpleServerResponse response) {
                        if (response == null) {
                            errorHandler().manageCallback();
                            return;
                        }

                        switch (response.code) {
                            case 0: // Ok!
                                done();
                                event().onDelete(m_resource);
                                break;
                            case 1:
                                event().errorUsernameDoesNotExist(m_resource.getResourceId());
                                break;
                        }
                    }
                });
    }
}