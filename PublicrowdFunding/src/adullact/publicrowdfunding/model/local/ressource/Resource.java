package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import adullact.publicrowdfunding.model.server.request.DeleteRequest;
import adullact.publicrowdfunding.model.server.request.ListerRequest;
import adullact.publicrowdfunding.model.server.request.RetrieveRequest;
import adullact.publicrowdfunding.model.server.request.UpdateRequest;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ferrand on 18/07/2014.
 */
public abstract class Resource<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource> {
    /* ---- Cache manager ---- */
    private static HashMap<String, HashMap<String, Cache>> cachedResource = new HashMap<String, HashMap<String, Cache>>();
    private Cache<TResource> m_cache;

    public boolean isCached() {
        return (m_cache != null);
    }

    protected void insertIntoCache() {
        if(!isCached()) {
            HashMap<String, Cache> cachedType = cachedResource.get(getClass().getSimpleName());
            m_cache = new Cache(this);
            if(cachedType == null) {
                cachedType = new HashMap<String, Cache>();
                cachedResource.put(getClass().getSimpleName(), cachedType);
            }

            cachedType.put(getResourceId(), m_cache);
        }
    }

    public Cache<TResource> getCache() {
        if(!isCached()) {
            return new Cache(this).forceRetrieve(); // Shallow cache
        }

        return m_cache;
    }

    /* ----------------------- */

    private boolean m_changed;

    public Resource() {
        m_changed = false;
        m_cache = null;
    }

    protected void changed() {
        m_changed = true;
    }

    public boolean hasChanged() {
        boolean ret = m_changed;
        m_changed = false;

        return ret;
    }

    final public TResource initializeID(String id) {
        HashMap<String, Cache> cachedType = cachedResource.get(getClass().getSimpleName());
        if (cachedType == null) {
            cachedType = new HashMap<String, Cache>();
            cachedResource.put(getClass().getSimpleName(), cachedType);
        } else {
            m_cache = cachedType.get(id);
        }

        return internInitializeID(id);
    }

    public abstract String getResourceId();
    protected abstract TResource internInitializeID(String id);
    public abstract TServerResource toServerResource();
    public abstract TResource makeCopyFromServer(TServerResource serverResource);
    public abstract TResource syncFromServer(TDetailedServerResource detailedServerResource);
    public abstract Observable<TDetailedServerResource> methodGET(Service service);
    public abstract Observable<ArrayList<TServerResource>> methodGETAll(Service service, Map<String, String> filter);
    public abstract Observable<SimpleServerResponse> methodPUT(Service service);
    public abstract Observable<SimpleServerResponse> methodPOST(Service service);
    public abstract Observable<SimpleServerResponse> methodDELETE(Service service);
    public void serverLister(ListerEvent<TResource> listerEvent) {
        (new ListerRequest<TResource, TServerResource, TDetailedServerResource>((TResource) this, new HashMap<String, String>(), listerEvent)).execute();
    }
    public void serverRetrieve(RetrieveEvent<TResource> retrieveEvent) {
        (new RetrieveRequest<TResource, TServerResource, TDetailedServerResource>((TResource) this, retrieveEvent)).execute();
    }
    public void serverCreate(CreateEvent<TResource> createEvent) {
        (new CreateRequest<TResource, TServerResource, TDetailedServerResource>((TResource) this, createEvent)).execute();
    }
    public void serverUpdate(UpdateEvent<TResource> updateEvent) {
        (new UpdateRequest<TResource, TServerResource, TDetailedServerResource>((TResource) this, updateEvent)).execute();
    }
    public void serverDelete(DeleteEvent<TResource> deleteEvent) {
        (new DeleteRequest<TResource, TServerResource, TDetailedServerResource>((TResource) this, deleteEvent)).execute();
    }
}
