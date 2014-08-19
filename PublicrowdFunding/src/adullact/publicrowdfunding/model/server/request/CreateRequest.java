package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.entities.RowAffected;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateEvent;

public class CreateRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends
        AuthenticatedRequest<CreateRequest<TResource,?,?>,CreateEvent<TResource>,CreateErrorHandler<TResource>> {
    private TResource m_resource;

    public CreateRequest(TResource resource, CreateEvent<TResource> event) {
        super(event, new CreateErrorHandler<TResource>());

        this.m_resource = resource;
    }

    @Override
    public void execute() {
        m_resource.methodPOST(service()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RowAffected>() {
                    @Override
                    public void call(RowAffected response) {
                        done();
                        m_resource.setResourceId(response.id);
                        event().onCreate(m_resource);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        errorHandler().manageCallback();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("Hello");
                    }
                });
    }
}