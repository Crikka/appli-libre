package adullact.publicrowdfunding.model.local.cache;

import adullact.publicrowdfunding.model.local.ressource.Resource;

/**
 * @author Ferrand and Nelaupe
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
