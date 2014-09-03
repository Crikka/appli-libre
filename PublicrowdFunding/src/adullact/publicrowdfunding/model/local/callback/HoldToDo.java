package adullact.publicrowdfunding.model.local.callback;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class HoldToDo<TResource extends Resource<TResource, ?, ?>> extends WhatToDo<TResource> {
    @Override
    public void eventually() {}
}
