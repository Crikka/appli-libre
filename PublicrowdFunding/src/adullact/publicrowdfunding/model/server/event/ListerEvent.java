package adullact.publicrowdfunding.model.server.event;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.errorHandler.ListerErrorHandler;
import adullact.publicrowdfunding.model.server.request.ListerRequest;

public abstract class ListerEvent<TResource extends Resource<TResource, ?, ?>>
        extends Event<ListerRequest<TResource,?,?>,ListerEvent<TResource>,ListerErrorHandler<TResource>> {

	/* Callback functions */
    public abstract void onLister(ArrayList<TResource> resources);
    /* ----------------- */

}