package adullact.publicrowdfunding.model.server.request;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.ListerErrorHandler;
import adullact.publicrowdfunding.model.server.event.ListerEvent;

public class ListerRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends Request<ListerRequest<TResource,?,?>,ListerEvent<TResource>,ListerErrorHandler<TResource>> {
    private TResource m_resource;
    private Map<String, String> m_filter;

    public ListerRequest(TResource resource, Map<String, String> filter,  ListerEvent event) {
        super(event, new ListerErrorHandler<TResource>());

        this.m_resource = resource;
        this.m_filter = filter;
    }

    @Override
    public void execute() {
        m_resource.methodGETAll(service(), m_filter)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ArrayList<TServerResource>, ArrayList<TResource>>() {

                    @Override
                    public ArrayList<TResource> call(ArrayList<TServerResource> serverResources) {
                        ArrayList<TResource> resources = new ArrayList<TResource>();
                        for (TServerResource serverResource : serverResources) {
                            resources.add(m_resource.makeCopyFromServer(serverResource));
                        }
                        return resources;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<TResource>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        errorHandler().manageCallback();
                    }

                    @Override
                    public void onNext(ArrayList<TResource> resources) {
                        done();

                        event().onLister(resources);
                    }
                });
    }
}