package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.request.ProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class ProjectEvent<TEvent extends ProjectEvent<TEvent, TRequest>, TRequest extends ProjectRequest<TRequest, TEvent>> extends Event<TEvent, TRequest> {
	
	public Project project() {
		return request().project();
	}
}
