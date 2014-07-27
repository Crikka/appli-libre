package adullact.publicrowdfunding.model.local.callback;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 21/07/2014.
 */
public abstract class GatherToDo<TResource extends Resource<TResource, ?, ?>> extends WhatToDo<TResource> {
    private ArrayList<TResource> m_furtherResources;
    private ArrayList<TResource> m_changedResources;
    private ArrayList<TResource> m_unchangedResources;
    private ArrayList<TResource> m_deletedResources;

    public GatherToDo() {
        this.m_furtherResources = new ArrayList<TResource>();
        this.m_changedResources = new ArrayList<TResource>();
        this.m_unchangedResources = new ArrayList<TResource>();
        this.m_deletedResources = new ArrayList<TResource>();
    }

    @Override
    public void hold(TResource resource) {
        if(isFurther()) {
            m_furtherResources.add(resource);
        }

        if(isChanged()) {
            m_changedResources.add(resource);
        }

        if(isUnchanged()) {
            m_unchangedResources.add(resource);
        }

        if(isDeleted()) {
            m_deletedResources.add(resource);
        }
    }

    @Override
    public void eventually() {
        holdAllFurther(m_furtherResources);
        holdAllChanged(m_changedResources);
        holdAllUnchanged(m_unchangedResources);
        holdAllDeleted(m_deletedResources);
    }

    public abstract void holdAllFurther(ArrayList<TResource> furtherResources);
    public abstract void holdAllChanged(ArrayList<TResource> changedResources);
    public abstract void holdAllUnchanged(ArrayList<TResource> unchangedResources);
    public abstract void holdAllDeleted(ArrayList<TResource> deletedResources);
}
