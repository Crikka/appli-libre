package adullact.publicrowdfunding.model.local.callback;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * @author Ferrand and Nelaupe
 */
public abstract class HoldPerStateToDo<TResource extends Resource<TResource, ?, ?>> extends WhatToDo<TResource> {

    @Override
    public void hold(TResource resource) {
        if(isFurther()) {
            holdFurther(resource);
        }

        if(isChanged()) {
            holdChanged(resource);
        }

        if(isUnchanged()) {
            holdUnchanged(resource);
        }

        if(isDeleted()) {
            holdDeleted(resource);
        }
    }

    public abstract void holdFurther(TResource furtherResources);
    public abstract void holdChanged(TResource changedResources);
    public abstract void holdUnchanged(TResource unchangedResources);
    public abstract void holdDeleted(TResource deletedResources);
}
