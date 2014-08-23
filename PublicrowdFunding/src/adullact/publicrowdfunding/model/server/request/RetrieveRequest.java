package adullact.publicrowdfunding.model.server.request;

import java.util.ArrayList;

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
        m_resource.methodGET(service())
                .subscribeOn(Schedulers.io())
                .doOnError(new Action1<Throwable>() {

                    @Override
                    public void call(Throwable throwable) {
                        errorHandler().manageCallback();
                    }
                })
                .map(new Func1<TDetailedServerResource, TResource>() {

                    @Override
                    public TResource call(TDetailedServerResource detailedServerResource) {
                        m_resource.syncFromServer(detailedServerResource);
                        return m_resource;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TResource>() {

                    @Override
                    public void call(TResource resources) {
                        done();

                        event().onRetrieve(m_resource);
                    }
                });
    }
}