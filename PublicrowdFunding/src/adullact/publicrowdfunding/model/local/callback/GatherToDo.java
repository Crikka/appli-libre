package adullact.publicrowdfunding.model.local.callback;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 21/07/2014.
 */
public abstract class GatherToDo<TResource extends Resource> implements WhatToDo<TResource> {
    private ArrayList<TResource> m_gathering;

    public GatherToDo() {
        this.m_gathering = new ArrayList<TResource>();
    }

    @Override
    public void hold(TResource resource) {
        m_gathering.add(resource);
    }

    @Override
    public void eventually() {
        eventually(m_gathering);
    }

    public abstract void eventually(ArrayList<TResource> resources);
}
