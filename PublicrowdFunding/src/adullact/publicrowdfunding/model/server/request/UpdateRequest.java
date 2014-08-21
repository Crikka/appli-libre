package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.UpdateErrorHandler;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;

public class UpdateRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends AdministratorRequest<UpdateRequest<TResource, ?, ?>, UpdateEvent<TResource>, UpdateErrorHandler<TResource>>  {
    private TResource m_resource;

    public UpdateRequest(TResource resource, UpdateEvent event) {
        super(event, new UpdateErrorHandler<TResource>());

        this.m_resource = resource;
    }

    @Override
    public void execute() {
        m_resource.methodPUT(service()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SimpleServerResponse>() {
                    @Override
                    public void call(SimpleServerResponse response) {
                        done();
                        event().onUpdate(m_resource);
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
