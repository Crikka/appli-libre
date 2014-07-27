package adullact.publicrowdfunding.model.local.callback;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 21/07/2014.
 */
public abstract class HoldToDo<TResource extends Resource<TResource, ?, ?>> extends WhatToDo<TResource> {
    @Override
    public void eventually() {}
}
