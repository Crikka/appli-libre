package adullact.publicrowdfunding.model.local.callback;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 18/07/2014.
 */
public interface WhatToDo<TResource extends Resource> {
    void hold(final TResource resource);
    void eventually();
}
