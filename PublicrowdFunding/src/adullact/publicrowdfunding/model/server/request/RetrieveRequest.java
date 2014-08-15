package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.RetrieveErrorHandler;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrieveRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends Request<RetrieveRequest<TResource,?,?>,RetrieveEvent<TResource>,RetrieveErrorHandler<TResource>> {
    private TResource m_resource;

    public RetrieveRequest(TResource resource, RetrieveEvent<TResource> event) {
        super(event, new RetrieveErrorHandler<TResource>());

        this.m_resource = resource;
    }

    @Override
    public void execute() {
        m_resource.methodGET(service()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, TDetailedServerResource>() {

                    @Override
                    public TDetailedServerResource call(Throwable throwable) {
                        return null;
                    }

                })
                .subscribe(new Action1<TDetailedServerResource>() {
                               @Override
                               public void call(TDetailedServerResource detailedServerResources) {
                                   m_resource.syncFromServer(detailedServerResources);
                                   event().onRetrieve(m_resource);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                errorHandler().manageCallback();
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                System.out.println("Hello");
                            }
                        });
    }
}