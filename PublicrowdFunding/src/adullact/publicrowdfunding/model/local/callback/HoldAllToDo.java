package adullact.publicrowdfunding.model.local.callback;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 27/07/2014.
 */
public abstract class HoldAllToDo<TResource extends Resource<TResource, ?, ?>> extends WhatToDo<TResource> {
    private ArrayList<TResource> m_resources;

    public HoldAllToDo() {
        this.m_resources = new ArrayList<TResource>();
    }

    @Override
    public void hold(TResource resource) {
        m_resources.add(resource);
    }

    @Override
    public void eventually() {
        holdAll(m_resources);
    }

    public abstract void holdAll(ArrayList<TResource> resources);
}