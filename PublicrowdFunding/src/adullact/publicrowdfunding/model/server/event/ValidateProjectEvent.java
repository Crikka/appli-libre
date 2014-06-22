package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.ValidateProjectErrorHandler;
import adullact.publicrowdfunding.model.server.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class ValidateProjectEvent  extends AuthentificatedEvent<ValidateProjectRequest, ValidateProjectEvent, ValidateProjectErrorHandler> implements AdministratorRequired {
	
	/* Callback functions */
	public abstract void onValidateProject(Project project);
	/* ----------------- */
	
	public Project project() {
		return request().project();
	}
	
}
