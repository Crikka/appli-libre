package adullact.publicrowdfunding.model.local.cache;

import java.util.TreeSet;

import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 19/08/2014.
 */
public class CacheSet<TResource extends Resource<TResource, ?, ?>> {
    private TreeSet<Cache<TResource>> m_resource;
    private boolean m_stopForEach = false;

    public CacheSet() {
        this.m_resource = new TreeSet<Cache<TResource>>(Cache.howCompare());
    }

    public void add(TResource resource) {
        m_resource.add(resource.getCache());
    }

    public void add(Cache<TResource> resource) {
        m_resource.add(resource);
    }

    public void remove(TResource resource) {
        m_resource.remove(resource.getCache());
    }

    public void remove(Cache<TResource> resource) {
        m_resource.remove(resource);
    }

    public void forEach(final WhatToDo<TResource> whatToDo) {
        m_stopForEach = false;
        for(Cache<TResource> cache : m_resource) {
            if(m_stopForEach) {
                break;
            }
            cache.toResource(new HoldToDo<TResource>() {
                @Override
                public void hold(TResource resource) {
                    whatToDo.hold(resource);
                }
            });
        }
        m_stopForEach = false;
        whatToDo.eventually();
    }

    public void stopForEach() {
        m_stopForEach = true;
    }
}
