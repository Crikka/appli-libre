package adullact.publicrowdfunding.model.local.cache;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Comparator;

import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;

/**
 * @author Ferrand and Nelaupe
 */
public class Cache<TResource extends Resource<TResource, ?, ?>> {
    private Sync<TResource> m_resource;
    private DateTime m_dateTime;
    private ArrayList<WhatToDo<TResource>> m_pendingWhatToDo;

    public Cache(TResource resource) {
        this.m_resource = new Sync<TResource>(resource);
        this.m_pendingWhatToDo = new ArrayList<WhatToDo<TResource>>();

        this.m_dateTime = null;
    }

    public static Comparator<Cache> howCompare() {
        return new Comparator<Cache>() {
            @Override
            public int compare(Cache cache1, Cache cache2) {
                return cache1.getResourceId().compareTo(cache2.getResourceId());
            }
        };
    }

    public String getResourceId() {
        return m_resource.id;
    }

    final public Cache<TResource> useIt() {
        m_resource.resource.overrideCache(this);

        return this;
    }

    public Cache<TResource> declareUpToDate() {
        m_dateTime = DateTime.now();

        return this;
    }

    public void setResource(TResource resource) {
        m_resource.resource = resource;
    }

    public Cache<TResource> forceRetrieve() {
        m_dateTime = null;

        return this;
    }

    public void toResource(final WhatToDo<TResource> whatToDo) {
        if(timeToRetrieve()) {
            final RetrieveEvent<TResource> event = new RetrieveEvent<TResource>() {

                @Override
                public void errorResourceIdDoesNotExists(String id) {
                    m_resource.setState(Sync.State.deleted);
                    workWithResource();
                }

                @Override
                public void onRetrieve(TResource resource) {
                    if(m_resource.resource.hasChanged()) {
                        m_resource.setState(Sync.State.changed);
                    }
                    else {
                        m_resource.setState(Sync.State.unchanged);
                    }
                    afterRetrieve();

                    workWithResource();
                }

                @Override
                public void errorNetwork() {
                    workWithResource();
                }

                @Override
                public void errorServer() {
                    workWithResource();
                }
            };

            if(addToPendingWhatToDo(whatToDo)) {
                m_resource.resource.serverRetrieve(event);
            }
        }
        else {
            whatToDo.give(m_resource);
            whatToDo.eventually();
        }
    }

    protected boolean timeToRetrieve() {
        if(m_dateTime == null) {
            return true;
        }
        else {
            Duration duration = new Duration(m_dateTime, DateTime.now());
            return (duration.getStandardMinutes() > 15);// Data may be outdated
        }
    }

    protected void afterRetrieve() {
        m_dateTime = DateTime.now();
    }

    /**
     *
     * @param whatToDo
     * @return true if new queue was created
     */
    private boolean addToPendingWhatToDo(WhatToDo<TResource> whatToDo) {
        boolean res = m_pendingWhatToDo.isEmpty();
        m_pendingWhatToDo.add(whatToDo);

        return res;
    }

    private void workWithResource() {
        for(WhatToDo<TResource> whatToDo : m_pendingWhatToDo) {
            whatToDo.give(m_resource);
            whatToDo.eventually();
        }
        m_pendingWhatToDo.clear();
    }
}
