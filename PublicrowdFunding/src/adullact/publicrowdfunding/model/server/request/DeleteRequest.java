package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.errorHandler.DeleteErrorHandler;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;

public class DeleteRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends AdministratorRequest<DeleteRequest<TResource, ?, ?>, DeleteEvent<TResource>, DeleteErrorHandler<TResource>>  {
    private TResource m_resource;

    public DeleteRequest(TResource resource, DeleteEvent<TResource> event) {
        super(event, new DeleteErrorHandler<TResource>());

        this.m_resource = resource;
    }

    @Override
    public void execute() {
        m_resource.methodDELETE(service()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SimpleServerResponse>() {
                    @Override
                    public void call(SimpleServerResponse response) {
                        done();
                        event().onDelete(m_resource);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        errorHandler().manageCallback();
                    }
                }, new Action0() {
                    @Override
                    public void call() {

                    }
                });
    }
}