package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.CreateProjectErrorHandler;
import adullact.publicrowdfunding.model.request.CreateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class CreateProjectEvent extends AuthentificatedEvent<CreateProjectEvent, CreateProjectRequest, CreateProjectErrorHandler> implements AuthentificationRequired, AdministratorFavour {
	
	/* Callback functions */
	public abstract void onProjectAdded(Project project);
	/* ----------------- */
	
	public Project project() {
		return request().project();
	}
	
}