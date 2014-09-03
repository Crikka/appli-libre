package adullact.publicrowdfunding.model.local.cache;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * Created by Ferrand on 03/09/2014.
 */
public class Local<TResource extends Resource<TResource, ?, ?>> extends Cache<TResource> {
    private boolean m_firstTime;

    public Local(TResource resource) {
        super(resource);

        m_firstTime = true;
    }

    @Override
    protected boolean timeToRetrieve() {
        return m_firstTime;
    }

    @Override
    protected void afterRetrieve() {
        m_firstTime = false;
    }

}
