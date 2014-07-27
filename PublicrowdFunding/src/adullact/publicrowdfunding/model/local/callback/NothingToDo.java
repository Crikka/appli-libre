package adullact.publicrowdfunding.model.local.callback;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 19/07/2014.
 */
public class NothingToDo<TResource extends Resource<TResource, ?, ?>> extends WhatToDo<TResource> {
    @Override
    public void hold(final TResource data) {}

    @Override
    public void eventually() {}
}
