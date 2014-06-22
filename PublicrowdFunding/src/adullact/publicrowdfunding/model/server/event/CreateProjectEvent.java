package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.CreateProjectErrorHandler;
import adullact.publicrowdfunding.model.server.request.CreateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class CreateProjectEvent extends AuthentificatedEvent< CreateProjectRequest, CreateProjectEvent, CreateProjectErrorHandler> implements AuthentificationRequired, AdministratorFavour {
	
	/* Callback functions */
	public abstract void onProjectAdded(Project project);
	/* ----------------- */
	
}