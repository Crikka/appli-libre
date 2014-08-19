package adullact.publicrowdfunding.model.local.cache;

import java.util.TreeSet;

import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 19/08/2014.
 */
public class CacheSet<TResource extends Resource<TResource, ?, ?>> {
    TreeSet<Cache<TResource>> m_resource;

    public CacheSet() {
        this.m_resource = new TreeSet<Cache<TResource>>(Cache.howCompare());
    }

    public void add(TResource resource) {
        m_resource.add(resource.getCache());
    }

    public void add(Cache<TResource> resource) {
        m_resource.add(resource);
    }

    public void forEach(final WhatToDo<TResource> whatToDo) {
        for(Cache<TResource> cache : m_resource) {
            cache.toResource(new HoldToDo<TResource>() {
                @Override
                public void hold(TResource resource) {
                    whatToDo.hold(resource);
                }
            });
        }
        whatToDo.eventually();
    }
}
