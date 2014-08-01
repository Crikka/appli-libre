package adullact.publicrowdfunding.model.server.request;

import java.util.ArrayList;
import java.util.Map;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.ListerErrorHandler;
import adullact.publicrowdfunding.model.server.event.ListerEvent;

public class ListerRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends AuthenticatedRequest<ListerRequest<TResource, ?, ?>, ListerEvent<TResource>, ListerErrorHandler<TResource>> {
    private TResource m_resource;
    private Map<String, String> m_filter;

    public ListerRequest(TResource resource, Map<String, String> filter,  ListerEvent event) {
        super(event, new ListerErrorHandler<TResource>());

        this.m_resource = resource;
        this.m_filter = filter;
    }

    @Override
    public void execute() {
        m_resource.methodGETAll(service(), m_filter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, ArrayList<TServerResource>>() {

                    @Override
                    public ArrayList<TServerResource> call(Throwable throwable) {
                        return null;
                    }

                })
                .subscribe(new Action1<ArrayList<TServerResource> >() {

                    @Override
                    public void call(ArrayList<TServerResource>  serverResources) {
                        if(serverResources == null) {
                            errorHandler().manageCallback();
                            return;
                        }

                        ArrayList<TResource> resources = new ArrayList<TResource>();
                        for(TServerResource serverResource : serverResources) {
                            resources.add(m_resource.makeCopyFromServer(serverResource));
                        }

                        event().onLister(resources);
                    }
                });
    }
}