package adullact.publicrowdfunding.model.server.request;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.RetrieveErrorHandler;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;
import rx.Observer;
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
                .map(new Func1<TDetailedServerResource, TResource>() {

                    @Override
                    public TResource call(TDetailedServerResource detailedServerResource) {
                        m_resource.syncFromServer(detailedServerResource);
                        return m_resource;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TResource>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        errorHandler().manageCallback();
                    }

                    @Override
                    public void onNext(TResource resource) {
                        done();

                        event().onRetrieve(resource);
                    }
                });
    }
}