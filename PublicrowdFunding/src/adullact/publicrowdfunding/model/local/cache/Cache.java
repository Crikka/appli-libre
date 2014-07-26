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
    private TResource m_data;
    private DateTime m_dateTime;

    public Cache(TResource data) {
        this.m_data = data;
        this.m_dateTime = DateTime.now();
    }

    public String getResourceId() {
        return m_data.getResourceId();
    }

    public void toData(final WhatToDo<TResource> whatToDo) {
        Duration duration = new Duration(DateTime.now(), m_dateTime);
        if(duration.getStandardMinutes() > 15) { // Data may be outdated
            final RetrieveEvent<TResource> event = new RetrieveEvent<TResource>() {
                @Override
                public void errorResourceIdDoesNotExists(String id) {
                    whatToDo.hold(m_data);
                }

                @Override
                public void onRetrieve(TResource resource) {
                    whatToDo.hold(resource);
                }
            };
            m_data.serverRetrieve(event);
        }
        else {
            whatToDo.hold(m_data);
        }
    }
}
