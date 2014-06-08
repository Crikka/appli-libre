package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.errorHandle.ValidateProjectErrorHandler;
import adullact.publicrowdfunding.model.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class ValidateProjectEvent  extends AuthentificatedEvent<ValidateProjectEvent, ValidateProjectRequest, ValidateProjectErrorHandler> implements AdministratorRequired {
	
	/* Callback functions */
	public abstract void onValidateProject(Project project);
	/* ----------------- */
	
	public Project project() {
		return request().project();
	}
	
}
