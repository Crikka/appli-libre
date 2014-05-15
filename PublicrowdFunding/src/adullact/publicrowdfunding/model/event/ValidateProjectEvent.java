package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class ValidateProjectEvent  extends ProjectEvent<ValidateProjectEvent, ValidateProjectRequest> implements AdministratorRequired {
	
	/* Callback functions */
	public abstract void onValidateProject(Project project);
	/* ----------------- */
	
}
