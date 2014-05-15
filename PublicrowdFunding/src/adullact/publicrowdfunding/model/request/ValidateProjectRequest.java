package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.event.ValidateProjectEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.Share;

public class ValidateProjectRequest extends ProjectRequest<ValidateProjectRequest, ValidateProjectEvent> {

	public ValidateProjectRequest(Project project) {
		super(project);
	}

	@Override
	public void execute(ValidateProjectEvent event) {
		if(Share.user.isAdmin()) {
			project().validate();
			event.onValidateProject(project());
		}
		else {
			event.errorAdministratorRequired();
		}
	}
	
}
