package adullact.publicrowdfunding.model.local.cache;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Give meta data about resource.
 *
 * Created by Ferrand on 27/07/2014.
 */
public class Sync<TResource extends Resource<TResource, ?, ?>>  {
    public static enum State {
        further, // Just create in local, not in server database
        changed, // Modified during last update
        unchanged, // Same before and after retrieve
        deleted // Deleted on server
    }
    private State m_state;

    public TResource resource;
    public String id;

    public Sync(TResource resource) {
        this.resource = resource;
        this.id = resource.getResourceId();
        this.m_state = State.further;
    }

     public void setState(State state) {
         m_state = state;
     }

    public boolean isFurther() {
        return (m_state == State.further);
    }

    public boolean isChanged() {
        return (m_state == State.changed);
    }

    public boolean isUnchanged() {
        return (m_state == State.unchanged);
    }

    public boolean isDeleted() {
        return (m_state == State.deleted);
    }
}
