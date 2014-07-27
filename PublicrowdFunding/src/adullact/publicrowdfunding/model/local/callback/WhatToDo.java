package adullact.publicrowdfunding.model.local.callback;

import adullact.publicrowdfunding.model.local.cache.Sync;
import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 18/07/2014.
 */
public abstract class WhatToDo<TResource extends Resource<TResource, ?, ?>> {
    private Sync<TResource> m_resource;

    protected final boolean isFurther() {
        return m_resource.isFurther();
    }
    protected final boolean isChanged() {
        return m_resource.isChanged();
    }
    protected final boolean isUnchanged() {
        return m_resource.isUnchanged();
    }
    protected final boolean isDeleted() {
        return m_resource.isDeleted();
    }

    public final void give(final Sync<TResource> resource) {
        m_resource = resource;
        hold(m_resource.resource);
    }

    public abstract void hold(final TResource resource);
    public abstract void eventually();


}
