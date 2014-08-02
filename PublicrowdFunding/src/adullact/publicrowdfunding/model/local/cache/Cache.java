package adullact.publicrowdfunding.model.local.cache;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;

/**
 * Created by Ferrand on 18/07/2014.
 */
public class Cache<TResource extends Resource<TResource, ?, ?>> {
    private Sync<TResource> m_resource;
    private DateTime m_dateTime;

    public Cache(TResource resource) {
        this.m_resource = new Sync<TResource>(resource);
        this.m_dateTime = DateTime.now();
    }

    public String getResourceId() {
        return m_resource.id;
    }

    public Cache<TResource> forceRetrieve() {
        m_dateTime = null;

        return this;
    }

    public TResource getCachedResource() {
        return m_resource.resource;
    }

    public void toResource(final WhatToDo<TResource> whatToDo) {
        boolean timeToRetrieve = false;
        if(m_dateTime == null) {
            timeToRetrieve = true;
        }
        else {
            Duration duration = new Duration(DateTime.now(), m_dateTime);
            timeToRetrieve = (duration.getStandardMinutes() > 15);// Data may be outdated
       }
        if(timeToRetrieve) {
            final RetrieveEvent<TResource> event = new RetrieveEvent<TResource>() {

                @Override
                public void errorResourceIdDoesNotExists(String id) {
                    m_resource.setState(Sync.State.deleted);
                    whatToDo.give(m_resource);
                }

                @Override
                public void onRetrieve(TResource resource) {
                    if(m_resource.resource.hasChanged()) {
                        m_resource.setState(Sync.State.changed);
                    }
                    else {
                        m_resource.setState(Sync.State.unchanged);
                    }
                    whatToDo.give(m_resource);
                }
            };
            m_resource.resource.serverRetrieve(event);
        }
        else {
            whatToDo.hold(m_resource.resource);
        }
    }
}
